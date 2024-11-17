import { DataTypes } from 'sequelize';
import sequelize from '../config/database';

const ChiTietPhongChat= sequelize.define("ChiTietPhongChat",{
    idNguoiDung:{
        type:DataTypes.STRING,
        allowNull:false,
        primaryKey:true,
        references:{
            model:'NguoiDung',// Tên bảng trong database
            key:'idNguoiDung'// Khóa chính được tham chiếu
        }
    },
    idPhongChat:{
        type:DataTypes.STRING,
        allowNull:false,
        primaryKey:true,
        references:{
            model:'PhongChat',// Tên bảng trong database
            key:'idPhongChat'// Khóa chính được tham chiếu
        }
    }
},{
    tableName: "ChiTietPhongChat",
    timestamps: false
})

export default ChiTietPhongChat;