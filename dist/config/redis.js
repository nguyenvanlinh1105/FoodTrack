"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const redisConnection = {
    host: process.env.REDIS_HOST,
    port: parseInt(process.env.REDIS_PORT, 10) || 6379,
    username: process.env.REDIS_USER,
    password: process.env.REDIS_PASSWORD,
    tls: {}
};
exports.default = redisConnection;
