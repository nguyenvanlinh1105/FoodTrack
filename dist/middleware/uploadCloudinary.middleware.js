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
exports.uploadFields = exports.uploadSingle = void 0;
const streamUpload_helper_1 = __importDefault(require("../helper/streamUpload.helper"));
const uploadSingle = (req, res, next) => __awaiter(void 0, void 0, void 0, function* () {
    if (req["file"]) {
        const uploadToCloudinary = (buffer) => __awaiter(void 0, void 0, void 0, function* () {
            let result = yield (0, streamUpload_helper_1.default)(buffer);
            req.body[req["file"].fieldname] = result["url"];
        });
        yield uploadToCloudinary(req["file"].buffer);
        next();
    }
    else {
        next();
    }
});
exports.uploadSingle = uploadSingle;
const uploadFields = (req, res, next) => __awaiter(void 0, void 0, void 0, function* () {
    if (req['files']) {
        try {
            req.body['images'] = [];
            for (const file of req['files']) {
                let result = yield (0, streamUpload_helper_1.default)(file.buffer);
                req.body['images'].push(result['url']);
            }
            next();
        }
        catch (error) {
            console.log(error);
        }
    }
});
exports.uploadFields = uploadFields;
