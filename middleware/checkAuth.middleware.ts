import { Express,Request,Response,NextFunction } from "express";
import NguoiDung from "../model/NguoiDung.model";
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
            res.locals.user=userExist;
            next();
        }
    }
}
export default checkAuth;