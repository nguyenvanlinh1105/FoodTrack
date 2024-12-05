import { Request,Response } from 'express';//Nhúng kiểu Request và Response từ module express
import * as allModel from '../../model/index.model';
import moment from 'moment';

import { paginationGeneral } from '../../helper/pagination.helper';

export const index=async(req:Request,res:Response)=>{
    const pagination=await paginationGeneral(req,4,allModel.BinhLuanSanPham);
    const comments=await allModel.BinhLuanSanPham.findAll({
        include:[
            {
                model:allModel.NguoiDung,
                as:'user',
                attributes:['idNguoiDung','hoTen']
            },
            {
                model:allModel.SanPham,
                as:'product',
                attributes:['idSanPham','tenSanPham']
            }
        ],
        raw:true,
        nest:true,
        limit: pagination.limitItems,
        offset: pagination.skip
    });
    for(const comment of comments) {
        comment['ngayBinhLuan'] = moment(comment['ngayBinhLuan']).format('YYYY-MM-DD HH:mm:ss');
    }
    res.render('admin/pages/comment/index',{
        title:'Quản lý bình luận',
        comments:comments,
        pagination:pagination,
    });
}

export const approveComment=async(req:Request,res:Response)=>{
    const id=parseInt(req.params.id);
    try {
        const isExist=await allModel.BinhLuanSanPham.findOne({
            where:{
                idBinhLuan:id
            }
        })
        if(!isExist){
            res.status(404).json({message:"Không tìm thấy bình luận"});
        }else{
            await allModel.BinhLuanSanPham.update({
                tinhTrang:'Đã chấp thuận'
            },{
                where:{
                    idBinhLuan:id
                }
            });
            res.status(200).json({message:"Duyệt bình luận thành công"});
        }

    } catch (error) {
        res.status(500).json({message:"Lỗi server" + error.message});
    }
}

export const disapprove=async(req:Request,res:Response)=>{
    const id=parseInt(req.params.id);
    try {
        const isExist=await allModel.BinhLuanSanPham.findOne({
            where:{
                idBinhLuan:id
            }
        })
        if(!isExist){
            res.status(404).json({message:"Không tìm thấy bình luận"});
        }else{
            await allModel.BinhLuanSanPham.update({
                tinhTrang:'Đã từ chối'
            },{
                where:{
                    idBinhLuan:id
                }
            });
            res.status(200).json({message:"Từ chối bình luận thành công"});
        }

    } catch (error) {
        res.status(500).json({message:"Lỗi server" + error.message});
    }
}