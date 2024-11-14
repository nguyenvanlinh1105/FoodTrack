"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const sequelize_1 = require("sequelize");
const database_1 = __importDefault(require("../config/database"));
const NguoiDung = database_1.default.define("NguoiDung", {
    idNguoiDung: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false,
        primaryKey: true
    },
    hoTen: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false
    },
    email: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false,
        unique: true
    },
    sdt: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false,
        unique: true
    },
    matKhau: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false,
    },
    ngaySinh: {
        type: sequelize_1.DataTypes.DATE,
    },
    gioiTinh: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false,
    },
    avatar: {
        type: sequelize_1.DataTypes.TEXT,
    },
    trangThai: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false,
        defaultValue: "active"
    },
    ngayTao: {
        type: sequelize_1.DataTypes.DATE
    },
    ngayCapNhat: {
        type: sequelize_1.DataTypes.DATE,
    },
    token: {
        type: sequelize_1.DataTypes.STRING,
        unique: true
    },
    vaiTro: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false,
        references: {
            model: "VaiTro",
            key: "idVaiTro",
        }
    },
    deleted: {
        type: sequelize_1.DataTypes.BOOLEAN,
        allowNull: false,
        defaultValue: false
    },
    diaChi: {
        type: sequelize_1.DataTypes.TEXT
    }
}, {
    tableName: "NguoiDung",
    timestamps: false
});
exports.default = NguoiDung;
