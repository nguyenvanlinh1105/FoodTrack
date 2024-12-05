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
import commentRoute from './comment.route';
//Middleware
import checkAuthMiddleware from '../../middleware/checkAuth.middleware';
import checkMessagesUnreadMiddleware from '../../middleware/checkMessagesUnread.middleware';

const routesAdmin = (app:Express)=>{
    const  PATH=app.locals.prefixAdmin;
    app.use(`/${PATH}`,authRoute);
    app.use(`/${PATH}/dashboard`,checkAuthMiddleware,checkMessagesUnreadMiddleware,dashboardRoute);
    app.use(`/${PATH}/management/staff`,checkAuthMiddleware,checkMessagesUnreadMiddleware,staffRoute);
    app.use(`/${PATH}/management/category`,checkAuthMiddleware,checkMessagesUnreadMiddleware,categoryRoute);
    app.use(`/${PATH}/management/food`,checkAuthMiddleware,checkMessagesUnreadMiddleware,foodRoute);
    app.use(`/${PATH}/management/customer`,checkAuthMiddleware,checkMessagesUnreadMiddleware,customerRoute);
    app.use(`/${PATH}/management/order`,checkAuthMiddleware,checkMessagesUnreadMiddleware,orderRoute);
    app.use(`/${PATH}/management/comment`,checkAuthMiddleware,checkMessagesUnreadMiddleware,commentRoute);
    app.use(`/${PATH}/chat`,checkAuthMiddleware,checkMessagesUnreadMiddleware,chatRoute);
}

export default routesAdmin;