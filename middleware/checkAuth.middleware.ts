import { Express,Request,Response,NextFunction } from "express";
import * as allModel from "../model/index.model";//Nhúng tất cả model

const checkAuth = async (req: Request, res: Response, next: NextFunction) => {
    if(!req.cookies.token){
        req.flash('error','Vui lòng đăng nhập');
        res.redirect('/admin/login');
    }else{
        const token=req.cookies.token;
        const userExist= await allModel.NguoiDung.findOne({
            where:{
                token:token,
                trangThai:'active',
                deleted:0
            },
            include: [{
                model: allModel.VaiTro,
                as: 'Role',
                attributes: ['tenVaiTro'] // Lấy tên vai trò
            }],
            attributes: { 
                exclude: ['matKhau'] // Loại bỏ các trường không cần lấy
            },
            raw:true
        })
        if(!userExist){
            req.flash('error','Tài khoản không tồn tại');
            res.redirect('/admin/login');
        }else{
            res.locals.user=userExist;
            next();
        }
    }
}
export default checkAuth;