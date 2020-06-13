$(document).ready(function f() {
    document.getElementById('menuUl').insertAdjacentHTML("beforeend",
        "      <li class='menu' id='loginbtn'>\n" +
        "                <a class=\"menuButton\" href=\"/login\" title=\"Войти\">Войти</a>\n" +
        "            </li>\n"
    );

    document.getElementById('accountInfo').insertAdjacentHTML("beforeend",
        "                  <li>\n" +
        "                            <a href=\"/login\" class=\"text-light\">Войти</a>\n" +
        "                        </li>\n" +
        "                        <li>\n" +
        "                            <a href=\"/registration\" class=\"text-light\">Зарегистрироваться</a>\n" +
        "                        </li>\n"
    );
});
