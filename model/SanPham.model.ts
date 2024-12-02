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
        allowNull:false,
        defaultValue: "active"
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
    soLuongDaBan: {
        type: DataTypes.INTEGER,
        allowNull: false,
        defaultValue: 0 // Đặt giá trị mặc định
    },
    donViTinh:{
        type:DataTypes.STRING,
        allowNull:false,
    },
    ngayTao:{
        type:DataTypes.DATE
    },
    deleted:{
        type:DataTypes.BOOLEAN,
        allowNull:false,
        defaultValue: false
    },
    slug: {
        type: DataTypes.STRING(255),
        allowNull: false,
    }
},{
    tableName: "SanPham",
    timestamps: false
})

export default SanPham;