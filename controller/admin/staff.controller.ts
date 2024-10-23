import { Request,Response } from 'express';//Nhúng kiểu Request và Response từ module express
import sequelize from "../../config/database";
import { QueryTypes } from "sequelize";
import NguoiDung from '../../model/NguoiDung.model';
import VaiTro from '../../model/VaiTro.model';

// Helper
import * as isValid from '../../helper/validField.helper';
import {hashPassword,verifyPassword} from '../../helper/hashAndVerifyPassword.helper';
import generateNextId from '../../helper/generateNextId.helper';
import * as generateString from '../../helper/generateRandom.helper';
import * as paginationHelper from '../../helper/pagination.helper';


export const pageStaff = async(req:Request,res:Response)=>{
    //Tính năng phân trang
    const idUserCurrent=res.locals.user['idNguoiDung'];
    const pagination= await paginationHelper.paginationStaff(req,NguoiDung,4,idUserCurrent);
    
    const listStaffs= await sequelize.query
    (   `SELECT NguoiDung.hoTen, NguoiDung.email, NguoiDung.sdt, 
                NguoiDung.ngaySinh, NguoiDung.gioiTinh, NguoiDung.avatar, 
                NguoiDung.trangThai, NguoiDung.token, VaiTro.tenVaiTro as tenVaiTro
        FROM NguoiDung
        JOIN VaiTro ON NguoiDung.vaiTro = VaiTro.idVaiTro
        WHERE NguoiDung.idNguoiDung <> :idUserCurrent
        AND NguoiDung.vaiTro IN ('VT001', 'VT003', 'VT004', 'VT005')
        LIMIT ${pagination.limitItems} OFFSET ${pagination.skip}
        `,
        {
            type:QueryTypes.SELECT,
            replacements:{idUserCurrent}
        }
    )
    console.log(pagination);
    res.render('admin/pages/staff/index',{
        title:'Quản lý nhân viên',
        listStaffs:listStaffs,
        pagination:pagination
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
    const userExist=await NguoiDung.findOne({
        where:{
            email:req.body['email'],
            trangThai:'active',
            vaiTro:['VT001','VT003','VT004','VT005']
        }
    });
    if(userExist){
        res.json({
            code:400,
            message:'Tài khoản đã tồn tại trong hệ thống'
        })
        return;
    }
    const newUser={
        idNguoiDung:await generateNextId(NguoiDung,'ND'),
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
    }
    try {
        const createdUser = await NguoiDung.create(newUser);
        res.json({
            code:200,
            message: 'Người dùng đã được tạo thành công!',
        });
    } catch (error) {
        res.json({
            code:500,
            message: 'Đã xảy ra lỗi khi tạo người dùng.',
            error: error.message
        });
    }
}

export const changeStatus=async (req:Request, res:Response)=>{
    const {token,status} = req.params;
    const [affectedRows]= await NguoiDung.update(
        { trangThai: status }, // Dữ liệu cần cập nhật
        { where: { token: token } } // Điều kiện cập nhật
    );
    if (affectedRows > 0) {
        req.flash("success","Cập nhật trạng thái thành công");
    }else{
        req.flash("error","Cập nhật trạng thái thất bại");
    }
    res.redirect('/admin/management/staff');
}