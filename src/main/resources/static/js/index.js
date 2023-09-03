let themeBtn = document.getElementById("theme_btn");
let menu = document.getElementById("menu");
let menu_list = document.getElementById("menu_list");
let mainGetStartBtn = document.getElementById("start_btn");
let buttonIsOn = false;

function addHandlers() {
    themeBtn.addEventListener("click", changeTheme);
    menu.addEventListener("click", toggleMenu);
    window.addEventListener("scroll", showHiddenContext)
    mainGetStartBtn.addEventListener("click",scrollDownMainBtn);
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

function showHiddenContext() {

    const scrollPosition = window.scrollY;
    const revealFirst = 530;
    const revealSecond = 700;
    const revealThird = 750;

    let leftContent1 = document.getElementById("left-side-con");
    let rightContent = document.getElementById("right-side-con");
    let leftContent2 = document.getElementById("left-side-con2");

    if (scrollPosition >= revealFirst) {
        leftContent1.className = "";
        leftContent1.classList.add("show");
    }

    if (scrollPosition >= revealSecond) {
        rightContent.className = "";
        rightContent.classList.add("show");
    }

    if (scrollPosition >= revealThird) {
        leftContent2.className = "";
        leftContent2.classList.add("show");
    }

}


function scrollDownMainBtn() {
    window.scrollTo(0,760);
}


addHandlers();