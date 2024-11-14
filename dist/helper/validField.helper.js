"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.isValidPhone = exports.isValidEmail = void 0;
const isValidEmail = (email) => {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return regex.test(email);
};
exports.isValidEmail = isValidEmail;
const isValidPhone = (phone) => {
    const phoneRegex = /^\d{10}$/;
    return phoneRegex.test(phone);
};
exports.isValidPhone = isValidPhone;
