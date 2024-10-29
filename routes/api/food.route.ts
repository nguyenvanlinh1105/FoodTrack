import express,{Router} from 'express';
const router : Router = express.Router();

import * as foodController from '../../controller/api/food.controller';

router.get('/bargain',foodController.bargain);
router.get('/bestseller',foodController.bestseller);
router.get('/new',foodController.newFood);
router.get('/list',foodController.listFood);
export default router;