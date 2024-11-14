"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.SanPham = exports.ChiTietDonHang = exports.DonHang = exports.NguoiDung = exports.VaiTro = exports.sequelize = void 0;
const database_1 = __importDefault(require("../config/database"));
exports.sequelize = database_1.default;
const VaiTro_model_1 = __importDefault(require("./VaiTro.model"));
exports.VaiTro = VaiTro_model_1.default;
const NguoiDung_model_1 = __importDefault(require("./NguoiDung.model"));
exports.NguoiDung = NguoiDung_model_1.default;
const DonHang_model_1 = __importDefault(require("./DonHang.model"));
exports.DonHang = DonHang_model_1.default;
const SanPham_model_1 = __importDefault(require("./SanPham.model"));
exports.SanPham = SanPham_model_1.default;
const ChiTietDonHang_model_1 = __importDefault(require("./ChiTietDonHang.model"));
exports.ChiTietDonHang = ChiTietDonHang_model_1.default;
VaiTro_model_1.default.hasMany(NguoiDung_model_1.default, {
    foreignKey: 'vaiTro',
    sourceKey: 'idVaiTro',
    as: 'Users'
});
NguoiDung_model_1.default.belongsTo(VaiTro_model_1.default, {
    foreignKey: 'vaiTro',
    targetKey: 'idVaiTro',
    as: 'Role'
});
NguoiDung_model_1.default.hasMany(DonHang_model_1.default, {
    foreignKey: 'idNguoiDung',
    sourceKey: 'idNguoiDung',
    as: 'Orders'
});
DonHang_model_1.default.belongsTo(NguoiDung_model_1.default, {
    foreignKey: 'idNguoiDung',
    targetKey: 'idNguoiDung',
    as: 'User'
});
DonHang_model_1.default.hasMany(ChiTietDonHang_model_1.default, {
    foreignKey: 'idDonHang',
    sourceKey: 'idDonHang',
    as: 'OrderDetails'
});
ChiTietDonHang_model_1.default.belongsTo(DonHang_model_1.default, {
    foreignKey: 'idDonHang',
    targetKey: 'idDonHang',
    as: 'Order'
});
SanPham_model_1.default.hasMany(ChiTietDonHang_model_1.default, {
    foreignKey: 'idSanPham',
    sourceKey: 'idSanPham',
    as: 'OrderDetails'
});
ChiTietDonHang_model_1.default.belongsTo(SanPham_model_1.default, {
    foreignKey: 'idSanPham',
    targetKey: 'idSanPham',
    as: 'Product'
});
