import { DataTypes } from 'sequelize';
import sequelize from '../config/database';

const Phuong= sequelize.define("Phuong",{
    idPhuong:{
        type:DataTypes.STRING,
        allowNull:false,
        primaryKey:true
    },
    tenPhuong:{
        type:DataTypes.STRING,
        allowNull:false
    },
    idQuan: {
        type: DataTypes.STRING,
        allowNull: false,
        references: {
            model: 'Quan',  // Tên bảng trong database
            key: 'idQuan'   // Khóa chính được tham chiếu
        }
    }
},{
    tableName: "Phuong",
    timestamps: false
})

export default Phuong;