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
exports.login = void 0;
const NguoiDung_model_1 = __importDefault(require("../model/NguoiDung.model"));
const login = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const users = yield NguoiDung_model_1.default.findAll({
        raw: true
    });
    if (users.length > 0) {
        res.json({
            code: 200,
            message: 'Login success',
        });
    }
    else {
        res.json({
            code: 401,
            message: 'Invalid username or password',
        });
    }
});
exports.login = login;
