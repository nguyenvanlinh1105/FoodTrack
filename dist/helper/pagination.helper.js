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
exports.paginationGeneral = exports.paginationCustomer = exports.paginationStaff = void 0;
const sequelize_1 = require("sequelize");
const NguoiDung_model_1 = __importDefault(require("../model/NguoiDung.model"));
const paginationStaff = (req_1, ...args_1) => __awaiter(void 0, [req_1, ...args_1], void 0, function* (req, limitItems = 4, idUserCurrent) {
    const pagination = {
        currentPage: 1,
        limitItems: limitItems,
        skip: 0
    };
    if (req.query.page) {
        pagination.currentPage = Number(req.query.page);
    }
    pagination.skip = (pagination.currentPage - 1) * pagination.limitItems;
    const countTotal = yield NguoiDung_model_1.default.count({
        where: {
            idNguoiDung: { [sequelize_1.Op.ne]: idUserCurrent },
            vaiTro: { [sequelize_1.Op.in]: ['VT001', 'VT003', 'VT004', 'VT005'] },
            deleted: 0
        }
    });
    pagination['totalPages'] = Math.ceil(countTotal / pagination.limitItems);
    return pagination;
});
exports.paginationStaff = paginationStaff;
const paginationCustomer = (req_1, ...args_1) => __awaiter(void 0, [req_1, ...args_1], void 0, function* (req, limitItems = 4) {
    const pagination = {
        currentPage: 1,
        limitItems: limitItems,
        skip: 0
    };
    if (req.query.page) {
        pagination.currentPage = Number(req.query.page);
    }
    pagination.skip = (pagination.currentPage - 1) * pagination.limitItems;
    const countTotal = yield NguoiDung_model_1.default.count({
        where: {
            vaiTro: 'VT002',
            deleted: 0
        }
    });
    pagination['totalPages'] = Math.ceil(countTotal / pagination.limitItems);
    return pagination;
});
exports.paginationCustomer = paginationCustomer;
const paginationGeneral = (req_1, ...args_1) => __awaiter(void 0, [req_1, ...args_1], void 0, function* (req, limitItems = 4, table) {
    const pagination = {
        currentPage: 1,
        limitItems: limitItems,
        skip: 0
    };
    if (req.query.page) {
        pagination.currentPage = Number(req.query.page);
    }
    pagination.skip = (pagination.currentPage - 1) * pagination.limitItems;
    if (table.name === 'BinhLuanSanPham') {
        const countTotal = yield table.count({});
        pagination['totalPages'] = Math.ceil(countTotal / pagination.limitItems);
        return pagination;
    }
    else {
        const countTotal = yield table.count({
            where: {
                deleted: 0
            }
        });
        pagination['totalPages'] = Math.ceil(countTotal / pagination.limitItems);
        return pagination;
    }
});
exports.paginationGeneral = paginationGeneral;
