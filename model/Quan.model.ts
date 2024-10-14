import { DataTypes } from 'sequelize';
import sequelize from '../config/database';

const Quan= sequelize.define("Quan",{
    idQuan:{
        type:DataTypes.STRING,
        allowNull:false,
        primaryKey:true
    },
    tenQuan:{
        type:DataTypes.STRING,
        allowNull:false
    },
    idThanhPho: {
        type: DataTypes.STRING,
        allowNull: false,
        references: {
            model: 'ThanhPho',  // Tên bảng trong database
            key: 'idThanhPho'   // Khóa chính được tham chiếu
        }
    }
},{
    tableName: "Quan",
    timestamps: false
})

export default Quan;