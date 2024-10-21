import sequelize from "../config/database";
import { QueryTypes, Model, ModelStatic } from 'sequelize';
import { Request,Response } from 'express';
export const paginationStaff = async (req:Request,model: ModelStatic<Model>,limitItems:number=4,idUserCurrent:string)=>{ // Hàm phân trang
    //Phân trang 
    const pagination={
        currentPage:1,
        limitItems:limitItems,
        skip:0
    };

    if(req.query.page){
        pagination.currentPage=Number(req.query.page);
    }
    pagination.skip=(pagination.currentPage-1)*pagination.limitItems;// Công thức tính skip
    const countTotal= await sequelize.query(
        `SELECT COUNT(*) as total
        FROM ${model.tableName}
        WHERE idNguoiDung <> '${idUserCurrent}'
        AND   vaiTro IN ('VT001', 'VT003', 'VT004', 'VT005')  
        `,
        {
            type:QueryTypes.SELECT
        }
    );// Đếm tổng số sản phẩm
    const totalRecords=countTotal[0]['total'];
    pagination['totalPages']= Math.ceil(totalRecords/pagination.limitItems);// Công thức tính tổng số trang
    return pagination;
}
