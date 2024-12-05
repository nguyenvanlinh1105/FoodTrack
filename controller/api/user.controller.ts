import { Request,Response } from 'express';//Nhúng kiểu Request và Response từ module express
import * as allModel from '../../model/index.model';
import { Op, Sequelize } from 'sequelize';

import {hashPassword,verifyPassword} from '../../helper/hashAndVerifyPassword.helper';
import generateNextId from '../../helper/generateNextId.helper';
import { generateRandomNumber } from '../../helper/generateRandom.helper';
import emailQueue,{checkOTPAPI} from '../../helper/emailQueue.helper';
import * as generateString from '../../helper/generateRandom.helper';
import * as isValid from '../../helper/validField.helper';

export const login= async (req:Request,res:Response)=>{
    const {email,matKhau} = req.body;
    try {
        const user = await allModel.NguoiDung.findOne({
            where: {
                email: email,
                trangThai: 'active',
                vaiTro: 'VT002',
                deleted: 0
            },
            include: [
                {
                    model: allModel.ChiTietPhongChat,
                    as: 'RoomDetails', // Alias định nghĩa trong quan hệ
                    attributes: ['idPhongChat'] // Chỉ lấy cột `idPhongChat`
                }
            ],
            raw: true 
        });
        if(!user){
            res.status(404).json({message:"Tài khoản không tồn tại hoặc đã bị khoá"});
        }else{
            const isMatch=verifyPassword(matKhau,user['matKhau']);
            if(isMatch){
                res.status(200).json({
                    message:"Đăng nhập thành công",
                    idUser:user['idNguoiDung'],
                    hoTenNguoiDung:user['hoTen'],
                    email:user['email'],
                    sdt:user['sdt'],
                    gioiTinh:user['gioiTinh'],
                    avatar:user['avatar'],
                    diaChi:user['diaChi'],
                    idPhongChat:user['RoomDetails.idPhongChat'],
                    ngaySinh:user['ngaySinh'],
                })
            }else{
                res.status(404).json({message:"Mật khẩu không chính xác"});
            }
        }
    } catch (error) {
        res.status(500).json({message:'Lỗi '+error.message});
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
        const user= await allModel.NguoiDung.findOne({
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
                idNguoiDung:await generateNextId(allModel.NguoiDung,'ND'),
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
            const createdUser = await allModel.NguoiDung.create(newUser);
            const createdRoom= await allModel.PhongChat.create({
                idPhongChat: await generateNextId(allModel.PhongChat, 'PC'),
                loaiPhong: 'private',
                thoiGianTao: new Date(),
                thoiGianCapNhat: new Date()
            })
            await allModel.ChiTietPhongChat.create({
                idNguoiDung: createdUser['idNguoiDung'],
                idPhongChat: createdRoom['idPhongChat'],
            });
            res.status(200).json({message: 'Đăng ký thành công!'});
        }
    } catch (error) {
        res.status(500).json({message:'Lỗi '+error.message});
    }
}
export const passwordForgot=async(req:Request,res:Response)=>{
    const email=req.body['email'];
    if(!isValid.isValidEmail(email)){
        res.status(404).json({message:'Email không hợp lệ'});
        return;
    }
    const user= await allModel.NguoiDung.findOne({
        where:{
            email:email,
            trangThai:'active',
            vaiTro:'VT002',
            deleted:0
        },
        raw:true
    })
    if(!user){
        res.status(404).json({message:'Tài khoản không tồn tại'});
    }else{
        const otp=generateRandomNumber(6);
        const subject= 'Mã OTP lấy lại mật khẩu';
        const html = `Mã OTP xác thực của bạn là <b style="color: greenyellow;">${otp}</b>. Mã OTP có hiệu lực trong 3 phút. Vui lòng không cung cấp mã OTP cho người khác`;
        
        await emailQueue.add('sendMail',{email,subject,html,otp});
        res.status(200).json({message:'Vui lòng kiểm tra email để lấy mã OTP'});
    }
}
export const otp=async(req:Request,res:Response)=>{
    const {email,otp}=req.body;
    if(!isValid.isValidEmail(email)){
        res.status(404).json({message:'Email không hợp lệ'});
        return;
    }
    checkOTPAPI(email,otp,req,res);
}
export const passwordReset=async(req:Request,res:Response)=>{
    const {email,matKhau}=req.body;
    if(!isValid.isValidEmail(email)){
        res.status(404).json({message:'Email không hợp lệ'});
        return;
    }
    if(!matKhau){
        res.status(404).json({message:'Mật khẩu không hợp lệ'});
        return
    }
    const user= await allModel.NguoiDung.findOne({
        where:{
            email:email,
            trangThai:'active',
            vaiTro:'VT002',
            deleted:0
        },
        raw:true
    });
    if(!user){
        res.status(404).json({message:'Tài khoản không tồn tại'});
    }else{
        await allModel.NguoiDung.update({
            matKhau:hashPassword(matKhau)
        },{
            where:{
                email:email
            }
        });
        res.status(200).json
        ({
            matKhau:matKhau,
            message:'Đổi mật khẩu thành công',
            email:email
        });
    }
}

export const updateInfo=async(req:Request,res:Response)=>{
    const {idUser,hoTenNguoiDung,sdt,email,gioiTinh,ngaySinh,diaChi}=req.body;
    try {
        await allModel.NguoiDung.update(
            {
                hoTen:hoTenNguoiDung,
                sdt:sdt,
                email:email,
                gioiTinh:gioiTinh,
                ngaySinh:ngaySinh,
                diaChi:diaChi,
                ngayCapNhat:new Date()
            },
            {
                where:{
                    idNguoiDung:idUser
                }
            }
        );
        res.status(200).send(
            {
                message:'Update thông tin thành công',
                hoTenNguoiDung:hoTenNguoiDung,
                email:email,
                sdt:sdt,
                gioiTinh:gioiTinh,
                diaChi:diaChi,
                ngaySinh:ngaySinh,
            });
    } catch (error) {
        res.status(500).json({message:'Lỗi '+error.message});
    } 
}
export const updateAvatar=async(req:Request,res:Response)=>{
    const {idNguoiDung,img}=req.body;
    try {
        await allModel.NguoiDung.update(
            {
                avatar:img
            },
            {
                where:{
                    idNguoiDung:idNguoiDung
                }
            }
        );
        res.status(200).send(
            {
                message:'Update ảnh thành công',
                avatar:img
            });
    } catch (error) {
        res.status(500).json({message:'Lỗi '+error.message});
    }
}
export const getInfo=async(req:Request,res:Response)=>{
    const {idNguoiDung}=req.query;
    try {
        const user= await allModel.NguoiDung.findOne({
            where:{
                idNguoiDung:idNguoiDung
            },
            raw:true
        });
        if(user){
            res.status(200).send({
                idUser:user['idNguoiDung'],
                hoTenNguoiDung:user['hoTen'],
                email:user['email'],
                sdt:user['sdt'],
                gioiTinh:user['gioiTinh'],
                avatar:user['avatar'],
                diaChi:user['diaChi'],
                ngaySinh:user['ngaySinh'],
            });
        }else{
            res.status(404).send({message:'Tài khoản không tồn tại'});
        }
    } catch (error) {
        res.status(500).json({message:'Lỗi '+error.message});
    }
}
export const comment = async(req:Request,res:Response)=>{
    const {idNguoiDung,idSanPham,noiDung,idDonHang}=req.body;
    console.log(req.body);
    try {
        await allModel.BinhLuanSanPham.create({
            idNguoiDung:idNguoiDung,
            idSanPham:idSanPham,
            idDonHang:idDonHang,
            noiDung:noiDung,
            ngayBinhLuan:new Date()
        })
        res.status(200).json({message:'Bình luận thành công'});
    } catch (error) {
        res.status(500).json({message:'Lỗi '+error.message});
    }
}

export const listNotification=async(req:Request,res:Response)=>{
    const {idNguoiDung}=req.query;
    try {
        const notifications= await allModel.ThongBao.findAll({
            where:{
                idNguoiDung:idNguoiDung,
                tinhTrang:0
            },
            order:[
                ['ngayThongBao','DESC']
            ],
            raw:true
        });
        if(notifications.length>0){
            const idsToUpdate = notifications.map(notification => notification['idThongBao']);
            await allModel.ThongBao.update(
                { tinhTrang: 1 }, 
                {
                    where: {
                        idThongBao: {
                            [Op.in]: idsToUpdate 
                        }
                    }
                }
            );
        }
        res.status(200).json(notifications);
    } catch (error) {
        res.status(500).json({message:'Lỗi '+error.message});
    }
}
