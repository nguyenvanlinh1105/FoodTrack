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
                backoff: 3000   // Thời gian chờ giữa mỗi lần thử lại (ms)
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
        const transaction = await allModel.sequelize.transaction();
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
            if (updated[0] === 0) {
                throw new Error('Không tìm thấy đơn hàng hoặc trạng thái không phù hợp');
            }
        
             // Lấy danh sách sản phẩm và số lượng
            let totalMoney=0;
            const foods = await allModel.ChiTietDonHang.findAll({
                where: {
                    idDonHang
                },
                attributes: ['idSanPham', 'soLuongDat'],
                transaction
            });
            const updates = foods.map(async(item) => {
                const food = await allModel.SanPham.findOne({
                    where: {
                        idSanPham: item['idSanPham'],
                    },
                    raw: true,
                    attributes: ['giaTien'],
                })
                totalMoney+=parseFloat(food['giaTien'])*parseInt(item['soLuongDat']);
                return allModel.SanPham.update(
                    {
                        soLuongDaBan: allModel.sequelize.literal(`soLuongDaBan + ${item['soLuongDat']}`)
                    },
                    {
                        where: {
                            idSanPham: item['idSanPham']
                        },
                        transaction 
                    }
                );
            });
            const order= await allModel.DonHang.findOne({
                where: {
                    idDonHang
                },
                include:[
                    {
                        model: allModel.NguoiDung,
                        as: 'User',
                        attributes: ['idNguoiDung','tichDiem'],
                    }
                ],
                raw: true,
                nest: true,
            })
            let tichDiem;
            console.log(totalMoney);
            if (totalMoney < 50000) {
                tichDiem = 0;
            } else if (totalMoney < 150000) {
                tichDiem = 2000;
            } else {
                tichDiem = 3000;
            }

            await allModel.NguoiDung.update({
                tichDiem: order['User']['tichDiem']+tichDiem
            },{
                where:{
                    idNguoiDung: order['User']['idNguoiDung']
                },
                transaction
            })
            _io.emit('SEND_SCORE_CLIENT', {
                tichDiem:(order['User']['tichDiem']+tichDiem)
            });
            await Promise.all(updates);
            await transaction.commit();
            console.log('Cập nhật thành công!');
        } catch (error) {
            console.error('Lỗi khi cập nhật trạng thái đơn hàng:', error);
        }
    },
    { connection: redisConnection }
);

export default orderUpdateQueue;