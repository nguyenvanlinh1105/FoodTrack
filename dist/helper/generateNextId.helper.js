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
Object.defineProperty(exports, "__esModule", { value: true });
const generateNextId = (model, prefix) => __awaiter(void 0, void 0, void 0, function* () {
    const idFieldName = `id${model.name}`;
    const records = yield model.findAll({ attributes: [idFieldName], raw: true });
    const ids = records.map(record => record[idFieldName]);
    let maxId = 0;
    ids.forEach(id => {
        const numberPart = parseInt(id.substring(prefix.length));
        if (numberPart > maxId) {
            maxId = numberPart;
        }
    });
    const newIdNumber = maxId + 1;
    const newId = `${prefix}${newIdNumber.toString().padStart(3, '0')}`;
    return newId;
});
exports.default = generateNextId;
