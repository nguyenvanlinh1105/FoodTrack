"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const user_route_1 = __importDefault(require("./user.route"));
const food_route_1 = __importDefault(require("./food.route"));
const order_route_1 = __importDefault(require("./order.route"));
const chat_route_1 = __importDefault(require("./chat.route"));
const routesAPI = (app) => {
    app.use("/user", user_route_1.default);
    app.use("/food", food_route_1.default);
    app.use("/order", order_route_1.default);
    app.use("/chat", chat_route_1.default);
};
exports.default = routesAPI;
