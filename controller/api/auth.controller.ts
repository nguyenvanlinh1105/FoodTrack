import { Request,Response } from 'express';//Nhúng kiểu Request và Response từ module express
import jwt from 'jsonwebtoken';

import * as allMode from "../../model/index.model";//Nhúng tất cả model


import {hashPassword,verifyPassword} from '../../helper/hashAndVerifyPassword.helper';

export const login= async (req:Request,res:Response)=>{
    const {email,matKhau} = req.body;
    console.log(email,matKhau);
    try {
        const user= await allMode.NguoiDung.findOne({
            where:{
                email:email,
                trangThai:'active',
                deleted:0
            },
            include: [
                {
                    model: allMode.VaiTro,
                    as: 'Role',
                    attributes: ['tenVaiTro'] // Lấy thuộc tính tenVaiTro
                }
            ],
            attributes: {
                exclude: ['ngayTao', 'ngayCapNhat', 'vaiTro'] // Loại bỏ các thuộc tính không cần thiết
            },
            raw:true
        });
        if(!user){
            res.status(404).json({message:"Tài khoản không tồn tại hoặc đã bị khoá"});
        }else{
            const isMatch=verifyPassword(matKhau,user['matKhau']);
            if(isMatch){
                const result = {
                    ...user,
                    tenVaiTro: user['Role.tenVaiTro'],
                };
                console.log(result['tenVaiTro']);
                const accessToken=jwt.sign({
                    id:result['idNguoiDung'],
                    role:result['tenVaiTro'],
                },process.env.ACCESS_TOKEN_SECRET,{expiresIn:'1h'});

                const refreshToken=jwt.sign({
                    id:result['idNguoiDung'],
                    role:result['tenVaiTro'],
                },process.env.REFRESH_TOKEN_SECRET,{expiresIn:'365d'});
                
                res.status(200).json({
                    message:"Đăng nhập thành công",
                    id:result['idNguoiDung'],
                    hoTen:result['hoTen'],
                    email:result['email'],
                    sdt:result['sdt'],
                    gioiTinh:result['gioiTinh'],
                    avatar:result['avatar'],
                    accessToken:accessToken
                })
            }else{
                res.status(404).json({message:"Mật khẩu không chính xác"});
            }
        }
    } catch (error) {
        res.status(500).json({message:"Lỗi server"});
    }
}

export const users= async (req: Request, res: Response) => {
    const users=await allMode.NguoiDung.findAll({
        where:{
            deleted:0,
            trangThai:'active',
        },
        attributes: {
            exclude: ['ngayTao', 'ngayCapNhat', 'vaiTro'] // Loại bỏ các thuộc tính không cần thiết
        },
        raw:true
    })
    res.status(200).json(users);
}

export const deleteUser= async (req: Request, res: Response) => {
    res.status(200).json({message:"Xóa thành công"});
}