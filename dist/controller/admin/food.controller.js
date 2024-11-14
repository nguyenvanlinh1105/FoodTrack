"use strict";
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
exports.create = exports.createPage = exports.index = void 0;
const slugify_1 = __importDefault(require("slugify"));
const dayjs_1 = __importDefault(require("dayjs"));
const DanhMuc_model_1 = __importDefault(require("../../model/DanhMuc.model"));
const SanPham_model_1 = __importDefault(require("../../model/SanPham.model"));
const generateNextId_helper_1 = __importDefault(require("../../helper/generateNextId.helper"));
const pagination_helper_1 = require("../../helper/pagination.helper");
const index = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const pagination = yield (0, pagination_helper_1.paginationGeneral)(req, 4, SanPham_model_1.default);
    const foods = yield SanPham_model_1.default.findAll({
        where: {
            deleted: 0,
            trangThai: 'active'
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
        soLuong: req.body['soLuong'],
        donViTinh: ((req.body['idDanhMuc'] === 'DM001') ? 'suất' : 'ly'),
        ngayTao: (0, dayjs_1.default)().format('YYYY-MM-DD HH:mm:ss'),
        slug: slug
    };
    const newFood = yield SanPham_model_1.default.create(dataFood);
    res.status(200).json({ message: 'Thêm món ăn thành công' });
});
exports.create = create;
