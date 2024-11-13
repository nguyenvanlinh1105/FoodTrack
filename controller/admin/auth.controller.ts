import { Request,Response } from 'express';//Nhúng kiểu Request và Response từ module express
import axios from 'axios';
import jwt from 'jsonwebtoken';
import * as allMode from "../../model/index.model";//Nhúng tất cả model

//Helper
import {hashPassword, verifyPassword} from '../../helper/hashAndVerifyPassword.helper';
import emailQueue,{checkOTP} from '../../helper/emailQueue.helper';
import { generateRandomNumber } from '../../helper/generateRandom.helper';

export const loginPage= async (req:Request,res:Response)=>{
    res.render('admin/pages/auth/login',{
        title:'Trang đăng nhập'
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

export const passwordForgotPage= async(req:Request,res:Response)=>{
    const site_key=process.env.RECAPTCHA_SITE_KEY;
    res.render('admin/pages/auth/forgot-password',{
        title:"Quên mật khẩu",
        site_key:site_key
    })
}

export const passwordForgot=async(req:Request,res:Response)=>{
    const email=req.body['email'];
    const captcha=req.body['g-recaptcha-response'];
    if (!captcha) {
        req.flash('error','Vui lòng xác nhận captcha');
        return res.redirect('back');
    }
    try {
        const response = await axios.post(
          `https://www.google.com/recaptcha/api/siteverify`,
          null,
          {
            params: {
              secret: process.env.RECAPTCHA_SECRET_KEY, // Secret key của bạn
              response: captcha,
            },
          }
        );
        const data=response.data;
        if (data.success) {
            const user= await allMode.NguoiDung.findOne({
                where:{
                    email:email,
                    trangThai:'active',
                    vaiTro:['VT001','VT003','VT004','VT005'],
                    deleted:0
                },
                raw:true
            })
            if(!user){
                req.flash('error','Tài khoản không tồn tại');
                return res.redirect('back');
            }else{
                const otp=generateRandomNumber(6);
                const subject= 'Mã OTP lấy lại mật khẩu';
                const html = `Mã OTP xác thực của bạn là <b style="color: greenyellow;">${otp}</b>. Mã OTP có hiệu lực trong 3 phút. Vui lòng không cung cấp mã OTP cho người khác`;
                
                await emailQueue.add('sendMail',{email,subject,html,otp});

                req.flash('success','Vui lòng kiểm tra email để lấy mã OTP');
                res.redirect(`/admin/password/otp?email=${encodeURIComponent(email)}`);
            }
          } else {
            req.flash('error','CAPTCHA không hợp lệ!');
            return res.redirect('back');
          }
    }catch(e) {
        req.flash('error','Xác minh captcha không thành công');
        return res.redirect('back');
    }
}

export const passwordOTPPage= async(req:Request,res:Response)=>{
    const email=req.query.email;
    const site_key=process.env.RECAPTCHA_SITE_KEY;
    res.render('admin/pages/auth/otp-password',{
        title:"Nhập mã OTP",
        email:email,
        site_key:site_key
    })
}

export const otp=async(req:Request,res:Response)=>{
    const {email,otp}=req.body;

    const captcha=req.body['g-recaptcha-response'];
    if (!captcha) {
        req.flash('error','Vui lòng xác nhận captcha');
        return res.redirect('back');
    }
    try {
        const response = await axios.post(
          `https://www.google.com/recaptcha/api/siteverify`,
          null,
          {
            params: {
              secret: process.env.RECAPTCHA_SECRET_KEY, // Secret key của bạn
              response: captcha,
            },
          }
        );
        const data=response.data;
        if (data.success) {
            checkOTP(email,otp,req,res);
          } else {
            req.flash('error','CAPTCHA không hợp lệ!');
            return res.redirect('back');
          }
    }catch(e) {
        req.flash('error','Xác minh captcha không thành công');
        return res.redirect('back');
    }
}

export const passwordResetPage= async(req:Request,res:Response)=>{
    const email=req.query.email;
    const site_key=process.env.RECAPTCHA_SITE_KEY;
    res.render('admin/pages/auth/reset-password',{
        title:"Nhập mật khẩu mới",
        email:email,
        site_key:site_key
    })
}

export const passwordReset=async(req:Request,res:Response)=>{
    const {email,newPassword}=req.body;
    console.log(email,newPassword);
    const captcha=req.body['g-recaptcha-response'];
    try {
        const response = await axios.post(
          `https://www.google.com/recaptcha/api/siteverify`,
          null,
          {
            params: {
              secret: process.env.RECAPTCHA_SECRET_KEY, // Secret key của bạn
              response: captcha,
            },
          }
        );
        const data=response.data;
        if (data.success) {
            const user= await allMode.NguoiDung.findOne({
                where:{
                    email:email,
                    trangThai:'active',
                    vaiTro:['VT001','VT003','VT004','VT005'],
                    deleted:0
                },
                raw:true
            });
            if(!user){
                req.flash('error','Tài khoản không tồn tại');
                return res.redirect('back');
            }else{
                await allMode.NguoiDung.update({
                    matKhau:hashPassword(newPassword)
                },{
                    where:{
                        email:email
                    }
                });
                req.flash('success','Cật nhập mật khẩu thành công');
                return res.redirect('/admin/login');
            }
          } else {
            req.flash('error','CAPTCHA không hợp lệ!');
            return res.redirect('back');
          }
    }catch(e) {
        req.flash('error','Xác minh captcha không thành công');
        return res.redirect('back');
    }
}

export const profilePage= async(req:Request,res:Response)=>{
    const user={
        ...res.locals.user,
        tenVaitro:res.locals.user['Role.tenVaiTro']
    }
    const roles=await allMode.VaiTro.findAll({
        where:{
            idVaiTro:['VT001','VT003','VT004','VT005']
        },
        raw:true
    })
    res.render('admin/pages/auth/profile',{
        title:'Trang cá nhân',
        user:user,
        roles:roles
    })
}

export const profileUpdate = async(req:Request,res:Response)=>{
    const { password, 'password-confirm': passwordConfirm,...otherData} = req.body;
    const token=res.locals.user.token;
    if ((password && !passwordConfirm) || (!password && passwordConfirm)) {
        req.flash('error', 'Vui lòng nhập cả mật khẩu và xác nhận mật khẩu.');
        return res.redirect('back');
    }

    if (password && passwordConfirm && password !== passwordConfirm) {
        req.flash('error', 'Mật khẩu và xác nhận mật khẩu không khớp.');
        return res.redirect('back');
    }
    const updatedData = {
        ...otherData,
        ...(password ? { password: hashPassword(password) } : {}) // Chỉ thêm password nếu password tồn tại
    };
    console.log(updatedData);
    await allMode.NguoiDung.update(updatedData, {
        where:{
            token: token,
            trangThai:'active',
            deleted:0,
        }
    });
    req.flash('success','Cập nhật thông tin thành công');
    res.redirect('/admin/dashboard');
}