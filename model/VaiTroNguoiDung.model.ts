import { DataTypes } from 'sequelize';
import sequelize from '../config/database';

const VaiTroNguoiDung= sequelize.define("VaiTroNguoiDung",{
    idVaiTro:{
        type:DataTypes.STRING,
        allowNull:false,
        primaryKey:true,
        references:{
            model: "VaiTro",
            key: "idVaiTro",
        }
    },
    idNguoiDung:{
        type:DataTypes.STRING,
        allowNull:false,
        primaryKey:true,
        references:{
            model: "NguoiDung",
            key: "idNguoiDung",
        }
    },

},{
    tableName: "VaiTroNguoiDung",
    timestamps: false
})

export default VaiTroNguoiDung;