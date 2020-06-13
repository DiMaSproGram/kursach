$(document).ready(function f() {
    document.getElementById('menuBar').insertAdjacentHTML("afterbegin",
        " <div id=\"logo\">\n" +
        "            <a id=\"logoName\" href=\"/\">PC-CREATOR</a>\n" +
        "        </div>\n" +
        "\n" +
        "        <ul id='menuUl'>\n" +
        "            <li class='menu'>\n" +
        "                <a class=\"menuButton\" href=\"/catalog\" title=\"Каталог\">Каталог</a>\n" +
        "            </li>\n" +
        "            <li class='menu'>\n" +
        "                <a class=\"menuButton\" href=\"/creator\" title=\"Сборщик ПК\">Сборщик ПК</a>\n" +
        "            </li>\n" +
        "        </ul>"
    );

    document.getElementById('footer').insertAdjacentHTML("afterbegin",
        " <div class=\"container-fluid text-center text-md-left\">\n" +
        "            <div class=\"row\">\n" +
        "                <div class=\"col mt-md-0 mb-3\" style=\"padding-left: 10%;\">\n" +
        "                    <h5 class=\"text-uppercase text-light\">PC-CREATOR</h5>\n" +
        "                    <p class=\"text-light\">Спасибо за использование нашего сайта.</p>\n" +
        "                </div>\n" +
        "                <hr class=\"clearfix w-100 d-md-none pb-3\">\n" +
        "                <div class=\"col mb-md-0 mb-3\">\n" +
        "                    <h5 class=\"text-uppercase text-light\">Каталог</h5>\n" +
        "                    <ul class=\"list-unstyled\">\n" +
        "                        <li>\n" +
        "                            <a href=\"/catalog/video_card\" class=\"text-light\">Видеокарты</a>\n" +
        "                        </li>\n" +
        "                        <li>\n" +
        "                            <a href=\"/catalog/processor\" class=\"text-light\">Процессоры</a>\n" +
        "                        </li>\n" +
        "                        <li>\n" +
        "                            <span class=\"disabled text-light\">Другие...</span>\n" +
        "                        </li>\n" +
        "                    </ul>\n" +
        "                </div>\n" +
        "                <div class=\"col mb-md-0 mb-3\">\n" +
        "                    <h5 class=\"text-uppercase text-light\">Сборщик ПК</h5>\n" +
        "                    <ul class=\"list-unstyled\">\n" +
        "                        <li>\n" +
        "                            <a href=\"/creator\" class=\"text-light\">Сборщик ПК</a>\n" +
        "                        </li>\n" +
        "                    </ul>\n" +
        "                </div>\n" +
        "                <div class=\"col mb-md-0 mb-3\">\n" +
        "                    <h5 class=\"text-uppercase text-light\">Аккаунт</h5>\n" +
        "                    <ul id='accountInfo' class=\"list-unstyled\">\n" +
        "                    </ul>\n" +
        "                </div>\n" +
        "            </div>\n" +
        "        </div>\n" +
        "        <div class=\"footer-copyright text-center text-light\" style=\"padding-bottom: 10px;\">\n" +
        "            © 2020 PC-creator © All rights reserved\n" +
        "        </div>"
    );
});