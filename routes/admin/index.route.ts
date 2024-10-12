//Router
import {Express} from 'express';

import authRoute from './auth.route';
const routesAdmin = (app:Express)=>{
    const  PATH=app.locals.prefixAdmin;
    app.use(`/${PATH}`,authRoute);
}

export default routesAdmin;