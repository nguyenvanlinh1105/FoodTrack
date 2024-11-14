import express,{Router} from 'express';
const router : Router = express.Router();

import * as customerController  from '../../controller/admin/customer.controller';

router.get('/',customerController.pageCustomer);
router.get('/detail/:token',customerController.detailCustomerPage);
router.patch('/delete/:token',customerController.deleteCustomer);
router.get('/change-status/:token/:status',customerController.changeStatus);
export default router;