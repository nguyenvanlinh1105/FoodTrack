import express,{Router} from 'express';
const router : Router = express.Router();

import * as staffController  from '../../controller/admin/staff.controller';

router.get('/',staffController.pageStaff);
router.get('/create',staffController.createAdminPage);
router.post('/create',staffController.createAdmin);
export default router;