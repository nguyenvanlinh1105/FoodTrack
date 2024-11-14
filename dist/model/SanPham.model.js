"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const sequelize_1 = require("sequelize");
const database_1 = __importDefault(require("../config/database"));
const SanPham = database_1.default.define("SanPham", {
    idSanPham: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false,
        primaryKey: true
    },
    idDanhMuc: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false,
        references: {
            model: "DanhMuc",
            key: "idDanhMuc"
        }
    },
    tenSanPham: {
        type: sequelize_1.DataTypes.TEXT,
        allowNull: false
    },
    trangThai: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false,
        defaultValue: "active"
    },
    giaTien: {
        type: sequelize_1.DataTypes.DECIMAL(15, 2),
        allowNull: false
    },
    images: {
        type: sequelize_1.DataTypes.TEXT,
    },
    moTa: {
        type: sequelize_1.DataTypes.TEXT,
    },
    soLuong: {
        type: sequelize_1.DataTypes.INTEGER,
        allowNull: false
    },
    donViTinh: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false,
    },
    ngayTao: {
        type: sequelize_1.DataTypes.DATE
    },
    deleted: {
        type: sequelize_1.DataTypes.BOOLEAN,
        allowNull: false,
        defaultValue: false
    },
    slug: {
        type: sequelize_1.DataTypes.STRING(255),
        allowNull: false,
    }
}, {
    tableName: "SanPham",
    timestamps: false
});
exports.default = SanPham;
