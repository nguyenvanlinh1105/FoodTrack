import { Request,Response } from 'express';//Nhúng kiểu Request và Response từ module express
import NguoiDung from '../../model/NguoiDung.model';

//Helper
import {hashPassword,verifyPassword} from '../../helper/hashAndVerifyPassword.helper';

export const loginPage= async (req:Request,res:Response)=>{
    res.render('admin/pages/auth/login',{
        title:'Đăng nhập'
    })
}

export const login =async (req:Request,res:Response)=>{
    const {email,password} = req.body;
    try {
        const user= await NguoiDung.findOne({
            where:{
                email:email,
                trangThai:'active',
                vaiTro:['VT001','VT003','VT004','VT005']
            },
            raw:true
        });
        if(!user){
            req.flash('error','Tài khoản không tồn tại');
            res.redirect('back');
        }else{
            const isMatch=verifyPassword(password,user['matKhau']);
            if(isMatch){
                res.cookie('token',user['token'],{maxAge:1000*60*60*1});
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