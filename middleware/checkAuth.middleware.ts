import { Express,Request,Response,NextFunction } from "express";
import NguoiDung from "../model/NguoiDung.model";
import VaiTroNguoiDung from "../model/VaiTroNguoiDung.model";
const checkAuth = async (req: Request, res: Response, next: NextFunction) => {
    if(!req.cookies.token){
        req.flash('error','Vui lòng đăng nhập');
        res.redirect('/admin/login');
    }else{
        const token=req.cookies.token;
        const userExist= await NguoiDung.findOne({
            where:{
                token:token,
                trangThai:'active'
            },
            attributes: { 
                exclude: ['matKhau', 'token'] // Loại bỏ các trường không cần lấy
            },
            raw:true
        })
        if(!userExist){
            req.flash('error','Tài khoản không tồn tại');
            res.redirect('/admin/login');
        }else{
            const role= await VaiTroNguoiDung.findOne({
                where:{
                    idNguoiDung:userExist['idNguoiDung'],
                    idVaiTro: ['VT001', 'VT003', 'VT004', 'VT005']
                },
                raw:true
            })
            if(!role){
                req.flash('error','Bạn không có quyền truy cập vào trang này');
                res.redirect('/admin');
            }
            else{
                res.locals.user=userExist;
                next();
            }
        }
    }
}
export default checkAuth;