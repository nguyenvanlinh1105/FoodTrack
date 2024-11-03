import express,{Router} from 'express';
const router : Router = express.Router();

import * as authController from '../../controller/admin/auth.controller';

router.get('/login',authController.loginPage);
router.post('/login',authController.login);
router.get('/logout',authController.logout);
router.get('/password/forgot',authController.passwordForgotPage);
router.post('/password/forgot',authController.passwordForgot);
router.get('/password/otp',authController.passwordOTPPage);
router.post('/password/otp',authController.otp);
router.get('/password/reset',authController.passwordResetPage);
router.post('/password/reset',authController.passwordReset);

export default router;