import { Request,Response } from 'express';//Nhúng kiểu Request và Response từ module express
import NguoiDung from '../../model/NguoiDung.model';

import {hashPassword,verifyPassword} from '../../helper/hashAndVerifyPassword.helper';

export const login= async (req:Request,res:Response)=>{
    const {email,password} = req.body;
    try {
        const user= await NguoiDung.findOne({
            where:{
                email:email,
                trangThai:'active',
            },
            raw:true
        });
        if(!user){
            res.json({
                code:401,
                message:'Tài khoản không tồn tại hoặc đã bị khoá',
            })
        }else{
            const isMatch=verifyPassword(password,user['matKhau']);
            if(isMatch){
                res.json({
                    code:200,
                    message:'Đăng nhập thành công',
                    data:{
                        id:user['idNguoiDung'],
                        tenNguoiDung:user['hoTen'],
                        email:user['email']
                    },
                })
            }else{
                res.json({
                    code:401,
                    message:'Sai mật khẩu',
                })
            }
        }
    } catch (error) {
        res.json({
            code:500,
            message:'Lỗi server',
            error:error.message,
        })
    }
}