import { Request,Response } from 'express';//Nhúng kiểu Request và Response từ module express
import NguoiDung from '../../model/NguoiDung.model';

import {hashPassword,verifyPassword} from '../../helper/hashAndVerifyPassword.helper';
import generateNextId from '../../helper/generateNextId.helper';
import * as generateString from '../../helper/generateRandom.helper';
import * as isValid from '../../helper/validField.helper';

export const login= async (req:Request,res:Response)=>{
    const {email,matKhau} = req.body;
    try {
        const user= await NguoiDung.findOne({
            where:{
                email:email,
                trangThai:'active',
                vaiTro:'VT002',
                deleted:0
            },
            raw:true
        });
        if(!user){
            res.status(404).json({message:"Tài khoản không tồn tại hoặc đã bị khoá"});
        }else{
            const isMatch=verifyPassword(matKhau,user['matKhau']);
            if(isMatch){
                res.status(200).json({
                    message:"Đăng nhập thành công",
                    hoTen:user['hoTen'],
                    email:user['email'],
                    sdt:user['sdt'],
                    gioiTinh:user['gioiTinh'],
                    avatar:user['avatar']
                })
            }else{
                res.status(404).json({message:"Mật khẩu không chính xác"});
            }
        }
    } catch (error) {
        res.status(500).json({message:"Lỗi server"});
    }
}

export const register=async(req:Request,res:Response)=>{
    const {hoTenNguoiDung,email,sdt,matKhau,gioiTinh}=req.body;
    if (!isValid.isValidEmail(email)) {
        res.status(404).json({message:'Email không hợp lệ'});
        return;
    }
    if (!isValid.isValidPhone(sdt)) {
        res.status(404).json({message:'Số điện thoại không hợp lệ'});
        return;
    }
    try {
        const user= await NguoiDung.findOne({
            where:{
                email:email,
                trangThai:'active',
                vaiTro:'VT002'
            },
            raw:true
        });
        if(user){
            res.status(404).json({message:'Tài khoản đã tôn tại!'});
        }else{
            const newUser={
                idNguoiDung:await generateNextId(NguoiDung,'ND'),
                hoTen:hoTenNguoiDung,
                email:email,
                sdt:sdt,
                matKhau:hashPassword(matKhau),
                gioiTinh:gioiTinh,
                trangThai:'active',
                ngayTao:new Date(),
                ngayCapNhat:new Date(),
                vaiTro:'VT002',
                token:generateString.generateRandomString(30), // Tạo token ngẫu nhiên
            }
            const createdUser = await NguoiDung.create(newUser);
            res.status(200).json({message: 'Đăng ký thành công!'});
        }
    } catch (error) {
        res.status(500).json({message:'Lỗi server'});
    }

}