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
exports.checkOTPAPI = exports.checkOTP = void 0;
const bullmq_1 = require("bullmq");
const nodemailer_1 = __importDefault(require("nodemailer"));
const ioredis_1 = __importDefault(require("ioredis"));
const redis_1 = __importDefault(require("../config/redis"));
const redisClient = new ioredis_1.default(redis_1.default);
const emailQueue = new bullmq_1.Queue('emailQueue', { connection: redis_1.default });
const transporter = nodemailer_1.default.createTransport({
    service: 'gmail',
    auth: {
        user: process.env.SEND_MAIL_EMAIL,
        pass: process.env.SEND_MAIL_PASSWORD
    }
});
function sendEmail(mailOptions) {
    return __awaiter(this, void 0, void 0, function* () {
        try {
            const info = yield transporter.sendMail(mailOptions);
            console.log('Email sent:', info.response);
        }
        catch (error) {
            console.error('Error sending email:', error);
        }
    });
}
function storeOtp(email, otp) {
    return __awaiter(this, void 0, void 0, function* () {
        yield redisClient.set(email, otp, 'EX', 180);
    });
}
const worker = new bullmq_1.Worker('emailQueue', (job) => __awaiter(void 0, void 0, void 0, function* () {
    const { email, subject, html, otp } = job.data;
    console.log('Processing job:', job.id);
    yield storeOtp(email, otp);
    const mailOptions = {
        from: process.env.SEND_MAIL_EMAIL,
        to: email,
        subject: subject,
        html: html
    };
    yield sendEmail(mailOptions);
}), { connection: redis_1.default });
const checkOTP = (email, otp, req, res) => __awaiter(void 0, void 0, void 0, function* () {
    try {
        const storedOtp = yield redisClient.get(email);
        if (storedOtp) {
            if (storedOtp === otp) {
                req.flash('success', 'Xác thực OTP thành công');
                res.redirect(`/admin/password/reset?email=${encodeURIComponent(email)}`);
            }
            else {
                req.flash('error', 'OTP không hợp lệ!');
                res.redirect('back');
            }
        }
        else {
            req.flash('error', 'OTP hết thời gian tồn tại!');
            res.redirect('back');
        }
    }
    catch (error) {
        ;
        req.flash('error', 'Lỗi xác thực OTP');
        res.redirect('back');
    }
});
exports.checkOTP = checkOTP;
const checkOTPAPI = (email, otp, req, res) => __awaiter(void 0, void 0, void 0, function* () {
    try {
        const storedOtp = yield redisClient.get(email);
        if (storedOtp) {
            if (storedOtp === otp) {
                res.status(200).json({ message: 'Xác thực OTP thành công' });
            }
            else {
                res.status(404).json({ message: 'OTP không hợp lệ!' });
            }
        }
        else {
            res.status(404).json({ message: 'OTP hết thời gian tồn tại!' });
        }
    }
    catch (error) {
        res.status(500).json({ message: 'Lỗi xác thực OTP' });
    }
});
exports.checkOTPAPI = checkOTPAPI;
exports.default = emailQueue;
