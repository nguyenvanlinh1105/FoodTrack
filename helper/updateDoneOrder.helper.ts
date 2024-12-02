import redisConnection from "../config/redis";
import { Queue, Worker } from 'bullmq';
import * as allModel from '../model/index.model';

// Khởi tạo hàng chờ riêng để cập nhật đơn hàng
const orderUpdateQueue = new Queue('orderUpdateQueue', { connection: redisConnection });

// Hàm thêm đơn hàng vào hàng chờ
export const addOrderToQueue = async (idDonHang: string):Promise<boolean> => {
    try {
        await orderUpdateQueue.add(
            'updateOrderStatus', 
            { idDonHang }, 
            {
                delay: 60000,   // 1 phút
                attempts: 3,     // Số lần thử lại nếu thất bại
                backoff: 10000   // Thời gian chờ giữa mỗi lần thử lại (ms)
            }
        );
        return true;
    } catch (error) {
       return false;
    }
};

// Worker xử lý công việc trong hàng chờ
const orderUpdateWorker = new Worker(
    'orderUpdateQueue',
    async (job) => {
        const { idDonHang } = job.data;
        try {
            // Tìm và cập nhật trạng thái đơn hàng sang "Hoàn thành"
            const updated = await allModel.DonHang.update(
                { 
                    tinhTrang: 'Hoàn thành',
                    thoiGianHoanThanh: new Date()
                },
                {
                    where: {
                        idDonHang,
                        tinhTrang: 'Đang giao'
                    }
                }
            );

            if (updated[0] > 0) {
                console.log(`Đơn hàng ${idDonHang} đã được cập nhật sang trạng thái "Hoàn thành".`);
            } else {
                console.log(`Không tìm thấy hoặc không thể cập nhật đơn hàng ${idDonHang}.`);
            }
        } catch (error) {
            console.error('Lỗi khi cập nhật trạng thái đơn hàng:', error);
        }
    },
    { connection: redisConnection }
);

export default orderUpdateQueue;