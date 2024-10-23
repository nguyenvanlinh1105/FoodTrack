//Router
import {Express} from 'express';

import authRoute from './auth.route';
import dashboardRoute from './dashboard.route';
import staffRoute from './staff.route';
import categoryRoute from './category.route';

//Middleware
import checkAuthMiddleware from '../../middleware/checkAuth.middleware';

const routesAdmin = (app:Express)=>{
    const  PATH=app.locals.prefixAdmin;
    app.use(`/${PATH}`,authRoute);
    app.use(`/${PATH}/dashboard`,checkAuthMiddleware,dashboardRoute);
    app.use(`/${PATH}/management/staff`,checkAuthMiddleware,staffRoute);
    app.use(`/${PATH}/management/category`,checkAuthMiddleware,categoryRoute);
}

export default routesAdmin;