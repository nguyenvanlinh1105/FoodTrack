"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const sequelize_1 = require("sequelize");
const database_1 = __importDefault(require("../config/database"));
const BinhLuanSanPham = database_1.default.define("BinhLuanSanPham", {
    idBinhLuan: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false,
        primaryKey: true
    },
    idNguoiDung: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false,
        primaryKey: true,
        references: {
            model: "NguoiDung",
            key: "idNguoiDung"
        }
    },
    idSanPham: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false,
        primaryKey: true,
        references: {
            model: "SanPham",
            key: "idSanPham"
        }
    },
    noiDung: {
        type: sequelize_1.DataTypes.TEXT,
        allowNull: false
    },
    ngayBinhLuan: {
        type: sequelize_1.DataTypes.DATE,
    }
}, {
    tableName: "BinhLuanSanPham",
    timestamps: false
});
exports.default = BinhLuanSanPham;
