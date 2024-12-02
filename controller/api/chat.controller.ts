import { Request,Response } from 'express';//Nhúng kiểu Request và Response từ module express
import TinNhan from '../../model/TinNhan.model';
import NguoiDung from '../../model/NguoiDung.model';

export const chat = async (req: Request, res: Response) => {
    const {idPhongChat} = req.query;
    const listChat=await TinNhan.findAll({
        where:{
            idPhongChat:idPhongChat
        },
        order:[['thoiGianTao','ASC']],
        raw:true,
    });
    const newListChat=[];
    for(const chat of listChat) {
        const data={
            idUser:chat['Gui'],
            noiDungChat:chat['noiDung'],
        }
        newListChat.push(data);
    }
    res.status(200).json(newListChat);
}