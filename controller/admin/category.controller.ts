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
    const dataNewCategory={
        idDanhMuc: await generateNextId(DanhMuc,'DM'),
        tenDanhMuc:tenDanhMuc,
        moTa:moTa,
        ngayTao: new Date().toISOString(),
        slug: slug
    }
    try {
        const newCategory= await DanhMuc.create(dataNewCategory);
        res.status(200).json({message:'Danh mục đã được tạo thành công!'});
    } catch (error) {
        res.status(500).json('Đã xảy ra lỗi khi tạo danh mục.');
    }
}

export const detailPage=async(req: Request, res: Response)=>{
    const slug=req.params.slug;
    const category=await DanhMuc.findOne({
        where:{
            slug:slug,
            deleted:0,
        },
        raw:true
    })
    res.render('admin/pages/category/detail',{
        title:'Chi tiết danh mục',
        category:category
    })
}

export const editPage = async(req: Request, res: Response)=>{
    (req.session as any).previousPage = req.headers.referer;
    const slug=req.params.slug;
    const category=await DanhMuc.findOne({
        where:{
            slug:slug,
            deleted:0,
        },
        raw:true
    })
    res.render('admin/pages/category/edit',{
        title:'Chỉnh sửa danh mục',
        category:category
    })
}
export const edit=async(req: Request, res: Response)=>{
    const slug=req.params.slug;
    const {tenDanhMuc,moTa,ngayTao}=req.body;
    await DanhMuc.update({
        tenDanhMuc:tenDanhMuc,
        moTa:moTa,
        ngayTao:ngayTao
    },{
        where:{
            slug:slug
        }
    });
    req.flash('success','Danh mục đã được cập nhật thành công!');
    const previousPage = (req.session as any).previousPage || '/admin/management/category';  // Default trang nếu không có session
    res.redirect(previousPage);
}

export const deleteCategory=async(req: Request, res: Response)=>{
    const slug=req.params.slug;
    await DanhMuc.update({
        deleted:1
    },{
        where:{
            slug:slug
        }
    });
    res.status(200).json({message:'Xóa danh mục thành công'});
}