import { Request,Response } from 'express';//Nhúng kiểu Request và Response từ module express
import * as allModel from '../../model/index.model';
import { Op } from 'sequelize';
import { QueryTypes } from "sequelize";
import sequelize from '../../config/database';


export const dashboard = async(req:Request,res:Response)=>{
    const startOfDay = new Date();
    startOfDay.setHours(0, 0, 0, 0);
    const endOfDay = new Date();
    endOfDay.setHours(23, 59, 59, 999); 

    //Theo ngày
    const ordersDate = await allModel.DonHang.findAll({
        where: {
            ngayTao: {
                [Op.between]: [startOfDay, endOfDay], // Lọc các bản ghi trong khoảng thời gian hôm nay
            },
            deleted:false,
        },
        attributes: 
        [
            'idDonHang',
            'ngayTao',
            'tinhTrang'
        ],
        raw: true,
    });
    
    const countOrdersDate= await allModel.DonHang.findAll({
        where: {
            ngayTao: {
                [Op.between]: [startOfDay, endOfDay], // Lọc các bản ghi trong khoảng thời gian hôm nay
            },
            deleted:false,
        },
        attributes:
        [
            [sequelize.fn('SUM',sequelize.literal(`CASE WHEN tinhTrang = 'Đang xử lý' THEN 1 ELSE 0 END`)), 'soLuongDonHangDangXuLy'],
            [sequelize.fn('SUM',sequelize.literal(`CASE WHEN tinhTrang = 'Đã xác nhận' THEN 1 ELSE 0 END`)), 'soLuongDonHangDaXacNhan'],
            [sequelize.fn('SUM',sequelize.literal(`CASE WHEN tinhTrang = 'Đang giao' THEN 1 ELSE 0 END`)), 'soLuongDonHangDangGiao'],
            [sequelize.fn('SUM',sequelize.literal(`CASE WHEN tinhTrang = 'Hoàn thành' THEN 1 ELSE 0 END`)), 'soLuongDonHangHoanThanh'],
            [sequelize.fn('SUM',sequelize.literal(`CASE WHEN tinhTrang = 'Đã hủy' THEN 1 ELSE 0 END`)), 'soLuongDonHangDaHuy'],
        ],
        group:['ngayTao'],
        raw: true,
    })
    const moneyOrdersDate={
        totalMoneyDangXuLyDate:0,
        totalMoneyDaXacNhanDate:0,
        totalMoneyDangGiaoDate:0,
        totalMoneyHoanThanhDate:0,
        totalMoneyDaHuyDate:0,
    }

    let totalMoneyDate=0;
    const totalOrdersDate=ordersDate.length;
    for(const order of ordersDate){
        const foods= await allModel.ChiTietDonHang.findAll({
            where: {
                idDonHang: order['idDonHang'],
            },
            attributes:['soLuongDat'],
            include: [
                {
                    model: allModel.SanPham,
                    as: 'Product',
                    attributes: ['giaTien'],
                },
            ],
            raw: true,
            nest: true,
        });
        order['OrderDetails']=foods;
        for(const food of foods){
            totalMoneyDate+=food['soLuongDat']*parseFloat(food['Product']['giaTien']);
            switch(order['tinhTrang']){
                case 'Đang xử lý':
                    moneyOrdersDate['totalMoneyDangXuLyDate']+=food['soLuongDat']*parseFloat(food['Product']['giaTien']);
                    break;
                case 'Đã xác nhận':
                    moneyOrdersDate['totalMoneyDaXacNhanDate']+=food['soLuongDat']*parseFloat(food['Product']['giaTien']);
                    break;
                case 'Đang giao':
                    moneyOrdersDate['totalMoneyDangGiaoDate']+=food['soLuongDat']*parseFloat(food['Product']['giaTien']);
                    break;
                case 'Hoàn thành':
                    moneyOrdersDate['totalMoneyHoanThanhDate']+=food['soLuongDat']*parseFloat(food['Product']['giaTien']);
                    break;
                case 'Đã hủy':
                    moneyOrdersDate['totalMoneyDaHuyDate']+=food['soLuongDat']*parseFloat(food['Product']['giaTien']);
                    break;
            }
        }
    }
    const totalMoneyDateStr = (totalMoneyDate) ? new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(totalMoneyDate) : '0₫'; 
    for(const money in moneyOrdersDate){
        moneyOrdersDate[money]=new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(moneyOrdersDate[money]);
    }
    //Theo tháng
    const startOfMonth = new Date();
    startOfMonth.setDate(1); // Đặt ngày đầu tháng
    startOfMonth.setHours(0, 0, 0, 0);

    const endOfMonth = new Date();
    endOfMonth.setMonth(endOfMonth.getMonth() + 1); 
    endOfMonth.setDate(0); // Đặt ngày cuối tháng
    endOfMonth.setHours(23, 59, 59, 999); // 

    const ordersMonth = await allModel.DonHang.findAll({
        where: {
            ngayTao: {
                [Op.between]: [startOfMonth, endOfMonth], // Lọc các bản ghi trong tháng này
            },
            deleted: false,
        },
        attributes: ['idDonHang', 'ngayTao'],
        raw: true,
    });
    const totalOrdersMonth = ordersMonth.length;
    let totalMoneyMonth = 0;
    for (const order of ordersMonth) {
        const foods = await allModel.ChiTietDonHang.findAll({
            where: {
                idDonHang: order['idDonHang'],
            },
            attributes: ['soLuongDat'],
            include: [
                {
                    model: allModel.SanPham,
                    as: 'Product',
                    attributes: ['giaTien'],
                },
            ],
            raw: true,
            nest: true,
    });
    order['OrderDetails'] = foods;
    for (const food of foods) {
            totalMoneyMonth += food['soLuongDat'] * parseFloat(food['Product']['giaTien']);
        }
    }
    const totalMoneyMonthStr = (totalMoneyMonth) ? new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(totalMoneyMonth) : '0₫';
    const moneyOrdersMonth=await sequelize.query(`
        SELECT 
            a.ngay, 
            a.soLuongDonHang, 
            COALESCE(b.tongTien, 0) AS tongTien
        FROM 
            (SELECT 
                DAY(ngayTao) AS ngay, 
                COUNT(*) AS soLuongDonHang
            FROM 
                DonHang
            WHERE 
                YEAR(ngayTao) = YEAR(CURRENT_DATE)
                AND MONTH(ngayTao) = MONTH(CURRENT_DATE)
            GROUP BY 
                DATE(ngayTao)) a
        LEFT JOIN 
            (SELECT 
                DAY(DH.ngayTao) AS ngay, 
                SUM(CD.soLuongDat * SP.giaTien) AS tongTien
            FROM 
                DonHang DH
            JOIN 
                ChiTietDonHang CD ON DH.idDonHang = CD.idDonHang
            JOIN 
                SanPham SP ON CD.idSanPham = SP.idSanPham
            WHERE 
                YEAR(DH.ngayTao) = YEAR(CURRENT_DATE)
                AND MONTH(DH.ngayTao) = MONTH(CURRENT_DATE)
            GROUP BY 
                DAY(DH.ngayTao)) b
        ON a.ngay = b.ngay
        ORDER BY 
            a.ngay; 
    `,{type:QueryTypes.SELECT});
    for(const money of moneyOrdersMonth){
        money['tongTien']=new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(money['tongTien']);
    }
    res.render('admin/pages/dashboard',{
        title:'Trang chủ',
        totalOrdersDate:totalOrdersDate,
        totalMoneyDate:totalMoneyDateStr,
        totalOrdersMonth:totalOrdersMonth,
        totalMoneyMonth: totalMoneyMonthStr,
        countOrdersDate:countOrdersDate,
        moneyOrdersDate:moneyOrdersDate,
        moneyOrdersMonth:moneyOrdersMonth,
    })
}