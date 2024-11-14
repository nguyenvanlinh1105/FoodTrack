"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const axios_1 = __importDefault(require("axios"));
const getMapEmbedUrl = (address) => __awaiter(void 0, void 0, void 0, function* () {
    const key = process.env.API_MAP_KEY;
    console.log(key);
    console.log(address);
    try {
        const response = yield axios_1.default.get('https://maps.googleapis.com/maps/api/geocode/json', {
            params: {
                address: address,
                key: process.env.API_MAP_KEY
            }
        });
        console.log(response);
        if (response.data.results.length > 0) {
            const { lat, lng } = response.data.results[0].geometry.location;
            const mapEmbedUrl = `https://www.google.com/maps/embed/v1/place?key=${process.env.API_MAP_KEY}&q=${lat},${lng}`;
            return mapEmbedUrl;
        }
        else {
            throw new Error('Không tìm thấy địa chỉ');
        }
    }
    catch (error) {
        console.error('Lỗi khi lấy URL bản đồ:', error);
        return null;
    }
});
exports.default = getMapEmbedUrl;
