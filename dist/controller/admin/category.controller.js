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
exports.deleteCategory = exports.edit = exports.editPage = exports.detailPage = exports.create = exports.createPage = exports.index = void 0;
const DanhMuc_model_1 = __importDefault(require("../../model/DanhMuc.model"));
const slugify_1 = __importDefault(require("slugify"));
const generateNextId_helper_1 = __importDefault(require("../../helper/generateNextId.helper"));
const pagination_helper_1 = require("../../helper/pagination.helper");
const index = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const pagination = yield (0, pagination_helper_1.paginationGeneral)(req, 4, DanhMuc_model_1.default);
    const categories = yield DanhMuc_model_1.default.findAll({
        where: {
            deleted: 0
        },
        raw: true,
        limit: pagination.limitItems,
        offset: pagination.skip
    });
    res.render('admin/pages/category/index', {
        title: 'Quản lý danh mục',
        categories: categories,
        pagination: pagination
    });
});
exports.index = index;
const createPage = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    res.render('admin/pages/category/create', {
        title: 'Tạo danh mục',
    });
});
exports.createPage = createPage;
const create = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const { tenDanhMuc, moTa } = req.body;
    const slug = (0, slugify_1.default)(`${tenDanhMuc}-${Date.now()}`, {
        lower: true,
        locale: 'vi',
        remove: /[`~!@#$%^&*()_+\-=[\]{};':"\\|,.<>\/?]+/g,
        replacement: '-'
    });
    const dataNewCategory = {
        idDanhMuc: yield (0, generateNextId_helper_1.default)(DanhMuc_model_1.default, 'DM'),
        tenDanhMuc: tenDanhMuc,
        moTa: moTa,
        ngayTao: new Date().toISOString(),
        slug: slug
    };
    try {
        const newCategory = yield DanhMuc_model_1.default.create(dataNewCategory);
        res.status(200).json({ message: 'Danh mục đã được tạo thành công!' });
    }
    catch (error) {
        res.status(500).json('Đã xảy ra lỗi khi tạo danh mục.');
    }
});
exports.create = create;
const detailPage = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const slug = req.params.slug;
    const category = yield DanhMuc_model_1.default.findOne({
        where: {
            slug: slug,
            deleted: 0,
        },
        raw: true
    });
    res.render('admin/pages/category/detail', {
        title: 'Chi tiết danh mục',
        category: category
    });
});
exports.detailPage = detailPage;
const editPage = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    req.session.previousPage = req.headers.referer;
    const slug = req.params.slug;
    const category = yield DanhMuc_model_1.default.findOne({
        where: {
            slug: slug,
            deleted: 0,
        },
        raw: true
    });
    res.render('admin/pages/category/edit', {
        title: 'Chỉnh sửa danh mục',
        category: category
    });
});
exports.editPage = editPage;
const edit = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const slug = req.params.slug;
    const { tenDanhMuc, moTa, ngayTao } = req.body;
    yield DanhMuc_model_1.default.update({
        tenDanhMuc: tenDanhMuc,
        moTa: moTa,
        ngayTao: ngayTao
    }, {
        where: {
            slug: slug
        }
    });
    req.flash('success', 'Danh mục đã được cập nhật thành công!');
    const previousPage = req.session.previousPage || '/admin/management/category';
    res.redirect(previousPage);
});
exports.edit = edit;
const deleteCategory = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const slug = req.params.slug;
    yield DanhMuc_model_1.default.update({
        deleted: 1
    }, {
        where: {
            slug: slug
        }
    });
    res.status(200).json({ message: 'Xóa danh mục thành công' });
});
exports.deleteCategory = deleteCategory;
