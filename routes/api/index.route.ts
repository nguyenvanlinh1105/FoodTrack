//Router
import {Express} from 'express';

import userRouter from './user.route';
import foodRouter from './food.route';
import orderRouter from './order.route';
import chatRouter from './chat.route';

const routesAPI = (app:Express)=>{
    app.use("/user",userRouter);
    app.use("/food",foodRouter);
    app.use("/order",orderRouter);
    app.use("/chat",chatRouter);
}

export default routesAPI;