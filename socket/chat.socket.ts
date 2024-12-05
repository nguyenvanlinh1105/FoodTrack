import { Request,Response } from 'express';//Nhúng kiểu Request và Response từ module express

import * as saveChatDB from '../helper/saveChatDB.helper';//Nhúng file saveChatDB.helper

const chat =async (req: Request, res: Response) => {
    const { IdRoom } = req.params;
    const user=res.locals.user;
    //Admin
    _io.once('connection', (socket) => {
        socket.on('ADMIN_SEND_MESSAGE', (data) => {
            _io.emit('SEND_TO_CLIENT', data);
            const currentTime = new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
            _io.emit('SERVER_RESEND_MESSAGE_ADMIN', { 
                message: data.message, 
                avatar: user.avatar,
                gender: user.gioiTinh,
                time: currentTime,
            });
            saveChatDB.saveChat(IdRoom, data.message, "", new Date(), user['idNguoiDung']);
        });
        
    });
}
export default chat;