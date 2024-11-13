import express,{Router} from 'express';
const router : Router = express.Router();

import * as userController  from '../../controller/admin/customer.controller';

router.get('/',userController.pageCustomer);
// router.get('/create',staffController.createAdminPage);
// router.post('/create',staffController.createAdmin);
// router.get('/change-status/:token/:status',staffController.changeStatus);
export default router;