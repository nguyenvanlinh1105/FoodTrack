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

