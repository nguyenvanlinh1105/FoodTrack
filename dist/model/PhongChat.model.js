"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const sequelize_1 = require("sequelize");
const database_1 = __importDefault(require("../config/database"));
const PhongChat = database_1.default.define("PhongChat", {
    idPhongChat: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false,
        primaryKey: true
    },
    loaiPhong: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false
    },
    thoiGianTao: {
        type: sequelize_1.DataTypes.DATE,
    },
    thoiGianCapNhat: {
        type: sequelize_1.DataTypes.DATE,
    }
}, {
    tableName: "PhongChat",
    timestamps: false
});
exports.default = PhongChat;
