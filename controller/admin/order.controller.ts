import { Request,Response } from 'express';//Nhúng kiểu Request và Response từ module express
import * as allModel from '../../model/index.model';

import { paginationGeneral } from '../../helper/pagination.helper';
import { addOrderToQueue } from '../../helper/updateDoneOrder.helper';
import { parse } from 'path';

export const index = async(req: Request, res: Response)=>{
    const pagination = await paginationGeneral(req,4,allModel.DonHang);
    const orders = await allModel.DonHang.findAll({
        where: {
            deleted: 0
        },
        include: [
            {
                model: allModel.NguoiDung,
                as: 'User', 
                attributes: ['hoTen'] 
            }
        ],
        raw: true,
        limit:pagination.limitItems,
        offset: pagination.skip
    });
    for(const order of orders){
        order['tenNguoiDung']=order['User.hoTen'];
        delete order['User.hoTen'];
    }
    res.render('admin/pages/order/index',{
        title:'Quản lý đơn hàng',
        orders:orders,
        pagination:pagination
    })
}

export const detailOrder = async(req: Request, res: Response)=>{
    const {idDonHang} = req.params;
    let sum=0;
    try{
        const order = await allModel.DonHang.findOne({
            where: 
            { 
                idDonHang : idDonHang,
            },
            include: [
                {
                    model: allModel.NguoiDung,
                    as: 'User', 
                    attributes: ['hoTen','sdt','avatar','gioiTinh'], 
                },
            ],
            raw: true, 
            nest: true, 
        });
        const foods=await allModel.ChiTietDonHang.findAll({
            where: {
                idDonHang: idDonHang
            },
            attributes:['soLuongDat'],
            include: [
                {
                    model: allModel.SanPham,
                    as: 'Product',
                    attributes: ['tenSanPham', 'giaTien', 'images','donViTinh'],
                },
            ],
            raw: true,
            nest: true,
        });
        for(const food of foods){
            food['Product']['giaTien'] = parseFloat(food['Product']['giaTien']).toLocaleString('vi-VN', {
                style: 'currency',
                currency: 'VND',
                minimumFractionDigits: 0,
                maximumFractionDigits: 0
            });
            sum+=(parseFloat(food['Product']['giaTien'])*parseFloat(food['soLuongDat']));
            food['Product']['images'] = JSON.parse(food['Product']['images'])[0];
        }
        const total=sum+15;
        res.render('admin/pages/order/detail',{
            title:'Chi tiết đơn hàng',
            order:order,
            foods:foods,
            sum:sum+'.000 đ',
            total:total+'.000 đ'
        })
    }catch(error){
        req.flash('error',error.message);
        res.redirect('/admin/management/order');
    }
}

export const deliverOrder = async(req: Request, res: Response)=>{
    const {idDonHang} = req.params;
    try{
        const order = await allModel.DonHang.findOne({
            where: 
            { 
                idDonHang : idDonHang,
                tinhTrang:"Đã xác nhận"
            },
        });
        if(!order){
            req.flash('error','Không tìm thấy đơn hàng');
            res.redirect('back');
        }else{
            await allModel.DonHang.update({
                tinhTrang:"Đang giao"
            },{
                where:{
                    idDonHang:idDonHang,
                    tinhTrang:"Đã xác nhận"
                }
            })
            
            const isQueue= await addOrderToQueue(idDonHang);
            if(!isQueue){
                res.status(404).json({message:'Đã xảy ra lỗi khi vận chuyển đơn hàng.'});
            }else{
                res.status(200).json({message:'Đơn hàng đã được vận chuyển!'});
            }   
        }
    }catch(error){
        res.status(500).json('Đã xảy ra lỗi khi vận chuyển đơn hàng.');
    }
}