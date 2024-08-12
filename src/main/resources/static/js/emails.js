let edit_btn = document.getElementById("edit_btn");

function addHandlers() {
    edit_btn.addEventListener("click", enterEditMode);
}

function enterEditMode() {
    let table = document.getElementById("collection");
    let row = document.getElementById("row1");
    row.setAttribute('contenteditable', 'true');
}


addHandlers()