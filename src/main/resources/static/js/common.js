let themeBtn = document.getElementById("theme_btn");
let menu = document.getElementById("menu");
let menu_list = document.getElementById("menu_list");
let open_popups = document.getElementsByClassName("comment-button");
let popup_dialog = document.getElementById("rate_popup_block");

let buttonIsOn = false;

function addHandlers() {
    themeBtn.addEventListener("click", changeTheme);
    menu.addEventListener("click", toggleMenu);
    menu.addEventListener("focusout", toggleMenuOff);
    Array.from(open_popups).forEach((popup) => {
        popup.addEventListener("click", openPopup)
    })
    popup_dialog.addEventListener("focusout", closePopup);
}

function openPopup(e) {
    let popup = document.getElementById("rate_popup_block");

    console.log(e.target.parentNode.id);

    if (popup.style.display === "none") {
        popup.style.display = "block";
        document.getElementById("rate").focus();
        document.form_rate.action = "/rates/site/" + e.target.parentNode.id;
    } else {
        popup.style.display = "none";
    }
}

function closePopup() {
    let popup = document.getElementById("rate_popup_block");
        popup.style.display = "none";
}

function changeTheme() {

    let icon = document.createElement('img');

    if (buttonIsOn) {
        icon.src = "/images/toggle-off-solid.png";
        icon.alt = "toggle off";

        buttonIsOn = false;

    } else {
        icon.src = "/images/toggle-on-solid.png";
        icon.alt = "toggle on";

        buttonIsOn = true;
    }

    themeBtn.removeChild(themeBtn.firstChild);

    themeBtn.appendChild(icon);
}


function toggleMenu() {

    let state;
    let newState;

    if (menu_list.classList.contains('show')) {

        state = 'show';
        newState = 'disappear';

    } else {

        state = 'disappear';
        newState = 'show';
    }

    menu_list.classList.replace(state,newState);

}

function toggleMenuOff() {

    let state = 'show';
    let offState = 'disappear';

    menu_list.classList.replace(state,offState);

}


addHandlers();