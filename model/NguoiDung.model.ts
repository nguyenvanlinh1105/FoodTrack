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
    matKhau:{
        type:DataTypes.STRING,
        allowNull:false,
    },
    ngaySinh:{
        type:DataTypes.DATE,
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
    },
    vaiTro:{
        type:DataTypes.STRING,
        allowNull:false,
        references:{
            model: "VaiTro",
            key: "idVaiTro",
        }
    },
    deleted:{
        type:DataTypes.BOOLEAN,
        allowNull:false,
        defaultValue: false
    }
},{
    tableName: "NguoiDung",
    timestamps: false
})

export default NguoiDung;