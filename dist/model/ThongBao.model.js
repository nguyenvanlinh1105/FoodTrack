"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const sequelize_1 = require("sequelize");
const database_1 = __importDefault(require("../config/database"));
const ThongBao = database_1.default.define("ThongBao", {
    idThongBao: {
        type: sequelize_1.DataTypes.INTEGER,
        allowNull: false,
        primaryKey: true,
        autoIncrement: true,
    },
    idNguoiDung: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false,
        references: {
            model: 'NguoiDung',
            key: 'idNguoiDung'
        }
    },
    tieuDe: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false
    },
    noiDung: {
        type: sequelize_1.DataTypes.TEXT,
        allowNull: true
    },
    tinhTrang: {
        type: sequelize_1.DataTypes.INTEGER,
        allowNull: false,
    },
    ngayThongBao: {
        type: sequelize_1.DataTypes.DATE,
    }
}, {
    tableName: "ThongBao",
    timestamps: false
});
exports.default = ThongBao;
