import { DataTypes } from 'sequelize';
import sequelize from '../config/database';

const NguoiDung= sequelize.define("NguoiDung",{
    idNguoiDung:{
        type:DataTypes.STRING,
        allowNull:false,
        primaryKey:true
    },
    hoTen:{
        type:DataTypes.STRING,
        allowNull:false
    },
    email:{
        type:DataTypes.STRING,
        allowNull:false,
        unique: true
    },
    sdt:{
        type:DataTypes.STRING,
        allowNull:false,
        unique: true
    },
    tenDangNhap:{
        type:DataTypes.STRING,
        allowNull:false,
    },
    matKhau:{
        type:DataTypes.STRING,
        allowNull:false,
    },
    vaiTro:{
        type:DataTypes.STRING,
        allowNull:false,
    },
    ngaySinh:{
        type:DataTypes.DATE,
    },
    diaChi:{
        type:DataTypes.TEXT,
    },
    gioiTinh:{
        type:DataTypes.STRING,
        allowNull:false,
    },
    avatar:{
        type:DataTypes.TEXT,
    },
    trangThai:{
        type:DataTypes.STRING,
        allowNull:false,
    },
    ngayTao:{
        type:DataTypes.DATE
    },
    ngayCapNhat:{
        type:DataTypes.DATE,
    },
    token:{
        type:DataTypes.STRING,
        unique: true
    }
},{
    tableName: "NguoiDung",
    timestamps: false
})

export default NguoiDung;