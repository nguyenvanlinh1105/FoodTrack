"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const sequelize_1 = require("sequelize");
const database_1 = __importDefault(require("../config/database"));
const DanhMuc = database_1.default.define("DanhMuc", {
    idDanhMuc: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false,
        primaryKey: true
    },
    tenDanhMuc: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false
    },
    moTa: {
        type: sequelize_1.DataTypes.TEXT,
    },
    ngayTao: {
        type: sequelize_1.DataTypes.DATE,
    },
    deleted: {
        type: sequelize_1.DataTypes.BOOLEAN,
        allowNull: false,
        defaultValue: false
    }, slug: {
        type: sequelize_1.DataTypes.STRING(255),
        allowNull: false,
    }
}, {
    tableName: "DanhMuc",
    timestamps: false
});
exports.default = DanhMuc;
