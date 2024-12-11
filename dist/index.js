"use strict";
var __createBinding = (this && this.__createBinding) || (Object.create ? (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    var desc = Object.getOwnPropertyDescriptor(m, k);
    if (!desc || ("get" in desc ? !m.__esModule : desc.writable || desc.configurable)) {
      desc = { enumerable: true, get: function() { return m[k]; } };
    }
    Object.defineProperty(o, k2, desc);
}) : (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    o[k2] = m[k];
}));
var __setModuleDefault = (this && this.__setModuleDefault) || (Object.create ? (function(o, v) {
    Object.defineProperty(o, "default", { enumerable: true, value: v });
}) : function(o, v) {
    o["default"] = v;
});
var __importStar = (this && this.__importStar) || function (mod) {
    if (mod && mod.__esModule) return mod;
    var result = {};
    if (mod != null) for (var k in mod) if (k !== "default" && Object.prototype.hasOwnProperty.call(mod, k)) __createBinding(result, mod, k);
    __setModuleDefault(result, mod);
    return result;
};
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = __importDefault(require("express"));
const dotenv_1 = __importDefault(require("dotenv"));
const cors_1 = __importDefault(require("cors"));
dotenv_1.default.config();
const path_1 = __importDefault(require("path"));
const http_1 = __importDefault(require("http"));
const socket_io_1 = require("socket.io");
const body_parser_1 = __importDefault(require("body-parser"));
const method_override_1 = __importDefault(require("method-override"));
const connect_flash_1 = __importDefault(require("connect-flash"));
const cookie_parser_1 = __importDefault(require("cookie-parser"));
const express_session_1 = __importDefault(require("express-session"));
const system_1 = require("./config/system");
const database_1 = __importDefault(require("./config/database"));
const index_route_1 = __importDefault(require("./routes/api/index.route"));
const index_route_2 = __importDefault(require("./routes/admin/index.route"));
const app = (0, express_1.default)();
const server = http_1.default.createServer(app);
const port = process.env.PORT || 3000;
const io = new socket_io_1.Server(server, {
    cors: {
        origin: "*",
        methods: ["GET", "POST", "PUT", "DELETE"],
    },
});
global._io = io;
const saveChatDB = __importStar(require("./helper/saveChatDB.helper"));
_io.on('connection', (socket) => {
    socket.on('CLIENT_SEND_MESSAGE', (data) => __awaiter(void 0, void 0, void 0, function* () {
        const currentTime = new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
        try {
            const chatData = JSON.parse(data);
            _io.emit('SERVER_RESEND_MESSAGE_CLIENT', {
                avatar: chatData.avatar,
                name: chatData.tenNguoiDung,
                message: chatData.noiDungChat,
                gender: chatData.gioiTinh,
                time: currentTime,
                idRoom: chatData.idPhongChat,
                idUser: chatData.idUser,
            });
        }
        catch (error) {
            console.log('Error', error);
        }
    }));
    socket.on('SAVE_CHAT_READ', (data) => {
        saveChatDB.saveChat(data.idRoom, data.message, "", new Date(), data.idUser);
    });
    socket.on('SAVE_CHAT_UNREAD', (data) => {
        saveChatDB.saveChat(data.idRoom, data.message, "", new Date(), data.idUser, 0);
    });
});
app.use((0, cors_1.default)());
database_1.default;
app.set('views', `${__dirname}/views`);
app.set('view engine', 'pug');
app.use(express_1.default.static(`${__dirname}/public`));
app.use(body_parser_1.default.urlencoded({ extended: false }));
app.use(body_parser_1.default.json());
app.locals.prefixAdmin = system_1.systemConfig.prefixAdmin;
app.use((0, cookie_parser_1.default)());
app.use((0, express_session_1.default)({ secret: 'some secret',
}));
app.use((0, connect_flash_1.default)());
app.use(express_1.default.json());
app.use((0, method_override_1.default)('_method'));
app.use('/tinymce', express_1.default.static(path_1.default.join(__dirname, 'node_modules', 'tinymce')));
app.use((req, res, next) => {
    res.locals.messages = req.flash();
    next();
});
(0, index_route_1.default)(app);
(0, index_route_2.default)(app);
app.get('*', (req, res) => {
    res.render('admin/pages/error/404');
});
server.listen(port, () => {
    console.log(`Server is running at http://localhost:${port}`);
});
