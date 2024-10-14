import express,{Router} from 'express';
const router : Router = express.Router();

import * as staffController  from '../../controller/admin/staff.controller';

router.get('/',staffController.pageStaff);

export default router;