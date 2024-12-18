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
exports.changeStatus = exports.deleteFood = exports.edit = exports.editPage = exports.detail = exports.create = exports.createPage = exports.index = void 0;
const slugify_1 = __importDefault(require("slugify"));
const dayjs_1 = __importDefault(require("dayjs"));
const DanhMuc_model_1 = __importDefault(require("../../model/DanhMuc.model"));
const SanPham_model_1 = __importDefault(require("../../model/SanPham.model"));
const allModel = __importStar(require("../../model/index.model"));
const generateNextId_helper_1 = __importDefault(require("../../helper/generateNextId.helper"));
const pagination_helper_1 = require("../../helper/pagination.helper");
const index = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const pagination = yield (0, pagination_helper_1.paginationGeneral)(req, 4, SanPham_model_1.default);
    const foods = yield SanPham_model_1.default.findAll({
        where: {
            deleted: 0,
        },
        raw: true,
        limit: pagination.limitItems,
        offset: pagination.skip
    });
    for (const food of foods) {
        food['images'] = JSON.parse(food['images'])[0];
        food['giaTien'] = parseFloat(food['giaTien']).toLocaleString('vi-VN', {
            style: 'currency',
            currency: 'VND',
            minimumFractionDigits: 0,
            maximumFractionDigits: 0
        });
    }
    res.render('admin/pages/food/index', {
        title: 'Quản lý món ăn',
        foods: foods,
        pagination: pagination
    });
});
exports.index = index;
const createPage = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const categories = yield DanhMuc_model_1.default.findAll({
        where: {
            deleted: 0
        },
        raw: true
    });
    res.render('admin/pages/food/create', {
        title: 'Tạo món ăn mới',
        categories: categories
    });
});
exports.createPage = createPage;
const create = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const slug = (0, slugify_1.default)(`${req.body['tenSanPham']}-${Date.now()}`, {
        lower: true,
        locale: 'vi',
        remove: /[`~!@#$%^&*()_+\-=[\]{};':"\\|,.<>\/?]+/g,
        replacement: '-'
    });
    const dataFood = {
        idSanPham: yield (0, generateNextId_helper_1.default)(SanPham_model_1.default, 'MA'),
        idDanhMuc: req.body['idDanhMuc'],
        tenSanPham: req.body['tenSanPham'],
        giaTien: req.body['giaTien'],
        images: JSON.stringify(req.body.images),
        moTa: req.body['moTa'],
        donViTinh: ((req.body['idDanhMuc'] === 'DM001') ? 'suất' : 'ly'),
        ngayTao: (0, dayjs_1.default)().format('YYYY-MM-DD HH:mm:ss'),
        slug: slug
    };
    const newFood = yield SanPham_model_1.default.create(dataFood);
    res.status(200).json({ message: 'Thêm món ăn thành công' });
});
exports.create = create;
const detail = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const food = yield allModel.SanPham.findOne({
        where: {
            slug: req.params.slug,
            deleted: 0
        },
        include: [{
                model: allModel.DanhMuc,
                as: 'Category',
                attributes: ['tenDanhMuc']
            }],
        raw: true,
    });
    const categories = yield DanhMuc_model_1.default.findAll({
        where: {
            deleted: 0
        },
        raw: true
    });
    food['images'] = JSON.parse(food['images'])[0];
    food['tenDanhMuc'] = food['Category.tenDanhMuc'];
    delete food['Category.tenDanhMuc'];
    food['giaTien'] = parseFloat(food['giaTien']).toLocaleString('vi-VN', {
        style: 'currency',
        currency: 'VND',
        minimumFractionDigits: 0,
        maximumFractionDigits: 0
    });
    food['ngayTao'] = (0, dayjs_1.default)(food['ngayTao']).format('YYYY-MM-DD');
    res.render('admin/pages/food/detail', {
        title: 'Chi tiết món ăn',
        food: food,
        categories: categories
    });
});
exports.detail = detail;
const editPage = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    req.session.previousPage = req.headers.referer;
    const food = yield allModel.SanPham.findOne({
        where: {
            slug: req.params.slug
        },
        include: [{
                model: allModel.DanhMuc,
                as: 'Category',
                attributes: ['tenDanhMuc']
            }],
        raw: true,
    });
    const categories = yield DanhMuc_model_1.default.findAll({
        where: {
            deleted: 0
        },
        raw: true
    });
    food['images'] = JSON.parse(food['images']);
    food['tenDanhMuc'] = food['Category.tenDanhMuc'];
    delete food['Category.tenDanhMuc'];
    food['giaTien'] = parseFloat(food['giaTien']);
    food['ngayTao'] = (0, dayjs_1.default)(food['ngayTao']).format('YYYY-MM-DD');
    res.render('admin/pages/food/edit', {
        title: 'Chỉnh sửa món ăn',
        food: food,
        categories: categories
    });
});
exports.editPage = editPage;
const edit = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    if (req.body.images && req.body.images.length > 0) {
        req.body.images = JSON.stringify(req.body.images);
    }
    else {
        delete req.body.images;
    }
    yield SanPham_model_1.default.update(req.body, {
        where: {
            slug: req.params.slug
        }
    });
    req.flash('success', 'Cập nhật món ăn thành công');
    const previousPage = req.session.previousPage || '/admin/management/food';
    res.redirect(previousPage);
});
exports.edit = edit;
const deleteFood = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    yield SanPham_model_1.default.update({
        deleted: 1
    }, {
        where: {
            slug: req.params.slug
        }
    });
    res.status(200).json({ message: 'Xóa món ăn thành công' });
});
exports.deleteFood = deleteFood;
const changeStatus = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const { slug, status } = req.params;
    try {
        const [affectedRows] = yield allModel.SanPham.update({ trangThai: status }, { where: { slug: slug, deleted: false } });
        if (affectedRows > 0) {
            req.flash("success", "Cập nhật trạng thái thành công");
        }
        else {
            req.flash("error", "Cập nhật trạng thái thất bại");
        }
        res.redirect('back');
    }
    catch (error) {
        req.flash("error", "Cập nhật trạng thái thất bại");
        res.redirect('back');
    }
});
exports.changeStatus = changeStatus;
