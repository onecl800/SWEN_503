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
                <div id="middle_content" class="wizard_content main_area">
                    
                    <div class="form_line_div" id="word_content_area">
                        <p id="words">Are you looking for work while you study? Maybe once you’ve finished?
                        Or does a course you’re applying for require a CV?
                        We’ve got you covered with BluePrint’s CV builder.
                        We’ll guide you through the different sections you’ll need to write a CV - all you need to do is fill them out. No need to worry about formatting - we do all the design work for you. Once you’re done we will provide you with a CV (in PDF format) which you can save and print, ready to start applying for jobs!</p>
                    </div>
                    
                    <div class="form_line_div">
                        <!-- <h1>BluePrint CV Builder</h1> -->
                    </div>
                    
                    <div>
                        <form action="./create_cv.php" method="POST">

                            <!-- This field is for demonstration purposes only. On the final system, the user's student ID will already
                             be recorded when they sign in. As such, no login authentication is provided at this stage. -->
                            <input type="hidden" name="id" value="<?php echo $_POST['id']; ?>">
                            <div class="form_line_div" id="buttons">
                                <input type="submit" id="left_button" value="Create CV" name="submit">
                                <!-- Create CV button will lead to an entirely clean edit form. -->
                                <input type="submit" id="middle_button" value="Edit CV" name="submit" <?php if (!file_exists('./users/' . $_POST['id'] . '/' . $_POST['id'] . '.json')) { echo ' disabled';}?>/>
                            </div>
                            <!-- Edit CV will lead to a CV pre-populated with the information that a student has previously entered.
                             Under ideal functionality, a user would be able to save multiple CVs and choose which they want to load from. As it currently stands, whenever a user saves their CV it over-writes any other prior versions. -->
                            
                        </form>
                    </div>
                </div>
            </div>
        </div>
        
    </body>
</html>