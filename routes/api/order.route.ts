import express,{Router} from 'express';
const router : Router = express.Router();

import * as orderController from '../../controller/api/order.controller';

router.post('/new',orderController.newOrder);

export default router;