import { Sequelize } from "sequelize";

const sequelize = new Sequelize(
    process.env.DATABASE_NAME, //Tên database
    process.env.DATABASE_USERNAME, //Username
    process.env.DATABASE_PASSWORD, //Password
    {
       host: process.env.DATABASE_HOST, //Tên địa chỉ host
       dialect: 'mysql'
    }
);
sequelize.authenticate().then(() => {
    console.log('Kết nối DB thành công');
 }).catch((error) => {
    console.error('Kết nối thất bại: ', error);
 });

export default sequelize;