import { Request,Response } from 'express';//Nhúng kiểu Request và Response từ module express
import VaiTro from '../../model/VaiTro.model';
import sequelize from "../../config/database";
import { QueryTypes } from "sequelize";

export const pageStaff = async(req:Request,res:Response)=>{
    
    res.render('admin/pages/staff/index',{
        title:'Quản lý nhân viên'
    })
}
export const createPage=async(req:Request, res:Response)=>{
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