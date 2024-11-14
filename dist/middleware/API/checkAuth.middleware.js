"use strict";
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
exports.verifyTokenAndAdminAuth = exports.verifyToken = void 0;
const jsonwebtoken_1 = __importDefault(require("jsonwebtoken"));
const verifyToken = (req, res, next) => __awaiter(void 0, void 0, void 0, function* () {
    const token = req.headers.token;
    if (token) {
        const accessToken = `${token}`.split(' ')[1];
        jsonwebtoken_1.default.verify(accessToken, process.env.ACCESS_TOKEN_SECRET, (err, user) => {
            if (err) {
                console.log(err);
                res.status(403).json({ message: 'Token invalid' });
            }
            else {
                req['user'] = user;
                next();
            }
        });
    }
    else {
        res.status(401).json({ message: "You're not authenticated" });
    }
});
exports.verifyToken = verifyToken;
const verifyTokenAndAdminAuth = (req, res, next) => __awaiter(void 0, void 0, void 0, function* () {
    console.log(req['user']);
    if (req.params.id == req['user']['id'] || req['user']['role'] == 'Admin') {
        next();
    }
    else {
        res.status(403).json({ message: "You're not authorized" });
    }
});
exports.verifyTokenAndAdminAuth = verifyTokenAndAdminAuth;
