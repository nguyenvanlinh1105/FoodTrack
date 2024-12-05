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
        type:DataTypes.STRING,
        allowNull:false
    },
    thoiGianHoanThanh:{
        type:DataTypes.DATE,
    },
    ngayTao:{
        type:DataTypes.DATE,
        allowNull:false,
    },
    deleted:{
        type:DataTypes.BOOLEAN,
        allowNull:false,
        defaultValue: false
    },
    ghiChu:{
        type:DataTypes.STRING,
    },
    diaChi:{
        type:DataTypes.STRING,
    },
    tinhTrangThanhToan:{
        type:DataTypes.STRING,
    },
    thoiGianHuy:{
        type:DataTypes.DATE
    }
},{
    tableName: "DonHang",
    timestamps: false
})

export default DonHang;