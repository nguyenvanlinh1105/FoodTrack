import { Request,Response } from 'express';//Nhúng kiểu Request và Response từ module express
import { Op, Sequelize } from 'sequelize';
import SanPham from '../../model/SanPham.model';
import SanPhamYeuThich from '../../model/SanPhamYeuThich.model';
import * as allModel from '../../model/index.model';

import unidecode from 'unidecode';
import he from 'he';

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
            donViTinh:'suất',
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
                    attributes: ['idSanPham','tenSanPham', 'giaTien', 'images', 'moTa'],
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

export const listOrder = async(req: Request, res: Response) => {
    const {idNguoiDung} = req.query;
    try {
        const orders=await allModel.DonHang.findAll({
            where:{
                idNguoiDung:idNguoiDung,
                tinhTrang:'Hoàn thành'
            },
            attributes:['idDonHang','tinhTrang','thoiGianHoanThanh','ngayTao'],
            raw:true
        })
        for(const order of orders){
            const foods= await allModel.ChiTietDonHang.findAll({
                where: {
                    idDonHang: order['idDonHang'],
                },
                attributes:['soLuongDat',
                    [
                        Sequelize.literal(`
                            CASE 
                                WHEN EXISTS (
                                    SELECT 1 
                                    FROM BinhLuanSanPham 
                                    WHERE BinhLuanSanPham.idSanPham = Product.idSanPham 
                                      AND BinhLuanSanPham.idNguoiDung = '${idNguoiDung}'
                                      AND BinhLuanSanPham.idDonHang = '${order['idDonHang']}'
                                ) THEN 1
                                ELSE 0
                            END
                        `),
                        'hasComment',
                    ],
                ],
                include: [
                    {
                        model: allModel.SanPham,
                        as: 'Product',
                        attributes: ['idSanPham','tenSanPham', 'giaTien', 'images', 'moTa','donViTinh'],
                    },
                ],
                raw: true,
                nest: true,
            });
            order['chiTietDonHangs']=[];
            for(const food of foods){
                food['Product']['images'] = JSON.parse(food['Product']['images'])[0];
                food['Product']['moTa']=he.decode(food['Product']['moTa']);
                food['Product']['moTa'] = food['Product']['moTa'].replace(/^<p>/, '').replace(/<\/p>$/, '');
                food['Product']['giaTien']=parseFloat(food['Product']['giaTien']);
                order['chiTietDonHangs'].push(food);
            }
            order['thoiGianHoanThanh'] = order['thoiGianHoanThanh'].toLocaleString();
        }
        res.status(200).json(orders);
    } catch (error) {
        res.status(500).json({message:'Lỗi '+error.message});
    }
}

export const listComment = async(req: Request, res: Response) => {
    const {idSanPham} = req.query;
    try {
        const isExistComment = await allModel.BinhLuanSanPham.findOne({
            where:{
                idSanPham:idSanPham,
                tinhTrang:'Đã chấp thuận'
            },
            raw:true,
        })
        if(!isExistComment){
            res.status(404).json({message:"Chưa có bình luận nào"});
        }else{
            const comments=await allModel.BinhLuanSanPham.findAll({
                where:{
                    idSanPham:idSanPham,
                    tinhTrang:'Đã chấp thuận'
                },
                attributes:['idBinhLuan','noiDung','ngayBinhLuan'],
                include:[
                    {
                        model: allModel.NguoiDung,
                        as: 'user',
                        attributes: [[Sequelize.col('hoTen'), 'hoTenNguoiDung'],'avatar'],
                    },
                ],
                raw:true,
                nest: true,
            })
            for(const comment of comments){
                comment['ngayBinhLuan'] = comment['ngayBinhLuan'].toLocaleString();
            }
            res.status(200).json(comments);
        }
    } catch (error) {
        res.status(500).json({message:'Lỗi '+error.message});
    }
}

export const search= async (req: Request, res: Response) => {
    const query :string = `${req.query.query}`;
    try {
        if (query) {
            let querySlug = `${query.trim().replace(/\s/g, '-')}`;
            querySlug = querySlug.replace(/-+/g, '-');
            querySlug = unidecode(querySlug);  // Đảm bảo không có dấu
        
            const regexQuery = new RegExp(query, 'i');
            const regexQuerySlug = new RegExp(querySlug, 'i');
        
            const foods = await SanPham.findAll({
                where: {
                    [Op.or]: [
                        { tenSanPham: { [Op.like]: `%${query}%` } },  // Dùng LIKE cho MySQL hoặc SQLite
                        { slug: { [Op.like]: `%${querySlug}%` } }     // Dùng LIKE cho MySQL hoặc SQLite
                    ]
                },
                raw: true,
                attributes: ['idSanPham', 'slug', 'tenSanPham', 'giaTien', 'images', 'soLuongDaBan', 'moTa']
            });
        
            if (foods.length == 0) {
                res.status(404).json({ message: "Không có món ăn nào" });
            } else {
                for (const food of foods) {
                    food['giaTien'] = parseFloat(food['giaTien']);
                    const images = JSON.parse(food['images']);
                    food['images'] = images[0];  // Lấy ảnh đầu tiên
                    food['moTa'] = he.decode(food['moTa']);  // Giải mã HTML entities
                    food['moTa'] = food['moTa'].replace(/^<p>/, '').replace(/<\/p>$/, '');  // Loại bỏ thẻ <p> nếu có
                }
                res.status(200).json(foods);
            }
        }
    } catch (error) {
        res.status(500).json({message:'Lỗi '+error.message});
    }
}