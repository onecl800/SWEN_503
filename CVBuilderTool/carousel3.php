<div class="item">
    <div id="slide3" class="wizard_content">
        <div class="form_fields">
            <h2>Employment (optional)</h2>
            <p>Although this section is optional, it is probably the most important. Always start with the most recent experience first.</p>
            <div id="employmentDiv">
                <?php
                //auto populate all employment fields on loading of page
                $employmentTracker = 0;
                if ($loadData) {
                    //loading all employment data
                    foreach ($user_json['employment'] as $employmentObj) {
                        echo '<div id="employment_' . $employmentTracker . '">';
                        echo '<div class="form_line_div"><label class="form_element_left">Company:</label><input class="form_element_middle" onkeypress="limitToLettersAndNumbers(event)" name="employment[' . $employmentTracker . '][jobCompany]" type="text" maxlength="35" value="' . $employmentObj['jobCompany'] . '" data-toggle="tooltip" data-placement="right" title="' . $tooltips_json['jobCompany'] . '" placeholder="' . $examples_json['company'] .'"/></div>';
                        echo '<div class="form_line_div"><label class="form_element_left">Start date:</label>';
                        echo addMonthYearPicker('employment['.$employmentTracker.'][startDate]', $employmentObj['startDate'], $tooltips_json['startDate']);
                        echo '</div>';
                        echo '<div class="form_line_div"><label class="form_element_left">End date:</label>';
                        if (isset($employmentObj['endDate'])) {
                            echo addMonthYearPicker('employment['.$employmentTracker.'][endDate]', $employmentObj['endDate'], $tooltips_json['endDate']);
                        } else {
                            echo addMonthYearPickerDisabled('employment['.$employmentTracker.'][endDate]', '', $tooltips_json['endDate']);
                        }
                        
                        echo '</div>';
                        echo '<div class="form_line_div"><label class="form_element_left">Job Title:</label><input onkeypress="limitToLettersAndNumbers(event)" class="form_element_middle" name="employment[' . $employmentTracker . '][title]" type="text" maxlength="35" value="' . $employmentObj['title'] . '" data-toggle="tooltip" data-placement="right" title="' . $tooltips_json['title'] . '" placeholder="' . $examples_json['jobTitle'] .'"/></div>';
                        echo '<div id=""><div class="form_line_div"><label class="form_element_left" id="high_text_field">What did you do there?:</label><textarea class="form_element_middle" onkeypress="limitForTextAreas(event)" name="employment[' . $employmentTracker . '][jobDescription]" maxlength="500" rows="4" cols="50" data-toggle="tooltip" data-placement="right" title="' . $tooltips_json['jobDescription'] . '" placeholder="' . $examples_json['jobRole'] . '"/>' . $employmentObj['jobDescription'] . '</textarea></div></div>';
                        echo '<br></div>';
                        ++$employmentTracker;
                    }
                    --$employmentTracker;
                } else {
                    //loading default empty employment data
                    echo '<div id="employment_' . $employmentTracker . '">';
                    echo '<div class="form_line_div"><label class="form_element_left">Company:</label><input class="form_element_middle" onkeypress="limitToLettersAndNumbers(event)" name="employment[0][jobCompany]" type="text" maxlength="35" data-toggle="tooltip" data-placement="right" title="' . $tooltips_json['jobCompany'] . '" placeholder="' . $examples_json['company'] .'"/></div>';
                    echo '<div class="form_line_div"><label class="form_element_left">Start date:</label>';
                    echo addMonthYearPicker('employment[0][startDate]', '', $tooltips_json['startDate']);
                    echo '</div>';
                    echo '<div class="form_line_div"><label class="form_element_left">End date:</label>';
                    echo addMonthYearPickerDisabled('employment[0][endDate]', '', $tooltips_json['endDate']);
                    echo '</div>';
                    echo '<div class="form_line_div"><label class="form_element_left">Job Title:</label><input class="form_element_middle" onkeypress="limitToLettersAndNumbers(event)" name="employment[0][title]" type="text" maxlength="35" data-toggle="tooltip" data-placement="right" title="' . $tooltips_json['title'] . '" placeholder="' . $examples_json['jobTitle'] .'"/></div>';
                    echo '<div id=""><div class="form_line_div"><label class="form_element_left">What did you do there?:</label><textarea class="form_element_middle" onkeypress="limitForTextAreas(event)" name="employment[0][jobDescription]" rows="4" cols="50" maxlength="500" data-toggle="tooltip" data-placement="right" title="' . $tooltips_json['jobDescription'] . '" placeholder="' . $examples_json['jobRole'] .'"/></textarea></div></div>';
                    echo '<br></div>';
                }

                echo '<script>var employmentTracker = ' . $employmentTracker . ';</script>'; //variable for javascript
                ?>
            </div>
            <div class="form_line_div">
                <input type="button" class="add_or_remove_fields_button" id="more_fields_employment" onclick="addEmployment();" value="Add Another Entry"/>
                <!-- Load the remove button as hidden if employment tracker is zero as this means there is only one entry -->
                <?php
                if ($employmentTracker == 0)    {
                    echo '<input type="button" class="add_or_remove_fields_button" id="remove_fields_employment" onclick="removeEmployment();" value="Remove Last Entry" hidden/>';
                } else {
                    echo '<input type="button" class="add_or_remove_fields_button" id="remove_fields_employment" onclick="removeEmployment();" value="Remove Last Entry"/>';
                }
                ?>
            </div>
        </div>
        <div class="carousel_bottom_nav_bar">
            <button id="left_button" data-target="#wizard_pages" data-slide-to="1">Back</button>
            <button id="middle_button" name="submit" value="save" onclick="saveQualities(false);">Save & Exit</button>
            <button id="right_button" data-target="#wizard_pages" data-slide-to="3">Next</button>
        </div>
    </div>
</div>
