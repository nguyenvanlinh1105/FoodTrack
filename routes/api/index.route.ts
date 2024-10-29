//Router
import {Express} from 'express';

import userRouter from './user.route';
import foodRouter from './food.route';
import authRouter from './auth.route';
const routesAPI = (app:Express)=>{
    app.use("/user",userRouter);
    app.use("/food",foodRouter);
    app.use("/auth",authRouter);
}

export default routesAPI;