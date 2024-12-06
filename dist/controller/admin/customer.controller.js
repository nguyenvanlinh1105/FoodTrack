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
exports.deleteCustomer = exports.detailCustomerPage = exports.changeStatus = exports.pageCustomer = void 0;
const database_1 = __importDefault(require("../../config/database"));
const moment_1 = __importDefault(require("moment"));
const allModel = __importStar(require("../../model/index.model"));
const pagination_helper_1 = require("../../helper/pagination.helper");
const pageCustomer = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const pagination = yield (0, pagination_helper_1.paginationCustomer)(req, 4);
    const customers = yield allModel.NguoiDung.findAll({
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
        raw: true,
    });
    for (const customer of customers) {
        const phongChat = yield allModel.ChiTietPhongChat.findOne({
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
    res.render('admin/pages/customer/index', {
        title: 'Quản lý khách hàng',
        customers: customers,
        pagination: pagination
    });
});
exports.pageCustomer = pageCustomer;
const changeStatus = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const { token, status } = req.params;
    const [affectedRows] = yield allModel.NguoiDung.update({ trangThai: status }, { where: { token: token } });
    if (affectedRows > 0) {
        req.flash("success", "Cập nhật trạng thái thành công");
    }
    else {
        req.flash("error", "Cập nhật trạng thái thất bại");
    }
    res.redirect('back');
});
exports.changeStatus = changeStatus;
const detailCustomerPage = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const token = req.params.token;
    const data = yield allModel.NguoiDung.findOne({
        where: {
            token: token,
            trangThai: 'active',
            vaiTro: 'VT002',
            deleted: 0
        },
        attributes: {
            exclude: ['idNguoiDung', 'ngayCapNhat'],
            include: [
                [database_1.default.fn('COUNT', database_1.default.fn('DISTINCT', database_1.default.literal(`CASE WHEN Orders.tinhTrang ='Đang xử lý' or Orders.tinhTrang = 'Đã xác nhận' or Orders.tinhTrang = 'Đang giao' THEN Orders.idDonHang END`))), 'soLuongDonHangDangXuLy'],
                [database_1.default.fn('SUM', database_1.default.literal(`CASE WHEN Orders.tinhTrang ='Đang xử lý' or Orders.tinhTrang = 'Đã xác nhận' or Orders.tinhTrang = 'Đang giao' THEN \`Orders->OrderDetails\`.\`soLuongDat\` * \`Orders->OrderDetails->Product\`.\`giaTien\` END`)), 'tongTienDonHangDangXuLy'],
                [database_1.default.fn('COUNT', database_1.default.fn('DISTINCT', database_1.default.literal(`CASE WHEN Orders.tinhTrang = 'Hoàn thành' THEN Orders.idDonHang END`))), 'soLuongDonHangDaThanhToan'],
                [database_1.default.fn('SUM', database_1.default.literal(`CASE WHEN Orders.tinhTrang = 'Hoàn thành' THEN \`Orders->OrderDetails\`.\`soLuongDat\` * \`Orders->OrderDetails->Product\`.\`giaTien\` END`)), 'tongTienDonHangDaThanhToan']
            ]
        },
        include: [
            {
                model: allModel.DonHang,
                as: 'Orders',
                attributes: [],
                include: [
                    {
                        model: allModel.ChiTietDonHang,
                        as: 'OrderDetails',
                        attributes: [],
                        include: [
                            {
                                model: allModel.SanPham,
                                as: 'Product',
                                attributes: []
                            }
                        ]
                    }
                ]
            }
        ],
        group: ['NguoiDung.idNguoiDung'],
        raw: true
    });
    const customer = Object.assign({}, data);
    customer['ngayTao'] = (0, moment_1.default)(data['ngayTao']).format('YYYY-MM-DD');
    customer['soLuongDonHangDangXuLy'] = (data['soLuongDonHangDangXuLy'] > 0) ? data['soLuongDonHangDangXuLy'] : 0;
    customer['tongTienDonHangDangXuLy'] = (data['tongTienDonHangDangXuLy']) ? new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(data['tongTienDonHangDangXuLy']) : '0₫';
    customer['soLuongDonHangDaThanhToan'] = (data['soLuongDonHangDaThanhToan'] > 0) ? data['soLuongDonHangDaThanhToan'] : 0;
    customer['tongTienDonHangDaThanhToan'] = (data['tongTienDonHangDaThanhToan']) ? new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(data['tongTienDonHangDaThanhToan']) : '0₫';
    res.render('admin/pages/customer/detail', {
        title: 'Chi tiết khách hàng',
        customer: customer,
    });
});
exports.detailCustomerPage = detailCustomerPage;
const deleteCustomer = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const token = req.params.token;
    yield allModel.NguoiDung.update({ deleted: 1 }, {
        where: {
            token: token,
            trangThai: 'active',
            deleted: 0,
            vaiTro: 'VT002'
        }
    });
    res.status(200).json({ message: 'Xóa khách hàng thành công' });
});
exports.deleteCustomer = deleteCustomer;
