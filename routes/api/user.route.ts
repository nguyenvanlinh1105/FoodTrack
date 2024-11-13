import express,{Router} from 'express';
const router : Router = express.Router();

import * as userController from '../../controller/api/user.controller';

router.post('/login',userController.login);
router.post('/register',userController.register);
router.post('/password/forgot',userController.passwordForgot);
router.post('/password/otp',userController.otp);
router.post('/password/reset',userController.passwordReset);

export default router;