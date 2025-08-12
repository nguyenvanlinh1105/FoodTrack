# Tài liệu
[Tài liệu FoodTrack.docx](https://github.com/user-attachments/files/21729997/Tai.li.u.FoodTrack.docx)

# 🥗 FoodTrack – Ứng dụng bán đồ ăn và đồ uống trực tuyến

**FoodTrack** là một ứng dụng Android cho phép người dùng đặt món ăn, đồ uống nhanh chóng và tiện lợi. Bên cạnh đó, ứng dụng còn cung cấp hệ thống quản lý đơn hàng trực tuyến dành cho các quán ăn, nhà hàng truyền thống.

## 🚀 Tính năng chính

### 👤 Người dùng:
- Đăng ký, đăng nhập, khôi phục mật khẩu (OTP)
- Tìm kiếm món ăn, xem chi tiết sản phẩm
- Thêm món vào giỏ hàng hoặc danh mục yêu thích
- Thanh toán với nhiều hình thức, hỗ trợ tích điểm
- Theo dõi trạng thái đơn hàng
- Xem lịch sử đơn hàng, mua lại đơn đã hủy
- Đánh giá và xem đánh giá sản phẩm
- Nhận thông báo hệ thống: đặt hàng, hủy đơn, cập nhật mật khẩu, v.v.
- Chat hỗ trợ trực tuyến 24/7 với nhân viên
- Giao diện đơn giản, dễ sử dụng

### 🛠️ Quản trị viên (Admin):
- Thống kê đơn hàng và doanh thu
- Quản lý tài khoản người dùng
- Quản lý danh sách món ăn/thức uống (thêm, sửa, xóa)
- Xử lý đơn hàng và cập nhật trạng thái
- Quản lý bình luận, duyệt/xóa
- Gửi thông báo cho người dùng
- Hỗ trợ chat với khách hàng

---

## 🧰 Công nghệ sử dụng

- **Android Studio** (Java)
- **MySQL** (kết nối qua AWS RDS)
- **Retrofit** – Giao tiếp API
- **Gson** – Xử lý JSON
- **Socket.io** – Chat trực tuyến
- **SharedPreferences** – Lưu dữ liệu cục bộ
- **Render** – Máy chủ build và backend
- **Android Emulator SDK 31 (Android 13)** – Kiểm thử ứng dụng

---

## 📱 Phạm vi ứng dụng

### Đối tượng sử dụng:
- Sinh viên, nhân viên văn phòng, người nội trợ
- Các quán ăn, nhà hàng truyền thống muốn chuyển đổi số

### Thiết bị hỗ trợ:
- Thiết bị Android SDK 28 trở lên (Android 8+)

---

## 👨‍💻 Nhóm phát triển

| Họ tên              | MSSV             | Vai trò       |
|---------------------|------------------|----------------|
| Nguyễn Văn Linh     | 22115053122225   | Nhóm trưởng    |
| Phạm Lê Văn Huy     | 22115053122117   | Thành viên     |
| Nguyễn Ngọc Tú Anh  | 22115053122302   | Thành viên     |

> Giảng viên hướng dẫn: **ThS. Đỗ Phú Huy** – Trường Đại học Sư Phạm Kỹ Thuật, ĐH Đà Nẵng**

---

## 📸 Một số giao diện
- Trang đăng nhập / đăng ký
- <img width="372" height="848" alt="image" src="https://github.com/user-attachments/assets/9baeb339-410b-4e8c-ae9c-d8e4f0c3f8b2" />
  <img width="373" height="847" alt="image" src="https://github.com/user-attachments/assets/ffc9510e-b9af-4b7b-90fb-42740e9627dd" />

  - Giao diện chức năng chỉnh sửa thông tin tài khoản
  <img width="378" height="861" alt="image" src="https://github.com/user-attachments/assets/2ffe9550-d827-4943-888c-863ee3b11aaf" />
   <img width="241" height="551" alt="image" src="https://github.com/user-attachments/assets/16f5d53c-ccf7-4569-b60a-a1bfda5deb9c" />

  - Danh sách món ăn
  - <img width="241" height="551" alt="image" src="https://github.com/user-attachments/assets/0bf56035-d30c-4a8b-a2ed-43078f8b61b5" />
    <img width="241" height="550" alt="image" src="https://github.com/user-attachments/assets/fb53d17a-65f1-4e80-a175-fdec172dd5fb" />

- Giỏ hàng
- 
  <img width="242" height="551" alt="image" src="https://github.com/user-attachments/assets/58e8408e-8083-4f87-a5c5-046a9899dc35" />
  <img width="243" height="550" alt="image" src="https://github.com/user-attachments/assets/2de2399b-1505-49bb-83c9-ee0d6395f24a" />

  -Giao diện cho chức năng Tìm kiếm các món ăn, thức uống theo tên của sản phẩm
  
  <img width="241" height="550" alt="image" src="https://github.com/user-attachments/assets/26c19632-aa47-4200-8e8e-34691a591276" />
  <img width="241" height="551" alt="image" src="https://github.com/user-attachments/assets/03787351-de1c-437c-98f9-5ad9871306bf" />

- Giao diện cho chức năng Thêm, xóa món ăn, thức uống vào danh mục yêu thích

<img width="380" height="864" alt="image" src="https://github.com/user-attachments/assets/de634498-d74b-41e4-97d3-34dac348e0b3" />
<img width="380" height="864" alt="image" src="https://github.com/user-attachments/assets/1a3f3c44-0c11-4589-9c73-f1906c09a558" />

- Giao diện cho chức năng Thanh toán sử dụng tích điểm
- 
<img width="242" height="552" alt="image" src="https://github.com/user-attachments/assets/7fbec329-8bf6-4bd7-aa30-d57ebe7e5aec" />
<img width="242" height="552" alt="image" src="https://github.com/user-attachments/assets/b9829674-aab0-4936-8dea-5bb9ba9ea867" />



- Theo dõi đơn hàng
- 
  <img width="243" height="551" alt="image" src="https://github.com/user-attachments/assets/8a6b6857-8903-4e98-a824-fa4269f75162" />
  <img width="242" height="552" alt="image" src="https://github.com/user-attachments/assets/fdb90b0e-ddb0-4e72-a80e-46b6116e5ac4" />

- Giao diện quản trị Admin
- <img width="979" height="493" alt="image" src="https://github.com/user-attachments/assets/673c693c-ea63-4dca-8d84-e22c9c926705" />

