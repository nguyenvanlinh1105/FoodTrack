import { DataTypes } from 'sequelize';
import sequelize from '../config/database';

const ThanhPho= sequelize.define("ThanhPho",{
    idThanhPho:{
        type:DataTypes.STRING,
        allowNull:false,
        primaryKey:true
    },
    tenThanhPho:{
        type:DataTypes.STRING,
        allowNull:false
    }
},{
    tableName: "ThanhPho",
    timestamps: false
})

export default ThanhPho;
