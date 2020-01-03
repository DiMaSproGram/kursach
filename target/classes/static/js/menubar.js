$(document).ready(function f() {
    document.getElementById('menuBar').insertAdjacentHTML("afterbegin",
        "   <ul id='menuUl'>\n" +
        "            <li class='menu'>\n" +
        "                <a class=\"menuButton\" href=\"/catalog\" title=\"Каталог\">Каталог</a>\n" +
        "            </li>\n" +
        "            <li class='menu'>\n" +
        "                <a class=\"menuButton\" href=\"/creator\" title=\"Сборщик ПК\">Сборщик ПК</a>\n" +
        "            </li>\n" +
        "            <li class='menuSpace'>\n" +
        "            </li>" +
        "        </ul>"
    );
})