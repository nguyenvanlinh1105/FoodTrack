import { DataTypes } from 'sequelize';
import sequelize from '../config/database';

const ThongBao= sequelize.define("ThongBao",{
    idThongBao: {
        type: DataTypes.INTEGER,  // Chuyển từ STRING thành INTEGER
        allowNull: false,
        primaryKey: true,
        autoIncrement: true,  // Thêm thuộc tính AUTO_INCREMENT
    },
    idNguoiDung:{
        type:DataTypes.STRING,
        allowNull:false,
        references:{
            model:'NguoiDung',// Tên bảng trong database
            key:'idNguoiDung'// Khóa chính được tham chiếu
        }
    },
    tieuDe:{
        type:DataTypes.STRING,
        allowNull:false
    },
    noiDung:{
        type:DataTypes.TEXT,
        allowNull:true
    },
    tinhTrang:{
        type:DataTypes.INTEGER,
        allowNull:false,
    },
    ngayThongBao:{
        type:DataTypes.DATE,
    }
},{
    tableName: "ThongBao",
    timestamps: false
})

export default ThongBao;