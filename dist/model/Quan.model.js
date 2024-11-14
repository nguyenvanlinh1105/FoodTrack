"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const sequelize_1 = require("sequelize");
const database_1 = __importDefault(require("../config/database"));
const Quan = database_1.default.define("Quan", {
    idQuan: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false,
        primaryKey: true
    },
    tenQuan: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false
    },
    idThanhPho: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false,
        references: {
            model: 'ThanhPho',
            key: 'idThanhPho'
        }
    }
}, {
    tableName: "Quan",
    timestamps: false
});
exports.default = Quan;
