$(document).ready(function f() {
    document.getElementById('menuBar').insertAdjacentHTML("afterbegin",
        "   <ul>\n" +
        "            <li class='menu'>\n" +
        "                <a class=\"menuButton\" href=\"/catalog\" title=\"Каталог\">Каталог</a>\n" +
        "            </li>\n" +
        "            <li class='menu'>\n" +
        "                <a class=\"menuButton\" href=\"/generator\" title=\"Сборщик ПК\">Сборщик ПК</a>\n" +
        "            </li>\n" +
        "            <li class='menu'>\n" +
        "                <a class=\"menuButton\" href=\"/about\" title=\"О нас\">О нас</a>\n" +
        "            </li>\n" +
        "            <li class='menu'>\n" +
        "            </li>\n" +
        "            <li class='menu'>\n" +
        "                <a class=\"menuButton\" href=\"/login\" title=\"Войти\">Войти</a>\n" +
        "            </li>\n" +
        "        </ul>"
    );
})