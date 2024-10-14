//Router
import {Express} from 'express';

import authRoute from './auth.route';
import dashboardRoute from './dashboard.route';

//Middleware
import checkAuthMiddleware from '../../middleware/checkAuth.middleware';

const routesAdmin = (app:Express)=>{
    const  PATH=app.locals.prefixAdmin;
    app.use(`/${PATH}`,authRoute);
    app.use(`/${PATH}/dashboard`,checkAuthMiddleware,dashboardRoute);
}

export default routesAdmin;