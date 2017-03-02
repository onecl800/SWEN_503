<div class="item active">
    <!--form id="form" action="save_createPDF.php" method="post"-->
    <input type="hidden" name="id" value="<?php echo $id; ?>"/>
    <div id="slide1" class="wizard_content">
        <div class="form_fields">
            <div class="form_line_div">
                <h2>Personal Information</h2>
            </div>
            <div class="form_line_div">
                <p>It is very important that the details in this section are 100% correct.</p>
            </div>
            <div class="form_line_div">
                <label class="form_element_left" id="req">First Name:</label>
                <input class="form_element_middle" name="firstName" type="text" maxlength="25" value="<?php echo $firstName; ?>" data-toggle="tooltip" data-placement="right" title="<?php echo $tooltips_json['firstName']; ?>" placeholder="<?php echo $examples_json['firstName'] ?>"/>
            </div>

            <div class="form_line_div">
                <label class="form_element_left" id="req">Last Name:</label>
                <input class="form_element_middle" name="lastName" type="text" maxlength="25" value="<?php echo $lastName; ?>" data-toggle="tooltip" data-placement="right" title="<?php echo $tooltips_json['lastName']; ?>" placeholder="<?php echo $examples_json['lastName'] ?>"/>
            </div>

            <div class="form_line_div">
                <label class="form_element_left">Age:</label>
                <input class="form_element_middle" name="age" type="text" maxlength="2" value="<?php echo $age; ?>" data-toggle="tooltip" data-placement="right" title="<?php echo $tooltips_json['age']; ?>"
                placeholder="<?php echo $examples_json['age'] ?>"/>
            </div>


            <?php
            //loading multiple languages
            $languageTracker = 0;
            if ($loadData) {
                //loading languages and adding extra fields when needed
                foreach ($user_json['languages'] as $languageObj) {
                    if ($languageTracker == 0) {
                        echo '<div class="form_line_div" id="language_0">';
                        echo '<label class="form_element_left">Languages:</label>';
                        echo '<input id="language" class="form_element_middle" onkeypress="limitToLetters(event)" name="languages[0][language]" type="text" maxlength="25" value="'.$languageObj['language'].'" data-toggle="tooltip" data-placement="right" title="'.$tooltips_json['languages'].'" placeholder="'.$examples_json['languages'].'"/>';
                        echo '<input class="addOrRemoveButton" id="addLanguageButton" type="button" value="+" onclick="addLanguage();"/>'; //add the + button next to the first language
                        echo '</div>';
                    } else {
                        //loading more languages field without the + button
                        echo '<div class="form_line_div" id="language_'.$languageTracker.'">';
                        echo '<label class="form_element_left"></label>';
                        echo '<input id="language" class="form_element_middle" onkeypress="limitToLetters(event)" name="languages['.$languageTracker.'][language]" type="text" maxlength="25" value="'.$languageObj['language'].'" data-toggle="tooltip" data-placement="right" title="'.$tooltips_json['languages'].'" placeholder="'.$examples_json['languages'].'"/>';
                        if ($languageTracker == count($user_json['languages']) - 1) {
                            echo '<div id="removeLanguageButton"><input class="addOrRemoveButton" type="button" value="-" onclick="removeLanguage();"/></div>';
                        }
                        echo '</div>';
                    }
                    ++$languageTracker;
                }
                --$languageTracker;
            } else {
                echo '<div class="form_line_div" id="language_0">';
                echo '<label class="form_element_left">Languages:</label>';
                echo ' <input id="language" class="form_element_middle" onkeypress="limitToLetters(event)" name="languages[0][language]" type="text" maxlength="25" value="" data-toggle="tooltip" data-placement="right" title="'.$tooltips_json['languages'].'" placeholder="'.$examples_json['languages'].'" />';
                echo '<input class="addOrRemoveButton" id="addLanguageButton" type="button" value="+" onclick="addLanguage();"/>';
                echo '</div>';
            }

            echo '<script>var languageTracker = '.$languageTracker.';</script>'; //adding the languageTracker variable for javacript
            ?>

            <div class="form_line_div">
                <label class="form_element_left" id="req">School:</label>
                <input class="form_element_middle" name="school" type="text" maxlength="35" value="<?php echo $school; ?>" data-toggle="tooltip" data-placement="right" title="<?php echo $tooltips_json['school']; ?>" placeholder="<?php echo $examples_json['school'] ?>"/><br>
            </div>

            <div class="form_line_div">
                <label class="form_element_left">Drivers License Held:</label>
                <!-- how do we populate this from the java script? -->
                <!--<input class="form_element_middle" name="license" required type="text" value="<?php echo $license; ?>"/><br> -->

                <!--This is the drop down for the license held part. Remove the old input part and paste in this after the label-->
                <select class="form_element_middle" id="license_drop_down" name="license" data-toggle="tooltip" data-placement="right" title="<?php echo $tooltips_json['license']; ?>">
                    <option name='license' value="No License" selected="selected">No License</option>
                    <option name='license' value="Learner's License" <?php if ($license == 'Learner\'s License') {
                echo 'selected="selected"';
            } ?>>Learner's License</option>
                    <option name='license' value="Restricted License" <?php if ($license == 'Restricted License') {
                echo 'selected="selected"';
            } ?>>Restricted License</option>
                    <option name='license' value="Full License" <?php if ($license == 'Full License') {
                echo 'selected="selected"';
            } ?>>Full License</option>
                </select>
            </div>

            <div class="form_line_div">
                <label class="form_element_left" id="req">Phone Number:</label>
                <input class="form_element_middle" name="phoneNum" type="text" maxlength="25" value="<?php echo $phoneNum; ?>" data-toggle="tooltip" data-placement="right" title="<?php echo $tooltips_json['phoneNum']; ?>" placeholder="<?php echo $examples_json['phoneNum'] ?>"/>
            </div>

            <div class="form_line_div">
                <label class="form_element_left" id="req">Email:</label>
                <input class="form_element_middle" name="mail" type="text" maxlength="50" value="<?php echo $mail; ?>" data-toggle="tooltip" data-placement="right" title="<?php echo $tooltips_json['mail']; ?>" placeholder="<?php echo $examples_json['email'] ?>"/>
            </div>

            <div class="form_line_div">
                <label class="form_element_left">Suburb:</label>
                <input class="form_element_middle" name="suburb" type="text" maxlength="25" value="<?php echo $suburb; ?>" data-toggle="tooltip" data-placement="right" title="<?php echo $tooltips_json['suburb']; ?>" placeholder="<?php echo $examples_json['suburb'] ?>"/>
            </div>

            <div class="form_line_div">
                <label class="form_element_left" id="req">City:</label>
                <input class="form_element_middle" name="city" type="text" maxlength="25" value="<?php echo $city; ?>" data-toggle="tooltip" data-placement="right" title="<?php echo $tooltips_json['city']; ?>" placeholder="<?php echo $examples_json['city'] ?>"/>
            </div>

            <div class="form_line_div">
                <label class="form_element_left">Country:</label>
                <!--Create country drop down list-->
                <select class="form_element_middle" data-toggle="tooltip" name="country" data-placement="right" title="<?php echo $tooltips_json['country']; ?>">
                    <?php

                    //loading countries from text file for dropdown list
                    if ($file = fopen('countrydropdown.txt', 'r')) {
                        while (!feof($file)) {
                            $line = fgets($file);
                            $line = preg_replace('/\s+/', ' ', trim($line));
                            if ($country == $line) {
                                //load default country
                                echo '<option name="country" selected = "selected">'.$line.'</option>';
                            } else {
                                //adding country to dropdown
                                echo '<option name="country">'.$line.'</option>';
                            }
                        }
                        fclose($file);
                    }
                    ?>
                </select>


            </div>
        </div>
        <div class="carousel_bottom_nav_bar">
            <!-- <button id="left_button" disabled>Back</button> -->
            <button id="middle_button" name="submit" value="save" onclick="saveQualities(false);">Save & Exit</button>
            <button id="right_button" data-target="#wizard_pages" data-slide-to="1">Next</button>
        </div>
        <!--Close wizard content div-->
    </div>
    <!--Close slide 1 div-->
</div>
