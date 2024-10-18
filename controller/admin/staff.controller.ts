import { Request,Response } from 'express';//Nhúng kiểu Request và Response từ module express
import sequelize from "../../config/database";
import { QueryTypes } from "sequelize";
import NguoiDung from '../../model/NguoiDung.model';

// Hellper
import * as isValid from '../../helper/validField.helper';
import {hashPassword,verifyPassword} from '../../helper/hashAndVerifyPassword.helper';
import generateNextId from '../../helper/generateNextId.helper';
import * as generateString from '../../helper/generateRandom.helper';

export const pageStaff = async(req:Request,res:Response)=>{
    res.render('admin/pages/staff/index',{
        title:'Quản lý nhân viên'
    })
}
export const createAdminPage=async(req:Request, res:Response)=>{
    const roles= await sequelize.query
    (   `SELECT * 
        FROM VaiTro
        WHERE idVaiTro <> 'VT002'`,
        {
            type:QueryTypes.SELECT
        }
    )
    res.render('admin/pages/staff/create',{
        title:'Tạo tài khoản cho nhân viên',
        roles:roles
    })
}
export const createAdmin=async(req:Request, res:Response)=>{
    if (!isValid.isValidEmail(req.body['email'])) {
        res.json({
            code: 400,
            message: `Email không hợp lệ`,
        });
        return;
    }
    if (!isValid.isValidPhone(req.body['sdt'])) {
        res.json({
            code: 400,
            message: `Số điện thoại không hợp lệ`,
        });
        return;
    }
    const userExist=await NguoiDung.findOne({
        where:{
            email:req.body['email'],
            trangThai:'active',
            vaiTro:['VT001','VT003','VT004','VT005']
        }
    });
    if(userExist){
        res.json({
            code:400,
            message:'Tài khoản đã tồn tại trong hệ thống'
        })
        return;
    }
    const newUser={
        idNguoiDung:await generateNextId(NguoiDung,'ND'),
        hoTen:req.body['hoTen'],
        email:req.body['email'],
        sdt:req.body['sdt'],
        matKhau:hashPassword(req.body['matKhau']),
        gioiTinh:req.body['gioiTinh'],
        trangThai:'active',
        ngayTao:new Date(),
        ngayCapNhat:new Date(),
        vaiTro:req.body['vaiTro'],
        token:generateString.generateRandomString(30), // Tạo token ngẫu nhiên
    }
    try {
        const createdUser = await NguoiDung.create(newUser);
        res.json({
            code:200,
            message: 'Người dùng đã được tạo thành công!',
        });
    } catch (error) {
        res.json({
            code:500,
            message: 'Đã xảy ra lỗi khi tạo người dùng.',
            error: error.message
        });
    }
}