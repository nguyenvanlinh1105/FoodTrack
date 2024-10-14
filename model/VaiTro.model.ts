import { DataTypes } from 'sequelize';
import sequelize from '../config/database';

const VaiTro= sequelize.define("VaiTro",{
    idVaiTro:{
        type:DataTypes.STRING,
        allowNull:false,
        primaryKey:true
    },
    tenVaiTro:{
        type:DataTypes.STRING,
        allowNull:false
    },
    moTa:{
        type:DataTypes.TEXT,
    }
},{
    tableName: "VaiTro",
    timestamps: false
})

export default VaiTro;