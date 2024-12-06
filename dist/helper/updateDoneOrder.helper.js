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
exports.addOrderToQueue = void 0;
const redis_1 = __importDefault(require("../config/redis"));
const bullmq_1 = require("bullmq");
const allModel = __importStar(require("../model/index.model"));
const orderUpdateQueue = new bullmq_1.Queue('orderUpdateQueue', { connection: redis_1.default });
const addOrderToQueue = (idDonHang) => __awaiter(void 0, void 0, void 0, function* () {
    try {
        yield orderUpdateQueue.add('updateOrderStatus', { idDonHang }, {
            delay: 60000,
            attempts: 3,
            backoff: 3000
        });
        return true;
    }
    catch (error) {
        return false;
    }
});
exports.addOrderToQueue = addOrderToQueue;
const orderUpdateWorker = new bullmq_1.Worker('orderUpdateQueue', (job) => __awaiter(void 0, void 0, void 0, function* () {
    const { idDonHang } = job.data;
    const transaction = yield allModel.sequelize.transaction();
    try {
        const updated = yield allModel.DonHang.update({
            tinhTrang: 'Hoàn thành',
            thoiGianHoanThanh: new Date()
        }, {
            where: {
                idDonHang,
                tinhTrang: 'Đang giao'
            }
        });
        if (updated[0] === 0) {
            throw new Error('Không tìm thấy đơn hàng hoặc trạng thái không phù hợp');
        }
        let totalMoney = 0;
        const foods = yield allModel.ChiTietDonHang.findAll({
            where: {
                idDonHang
            },
            attributes: ['idSanPham', 'soLuongDat'],
            transaction
        });
        const updates = foods.map((item) => __awaiter(void 0, void 0, void 0, function* () {
            const food = yield allModel.SanPham.findOne({
                where: {
                    idSanPham: item['idSanPham'],
                },
                raw: true,
                attributes: ['giaTien'],
            });
            totalMoney += parseFloat(food['giaTien']) * parseInt(item['soLuongDat']);
            return allModel.SanPham.update({
                soLuongDaBan: allModel.sequelize.literal(`soLuongDaBan + ${item['soLuongDat']}`)
            }, {
                where: {
                    idSanPham: item['idSanPham']
                },
                transaction
            });
        }));
        const order = yield allModel.DonHang.findOne({
            where: {
                idDonHang
            },
            include: [
                {
                    model: allModel.NguoiDung,
                    as: 'User',
                    attributes: ['idNguoiDung', 'tichDiem'],
                }
            ],
            raw: true,
            nest: true,
        });
        let tichDiem;
        console.log(totalMoney);
        if (totalMoney < 50000) {
            tichDiem = 0;
        }
        else if (totalMoney < 150000) {
            tichDiem = 2000;
        }
        else {
            tichDiem = 3000;
        }
        yield allModel.NguoiDung.update({
            tichDiem: order['User']['tichDiem'] + tichDiem
        }, {
            where: {
                idNguoiDung: order['User']['idNguoiDung']
            },
            transaction
        });
        _io.emit('SEND_SCORE_CLIENT', {
            tichDiem: (order['User']['tichDiem'] + tichDiem)
        });
        yield Promise.all(updates);
        yield transaction.commit();
        console.log('Cập nhật thành công!');
    }
    catch (error) {
        console.error('Lỗi khi cập nhật trạng thái đơn hàng:', error);
    }
}), { connection: redis_1.default });
exports.default = orderUpdateQueue;
