import { Request,Response } from 'express';//Nhúng kiểu Request và Response từ module express
import { Queue, Worker } from 'bullmq';
import nodemailer from 'nodemailer';
import Redis from 'ioredis';

// Cấu hình kết nối Redis
const redisConnection = {
    host: '127.0.0.1', // Địa chỉ Redis server
    port: 6379,
    db: 1 // Cổng Redis
};
// Khởi tạo client Redis
const redisClient = new Redis(redisConnection);
// Khởi tạo hàng đợi email
const emailQueue = new Queue('emailQueue', {
    connection: redisConnection
});
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
        console.log('Email sent: ', info.response);
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
    console.log(otp);
    // Lưu OTP vào Redis
    await storeOtp(email, otp);

    const mailOptions = {
        from: process.env.SEND_MAIL_EMAIL,
        to: email,
        subject: subject,
        html: html // Thêm OTP vào nội dung email
    };

    // Gửi email
    await sendEmail(mailOptions);
}, { connection: redisConnection }); // Thêm cấu hình kết nối cho worker

export const checkOTP= async (email:string,otp:string,req:Request,res: Response) => {
    try {
        const storedOtp = await redisClient.get(email);
        if (storedOtp) {
            if (storedOtp === otp) {
                req.flash('success','Xác thực OTP thành công');
                res.redirect(`/admin/password/reset?email=${encodeURIComponent(email)}`);
            } else {
                req.flash('error','OTP không hợp lệ!');
                res.redirect('back');
            }
        } else {
            req.flash('error','OTP hết thời gian tồn tại!');
            res.redirect('back');
        }
    } catch (error) {
        req.flash('error','Lỗi xác thực OTP');
        res.redirect('back');
    }
}
export const checkOTPAPI= async (email:string,otp:string,req:Request,res: Response) => {
    try {
        const storedOtp = await redisClient.get(email);
        if (storedOtp) {
            if (storedOtp === otp) {
                res.status(200).json({message:'Xác thực OTP thành công'});
            } else {
                res.status(404).json({message:'OTP không hợp lệ!'});
            }
        } else {
            res.status(404).json({message:'OTP hết thời gian tồn tại!'});
        }
    } catch (error) {
        res.status(500).json({message:'Lỗi xác thực OTP'});
    }
}
export default emailQueue;

