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
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = __importDefault(require("express"));
const router = express_1.default.Router();
const multer_1 = __importDefault(require("multer"));
const userController = __importStar(require("../../controller/api/user.controller"));
const uploadCloud = __importStar(require("../../middleware/uploadCloudinary.middleware"));
const upload = (0, multer_1.default)();
router.get('/info', userController.getInfo);
router.post('/login', userController.login);
router.post('/register', userController.register);
router.post('/password/forgot', userController.passwordForgot);
router.post('/password/otp', userController.otp);
router.post('/password/reset', userController.passwordReset);
router.post('/update/avatar', upload.single("img"), uploadCloud.uploadSingle, userController.updateAvatar);
router.post('/update/info', userController.updateInfo);
router.post('/comment', userController.comment);
router.get('/list/notification', userController.listNotification);
exports.default = router;
