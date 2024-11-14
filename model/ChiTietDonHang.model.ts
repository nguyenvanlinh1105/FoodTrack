import { DataTypes } from 'sequelize';
import sequelize from '../config/database';

const ChiTietDonHang= sequelize.define("ChiTietDonHang",{
    idChiTietDonHang:{
        type:DataTypes.STRING,
        allowNull:false,
        primaryKey:true
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
    idDonHang:{
        type:DataTypes.STRING,
        allowNull:false,
        primaryKey:true,
        references:{
            model: "DonHang",
            key: "idDonHang"
        }
    },
    soLuongDat:{
        type:DataTypes.INTEGER,
        allowNull:false
    },
},{
    tableName: "ChiTietDonHang",
    timestamps: false
})

export default ChiTietDonHang;