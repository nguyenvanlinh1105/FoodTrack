"use strict";
var __createBinding = (this && this.__createBinding) || (Object.create ? (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    var desc = Object.getOwnPropertyDescriptor(m, k);
    if (!desc || ("get" in desc ? !m.__esModule : desc.writable || desc.configurable)) {
      desc = { enumerable: true, get: function() { return m[k]; } };
    }
    Object.defineProperty(o, k2, desc);
}) : (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    o[k2] = m[k];
}));
var __setModuleDefault = (this && this.__setModuleDefault) || (Object.create ? (function(o, v) {
    Object.defineProperty(o, "default", { enumerable: true, value: v });
}) : function(o, v) {
    o["default"] = v;
});
var __importStar = (this && this.__importStar) || function (mod) {
    if (mod && mod.__esModule) return mod;
    var result = {};
    if (mod != null) for (var k in mod) if (k !== "default" && Object.prototype.hasOwnProperty.call(mod, k)) __createBinding(result, mod, k);
    __setModuleDefault(result, mod);
    return result;
};
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.dashboard = void 0;
const allModel = __importStar(require("../../model/index.model"));
const sequelize_1 = require("sequelize");
const sequelize_2 = require("sequelize");
const database_1 = __importDefault(require("../../config/database"));
const dashboard = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const startOfDay = new Date();
    startOfDay.setHours(0, 0, 0, 0);
    const endOfDay = new Date();
    endOfDay.setHours(23, 59, 59, 999);
    const ordersDate = yield allModel.DonHang.findAll({
        where: {
            ngayTao: {
                [sequelize_1.Op.between]: [startOfDay, endOfDay],
            },
            deleted: false,
        },
        attributes: [
            'idDonHang',
            'ngayTao',
            'tinhTrang'
        ],
        raw: true,
    });
    const countOrdersDate = yield allModel.DonHang.findAll({
        where: {
            ngayTao: {
                [sequelize_1.Op.between]: [startOfDay, endOfDay],
            },
            deleted: false,
        },
        attributes: [
            [database_1.default.fn('SUM', database_1.default.literal(`CASE WHEN tinhTrang = 'Đang xử lý' THEN 1 ELSE 0 END`)), 'soLuongDonHangDangXuLy'],
            [database_1.default.fn('SUM', database_1.default.literal(`CASE WHEN tinhTrang = 'Đã xác nhận' THEN 1 ELSE 0 END`)), 'soLuongDonHangDaXacNhan'],
            [database_1.default.fn('SUM', database_1.default.literal(`CASE WHEN tinhTrang = 'Đang giao' THEN 1 ELSE 0 END`)), 'soLuongDonHangDangGiao'],
            [database_1.default.fn('SUM', database_1.default.literal(`CASE WHEN tinhTrang = 'Hoàn thành' THEN 1 ELSE 0 END`)), 'soLuongDonHangHoanThanh'],
            [database_1.default.fn('SUM', database_1.default.literal(`CASE WHEN tinhTrang = 'Đã hủy' THEN 1 ELSE 0 END`)), 'soLuongDonHangDaHuy'],
        ],
        group: ['ngayTao'],
        raw: true,
    });
    const moneyOrdersDate = {
        totalMoneyDangXuLyDate: 0,
        totalMoneyDaXacNhanDate: 0,
        totalMoneyDangGiaoDate: 0,
        totalMoneyHoanThanhDate: 0,
        totalMoneyDaHuyDate: 0,
    };
    let totalMoneyDate = 0;
    const totalOrdersDate = ordersDate.length;
    for (const order of ordersDate) {
        const foods = yield allModel.ChiTietDonHang.findAll({
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
            totalMoneyDate += food['soLuongDat'] * parseFloat(food['Product']['giaTien']);
            switch (order['tinhTrang']) {
                case 'Đang xử lý':
                    moneyOrdersDate['totalMoneyDangXuLyDate'] += food['soLuongDat'] * parseFloat(food['Product']['giaTien']);
                    break;
                case 'Đã xác nhận':
                    moneyOrdersDate['totalMoneyDaXacNhanDate'] += food['soLuongDat'] * parseFloat(food['Product']['giaTien']);
                    break;
                case 'Đang giao':
                    moneyOrdersDate['totalMoneyDangGiaoDate'] += food['soLuongDat'] * parseFloat(food['Product']['giaTien']);
                    break;
                case 'Hoàn thành':
                    moneyOrdersDate['totalMoneyHoanThanhDate'] += food['soLuongDat'] * parseFloat(food['Product']['giaTien']);
                    break;
                case 'Đã hủy':
                    moneyOrdersDate['totalMoneyDaHuyDate'] += food['soLuongDat'] * parseFloat(food['Product']['giaTien']);
                    break;
            }
        }
    }
    const totalMoneyDateStr = (totalMoneyDate) ? new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(totalMoneyDate) : '0₫';
    for (const money in moneyOrdersDate) {
        moneyOrdersDate[money] = new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(moneyOrdersDate[money]);
    }
    const startOfMonth = new Date();
    startOfMonth.setDate(1);
    startOfMonth.setHours(0, 0, 0, 0);
    const endOfMonth = new Date();
    endOfMonth.setMonth(endOfMonth.getMonth() + 1);
    endOfMonth.setDate(0);
    endOfMonth.setHours(23, 59, 59, 999);
    const ordersMonth = yield allModel.DonHang.findAll({
        where: {
            ngayTao: {
                [sequelize_1.Op.between]: [startOfMonth, endOfMonth],
            },
            deleted: false,
        },
        attributes: ['idDonHang', 'ngayTao'],
        raw: true,
    });
    const totalOrdersMonth = ordersMonth.length;
    let totalMoneyMonth = 0;
    for (const order of ordersMonth) {
        const foods = yield allModel.ChiTietDonHang.findAll({
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
    const moneyOrdersMonth = yield database_1.default.query(`
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
    `, { type: sequelize_2.QueryTypes.SELECT });
    for (const money of moneyOrdersMonth) {
        money['tongTien'] = new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(money['tongTien']);
    }
    res.render('admin/pages/dashboard', {
        title: 'Trang chủ',
        totalOrdersDate: totalOrdersDate,
        totalMoneyDate: totalMoneyDateStr,
        totalOrdersMonth: totalOrdersMonth,
        totalMoneyMonth: totalMoneyMonthStr,
        countOrdersDate: countOrdersDate,
        moneyOrdersDate: moneyOrdersDate,
        moneyOrdersMonth: moneyOrdersMonth,
    });
});
exports.dashboard = dashboard;
