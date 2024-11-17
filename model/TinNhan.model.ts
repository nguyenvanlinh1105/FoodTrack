import { DataTypes } from 'sequelize';
import sequelize from '../config/database';

const TinNhan= sequelize.define("TinNhan",{
    idTinNhan:{
        type:DataTypes.STRING,
        allowNull:false,
        primaryKey:true
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
    thoiGianCapNhat:{
        type:DataTypes.DATE,
    }
},{
    tableName: "TinNhan",
    timestamps: false
})

export default TinNhan;