import { Request,Response } from 'express';//Nhúng kiểu Request và Response từ module express
import * as allModel from '../../model/index.model';
import { Op } from 'sequelize';


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
        attributes: ['idDonHang','ngayTao'],
        raw: true,
    });
    const totalOrdersDate=ordersDate.length;
    let totalMoneyDate=0;
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
        }
    }
    console.log(totalMoneyDate);
    const totalMoneyDateStr = (totalMoneyDate) ? new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(totalMoneyDate) : '0₫'; 

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
 
    res.render('admin/pages/dashboard',{
        title:'Trang chủ',
        totalOrdersDate:totalOrdersDate,
        totalMoneyDate:totalMoneyDateStr,
        totalOrdersMonth:totalOrdersMonth,
        totalMoneyMonth: totalMoneyMonthStr,
    })
}