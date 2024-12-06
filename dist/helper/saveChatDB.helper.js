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
exports.saveChat = void 0;
const TinNhan_model_1 = __importDefault(require("../model/TinNhan.model"));
const saveChat = (idPhongChat_1, noiDung_1, ...args_1) => __awaiter(void 0, [idPhongChat_1, noiDung_1, ...args_1], void 0, function* (idPhongChat, noiDung, images = "", thoiGianTao, Gui, tinhTrang = 1) {
    const data = {
        idPhongChat: idPhongChat,
        noiDung: noiDung,
        thoiGianTao: thoiGianTao,
        Gui: Gui,
        tinhTrang: tinhTrang,
    };
    if (images) {
        data['images'] = images;
    }
    yield TinNhan_model_1.default.create(data);
});
exports.saveChat = saveChat;
