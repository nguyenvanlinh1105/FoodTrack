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
            },
            raw:true
        });
        if(!user){
            res.redirect('back');
        }else{
            const newPassword= hashPassword(password);
            const isMatch=verifyPassword(password,newPassword);
            if(isMatch){
                res.redirect('/admin/dashboard');
            }else{
                res.redirect('back');
            }
        }
    } catch (error) {
        res.redirect('back');
    }
}