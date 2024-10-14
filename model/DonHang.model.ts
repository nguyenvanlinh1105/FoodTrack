import { DataTypes } from 'sequelize';
import sequelize from '../config/database';

const DonHang= sequelize.define("DonHang",{
    idDonHang:{
        type:DataTypes.STRING,
        allowNull:false,
        primaryKey:true
    },
    idNguoiDung:{
        type:DataTypes.STRING,
        allowNull:false,
        references:{
            model:'NguoiDung',// Tên bảng trong database
            key:'idNguoiDung'// Khóa chính được tham chiếu
        }
    },
    tinhTrang:{
        type:DataTypes.ENUM('pending','confirm','deliver','finish','cancel'),
        allowNull:false
    },
    thoiGianHoanThanh:{
        type:DataTypes.DATE,
    },
    ngayTao:{
        type:DataTypes.DATE,
        allowNull:false,
    }
},{
    tableName: "DonHang",
    timestamps: false
})

export default DonHang;