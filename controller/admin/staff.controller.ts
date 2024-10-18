import { Request,Response } from 'express';//Nhúng kiểu Request và Response từ module express
import VaiTro from '../../model/VaiTro.model';
import sequelize from "../../config/database";
import { QueryTypes } from "sequelize";
import nodemailer from 'nodemailer';

import * as isValid from '../../helper/validField.helper';

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
    res.json({
        code:200,
        message:'Tạo tài khoản thành công'
    })
}