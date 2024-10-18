import { Model, ModelStatic } from 'sequelize';

const generateNextId = async (model: ModelStatic<Model>, prefix: string): Promise<string> => {
    const idFieldName = `id${model.name}`;
    const records = await model.findAll({ attributes: [idFieldName], raw: true });
    const ids = records.map(record => record[idFieldName]);
    let maxId = 0;

    ids.forEach(id => {
        const numberPart = parseInt(id.substring(prefix.length)); // Lấy phần số sau prefix
        if (numberPart > maxId) {
            maxId = numberPart;
        }
    });
    const newIdNumber = maxId + 1;
    const newId = `${prefix}${newIdNumber.toString().padStart(3, '0')}`; // Đảm bảo có 3 chữ số
    
    return newId;
}

export default generateNextId;