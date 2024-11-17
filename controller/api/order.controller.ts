import { Request,Response } from 'express';//Nhúng kiểu Request và Response từ module express
import * as allModel from '../../model/index.model';

import generateNextId from '../../helper/generateNextId.helper';
export const newOrder= async (req: Request, res: Response) => {
    const {idDonHang,idUser,idSanPham,soLuongDat}=req.body;
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
    
            const newChiTietDonHang = await allModel.ChiTietDonHang.create({
                idChiTietDonHang: idChiTietDonHang,
                idDonHang: idDonHang, // Liên kết với DonHang
                idSanPham: idSanPham,
                soLuongDat: soLuongDat,
            });
            res.status(200).json(
            {
                message:'Đơn hàng đã được tạo và thêm món ăn thành công!',
                idDonHang:idDonHang
            });
        }else{
            const lastThreeIdDonHang = idDonHang.trim().slice(-3); // Lấy 3 ký tự cuối
            const lastThreeIdSanPham = idSanPham.trim().slice(-3); // Lấy 3 ký tự cuối
            const idChiTietDonHang = `CTDH${lastThreeIdDonHang}_${lastThreeIdSanPham}`;
    
            const newChiTietDonHang = await allModel.ChiTietDonHang.create({
                idChiTietDonHang: idChiTietDonHang,
                idDonHang: idDonHang, // Liên kết với DonHang
                idSanPham: idSanPham,
                soLuongDat: soLuongDat,
            });
            res.status(200).json(
            {
                message:'Thêm món thành công',
            });
        }
    }catch(error){
        res.status(500).json({message:'Lỗi server'});
    }
    
}