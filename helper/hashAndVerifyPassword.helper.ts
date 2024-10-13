import bcrypt from 'bcrypt';//Nhúng module bcrypt để mã hóa mật khẩu
export const hashPassword = (password: string): string => {
    const salt = bcrypt.genSaltSync(10);
    const newPassword = bcrypt.hashSync(password, salt);
    return newPassword;
};
export const verifyPassword= (inputPassword: string, hashedPassword: string) :boolean=>{
    return bcrypt.compareSync(inputPassword, hashedPassword);
}
