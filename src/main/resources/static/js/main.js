function hideShowPassword() {

    const btn1 = document.getElementById("btp_p1");
    const btn2 = document.getElementById("btn_p2");

    btn1.addEventListener('click', hideShowPassHandler);
    btn2.addEventListener('click', hideShowPassHandler);

    function hideShowPassHandler(e) {
        console.log(e.currentTarget);
        console.log(e.parentNode);

        const div = e.target.parentNode;
        const passwordField = div.firstChild;

        // TODO fix hideShowPassHandler change passwordField type to text
        passwordField.type='text';
        // document.getElementById('pass').type = 'text';
    }


}

hideShowPassword();