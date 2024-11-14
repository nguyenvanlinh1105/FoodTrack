"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const auth_route_1 = __importDefault(require("./auth.route"));
const dashboard_route_1 = __importDefault(require("./dashboard.route"));
const staff_route_1 = __importDefault(require("./staff.route"));
const category_route_1 = __importDefault(require("./category.route"));
const food_route_1 = __importDefault(require("./food.route"));
const customer_route_1 = __importDefault(require("./customer.route"));
const checkAuth_middleware_1 = __importDefault(require("../../middleware/checkAuth.middleware"));
const routesAdmin = (app) => {
    const PATH = app.locals.prefixAdmin;
    app.use(`/${PATH}`, auth_route_1.default);
    app.use(`/${PATH}/dashboard`, checkAuth_middleware_1.default, dashboard_route_1.default);
    app.use(`/${PATH}/management/staff`, checkAuth_middleware_1.default, staff_route_1.default);
    app.use(`/${PATH}/management/category`, checkAuth_middleware_1.default, category_route_1.default);
    app.use(`/${PATH}/management/food`, checkAuth_middleware_1.default, food_route_1.default);
    app.use(`/${PATH}/management/customer`, checkAuth_middleware_1.default, customer_route_1.default);
};
exports.default = routesAdmin;
