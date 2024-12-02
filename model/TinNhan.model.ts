import { DataTypes } from 'sequelize';
import sequelize from '../config/database';

const TinNhan= sequelize.define("TinNhan",{
    idTinNhan: {
        type: DataTypes.INTEGER,  // Chuyển từ STRING thành INTEGER
        allowNull: false,
        primaryKey: true,
        autoIncrement: true,  // Thêm thuộc tính AUTO_INCREMENT
    },
    idPhongChat:{
        type:DataTypes.STRING,
        allowNull:false,
        references:{
            model:'PhongChat',// Tên bảng trong database
            key:'idPhongChat'// Khóa chính được tham chiếu
        }
    },
    noiDung:{
        type:DataTypes.STRING,
        allowNull:false
    },
    images:{
        type:DataTypes.TEXT,
        allowNull:true
    },
    thoiGianTao:{
        type:DataTypes.DATE,
    },
    Gui:{
        type:DataTypes.STRING,
        allowNull:false,
    }
},{
    tableName: "TinNhan",
    timestamps: false
})

export default TinNhan;