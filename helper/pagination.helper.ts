import { Op,QueryTypes, Model, ModelStatic } from 'sequelize';
import { Request } from 'express';
import NguoiDung from '../model/NguoiDung.model';

export const paginationStaff = async (req:Request,limitItems:number=4,idUserCurrent:string)=>{ // Hàm phân trang
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

    const countTotal = await NguoiDung.count({
        where: {
            idNguoiDung: { [Op.ne]: idUserCurrent }, // idNguoiDung <> idUserCurrent
            vaiTro: { [Op.in]: ['VT001', 'VT003', 'VT004', 'VT005'] }, // vaiTro IN (...)
            deleted:0
        }
    });
    pagination['totalPages']= Math.ceil(countTotal/pagination.limitItems);// Công thức tính tổng số trang
    return pagination;
}


export const paginationCustomer = async (req:Request,limitItems:number=4)=>{ // Hàm phân trang
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

    const countTotal = await NguoiDung.count({
        where: {
            vaiTro:'VT002',
            deleted:0
        }
    });
    pagination['totalPages']= Math.ceil(countTotal/pagination.limitItems);// Công thức tính tổng số trang
    return pagination;
}

export const  paginationGeneral = async (req:Request,limitItems:number=4,table:ModelStatic<Model<any, any>>)=>{ // Hàm phân trang
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
    if(table.name==='BinhLuanSanPham'){
        const countTotal = await table.count({});
        pagination['totalPages']= Math.ceil(countTotal/pagination.limitItems);// Công thức tính tổng số trang
        return pagination;
    }else{
        const countTotal = await table.count({
            where:{
                deleted:0
            }
        });
        pagination['totalPages']= Math.ceil(countTotal/pagination.limitItems);// Công thức tính tổng số trang
        return pagination;
    }
}
