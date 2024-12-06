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
exports.index = void 0;
const allModel = __importStar(require("../../model/index.model"));
const chat_socket_1 = __importDefault(require("../../socket/chat.socket"));
const TinNhan_model_1 = __importDefault(require("../../model/TinNhan.model"));
const NguoiDung_model_1 = __importDefault(require("../../model/NguoiDung.model"));
const index = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const user = res.locals.user;
    const { IdRoom } = req.params;
    (0, chat_socket_1.default)(req, res);
    const customer = yield allModel.ChiTietPhongChat.findOne({
        where: {
            idPhongChat: IdRoom
        },
        include: [{
                model: allModel.NguoiDung,
                as: 'User',
                attributes: {
                    exclude: ['ngayTao', 'ngayCapNhat', 'token', 'vaiTro'],
                    include: [
                        ['idNguoiDung', 'idNguoiDung'],
                        ['hoTen', 'hoTen'],
                        ['email', 'email'],
                        ['sdt', 'sdt'],
                        ['gioiTinh', 'gioiTinh'],
                        ['avatar', 'avatar'],
                        ['trangThai', 'trangThai'],
                        ['diaChi', 'diaChi']
                    ]
                }
            }],
        raw: true
    });
    const newCustomer = {
        idNguoiDung: customer['idNguoiDung'],
        hoTen: customer['User.hoTen'],
        email: customer['User.email'],
        sdt: customer['User.sdt'],
        gioiTinh: customer['User.gioiTinh'],
        avatar: customer['User.avatar'],
        trangThai: customer['User.trangThai'],
        diaChi: customer['User.diaChi'],
    };
    const listChat = yield TinNhan_model_1.default.findAll({
        where: {
            idPhongChat: IdRoom
        },
        order: [['thoiGianTao', 'ASC']],
        raw: true,
    });
    for (const chat of listChat) {
        const user = yield NguoiDung_model_1.default.findOne({
            where: {
                idNguoiDung: chat['Gui']
            },
            attributes: ['hoTen', 'avatar', 'gioiTinh'],
            raw: true
        });
        chat['thoiGianTao'] = chat['thoiGianTao'].toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
        chat['avatar'] = user['avatar'];
        chat['gioiTinh'] = user['gioiTinh'];
    }
    res.render('admin/pages/chat/index', {
        title: "Chat",
        IdRoom: IdRoom,
        customer: newCustomer,
        user: user,
        listChat: listChat
    });
});
exports.index = index;
