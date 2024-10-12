"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = __importDefault(require("express"));
const dotenv_1 = __importDefault(require("dotenv"));
const cors_1 = __importDefault(require("cors"));
dotenv_1.default.config();
const database_1 = __importDefault(require("./config/database"));
const index_route_1 = __importDefault(require("./routes/index.route"));
const app = (0, express_1.default)();
const port = process.env.PORT || 3000;
app.use((0, cors_1.default)());
database_1.default;
(0, index_route_1.default)(app);
app.listen(port, () => {
    console.log(`Server is running at http://localhost:${port}`);
});
