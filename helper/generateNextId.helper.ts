import { Model, ModelStatic } from 'sequelize';

const generateNextId = async (model: ModelStatic<Model>, prefix: string): Promise<string> => {
    // Lấy tên thuộc tính ID theo quy ước (có thể thay đổi nếu cần)
    const idFieldName = `id${model.name}`;

    const records = await model.findAll({ attributes: [idFieldName], raw: true });
    
    // Tạo một mảng chứa tất cả các id
    const ids = records.map(record => record[idFieldName]);
    
    // Tìm id lớn nhất
    let maxId = 0;

    ids.forEach(id => {
        const numberPart = parseInt(id.substring(prefix.length)); // Lấy phần số sau prefix
        if (numberPart > maxId) {
            maxId = numberPart;
        }
    });
    
    // Tạo id mới
    const newIdNumber = maxId + 1;
    const newId = `${prefix}${newIdNumber.toString().padStart(3, '0')}`; // Đảm bảo có 3 chữ số
    
    return newId;
}

export default generateNextId;