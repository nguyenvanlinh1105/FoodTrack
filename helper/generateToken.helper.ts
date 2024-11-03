import jwt from 'jsonwebtoken';

// Interface dữ liệu của người dùng
interface NguoiDung {
  id: string;
  role: string;
}

// Hàm tạo access token với kiểu dữ liệu rõ ràng
export const generateAccessToken = (user: NguoiDung): string => {
  return jwt.sign(
    {
      id: user.id,
      role: user.role,
    },
    process.env.ACCESS_TOKEN_SECRET as string, // Chuyển thành string để tránh lỗi
    { expiresIn: '40s' }
  );
};

export const generateRefreshToken = (user: NguoiDung): string => {
    return jwt.sign({
        id:user.id,
        role:user.role,
    },process.env.REFRESH_TOKEN_SECRET,{expiresIn:'365d'})
  };