$(document).ready(function f() {
    document.getElementById('menuUl').insertAdjacentHTML("beforeend",
        "      <li class='menu' id='loginbtn'>\n" +
        "                <a class=\"menuButton\" href=\"/login\" title=\"Войти\">Войти</a>\n" +
        "            </li>\n"
    );
    if(document.getElementById("account") != null)
        document.getElementById("account").style.display = "none";
})
