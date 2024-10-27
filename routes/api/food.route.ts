import express,{Router} from 'express';
const router : Router = express.Router();

import * as foodController from '../../controller/api/food.controller';

router.get('/bargain',foodController.bargain);
router.get('/bestseller',foodController.bestseller);

export default router;