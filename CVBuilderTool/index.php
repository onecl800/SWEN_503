<!DOCTYPE html>
<html lang="en-NZ">

<head>
    <meta charset="UTF-8">
    <title>BluePrint CV Builder</title>
    <link href="./bootstrap-3.3.7/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="create_cv.css" rel="stylesheet"/>

</head>

<body>
    <div class="container-fluid">
        <!-- Bootstrap Grid -->
        <div class="row" id="top_nav_bar">
        
                <div class="col-xs-2 col-lg-2" id="top_nav_bar_left_area">
                    <p id="top_nav_bar_left_title">CV Builder Tool</p>
                </div>
                <div class="col-xs-8 col-lg-8" id="top_nav_bar_middle_area_landing">

                </div>
                <div class="col-xs-2 col-lg-2" id="top_nav_bar_right_area">
                    
                </div>
        </div>

        <div class="row" id="carousel_area">
            <div id="middle_content" class="wizard_content  main_area">

                <div class="form_line_div">
                    <h1>Enter testing student ID</h1>
                </div>

                <div>
                    <form action="./landing_page.php" method="POST">
                        <div class="form_line_div">
                            <label id="student_word" for="inputid">Student ID:</label>

                            <input  type="number" id="inputid" name="id"/>
                        </div>
                        <!-- This field is for demonstration purposes only. On the final system, the user's student ID will already
                        be recorded when they sign in. As such, no login authentication is provided at this stage. -->
                        <div class="form_line_div" id="buttons">
                            <input type="submit" id="left_button" value="Test" name="action"/>
                        </div>
                        <!-- Edit CV will lead to a CV pre-populated with the information that a student has previously entered.
                        Under ideal functionality, a user would be able to save multiple CVs and choose which they want to load from. As it currently stands, whenever a user saves their CV it over-writes any other prior versions. -->
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>