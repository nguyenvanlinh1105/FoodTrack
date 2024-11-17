import express,{Router} from 'express';
const router : Router = express.Router();

import * as categoryController from '../../controller/admin/category.controller';

router.get('/',categoryController.index);
router.get('/create', categoryController.createPage);
router.post('/create', categoryController.create);
router.get('/detail/:slug', categoryController.detailPage);
router.get('/edit/:slug', categoryController.editPage);
router.patch('/edit/:slug', categoryController.edit);
router.patch('/delete/:slug', categoryController.deleteCategory);
export default router;