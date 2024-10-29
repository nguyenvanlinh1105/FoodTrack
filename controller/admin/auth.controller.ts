import { Request,Response } from 'express';//Nhúng kiểu Request và Response từ module express
import jwt from 'jsonwebtoken';
import * as allMode from "../../model/index.model";//Nhúng tất cả model

//Helper
import {verifyPassword} from '../../helper/hashAndVerifyPassword.helper';

export const loginPage= async (req:Request,res:Response)=>{
    res.render('admin/pages/auth/login',{
        title:'Đăng nhập'
    })
}

export const login =async (req:Request,res:Response)=>{
    const {email,password} = req.body;
    try {
        const user= await allMode.NguoiDung.findOne({
            where:{
                email:email,
                trangThai:'active',
                vaiTro:['VT001','VT003','VT004','VT005'],
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
            req.flash('error','Tài khoản không tồn tại');
            res.redirect('back');
        }else{
            const isMatch=verifyPassword(password,user['matKhau']);
            if(isMatch){
                const result = {
                    ...user,
                    tenVaiTro: user['Role.tenVaiTro'],
                };
                res.cookie('token',result['token'],{maxAge:1000*60*60*1});
                req.flash('success','Đăng nhập thành công');
                res.redirect('/admin/dashboard');
                
            }else{
                req.flash('error','Mật khẩu không đúng');
                res.redirect('back');
            }
        }
    } catch (error) {
        req.flash('error','Lỗi');
        res.redirect('back');
    }
}
export const logout = async (req: Request,res: Response) =>{
    res.clearCookie('token');
    req.flash('success','Đăng xuất thành công');
    res.redirect('/admin/login');
}