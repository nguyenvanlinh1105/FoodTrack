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
Object.defineProperty(exports, "__esModule", { value: true });
exports.notificationOrder = exports.deliverOrder = exports.detailOrder = exports.index = void 0;
const allModel = __importStar(require("../../model/index.model"));
const pagination_helper_1 = require("../../helper/pagination.helper");
const updateDoneOrder_helper_1 = require("../../helper/updateDoneOrder.helper");
const index = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    _io.once('connection', (socket) => {
        socket.on('ADMIN_SEND_NOTIFICATION', (data) => {
            const currentTime = new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
            _io.emit('SEND_NOTIFICATION_CLIENT', data);
        });
    });
    const pagination = yield (0, pagination_helper_1.paginationGeneral)(req, 4, allModel.DonHang);
    const orders = yield allModel.DonHang.findAll({
        where: {
            deleted: 0
        },
        include: [
            {
                model: allModel.NguoiDung,
                as: 'User',
                attributes: ['hoTen']
            }
        ],
        raw: true,
        limit: pagination.limitItems,
        offset: pagination.skip
    });
    for (const order of orders) {
        order['tenNguoiDung'] = order['User.hoTen'];
        delete order['User.hoTen'];
    }
    res.render('admin/pages/order/index', {
        title: 'Quản lý đơn hàng',
        orders: orders,
        pagination: pagination
    });
});
exports.index = index;
const detailOrder = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const { idDonHang } = req.params;
    let sum = 0;
    try {
        const order = yield allModel.DonHang.findOne({
            where: {
                idDonHang: idDonHang,
            },
            include: [
                {
                    model: allModel.NguoiDung,
                    as: 'User',
                    attributes: ['hoTen', 'sdt', 'avatar', 'gioiTinh'],
                },
            ],
            raw: true,
            nest: true,
        });
        const foods = yield allModel.ChiTietDonHang.findAll({
            where: {
                idDonHang: idDonHang
            },
            attributes: ['soLuongDat'],
            include: [
                {
                    model: allModel.SanPham,
                    as: 'Product',
                    attributes: ['tenSanPham', 'giaTien', 'images', 'donViTinh'],
                },
            ],
            raw: true,
            nest: true,
        });
        for (const food of foods) {
            food['Product']['giaTien'] = parseFloat(food['Product']['giaTien']).toLocaleString('vi-VN', {
                style: 'currency',
                currency: 'VND',
                minimumFractionDigits: 0,
                maximumFractionDigits: 0
            });
            sum += (parseFloat(food['Product']['giaTien']) * parseFloat(food['soLuongDat']));
            food['Product']['images'] = JSON.parse(food['Product']['images'])[0];
        }
        const total = sum + 15;
        res.render('admin/pages/order/detail', {
            title: 'Chi tiết đơn hàng',
            order: order,
            foods: foods,
            sum: sum + '.000 đ',
            total: total + '.000 đ'
        });
    }
    catch (error) {
        req.flash('error', error.message);
        res.redirect('/admin/management/order');
    }
});
exports.detailOrder = detailOrder;
const deliverOrder = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const { idDonHang } = req.params;
    try {
        const order = yield allModel.DonHang.findOne({
            where: {
                idDonHang: idDonHang,
                tinhTrang: "Đã xác nhận"
            },
        });
        if (!order) {
            req.flash('error', 'Không tìm thấy đơn hàng');
            res.redirect('back');
        }
        else {
            yield allModel.DonHang.update({
                tinhTrang: "Đang giao"
            }, {
                where: {
                    idDonHang: idDonHang,
                    tinhTrang: "Đã xác nhận"
                }
            });
            const isQueue = yield (0, updateDoneOrder_helper_1.addOrderToQueue)(idDonHang);
            if (!isQueue) {
                res.status(404).json({ message: 'Đã xảy ra lỗi khi vận chuyển đơn hàng.' });
            }
            else {
                res.status(200).json({ message: 'Đơn hàng đã được vận chuyển!' });
            }
        }
    }
    catch (error) {
        res.status(500).json('Đã xảy ra lỗi khi vận chuyển đơn hàng.');
    }
});
exports.deliverOrder = deliverOrder;
const notificationOrder = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const { idDonHang } = req.params;
    try {
        const order = yield allModel.DonHang.findOne({
            where: {
                idDonHang: idDonHang,
            },
            include: [
                {
                    model: allModel.NguoiDung,
                    as: 'User',
                    attributes: ['idNguoiDung']
                }
            ],
            raw: true,
            nest: true,
        });
        if (!order) {
            res.status(404).json({ message: 'Không tìm thấy đơn hàng' });
        }
        else {
            res.status(200).json({ message: 'Đã gửi thông báo!' });
            yield allModel.ThongBao.create({
                idNguoiDung: order['User']['idNguoiDung'],
                noiDung: req.body.noiDung,
                tieuDe: req.body.tieuDe,
                tinhTrang: 0,
                ngayThongBao: req.body.ngayThongBao
            });
        }
    }
    catch (error) {
        res.status(500).json('Đã xảy ra lỗi khi gửi thông báo.');
    }
});
exports.notificationOrder = notificationOrder;
