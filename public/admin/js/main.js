//Ẩn alert
const alertSuccess = document.querySelector('[show-alert]');
if (alertSuccess) {
    const time = Number(alertSuccess.getAttribute('show-alert')) || 3000;
    setTimeout(() => {
        alertSuccess.classList.add('hidden');
    }, time)
}
const staffForm = document.getElementById('staffForm');
if (staffForm) {
    staffForm.addEventListener('submit', async function (event) {
        event.preventDefault();
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

        if (hasError) return;

        try {
            const response = await fetch(form.action, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data)
            });

            const result = await response.json();

            if(result.code==400){
                Swal.fire({
                    icon: "error",
                    title: "Lỗi xảy ra",
                    text: result.message,
                });
            }else{
                Swal.fire({
                    position: "center",
                    icon: "success",
                    title: result.message,
                    showConfirmButton: false,
                    timer: 1500
                });
            }

        } catch (error) {
            showMessage(`Có lỗi xảy ra: ${error.message}`, 'error');
        }
    });

    // Lắng nghe sự kiện thay đổi input để ẩn lỗi khi nhập lại
    const inputs = staffForm.querySelectorAll('input, select');
    inputs.forEach(input => {
        input.addEventListener('input', () => {
            const errorAlert = input.closest('.col-md-12')?.querySelector('.invalid-feedback.active');
            if (errorAlert) {
                errorAlert.classList.remove('active');
            }
        });
    });
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

