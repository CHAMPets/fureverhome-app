function automate_animation() {
    var h1_list = document.querySelectorAll("h1");
    for (var i = 0; i < h1_list.length; i++)
        h1_list[i].setAttribute("data-aos", "fade-up");

    var card_list = document.querySelectorAll(".card");
    for (var i = 0; i < card_list.length; i++)
        card_list[i].setAttribute("data-aos", "zoom-in");

    AOS.init();
}