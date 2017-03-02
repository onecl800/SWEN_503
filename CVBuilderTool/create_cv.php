<!--PHP code for loading values when called via load button on front page-->
<?php
$createString = 'Create New'; //string to match button text in index.php
$loadString = 'Edit CV'; //string to match button text in index.php
$loadData = false;

//loading tooltips.json
$tooltips_json = json_decode(file_get_contents('tooltips.json'), true); //loading tool tips from json file

//loading example.json
$examples_json = json_decode(file_get_contents('example.json'), true); //loading examples from json file

$id = $_POST['id']; //global $id variable

//Variables that are strings
$firstName = $lastName = $suburb = $city = $school = ''; //global single input field variables
$license = ''; //global single input field variables

//Variables that are numbers
$age = $phoneNum = ''; //global single input field variables

// Variables that are a TextArea
$statement = ''; //global single input field variables

// Variables that are an email address
$mail = ''; //global single input field variables

// Variables that have a default value set

$country = 'New Zealand'; //default country

if ($_POST['submit'] == $loadString) {
    //loading of global single input variables if user use edit cv button
    $loadData = true;
    $filename = 'users/' . $id . '/' . $id . '.json';
    $file = file_get_contents($filename);
    $user_json = json_decode($file, true); //loading ID.json file as a variable

    $firstName = $user_json['firstName'];
    $lastName = $user_json['lastName'];
    $suburb = $user_json['suburb'];
    $city = $user_json['city'];
    $country = $user_json['country'];
    $school = $user_json['school'];
    $age = $user_json['age'];
    $phoneNum = $user_json['phoneNum'];
    $statement = $user_json['statement'];
    $mail = $user_json['mail'];
    $license = $user_json['license'];
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>CV-Tool</title>

    <!-- Bootstrap -->
    <link href="./bootstrap-3.3.7/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="./bootstrap-datepicker/css/bootstrap-datepicker.css" rel="stylesheet"/>
    <link href="create_cv.css" rel="stylesheet"/>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="./bootstrap-3.3.7/dist/css/bootstrap.min.js"></script>
    <script src="./bootstrap-3.3.7/dist/js/bootstrap.min.js"></script>
    <![endif]-->

    <!--                        CSS internal-->
</head>
<body>
<!-- <h1 id="title" >Welcome to the awesome CV Builder!</h1> -->
<div class="container-fluid">
    <form id="form" name="myForm" onsubmit="return validateForm();" action="save_createPDF.php" method="post">
        <!-- Bootstrap Grid -->
        <div class="row" id="top_nav_bar">
            <div class="col-xs-2 col-lg-2" id="top_nav_bar_left_area">
                <p id="top_nav_bar_left_title">CV Builder Tool</p>
            </div>
            <div class="col-xs-8 col-lg-8" id="top_nav_bar_middle_area">
                <!-- <div id="top_nav_bar_start_middle"></div> -->
                <button class="top_nav_bar_button_active" id="details_button" data-target="#wizard_pages" data-slide-to="0">Personal Details</button>
                <button class="top_nav_bar_button_inactive" id="statement_button" data-target="#wizard_pages" data-slide-to="1">Personal Statement</button>
                <button class="top_nav_bar_button_inactive" id="employment_button" data-target="#wizard_pages" data-slide-to="2">Employment</button>
                <button class="top_nav_bar_button_inactive" id="volunteer_button" data-target="#wizard_pages" data-slide-to="3">Volunteer Work</button>
                <button class="top_nav_bar_button_inactive" id="education_button" data-target="#wizard_pages" data-slide-to="4">Education</button>
                <button class="top_nav_bar_button_inactive" id="attributes_button" data-target="#wizard_pages" data-slide-to="5">Personal Attributes</button>
                <button class="top_nav_bar_button_inactive" id="awards_button" data-target="#wizard_pages" data-slide-to="6">Achievements & Awards</button>
                <button class="top_nav_bar_button_inactive" id="referees_button" data-target="#wizard_pages" data-slide-to="7">Referees</button>
            </div>
            <div class="col-xs-2 col-lg-2" id="top_nav_bar_right_area">
                <button id="finish_button_top_corner" name="submit" value="finish" onclick="saveQualities(true);">Finish</button>
            </div>
        </div>
        <div class="row" id="carousel_area">
            <!-- Carousel container -->
            <div id="wizard_pages" class="carousel slide" data-interval="false" data-ride="carousel" data-wrap="false"
                 style="width:100%;margin:auto;">

                <!-- Indicators -->
                <ol class="carousel-indicators">
                    <li data-target="#wizard_pages" data-slide-to="0" class="active"></li>
                    <li data-target="#wizard_pages" data-slide-to="1"></li>
                    <li data-target="#wizard_pages" data-slide-to="2"></li>
                    <li data-target="#wizard_pages" data-slide-to="3"></li>
                    <li data-target="#wizard_pages" data-slide-to="4"></li>
                    <li data-target="#wizard_pages" data-slide-to="5"></li>
                    <li data-target="#wizard_pages" data-slide-to="6"></li>
                    <li data-target="#wizard_pages" data-slide-to="7"></li>
                </ol>

                <!-- Content -->

                <div class="carousel-inner" role="listbox">

                    <?php
                    include 'datepicker.php';

                    //inserting each carousel slide into the page
                    //<!-- Slide 1 -->
                    include 'carousel1.php';

                    //<!-- Slide 2 -->
                    include 'carousel2.php';

                    //<!-- Slide 3 -->
                    include 'carousel3.php';

                    //<!-- Slide 4 -->
                    include 'carousel4.php';

                    //<!-- Slide 5 -->
                    include 'carousel5.php';

                    //<!-- Slide 6 -->
                    include 'carousel6.php';

                    //<!-- Slide 7 -->
                    include 'carousel7.php';

                    //<!-- Slide 8 -->
                    include 'carousel8.php';
                    ?>

                </div>
            </div>
        </div>
    </form>
    <!-- Previous/Next controls -->
    <a class="left carousel-control" href="#wizard_pages" role="button" data-slide="prev">
        <span class="icon-prev" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
    </a>
    <a class="right carousel-control" href="#wizard_pages" role="button" data-slide="next">
        <span class="icon-next" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
    </a>
</div>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<!--        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>-->
<script src="./jquery-3.1.1/jquery-3.1.1.js"></script>
<script src="./moment/moment.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="./bootstrap-3.3.7/dist/js/bootstrap.min.js"></script>
<script src="./bootstrap-3.3.7/js/transition.js"></script>
<script src="./bootstrap-3.3.7/js/collapse.js"></script>
<script src="./bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script src="create_cv.js"></script>
<script src="button_function.js"></script>
<script src="carousel.js"></script>
<script src="datepicker.js"></script>
</div>
</body>
</html>