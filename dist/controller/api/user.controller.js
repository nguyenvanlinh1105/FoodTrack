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
exports.listNotification = exports.comment = exports.getInfo = exports.updateAvatar = exports.updateInfo = exports.passwordReset = exports.otp = exports.passwordForgot = exports.register = exports.login = void 0;
const allModel = __importStar(require("../../model/index.model"));
const sequelize_1 = require("sequelize");
const hashAndVerifyPassword_helper_1 = require("../../helper/hashAndVerifyPassword.helper");
const generateNextId_helper_1 = __importDefault(require("../../helper/generateNextId.helper"));
const generateRandom_helper_1 = require("../../helper/generateRandom.helper");
const emailQueue_helper_1 = __importStar(require("../../helper/emailQueue.helper"));
const generateString = __importStar(require("../../helper/generateRandom.helper"));
const isValid = __importStar(require("../../helper/validField.helper"));
const login = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const { email, matKhau } = req.body;
    try {
        const user = yield allModel.NguoiDung.findOne({
            where: {
                email: email,
                trangThai: 'active',
                vaiTro: 'VT002',
                deleted: 0
            },
            include: [
                {
                    model: allModel.ChiTietPhongChat,
                    as: 'RoomDetails',
                    attributes: ['idPhongChat']
                }
            ],
            raw: true
        });
        if (!user) {
            res.status(404).json({ message: "Tài khoản không tồn tại hoặc đã bị khoá" });
        }
        else {
            const isMatch = (0, hashAndVerifyPassword_helper_1.verifyPassword)(matKhau, user['matKhau']);
            if (isMatch) {
                res.status(200).json({
                    message: "Đăng nhập thành công",
                    idUser: user['idNguoiDung'],
                    hoTenNguoiDung: user['hoTen'],
                    email: user['email'],
                    sdt: user['sdt'],
                    gioiTinh: user['gioiTinh'],
                    avatar: user['avatar'],
                    diaChi: user['diaChi'],
                    idPhongChat: user['RoomDetails.idPhongChat'],
                    ngaySinh: user['ngaySinh'],
                    tichDiem: user['tichDiem'],
                });
            }
            else {
                res.status(404).json({ message: "Mật khẩu không chính xác" });
            }
        }
    }
    catch (error) {
        res.status(500).json({ message: 'Lỗi ' + error.message });
    }
});
exports.login = login;
const register = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const { hoTenNguoiDung, email, sdt, matKhau, gioiTinh } = req.body;
    if (!isValid.isValidEmail(email)) {
        res.status(404).json({ message: 'Email không hợp lệ' });
        return;
    }
    if (!isValid.isValidPhone(sdt)) {
        res.status(404).json({ message: 'Số điện thoại không hợp lệ' });
        return;
    }
    try {
        const user = yield allModel.NguoiDung.findOne({
            where: {
                email: email,
                trangThai: 'active',
                vaiTro: 'VT002'
            },
            raw: true
        });
        if (user) {
            res.status(404).json({ message: 'Tài khoản đã tôn tại!' });
        }
        else {
            const newUser = {
                idNguoiDung: yield (0, generateNextId_helper_1.default)(allModel.NguoiDung, 'ND'),
                hoTen: hoTenNguoiDung,
                email: email,
                sdt: sdt,
                matKhau: (0, hashAndVerifyPassword_helper_1.hashPassword)(matKhau),
                gioiTinh: gioiTinh,
                trangThai: 'active',
                ngayTao: new Date(),
                ngayCapNhat: new Date(),
                vaiTro: 'VT002',
                token: generateString.generateRandomString(30),
                tichDiem: 1000
            };
            const createdUser = yield allModel.NguoiDung.create(newUser);
            const createdRoom = yield allModel.PhongChat.create({
                idPhongChat: yield (0, generateNextId_helper_1.default)(allModel.PhongChat, 'PC'),
                loaiPhong: 'private',
                thoiGianTao: new Date(),
                thoiGianCapNhat: new Date()
            });
            yield allModel.ChiTietPhongChat.create({
                idNguoiDung: createdUser['idNguoiDung'],
                idPhongChat: createdRoom['idPhongChat'],
            });
            res.status(200).json({ message: 'Đăng ký thành công!' });
        }
    }
    catch (error) {
        res.status(500).json({ message: 'Lỗi ' + error.message });
    }
});
exports.register = register;
const passwordForgot = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const email = req.body['email'];
    if (!isValid.isValidEmail(email)) {
        res.status(404).json({ message: 'Email không hợp lệ' });
        return;
    }
    const user = yield allModel.NguoiDung.findOne({
        where: {
            email: email,
            trangThai: 'active',
            vaiTro: 'VT002',
            deleted: 0
        },
        raw: true
    });
    if (!user) {
        res.status(404).json({ message: 'Tài khoản không tồn tại' });
    }
    else {
        const otp = (0, generateRandom_helper_1.generateRandomNumber)(6);
        const subject = 'Mã OTP lấy lại mật khẩu';
        const html = `Mã OTP xác thực của bạn là <b style="color: greenyellow;">${otp}</b>. Mã OTP có hiệu lực trong 3 phút. Vui lòng không cung cấp mã OTP cho người khác`;
        yield emailQueue_helper_1.default.add('sendMail', { email, subject, html, otp });
        res.status(200).json({ message: 'Vui lòng kiểm tra email để lấy mã OTP' });
    }
});
exports.passwordForgot = passwordForgot;
const otp = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const { email, otp } = req.body;
    if (!isValid.isValidEmail(email)) {
        res.status(404).json({ message: 'Email không hợp lệ' });
        return;
    }
    (0, emailQueue_helper_1.checkOTPAPI)(email, otp, req, res);
});
exports.otp = otp;
const passwordReset = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const { email, matKhau } = req.body;
    if (!isValid.isValidEmail(email)) {
        res.status(404).json({ message: 'Email không hợp lệ' });
        return;
    }
    if (!matKhau) {
        res.status(404).json({ message: 'Mật khẩu không hợp lệ' });
        return;
    }
    const user = yield allModel.NguoiDung.findOne({
        where: {
            email: email,
            trangThai: 'active',
            vaiTro: 'VT002',
            deleted: 0
        },
        raw: true
    });
    if (!user) {
        res.status(404).json({ message: 'Tài khoản không tồn tại' });
    }
    else {
        yield allModel.NguoiDung.update({
            matKhau: (0, hashAndVerifyPassword_helper_1.hashPassword)(matKhau)
        }, {
            where: {
                email: email
            }
        });
        res.status(200).json({
            matKhau: matKhau,
            message: 'Đổi mật khẩu thành công',
            email: email
        });
    }
});
exports.passwordReset = passwordReset;
const updateInfo = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const { idUser, hoTenNguoiDung, sdt, email, gioiTinh, ngaySinh, diaChi } = req.body;
    try {
        yield allModel.NguoiDung.update({
            hoTen: hoTenNguoiDung,
            sdt: sdt,
            email: email,
            gioiTinh: gioiTinh,
            ngaySinh: ngaySinh,
            diaChi: diaChi,
            ngayCapNhat: new Date()
        }, {
            where: {
                idNguoiDung: idUser
            }
        });
        res.status(200).send({
            message: 'Update thông tin thành công',
            hoTenNguoiDung: hoTenNguoiDung,
            email: email,
            sdt: sdt,
            gioiTinh: gioiTinh,
            diaChi: diaChi,
            ngaySinh: ngaySinh,
        });
    }
    catch (error) {
        res.status(500).json({ message: 'Lỗi ' + error.message });
    }
});
exports.updateInfo = updateInfo;
const updateAvatar = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const { idNguoiDung, img } = req.body;
    try {
        yield allModel.NguoiDung.update({
            avatar: img
        }, {
            where: {
                idNguoiDung: idNguoiDung
            }
        });
        res.status(200).send({
            message: 'Update ảnh thành công',
            avatar: img
        });
    }
    catch (error) {
        res.status(500).json({ message: 'Lỗi ' + error.message });
    }
});
exports.updateAvatar = updateAvatar;
const getInfo = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const { idNguoiDung } = req.query;
    try {
        const user = yield allModel.NguoiDung.findOne({
            where: {
                idNguoiDung: idNguoiDung
            },
            raw: true
        });
        if (user) {
            res.status(200).send({
                idUser: user['idNguoiDung'],
                hoTenNguoiDung: user['hoTen'],
                email: user['email'],
                sdt: user['sdt'],
                gioiTinh: user['gioiTinh'],
                avatar: user['avatar'],
                diaChi: user['diaChi'],
                ngaySinh: user['ngaySinh'],
            });
        }
        else {
            res.status(404).send({ message: 'Tài khoản không tồn tại' });
        }
    }
    catch (error) {
        res.status(500).json({ message: 'Lỗi ' + error.message });
    }
});
exports.getInfo = getInfo;
const comment = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const { idNguoiDung, idSanPham, noiDung, idDonHang } = req.body;
    console.log(req.body);
    try {
        yield allModel.BinhLuanSanPham.create({
            idNguoiDung: idNguoiDung,
            idSanPham: idSanPham,
            idDonHang: idDonHang,
            noiDung: noiDung,
            ngayBinhLuan: new Date()
        });
        res.status(200).json({ message: 'Bình luận thành công' });
    }
    catch (error) {
        res.status(500).json({ message: 'Lỗi ' + error.message });
    }
});
exports.comment = comment;
const listNotification = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const { idNguoiDung } = req.query;
    try {
        const notifications = yield allModel.ThongBao.findAll({
            where: {
                idNguoiDung: idNguoiDung,
                tinhTrang: 0
            },
            order: [
                ['ngayThongBao', 'DESC']
            ],
            raw: true
        });
        if (notifications.length > 0) {
            const idsToUpdate = notifications.map(notification => notification['idThongBao']);
            yield allModel.ThongBao.update({ tinhTrang: 1 }, {
                where: {
                    idThongBao: {
                        [sequelize_1.Op.in]: idsToUpdate
                    }
                }
            });
        }
        res.status(200).json(notifications);
    }
    catch (error) {
        res.status(500).json({ message: 'Lỗi ' + error.message });
    }
});
exports.listNotification = listNotification;
