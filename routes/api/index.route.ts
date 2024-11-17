//Router
import {Express} from 'express';

import userRouter from './user.route';
import foodRouter from './food.route';
import order from './order.route';
const routesAPI = (app:Express)=>{
    app.use("/user",userRouter);
    app.use("/food",foodRouter);
    app.use("/order",order);
}

export default routesAPI;