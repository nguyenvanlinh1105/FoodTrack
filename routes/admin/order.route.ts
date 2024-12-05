import express,{Router} from 'express';
const router : Router = express.Router();

import * as orderController from '../../controller/admin/order.controller';

router.get('/',orderController.index);
router.get('/detail/:idDonHang',orderController.detailOrder);
router.patch('/deliver/:idDonHang',orderController.deliverOrder);
router.post('/notification/:idDonHang',orderController.notificationOrder);
export default router;