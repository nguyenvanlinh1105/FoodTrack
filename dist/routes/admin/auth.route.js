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
const multer_1 = __importDefault(require("multer"));
const router = express_1.default.Router();
const authController = __importStar(require("../../controller/admin/auth.controller"));
const uploadCloud = __importStar(require("../../middleware/uploadCloudinary.middleware"));
const checkAuth_middleware_1 = __importDefault(require("../../middleware/checkAuth.middleware"));
const checkMessagesUnread_middleware_1 = __importDefault(require("../../middleware/checkMessagesUnread.middleware"));
const upload = (0, multer_1.default)();
router.get('/login', authController.loginPage);
router.post('/login', authController.login);
router.get('/logout', authController.logout);
router.get('/password/forgot', authController.passwordForgotPage);
router.post('/password/forgot', authController.passwordForgot);
router.get('/password/otp', authController.passwordOTPPage);
router.post('/password/otp', authController.otp);
router.get('/password/reset', authController.passwordResetPage);
router.post('/password/reset', authController.passwordReset);
router.get('/profile', checkAuth_middleware_1.default, checkMessagesUnread_middleware_1.default, authController.profilePage);
router.patch('/profile', checkAuth_middleware_1.default, checkMessagesUnread_middleware_1.default, upload.single("avatar"), uploadCloud.uploadSingle, authController.profileUpdate);
router.patch('/update/messages', checkAuth_middleware_1.default, checkMessagesUnread_middleware_1.default, authController.updateMessages);
exports.default = router;
