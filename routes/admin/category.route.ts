import express,{Router} from 'express';
const router : Router = express.Router();

import * as categoryController from '../../controller/admin/category.controller';

router.get('/',categoryController.index);
router.get('/create', categoryController.createPage);
router.post('/create', categoryController.create);

export default router;