import TinNhan from "../model/TinNhan.model";

export const saveChat =async (idPhongChat:string,noiDung:string, images: string = "",thoiGianTao:Date,Gui:string) => {
    const data={
        idPhongChat: idPhongChat,
        noiDung: noiDung,
        thoiGianTao: thoiGianTao,
        Gui: Gui
    }
    if(images){
        data['images']=images;
    }
    await TinNhan.create(data);
}
