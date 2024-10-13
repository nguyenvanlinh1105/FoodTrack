//Router
import {Express} from 'express';

import authRoute from './auth.route';
import dashboardRoute from './dashboard.route';

const routesAdmin = (app:Express)=>{
    const  PATH=app.locals.prefixAdmin;
    app.use(`/${PATH}`,authRoute);
    app.use(`/${PATH}/dashboard`,dashboardRoute);
}

export default routesAdmin;