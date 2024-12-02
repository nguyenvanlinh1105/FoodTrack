//Ẩn alert
const alertSuccess = document.querySelector('[show-alert]');
if (alertSuccess) {
    const time = Number(alertSuccess.getAttribute('show-alert')) || 3000;
    setTimeout(() => {
        alertSuccess.classList.add('hidden');
    }, time)
}

const staffForm = document.getElementById('staffForm');
const categoryForm = document.getElementById('categoryForm');
const foodForm = document.getElementById('foodForm');
const arrayForm = [];
if(staffForm ){
    arrayForm.push(staffForm);
}
if(categoryForm){
    arrayForm.push(categoryForm);
}

if(foodForm){
    const upload = new FileUploadWithPreview.FileUploadWithPreview('upload-images',{
        multiple:true,
        maxFileCount:4,
    });
    const inputImages=foodForm.querySelector('#file-upload-with-preview-upload-images');
    inputImages.setAttribute('name', 'images');

    foodForm.addEventListener('submit', async function (event) {
        event.preventDefault();

        if (typeof tinymce !== 'undefined') {
            tinymce.triggerSave();
        }//  Đồng bộ dữ liệu với textarea của tinymce

        

        const form = event.target;
        const formData = new FormData(form);
        const data = Object.fromEntries(formData.entries());

        upload.cachedFileArray.forEach((file, index) => {
            formData.append('images', file);
        });

        // Ẩn tất cả thông báo lỗi trước khi kiểm tra
        const errorAlerts = form.querySelectorAll('.invalid-feedback');
        errorAlerts.forEach(alert => alert.classList.remove('active'));

        let hasError = false; // Đánh dấu nếu có lỗi

        // Kiểm tra từng trường dữ liệu
        for (const [key, value] of Object.entries(data)) {
            if (!value) { // Nếu trường trống
                const errorAlert = form.querySelector(`.invalid-feedback-${key}-missed`);
                if (errorAlert) {
                    errorAlert.classList.add('active'); // Hiển thị thông báo lỗi
                }
                hasError = true;
            }
        }
        if (hasError===true) return;

        try {
            const response = await fetch(form.action, {
                method: 'POST',
                body: formData
            })
            .then(async (response) => {
                const result = await response.json(); 
                if (response.status === 404) {
                    await Swal.fire({
                        icon: "error",
                        title: "Lỗi xảy ra",
                        text: result.message,
                    });
                    location.reload();
                } else if (response.status === 500) {
                    await Swal.fire({
                        icon: "error",
                        title: "Lỗi hệ thống",
                        text: "Đã xảy ra lỗi khi tạo danh mục.",
                    });
                    location.reload();
                } else {
                    await Swal.fire({
                        position: "center",
                        icon: "success",
                        title: result.message,
                        showConfirmButton: false,
                        timer: 1500
                    });
                    location.reload();
                }
            })
        } catch (error) {
            await Swal.fire({
                icon: "error",
                title: "Lỗi xảy ra",
                text: error.message,
            });
            location.reload();
        }
    });

    // Lắng nghe sự kiện thay đổi input để ẩn lỗi khi nhập lại
    const inputs = foodForm.querySelectorAll('input, select');
    inputs.forEach(input => {
        input.addEventListener('input', () => {
            const errorAlert = input.closest('.col-md-12')?.querySelector('.invalid-feedback.active');
            if (errorAlert) {
                errorAlert.classList.remove('active');
            }
        });
    });
}


if(arrayForm.length>0){
    arrayForm.forEach(formElement => {
        formElement.addEventListener('submit', async function (event) {
            event.preventDefault();

            if (typeof tinymce !== 'undefined') {
                tinymce.triggerSave();
            }//  Đồng bộ dữ liệu với textarea của tinymce
            const form = event.target;
            const formData = new FormData(form);
            const data = Object.fromEntries(formData.entries());
    
            // Ẩn tất cả thông báo lỗi trước khi kiểm tra
            const errorAlerts = form.querySelectorAll('.invalid-feedback');
            errorAlerts.forEach(alert => alert.classList.remove('active'));
    
            let hasError = false; // Đánh dấu nếu có lỗi
    
            // Kiểm tra từng trường dữ liệu
            for (const [key, value] of Object.entries(data)) {
                if (!value) { // Nếu trường trống
                    const errorAlert = form.querySelector(`.invalid-feedback-${key}-missed`);
                    if (errorAlert) {
                        errorAlert.classList.add('active'); // Hiển thị thông báo lỗi
                    }
                    hasError = true;
                }
            }
            if (hasError===true) return;
            try {
                fetch(form.action, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(data)
                })
                .then(async (response) => {
                    const result = await response.json(); 
                    if (response.status === 404) {
                        await Swal.fire({
                            icon: "error",
                            title: "Lỗi xảy ra",
                            text: result.message,
                        });
                        location.reload();
                    } else if (response.status === 500) {
                        await Swal.fire({
                            icon: "error",
                            title: "Lỗi hệ thống",
                            text: "Đã xảy ra lỗi khi tạo danh mục.",
                        });
                        location.reload();
                    } else {
                        await Swal.fire({
                            position: "center",
                            icon: "success",
                            title: result.message,
                            showConfirmButton: false,
                            timer: 1500
                        });
                        location.reload();
                    }
                })
                
            } catch (error) {
                await Swal.fire({
                    icon: "error",
                    title: "Lỗi xảy ra",
                    text: error.message,
                });
                location.reload();
            }
        });
    
        // Lắng nghe sự kiện thay đổi input để ẩn lỗi khi nhập lại
        const inputs = formElement.querySelectorAll('input, select');
        inputs.forEach(input => {
            input.addEventListener('input', () => {
                const errorAlert = input.closest('.col-md-12')?.querySelector('.invalid-feedback.active');
                if (errorAlert) {
                    errorAlert.classList.remove('active');
                }
            });
        });
    })
}


const userAdmin=document.querySelector('[userAdminAvatar]')
const userAdminLi=document.querySelector('[userAdminLi]');
if(userAdmin){
    userAdmin.addEventListener('click',(e)=>{
        e.preventDefault();
        const showLiItem=userAdminLi.querySelector('[userAdminDropdown]');
        showLiItem.classList.toggle('show');
    })
}

function enableEmailInput() {
    document.getElementById('email').removeAttribute('disabled'); // Bỏ thuộc tính disabled trước khi gửi
}

$(document).ready(function() {
    $('#dataTable').DataTable({
        language: {
            emptyTable: "Chưa có dữ liệu",
            zeroRecords: "Không tìm thấy",
            search: "",
            searchPlaceholder: 'Tìm kiếm',
            loadingRecords: "Loading...",
        },
        "width": "100%",
        lengthChange: false,
        paging: false,
        pageLength: 4,
        info: false,
        searching: true,
        ordering: true,
        stateSave: false, // Tắt lưu trạng thái
        columnDefs: [
            { 
                targets: -1,       
                orderable: false  
            }
        ]
    });
});

const uploadImage=document.querySelector('[upload-image]');
if(uploadImage){
    const inputImage=uploadImage.querySelector('[upload-image-input]');
    const previewImage=uploadImage.querySelector('[upload-image-preview]');
    if (inputImage && previewImage){
        inputImage.addEventListener('change',(e)=>{
            const [file]=inputImage.files;
            if(file){
                previewImage.src=URL.createObjectURL(file);
            }
        })
    }
}

const deleteStaffButtons=document.querySelectorAll('[btn-delete-staff]');
if(deleteStaffButtons.length >0){
    deleteStaffButtons.forEach((button)=>{
        button.addEventListener('click',async (e)=>{
            e.preventDefault();
            Swal.fire({
                title: 'Bạn có muốn xóa tài khoản này ?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonText: 'Xác nhận',
                cancelButtonText: 'Hủy',
                confirmButtonColor: '#28A745',
                cancelButtonColor: '#d33',
                focusConfirm: false
            }).then((result) => {
                if(result.isConfirmed){
                    const token=button.getAttribute('btn-delete-staff');
                    fetch(`/admin/management/staff/delete/${token}`, {
                        method: 'PATCH',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                    })
                    .then(async (response) => response.json())
                    .then(async (data) => {
                        await Swal.fire({
                            position: "center",
                            icon: "success",
                            title: data.message,
                            showConfirmButton: false,
                            timer: 1500
                        });
                        location.reload();
                    })
                    .catch( async (error) => {
                        await Swal.fire({
                            icon: "error",
                            title: "Lỗi xảy ra",
                            text: error.message,
                        });
                        location.reload();
                    });
                }
            })
        })
    })
}

const deleteCustomerButtons=document.querySelectorAll('[btn-delete-customer]');
if(deleteCustomerButtons.length >0){
    deleteCustomerButtons.forEach((button)=>{
        button.addEventListener('click',async (e)=>{
            e.preventDefault();
            Swal.fire({
                title: 'Bạn có muốn xóa tài khoản này ?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonText: 'Xác nhận',
                cancelButtonText: 'Hủy',
                confirmButtonColor: '#28A745',
                cancelButtonColor: '#d33',
                focusConfirm: false
            }).then((result) => {
                if(result.isConfirmed){
                    const token=button.getAttribute('btn-delete-customer');
                    fetch(`/admin/management/customer/delete/${token}`, {
                        method: 'PATCH',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                    })
                    .then(async (response) => response.json())
                    .then(async (data) => {
                        await Swal.fire({
                            position: "center",
                            icon: "success",
                            title: data.message,
                            showConfirmButton: false,
                            timer: 1500
                        });
                        location.reload();
                    })
                    .catch( async (error) => {
                        await Swal.fire({
                            icon: "error",
                            title: "Lỗi xảy ra",
                            text: error.message,
                        });
                        location.reload();
                    });
                }
            })
        })
    })
}


const deleteCategoryButtons=document.querySelectorAll('[btn-delete-category]');
if(deleteCategoryButtons.length >0){
    deleteCategoryButtons.forEach((button)=>{
        button.addEventListener('click',async (e)=>{
            e.preventDefault();
            Swal.fire({
                title: 'Bạn có muốn xóa danh mục này ?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonText: 'Xác nhận',
                cancelButtonText: 'Hủy',
                confirmButtonColor: '#28A745',
                cancelButtonColor: '#d33',
                focusConfirm: false
            }).then((result) => {
                if(result.isConfirmed){
                    const slug=button.getAttribute('btn-delete-category');
                    fetch(`/admin/management/category/delete/${slug}`, {
                        method: 'PATCH',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                    })
                    .then(async (response) => response.json())
                    .then(async (data) => {
                        await Swal.fire({
                            position: "center",
                            icon: "success",
                            title: data.message,
                            showConfirmButton: false,
                            timer: 1500
                        });
                        location.reload();
                    })
                    .catch( async (error) => {
                        await Swal.fire({
                            icon: "error",
                            title: "Lỗi xảy ra",
                            text: error.message,
                        });
                        location.reload();
                    });
                }
            })
        })
    })
}

const deleteFoodButtons=document.querySelectorAll('[btn-delete-food]');
if(deleteFoodButtons.length >0){
    deleteFoodButtons.forEach((button)=>{
        button.addEventListener('click',async (e)=>{
            e.preventDefault();
            Swal.fire({
                title: 'Bạn có muốn xóa món ăn này ?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonText: 'Xác nhận',
                cancelButtonText: 'Hủy',
                confirmButtonColor: '#28A745',
                cancelButtonColor: '#d33',
                focusConfirm: false
            }).then((result) => {
                if(result.isConfirmed){
                    const slug=button.getAttribute('btn-delete-food');
                    fetch(`/admin/management/food/delete/${slug}`, {
                        method: 'PATCH',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                    })
                    .then(async (response) => response.json())
                    .then(async (data) => {
                        await Swal.fire({
                            position: "center",
                            icon: "success",
                            title: data.message,
                            showConfirmButton: false,
                            timer: 1500
                        });
                        location.reload();
                    })
                    .catch( async (error) => {
                        await Swal.fire({
                            icon: "error",
                            title: "Lỗi xảy ra",
                            text: error.message,
                        });
                        location.reload();
                    });
                }
            })
        })
    })
}


const chatInput = document.querySelector('[chat-input]');
if (chatInput) {
    const socket = io(); // Kết nối đến server Socket.IO
    const chatHistory=document.querySelector('.chat-history');
    const chatBox = document.querySelector('#chat-box');
    chatHistory.scrollTop = chatBox.scrollHeight;
    chatInput.addEventListener('keydown', async (e) => {
        if (e.key === 'Enter' && chatInput.value.trim()) {
            const id=chatInput.getAttribute('id-user');
            const message = chatInput.value.trim();
            socket.emit('ADMIN_SEND_MESSAGE', 
                { 
                    id:id,
                    message: message,
                }); // Phát sự kiện với nội dung message
            chatInput.value = ''; // Xóa input sau khi gửi
        }
    });
    socket.on('SERVER_RESEND_MESSAGE_ADMIN', (data) => {
        const { message, avatar, gender, time } = data;
    
        // Tạo phần tử li cho tin nhắn
        const messageItem = document.createElement('li');
        messageItem.classList.add('clearfix'); // Thêm class clearfix cho tin nhắn
    
        // Tạo phần tử chứa thông tin tin nhắn (thời gian và avatar)
        const messageData = document.createElement('div');
        messageData.classList.add('message-data', 'text-right','message-data-right'); // Thêm class cho phần thông tin tin nhắn
    
        // Tạo phần tử thời gian
        const timeElement = document.createElement('span');
        timeElement.classList.add('message-data-time');
        timeElement.style.marginRight = '10px'; // Khoảng cách bên phải
        timeElement.innerText = time; // Thêm thời gian vào phần tử
    
        // Tạo phần tử avatar
        const avatarElement = document.createElement('img');
        avatarElement.classList.add('message-data-avatar');
        avatarElement.src = avatar || (gender === 'Nam' ? '/admin/images/undraw_profile.svg' : '/admin/images/undraw_profile_3.svg'); // Cấp nguồn avatar dựa trên giới tính hoặc avatar mặc định
    
        // Tạo phần tử nội dung tin nhắn
        const messageContent = document.createElement('div');
        messageContent.classList.add('message', 'other-message', 'float-right');
        messageContent.innerText = message; // Thêm nội dung tin nhắn vào phần tử
    
        // Gắn các phần tử vào messageData
        messageData.appendChild(timeElement);
        messageData.appendChild(avatarElement);
    
        // Gắn messageData và messageContent vào messageItem
        messageItem.appendChild(messageData);
        messageItem.appendChild(messageContent);
    
        // Thêm messageItem vào vùng chat
        if (chatBox) {
            chatBox.appendChild(messageItem);
            // Cuộn xuống cuối cùng trong chat box sau khi thêm tin nhắn
            chatHistory.scrollTop = chatBox.scrollHeight;
        }
    });
    socket.on('SERVER_RESEND_MESSAGE_CLIENT', (data) => {
        // Tạo li mới
        const li = document.createElement('li');
        li.classList.add('clearfix');
    
        // Tạo phần thông tin tin nhắn
        const messageData = document.createElement('div');
        messageData.classList.add('message-data','message-data-left');


         // Thêm ảnh đại diện
         const avatarImg = document.createElement('img');
         if (data.avatar) {
             avatarImg.src = data.avatar;
         } else if (data.gender === 'Nam') {
             avatarImg.src = '/admin/images/undraw_profile.svg';
         } else {
             avatarImg.src = '/admin/images/undraw_profile_3.svg';
         }
         avatarImg.alt = 'avatar';
     
         messageData.appendChild(avatarImg);

        const timeSpan = document.createElement('span');
        timeSpan.classList.add('message-data-time');
        timeSpan.textContent = data.time;
    
        messageData.appendChild(timeSpan);
    
        // Tạo nội dung tin nhắn
        const message = document.createElement('div');
        message.classList.add('message', 'other-message'); // hoặc 'my-message' nếu tin nhắn từ người dùng hiện tại
        message.textContent = data.message;
    
        // Thêm vào li
        li.appendChild(messageData);
        li.appendChild(message);
        if(chatBox){
            chatBox.appendChild(li);
            chatHistory.scrollTop = chatBox.scrollHeight;
        }
    });
            
}

const listMessageDropDown=document.querySelector('#messagesDropdown');
if(listMessageDropDown){
    listMessageDropDown.addEventListener('click',async (e)=>{
        e.preventDefault();
        const messageList=document.querySelector('.dropdown-list-message');
        messageList.classList.toggle('show');
    })
}

const deliverOrderButtons=document.querySelectorAll('[btn-deliver-order]');
if(deliverOrderButtons.length >0){
    deliverOrderButtons.forEach((button)=>{
        button.addEventListener('click',async (e)=>{
            e.preventDefault();
            Swal.fire({
                title: 'Bạn có giao đơn hàng này không ?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonText: 'Xác nhận',
                cancelButtonText: 'Hủy',
                confirmButtonColor: '#28A745',
                cancelButtonColor: '#d33',
                focusConfirm: false
            }).then((result) => {
                if(result.isConfirmed){
                    const id=button.getAttribute('btn-deliver-order');
                    fetch(`/admin/management/order/deliver/${id}`, {
                        method: 'PATCH',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                    })
                    .then(async (response) => response.json())
                    .then(async (data) => {
                        await Swal.fire({
                            position: "center",
                            icon: "success",
                            title: data.message,
                            showConfirmButton: false,
                            timer: 1500
                        });
                        location.reload();
                    })
                    .catch( async (error) => {
                        await Swal.fire({
                            icon: "error",
                            title: "Lỗi xảy ra",
                            text: error.message,
                        });
                        location.reload();
                    });
                }
            })
        })
    })
}