let open_popups = document.getElementsByClassName("comment-button");
let popup_dialog = document.getElementById("rate_popup_close_btn");

function addHandlers() {
    Array.from(open_popups).forEach((popup) => {
        popup.addEventListener("click", openPopup)
    })
    popup_dialog.addEventListener("click", closePopup);
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


addHandlers();