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
var __rest = (this && this.__rest) || function (s, e) {
    var t = {};
    for (var p in s) if (Object.prototype.hasOwnProperty.call(s, p) && e.indexOf(p) < 0)
        t[p] = s[p];
    if (s != null && typeof Object.getOwnPropertySymbols === "function")
        for (var i = 0, p = Object.getOwnPropertySymbols(s); i < p.length; i++) {
            if (e.indexOf(p[i]) < 0 && Object.prototype.propertyIsEnumerable.call(s, p[i]))
                t[p[i]] = s[p[i]];
        }
    return t;
};
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.updateMessages = exports.profileUpdate = exports.profilePage = exports.passwordReset = exports.passwordResetPage = exports.otp = exports.passwordOTPPage = exports.passwordForgot = exports.passwordForgotPage = exports.logout = exports.login = exports.loginPage = void 0;
const sequelize_1 = require("sequelize");
const axios_1 = __importDefault(require("axios"));
const allModel = __importStar(require("../../model/index.model"));
const hashAndVerifyPassword_helper_1 = require("../../helper/hashAndVerifyPassword.helper");
const emailQueue_helper_1 = __importStar(require("../../helper/emailQueue.helper"));
const generateRandom_helper_1 = require("../../helper/generateRandom.helper");
const loginPage = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    res.render('admin/pages/auth/login', {
        title: 'Trang đăng nhập'
    });
});
exports.loginPage = loginPage;
const login = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const { email, password } = req.body;
    try {
        const user = yield allModel.NguoiDung.findOne({
            where: {
                email: email,
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
                exclude: ['ngayTao', 'ngayCapNhat', 'vaiTro']
            },
            raw: true
        });
        if (!user) {
            req.flash('error', 'Tài khoản không tồn tại');
            res.redirect('back');
        }
        else {
            const isMatch = (0, hashAndVerifyPassword_helper_1.verifyPassword)(password, user['matKhau']);
            if (isMatch) {
                const result = Object.assign(Object.assign({}, user), { tenVaiTro: user['Role.tenVaiTro'] });
                res.cookie('token', result['token'], { maxAge: 1000 * 60 * 60 * 1 });
                req.flash('success', 'Đăng nhập thành công');
                res.redirect('/admin/dashboard');
            }
            else {
                req.flash('error', 'Mật khẩu không đúng');
                res.redirect('back');
            }
        }
    }
    catch (error) {
        req.flash('error', 'Lỗi');
        res.redirect('back');
    }
});
exports.login = login;
const logout = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    res.clearCookie('token');
    req.flash('success', 'Đăng xuất thành công');
    res.redirect('/admin/login');
});
exports.logout = logout;
const passwordForgotPage = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const site_key = process.env.RECAPTCHA_SITE_KEY;
    res.render('admin/pages/auth/forgot-password', {
        title: "Quên mật khẩu",
        site_key: site_key
    });
});
exports.passwordForgotPage = passwordForgotPage;
const passwordForgot = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const email = req.body['email'];
    const captcha = req.body['g-recaptcha-response'];
    if (!captcha) {
        req.flash('error', 'Vui lòng xác nhận captcha');
        return res.redirect('back');
    }
    try {
        const response = yield axios_1.default.post(`https://www.google.com/recaptcha/api/siteverify`, null, {
            params: {
                secret: process.env.RECAPTCHA_SECRET_KEY,
                response: captcha,
            },
        });
        const data = response.data;
        if (data.success) {
            const user = yield allModel.NguoiDung.findOne({
                where: {
                    email: email,
                    trangThai: 'active',
                    vaiTro: ['VT001', 'VT003', 'VT004', 'VT005'],
                    deleted: 0
                },
                raw: true
            });
            if (!user) {
                req.flash('error', 'Tài khoản không tồn tại');
                return res.redirect('back');
            }
            else {
                const otp = (0, generateRandom_helper_1.generateRandomNumber)(6);
                const subject = 'Mã OTP lấy lại mật khẩu';
                const html = `Mã OTP xác thực của bạn là <b style="color: greenyellow;">${otp}</b>. Mã OTP có hiệu lực trong 3 phút. Vui lòng không cung cấp mã OTP cho người khác`;
                yield emailQueue_helper_1.default.add('sendMail', { email, subject, html, otp });
                req.flash('success', 'Vui lòng kiểm tra email để lấy mã OTP');
                res.redirect(`/admin/password/otp?email=${encodeURIComponent(email)}`);
            }
        }
        else {
            req.flash('error', 'CAPTCHA không hợp lệ!');
            return res.redirect('back');
        }
    }
    catch (e) {
        req.flash('error', 'Xác minh captcha không thành công');
        return res.redirect('back');
    }
});
exports.passwordForgot = passwordForgot;
const passwordOTPPage = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const email = req.query.email;
    const site_key = process.env.RECAPTCHA_SITE_KEY;
    res.render('admin/pages/auth/otp-password', {
        title: "Nhập mã OTP",
        email: email,
        site_key: site_key
    });
});
exports.passwordOTPPage = passwordOTPPage;
const otp = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const { email, otp } = req.body;
    const captcha = req.body['g-recaptcha-response'];
    if (!captcha) {
        req.flash('error', 'Vui lòng xác nhận captcha');
        return res.redirect('back');
    }
    try {
        const response = yield axios_1.default.post(`https://www.google.com/recaptcha/api/siteverify`, null, {
            params: {
                secret: process.env.RECAPTCHA_SECRET_KEY,
                response: captcha,
            },
        });
        const data = response.data;
        if (data.success) {
            (0, emailQueue_helper_1.checkOTP)(email, otp, req, res);
        }
        else {
            req.flash('error', 'CAPTCHA không hợp lệ!');
            return res.redirect('back');
        }
    }
    catch (e) {
        req.flash('error', 'Xác minh captcha không thành công');
        return res.redirect('back');
    }
});
exports.otp = otp;
const passwordResetPage = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const email = req.query.email;
    const site_key = process.env.RECAPTCHA_SITE_KEY;
    res.render('admin/pages/auth/reset-password', {
        title: "Nhập mật khẩu mới",
        email: email,
        site_key: site_key
    });
});
exports.passwordResetPage = passwordResetPage;
const passwordReset = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const { email, newPassword } = req.body;
    console.log(email, newPassword);
    const captcha = req.body['g-recaptcha-response'];
    try {
        const response = yield axios_1.default.post(`https://www.google.com/recaptcha/api/siteverify`, null, {
            params: {
                secret: process.env.RECAPTCHA_SECRET_KEY,
                response: captcha,
            },
        });
        const data = response.data;
        if (data.success) {
            const user = yield allModel.NguoiDung.findOne({
                where: {
                    email: email,
                    trangThai: 'active',
                    vaiTro: ['VT001', 'VT003', 'VT004', 'VT005'],
                    deleted: 0
                },
                raw: true
            });
            if (!user) {
                req.flash('error', 'Tài khoản không tồn tại');
                return res.redirect('back');
            }
            else {
                yield allModel.NguoiDung.update({
                    matKhau: (0, hashAndVerifyPassword_helper_1.hashPassword)(newPassword)
                }, {
                    where: {
                        email: email
                    }
                });
                req.flash('success', 'Cật nhập mật khẩu thành công');
                return res.redirect('/admin/login');
            }
        }
        else {
            req.flash('error', 'CAPTCHA không hợp lệ!');
            return res.redirect('back');
        }
    }
    catch (e) {
        req.flash('error', 'Xác minh captcha không thành công');
        return res.redirect('back');
    }
});
exports.passwordReset = passwordReset;
const profilePage = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const user = Object.assign(Object.assign({}, res.locals.user), { tenVaiTro: res.locals.user['Role.tenVaiTro'] });
    const roles = yield allModel.VaiTro.findAll({
        where: {
            idVaiTro: ['VT001', 'VT003', 'VT004', 'VT005']
        },
        raw: true
    });
    res.render('admin/pages/auth/profile', {
        title: 'Trang cá nhân',
        user: user,
        roles: roles
    });
});
exports.profilePage = profilePage;
const profileUpdate = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    if (req.body.images && req.body.images.length > 0) {
        req.body.images = JSON.stringify(req.body.images);
    }
    else {
        delete req.body.images;
    }
    const _a = req.body, { password, 'password-confirm': passwordConfirm } = _a, otherData = __rest(_a, ["password", 'password-confirm']);
    const token = res.locals.user.token;
    if ((password && !passwordConfirm) || (!password && passwordConfirm)) {
        req.flash('error', 'Vui lòng nhập cả mật khẩu và xác nhận mật khẩu.');
        return res.redirect('back');
    }
    if (password && passwordConfirm && password !== passwordConfirm) {
        req.flash('error', 'Mật khẩu và xác nhận mật khẩu không khớp.');
        return res.redirect('back');
    }
    const updatedData = Object.assign(Object.assign({}, otherData), (password ? { matKhau: (0, hashAndVerifyPassword_helper_1.hashPassword)(password) } : {}));
    yield allModel.NguoiDung.update(updatedData, {
        where: {
            token: token,
            trangThai: 'active',
            deleted: 0,
        }
    });
    req.flash('success', 'Cập nhật thông tin thành công');
    res.redirect('/admin/dashboard');
});
exports.profileUpdate = profileUpdate;
const updateMessages = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    try {
        const { idChats } = req.body;
        if (!idChats || idChats.length === 0) {
            res.status(400).json({ message: 'Không có danh sách chat' });
        }
        yield allModel.TinNhan.update({ tinhTrang: 1 }, {
            where: {
                idTinNhan: {
                    [sequelize_1.Op.in]: idChats
                }
            }
        });
        res.status(200).json({ messages: 'Success' });
    }
    catch (error) {
        res.status(500).json({ message: 'Lỗi server ' + error.message });
    }
});
exports.updateMessages = updateMessages;
