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
import SanPhamYeuThich from './SanPhamYeuThich.model';
import BinhLuanSanPham from './BinhLuanSanPham.model';
import ThongBao from './ThongBao.model';

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
    foreignKey: 'idNguoiDung', 
    sourceKey: 'idNguoiDung',
    as: 'Orders' // Alias 
});

NguoiDung.hasMany(BinhLuanSanPham, {
    foreignKey: 'idNguoiDung', 
    sourceKey: 'idNguoiDung',
    as: 'Comments' // Alias 
});

NguoiDung.hasMany(ThongBao,{
    foreignKey:'idNguoiDung',
    sourceKey:'idNguoiDung',
    as:'Notifications'
})

BinhLuanSanPham.belongsTo(NguoiDung, {
    foreignKey: 'idNguoiDung', 
    targetKey: 'idNguoiDung',
    as: 'user' // Alias 
})
SanPham.hasMany(BinhLuanSanPham, {
    foreignKey: 'idSanPham', 
    sourceKey: 'idSanPham',
    as: 'Comments' // Alias 
});
BinhLuanSanPham.belongsTo(SanPham, {
    foreignKey: 'idSanPham', 
    targetKey: 'idSanPham',
    as: 'product' // Alias 
})
DonHang.hasMany(BinhLuanSanPham, {
    foreignKey: 'idDonHang', 
    sourceKey: 'idDonHang',
    as: 'Comments' // Alias 
})
BinhLuanSanPham.belongsTo(DonHang, {
    foreignKey: 'idDonHang', 
    targetKey: 'idDonHang',
    as: 'Order' // Alias
})

DonHang.belongsTo(NguoiDung, {
    foreignKey: 'idNguoiDung', 
    targetKey: 'idNguoiDung',
    as: 'User' // Alias 
});

DonHang.hasMany(ChiTietDonHang, {
    foreignKey: 'idDonHang', 
    sourceKey: 'idDonHang',
    as: 'OrderDetails' // Alias
});

ChiTietDonHang.belongsTo(DonHang, {
    foreignKey: 'idDonHang', 
    targetKey: 'idDonHang',
    as: 'Order' // Alias
});

SanPham.hasMany(ChiTietDonHang, {
    foreignKey: 'idSanPham',
    sourceKey: 'idSanPham',
    as: 'OrderDetails'  // Alias
});

ChiTietDonHang.belongsTo(SanPham, {
    foreignKey: 'idSanPham',
    targetKey: 'idSanPham',
    as: 'Product'  // Alias
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
    as: 'Messages', // Alias
});


//Phần chat
TinNhan.belongsTo(PhongChat, {
    foreignKey: 'idPhongChat',
    targetKey: 'idPhongChat',
    as: 'Room', // Alias
});

PhongChat.hasMany(ChiTietPhongChat, {
    foreignKey: 'idPhongChat',
    sourceKey: 'idPhongChat',
    as: 'Participants', // Alias
});

ChiTietPhongChat.belongsTo(PhongChat, {
    foreignKey: 'idPhongChat',
    targetKey: 'idPhongChat',
    as: 'Room', // Alias
});

NguoiDung.hasMany(ChiTietPhongChat, {
    foreignKey: 'idNguoiDung',
    sourceKey: 'idNguoiDung',
    as: 'RoomDetails', // Alias
});

ChiTietPhongChat.belongsTo(NguoiDung, {
    foreignKey: 'idNguoiDung',
    targetKey: 'idNguoiDung',
    as: 'User', // Alias
});


//SanPhamYeuThich
NguoiDung.hasMany(SanPhamYeuThich, {
    foreignKey: 'idNguoiDung',
    sourceKey: 'idNguoiDung',
    as: 'FavoriteProducts', // Alias
});

SanPham.hasMany(SanPhamYeuThich, {
    foreignKey: 'idSanPham',
    sourceKey: 'idSanPham',
    as: 'FavoriteUsers', // Alias
});
SanPhamYeuThich.belongsTo(NguoiDung, {
    foreignKey: 'idNguoiDung',
    targetKey: 'idNguoiDung',
    as: 'User', // Alias
});
SanPhamYeuThich.belongsTo(SanPham, {
    foreignKey: 'idSanPham',
    targetKey: 'idSanPham',
    as: 'Product', // Alias
});

export {sequelize,VaiTro, NguoiDung,DonHang,  ChiTietDonHang, SanPham,DanhMuc,PhongChat,ChiTietPhongChat,TinNhan,BinhLuanSanPham,ThongBao};