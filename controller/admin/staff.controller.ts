import { Request,Response } from 'express';//Nhúng kiểu Request và Response từ module express

export const pageStaff = async(req:Request,res:Response)=>{
    res.render('admin/pages/staff',{
        title:'Quản lý nhân viên'
    })
}