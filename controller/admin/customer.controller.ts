import { Request,Response } from 'express';//Nhúng kiểu Request và Response từ module express
import { Op } from "sequelize";
import sequelize from '../../config/database';
import moment from 'moment';

import * as allModel from "../../model/index.model";//Nhúng tất cả model
import {paginationCustomer} from '../../helper/pagination.helper';

export const pageCustomer = async(req:Request,res:Response)=>{
    const pagination= await paginationCustomer(req,4);
    const customers= await allModel.NguoiDung.findAll({
        attributes:[
            'hoTen', 'email', 'sdt', 
            'ngaySinh', 'gioiTinh', 'avatar', 
            'trangThai', 'token'
        ],
        where:{
            vaiTro:'VT002',
            deleted:0,
        },
        limit: pagination.limitItems,
        offset: pagination.skip,
        raw:true
    })
    res.render('admin/pages/customer/index',{
        title:'Quản lý khách hàng',
        customers:customers,
        pagination:pagination
    })
}

export const changeStatus=async (req:Request, res:Response)=>{
    const {token,status} = req.params;
    const [affectedRows]= await allModel.NguoiDung.update(
        { trangThai: status }, // Dữ liệu cần cập nhật
        { where: { token: token } } // Điều kiện cập nhật
    );
    if (affectedRows > 0) {
        req.flash("success","Cập nhật trạng thái thành công");
    }else{
        req.flash("error","Cập nhật trạng thái thất bại");
    }
    res.redirect('back');
}

export const detailCustomerPage = async (req:Request, res:Response) => {
    (req.session as any).previousPage = req.headers.referer;
    const token = req.params.token;
    const data = await allModel.NguoiDung.findOne({
        where: {
            token: token,
            trangThai: 'active',
            vaiTro: 'VT002',
            deleted: 0
        },
        attributes: {
            exclude: ['idNguoiDung', 'ngayCapNhat'],
            include: [
                [sequelize.fn('COUNT', sequelize.fn('DISTINCT', sequelize.col('Orders.idDonHang'))), 'soLuongDonHang'], // Đếm số lượng đơn hàng
                [sequelize.fn('SUM', sequelize.literal('`Orders->OrderDetails`.`soLuongDat` * `Orders->OrderDetails->Product`.`giaTien`')), 'tongTienDonHang']
            ]
        },
        include: [
            {
                model: allModel.DonHang,
                as: 'Orders',
                attributes: [], // Không cần thuộc tính của DonHang
                include: [
                    {
                        model: allModel.ChiTietDonHang,
                        as: 'OrderDetails',
                        attributes: [], // Không cần thuộc tính của ChiTietDonHang
                        include: [
                            {
                                model: allModel.SanPham,
                                as: 'Product',
                                attributes: [] // Không cần thuộc tính của SanPham
                            }
                        ]
                    }
                ]
            }
        ],
        group: ['NguoiDung.idNguoiDung'], // Nhóm theo idNguoiDung để COUNT hoạt động chính xác
        raw: true
    });
    const customer={
        ...data,
    }
    customer['ngayTao']=moment(data['ngayTao']).format('YYYY-MM-DD');
    customer['soLuongDonHang']=(data['soLuongDonHang']>0)?data['soLuongDonHang']:0; // Kiểm tra xem có đơn hàng nào đã đặt hay chưa, nếu có thì hiển thị số lượng, ngược lại hiển thị 0
    customer['tongTienDonHang'] = (data['tongTienDonHang'] > 0) ? 
    new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(data['tongTienDonHang']) : 
    '0₫'; // Kiểm tra xem có đơn hàng nào đã đặt hay chưa, nếu có thì hiển thị tổng tiền, ngược lại hiển thị 0₫
    res.render('admin/pages/customer/detail',{
        title:'Chi tiết khách hàng',
        customer:customer,
    })
}

export const deleteCustomer = async(req: Request, res: Response)=>{
    const token = req.params.token;
    await allModel.NguoiDung.update({deleted:1}, {
        where:{
            token: token,
            trangThai:'active',
            deleted:0,
            vaiTro:'VT002'
        }
    });
    res.status(200).json({message:'Xóa khách hàng thành công'});
}