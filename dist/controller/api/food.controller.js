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
exports.search = exports.listComment = exports.listOrder = exports.listDrink = exports.listLoveFood = exports.unloveFood = exports.loveFood = exports.detailFood = exports.listFood = exports.newFood = exports.bestseller = exports.bargain = void 0;
const sequelize_1 = require("sequelize");
const SanPham_model_1 = __importDefault(require("../../model/SanPham.model"));
const SanPhamYeuThich_model_1 = __importDefault(require("../../model/SanPhamYeuThich.model"));
const allModel = __importStar(require("../../model/index.model"));
const unidecode_1 = __importDefault(require("unidecode"));
const he_1 = __importDefault(require("he"));
const bargain = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const foods = yield SanPham_model_1.default.findAll({
        where: {
            deleted: 0,
            trangThai: 'active',
        },
        raw: true,
        order: [['giaTien', 'ASC']],
        limit: 20,
        attributes: ['idSanPham', 'slug', 'tenSanPham', 'giaTien', 'images', 'soLuongDaBan', 'moTa']
    });
    if (foods.length == 0) {
        res.status(404).json({ message: "Không có món ăn nào" });
    }
    else {
        for (const food of foods) {
            food['giaTien'] = parseFloat(food['giaTien']);
            const images = JSON.parse(food['images']);
            food['images'] = images[0];
            food['moTa'] = he_1.default.decode(food['moTa']);
            food['moTa'] = food['moTa'].replace(/^<p>/, '').replace(/<\/p>$/, '');
        }
        res.status(200).json(foods);
    }
});
exports.bargain = bargain;
const bestseller = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const foods = yield SanPham_model_1.default.findAll({
        where: {
            deleted: 0,
            trangThai: 'active',
        },
        raw: true,
        order: [['soLuongDaBan', 'DESC']],
        limit: 20,
        attributes: ['idSanPham', 'slug', 'tenSanPham', 'giaTien', 'images', 'soLuongDaBan', 'moTa']
    });
    if (foods.length == 0) {
        res.status(404).json({ message: "Không có món ăn nào" });
    }
    else {
        for (const food of foods) {
            food['giaTien'] = parseFloat(food['giaTien']);
            const images = JSON.parse(food['images']);
            food['images'] = images[0];
            food['moTa'] = he_1.default.decode(food['moTa']);
            food['moTa'] = food['moTa'].replace(/^<p>/, '').replace(/<\/p>$/, '');
        }
        res.status(200).json(foods);
    }
});
exports.bestseller = bestseller;
const newFood = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const foods = yield SanPham_model_1.default.findAll({
        where: {
            deleted: 0,
            trangThai: 'active',
        },
        raw: true,
        order: [['ngayTao', 'DESC']],
        limit: 20,
        attributes: ['idSanPham', 'slug', 'tenSanPham', 'giaTien', 'images', 'soLuongDaBan', 'moTa']
    });
    if (foods.length == 0) {
        res.status(404).json({ message: "Không có món ăn nào" });
    }
    else {
        for (const food of foods) {
            food['giaTien'] = parseFloat(food['giaTien']);
            const images = JSON.parse(food['images']);
            food['images'] = images[0];
            food['moTa'] = he_1.default.decode(food['moTa']);
            food['moTa'] = food['moTa'].replace(/^<p>/, '').replace(/<\/p>$/, '');
        }
        res.status(200).json(foods);
    }
});
exports.newFood = newFood;
const listFood = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const foods = yield SanPham_model_1.default.findAll({
        where: {
            deleted: 0,
            trangThai: 'active',
            donViTinh: 'suất',
        },
        raw: true,
        attributes: ['idSanPham', 'slug', 'tenSanPham', 'giaTien', 'images', 'soLuongDaBan', 'moTa']
    });
    if (foods.length == 0) {
        res.status(404).json({ message: "Không có món ăn nào" });
    }
    else {
        for (const food of foods) {
            food['giaTien'] = parseFloat(food['giaTien']);
            const images = JSON.parse(food['images']);
            food['images'] = images[0];
            food['moTa'] = he_1.default.decode(food['moTa']);
            food['moTa'] = food['moTa'].replace(/^<p>/, '').replace(/<\/p>$/, '');
        }
        res.status(200).json(foods);
    }
});
exports.listFood = listFood;
const detailFood = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const { idNguoiDung, idSanPham } = req.query;
    try {
        if (!idNguoiDung || !idSanPham) {
            res.status(400).json({ message: "Thiếu idNguoiDung hoặc idSanPham" });
        }
        const existFood = yield SanPhamYeuThich_model_1.default.findOne({
            where: {
                idNguoiDung: idNguoiDung,
                idSanPham: idSanPham,
            },
            raw: true,
        });
        if (existFood) {
            res.status(200).json({ isLove: true });
        }
        else {
            res.status(200).json({ isLove: false });
        }
    }
    catch (error) {
        res.status(500).json({ message: 'Lỗi ' + error.message });
    }
});
exports.detailFood = detailFood;
const loveFood = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const { idNguoiDung, idSanPham } = req.body;
    try {
        yield SanPhamYeuThich_model_1.default.create({
            idNguoiDung: idNguoiDung,
            idSanPham: idSanPham,
            ngayCapNhat: new Date(),
        });
        res.status(200).json({ message: "Thành công" });
    }
    catch (error) {
        res.status(500).json({ message: 'Lỗi ' + error.message });
    }
});
exports.loveFood = loveFood;
const unloveFood = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const { idNguoiDung, idSanPham } = req.body;
    try {
        yield SanPhamYeuThich_model_1.default.destroy({
            where: {
                idNguoiDung: idNguoiDung,
                idSanPham: idSanPham,
            }
        });
        res.status(200).json({ message: "Thành công" });
    }
    catch (error) {
        res.status(500).json({ message: 'Lỗi ' + error.message });
    }
});
exports.unloveFood = unloveFood;
const listLoveFood = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const { idNguoiDung } = req.query;
    try {
        const listLoveFood = yield SanPhamYeuThich_model_1.default.findAll({
            where: {
                idNguoiDung: idNguoiDung,
            },
            attributes: ['idSanPham'],
            include: [
                {
                    model: SanPham_model_1.default,
                    as: 'Product',
                    attributes: ['idSanPham', 'tenSanPham', 'giaTien', 'images', 'moTa'],
                },
            ],
            raw: true,
        });
        if (listLoveFood.length == 0) {
            res.status(404).json({ message: "Không có món ăn nào" });
        }
        else {
            for (const food of listLoveFood) {
                food['tenSanPham'] = food['Product.tenSanPham'];
                food['giaTien'] = parseFloat(food['Product.giaTien']);
                const images = JSON.parse(food['Product.images']);
                food['images'] = images[0];
                food['moTa'] = he_1.default.decode(food['Product.moTa']);
                food['moTa'] = food['moTa'].replace(/^<p>/, '').replace(/<\/p>$/, '');
                delete food['Product.giaTien'];
                delete food['Product.images'];
                delete food['Product.moTa'];
                delete food['Product.tenSanPham'];
            }
            res.status(200).json(listLoveFood);
        }
    }
    catch (error) {
        res.status(500).json({ message: 'Lỗi ' + error.message });
    }
});
exports.listLoveFood = listLoveFood;
const listDrink = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const foods = yield SanPham_model_1.default.findAll({
        where: {
            deleted: 0,
            trangThai: 'active',
            donViTinh: 'ly',
        },
        raw: true,
        attributes: ['idSanPham', 'slug', 'tenSanPham', 'giaTien', 'images', 'soLuongDaBan', 'moTa']
    });
    if (foods.length == 0) {
        res.status(404).json({ message: "Không có thức uống nào" });
    }
    else {
        for (const food of foods) {
            food['giaTien'] = parseFloat(food['giaTien']);
            const images = JSON.parse(food['images']);
            food['images'] = images[0];
            food['moTa'] = he_1.default.decode(food['moTa']);
            food['moTa'] = food['moTa'].replace(/^<p>/, '').replace(/<\/p>$/, '');
        }
        res.status(200).json(foods);
    }
});
exports.listDrink = listDrink;
const listOrder = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const { idNguoiDung } = req.query;
    try {
        const orders = yield allModel.DonHang.findAll({
            where: {
                idNguoiDung: idNguoiDung,
                tinhTrang: 'Hoàn thành'
            },
            attributes: ['idDonHang', 'tinhTrang', 'thoiGianHoanThanh', 'ngayTao'],
            raw: true
        });
        for (const order of orders) {
            const foods = yield allModel.ChiTietDonHang.findAll({
                where: {
                    idDonHang: order['idDonHang'],
                },
                attributes: ['soLuongDat',
                    [
                        sequelize_1.Sequelize.literal(`
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
                        attributes: ['idSanPham', 'tenSanPham', 'giaTien', 'images', 'moTa', 'donViTinh'],
                    },
                ],
                raw: true,
                nest: true,
            });
            order['chiTietDonHangs'] = [];
            for (const food of foods) {
                food['Product']['images'] = JSON.parse(food['Product']['images'])[0];
                food['Product']['moTa'] = he_1.default.decode(food['Product']['moTa']);
                food['Product']['moTa'] = food['Product']['moTa'].replace(/^<p>/, '').replace(/<\/p>$/, '');
                food['Product']['giaTien'] = parseFloat(food['Product']['giaTien']);
                order['chiTietDonHangs'].push(food);
            }
            order['thoiGianHoanThanh'] = order['thoiGianHoanThanh'].toLocaleString();
        }
        res.status(200).json(orders);
    }
    catch (error) {
        res.status(500).json({ message: 'Lỗi ' + error.message });
    }
});
exports.listOrder = listOrder;
const listComment = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const { idSanPham } = req.query;
    try {
        const isExistComment = yield allModel.BinhLuanSanPham.findOne({
            where: {
                idSanPham: idSanPham,
                tinhTrang: 'Đã chấp thuận'
            },
            raw: true,
        });
        if (!isExistComment) {
            res.status(404).json({ message: "Chưa có bình luận nào" });
        }
        else {
            const comments = yield allModel.BinhLuanSanPham.findAll({
                where: {
                    idSanPham: idSanPham,
                    tinhTrang: 'Đã chấp thuận'
                },
                attributes: ['idBinhLuan', 'noiDung', 'ngayBinhLuan'],
                include: [
                    {
                        model: allModel.NguoiDung,
                        as: 'user',
                        attributes: [[sequelize_1.Sequelize.col('hoTen'), 'hoTenNguoiDung'], 'avatar'],
                    },
                ],
                raw: true,
                nest: true,
            });
            for (const comment of comments) {
                comment['ngayBinhLuan'] = comment['ngayBinhLuan'].toLocaleString();
            }
            res.status(200).json(comments);
        }
    }
    catch (error) {
        res.status(500).json({ message: 'Lỗi ' + error.message });
    }
});
exports.listComment = listComment;
const search = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const query = `${req.query.query}`;
    try {
        if (query) {
            let querySlug = `${query.trim().replace(/\s/g, '-')}`;
            querySlug = querySlug.replace(/-+/g, '-');
            querySlug = (0, unidecode_1.default)(querySlug);
            const regexQuery = new RegExp(query, 'i');
            const regexQuerySlug = new RegExp(querySlug, 'i');
            const foods = yield SanPham_model_1.default.findAll({
                where: {
                    [sequelize_1.Op.or]: [
                        { tenSanPham: { [sequelize_1.Op.like]: `%${query}%` } },
                        { slug: { [sequelize_1.Op.like]: `%${querySlug}%` } }
                    ]
                },
                raw: true,
                attributes: ['idSanPham', 'slug', 'tenSanPham', 'giaTien', 'images', 'soLuongDaBan', 'moTa']
            });
            if (foods.length == 0) {
                res.status(404).json({ message: "Không có món ăn nào" });
            }
            else {
                for (const food of foods) {
                    food['giaTien'] = parseFloat(food['giaTien']);
                    const images = JSON.parse(food['images']);
                    food['images'] = images[0];
                    food['moTa'] = he_1.default.decode(food['moTa']);
                    food['moTa'] = food['moTa'].replace(/^<p>/, '').replace(/<\/p>$/, '');
                }
                res.status(200).json(foods);
            }
        }
    }
    catch (error) {
        res.status(500).json({ message: 'Lỗi ' + error.message });
    }
});
exports.search = search;
