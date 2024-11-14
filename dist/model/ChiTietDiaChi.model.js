"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const sequelize_1 = require("sequelize");
const database_1 = __importDefault(require("../config/database"));
const ChiTietDiaChi = database_1.default.define("ChiTietDiaChi", {
    idDiaChi: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false,
        primaryKey: true
    },
    idNguoiDung: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false,
        unique: true,
        references: {
            model: 'NguoiDung',
            key: 'idNguoiDung'
        }
    },
    idPhuong: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false,
        references: {
            model: 'Phuong',
            key: 'idPhuong'
        }
    }
}, {
    tableName: "ChiTietDiaChi",
    timestamps: false
});
exports.default = ChiTietDiaChi;
