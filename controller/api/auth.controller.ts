import { Request,Response } from 'express';//Nhúng kiểu Request và Response từ module express
import jwt from 'jsonwebtoken';

import * as allMode from "../../model/index.model";//Nhúng tất cả model

import { generateAccessToken,generateRefreshToken } from '../../helper/generateToken.helper';
import {hashPassword,verifyPassword} from '../../helper/hashAndVerifyPassword.helper';

let refreshTokens: string[] = [];
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
                let result = {
                    id:`${user['idNguoiDung']}`,
                    role: `${user['Role.tenVaiTro']}`,
                };


                const accessToken = generateAccessToken(result);

                const refreshToken= generateRefreshToken(result);
                
                // Lưu token vào cookie
                res.cookie("refreshToken", refreshToken, {
                    httpOnly: true,  // Ngăn JS client đọc cookie
                    secure: false,   // True trong production khi dùng HTTPS
                    path: "/",
                    sameSite: "strict", // Chống CSRF
                });
                refreshTokens.push(refreshToken);
                res.status(200).json({
                    message:"Đăng nhập thành công",
                    id:user['idNguoiDung'],
                    hoTen:user['hoTen'],
                    email:user['email'],
                    sdt:user['sdt'],
                    gioiTinh:user['gioiTinh'],
                    avatar:user['avatar'],
                    accessToken:accessToken,
                })
            }else{
                res.status(404).json({message:"Mật khẩu không chính xác"});
            }
        }
    } catch (error) {
        res.status(500).json({message:"Lỗi server"});
    }
}
export const requestRefreshToken = (req:Request, res:Response) => {
    const refreshToken = req.cookies.refreshToken;
    if(!refreshToken){
        res.status(403).json({message:"You're not authenticated"});
        return;
    }
    if(!refreshTokens.includes(refreshToken)){
        res.status(403).json({message:"Refresh token is not valid"});
        return;
    }
    else{
        
        jwt.verify(refreshToken,process.env.REFRESH_TOKEN_SECRET,(err,user)=>{
            if(err){
                console.log(err);
                res.status(403).json({message:'Token invalid'});
            }else{
                refreshTokens=refreshTokens.filter(token=>token!==refreshToken);// Lọc ra những token khác với token cần xóa để tạo mảng mới
                const newAccessToken=generateAccessToken(user);
                const newRefreshToken=generateRefreshToken(user);
                refreshTokens.push(newRefreshToken);
                res.cookie("refreshToken", newRefreshToken, {
                    httpOnly: true,  // Ngăn JS client đọc cookie
                    secure: false,   // True trong production khi dùng HTTPS
                    path: "/",
                    sameSite: "strict", // Chống CSRF
                });
                res.status(200).json({accessToken:newAccessToken});
            }
        });
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