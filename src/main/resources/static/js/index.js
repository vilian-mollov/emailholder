let mainGetStartBtn = document.getElementById("start_btn");

function addHandlers() {
    window.addEventListener("scroll", showHiddenContext)
    mainGetStartBtn.addEventListener("click",scrollDownMainBtn);
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