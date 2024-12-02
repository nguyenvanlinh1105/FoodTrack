import express,{Router} from 'express';
const router : Router = express.Router();
import multer from 'multer';

import * as userController from '../../controller/api/user.controller';

//Middleware
import * as uploadCloud from '../../middleware/uploadCloudinary.middleware';
const upload = multer();

router.get('/info',userController.getInfo);
router.post('/login',userController.login);
router.post('/register',userController.register);
router.post('/password/forgot',userController.passwordForgot);
router.post('/password/otp',userController.otp);
router.post('/password/reset',userController.passwordReset);
router.post('/update/avatar',upload.single("img"),uploadCloud.uploadSingle,userController.updateAvatar);
router.post('/update/info',userController.updateInfo);
router.post('/comment',userController.comment);

export default router;