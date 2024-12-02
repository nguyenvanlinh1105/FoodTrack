//Router
import {Express} from 'express';

import authRoute from './auth.route';
import dashboardRoute from './dashboard.route';
import staffRoute from './staff.route';
import categoryRoute from './category.route';
import foodRoute from './food.route';
import customerRoute from './customer.route';
import orderRoute from './order.route';
import chatRoute from './chat.route';
//Middleware
import checkAuthMiddleware from '../../middleware/checkAuth.middleware';

const routesAdmin = (app:Express)=>{
    const  PATH=app.locals.prefixAdmin;
    app.use(`/${PATH}`,authRoute);
    app.use(`/${PATH}/dashboard`,checkAuthMiddleware,dashboardRoute);
    app.use(`/${PATH}/management/staff`,checkAuthMiddleware,staffRoute);
    app.use(`/${PATH}/management/category`,checkAuthMiddleware,categoryRoute);
    app.use(`/${PATH}/management/food`,checkAuthMiddleware,foodRoute);
    app.use(`/${PATH}/management/customer`,checkAuthMiddleware,customerRoute);
    app.use(`/${PATH}/management/order`,checkAuthMiddleware,orderRoute);
    app.use(`/${PATH}/chat`,checkAuthMiddleware,chatRoute);
}

export default routesAdmin;