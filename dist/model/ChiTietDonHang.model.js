"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const sequelize_1 = require("sequelize");
const database_1 = __importDefault(require("../config/database"));
const ChiTietDonHang = database_1.default.define("ChiTietDonHang", {
    idChiTietDonHang: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false,
        primaryKey: true
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
    idDonHang: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false,
        primaryKey: true,
        references: {
            model: "DonHang",
            key: "idDonHang"
        }
    },
    soLuongDat: {
        type: sequelize_1.DataTypes.INTEGER,
        allowNull: false
    },
}, {
    tableName: "ChiTietDonHang",
    timestamps: false
});
exports.default = ChiTietDonHang;
