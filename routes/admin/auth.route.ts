import express,{Router} from 'express';
import multer from 'multer';
const router : Router = express.Router();

import * as authController from '../../controller/admin/auth.controller';

//Middleware
import * as uploadCloud from '../../middleware/uploadCloudinary.middleware';
import checkAuthMiddleware from '../../middleware/checkAuth.middleware';
import checkMessagesUnreadMiddleware from '../../middleware/checkMessagesUnread.middleware';
const upload = multer();

router.get('/login',authController.loginPage);
router.post('/login',authController.login);
router.get('/logout',authController.logout);
router.get('/password/forgot',authController.passwordForgotPage);
router.post('/password/forgot',authController.passwordForgot);
router.get('/password/otp',authController.passwordOTPPage);
router.post('/password/otp',authController.otp);
router.get('/password/reset',authController.passwordResetPage);
router.post('/password/reset',authController.passwordReset);
router.get('/profile',checkAuthMiddleware,checkMessagesUnreadMiddleware,authController.profilePage);
router.patch('/profile',checkAuthMiddleware,checkMessagesUnreadMiddleware,upload.single("avatar"),uploadCloud.uploadSingle,authController.profileUpdate);
router.patch('/update/messages',checkAuthMiddleware,checkMessagesUnreadMiddleware,authController.updateMessages);

export default router;