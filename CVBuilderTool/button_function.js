var tooltips_json = {};

function loadJSON(callback) {

    var xobj = new XMLHttpRequest();
        xobj.overrideMimeType("application/json");
    xobj.open('GET', 'tooltips.json', true); // Replace 'my_data' with the path to your file
    xobj.onreadystatechange = function () {
          if (xobj.readyState == 4 && xobj.status == "200") {
            // Required use of an anonymous callback as .open will NOT return a value but simply returns undefined in asynchronous mode
            callback(xobj.responseText);
          }
    };
    xobj.send(null);
 }

 loadJSON(function(response) {
  // Parse JSON string into object
    tooltips_json = JSON.parse(response);
    // console.log(tooltips_json);
 });

// Load the example.json file when the page has loaded

var example_text_json = {};
 $(document).ready(function () {
    $.getJSON('example.json', function (data) {
        example_text_json=data;
    });
});

// Javascript functions for carousel 1

 // Functionality for the language button
function addLanguage() {
    //Adds in form inputs an additional language
    //$("#removeLanguageButton").empty();
    $('div').remove("#removeLanguageButton");
    $('<div class="form_line_div" id="language_' + (languageTracker + 1) + '">' +
        '<label class="form_element_left"></label>' +
        ' <input id="language" class="form_element_middle"  onkeypress="limitToLetters(event)" name="languages[' + (languageTracker + 1) +
        '][language]" type="text" maxlength="25" value="" data-toggle="tooltip" data-placement="right" title="' +
        tooltips_json.languages + '" placeholder="' + example_text_json.languages + '"/><div id="removeLanguageButton"><input class="addOrRemoveButton" type="button" value="-" onclick="removeLanguage();"/></div></div>').insertAfter("#language_" + languageTracker);
    languageTracker++;
    initialise_tool_tip();
}

function removeLanguage() {
    $('div').remove("#language_" + languageTracker);
    languageTracker--;
    if (languageTracker > 0) {
        $("#language_" + languageTracker).append('<div id="removeLanguageButton"><input class="addOrRemoveButton" type="button" value="-" onclick="removeLanguage();"/></div>')
    }
}

// Javascript functions for carousel 3

function addEmployment() {
    //Adds in form inputs an additional section of employment
    $('<div id="employment_' + (employmentTracker + 1) +  '">' +
        '<div class="form_line_div">' +
        '<label class="form_element_left">Company:</label>' +
        '<input class="form_element_middle" onkeypress="limitToLettersAndNumbers(event)" name="employment[' + (employmentTracker + 1) +
        '][jobCompany]" value="" type="text" maxlength="35" data-toggle="tooltip" data-placement="right" title="' +
        tooltips_json.jobCompany + '" placeholder="' + example_text_json.company + '"/></div>' +
        '<div class="form_line_div">' +
        '<label class="form_element_left">Start date:</label>' +
        '<div class="input-group date form_element_middle  month_year_picker">' +
        '<input onkeydown="return false" type="text" class="form-control" id="employment[' + (employmentTracker + 1) + '][startDate]" name="employment[' + (employmentTracker + 1) + '][startDate]" value="" data-toggle="tooltip" data-placement="right" title="' +
        tooltips_json.startDate + '">' +
        '<span class="input-group-addon">' +
        '<span class="glyphicon glyphicon-calendar"></span></span></div></div>' +
        '<div class="form_line_div">' +
        '<label class="form_element_left">End date:</label>' +
        '<div class="input-group date form_element_middle month_year_picker">' +
        '<input disabled onkeydown="return false" type="text" class="form-control" id="employment[' + (employmentTracker + 1) + '][endDate]" name="employment[' + (employmentTracker + 1) + '][endDate]" value="" data-toggle="tooltip" data-placement="right" title="' +
        tooltips_json.endDate + '">' +
        '<span class="input-group-addon">' +
        '<span class="glyphicon glyphicon-calendar"></span></span></div></div>' +
        '<div class="form_line_div">' +
        '<label class="form_element_left">Job Title:</label>' +
        '<input class="form_element_middle" onkeypress="limitToLettersAndNumbers(event)" name="employment[' + (employmentTracker + 1) +
        '][title]" value="" type="text" maxlength="35" data-toggle="tooltip" data-placement="right" title="' +
        tooltips_json.title + '" placeholder="' + example_text_json.jobTitle + '"/></div>' +
        '<div class="form_line_div">' +
        '<label class="form_element_left">What did you do there?:</label>' +
        '<textarea class="form_element_middle" onkeypress="limitForTextAreas(event)" name="employment[' + (employmentTracker + 1) +
        '][jobDescription]" rows="4" cols="50" maxlength="500" data-toggle="tooltip" data-placement="right" title="' +
        tooltips_json.jobDescription + '"placeholder="' + example_text_json.jobRole + '"/></textarea></div><br></div>').insertAfter("#employment_" + employmentTracker);
    employmentTracker++;
    if (employmentTracker == 1)    {
        $( "#remove_fields_employment" ).show();
    }
    updateGlyphicons();
    // make tooltips styled properly
    initialise_tool_tip();
}

function removeEmployment() {
    if (employmentTracker > 0) {
        $('div').remove("#employment_" + employmentTracker);
        employmentTracker--;
        if (employmentTracker == 0)    {
            $( "#remove_fields_employment" ).hide();
        }
    }
}

// Javascript functions for carousel 4

function addVolunteer() {
    //Adds in form inputs an additional section of volunteer
    $('<div id="volunteer_' + (volunteerTracker + 1) + '"><div class="form_line_div">' +
        '<label class="form_element_left">Company:</label>' +
        '<input class="form_element_middle" onkeypress="limitToLettersAndNumbers(event)" name="volunteering[' + (volunteerTracker + 1) +
        '][volCompany]" value="" type="text" maxlength="35" data-toggle="tooltip" data-placement="right" title="' +
        tooltips_json.volCompany + '" placeholder="' + example_text_json.volunteerOrganisation + '"/></div>' +
        '<div class="form_line_div">' +
        '<label class="form_element_left">Start date:</label>' +
        '<div class="input-group date form_element_middle  month_year_picker">' +
        '<input onkeydown="return false" type="text" class="form-control" id="volunteering[' + (volunteerTracker + 1) + '][volStartDate]" name="volunteering[' + (volunteerTracker + 1) + '][volStartDate]" value="" data-toggle="tooltip" data-placement="right" title="' +
        tooltips_json.volStartDate + '">' +
        '<span class="input-group-addon">' +
        '<span class="glyphicon glyphicon-calendar"></span></span></div></div>' +
        '<div class="form_line_div">' +
        '<label class="form_element_left">End date:</label>' +
        '<div class="input-group date form_element_middle month_year_picker">' +
        '<input disabled onkeydown="return false" type="text" class="form-control" id= "volunteering[' + (volunteerTracker + 1) + '][volEndDate]" name="volunteering[' + (volunteerTracker + 1) + '][volEndDate]" value="" data-toggle="tooltip" data-placement="right" title="' +
        tooltips_json.volEndDate + '">' +
        '<span class="input-group-addon">' +
        '<span class="glyphicon glyphicon-calendar"></span></span></div></div>' +
        '<div class="form_line_div"><label class="form_element_left">Job Title:</label>' +
        '<input class="form_element_middle" onkeypress="limitToLettersAndNumbers(event)" name="volunteering[' + (volunteerTracker + 1) +
        '][volTitle]" value="" type="text" maxlength="35" data-toggle="tooltip" data-placement="right" title="' +
        tooltips_json.volTitle + '" placeholder="' + example_text_json.volunteerTitle + '"/></div>' +
        '<div class="form_line_div">' +
        '<label class="form_element_left">What did you do there?:</label>' +
        '<textarea class="form_element_middle" onkeypress="limitForTextAreas(event)" name="volunteering[' + (volunteerTracker + 1) +
        '][volDescription]" rows="4" cols="50" maxlength="500" data-toggle="tooltip" data-placement="right" title="' +
        tooltips_json.volDescription + '" placeholder="' + example_text_json.volunteerRole + '"></textarea></div><br></div>').insertAfter( "#volunteer_" + volunteerTracker );
    volunteerTracker++;
    if (volunteerTracker == 1)    {
        $( "#remove_fields_volunteer" ).show();
    }
    updateGlyphicons();
    initialise_tool_tip();
}

function removeVolunteer() {
    if (volunteerTracker > 0) {
        $('div').remove("#volunteer_" + volunteerTracker);
        volunteerTracker--;
        if (volunteerTracker == 0)    {
            $( "#remove_fields_volunteer" ).hide();
        }
    }
}

// Javascript functions for carousel 5

function addEducation() {
    $( '<br><div id="education_' + (educationTracker + 1) + '">' +

    '<div class="form_line_div">' +
      '<label class="form_element_left">Year:</label>' +
        '<div class="input-group date form_element_middle year_picker">' +
        '<input onkeydown="return false" type="text" class="form-control" name="education[' + (educationTracker + 1) +
        '][schoolYear]" value="" data-toggle="tooltip" data-placement="right" title="' +
        tooltips_json.schoolYear + '">' +
        '<span class="input-group-addon">' +
        '<span class="glyphicon glyphicon-calendar"></span></span></div>' +
      addDropdownEducation('education[' + (educationTracker + 1) + '][schoolYearGrade]', tooltips_json.schoolYearGrade) +
      '</div><br>' +
      addSubjects('education[' + (educationTracker + 1) + ']') +
      '</div>' ).insertAfter( "#education_" + educationTracker );
    subjectsTracker.push(defaultEducationSubjects-1);
    educationTracker++;
    if (educationTracker == 1)    {
        $( "#remove_fields_education" ).show();
    }
    updateGlyphicons();
    initialise_tool_tip();
}

function addDropdownEducation(name, tooltip) {
    return '<select name="' + name + '" data-toggle="tooltip" data-placement="right" title="' + tooltip + '">' +
    //        '<option name="' + name + '" value="" selected disabled hidden>Current status (optional)</option>' +
    '<option name="' + name + '" value="">' + '' + '</option>' +
    '<option name="' + name + '" value="Currently studying NCEA Level 1">Currently studying NCEA Level 1</option>' +
    '<option name="' + name + '" value="Currently studying NCEA Level 2">Currently studying NCEA Level 2</option>' +
    '<option name="' + name + '" value="Currently studying NCEA Level 3">Currently studying NCEA Level 3</option>' +
    '<option name="' + name + '" value="NCEA Level 1">NCEA Level 1</option>' +
    '<option name="' + name + '" value="NCEA Level 1 endorsed with Merit">NCEA Level 1 endorsed with Merit</option>' +
    '<option name="' + name + '" value="NCEA Level 1 endorsed with Excellence">NCEA Level 1 endorsed with Excellence</option>' +
    '<option name="' + name + '" value="NCEA Level 2">NCEA Level 2</option>' +
    '<option name="' + name + '" value="NCEA Level 2 endorsed with Merit">NCEA Level 2 endorsed with Merit</option>' +
    '<option name="' + name + '" value="NCEA Level 2 endorsed with Excellence">NCEA Level 2 endorsed with Excellence</option>' +
    '<option name="' + name + '" value="NCEA Level 3">NCEA Level 3</option>' +
    '<option name="' + name + '" value="NCEA Level 3 endorsed with Merit">NCEA Level 3 endorsed with Merit</option>' +
    '<option name="' + name + '" value="NCEA Level 3 endorsed with Excellence">NCEA Level 3 endorsed with Excellence</option>' +
    '</select>';
}

function addSubject(educationIndex) {
    // check the number of subjects is less than twelve
    if (subjectsTracker[educationIndex] < (12 - 1))   {
        $("#removeSubjectButton_" + educationIndex).remove();
        subjectsTracker[educationIndex]++;
        var line = '';
        line += '<div class="form_line_div" id="education_' + educationIndex + '_subjects_' + subjectsTracker[educationIndex] +
        '"><label class="form_element_left">Subject:</label><input class="form_element_middle" value="" type="text" maxlength="40" onkeypress="limitToLetters(event)"' +
        ' name="education[' + educationIndex + '][subjects][' + subjectsTracker[educationIndex] +
        '][subject]" id="subject" data-toggle="tooltip" data-placement="right" title="'+ tooltips_json.classes +'" placeholder="' +
        example_text_json.subject + '">';
        line += addDropdownEducation('education[' + educationIndex + '][subjects][' + subjectsTracker[educationIndex] + '][grade]', tooltips_json.grades);
        line += '<input class="addOrRemoveButton" id="removeSubjectButton_' + educationIndex + '" type="button" value="-" onclick="removeSubject(' + educationIndex + ');"/>';
        line += '</div>';
        $("#education_" + educationIndex ).append(line);
    } else  {
        alert("Sorry you cannot add more than 12 subjects to a single year");
    }
    initialise_tool_tip();
}

function addSubjects(name) {
    var line = '';
    for (var i = 0; i < defaultEducationSubjects; i++) {
        line += '<div class="form_line_div" id="education_' + (educationTracker + 1) + '_subjects_' + i +'"><label class="form_element_left">Subject:</label><input class="form_element_middle"' +
        'value="" type="text" onkeypress="limitToLetters(event)" name="' + name + '[subjects][' + i + '][subject]" id="subject" maxlength="40" data-toggle="tooltip" data-placement="right" title="'+ tooltips_json.classes +'"' +
        ' placeholder="' + example_text_json.subject + '">';
        line += addDropdownEducation(name + '[subjects][' + i + '][grade]', tooltips_json.grades);
        if (i == 0)  {
            line += '<input class="addOrRemoveButton" id="addSubjectButton_' + (educationTracker + 1) + '" type="button" value="+" onclick="addSubject(' + (educationTracker + 1) + ');"/>';
        } else if (i == defaultEducationSubjects - 1) {
            line += '<input class="addOrRemoveButton" id="removeSubjectButton_' + (educationTracker + 1) + '" type="button" value="-" onclick="removeSubject(' + (educationTracker + 1) + ');"/>';
        }
        line += '</div>';
    }
    return line;
}

function removeSubject(educationIndex) {
    // decerment the number of subjects in the array for the particular deducation div
    $('div').remove('#education_' + educationIndex + '_subjects_' + subjectsTracker[educationIndex]);
    subjectsTracker[educationIndex]--;
    if (subjectsTracker[educationIndex] > 0) {
        $('#education_' + educationIndex + '_subjects_' + (subjectsTracker[educationIndex])).append('<input class="addOrRemoveButton" id="removeSubjectButton_' + educationIndex + '" type="button" value="-" onclick="removeSubject(' + educationIndex + ');"/>');
    }
    // $('education[' + educationIndex + '][subjects][' + subjectsTracker[educationIndex] + '][grade]').insertAfter('<input id="removeSubjectButton_' + educationIndex + '" type="button" value="-" onclick="removeSubject(' + educationIndex + ');"/>');
}

function removeEducation() {
    if (educationTracker > 0) {
        $('div').remove("#education_" + educationTracker);
        educationTracker--;
        if (educationTracker == 0)    {
            $( "#remove_fields_education" ).hide();
        }
    }
}

// Javascript functions for carousel 7

function addAchievements() {
    //Adds in form inputs an additional section of achievements
    $('<div id="achievements_' + (achievementsTracker + 1) + '"><div class="form_line_div">' +
        '<label class="form_element_left">Year</label>' +
        '<div class="input-group date form_element_middle year_picker">' +
        '<input onkeydown="return false" type="text" class="form-control" name="awards[' + (achievementsTracker + 1) + '][awardYear]" value="" data-toggle="tooltip" data-placement="right" title="' +
        tooltips_json.awardYear + '">' +
        '<span class="input-group-addon">' +
        '<span class="glyphicon glyphicon-calendar"></span></span></div></div>' +
        '<div class="form_line_div">' +
        '<label class="form_element_left">Award</label>' +
        '<textarea class="form_element_middle" onkeypress="limitForTextAreas(event)" name="awards[' + (achievementsTracker + 1) + '][award]" rows="4" cols="50" maxlength="250" data-toggle="tooltip" data-placement="right" title="' +
        tooltips_json.award + '" placeholder="' +
        example_text_json.award + '"></textarea></div><br></div>').insertAfter( "#achievements_" + achievementsTracker );
        //'<input class="form_element_middle" name="awards[' + (achievementsTracker + 1) + '][award]" value="" type="text"></div><br></div>').insertAfter( "#achievements_" + achievementsTracker );
    achievementsTracker++;
    if (achievementsTracker == 1)    {
        $( "#remove_fields_achievements" ).show();
    }
    updateGlyphicons();
    initialise_tool_tip();
}

// This function is what is run when the remove Last Entry button is pressed

function removeAchievements() {
    if (achievementsTracker > 0) {
        $('div').remove("#achievements_" + achievementsTracker);
        achievementsTracker--;
        if (achievementsTracker == 0)    {
            $( "#remove_fields_achievements" ).hide();
        }
    }
}

function addExtracurricular() {
    //Adds in form inputs an additional section of extracurricular
    //alert(extracurricularTracker);
    $( '<div id="extracurricular_' + (extracurricularTracker + 1) + '"><div class="form_line_div">' +
        '<label class="form_element_left">Year</label>' +
        '<div class="input-group date form_element_middle year_picker">' +
        '<input onkeydown="return false" type="text" class="form-control" name="extracur[' + (extracurricularTracker + 1) + '][extracurYear]" value="" data-toggle="tooltip" data-placement="right" title="' +
        tooltips_json.extracurYear + '">' +
        '<span class="input-group-addon">' +
        '<span class="glyphicon glyphicon-calendar"></span></span></div></div>' +
        '<div class="form_line_div">' +
        '<label class="form_element_left">Activity</label>' +
        '<textarea class="form_element_middle" onkeypress="limitForTextAreas(event)" name="extracur[' + (extracurricularTracker + 1) + '][activity]" rows="4" cols="50" maxlength="250" data-toggle="tooltip" data-placement="right" title="' +
        tooltips_json.activity + '" placeholder="' +
        example_text_json.extraCurrActivity + '"></textarea></div><br></div>' ).insertAfter( "#extracurricular_" + extracurricularTracker );
        //'<input class="form_element_middle" name="extracur[' + (extracurricularTracker + 1) + '][activity]" value="" type="text"></div><br></div>' ).insertAfter( "#extracurricular_" + extracurricularTracker );

    extracurricularTracker++;
    if (extracurricularTracker == 1)    {
        $( "#remove_fields_extracurricular" ).show();
    }
    updateGlyphicons();
    initialise_tool_tip();
}

function removeExtracurricular() {
    if (extracurricularTracker > 0) {
        $('div').remove("#extracurricular_" + extracurricularTracker);
        extracurricularTracker--;
        if (extracurricularTracker == 0)    {
            $( "#remove_fields_extracurricular" ).hide();
        }
    }
}

function addReferees() {
    //Adds in form inputs an additional section of referees
    $('<div id="referees_' + (refereesTracker + 1) + '"><div class="form_line_div">' +
        '<label class="form_element_left">Name</label>' +
        '<input class="form_element_middle" onkeypress="limitToLetters(event)" name="referees[' + (refereesTracker + 1) + '][refName]" value="" type="text" maxlength="50" data-toggle="tooltip" data-placement="right" title="' +
        tooltips_json.refName + '" placeholder="' +
        example_text_json.refName + '"></div>' +
        '<div class="form_line_div"><label class="form_element_left">Position</label>' +
        '<input class="form_element_middle" onkeypress="limitToLettersAndNumbers(event)" name="referees[' + (refereesTracker + 1) + '][refPosition]" value="" type="text" maxlength="35" data-toggle="tooltip" data-placement="right" title="' +
        tooltips_json.refPosition + '" placeholder="' +
        example_text_json.refPos + '"></div>' +
        '<div class="form_line_div"><label class="form_element_left">Email</label>' +
        '<input class="form_element_middle" onkeypress="limitToEmails(event)" name="referees[' + (refereesTracker + 1) + '][refEmail]" value="" type="text" maxlength="50" data-toggle="tooltip" data-placement="right" title="' +
        tooltips_json.refEmail + '"placeholder="' +
        example_text_json.refEmail + '"></div>' +
        '<div class="form_line_div"><label class="form_element_left">Number</label>' +
        '<input class="form_element_middle" onkeypress="limitToPhoneNumbers(event)" name="referees[' + (refereesTracker + 1) + '][refNum]" value="" type="text" maxlength="25" data-toggle="tooltip" data-placement="right" title="' +
        tooltips_json.refNum + '"placeholder="' +
        example_text_json.refNum + '"></div><br></div>').insertAfter( "#referees_" + refereesTracker );
    refereesTracker++;
    if (refereesTracker == 1)    {
            $( "#remove_fields_referees" ).show();
    }
    initialise_tool_tip();
}

// This function removes a referee entry

function removeReferees() {

    if (refereesTracker > 0)    {
        $('div').remove("#referees_" + refereesTracker);
        refereesTracker--;
        if (refereesTracker == 0)    {
            $( "#remove_fields_referees" ).hide();
        }
    }
}