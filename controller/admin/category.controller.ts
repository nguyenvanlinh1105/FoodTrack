import { Request,Response } from 'express';//Nhúng kiểu Request và Response từ module express
import DanhMuc from '../../model/DanhMuc.model';
import slugify from 'slugify';

import generateNextId from '../../helper/generateNextId.helper';
import { paginationGeneral } from '../../helper/pagination.helper';

export const index= async(req: Request, res: Response)=>{
    const pagination=await paginationGeneral(req,4,DanhMuc);
    const categories=await DanhMuc.findAll({
        where:{
            deleted:0
        },
        raw:true,
        limit: pagination.limitItems,
        offset: pagination.skip
    })
    res.render('admin/pages/category/index',{
        title:'Quản lý danh mục',
        categories:categories,
        pagination:pagination
    })
}
export const createPage= async(req: Request, res: Response)=>{
    res.render('admin/pages/category/create',{
        title:'Tạo danh mục',
    })
}
export const create=async(req: Request, res: Response)=>{
    const {tenDanhMuc,moTa}=req.body;
    const slug=slugify(`${tenDanhMuc}-${Date.now()}`,{
        lower:true,
        locale:'vi',
        remove: /[`~!@#$%^&*()_+\-=[\]{};':"\\|,.<>\/?]+/g,
        replacement: '-'  // Replace all non-alphanumeric characters with the replacement character
    });
    console.log(slug);
    const dataNewCategory={
        idDanhMuc: await generateNextId(DanhMuc,'DM'),
        tenDanhMuc:tenDanhMuc,
        moTa:moTa,
        ngayTao: new Date().toISOString(),
        slug: slug
    }
    try {
        const newCategory= await DanhMuc.create(dataNewCategory);
        res.json({
            code:200,
            message: 'Danh mục đã được tạo thành công!',
        });
    } catch (error) {
        res.json({
            code:500,
            message: 'Đã xảy ra lỗi khi tạo danh mục.',
            error: error.message
        });
    }
}