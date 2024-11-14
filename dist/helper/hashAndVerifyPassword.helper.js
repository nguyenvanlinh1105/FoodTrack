"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.verifyPassword = exports.hashPassword = void 0;
const bcrypt_1 = __importDefault(require("bcrypt"));
const hashPassword = (password) => {
    const salt = bcrypt_1.default.genSaltSync(10);
    const newPassword = bcrypt_1.default.hashSync(password, salt);
    return newPassword;
};
exports.hashPassword = hashPassword;
const verifyPassword = (inputPassword, hashedPassword) => {
    return bcrypt_1.default.compareSync(inputPassword, hashedPassword);
};
exports.verifyPassword = verifyPassword;
