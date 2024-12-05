import express,{Router} from 'express';
const router : Router = express.Router();

import * as commentController from '../../controller/admin/comment.controller';

router.get('/',commentController.index);
router.patch('/approve/:id',commentController.approveComment);
router.patch('/disapprove/:id',commentController.disapprove);

export default router;