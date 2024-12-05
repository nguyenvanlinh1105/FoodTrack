import express, { Express,Request,Response,NextFunction } from "express";
import dotenv from 'dotenv';//Nhúng dotenv từ module dotenv
import cors from "cors" //Nhúng cors vào dự án
dotenv.config();//Thêm config cho dotenv
import path from "path";//Nhúng path
import http from "http";//Nhúng http
import { Server } from "socket.io";
import bodyParser from'body-parser';//Nhúng body-parser từ module body-parser
import methodOverride from 'method-override';//Nhúng method-override từ module method-override

import flash from 'connect-flash';
import cookieParser from 'cookie-parser';
import session from 'express-session';

//Import Config
import { systemConfig } from "./config/system";

//Import database
import sequelize from "./config/database";

//Import routes
import routesAPI from "./routes/api/index.route";
import routesAdmin from "./routes/admin/index.route";

const app: Express = express();
const server = http.createServer(app);//Tạo server
const port : number | string =process.env.PORT ||3000;


const io = new Server(server, {
    cors: {
        origin: "*", // Cấu hình CORS để cho phép truy cập từ các domain khác
        methods: ["GET", "POST", "PUT", "DELETE"],
    },
});

// Khai báo biến toàn cục
declare global {
    var _io: Server | undefined;
}
global._io = io;

//Client
import * as saveChatDB from './helper/saveChatDB.helper';
import TinNhan from './model/TinNhan.model';
_io.on('connection', (socket) => {
    socket.on('CLIENT_SEND_MESSAGE', async (data) => {
        const currentTime = new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
        try {
            const chatData = JSON.parse(data);
            _io.emit('SERVER_RESEND_MESSAGE_CLIENT', { 
                avatar: chatData.avatar,
                name:chatData.tenNguoiDung,
                message: chatData.noiDungChat, 
                gender: chatData.gioiTinh,
                time: currentTime,
                idRoom: chatData.idPhongChat,
                idUser: chatData.idUser,
            });
        } catch (error) {
            console.log('Error', error);
        }
    })
    socket.on('SAVE_CHAT_READ', (data) => {
        saveChatDB.saveChat(data.idRoom, data.message, "", new Date(), data.idUser);
    });

    socket.on('SAVE_CHAT_UNREAD', (data) => {
        saveChatDB.saveChat(data.idRoom, data.message, "", new Date(), data.idUser, 0);
    });
});

app.use(cors());//Nhúng cors vào dự án

sequelize;

app.set('views', `${__dirname}/views`);
app.set('view engine', 'pug');
app.use(express.static(`${__dirname}/public`));//Định tuyến file tĩnh (Quan trọng phải có)

//Nhúng body-parser vào dự án
app.use(bodyParser.urlencoded({ extended: false }))//Nhận dữ liệu từ form
app.use(bodyParser.json());//Nhận dữ liệu từ fetch

app.locals.prefixAdmin= systemConfig.prefixAdmin;//Truyền biến locals cho các router và file pug sử dụng

//Phần flash -> Để hiển thị thông báo (Quan trọng phải có)
app.use(cookieParser());
app.use(session(
    {   secret: 'some secret', // Thay thế bằng một khóa bí mật mạnh
    }
));
app.use(flash());

app.use(express.json());
app.use(methodOverride('_method'));
app.use('/tinymce', express.static(path.join(__dirname, 'node_modules', 'tinymce')));

//Middleware để truyền biến messages vào tất cả các view
app.use((req:Request, res:Response,next:NextFunction)=>{
    res.locals.messages = req.flash();
    next();
})

routesAPI(app);
routesAdmin(app);

server.listen(port, () => {
    console.log(`Server is running at http://localhost:${port}`);
});