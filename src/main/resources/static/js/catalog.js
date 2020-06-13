    $(document).ready(function f() {
        document.getElementById("leftMenu").insertAdjacentHTML("afterbegin",
            "<div class=\"dropdown-menu\" style=\"display: block; position: unset; font-size: 1.5rem; margin-top: 16px; background-color: #474747;\">\n" +
            "                <a class=\"dropdown-item text-light\" href=\"/catalog/video_card\">Видеокарты</a>\n" +
            "                <a class=\"dropdown-item text-light\" href=\"/catalog/processor\">Процессоры</a>\n" +
            "                <a class=\"dropdown-item text-light\" href=\"/catalog/motherboard\">Материнские платы</a>\n" +
            "                <a class=\"dropdown-item text-light\" href=\"/catalog/hdd\">Жёсткие диски</a>\n" +
            "                <a class=\"dropdown-item text-light\" href=\"/catalog/ram\">Оперативная память</a>\n" +
            "                <a class=\"dropdown-item text-light\" href=\"/catalog/ssd\">SSD</a>\n" +
            "                <a class=\"dropdown-item text-light\" href=\"/catalog/power_supply\">Блоки питания</a>\n" +
            "                <a class=\"dropdown-item text-light\" href=\"/catalog/coolers\">Система охлаждения</a>\n" +
            "                <a class=\"dropdown-item text-light\" href=\"/catalog/computer_case\">Корпуса</a>\n" +
            "            </div>"
        );
      var btn = $('#button');

      $(window).scroll(function() {
        if ($(window).scrollTop() > 300) {
          btn.addClass('show');
        } else {
          btn.removeClass('show');
        }
      });

      btn.on('click', function(e) {
        e.preventDefault();
        backToTop()
      });
      function backToTop() {
        if (window.pageYOffset > 0) {
          window.scrollBy(0, -80);
          setTimeout(backToTop, 0);
        }
      }
    });