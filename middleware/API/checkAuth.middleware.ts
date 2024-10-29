import { Request,Response, NextFunction } from 'express';
import jwt from 'jsonwebtoken';
export const verifyToken= async(req: Request, res: Response, next: NextFunction) => {
    const token = req.headers.token;
    if(token){
        const accessToken=`${token}`.split(' ')[1];
        jwt.verify(accessToken,process.env.ACCESS_TOKEN_SECRET,(err,user)=>{
            if(err){
                console.log(err);
                res.status(403).json({message:'Token invalid'});
            }else{
                req['user']=user;
                next();
            }
        })
    }else{
        res.status(401).json({message:"You're not authenticated"});
    }
}
export const verifyTokenAndAdminAuth=async(req: Request, res: Response, next: NextFunction) =>{
    console.log(req['user']);
    if(req.params.id==req['user']['id'] || req['user']['role']=='Admin'){
        next();
    }
    else{
        res.status(403).json({message:"You're not authorized"});
    }
}