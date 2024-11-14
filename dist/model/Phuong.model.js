"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const sequelize_1 = require("sequelize");
const database_1 = __importDefault(require("../config/database"));
const Phuong = database_1.default.define("Phuong", {
    idPhuong: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false,
        primaryKey: true
    },
    tenPhuong: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false
    },
    idQuan: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false,
        references: {
            model: 'Quan',
            key: 'idQuan'
        }
    }
}, {
    tableName: "Phuong",
    timestamps: false
});
exports.default = Phuong;
