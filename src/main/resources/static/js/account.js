$(document).ready(function f() {

    document.getElementById('menuUl').insertAdjacentHTML("beforeend",
        "        <li class='menu' id='account'>\n" +
        "                <a class=\"menuButton\" href=\"/account\" title=\"Аккаунт\">Аккаунт</a>\n" +
        "              </li>\n"
    );
    if(document.getElementById("loginbtn") != null)
        document.getElementById("loginbtn").style.display = "none";
})
