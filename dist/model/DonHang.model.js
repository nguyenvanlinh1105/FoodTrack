"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const sequelize_1 = require("sequelize");
const database_1 = __importDefault(require("../config/database"));
const DonHang = database_1.default.define("DonHang", {
    idDonHang: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false,
        primaryKey: true
    },
    idNguoiDung: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false,
        references: {
            model: 'NguoiDung',
            key: 'idNguoiDung'
        }
    },
    tinhTrang: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false
    },
    thoiGianHoanThanh: {
        type: sequelize_1.DataTypes.DATE,
    },
    ngayTao: {
        type: sequelize_1.DataTypes.DATE,
        allowNull: false,
    },
    deleted: {
        type: sequelize_1.DataTypes.BOOLEAN,
        allowNull: false,
        defaultValue: false
    },
    ghiChu: {
        type: sequelize_1.DataTypes.STRING,
    },
    diaChi: {
        type: sequelize_1.DataTypes.STRING,
    },
    tinhTrangThanhToan: {
        type: sequelize_1.DataTypes.STRING,
    },
    thoiGianHuy: {
        type: sequelize_1.DataTypes.DATE
    }
}, {
    tableName: "DonHang",
    timestamps: false
});
exports.default = DonHang;
