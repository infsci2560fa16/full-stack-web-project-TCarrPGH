/*jslint browser: true*/ /*global  $*/
$(".dropdown-toggle").dropdown();
// Create a "close" button and append it to each list item

// Add a "checked" symbol when clicking on a list item
var list = document.querySelector('ul');
list.addEventListener('click', function (ev) {
    "use strict";
    if (ev.target.tagName === 'LI') {
        ev.target.classList.toggle('checked');
    }
}, false);

// Create a new list item when clicking on the "Add" button
function newElement() {
    "use strict";
    var li = document.createElement("li");
    var inputValue = document.getElementById("produceInput").value;
    var t = document.createTextNode(inputValue);
    li.appendChild(t);
    if (inputValue === '') {
    alert("You must write something!");
    } else {
        document.getElementById("produceUL").appendChild(li);
    }
    document.getElementById("produceInput").value = "";

    var span = document.createElement("SPAN");
    var txt = document.createTextNode("\u00D7");
    span.className = "close";
    span.appendChild(txt);
    li.appendChild(span);
    function handler() {
        li.style.display = "none";
    }
    span.onclick = handler;
}
