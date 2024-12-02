import { DataTypes } from 'sequelize';
import sequelize from '../config/database';

const BinhLuanSanPham= sequelize.define("BinhLuanSanPham",{
    idBinhLuan:{
        type:DataTypes.INTEGER,
        allowNull:false,
        primaryKey:true,
        autoIncrement: true
    },
    idNguoiDung:{
        type:DataTypes.STRING,
        allowNull:false,
        primaryKey:true,
        references:{
            model: "NguoiDung",
            key: "idNguoiDung"
        }
    },
    idSanPham:{
        type:DataTypes.STRING,
        allowNull:false,
        primaryKey:true,
        references:{
            model: "SanPham",
            key: "idSanPham"
        }
    },
    noiDung:{
        type: DataTypes.TEXT,
        allowNull:false
    },
    ngayBinhLuan:{
        type: DataTypes.DATE,
    }
},{
    tableName: "BinhLuanSanPham",
    timestamps: false
})

export default BinhLuanSanPham;