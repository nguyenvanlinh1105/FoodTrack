import TinNhan from "../model/TinNhan.model";

export const saveChat =async (idPhongChat:string,noiDung:string, images: string = "",thoiGianTao:Date,Gui:string,tinhTrang:number = 1) => {
    const data={
        idPhongChat: idPhongChat,
        noiDung: noiDung,
        thoiGianTao: thoiGianTao,
        Gui: Gui,
        tinhTrang: tinhTrang,
    }
    if(images){
        data['images']=images;
    }
    await TinNhan.create(data);
}
