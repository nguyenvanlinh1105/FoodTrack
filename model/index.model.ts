// models/index.ts
import sequelize from '../config/database';
import VaiTro from './VaiTro.model';
import NguoiDung from './NguoiDung.model';
import DonHang from './DonHang.model';
import SanPham from './SanPham.model';
import ChiTietDonHang from './ChiTietDonHang.model';
import DanhMuc from './DanhMuc.model';
import PhongChat from './PhongChat.model';
import ChiTietPhongChat from './ChiTietPhongChat.model';
import TinNhan from './TinNhan.model';

// Define associations
VaiTro.hasMany(NguoiDung, {
    foreignKey: 'vaiTro',
    sourceKey: 'idVaiTro',
    as: 'Users' // Alias
});

NguoiDung.belongsTo(VaiTro, {
    foreignKey: 'vaiTro',
    targetKey: 'idVaiTro',
    as: 'Role' // Alias
});

NguoiDung.hasMany(DonHang, {
    foreignKey: 'idNguoiDung', // Foreign key in DonHang referring to NguoiDung
    sourceKey: 'idNguoiDung',
    as: 'Orders' // Alias for the relationship
});

DonHang.belongsTo(NguoiDung, {
    foreignKey: 'idNguoiDung', // Foreign key in DonHang referring to NguoiDung
    targetKey: 'idNguoiDung',
    as: 'User' // Alias for the relationship
});


DonHang.hasMany(ChiTietDonHang, {
    foreignKey: 'idDonHang', // Foreign key in ChiTietDonHang referring to DonHang
    sourceKey: 'idDonHang',
    as: 'OrderDetails' // Alias for the relationship
});

ChiTietDonHang.belongsTo(DonHang, {
    foreignKey: 'idDonHang', // Foreign key in ChiTietDonHang referring to DonHang
    targetKey: 'idDonHang',
    as: 'Order' // Alias for the relationship
});

SanPham.hasMany(ChiTietDonHang, {
    foreignKey: 'idSanPham',
    sourceKey: 'idSanPham',
    as: 'OrderDetails'  // Alias để sử dụng trong truy vấn
});

ChiTietDonHang.belongsTo(SanPham, {
    foreignKey: 'idSanPham',
    targetKey: 'idSanPham',
    as: 'Product'  // Alias để sử dụng trong truy vấn
});

SanPham.belongsTo(DanhMuc, {
    foreignKey: 'idDanhMuc',
    targetKey: 'idDanhMuc',
    as: 'Category' // Alias
});

DanhMuc.hasMany(SanPham, {
    foreignKey: 'idDanhMuc',
    sourceKey: 'idDanhMuc',
    as: 'Products' // Alias
});

PhongChat.hasMany(TinNhan, {
    foreignKey: 'idPhongChat',
    sourceKey: 'idPhongChat',
    as: 'Messages', // Alias cho tin nhắn
});


//Phần chat
TinNhan.belongsTo(PhongChat, {
    foreignKey: 'idPhongChat',
    targetKey: 'idPhongChat',
    as: 'Room', // Alias cho phòng chat
});

PhongChat.hasMany(ChiTietPhongChat, {
    foreignKey: 'idPhongChat',
    sourceKey: 'idPhongChat',
    as: 'Participants', // Alias cho danh sách người tham gia
});

ChiTietPhongChat.belongsTo(PhongChat, {
    foreignKey: 'idPhongChat',
    targetKey: 'idPhongChat',
    as: 'Room', // Alias cho phòng chat
});

NguoiDung.hasMany(ChiTietPhongChat, {
    foreignKey: 'idNguoiDung',
    sourceKey: 'idNguoiDung',
    as: 'RoomDetails', // Alias cho chi tiết phòng chat
});

ChiTietPhongChat.belongsTo(NguoiDung, {
    foreignKey: 'idNguoiDung',
    targetKey: 'idNguoiDung',
    as: 'User', // Alias cho người dùng
});

export {sequelize,VaiTro, NguoiDung,DonHang,  ChiTietDonHang, SanPham,DanhMuc,PhongChat,ChiTietPhongChat,TinNhan};