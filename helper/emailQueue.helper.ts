import { Request, Response } from 'express';
import { Queue, Worker } from 'bullmq';
import nodemailer from 'nodemailer';
import Redis from 'ioredis';

// Khởi tạo client Redis
const redisConnection = {
    host: process.env.REDIS_HOST,
    port: parseInt(process.env.REDIS_PORT, 10) || 6379,
    username: process.env.REDIS_USER,
    password: process.env.REDIS_PASSWORD,
    tls: {}  // Sử dụng TLS để bảo mật kết nối
};

const redisClient = new Redis(redisConnection);

// Khởi tạo hàng đợi email với BullMQ
const emailQueue = new Queue('emailQueue', { connection: redisConnection });

// Cấu hình transporter cho nodemailer
const transporter = nodemailer.createTransport({
    service: 'gmail',
    auth: {
        user: process.env.SEND_MAIL_EMAIL,
        pass: process.env.SEND_MAIL_PASSWORD
    }
});

// Hàm gửi email
async function sendEmail(mailOptions) {
    try {
        const info = await transporter.sendMail(mailOptions);
        console.log('Email sent:', info.response);
    } catch (error) {
        console.error('Error sending email:', error);
    }
}

// Hàm lưu OTP vào Redis
async function storeOtp(email, otp) {
    await redisClient.set(email, otp, 'EX', 180);  // Lưu OTP với email làm key
}

// Khởi tạo worker để xử lý các công việc trong hàng đợi
const worker = new Worker('emailQueue', async (job) => {
    const { email, subject, html, otp } = job.data;
    console.log('Processing job:', job.id);
    // Lưu OTP vào Redis
    await storeOtp(email, otp);

    const mailOptions = {
        from: process.env.SEND_MAIL_EMAIL,
        to: email,
        subject: subject,
        html: html
    };

    // Gửi email
    await sendEmail(mailOptions);
}, { connection: redisConnection });

// Hàm kiểm tra OTP từ Redis
export const checkOTP = async (email:string, otp:string, req:Request, res:Response) => {
    try {
        const storedOtp = await redisClient.get(email);
        if (storedOtp) {
            if (storedOtp === otp) {
                req.flash('success', 'Xác thực OTP thành công');
                res.redirect(`/admin/password/reset?email=${encodeURIComponent(email)}`);
            } else {
                req.flash('error', 'OTP không hợp lệ!');
                res.redirect('back');
            }
        } else {
            req.flash('error', 'OTP hết thời gian tồn tại!');
            res.redirect('back');
        }
    } catch (error) {;
        req.flash('error', 'Lỗi xác thực OTP');
        res.redirect('back');
    }
};

// Hàm kiểm tra OTP qua API
export const checkOTPAPI = async (email:string, otp:string, req:Request, res:Response) => {
    try {
        const storedOtp = await redisClient.get(email);
        if (storedOtp) {
            if (storedOtp === otp) {
                res.status(200).json({ message: 'Xác thực OTP thành công' });
            } else {
                res.status(404).json({ message: 'OTP không hợp lệ!' });
            }
        } else {
            res.status(404).json({ message: 'OTP hết thời gian tồn tại!' });
        }
    } catch (error) {
        res.status(500).json({ message: 'Lỗi xác thực OTP' });
    }
};

export default emailQueue;

