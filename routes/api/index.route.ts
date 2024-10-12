//Router
import {Express} from 'express';

import userRouter from './user.route';

const routesAPI = (app:Express)=>{
    app.use("/user",userRouter);
}

export default routesAPI;