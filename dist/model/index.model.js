"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.ThongBao = exports.BinhLuanSanPham = exports.TinNhan = exports.ChiTietPhongChat = exports.PhongChat = exports.DanhMuc = exports.SanPham = exports.ChiTietDonHang = exports.DonHang = exports.NguoiDung = exports.VaiTro = exports.sequelize = void 0;
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
const DanhMuc_model_1 = __importDefault(require("./DanhMuc.model"));
exports.DanhMuc = DanhMuc_model_1.default;
const PhongChat_model_1 = __importDefault(require("./PhongChat.model"));
exports.PhongChat = PhongChat_model_1.default;
const ChiTietPhongChat_model_1 = __importDefault(require("./ChiTietPhongChat.model"));
exports.ChiTietPhongChat = ChiTietPhongChat_model_1.default;
const TinNhan_model_1 = __importDefault(require("./TinNhan.model"));
exports.TinNhan = TinNhan_model_1.default;
const SanPhamYeuThich_model_1 = __importDefault(require("./SanPhamYeuThich.model"));
const BinhLuanSanPham_model_1 = __importDefault(require("./BinhLuanSanPham.model"));
exports.BinhLuanSanPham = BinhLuanSanPham_model_1.default;
const ThongBao_model_1 = __importDefault(require("./ThongBao.model"));
exports.ThongBao = ThongBao_model_1.default;
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
NguoiDung_model_1.default.hasMany(BinhLuanSanPham_model_1.default, {
    foreignKey: 'idNguoiDung',
    sourceKey: 'idNguoiDung',
    as: 'Comments'
});
NguoiDung_model_1.default.hasMany(ThongBao_model_1.default, {
    foreignKey: 'idNguoiDung',
    sourceKey: 'idNguoiDung',
    as: 'Notifications'
});
BinhLuanSanPham_model_1.default.belongsTo(NguoiDung_model_1.default, {
    foreignKey: 'idNguoiDung',
    targetKey: 'idNguoiDung',
    as: 'user'
});
SanPham_model_1.default.hasMany(BinhLuanSanPham_model_1.default, {
    foreignKey: 'idSanPham',
    sourceKey: 'idSanPham',
    as: 'Comments'
});
BinhLuanSanPham_model_1.default.belongsTo(SanPham_model_1.default, {
    foreignKey: 'idSanPham',
    targetKey: 'idSanPham',
    as: 'product'
});
DonHang_model_1.default.hasMany(BinhLuanSanPham_model_1.default, {
    foreignKey: 'idDonHang',
    sourceKey: 'idDonHang',
    as: 'Comments'
});
BinhLuanSanPham_model_1.default.belongsTo(DonHang_model_1.default, {
    foreignKey: 'idDonHang',
    targetKey: 'idDonHang',
    as: 'Order'
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
SanPham_model_1.default.belongsTo(DanhMuc_model_1.default, {
    foreignKey: 'idDanhMuc',
    targetKey: 'idDanhMuc',
    as: 'Category'
});
DanhMuc_model_1.default.hasMany(SanPham_model_1.default, {
    foreignKey: 'idDanhMuc',
    sourceKey: 'idDanhMuc',
    as: 'Products'
});
PhongChat_model_1.default.hasMany(TinNhan_model_1.default, {
    foreignKey: 'idPhongChat',
    sourceKey: 'idPhongChat',
    as: 'Messages',
});
TinNhan_model_1.default.belongsTo(PhongChat_model_1.default, {
    foreignKey: 'idPhongChat',
    targetKey: 'idPhongChat',
    as: 'Room',
});
PhongChat_model_1.default.hasMany(ChiTietPhongChat_model_1.default, {
    foreignKey: 'idPhongChat',
    sourceKey: 'idPhongChat',
    as: 'Participants',
});
ChiTietPhongChat_model_1.default.belongsTo(PhongChat_model_1.default, {
    foreignKey: 'idPhongChat',
    targetKey: 'idPhongChat',
    as: 'Room',
});
NguoiDung_model_1.default.hasMany(ChiTietPhongChat_model_1.default, {
    foreignKey: 'idNguoiDung',
    sourceKey: 'idNguoiDung',
    as: 'RoomDetails',
});
ChiTietPhongChat_model_1.default.belongsTo(NguoiDung_model_1.default, {
    foreignKey: 'idNguoiDung',
    targetKey: 'idNguoiDung',
    as: 'User',
});
NguoiDung_model_1.default.hasMany(SanPhamYeuThich_model_1.default, {
    foreignKey: 'idNguoiDung',
    sourceKey: 'idNguoiDung',
    as: 'FavoriteProducts',
});
SanPham_model_1.default.hasMany(SanPhamYeuThich_model_1.default, {
    foreignKey: 'idSanPham',
    sourceKey: 'idSanPham',
    as: 'FavoriteUsers',
});
SanPhamYeuThich_model_1.default.belongsTo(NguoiDung_model_1.default, {
    foreignKey: 'idNguoiDung',
    targetKey: 'idNguoiDung',
    as: 'User',
});
SanPhamYeuThich_model_1.default.belongsTo(SanPham_model_1.default, {
    foreignKey: 'idSanPham',
    targetKey: 'idSanPham',
    as: 'Product',
});
