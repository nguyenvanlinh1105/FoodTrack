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
exports.detail = exports.disapprove = exports.approveComment = exports.index = void 0;
const allModel = __importStar(require("../../model/index.model"));
const moment_1 = __importDefault(require("moment"));
const pagination_helper_1 = require("../../helper/pagination.helper");
const index = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const pagination = yield (0, pagination_helper_1.paginationGeneral)(req, 4, allModel.BinhLuanSanPham);
    const comments = yield allModel.BinhLuanSanPham.findAll({
        include: [
            {
                model: allModel.NguoiDung,
                as: 'user',
                attributes: ['idNguoiDung', 'hoTen']
            },
            {
                model: allModel.SanPham,
                as: 'product',
                attributes: ['idSanPham', 'tenSanPham']
            }
        ],
        raw: true,
        nest: true,
        limit: pagination.limitItems,
        offset: pagination.skip
    });
    for (const comment of comments) {
        comment['ngayBinhLuan'] = (0, moment_1.default)(comment['ngayBinhLuan']).format('YYYY-MM-DD HH:mm:ss');
    }
    res.render('admin/pages/comment/index', {
        title: 'Quản lý bình luận',
        comments: comments,
        pagination: pagination,
    });
});
exports.index = index;
const approveComment = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const id = parseInt(req.params.id);
    try {
        const isExist = yield allModel.BinhLuanSanPham.findOne({
            where: {
                idBinhLuan: id
            }
        });
        if (!isExist) {
            res.status(404).json({ message: "Không tìm thấy bình luận" });
        }
        else {
            yield allModel.BinhLuanSanPham.update({
                tinhTrang: 'Đã chấp thuận'
            }, {
                where: {
                    idBinhLuan: id
                }
            });
            res.status(200).json({ message: "Duyệt bình luận thành công" });
        }
    }
    catch (error) {
        res.status(500).json({ message: "Lỗi server" + error.message });
    }
});
exports.approveComment = approveComment;
const disapprove = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const id = parseInt(req.params.id);
    try {
        const isExist = yield allModel.BinhLuanSanPham.findOne({
            where: {
                idBinhLuan: id
            }
        });
        if (!isExist) {
            res.status(404).json({ message: "Không tìm thấy bình luận" });
        }
        else {
            yield allModel.BinhLuanSanPham.update({
                tinhTrang: 'Đã từ chối'
            }, {
                where: {
                    idBinhLuan: id
                }
            });
            res.status(200).json({ message: "Từ chối bình luận thành công" });
        }
    }
    catch (error) {
        res.status(500).json({ message: "Lỗi server" + error.message });
    }
});
exports.disapprove = disapprove;
const detail = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const id = parseInt(req.params.id);
    try {
        const comment = yield allModel.BinhLuanSanPham.findOne({
            where: {
                idBinhLuan: id
            },
            include: [
                {
                    model: allModel.NguoiDung,
                    as: 'user',
                    attributes: ['idNguoiDung', 'hoTen', 'email', 'sdt', 'avatar', 'gioiTinh']
                },
                {
                    model: allModel.SanPham,
                    as: 'product',
                    attributes: ['idSanPham', 'tenSanPham', 'images'],
                    include: [
                        {
                            model: allModel.DanhMuc,
                            as: 'Category',
                            attributes: ['tenDanhMuc']
                        }
                    ]
                }
            ],
            raw: true,
            nest: true,
        });
        const images = JSON.parse(comment['product']['images']);
        comment['product']['images'] = images;
        comment['ngayBinhLuan'] = (0, moment_1.default)(comment['ngayBinhLuan']).format('YYYY-MM-DD HH:mm:ss');
        console.log(comment);
        res.render('admin/pages/comment/detail', {
            title: 'Chi tiết bình luận',
            comment: comment
        });
    }
    catch (error) {
        req.flash('error', 'Lỗi' + error.message);
        res.redirect('/admin/management/comment');
    }
});
exports.detail = detail;
