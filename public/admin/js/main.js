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
            console.log('Vào');
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
            console.log('Tới đây');
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
    var table = $('#dataTable').DataTable({
        language: {
            emptyTable: "Chưa có dữ liệu",
            zeroRecords: "Không tìm thấy",
            search: "",
            searchPlaceholder: 'Tìm kiếm',
            loadingRecords: "Loading...",
        },
        "width": "100%",
        lengthChange: false,      // Bỏ phần thay đổi số lượng bản ghi hiển thị
        paging: false,            // Bật phân trang
        pageLength: 4,            // Giới hạn tối đa 4 bản ghi mỗi trang
        info: false,              // Bỏ phần số thông tin bảng ghi
        searching: true,          // Giữ lại chức năng tìm kiếm
        ordering: true,           // Giữ lại chức năng sắp xếp
        stateSave: true,          // Lưu lại trạng thái
        columnDefs: [
            { 
                targets: -1,       // Cột cuối cùng (target: -1)
                orderable: false   // Tắt tính năng sắp xếp cho cột cuối cùng
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
                    console.log(slug);
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
                    console.log(slug);
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