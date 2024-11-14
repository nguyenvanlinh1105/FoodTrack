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
exports.deleteStaff = exports.updateStaff = exports.detailStaffPage = exports.changeStatus = exports.createAdmin = exports.createAdminPage = exports.pageStaff = void 0;
const sequelize_1 = require("sequelize");
const moment_1 = __importDefault(require("moment"));
const allModel = __importStar(require("../../model/index.model"));
const isValid = __importStar(require("../../helper/validField.helper"));
const hashAndVerifyPassword_helper_1 = require("../../helper/hashAndVerifyPassword.helper");
const generateNextId_helper_1 = __importDefault(require("../../helper/generateNextId.helper"));
const generateString = __importStar(require("../../helper/generateRandom.helper"));
const pagination_helper_1 = require("../../helper/pagination.helper");
const pageStaff = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const idUserCurrent = res.locals.user['idNguoiDung'];
    const pagination = yield (0, pagination_helper_1.paginationStaff)(req, 4, idUserCurrent);
    const listStaffs = yield allModel.NguoiDung.findAll({
        attributes: [
            'hoTen', 'email', 'sdt',
            'ngaySinh', 'gioiTinh', 'avatar',
            'trangThai', 'token'
        ],
        where: {
            idNguoiDung: { [sequelize_1.Op.ne]: idUserCurrent },
            vaiTro: { [sequelize_1.Op.in]: ['VT001', 'VT003', 'VT004', 'VT005'] },
            deleted: 0
        },
        include: [{
                model: allModel.VaiTro,
                as: 'Role',
                attributes: ['tenVaiTro']
            }],
        limit: pagination.limitItems,
        offset: pagination.skip,
        raw: true
    });
    for (const staff of listStaffs) {
        staff['tenVaiTro'] = staff['Role.tenVaiTro'];
    }
    res.render('admin/pages/staff/index', {
        title: 'Quản lý nhân viên',
        listStaffs: listStaffs,
        pagination: pagination
    });
});
exports.pageStaff = pageStaff;
const createAdminPage = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const roles = yield allModel.VaiTro.findAll({
        where: {
            idVaiTro: { [sequelize_1.Op.ne]: 'VT002' }
        }
    });
    res.render('admin/pages/staff/create', {
        title: 'Tạo tài khoản cho nhân viên',
        roles: roles
    });
});
exports.createAdminPage = createAdminPage;
const createAdmin = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    if (!isValid.isValidEmail(req.body['email'])) {
        res.status(404).json({ message: 'Email không hợp lệ' });
        return;
    }
    if (!isValid.isValidPhone(req.body['sdt'])) {
        res.status(404).json({ message: 'Số điện thoại không hợp lệ' });
        return;
    }
    const userExist = yield allModel.NguoiDung.findOne({
        where: {
            email: req.body['email'],
            trangThai: 'active',
            vaiTro: ['VT001', 'VT003', 'VT004', 'VT005']
        },
        raw: true
    });
    if (userExist) {
        res.status(404).json({ message: 'Tài khoản đã tồn tại trong hệ thống' });
        return;
    }
    const newUser = {
        idNguoiDung: yield (0, generateNextId_helper_1.default)(allModel.NguoiDung, 'ND'),
        hoTen: req.body['hoTen'],
        email: req.body['email'],
        sdt: req.body['sdt'],
        matKhau: (0, hashAndVerifyPassword_helper_1.hashPassword)(req.body['matKhau']),
        gioiTinh: req.body['gioiTinh'],
        trangThai: 'active',
        ngayTao: new Date(),
        ngayCapNhat: new Date(),
        vaiTro: req.body['vaiTro'],
        token: generateString.generateRandomString(30),
    };
    try {
        const createdUser = yield allModel.NguoiDung.create(newUser);
        res.status(200).json({ message: 'Tạo tài khoản thành công!' });
    }
    catch (error) {
        res.status(500).json({ message: 'Đã xảy ra lỗi khi tạo tài khoản.' });
    }
});
exports.createAdmin = createAdmin;
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
const detailStaffPage = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    req.session.previousPage = req.headers.referer;
    const token = req.params.token;
    const data = yield allModel.NguoiDung.findOne({
        where: {
            token: token,
            trangThai: 'active',
            vaiTro: ['VT001', 'VT003', 'VT004', 'VT005'],
            deleted: 0
        },
        include: [
            {
                model: allModel.VaiTro,
                as: 'Role',
                attributes: ['tenVaiTro']
            }
        ],
        attributes: {
            exclude: ['idNguoiDung', 'ngayCapNhat']
        },
        raw: true
    });
    const staff = Object.assign(Object.assign({}, data), { tenVaiTro: data['Role.tenVaiTro'] });
    staff['ngayTao'] = (0, moment_1.default)(data['ngayTao']).format('YYYY-MM-DD');
    const roles = yield allModel.VaiTro.findAll({
        where: {
            idVaiTro: ['VT001', 'VT003', 'VT004', 'VT005']
        },
        raw: true
    });
    res.render('admin/pages/staff/detail', {
        title: 'Chi tiết nhân viên',
        staff: staff,
        roles: roles
    });
});
exports.detailStaffPage = detailStaffPage;
const updateStaff = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const data = req.body;
    const token = req.params.token;
    const updatedData = {
        vaiTro: data['vaiTro'],
    };
    yield allModel.NguoiDung.update(updatedData, {
        where: {
            token: token,
            trangThai: 'active',
            deleted: 0,
        }
    });
    req.flash('success', 'Cập nhật thông tin nhân viên thành công');
    const previousPage = req.session.previousPage || '/admin/management/staff';
    res.redirect(previousPage);
});
exports.updateStaff = updateStaff;
const deleteStaff = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const token = req.params.token;
    yield allModel.NguoiDung.update({ deleted: 1 }, {
        where: {
            token: token,
            trangThai: 'active',
            deleted: 0,
            vaiTro: { [sequelize_1.Op.ne]: 'VT002' }
        }
    });
    res.status(200).json({ message: 'Xóa nhân viên thành công' });
});
exports.deleteStaff = deleteStaff;
