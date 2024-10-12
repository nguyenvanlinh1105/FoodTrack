import express, { Express } from "express";
import dotenv from 'dotenv';//Nhúng dotenv từ module dotenv
import cors from "cors" //Nhúng cors vào dự án
dotenv.config();//Thêm config cho dotenv
import bodyParser from'body-parser';//Nhúng body-parser từ module body-parser

//Import Config
import { systemConfig } from "./config/system";

//Import database
import sequelize from "./config/database";

//Import routes
import routesAPI from "./routes/api/index.route";
import routesAdmin from "./routes/admin/index.route";

const app: Express = express();
const port : number | string =process.env.PORT ||3000;

app.use(cors());//Nhúng cors vào dự án

sequelize;

app.set('views', `${__dirname}/views`);
app.set('view engine', 'pug');
app.use(express.static(`${__dirname}/public`));//Định tuyến file tĩnh (Quan trọng phải có)

//Nhúng body-parser vào dự án
app.use(bodyParser.urlencoded({ extended: false }))//Nhận dữ liệu từ form
app.use(bodyParser.json());//Nhận dữ liệu từ fetch

app.locals.prefixAdmin= systemConfig.prefixAdmin;//Truyền biến locals cho các router và file pug sử dụng


routesAPI(app);
routesAdmin(app);

app.listen(port, () => {
    console.log(`Server is running at http://localhost:${port}`);
});