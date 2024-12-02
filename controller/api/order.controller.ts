import { Request,Response } from 'express';//Nhúng kiểu Request và Response từ module express
import * as allModel from '../../model/index.model';
import {Op} from 'sequelize';
import he from 'he';

import generateNextId from '../../helper/generateNextId.helper';
export const newOrder= async (req: Request, res: Response) => {
    const {idDonHang,idSanPham,idUser,soLuongDat}=req.body;
    try{
        if(!idDonHang){
            const idDonHang=await generateNextId(allModel.DonHang,'DH');
            const newDonHang = await allModel.DonHang.create({
                idDonHang:idDonHang,
                idNguoiDung:idUser,
                tinhTrang:'Đang xử lý',
                ngayTao:new Date(),
            })
            const lastThreeIdDonHang = idDonHang.trim().slice(-3); // Lấy 3 ký tự cuối
            const lastThreeIdSanPham = idSanPham.trim().slice(-3); // Lấy 3 ký tự cuối
            const idChiTietDonHang = `CTDH${lastThreeIdDonHang}_${lastThreeIdSanPham}`;

            const isExist= await allModel.ChiTietDonHang.findOne({
                where:{
                    idDonHang:idDonHang,
                    idSanPham:idSanPham
                },
                raw:true
            })
            if(isExist){
                await allModel.ChiTietDonHang.update({
                    soLuongDat:isExist['soLuongDat']+soLuongDat,
                },{
                    where:{
                        idDonHang:idDonHang,
                        idSanPham:idSanPham
                    }
                })
            }else{
                const newChiTietDonHang = await allModel.ChiTietDonHang.create({
                    idChiTietDonHang: idChiTietDonHang,
                    idDonHang: idDonHang, // Liên kết với DonHang
                    idSanPham: idSanPham,
                    soLuongDat: soLuongDat,
                });
            }
            res.status(200).json(
            {
                message:'Đơn hàng đã được tạo và thêm món ăn thành công!',
                idDonHang:idDonHang
            });
        }else{
            const lastThreeIdDonHang = idDonHang.trim().slice(-3); // Lấy 3 ký tự cuối
            const lastThreeIdSanPham = idSanPham.trim().slice(-3); // Lấy 3 ký tự cuối
            const idChiTietDonHang = `CTDH${lastThreeIdDonHang}_${lastThreeIdSanPham}`;
            
            const isExist= await allModel.ChiTietDonHang.findOne({
                where:{
                    idDonHang:idDonHang,
                    idSanPham:idSanPham
                },
                raw:true
            })
            if(isExist){
                await allModel.ChiTietDonHang.update({
                    soLuongDat:isExist['soLuongDat']+soLuongDat,
                },{
                    where:{
                        idDonHang:idDonHang,
                        idSanPham:idSanPham
                    }
                })
            }else{
                const newChiTietDonHang = await allModel.ChiTietDonHang.create({
                    idChiTietDonHang: idChiTietDonHang,
                    idDonHang: idDonHang, // Liên kết với DonHang
                    idSanPham: idSanPham,
                    soLuongDat: soLuongDat,
                });
            }
            res.status(200).json(
            {
                message:'Thêm món thành công',
            });
        }
    }catch(error){
        res.status(500).json({message:'Lỗi '+error.message});
    }
    
}

export const detailOrder= async(req: Request, res: Response) => {
    const {idDonHang} = req.query;
    try {
        if (!idDonHang) {
            res.status(400).json({ message: "Thiếu idDonHang" });
        }
        const order = await allModel.DonHang.findOne({
            where: {
                idDonHang: idDonHang,
                tinhTrang:'Đang xử lý'
            },
            raw: true,
        });
        if (!order) {
            res.status(404).json({ message: "Không tìm thấy đơn hàng" });
        } else {
            const detailOrder = await allModel.ChiTietDonHang.findAll({
                where: {
                    idDonHang: idDonHang,
                },
                raw: true,
                attributes: ['idSanPham', 'soLuongDat'],
            });
            for (const food of detailOrder) {
                const foodDetail = await allModel.SanPham.findOne({
                    where: {
                        idSanPham: food['idSanPham'],
                    },
                    raw: true,
                    attributes: ['tenSanPham', 'giaTien', 'images','moTa'],
                });
                food['tenSanPham'] = foodDetail['tenSanPham'];
                food['giaTien'] =parseFloat(foodDetail['giaTien']);
                food['images'] = JSON.parse(foodDetail['images'])[0];
                food['moTa']=he.decode(foodDetail['moTa']);
                food['moTa'] = foodDetail['moTa'].replace(/^<p>/, '').replace(/<\/p>$/, '');
            }
            res.status(200).json(detailOrder);
        }
    } catch (error) {
        res.status(500).json({message:'Lỗi '+error.message});
    }
}

export const cancelFood= async(req: Request, res: Response) => {
    const {idDonHang,idSanPham}=req.body;
    try{
        const isExist= await allModel.ChiTietDonHang.findOne({
            where:{
                idDonHang:idDonHang,
                idSanPham:idSanPham
            },
            raw:true
        })
        if(isExist){
            await allModel.ChiTietDonHang.destroy({
                where:{
                    idDonHang:idDonHang,
                    idSanPham:idSanPham
                }
            })
            res.status(200).json({message:'Hủy món thành công'});
        }else{
            res.status(404).json({message:'Không tìm thấy món ăn'});
        }
    }catch(error){
        res.status(500).json({message:'Lỗi '+error.message});
    }
}

export const confirm= async(req: Request, res: Response) => {
    const {idDonHang,diaChi,ghiChu,phuongThucThanhToan,tinhTrang}=req.body;
    try {
        await allModel.DonHang.update({
            tinhTrang:tinhTrang,
            diaChi:diaChi,
            ghiChu:ghiChu,
            tinhTrangThanhToan:phuongThucThanhToan,
        },{
            where:{
                idDonHang:idDonHang,
                tinhTrang:'Đang xử lý'
            }
        })
        res.status(200).json({message:'Xác nhận đơn hàng thành công'});
    } catch (error) {
        res.status(500).json({message:'Lỗi '+error.message});
    }
}
export const deny= async(req: Request, res: Response) => {
    const {idDonHang}=req.body;
    console.log(idDonHang);
    try {
        await allModel.DonHang.update({
            tinhTrang:'Đã hủy',
        },{
            where:{
                idDonHang:idDonHang,
                tinhTrang:'Đã xác nhận'
            }
        })
        res.status(200).json({message:'Xác nhận đơn hàng thành công'});
    } catch (error) {
        res.status(500).json({message:'Lỗi '+error.message});
    }
}

export const listUnfinished= async (req: Request, res: Response) => {
    const {idNguoiDung}=req.query;
    try {
        const orders = await allModel.DonHang.findAll({
            where: 
            { 
                idNguoiDung:idNguoiDung, 
                tinhTrang: { [Op.in]: ['Đã xác nhận', 'Đang giao'] },
            },
            attributes: ['idDonHang', 'ngayTao', 'diaChi', 'tinhTrang','ghiChu','tinhTrangThanhToan'],
            raw: true, 
        });
        for(const order of orders){
            const foods= await allModel.ChiTietDonHang.findAll({
                where: {
                    idDonHang: order['idDonHang'],
                },
                attributes:['soLuongDat'],
                include: [
                    {
                        model: allModel.SanPham,
                        as: 'Product',
                        attributes: ['tenSanPham', 'giaTien', 'images'],
                    },
                ],
                raw: true,
                nest: true,
            });
            order['chiTietDonHangs']=[];
            for(const food of foods){
                food['Product']['images'] = JSON.parse(food['Product']['images'])[0];
                order['chiTietDonHangs'].push(food);
            }
        }
        res.status(200).json(orders);
    } catch (error) {
        res.status(500).json({message:'Lỗi '+error.message});
    }
}

export const listFinished= async (req: Request, res: Response) => {
    const {idNguoiDung}=req.query;
    try {
        const orders = await allModel.DonHang.findAll({
            where: 
            { 
                idNguoiDung:idNguoiDung, 
                tinhTrang: 'Hoàn thành',
            },
            attributes: ['idDonHang', 'ngayTao', 'diaChi', 'tinhTrang','ghiChu','tinhTrangThanhToan'],
            raw: true, 
        });
        for(const order of orders){
            const foods= await allModel.ChiTietDonHang.findAll({
                where: {
                    idDonHang: order['idDonHang'],
                },
                attributes:['soLuongDat'],
                include: [
                    {
                        model: allModel.SanPham,
                        as: 'Product',
                        attributes: ['tenSanPham', 'giaTien', 'images'],
                    },
                ],
                raw: true,
                nest: true,
            });
            order['chiTietDonHangs']=[];
            for(const food of foods){
                food['Product']['images'] = JSON.parse(food['Product']['images'])[0];
                order['chiTietDonHangs'].push(food);
            }
        }
        res.status(200).json(orders);
    } catch (error) {
        res.status(500).json({message:'Lỗi '+error.message});
    }
}

export const listDeny= async (req: Request, res: Response) => {
    const {idNguoiDung}=req.query;
    try {
        const orders = await allModel.DonHang.findAll({
            where: 
            { 
                idNguoiDung:idNguoiDung, 
                tinhTrang: 'Đã hủy',
            },
            attributes: ['idDonHang', 'ngayTao', 'diaChi', 'tinhTrang','ghiChu','tinhTrangThanhToan'],
            raw: true, 
        });
        for(const order of orders){
            const foods= await allModel.ChiTietDonHang.findAll({
                where: {
                    idDonHang: order['idDonHang'],
                },
                attributes:['soLuongDat'],
                include: [
                    {
                        model: allModel.SanPham,
                        as: 'Product',
                        attributes: ['tenSanPham', 'giaTien', 'images'],
                    },
                ],
                raw: true,
                nest: true,
            });
            order['chiTietDonHangs']=[];
            for(const food of foods){
                food['Product']['images'] = JSON.parse(food['Product']['images'])[0];
                order['chiTietDonHangs'].push(food);
            }
        }
        res.status(200).json(orders);
    } catch (error) {
        res.status(500).json({message:'Lỗi '+error.message});
    }
}

export const updateQuanity= async (req: Request, res: Response) => {
    const {idDonHang,idSanPham,soLuongDat}=req.body;
    try{
        const isExist= await allModel.ChiTietDonHang.findOne({
            where:{
                idDonHang:idDonHang,
                idSanPham:idSanPham
            }
        })
        if(!isExist){
            res.status(404).json({message:'Không tìm thấy món ăn'});
        }
        else{
            await allModel.ChiTietDonHang.update({
                soLuongDat:soLuongDat
            },{
                where:{
                    idDonHang:idDonHang,
                    idSanPham:idSanPham
                }
            })
            res.status(200).json({message:'Cập nhật số lượng thành công'});
        }
    }catch(error){
        res.status(500).json({message:'Lỗi '+error.message});
    }
}