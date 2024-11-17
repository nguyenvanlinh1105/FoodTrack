import { Request,Response } from 'express';//Nhúng kiểu Request và Response từ module express
import SanPham from '../../model/SanPham.model';
import { DOUBLE } from 'sequelize';

export const bargain= async (req: Request, res: Response) => {
    const foods= await SanPham.findAll({
        where:{
            deleted:0,
            trangThai:'active',
        },
        raw:true,
        order:[['giaTien','ASC']],
        limit:20,
        attributes:['idSanPham','slug','tenSanPham','giaTien','images','soLuong']
    })
    if(foods.length==0){
        res.status(404).json({message:"Không có món ăn nào"});
    }else{
        for(const food of foods){
            food['giaTien']=parseFloat(food['giaTien']);
            const images=JSON.parse(food['images']);
            food['images']=images[0];
        }
        res.status(200).json(foods);
    }
}
export const bestseller= async (req: Request, res: Response) => {
    const foods= await SanPham.findAll({
        where:{
            deleted:0,
            trangThai:'active',
        },
        raw:true,
        order:[['soLuong','DESC']],
        limit:20,
        attributes:['idSanPham','slug','tenSanPham','giaTien','images','soLuong']
    })
    if(foods.length==0){
        res.status(404).json({message:"Không có món ăn nào"});
    }else{
        for(const food of foods){
            food['giaTien']=parseFloat(food['giaTien']);
            const images=JSON.parse(food['images']);
            food['images']=images[0];
        }
        res.status(200).json(foods);
    }
}
export const newFood= async (req: Request, res: Response) => {
    const foods= await SanPham.findAll({
        where:{
            deleted:0,
            trangThai:'active',
        },
        raw:true,
        order:[['ngayTao','ASC']],
        limit:20,
        attributes:['idSanPham','slug','tenSanPham','giaTien','images','soLuong']
    })
    if(foods.length==0){
        res.status(404).json({message:"Không có món ăn nào"});
    }else{
        for(const food of foods){
            food['giaTien']=parseFloat(food['giaTien']);
            const images=JSON.parse(food['images']);
            food['images']=images[0];
        }
        res.status(200).json(foods);
    }
}
export const listFood= async (req: Request, res: Response) => {
    const foods= await SanPham.findAll({
        where:{
            deleted:0,
            trangThai:'active',
        },
        raw:true,
        attributes:['idSanPham','slug','tenSanPham','giaTien','images','soLuong']
    })
    if(foods.length==0){
        res.status(404).json({message:"Không có món ăn nào"});
    }else{
        for(const food of foods){
            food['giaTien']=parseFloat(food['giaTien']);
            const images=JSON.parse(food['images']);
            food['images']=images[0];
        }
        res.status(200).json(foods);
    }
}