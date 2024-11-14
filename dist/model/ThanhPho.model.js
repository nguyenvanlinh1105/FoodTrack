"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const sequelize_1 = require("sequelize");
const database_1 = __importDefault(require("../config/database"));
const ThanhPho = database_1.default.define("ThanhPho", {
    idThanhPho: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false,
        primaryKey: true
    },
    tenThanhPho: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false
    },
}, {
    tableName: "ThanhPho",
    timestamps: false
});
exports.default = ThanhPho;
