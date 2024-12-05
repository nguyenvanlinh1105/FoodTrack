import express,{Router} from 'express';
const router : Router = express.Router();

import * as orderController from '../../controller/api/order.controller';

router.post('/new',orderController.newOrder);
router.get('/detail',orderController.detailOrder);
router.post ('/cancel',orderController.cancelFood);
router.post('/confirm',orderController.confirm);
router.post('/deny',orderController.deny);
router.get('/list/unfinished',orderController.listUnfinished);
router.get('/list/deny',orderController.listDeny);
router.post('/update/quantity',orderController.updateQuantity);
router.post('/reorder',orderController.reorder);
export default router;