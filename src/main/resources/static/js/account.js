$(document).ready(function f() {

    document.getElementById('menuUl').insertAdjacentHTML("beforeend",
        "        <li class='menu' id='account'>\n" +
        "                <a class=\"menuButton\" href=\"/account\" title=\"Аккаунт\">Аккаунт</a>\n" +
        "              </li>\n"
    );

    document.getElementById('accountInfo').insertAdjacentHTML("beforeend",
        "                  <li th:if=\"${active}\">\n" +
        "                            <a href=\"/account\" class=\"text-light\">Аккаунт</a>\n" +
        "                        </li>\n"
    );
});
