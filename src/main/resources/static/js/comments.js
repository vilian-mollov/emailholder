let commentInputField = document.getElementById("comment");

function addHandlers() {
    commentInputField.addEventListener("keypress", toggleAddButton);
    commentInputField.addEventListener("focusout", toggleAddButton);

}


function toggleAddButton() {

    const inputField = document.getElementById("comment");
    const addCommentButton = document.getElementById("add_button");

    if (inputField.value.trim() !== "") {
        addCommentButton.disabled = false;
    } else {
        addCommentButton.disabled = true;
    }
}

addHandlers()