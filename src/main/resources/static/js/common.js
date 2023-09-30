let themeBtn = document.getElementById("theme_btn");
let menu = document.getElementById("menu");
let menu_list = document.getElementById("menu_list");

let buttonIsOn = false;

function addHandlers() {
    themeBtn.addEventListener("click", changeTheme);
    menu.addEventListener("click", toggleMenu);
}


function changeTheme() {

    let icon = document.createElement('img');

    if (buttonIsOn) {
        icon.src = "images/toggle-off-solid.svg";
        icon.alt = "toggle off";

        buttonIsOn = false;

    } else {
        icon.src = "images/toggle-on-solid.svg";
        icon.alt = "toggle on";

        buttonIsOn = true;
    }

    themeBtn.removeChild(themeBtn.firstChild);

    themeBtn.appendChild(icon);
}


function toggleMenu() {

    let state;

    if (menu_list.classList.contains('show')) {

        state = 'disappear';
        console.log(state);

    } else {

        state = 'show';
        console.log(state);

    }

    menu_list.className = '';
    menu_list.classList.add(state);

}


addHandlers();