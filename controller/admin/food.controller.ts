import { Request,Response } from 'express';//Nhúng kiểu Request và Response từ module express
import slugify from 'slugify';
import dayjs from 'dayjs';
import DanhMuc from '../../model/DanhMuc.model';
import SanPham from '../../model/SanPham.model';

import generateNextId from '../../helper/generateNextId.helper';
import { paginationGeneral } from '../../helper/pagination.helper';

export const index= async(req: Request, res: Response)=>{
    const pagination = await paginationGeneral(req,4,SanPham);
    const foods=await SanPham.findAll({
        where:{
            deleted:0,
            trangThai:'active'
        }
        ,raw:true,
        limit:pagination.limitItems,
        offset: pagination.skip
    })
    for(const food of foods){
        food['images']=JSON.parse(food['images'])[0];
        food['giaTien']=parseFloat(food['giaTien']).toLocaleString('vi-VN', {
            style: 'currency',
            currency: 'VND',
            minimumFractionDigits: 0,
            maximumFractionDigits: 0
        });
    }
    res.render('admin/pages/food/index',{
        title:'Quản lý món ăn',
        foods:foods,
        pagination:pagination
    })
}

export const createPage= async(req: Request, res: Response)=>{
    const categories = await DanhMuc.findAll({
        where:{
            deleted:0
        },
        raw:true
    });
    res.render('admin/pages/food/create',{
        title:'Tạo món ăn mới',
        categories:categories
    })
}

export const create = async(req: Request, res: Response)=>{
    const slug=slugify(`${req.body['tenSanPham']}-${Date.now()}`,{
        lower:true,
        locale:'vi',
        remove: /[`~!@#$%^&*()_+\-=[\]{};':"\\|,.<>\/?]+/g,
        replacement: '-'  // Replace all non-alphanumeric characters with the replacement character
    });
    const dataFood={
        idSanPham: await generateNextId(SanPham,'MA'),
        idDanhMuc:req.body['idDanhMuc'],
        tenSanPham:req.body['tenSanPham'],
        giaTien:req.body['giaTien'],
        images:JSON.stringify(req.body.images),
        moTa:req.body['moTa'],
        soLuong:req.body['soLuong'],
        donViTinh:((req.body['idDanhMuc']==='DM001')?'suất':'ly'),
        ngayTao:dayjs().format('YYYY-MM-DD HH:mm:ss'),
        slug:slug
    }
    const newFood=await SanPham.create(dataFood);
    res.json({
        code: 200,
        message: 'Thêm món ăn thành công'
    });
}