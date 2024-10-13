import { Request,Response } from 'express';//Nhúng kiểu Request và Response từ module express

export const dashboard = async(req:Request,res:Response)=>{
    res.render('admin/pages/dashboard',{
        title:'Trang chủ'
    })
}