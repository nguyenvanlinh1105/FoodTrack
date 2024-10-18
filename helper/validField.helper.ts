export const isValidEmail=(email:string):boolean=>{
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return regex.test(email);
};
export const isValidPhone=(phone:string):boolean=>{
    const phoneRegex = /^\d{10}$/; 
    return phoneRegex.test(phone);
};
