import express,{Router} from 'express';
const router : Router = express.Router();

import * as userController from '../../controller/api/user.controller';

router.post('/login',userController.login);
router.post('/register',userController.register);

export default router;