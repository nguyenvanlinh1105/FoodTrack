import { DataTypes } from 'sequelize';
import sequelize from '../config/database';

const SanPhamYeuThich= sequelize.define("SanPhamYeuThich",{
    idYeuThich:{
        type:DataTypes.INTEGER,
        allowNull:false,
        primaryKey:true,
        autoIncrement: true
    },
    idNguoiDung:{
        type:DataTypes.STRING,
        allowNull:false,
        primaryKey:true,
        references:{
            model: "NguoiDung",
            key: "idNguoiDung"
        }
    },
    idSanPham:{
        type:DataTypes.STRING,
        allowNull:false,
        primaryKey:true,
        references:{
            model: "SanPham",
            key: "idSanPham"
        }
    },
    ngayCapNhat:{
        type: DataTypes.DATE
    }
},{
    tableName: "SanPhamYeuThich",
    timestamps: false
})

export default SanPhamYeuThich;