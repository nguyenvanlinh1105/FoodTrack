// models/index.ts
import sequelize from '../config/database';
import VaiTro from './VaiTro.model';
import NguoiDung from './NguoiDung.model';
import DonHang from './DonHang.model';
import SanPham from './SanPham.model';
import ChiTietDonHang from './ChiTietDonHang.model';

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



export {sequelize,VaiTro, NguoiDung,DonHang,  ChiTietDonHang, SanPham};