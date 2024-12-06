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
exports.chat = void 0;
const TinNhan_model_1 = __importDefault(require("../../model/TinNhan.model"));
const chat = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const { idPhongChat } = req.query;
    const listChat = yield TinNhan_model_1.default.findAll({
        where: {
            idPhongChat: idPhongChat
        },
        order: [['thoiGianTao', 'ASC']],
        raw: true,
    });
    const newListChat = [];
    for (const chat of listChat) {
        const data = {
            idUser: chat['Gui'],
            noiDungChat: chat['noiDung'],
        };
        newListChat.push(data);
    }
    res.status(200).json(newListChat);
});
exports.chat = chat;
