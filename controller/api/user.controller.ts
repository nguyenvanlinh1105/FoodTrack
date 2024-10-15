import { Request,Response } from 'express';//Nhúng kiểu Request và Response từ module express
import NguoiDung from '../../model/NguoiDung.model';

export const login= async (req:Request,res:Response)=>{
    const { email, password } = req.body;
    console.log(email, password);
    const users=await NguoiDung.findAll({
        raw: true
    });
    if(users.length > 0){
        res.json({
            code:200,
            message:'Login success',
        })
    }else{
        res.json({
            code:401,
            message:'Invalid username or password',
        })
    }
}