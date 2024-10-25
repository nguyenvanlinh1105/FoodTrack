import express,{Router} from 'express';
import multer from 'multer';
const router : Router = express.Router();

import * as uploadCloud from '../../middleware/uploadCloudinary.middleware';
import * as foodController from '../../controller/admin/food.controller';

const upload = multer();

router.get('/',foodController.index);
router.get('/create', foodController.createPage);
router.post('/create',upload.array('images', 4),uploadCloud.uploadFields,foodController.create);

export default router;