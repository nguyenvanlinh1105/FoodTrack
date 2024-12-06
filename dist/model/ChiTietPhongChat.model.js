"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const sequelize_1 = require("sequelize");
const database_1 = __importDefault(require("../config/database"));
const ChiTietPhongChat = database_1.default.define("ChiTietPhongChat", {
    idNguoiDung: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false,
        primaryKey: true,
        references: {
            model: 'NguoiDung',
            key: 'idNguoiDung'
        }
    },
    idPhongChat: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false,
        primaryKey: true,
        references: {
            model: 'PhongChat',
            key: 'idPhongChat'
        }
    }
}, {
    tableName: "ChiTietPhongChat",
    timestamps: false
});
exports.default = ChiTietPhongChat;
