// models/index.ts
import sequelize from '../config/database';
import VaiTro from './VaiTro.model';
import NguoiDung from './NguoiDung.model';

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

export {sequelize,VaiTro, NguoiDung };