import { Request,Response,NextFunction } from "express";
import streamUpload from '../helper/streamUpload.helper';

export const uploadSingle= async (req:Request,res:Response,next:NextFunction)=>{
    
    if(req["file"]){
      const uploadToCloudinary= async (buffer)=>{
          let result= await streamUpload(buffer);
          req.body[req["file"].fieldname]=result["url"];
      }
      await uploadToCloudinary(req["file"].buffer);
      next();
    }
    else{
      next();
    } 

}
export const uploadFields=async(req:Request,res:Response,next:NextFunction)=>{
  if(req['files']){
    try{
      req.body['images']=[];
      for(const file of req['files']){
        let result=await streamUpload(file.buffer);
        req.body['images'].push(result['url']);
      }
      next();
    }catch(error) {
      console.log(error);
    }
  }
}