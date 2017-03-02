<div class="item">
    <div id="slide6" class="wizard_content">
        <div class="form_fields">
            <div class="form_line_div">
                <h2 id="req">Personal Attributes</h2>
            </div>
            <div class="form_line_div">
                <p>Pick up to 6 attributes that best describe you to future employers</p>
            </div>

            <?php
            function loadQualities($value)
            {
                //checks load the qualities field from json and echo 'checked' if the field is checked previously
                //this function is to auto check quality boxes on load
                global $loadData; //use $loaData from global variable
                global $user_json; //use $user_json from global variable
                if ($loadData && isset($user_json['qualities'])) { //making sure user clicked edit cv and 'qualities' key existed in json file
                    foreach ($user_json['qualities'] as $qualityObj) { //loop through all qualities object
                        if ($value == $qualityObj['quality']) { //check if the box value equals the quality field
                            return ' checked '; //echo to html
                        }
                    }
                }

                return '';
            }

            ?>


            <div class="form_line_div" id="personal_attributes_container">

                <?php

                $qualitiesList = ['Accurate', 'Adaptable', 'Alert', 'Ambitious', 'Amiable', 'Analytical', 'Articulate', 'Assertive', 'Attentive', 'Broad-minded', 'Businesslike', 'Calm', 'Capable',
                    'Careful', 'Competent', 'Confident', 'Conscientious', 'Consistent', 'Cooperative', 'Dedicated', 'Dependable', 'Determined', 'Efficient', 'Energetic', 'Enterprising', 'Flexible',
                    'Hardworking', 'Honest', 'Independent', 'Industrious', 'Innovative', 'Motivated', 'Optimistic', 'Organized', 'Patient', 'People-oriented', 'Persevering', 'Practical', 'Productive',
                    'Realistic', 'Reliable', 'Resourceful', 'Responsible', 'Versatile'];
                $qualitiesTracker = 0;
                foreach ($qualitiesList as $quality) {
                    $checkedState = loadQualities($quality);
                    if ($checkedState == '' && count($user_json['qualities']) == 6) {
                        $toggledState = ' color_toggled_grey';
                    } else if ($checkedState == '' && count($user_json['qualities']) < 6) {
                        $toggledState = '';
                    } else if ($checkedState == ' checked ') {
                        $qualitiesTracker++;
                        $toggledState = ' color_toggled_on';
                    }
                    echo '<div class="personal_attribute' . $toggledState . '" id="' . lcfirst($quality) . '_box"><div class="form_line_div attribute attribute_top"><p id="quality_text"></p><p class="quality_text_class" id="' . lcfirst($quality) . '">' . $quality . '</p></div>';
                    echo '<div class="form_line_div attribute attribute_bottom"><div class="attribLong" id="' . lcfirst($quality) . 'Long">' . $tooltips_json[lcfirst($quality)] . '</div></div>';
                    echo '<input type="checkbox" id="' . lcfirst($quality) . '_checkbox" name="quality"  value="' . $quality . '" style="display:none"' . $checkedState . '/>';
                    echo '</div>';
                }

                echo '<script>qualitiesTracker = ' . $qualitiesTracker . ';</script>';
                ?>

            </div>
        </div>
        <div class="carousel_bottom_nav_bar">
            <button id="left_button" data-target="#wizard_pages" data-slide-to="4">Back</button>
            <button id="middle_button" name="submit" value="save" onclick="saveQualities(false);">Save & Exit</button>
            <button id="right_button" data-target="#wizard_pages" data-slide-to="6">Next</button>
        </div>
    </div>
</div>
