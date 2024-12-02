import { Request,Response } from 'express';//Nhúng kiểu Request và Response từ module express
import SanPham from '../../model/SanPham.model';
import { DOUBLE } from 'sequelize';
import he from 'he';
import SanPhamYeuThich from '../../model/SanPhamYeuThich.model';

export const bargain= async (req: Request, res: Response) => {
    const foods= await SanPham.findAll({
        where:{
            deleted:0,
            trangThai:'active',
        },
        raw:true,
        order:[['giaTien','ASC']],
        limit:20,
        attributes:['idSanPham','slug','tenSanPham','giaTien','images','soLuongDaBan','moTa']
    })
    if(foods.length==0){
        res.status(404).json({message:"Không có món ăn nào"});
    }else{
        for(const food of foods){
            food['giaTien']=parseFloat(food['giaTien']);
            const images=JSON.parse(food['images']);
            food['images']=images[0];
            food['moTa']=he.decode(food['moTa']);
            food['moTa'] = food['moTa'].replace(/^<p>/, '').replace(/<\/p>$/, '');
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
        order:[['soLuongDaBan','DESC']],
        limit:20,
        attributes:['idSanPham','slug','tenSanPham','giaTien','images','soLuongDaBan','moTa']
    })
    if(foods.length==0){
        res.status(404).json({message:"Không có món ăn nào"});
    }else{
        for(const food of foods){
            food['giaTien']=parseFloat(food['giaTien']);
            const images=JSON.parse(food['images']);
            food['images']=images[0];
            food['moTa']=he.decode(food['moTa']);
            food['moTa'] = food['moTa'].replace(/^<p>/, '').replace(/<\/p>$/, '');
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
        order:[['ngayTao','DESC']],
        limit:20,
        attributes:['idSanPham','slug','tenSanPham','giaTien','images','soLuongDaBan','moTa']
    })
    if(foods.length==0){
        res.status(404).json({message:"Không có món ăn nào"});
    }else{
        for(const food of foods){
            food['giaTien']=parseFloat(food['giaTien']);
            const images=JSON.parse(food['images']);
            food['images']=images[0];
            food['moTa']=he.decode(food['moTa']);
            food['moTa'] = food['moTa'].replace(/^<p>/, '').replace(/<\/p>$/, '');
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
        attributes:['idSanPham','slug','tenSanPham','giaTien','images','soLuongDaBan','moTa']
    })
    if(foods.length==0){
        res.status(404).json({message:"Không có món ăn nào"});
    }else{
        for(const food of foods){
            food['giaTien']=parseFloat(food['giaTien']);
            const images=JSON.parse(food['images']);
            food['images']=images[0];
            food['moTa']=he.decode(food['moTa']);
            food['moTa'] = food['moTa'].replace(/^<p>/, '').replace(/<\/p>$/, '');
        }
        res.status(200).json(foods);
    }
}
export const detailFood =  async (req: Request, res: Response) => {
    const {idNguoiDung,idSanPham} = req.query;
    try {
        if (!idNguoiDung || !idSanPham) {
            res.status(400).json({ message: "Thiếu idNguoiDung hoặc idSanPham" });
        }
        const existFood= await SanPhamYeuThich.findOne({
            where: {
                idNguoiDung: idNguoiDung,
                idSanPham: idSanPham,
            },
            raw: true,
        })
        if (existFood) {
            res.status(200).json({isLove: true });
        } else {
            res.status(200).json({isLove: false });
        }
    } catch (error) {
        res.status(500).json({message:'Lỗi '+error.message});
    }
}
export const loveFood= async (req: Request, res: Response) => {
    const {idNguoiDung,idSanPham}=req.body;
    try {
        await SanPhamYeuThich.create({
            idNguoiDung:idNguoiDung,
            idSanPham:idSanPham,
            ngayCapNhat:new Date(),
        })
        res.status(200).json({message:"Thành công"});
    } catch (error) {
        res.status(500).json({message:'Lỗi '+error.message});
    }
}
export const unloveFood= async (req: Request, res: Response) => {
    const {idNguoiDung,idSanPham}=req.body;
    try {
        await SanPhamYeuThich.destroy({
            where:{
                idNguoiDung:idNguoiDung,
                idSanPham:idSanPham,
            }
        })
        res.status(200).json({message:"Thành công"});
    } catch (error) {
        res.status(500).json({message:'Lỗi '+error.message});
    }
}
export const listLoveFood= async (req: Request, res: Response) => {
    const {idNguoiDung} = req.query;
    try{
        const listLoveFood= await SanPhamYeuThich.findAll({
            where:{
                idNguoiDung:idNguoiDung,
            },
            attributes:['idSanPham'],
            include:[
                {
                    model: SanPham,
                    as: 'Product', 
                    attributes: ['tenSanPham', 'giaTien', 'images', 'moTa'],
                },
            ],
            raw:true,
        })
        if(listLoveFood.length==0){
            res.status(404).json({message:"Không có món ăn nào"});
        }else{
            for(const food of listLoveFood){
                food['tenSanPham']=food['Product.tenSanPham'];
                food['giaTien']=parseFloat(food['Product.giaTien']);
                const images=JSON.parse(food['Product.images']);
                food['images']=images[0];
                food['moTa']=he.decode(food['Product.moTa']);
                food['moTa'] = food['moTa'].replace(/^<p>/, '').replace(/<\/p>$/, '');
                delete food['Product.giaTien'];
                delete food['Product.images'];
                delete food['Product.moTa'];
                delete food['Product.tenSanPham'];
            }
            res.status(200).json(listLoveFood);
        }
    }catch(error){
        res.status(500).json({message:'Lỗi '+error.message});
    }
}

export const listDrink= async(req: Request, res: Response) => {
    const foods= await SanPham.findAll({
        where:{
            deleted:0,
            trangThai:'active',
            donViTinh:'ly',
        },
        raw:true,
        attributes:['idSanPham','slug','tenSanPham','giaTien','images','soLuongDaBan','moTa']
    })
    if(foods.length==0){
        res.status(404).json({message:"Không có thức uống nào"});
    }else{
        for(const food of foods){
            food['giaTien']=parseFloat(food['giaTien']);
            const images=JSON.parse(food['images']);
            food['images']=images[0];
            food['moTa']=he.decode(food['moTa']);
            food['moTa'] = food['moTa'].replace(/^<p>/, '').replace(/<\/p>$/, '');
        }
        res.status(200).json(foods);
    }
}