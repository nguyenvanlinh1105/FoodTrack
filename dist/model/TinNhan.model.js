"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const sequelize_1 = require("sequelize");
const database_1 = __importDefault(require("../config/database"));
const TinNhan = database_1.default.define("TinNhan", {
    idTinNhan: {
        type: sequelize_1.DataTypes.INTEGER,
        allowNull: false,
        primaryKey: true,
        autoIncrement: true,
    },
    idPhongChat: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false,
        references: {
            model: 'PhongChat',
            key: 'idPhongChat'
        }
    },
    noiDung: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false
    },
    images: {
        type: sequelize_1.DataTypes.TEXT,
        allowNull: true
    },
    thoiGianTao: {
        type: sequelize_1.DataTypes.DATE,
    },
    Gui: {
        type: sequelize_1.DataTypes.STRING,
        allowNull: false,
    },
    tinhTrang: {
        type: sequelize_1.DataTypes.INTEGER,
        allowNull: false,
    }
}, {
    tableName: "TinNhan",
    timestamps: false
});
exports.default = TinNhan;
