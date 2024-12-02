import { Request,Response } from 'express';//Nhúng kiểu Request và Response từ module express
import slugify from 'slugify';
import dayjs from 'dayjs';
import DanhMuc from '../../model/DanhMuc.model';
import SanPham from '../../model/SanPham.model';
import * as allModel from '../../model/index.model';


import generateNextId from '../../helper/generateNextId.helper';
import { paginationGeneral } from '../../helper/pagination.helper';

export const index= async(req: Request, res: Response)=>{
    const pagination = await paginationGeneral(req,4,SanPham);
    const foods=await SanPham.findAll({
        where:{
            deleted:0,
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
        donViTinh:((req.body['idDanhMuc']==='DM001')?'suất':'ly'),
        ngayTao:dayjs().format('YYYY-MM-DD HH:mm:ss'),
        slug:slug
    }
    const newFood=await SanPham.create(dataFood);
    res.status(200).json({message:'Thêm món ăn thành công'});
}

export const detail= async(req: Request, res: Response)=>{
    const food = await allModel.SanPham.findOne({
        where: {
            slug: req.params.slug
        },
        include: [{
            model: allModel.DanhMuc,
            as: 'Category', // Sử dụng alias đã định nghĩa
            attributes: ['tenDanhMuc'] // Chỉ lấy cột 'tenDanhMuc'
        }],
        raw: true,
    });
    const categories = await DanhMuc.findAll({
        where:{
            deleted:0
        },
        raw:true
    })
    food['images']=JSON.parse(food['images'])[0];
    food['tenDanhMuc']=food['Category.tenDanhMuc'];
    delete food['Category.tenDanhMuc'];
    food['giaTien']=parseFloat(food['giaTien']).toLocaleString('vi-VN', {
        style: 'currency',
        currency: 'VND',
        minimumFractionDigits: 0,
        maximumFractionDigits: 0
    });
    food['ngayTao']=dayjs(food['ngayTao']).format('YYYY-MM-DD');
    res.render('admin/pages/food/detail',{
        title:'Chi tiết món ăn',
        food:food,
        categories:categories
    })
}

export const editPage= async(req: Request, res: Response)=>{
    (req.session as any).previousPage = req.headers.referer;
    const food = await allModel.SanPham.findOne({
        where: {
            slug: req.params.slug
        },
        include: [{
            model: allModel.DanhMuc,
            as: 'Category', // Sử dụng alias đã định nghĩa
            attributes: ['tenDanhMuc'] // Chỉ lấy cột 'tenDanhMuc'
        }],
        raw: true,
    });
    const categories = await DanhMuc.findAll({
        where:{
            deleted:0
        },
        raw:true
    })
    food['images']=JSON.parse(food['images']);
    food['tenDanhMuc']=food['Category.tenDanhMuc'];
    delete food['Category.tenDanhMuc'];
    food['giaTien']=parseFloat(food['giaTien']);
    food['ngayTao']=dayjs(food['ngayTao']).format('YYYY-MM-DD');
    res.render('admin/pages/food/edit',{
        title:'Chỉnh sửa món ăn',
        food:food,
        categories:categories
    })
}

export const edit = async(req: Request, res: Response)=>{
    if (req.body.images && req.body.images.length > 0) {
        req.body.images=JSON.stringify(req.body.images);
    } else {
        delete req.body.images;  
    }
    await SanPham.update(req.body,{
        where:{
            slug:req.params.slug
        }
    });
    req.flash('success','Cập nhật món ăn thành công');
    const previousPage = (req.session as any).previousPage || '/admin/management/food';  // Default trang nếu không có session
    res.redirect(previousPage);
}

export const deleteFood=async(req: Request, res: Response)=>{
    await SanPham.update({
        deleted:1
    },{
        where:{
            slug:req.params.slug
        }
    });
    res.status(200).json({message:'Xóa món ăn thành công'});
}

export const changeStatus=async(req: Request, res: Response)=>{
    const  {slug,status} = req.params;
    try {
        const [affectedRows]= await allModel.SanPham.update(
            { trangThai: status }, // Dữ liệu cần cập nhật
            { where: { slug: slug, deleted:false } } // Điều kiện cập nhật
        );
        if (affectedRows > 0) {
            req.flash("success","Cập nhật trạng thái thành công");
        }else{
            req.flash("error","Cập nhật trạng thái thất bại");
        }
        res.redirect('back');
    } catch (error) {
        req.flash("error","Cập nhật trạng thái thất bại");
        res.redirect('back');
    }
}