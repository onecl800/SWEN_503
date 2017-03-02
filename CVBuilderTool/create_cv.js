// Initialize tooltip component

function initialise_tool_tip() {
    $('[data-toggle="tooltip"]').tooltip();
    $('[data-toggle="popover"]').popover();
}

initialise_tool_tip();

// Remove arrows from first and last page

$('#wizard_pages').on('slid.bs.carousel', '', function () {
    var $this = $(this);

    $this.children('.carousel-control').show();

    if ($('.carousel-inner .item:first').hasClass('active')) {
        $this.children('.left.carousel-control').hide();
    } else if ($('.carousel-inner .item:last').hasClass('active')) {

        $this.children('.right.carousel-control').hide();
    } else {
        $this.children('.carousel-control').show();

    }
});

// This function limits the number of personal attributes that can be selected
var qualitiesIndex = 0;
jQuery(function () {
    var max = 6;
    var checkboxes = jQuery('input[type="checkbox"]');

    checkboxes.change(function () {
        var current = checkboxes.filter(':checked').length;
        checkboxes.filter(':not(:checked)').prop('disabled', current >= max);

    });
});


jQuery(function () {
    var max = 6;
    var checkboxes = jQuery('input[type="checkbox"]');

    var current = checkboxes.filter(':checked').length;
    checkboxes.filter(':not(:checked)').prop('disabled', current >= max);
});

/**The purpose of this variable is that it's a boolean to track which button was clicked
 This is for the purposes of whether to run validateForm() internal logic or not*/
var runValidation = false;

/**Variable to check the number of attributes currently selected*/
var qualitiesChecked = 0;

/**Validates the form for all required tags*/
function validateForm() {
    //If finish button was clicked
    if (runValidation) {
        //remove any red input borders
        var formInputs = document.getElementsByTagName('input');
        for (var i = 0; i < formInputs.length; i++) {
            formInputs[i].style.border = "";

            //Callls function to remove spaces from before and after value
            formInputs[i].value = removeSpaces(formInputs[i].value);
            
        }  

        //To handle removing spaces from text area
        var textAreaInput = document.getElementsByTagName('textarea');
        for (var i = 0; i < textAreaInput.length; i++) {
            
            textAreaInput[i].value = removeSpaces(textAreaInput[i].value);
            
        }  
       
        if (!validateItem("firstName", "First Name", 0)) {   
            return false;
        }
        if (!validateItem("lastName", "Last Name", 0)) {
            return false;
        }
        if (!validateItem("school", "School", 0)) {
            return false;
        }
        if (!validateItem("phoneNum", "Phone Number", 0)) {
            return false;
        }
        if (!validateItem("mail", "Email", 0) || !validateEmail("mail", 0)) {
            return false;
        }
        if (!validateItem("mail", "Email", 0)) {
            return false;
        }
        if (!validateItem("city", "City", 0)) {
            return false;
        }

        //make employment start date mandatory if company name is filled in
        for (var i = 0; i <= employmentTracker; i++) {
            if (!validateItemDatePicker("employment[" + i + "][jobCompany]", "employment[" + i + "][startDate]", 2)) {
                return false;
            }
        }

        //make volunteering start date mandatory if company name is filled in
        for (var i = 0; i <= volunteerTracker; i++) {
            if (!validateItemDatePicker("volunteering[" + i + "][volCompany]", "volunteering[" + i + "][volStartDate]", 3)) {
                return false;
            }
        }


        for (var i = 0; i <= educationTracker; i++) {
            if (!validateItem("education[" + i + "][schoolYear]", "Extra education years must be removed and/or all education years", 4)) {
                return false;
            }
        }

        if (qualitiesChecked == 0) {
            alert("Please select at least 1 personal attribute");
            $("#wizard_pages").carousel(5);
            return false;
        }

        for (var i = 0; i <= refereesTracker; i++) {
            console.log(i);
            if (!validateItem("referees[" + i + "][refName]", "Referee Name", 7)) {
                return false;
            }
            if (!validateItem("referees[" + i + "][refPosition]", "Referee Position", 7)) {
                return false;
            }
            if (!validateItem("referees[" + i + "][refEmail]", "Referee Email", 7) || !validateEmail("referees[" + i + "][refEmail]", 7)) {
                return false;
            }
            if (!validateItem("referees[" + i + "][refNum]", "Referee Contact Number", 7)) {
                return false;
            }
        }

    }
    return true;
}

/**Used by validateForm() for validating items*/
function validateItem(elementName, elementPrintName, carouselSlide) {
    var x = document.forms["myForm"][elementName].value;
    if (x == "") {
        alert(elementPrintName + " must be filled out");
        $("#wizard_pages").carousel(carouselSlide);
        document.forms["myForm"][elementName].style.borderColor = "red";
        document.forms["myForm"][elementName].select();
        return false;
    }
    document.forms["myForm"][elementName].style.borderColor = "";
    return true;
}

/**Used by validateForm() for validating datepickers - prevents dropdown of datepicker on return to slide*/
function validateItemDatePicker(companyName, startDateName, carouselSlide) {
    var companyNameValue = document.forms["myForm"][companyName].value;
    var startDataValue = document.forms["myForm"][startDateName].value;

    if (companyNameValue.length != "" && startDataValue == "") {
        alert("Start date must be filled out because you have entered " + companyNameValue + ".");
        $("#wizard_pages").carousel(carouselSlide);
        document.forms["myForm"][startDateName].style.borderColor = "red";
        //document.forms["myForm"][elementName].select();
        return false;
    }
    //document.forms["myForm"][startDataValue].style.borderColor = "";
    return true;
}

/**The boolean is true if finish was clicked and false if save was clicked
 The purpose of the boolean is to track which button was clicked*/
function saveQualities(finishClicked) {
    runValidation = finishClicked;

    $("input:checkbox[name=quality]:checked").each(function () {
        qualitiesChecked++;
        $(this).attr('name', 'qualities[' + qualitiesIndex + '][quality]');
        qualitiesIndex++;
    });
}

//This function is for validating email address formats
function validateEmail(elementName, carouselSlide) {
    var x = document.forms["myForm"][elementName].value;
    //Checks that the field contains an @ character
    if (x.includes("@")) {
        //Splits the string at the @ character
        var stringArray = x.split("@");
        if (stringArray.length == 2) {
            //Checks that the strings on either side of the @ are not empty
            if (stringArray[0].length > 0 && stringArray[1].length > 0) {
                return true;
            } else {
                alert("Valid email format must be used");
                document.forms["myForm"][elementName].style.borderColor = "red";
                document.forms["myForm"][elementName].select();
                $("#wizard_pages").carousel(carouselSlide);
                return false;
            }
        } else {
            alert("Valid email format must be used");
            document.forms["myForm"][elementName].style.borderColor = "red";
            document.forms["myForm"][elementName].select();
            $("#wizard_pages").carousel(carouselSlide);
            return false;
        }
    } else {
        alert("Valid email format must be used");
        document.forms["myForm"][elementName].style.borderColor = "red";
        document.forms["myForm"][elementName].select();
        $("#wizard_pages").carousel(carouselSlide);
        return false;
    }
}


//Tooltips dropping down beneath selected characteristics
/*
 function displayCheckedDrop() {
 var m = $("input[class=checkboxOption]").length;

 for (i = 0; i < m; i++) {
 var attrib = $("input[class=checkboxOption]")[i];
 var attribLongID = attrib.id + "Long";
 var item = document.getElementById(attribLongID);
 item.style.display = "none";
 }

 var n = $("input:checked").length;
 for (i = 0; i < n; i++) {
 var attrib = $("input:checked")[i];
 var attribLongID = attrib.id + "Long";
 var item = document.getElementById(attribLongID);
 item.style.display = "block";
 }

 }
 */

// jquery for hover of qualities --> this code is legacy now with the new panel based personal attributes page

$("input[class=checkboxOption]").hover(hoverAttributeOn, hoverAttributeOff);

function hoverAttributeOn() {
    // Change the description to visible
    var attrib = $(this).attr('id');
    var attribLongID = attrib + "Long";
    var item = document.getElementById(attribLongID);
    item.style.display = "block";
}

function hoverAttributeOff() {
    // Change the description to visible
    if ($(this).is(':checked')) {

    } else {
        var attrib = $(this).attr('id');
        var attribLongID = attrib + "Long";
        var item = document.getElementById(attribLongID);
        item.style.display = "none";
    }
}

// Jquery for changing color when clicked

function changeColor() {
    var attrib = $(this).attr('id');
    var checkboxId = attrib.replace("box", "checkbox");
    if (qualitiesTracker < 6 && !document.getElementById(checkboxId).checked) {
        $(this).toggleClass('color_toggled_on');
        $("#" + checkboxId).attr("checked", "checked");
        qualitiesTracker++;
        toggleUncheckedBoxGrey();
    } else if (qualitiesTracker <= 6 && document.getElementById(checkboxId).checked) {
        toggleUncheckedBoxGrey();
        $(this).toggleClass('color_toggled_on');
        $("#" + checkboxId).removeAttr("checked");
        qualitiesTracker--;
    }
    console.log(qualitiesTracker);
}

function toggleUncheckedBoxGrey() {
    if (qualitiesTracker == 6) {
        var qualities = document.getElementsByClassName("personal_attribute");
        for (var i = 0; i < qualities.length; i++) {
            var attrib = $(qualities[i]).attr('id');
            var checkboxId = attrib.replace("box", "checkbox");
            if (!document.getElementById(checkboxId).checked) {
                console.log(checkboxId + " " + document.getElementById(checkboxId).checked);
                $(qualities[i]).toggleClass('color_toggled_grey');
            }
        }
    }
}

function updateLoadQualities() {
    var qualities = document.getElementsByClassName("personal_attribute");
    for (var i = 0; i < qualities.length; i++) {
        if ($(qualities[i]).hasClass("color_toggled_on")) {
            var checkboxId = attrib.replace($(qualities[i]).attr("id"), "checkbox");
            $("#" + checkboxId).attr("checked", "checked"); //make hidden checkbox checked
        }
    }
}

$(".personal_attribute").on("click", changeColor);

//Limit characters typed into field.
//At a later date, all of these functions should probably pop up explanations of what characters are and aren't allowed in their fields.
//Currently this also allows users to paste non-allowed characters into fields, but not to type them.

// limit to letters is for small text input fields. Do not use this for text box areas, as extra characters may be desired.
function limitToLetters(e) {
    var charCode = e.which;
    if (
        (charCode >= 65 && charCode <= 90) || //lowercase
        (charCode >= 97 && charCode <= 122) ||//uppercase
        (charCode == 32 || charCode == 39 || charCode == 45) || // space ' and -
        (charCode == 0 || charCode == 8) || //delete and backspace
        ((charCode >= 128 && charCode <= 154) || (charCode >= 160 && charCode <= 165)) // accents
    ) {
        return;
    } else {
        e.preventDefault();
    }
}

// limitToNumbers is for simple number inputs, such as ages.
function limitToNumbers(e) {
    var charCode = e.which;
    if ((charCode >= 48 && charCode <= 57) || //numbers
        (charCode == 0 || charCode == 8))//delete and backspace
    {
        return;
    } else {
        e.preventDefault();
    }
}

// limitToLettersAndNumbers is a function for small text inputs where we cannot be certain of the range of inputs, but still limits characters such as brackets and quotation marks.
function limitToLettersAndNumbers(e) {
    var charCode = e.which;
    if (
        (charCode >= 65 && charCode <= 90) || //lowercase
        (charCode >= 97 && charCode <= 122) ||//uppercase
        (charCode >= 48 && charCode <= 57) || //numbers
        (charCode == 32 || charCode == 39 || charCode == 45) || // space ' and -
        (charCode == 44 || charCode == 46) || // , and .
        (charCode == 0 || charCode == 8) || //delete and backspace
        ((charCode >= 128 && charCode <= 154) || (charCode >= 160 && charCode <= 165)) // accents
    ) {
        return;
    } else {
        e.preventDefault();
    }
}

// limitToPhoneNumbers is for input fields for phone numbers.

function limitToPhoneNumbers(e) {
    var charCode = e.which;
    if ((charCode >= 48 && charCode <= 57) || //numbers
        (charCode == 0 || charCode == 8) ||//delete and backspace
        (charCode == 32 || charCode == 40 || charCode == 41 || charCode == 43 || charCode == 45) || //space, (, ), +, and -
        (charCode == 69 || charCode == 84 || charCode == 88 || charCode == 101 || charCode == 116 || charCode == 120) ||//EXT, and ext
        (charCode == 35 || charCode == 46)) //# and .
    {
        return;
    } else {
        e.preventDefault();
    }
}

function limitToEmails(e) {
    // limitToEmails is for email input fields. Will work for most/all modern email addresses. May need revision if other characters arise.
    var charCode = e.which;
    if (
        (charCode >= 65 && charCode <= 90) || //lowercase
        (charCode >= 97 && charCode <= 122) ||//uppercase
        (charCode >= 48 && charCode <= 57) || //numbers
        (charCode == 45 || charCode == 46 || charCode == 64 || charCode == 95) || // - . @ and _
        (charCode == 0 || charCode == 8) //delete and backspace
    ) {
        return;
    } else {
        e.preventDefault();
    }
}

function limitForTextAreas(e) {
    var charCode = e.which;
    if (
        (charCode >= 32 && charCode <= 59) || charCode == 61 || (charCode >= 63 && charCode <= 154) || (charCode >= 160 && charCode <= 165) || // check for most typeable characters, other than < and > - this seeks to prevent injection attacks.
        (charCode == 0 || charCode == 8 || charCode == 13) //delete, backspace, and enter
    ) {
        return;
    } else {
        e.preventDefault();
    }
}

//Function to remove spaces from start and end of input strings
function removeSpaces(string){

    var resultString = "";

    resultString = string.trim();

    return resultString;

}


//Listeners for character prohibitors.

document.querySelector('input[name="firstName"]').onkeypress = function (e) {
    limitToLetters(e);
}
document.querySelector('input[name="lastName"]').onkeypress = function (e) {
    limitToLetters(e);
}
document.querySelector('input[name="age"]').onkeypress = function (e) {
    limitToNumbers(e);
}
document.querySelector('#language').onkeypress = function (e) {
    limitToLetters(e);
}
document.querySelector('input[name="school"]').onkeypress = function (e) {
    limitToLettersAndNumbers(e);
}
document.querySelector('input[name="age"]').onkeypress = function (e) {
    limitToNumbers(e);
}
document.querySelector('input[name="phoneNum"]').onkeypress = function (e) {
    limitToPhoneNumbers(e);
}
document.querySelector('input[name="mail"]').onkeypress = function (e) {
    limitToEmails(e);
}
document.querySelector('input[name="suburb"]').onkeypress = function (e) {
    limitToLetters(e);
}
document.querySelector('input[name="city"]').onkeypress = function (e) {
    limitToLetters(e);
}

document.querySelector('#personal_statement_text_field').onkeypress = function (e) {
    limitForTextAreas(e);
}
