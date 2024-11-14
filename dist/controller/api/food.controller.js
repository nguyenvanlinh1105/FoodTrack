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
exports.listFood = exports.newFood = exports.bestseller = exports.bargain = void 0;
const SanPham_model_1 = __importDefault(require("../../model/SanPham.model"));
const bargain = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const foods = yield SanPham_model_1.default.findAll({
        where: {
            deleted: 0,
            trangThai: 'active',
        },
        raw: true,
        order: [['giaTien', 'ASC']],
        limit: 20,
        attributes: ['slug', 'tenSanPham', 'giaTien', 'images', 'soLuong']
    });
    if (foods.length == 0) {
        res.status(404).json({ message: "Không có món ăn nào" });
    }
    else {
        for (const food of foods) {
            food['giaTien'] = parseFloat(food['giaTien']);
            const images = JSON.parse(food['images']);
            food['images'] = images[0];
        }
        res.status(200).json(foods);
    }
});
exports.bargain = bargain;
const bestseller = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const foods = yield SanPham_model_1.default.findAll({
        where: {
            deleted: 0,
            trangThai: 'active',
        },
        raw: true,
        order: [['soLuong', 'DESC']],
        limit: 20,
        attributes: ['slug', 'tenSanPham', 'giaTien', 'images', 'soLuong']
    });
    if (foods.length == 0) {
        res.status(404).json({ message: "Không có món ăn nào" });
    }
    else {
        for (const food of foods) {
            food['giaTien'] = parseFloat(food['giaTien']);
            const images = JSON.parse(food['images']);
            food['images'] = images[0];
        }
        res.status(200).json(foods);
    }
});
exports.bestseller = bestseller;
const newFood = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const foods = yield SanPham_model_1.default.findAll({
        where: {
            deleted: 0,
            trangThai: 'active',
        },
        raw: true,
        order: [['ngayTao', 'ASC']],
        limit: 20,
        attributes: ['slug', 'tenSanPham', 'giaTien', 'images', 'soLuong']
    });
    if (foods.length == 0) {
        res.status(404).json({ message: "Không có món ăn nào" });
    }
    else {
        for (const food of foods) {
            food['giaTien'] = parseFloat(food['giaTien']);
            const images = JSON.parse(food['images']);
            food['images'] = images[0];
        }
        res.status(200).json(foods);
    }
});
exports.newFood = newFood;
const listFood = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const foods = yield SanPham_model_1.default.findAll({
        where: {
            deleted: 0,
            trangThai: 'active',
        },
        raw: true,
        attributes: ['slug', 'tenSanPham', 'giaTien', 'images', 'soLuong']
    });
    if (foods.length == 0) {
        res.status(404).json({ message: "Không có món ăn nào" });
    }
    else {
        for (const food of foods) {
            food['giaTien'] = parseFloat(food['giaTien']);
            const images = JSON.parse(food['images']);
            food['images'] = images[0];
        }
        res.status(200).json(foods);
    }
});
exports.listFood = listFood;
