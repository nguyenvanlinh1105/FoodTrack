import express,{Router} from 'express';
const router : Router = express.Router();

import * as foodController from '../../controller/api/food.controller';

router.get('/bargain',foodController.bargain);
router.get('/bestseller',foodController.bestseller);
router.get('/new',foodController.newFood);
router.get('/list',foodController.listFood);
router.post('/love',foodController.loveFood);
router.post('/unlove',foodController.unloveFood);
router.get('/detail',foodController.detailFood);
router.get('/love/list',foodController.listLoveFood);
router.get('/list/drink',foodController.listDrink);
router.get('/list/order',foodController.listOrder);
router.get('/list/comment',foodController.listComment);
router.get('/search',foodController.search);
export default router;