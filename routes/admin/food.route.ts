import express,{Router} from 'express';
import multer from 'multer';
const router : Router = express.Router();

import * as uploadCloud from '../../middleware/uploadCloudinary.middleware';
import * as foodController from '../../controller/admin/food.controller';

const upload = multer();

router.get('/',foodController.index);
router.get('/create', foodController.createPage);
router.get('/detail/:slug',foodController.detail);
router.get('/edit/:slug',foodController.editPage);
router.patch('/edit/:slug',upload.array('images', 4),uploadCloud.uploadFields,foodController.edit);
router.post('/create',upload.array('images', 4),uploadCloud.uploadFields,foodController.create);
router.patch('/delete/:slug',foodController.deleteFood);
router.get('/change-status/:slug/:status',foodController.changeStatus);

export default router;