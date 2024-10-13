import express,{Router} from 'express';
const router : Router = express.Router();

import * as dashboardController from '../../controller/admin/dashboard.controller';

router.get('/',dashboardController.dashboard);

export default router;