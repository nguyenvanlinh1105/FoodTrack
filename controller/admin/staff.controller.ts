import { Request,Response } from 'express';//Nhúng kiểu Request và Response từ module express
import { Op } from "sequelize";
import moment from 'moment';
import * as allModel from "../../model/index.model";//Nhúng tất cả model

// Helper
import * as isValid from '../../helper/validField.helper';
import {hashPassword} from '../../helper/hashAndVerifyPassword.helper';
import generateNextId from '../../helper/generateNextId.helper';
import * as generateString from '../../helper/generateRandom.helper';
import {paginationStaff} from '../../helper/pagination.helper';


export const pageStaff = async(req:Request,res:Response)=>{
    //Tính năng phân trang
    const idUserCurrent=res.locals.user['idNguoiDung'];
    const pagination= await paginationStaff(req,4,idUserCurrent);
    const listStaffs= await allModel.NguoiDung.findAll({
        attributes:[
            'hoTen', 'email', 'sdt', 
            'ngaySinh', 'gioiTinh', 'avatar', 
            'trangThai', 'token'
        ],
        where:{
            idNguoiDung:{[Op.ne]:idUserCurrent},
            vaiTro:{[Op.in]:['VT001', 'VT003', 'VT004', 'VT005']},
            deleted:0
        },
        include: [{
            model: allModel.VaiTro,
            as: 'Role',
            attributes: ['tenVaiTro'] // Lấy tên vai trò
        }],
        limit: pagination.limitItems,
        offset: pagination.skip,
        raw:true
    })
    for(const staff of listStaffs){
        staff['tenVaiTro']=staff['Role.tenVaiTro'];
    }
    res.render('admin/pages/staff/index',{
        title:'Quản lý nhân viên',
        listStaffs:listStaffs,
        pagination:pagination
    })
}
export const createAdminPage=async(req:Request, res:Response)=>{
    const roles= await allModel.VaiTro.findAll({
        where:{
            idVaiTro:{[Op.ne]:'VT002'}
        }
    });
    res.render('admin/pages/staff/create',{
        title:'Tạo tài khoản cho nhân viên',
        roles:roles
    })
}
export const createAdmin=async(req:Request, res:Response)=>{
    if (!isValid.isValidEmail(req.body['email'])) {
        res.status(404).json({message:'Email không hợp lệ'});
        return;
    }
    if (!isValid.isValidPhone(req.body['sdt'])) {
        res.status(404).json({message:'Số điện thoại không hợp lệ'});
        return;
    }
    
    const userExist=await allModel.NguoiDung.findOne({
        where:{
            email:req.body['email'],
            trangThai:'active',
            vaiTro:['VT001','VT003','VT004','VT005']
        },
        raw:true
    });
    if(userExist){
        res.status(404).json({message:'Tài khoản đã tồn tại trong hệ thống'});
        return;
    }
    const newUser={
        idNguoiDung:await generateNextId(allModel.NguoiDung,'ND'),
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
        tichDiem:0,
    }
    try {
        const createdUser = await allModel.NguoiDung.create(newUser);
        res.status(200).json({message: 'Tạo tài khoản thành công!'});
    } catch (error) {
        res.status(500).json({message: 'Đã xảy ra lỗi khi tạo tài khoản.'});
    }
}

export const changeStatus=async (req:Request, res:Response)=>{
    const {token,status} = req.params;
    const [affectedRows]= await allModel.NguoiDung.update(
        { trangThai: status }, // Dữ liệu cần cập nhật
        { where: { token: token } } // Điều kiện cập nhật
    );
    if (affectedRows > 0) {
        req.flash("success","Cập nhật trạng thái thành công");
    }else{
        req.flash("error","Cập nhật trạng thái thất bại");
    }
    res.redirect('back');
}

export const detailStaffPage = async (req:Request, res:Response) => {
    (req.session as any).previousPage = req.headers.referer;
    const token = req.params.token;
    const data= await allModel.NguoiDung.findOne({
        where:{
            token:token,
            trangThai:'active',
            vaiTro:['VT001','VT003','VT004','VT005'],
            deleted:0
        },
        include: [
            {
                model: allModel.VaiTro,
                as: 'Role',
                attributes: ['tenVaiTro'] // Lấy thuộc tính tenVaiTro
            }
        ],
        attributes: {
            exclude: ['idNguoiDung','ngayCapNhat']
        },
        raw:true
    });
    const staff={
        ...data,
        tenVaiTro:data['Role.tenVaiTro']
    }
    staff['ngayTao']=moment(data['ngayTao']).format('YYYY-MM-DD');
    const roles=await allModel.VaiTro.findAll({
        where:{
            idVaiTro:['VT003','VT004','VT005']
        },
        raw:true
    })
    res.render('admin/pages/staff/detail',{
        title:'Chi tiết nhân viên',
        staff:staff,
        roles:roles
    })
}

export const updateStaff = async (req:Request,res:Response) => {
    const data=req.body;
    const token = req.params.token;
    const updatedData={
        vaiTro:data['vaiTro'],
    }
    await allModel.NguoiDung.update(updatedData, {
        where:{
            token: token,
            trangThai:'active',
            deleted:0,
        }
    });
    req.flash('success','Cập nhật thông tin nhân viên thành công');
    const previousPage = (req.session as any).previousPage || '/admin/management/staff';  // Default trang nếu không có session
    res.redirect(previousPage);
}

export const deleteStaff = async (req:Request,res:Response) => {
    const token = req.params.token;
    await allModel.NguoiDung.update({deleted:1}, {
        where:{
            token: token,
            trangThai:'active',
            deleted:0,
            vaiTro:{[Op.ne]:'VT002'}
        }
    });
    res.status(200).json({message:'Xóa nhân viên thành công'});
}