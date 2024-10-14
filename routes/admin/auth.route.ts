import express,{Router} from 'express';
const router : Router = express.Router();

import * as authController from '../../controller/admin/auth.controller';

router.get('/login',authController.loginPage);
router.post('/login',authController.login);
router.get('/logout',authController.logout);

export default router;