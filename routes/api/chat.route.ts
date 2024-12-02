import express,{Router} from 'express';
const router : Router = express.Router();

import * as chatController from '../../controller/api/chat.controller';

router.get('/',chatController.chat);
export default router;