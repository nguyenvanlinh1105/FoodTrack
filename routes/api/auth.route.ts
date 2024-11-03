import express,{Router} from 'express';
const router : Router = express.Router();

import * as authController from '../../controller/api/auth.controller';

import * as middlewareAuth from '../../middleware/API/checkAuth.middleware';

router.post('/login',authController.login);
router.post('/refresh',authController.requestRefreshToken);

router.get('/users',middlewareAuth.verifyToken,authController.users);
router.patch('/delete/:id',middlewareAuth.verifyToken,middlewareAuth.verifyTokenAndAdminAuth,authController.deleteUser);

export default router;