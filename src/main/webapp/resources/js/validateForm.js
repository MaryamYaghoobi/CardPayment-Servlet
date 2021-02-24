
$(document).ready(function() {


    $('form[id="saveForm"]').validate({
        rules: {
            firstName: 'required',
            lastName: 'required',
            email: {
                required: true,
                email: true,
            },
            password: {
                required: true,
                minlength: 6,
            },
            username: 'required',
            dateOfBirth: 'required'
        },
        messages: {
            firstName: 'وارد کردن نام اجباری است.',
            lastName: 'وارد کردن نام خانوادگی اجباری است.',
            email: 'ایمیل خود را با فرمت مناسب وارد کنید.',
            password: {
                minlength: 'رمزعبور میباست حداقل 6 کاراکتر باشد.',
                required: 'وارد کردن رمزعبور اجباری است.'
            },
            username: 'وارد کردن نام کاربری اجباری است.',
            dateOfBirth: 'وارد کردن تاریخ تولد اجباری است.'

        },
        submitHandler: function(form) {
            form.submit();
        }
    });

    $('form[id="editForm"]').validate({
        rules: {
            firstName: 'required',
            lastName: 'required',
            email: {
                required: true,
                email: true,
            }
        },
        messages: {
            firstName: 'وارد کردن نام اجباری است.',
            lastName: 'وارد کردن نام خانوادگی اجباری است.',
            email: 'ایمیل خود را با فرمت مناسب وارد کنید.'
        },
        submitHandler: function(form) {
            form.submit();
        }
    });
});