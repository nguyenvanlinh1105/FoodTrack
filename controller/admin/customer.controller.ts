import { Request,Response } from 'express';//Nhúng kiểu Request và Response từ module express
import { Op } from "sequelize";

import NguoiDung from '../../model/NguoiDung.model';
import {paginationCustomer} from '../../helper/pagination.helper';

export const pageCustomer = async(req:Request,res:Response)=>{
    const pagination= await paginationCustomer(req,4);
    const customers= await NguoiDung.findAll({
        attributes:[
            'hoTen', 'email', 'sdt', 
            'ngaySinh', 'gioiTinh', 'avatar', 
            'trangThai', 'token'
        ],
        where:{
            vaiTro:'VT002',
            deleted:0,
        },
        limit: pagination.limitItems,
        offset: pagination.skip,
        raw:true
    })
    res.render('admin/pages/customer/index',{
        title:'Quản lý khách hàng',
        customers:customers,
        pagination:pagination
    })
}