import { Express,Request,Response,NextFunction } from "express";
import * as allModel from "../model/index.model";//Nhúng tất cả model

const checkMessagesUnread = async (req: Request, res: Response, next: NextFunction) => {
    const listUnreadMessages = await allModel.TinNhan.findAll({
        where: {
            tinhTrang: 0
        },
        raw: true
    })
    for(const chat of listUnreadMessages){
        const user= await allModel.ChiTietPhongChat.findOne({
            where:{
                idPhongChat:chat['idPhongChat'],
            },
            include:[
                {
                    model: allModel.NguoiDung,  // Lấy thông tin người dùng liên quan
                    as: 'User',
                    attributes: ['hoTen','avatar','gioiTinh'],
                },
            ],
            raw:true
        })
        chat['tenNguoiDung'] = user['User.hoTen'];
        chat['avatar'] = user['User.avatar'];
    }
    res.locals.listUnreadMessages = listUnreadMessages;
    res.locals.countUnreadMessages = listUnreadMessages.length;
    next();
}

export default checkMessagesUnread;