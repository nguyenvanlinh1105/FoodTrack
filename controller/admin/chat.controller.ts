import { Request,Response } from 'express';//Nhúng kiểu Request và Response từ module express
import * as allModel from '../../model/index.model';
import chatSocket from'../../socket/chat.socket';//Nhúng file chat.socket

import TinNhan from '../../model/TinNhan.model';
import NguoiDung from '../../model/NguoiDung.model';

export const index = async (req: Request, res: Response) => {
    const user=res.locals.user;
    const { IdRoom } = req.params;
    chatSocket(req,res);
    const customer = await allModel.ChiTietPhongChat.findOne({
        where: {
            idPhongChat: IdRoom
        },
        include: [{
            model: allModel.NguoiDung,  // Lấy thông tin người dùng liên quan
            as: 'User',
            attributes: {
                exclude: ['ngayTao', 'ngayCapNhat', 'token', 'vaiTro'],  // Loại bỏ các trường không cần thiết
                include: [
                    ['idNguoiDung', 'idNguoiDung'],  // Sử dụng tên trường trực tiếp, không cần thêm tiền tố
                    ['hoTen', 'hoTen'],  // Sử dụng tên trường trực tiếp, không cần thêm tiền tố
                    ['email', 'email'],
                    ['sdt', 'sdt'],
                    ['gioiTinh', 'gioiTinh'],
                    ['avatar', 'avatar'],
                    ['trangThai', 'trangThai'],
                    ['diaChi', 'diaChi']
                ]
            }
        }],
        raw: true
    });
    const newCustomer={
        idNguoiDung:customer['idNguoiDung'],
        hoTen:customer['User.hoTen'],
        email:customer['User.email'],
        sdt:customer['User.sdt'],
        gioiTinh:customer['User.gioiTinh'],
        avatar:customer['User.avatar'],
        trangThai:customer['User.trangThai'],
        diaChi:customer['User.diaChi'],
    }
    const listChat=await TinNhan.findAll({
        where:{
            idPhongChat:IdRoom
        },
        order:[['thoiGianTao','ASC']],
        raw:true,
    });
    for(const chat of listChat) {
        const user=await NguoiDung.findOne({
            where:{
                idNguoiDung:chat['Gui']
            },
            attributes:['hoTen','avatar','gioiTinh'],
            raw:true
        })
        chat['thoiGianTao']= chat['thoiGianTao'].toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
        chat['avatar']=user['avatar'];
        chat['gioiTinh']=user['gioiTinh'];
    }
    res.render('admin/pages/chat/index',{
        title:"Chat",
        IdRoom:IdRoom,
        customer:newCustomer,
        user:user,
        listChat:listChat
    })
}