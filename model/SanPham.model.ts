import { DataTypes } from 'sequelize';
import sequelize from '../config/database';

const SanPham= sequelize.define("SanPham",{
    idSanPham:{
        type:DataTypes.STRING,
        allowNull:false,
        primaryKey:true
    },
    idDanhMuc:{
        type:DataTypes.STRING,
        allowNull:false,
        references:{
            model: "DanhMuc",
            key: "idDanhMuc"
        }
    },
    tenSanPham:{
        type:DataTypes.TEXT,
        allowNull:false
    },
    trangThai:{
        type:DataTypes.STRING,
        allowNull:false
    },
    giaTien:{
        type:DataTypes.DECIMAL(15,2),
        allowNull:false
    },
    images:{
        type:DataTypes.TEXT,
    },
    moTa:{
        type:DataTypes.TEXT,
    },
    soLuong:{
        type:DataTypes.INTEGER,
        allowNull:false
    },
    donViTinh:{
        type:DataTypes.STRING,
        allowNull:false
    },
    ngayTao:{
        type:DataTypes.DATE
    }
},{
    tableName: "SanPham",
    timestamps: false
})

export default SanPham;