import { DataTypes } from 'sequelize';
import sequelize from '../config/database';

const DanhMuc= sequelize.define("DanhMuc",{
    idDanhMuc:{
        type:DataTypes.STRING,
        allowNull:false,
        primaryKey:true
    },
    tenDanhMuc:{
        type:DataTypes.STRING,
        allowNull:false
    },
    moTa:{
        type:DataTypes.TEXT,
    },
    ngayTao:{
        type:DataTypes.DATE,
    }
},{
    tableName: "DanhMuc",
    timestamps: false
})

export default DanhMuc;