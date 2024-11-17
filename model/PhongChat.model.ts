import { DataTypes } from 'sequelize';
import sequelize from '../config/database';

const PhongChat= sequelize.define("PhongChat",{
    idPhongChat:{
        type:DataTypes.STRING,
        allowNull:false,
        primaryKey:true
    },
    loaiPhong:{
        type:DataTypes.STRING,
        allowNull:false
    },
    thoiGianTao:{
        type:DataTypes.DATE,
    },
    thoiGianCapNhat:{
        type:DataTypes.DATE,
    }
},{
    tableName: "PhongChat",
    timestamps: false
})

export default PhongChat;