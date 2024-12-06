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
exports.reorder = exports.updateQuantity = exports.listDeny = exports.listUnfinished = exports.deny = exports.confirm = exports.cancelFood = exports.detailOrder = exports.newOrder = void 0;
const allModel = __importStar(require("../../model/index.model"));
const sequelize_1 = require("sequelize");
const he_1 = __importDefault(require("he"));
const generateNextId_helper_1 = __importDefault(require("../../helper/generateNextId.helper"));
const newOrder = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const { idDonHang, idSanPham, idUser, soLuongDat } = req.body;
    try {
        if (!idDonHang) {
            const idDonHang = yield (0, generateNextId_helper_1.default)(allModel.DonHang, 'DH');
            const newDonHang = yield allModel.DonHang.create({
                idDonHang: idDonHang,
                idNguoiDung: idUser,
                tinhTrang: 'Đang xử lý',
                ngayTao: new Date(),
            });
            const lastThreeIdDonHang = idDonHang.trim().slice(-3);
            const lastThreeIdSanPham = idSanPham.trim().slice(-3);
            const idChiTietDonHang = `CTDH${lastThreeIdDonHang}_${lastThreeIdSanPham}`;
            const isExist = yield allModel.ChiTietDonHang.findOne({
                where: {
                    idDonHang: idDonHang,
                    idSanPham: idSanPham
                },
                raw: true
            });
            if (isExist) {
                yield allModel.ChiTietDonHang.update({
                    soLuongDat: isExist['soLuongDat'] + soLuongDat,
                }, {
                    where: {
                        idDonHang: idDonHang,
                        idSanPham: idSanPham
                    }
                });
            }
            else {
                const newChiTietDonHang = yield allModel.ChiTietDonHang.create({
                    idChiTietDonHang: idChiTietDonHang,
                    idDonHang: idDonHang,
                    idSanPham: idSanPham,
                    soLuongDat: soLuongDat,
                });
            }
            res.status(200).json({
                message: 'Đơn hàng đã được tạo và thêm món ăn thành công!',
                idDonHang: idDonHang
            });
        }
        else {
            const lastThreeIdDonHang = idDonHang.trim().slice(-3);
            const lastThreeIdSanPham = idSanPham.trim().slice(-3);
            const idChiTietDonHang = `CTDH${lastThreeIdDonHang}_${lastThreeIdSanPham}`;
            const isExist = yield allModel.ChiTietDonHang.findOne({
                where: {
                    idDonHang: idDonHang,
                    idSanPham: idSanPham
                },
                raw: true
            });
            if (isExist) {
                yield allModel.ChiTietDonHang.update({
                    soLuongDat: isExist['soLuongDat'] + soLuongDat,
                }, {
                    where: {
                        idDonHang: idDonHang,
                        idSanPham: idSanPham
                    }
                });
            }
            else {
                const newChiTietDonHang = yield allModel.ChiTietDonHang.create({
                    idChiTietDonHang: idChiTietDonHang,
                    idDonHang: idDonHang,
                    idSanPham: idSanPham,
                    soLuongDat: soLuongDat,
                });
            }
            res.status(200).json({
                message: 'Thêm món thành công',
            });
        }
    }
    catch (error) {
        res.status(500).json({ message: 'Lỗi ' + error.message });
    }
});
exports.newOrder = newOrder;
const detailOrder = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const { idDonHang } = req.query;
    try {
        if (!idDonHang) {
            res.status(400).json({ message: "Thiếu idDonHang" });
        }
        const order = yield allModel.DonHang.findOne({
            where: {
                idDonHang: idDonHang,
                tinhTrang: 'Đang xử lý'
            },
            raw: true,
        });
        if (!order) {
            res.status(404).json({ message: "Không tìm thấy đơn hàng" });
        }
        else {
            const detailOrder = yield allModel.ChiTietDonHang.findAll({
                where: {
                    idDonHang: idDonHang,
                },
                raw: true,
                attributes: ['idSanPham', 'soLuongDat'],
            });
            for (const food of detailOrder) {
                const foodDetail = yield allModel.SanPham.findOne({
                    where: {
                        idSanPham: food['idSanPham'],
                    },
                    raw: true,
                    attributes: ['tenSanPham', 'giaTien', 'images', 'moTa'],
                });
                food['tenSanPham'] = foodDetail['tenSanPham'];
                food['giaTien'] = parseFloat(foodDetail['giaTien']);
                food['images'] = JSON.parse(foodDetail['images'])[0];
                food['moTa'] = he_1.default.decode(foodDetail['moTa']);
                food['moTa'] = foodDetail['moTa'].replace(/^<p>/, '').replace(/<\/p>$/, '');
            }
            res.status(200).json(detailOrder);
        }
    }
    catch (error) {
        res.status(500).json({ message: 'Lỗi ' + error.message });
    }
});
exports.detailOrder = detailOrder;
const cancelFood = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const { idDonHang, idSanPham } = req.body;
    try {
        const isExist = yield allModel.ChiTietDonHang.findOne({
            where: {
                idDonHang: idDonHang,
                idSanPham: idSanPham
            },
            raw: true
        });
        if (isExist) {
            yield allModel.ChiTietDonHang.destroy({
                where: {
                    idDonHang: idDonHang,
                    idSanPham: idSanPham
                }
            });
            res.status(200).json({ message: 'Hủy món thành công' });
        }
        else {
            res.status(404).json({ message: 'Không tìm thấy món ăn' });
        }
    }
    catch (error) {
        res.status(500).json({ message: 'Lỗi ' + error.message });
    }
});
exports.cancelFood = cancelFood;
const confirm = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const { idDonHang, diaChi, ghiChu, phuongThucThanhToan, tinhTrang, tichDiem } = req.body;
    try {
        const order = yield allModel.DonHang.findOne({
            where: {
                idDonHang: idDonHang,
                tinhTrang: 'Đang xử lý'
            },
            include: [
                {
                    model: allModel.NguoiDung,
                    as: 'User',
                    attributes: ['idNguoiDung', 'tichDiem']
                }
            ],
            raw: true,
            nest: true
        });
        yield allModel.DonHang.update({
            tinhTrang: tinhTrang,
            diaChi: diaChi,
            ghiChu: ghiChu,
            tinhTrangThanhToan: phuongThucThanhToan,
        }, {
            where: {
                idDonHang: idDonHang,
                tinhTrang: 'Đang xử lý'
            }
        });
        yield allModel.NguoiDung.update({
            tichDiem: order['User']['tichDiem'] - tichDiem
        }, {
            where: {
                idNguoiDung: order['User']['idNguoiDung']
            }
        });
        res.status(200).json({ message: 'Xác nhận đơn hàng thành công' });
    }
    catch (error) {
        res.status(500).json({ message: 'Lỗi ' + error.message });
    }
});
exports.confirm = confirm;
const deny = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const { idDonHang } = req.body;
    try {
        yield allModel.DonHang.update({
            tinhTrang: 'Đã hủy',
            thoiGianHuy: new Date()
        }, {
            where: {
                idDonHang: idDonHang,
                tinhTrang: 'Đã xác nhận'
            }
        });
        res.status(200).json({ message: 'Xác nhận đơn hàng thành công' });
    }
    catch (error) {
        res.status(500).json({ message: 'Lỗi ' + error.message });
    }
});
exports.deny = deny;
const listUnfinished = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const { idNguoiDung } = req.query;
    try {
        const orders = yield allModel.DonHang.findAll({
            where: {
                idNguoiDung: idNguoiDung,
                tinhTrang: { [sequelize_1.Op.in]: ['Đã xác nhận', 'Đang giao', 'Hoàn thành'] },
            },
            attributes: ['idDonHang', 'ngayTao', 'diaChi', 'tinhTrang', 'ghiChu', 'tinhTrangThanhToan'],
            order: [['ngayTao', 'DESC']],
            raw: true,
        });
        for (const order of orders) {
            const foods = yield allModel.ChiTietDonHang.findAll({
                where: {
                    idDonHang: order['idDonHang'],
                },
                attributes: ['soLuongDat'],
                include: [
                    {
                        model: allModel.SanPham,
                        as: 'Product',
                        attributes: ['tenSanPham', 'giaTien', 'images'],
                    },
                ],
                raw: true,
                nest: true,
            });
            order['chiTietDonHangs'] = [];
            for (const food of foods) {
                food['Product']['images'] = JSON.parse(food['Product']['images'])[0];
                order['chiTietDonHangs'].push(food);
            }
        }
        res.status(200).json(orders);
    }
    catch (error) {
        res.status(500).json({ message: 'Lỗi ' + error.message });
    }
});
exports.listUnfinished = listUnfinished;
const listDeny = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const { idNguoiDung } = req.query;
    try {
        const orders = yield allModel.DonHang.findAll({
            where: {
                idNguoiDung: idNguoiDung,
                tinhTrang: 'Đã hủy',
                deleted: false
            },
            attributes: ['idDonHang', 'ngayTao', 'diaChi', 'tinhTrang', 'ghiChu', 'tinhTrangThanhToan', 'thoiGianHuy'],
            order: [['idDonHang', 'DESC']],
            raw: true,
        });
        for (const order of orders) {
            const foods = yield allModel.ChiTietDonHang.findAll({
                where: {
                    idDonHang: order['idDonHang'],
                },
                attributes: ['soLuongDat'],
                include: [
                    {
                        model: allModel.SanPham,
                        as: 'Product',
                        attributes: ['idSanPham', 'tenSanPham', 'giaTien', 'images'],
                    },
                ],
                raw: true,
                nest: true,
            });
            order['chiTietDonHangs'] = [];
            for (const food of foods) {
                food['Product']['images'] = JSON.parse(food['Product']['images'])[0];
                order['chiTietDonHangs'].push(food);
            }
            order['thoiGianHuy'] = order['thoiGianHuy'].toLocaleString();
        }
        res.status(200).json(orders);
    }
    catch (error) {
        res.status(500).json({ message: 'Lỗi ' + error.message });
    }
});
exports.listDeny = listDeny;
const updateQuantity = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const { idDonHang, idSanPham, soLuongDat } = req.body;
    console.log(req.body);
    try {
        const isExist = yield allModel.ChiTietDonHang.findOne({
            where: {
                idDonHang: idDonHang,
                idSanPham: idSanPham
            }
        });
        if (!isExist) {
            res.status(404).json({ message: 'Không tìm thấy món ăn' });
        }
        else {
            yield allModel.ChiTietDonHang.update({
                soLuongDat: soLuongDat
            }, {
                where: {
                    idDonHang: idDonHang,
                    idSanPham: idSanPham
                }
            });
            res.status(200).json({ message: 'Cập nhật số lượng thành công' });
        }
    }
    catch (error) {
        res.status(500).json({ message: 'Lỗi ' + error.message });
    }
});
exports.updateQuantity = updateQuantity;
const reorder = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    let { idUser, idDonHang, products, idDonHangHuy } = req.body;
    try {
        let idDonHangTra = null;
        if (!idDonHang) {
            idDonHang = yield (0, generateNextId_helper_1.default)(allModel.DonHang, 'DH');
            const newDonHang = yield allModel.DonHang.create({
                idDonHang: idDonHang,
                idNguoiDung: idUser,
                tinhTrang: 'Đang xử lý',
                ngayTao: new Date(),
            });
            idDonHangTra = idDonHang;
        }
        for (const product of products) {
            const lastThreeIdDonHang = idDonHang.trim().slice(-3);
            const lastThreeIdSanPham = product['idSanPham'].trim().slice(-3);
            const idChiTietDonHang = `CTDH${lastThreeIdDonHang}_${lastThreeIdSanPham}`;
            const isExist = yield allModel.ChiTietDonHang.findOne({
                where: {
                    idDonHang: idDonHang,
                    idSanPham: product['idSanPham']
                },
                raw: true
            });
            if (isExist) {
                yield allModel.ChiTietDonHang.update({
                    soLuongDat: isExist['soLuongDat'] + product['soLuongDat'],
                }, {
                    where: {
                        idDonHang: idDonHang,
                        idSanPham: product['idSanPham']
                    }
                });
            }
            else {
                yield allModel.ChiTietDonHang.create({
                    idChiTietDonHang: idChiTietDonHang,
                    idDonHang: idDonHang,
                    idSanPham: product['idSanPham'],
                    soLuongDat: product['soLuongDat']
                });
            }
        }
        yield allModel.DonHang.update({
            deleted: true
        }, {
            where: {
                idDonHang: idDonHangHuy
            }
        });
        res.status(200).json({
            message: 'Đã mua lại các sản phẩm thành công',
            idDonHang: idDonHangTra
        });
    }
    catch (error) {
        res.status(500).json({ message: 'Lỗi ' + error.message });
    }
});
exports.reorder = reorder;
