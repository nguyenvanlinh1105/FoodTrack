import express,{Router} from 'express';
const router : Router = express.Router();

import * as chatController from '../../controller/admin/chat.controller';

router.get('/:IdRoom',chatController.index);
export default router;