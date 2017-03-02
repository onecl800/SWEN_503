/**
 * Created by josh on 13/01/17.
 */
$('#wizard_pages').bind('slid.bs.carousel', function (e) {
    var carouselData = $(this).data('bs.carousel');
    var currentIndex = carouselData.getItemIndex(carouselData.$element.find('.item.active'));
    var slideIDs = ["details_button", "statement_button", "employment_button", "volunteer_button", "education_button", "attributes_button", "awards_button", "referees_button"];
    for (var i = 0; i < slideIDs.length; i++) {
        if (currentIndex == i) {
            document.getElementById(slideIDs[i]).className = "col-xs-1 top_nav_bar_button_active";
        } else {
            document.getElementById(slideIDs[i]).className = "col-xs-1 top_nav_bar_button_inactive";
        }
    }
});