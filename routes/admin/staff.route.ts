import express,{Router} from 'express';
const router : Router = express.Router();

import * as staffController  from '../../controller/admin/staff.controller';

router.get('/',staffController.pageStaff);
router.get('/create',staffController.createAdminPage);
router.post('/create',staffController.createAdmin);
router.get('/detail/:token',staffController.detailStaffPage);
router.patch('/detail/:token',staffController.updateStaff);
router.patch('/delete/:token',staffController.deleteStaff);
router.get('/change-status/:token/:status',staffController.changeStatus);
export default router;