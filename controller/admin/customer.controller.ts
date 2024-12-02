import { Request,Response } from 'express';//Nhúng kiểu Request và Response từ module express
import { Op } from "sequelize";
import sequelize from '../../config/database';
import moment from 'moment';

import * as allModel from "../../model/index.model";//Nhúng tất cả model
import {paginationCustomer} from '../../helper/pagination.helper';

export const pageCustomer = async(req:Request,res:Response)=>{
    const pagination= await paginationCustomer(req,4);
    const customers = await allModel.NguoiDung.findAll({
        attributes: [
            'idNguoiDung',
            'hoTen', 'email', 'sdt', 
            'ngaySinh', 'gioiTinh', 'avatar', 
            'trangThai', 'token'
        ],
        where: {
            vaiTro: 'VT002',
            deleted: false,
        },
        limit: pagination.limitItems,
        offset: pagination.skip,
        raw: true, // If you need to flatten the data
    });
    for (const customer of customers) {
        const phongChat = await allModel.ChiTietPhongChat.findOne({
            where: {
                idNguoiDung: customer['idNguoiDung'],
            },
            attributes: ['idPhongChat'],
            raw: true
        });
        if (phongChat) {
            customer['idPhongChat'] = phongChat['idPhongChat'];
        }
    }
    res.render('admin/pages/customer/index',{
        title:'Quản lý khách hàng',
        customers:customers,
        pagination:pagination
    })
}

export const changeStatus=async (req:Request, res:Response)=>{
    const {token,status} = req.params;
    const [affectedRows]= await allModel.NguoiDung.update(
        { trangThai: status }, // Dữ liệu cần cập nhật
        { where: { token: token } } // Điều kiện cập nhật
    );
    if (affectedRows > 0) {
        req.flash("success","Cập nhật trạng thái thành công");
    }else{
        req.flash("error","Cập nhật trạng thái thất bại");
    }
    res.redirect('back');
}

export const detailCustomerPage = async (req:Request, res:Response) => {
    const token = req.params.token;
    const data = await allModel.NguoiDung.findOne({
        where: {
            token: token,
            trangThai: 'active',
            vaiTro: 'VT002',
            deleted: 0
        },
        attributes: {
            exclude: ['idNguoiDung', 'ngayCapNhat'],
            include: [
                [sequelize.fn('COUNT', sequelize.fn('DISTINCT', sequelize.literal(`CASE WHEN Orders.tinhTrang ='Đang xử lý' or Orders.tinhTrang = 'Đã xác nhận' or Orders.tinhTrang = 'Đang giao' THEN Orders.idDonHang END`))), 'soLuongDonHangDangXuLy'], // Đếm số lượng đơn hàng
                [sequelize.fn('SUM', sequelize.literal(`CASE WHEN Orders.tinhTrang ='Đang xử lý' or Orders.tinhTrang = 'Đã xác nhận' or Orders.tinhTrang = 'Đang giao' THEN \`Orders->OrderDetails\`.\`soLuongDat\` * \`Orders->OrderDetails->Product\`.\`giaTien\` END`)), 'tongTienDonHangDangXuLy'], // Tổng tiền đơn hàng
                [sequelize.fn('COUNT', sequelize.fn('DISTINCT', sequelize.literal(`CASE WHEN Orders.tinhTrang = 'Hoàn thành' THEN Orders.idDonHang END`))), 'soLuongDonHangDaThanhToan'],
                [sequelize.fn('SUM', sequelize.literal(`CASE WHEN Orders.tinhTrang = 'Hoàn thành' THEN \`Orders->OrderDetails\`.\`soLuongDat\` * \`Orders->OrderDetails->Product\`.\`giaTien\` END`)), 'tongTienDonHangDaThanhToan']
            ]
        },
        include: [
            {
                model: allModel.DonHang,
                as: 'Orders',
                attributes: [], // Không cần thuộc tính của DonHang
                include: [
                    {
                        model: allModel.ChiTietDonHang,
                        as: 'OrderDetails',
                        attributes: [], // Không cần thuộc tính của ChiTietDonHang
                        include: [
                            {
                                model: allModel.SanPham,
                                as: 'Product',
                                attributes: [] // Không cần thuộc tính của SanPham
                            }
                        ]
                    }
                ]
            }
        ],
        group: ['NguoiDung.idNguoiDung'], // Nhóm theo idNguoiDung để COUNT hoạt động chính xác
        raw: true
    });
    const customer={
        ...data,
    }
    customer['ngayTao']=moment(data['ngayTao']).format('YYYY-MM-DD');
    customer['soLuongDonHangDangXuLy']=(data['soLuongDonHangDangXuLy']>0)?data['soLuongDonHangDangXuLy']:0; 
    customer['tongTienDonHangDangXuLy'] = (data['tongTienDonHangDangXuLy']) ? new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(data['tongTienDonHangDangXuLy']) : '0₫'; 
    
    customer['soLuongDonHangDaThanhToan']=(data['soLuongDonHangDaThanhToan']>0)?data['soLuongDonHangDaThanhToan']:0; 
    customer['tongTienDonHangDaThanhToan'] = (data['tongTienDonHangDaThanhToan']) ? new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(data['tongTienDonHangDaThanhToan']) : '0₫'; 
    res.render('admin/pages/customer/detail',{
        title:'Chi tiết khách hàng',
        customer:customer,
    })
}

export const deleteCustomer = async(req: Request, res: Response)=>{
    const token = req.params.token;
    await allModel.NguoiDung.update({deleted:1}, {
        where:{
            token: token,
            trangThai:'active',
            deleted:0,
            vaiTro:'VT002'
        }
    });
    res.status(200).json({message:'Xóa khách hàng thành công'});
}