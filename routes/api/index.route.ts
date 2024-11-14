//Router
import {Express} from 'express';

import userRouter from './user.route';
import foodRouter from './food.route';
const routesAPI = (app:Express)=>{
    app.use("/user",userRouter);
    app.use("/food",foodRouter);
}

export default routesAPI;