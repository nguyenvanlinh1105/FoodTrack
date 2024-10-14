import { DataTypes } from 'sequelize';
import sequelize from '../config/database';

const ChiTietDiaChi= sequelize.define("ChiTietDiaChi",{
    idDiaChi:{
        type:DataTypes.STRING,
        allowNull:false,
        primaryKey:true
    },
    idNguoiDung:{
        type:DataTypes.STRING,
        allowNull:false,
        unique:true,
        references:{
            model:'NguoiDung',// Tên bảng trong database
            key:'idNguoiDung'// Khóa chính được tham chiếu
        }
    },
    idPhuong: {
        type: DataTypes.STRING,
        allowNull: false,
        references: {
            model: 'Phuong',  // Tên bảng trong database
            key: 'idPhuong'   // Khóa chính được tham chiếu
        }
    }
},{
    tableName: "ChiTietDiaChi",
    timestamps: false
})

export default ChiTietDiaChi;