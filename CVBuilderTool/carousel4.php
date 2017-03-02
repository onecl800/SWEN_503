<div class="item">
    <div id="slide4" class="wizard_content">
        <div class="form_fields">
            <div class="form_line_div">
                <h2>Volunteer Work (optional)</h2>
            </div>
            <div class="form_line_div">
                <p>This is similar to your employment history. It's not manadatory, but looks really good to potential employers.</p>
            </div>

            <div id="volunteerDiv">
                <?php
                //auto populate all volunteer fields on loading of page
                $volunteerTracker = 0;
                if ($loadData) {
                    //loading all volunteer data
                    foreach ($user_json['volunteering'] as $volunteeringObj) {
                        echo '<div id="volunteer_' . $volunteerTracker . '">';
                        echo '<div class="form_line_div"><label class="form_element_left">Company:</label><input class="form_element_middle" onkeypress="limitToLettersAndNumbers(event)" name="volunteering[' . $volunteerTracker . '][volCompany]" value="' . $volunteeringObj['volCompany'] . '" type="text" maxlength="35" data-toggle="tooltip" data-placement="right" title="' . $tooltips_json['volCompany'] . '" placeholder="' . $examples_json['volunteerOrganisation'] .'"/></div>';
                        echo '<div class="form_line_div"><label class="form_element_left">Start date:</label>';
                        echo addMonthYearPicker('volunteering['.$volunteerTracker.'][volStartDate]', $volunteeringObj['volStartDate'], $tooltips_json['volStartDate']);
                        echo '</div>';
                        echo '<div class="form_line_div"><label class="form_element_left">End date:</label>';
                        if (isset($volunteeringObj['volEndDate'])) {
                            echo addMonthYearPicker('volunteering['.$volunteerTracker.'][volEndDate]', $volunteeringObj['volEndDate'], $tooltips_json['volEndDate']);
                        } else {
                            echo addMonthYearPickerDisabled('volunteering['.$volunteerTracker.'][volEndDate]', '', $tooltips_json['volEndDate']);
                        }
                        echo '</div>';
                        echo '<div class="form_line_div"><label class="form_element_left">Job Title:</label><input class="form_element_middle" onkeypress="limitToLettersAndNumbers(event)" name="volunteering[' . $volunteerTracker . '][volTitle]" value="' . $volunteeringObj['volTitle'] . '" type="text" maxlength="35" data-toggle="tooltip" data-placement="right" title="' . $tooltips_json['volTitle'] . '" placeholder="' . $examples_json['volunteerTitle'] .'" /></div>';
                        echo '<div class="form_line_div"><label class="form_element_left">What did you do there?:</label><textarea class="form_element_middle" onkeypress="limitForTextAreas(event)" name="volunteering[' . $volunteerTracker . '][volDescription]" value="" rows="4" cols="50" maxlength="500" data-toggle="tooltip" data-placement="right" title="' . $tooltips_json['volDescription'] . '" placeholder="' . $examples_json['volunteerRole'] .'">' . $volunteeringObj['volDescription'] . '</textarea></div>';
                        echo '<br></div>';
                        ++$volunteerTracker;
                    }
                    --$volunteerTracker;
                } else {
                    //loading empty volunteer fields
                    echo '<div id="volunteer_' . $volunteerTracker . '">';
                    echo '<div class="form_line_div"><label class="form_element_left">Company:</label><input class="form_element_middle" onkeypress="limitToLettersAndNumbers(event)" name="volunteering[0][volCompany]" value="" type="text" maxlength="35" data-toggle="tooltip" data-placement="right" title="' . $tooltips_json['volCompany'] . '" placeholder="' . $examples_json['volunteerOrganisation'] .'" /></div>';
                    echo '<div class="form_line_div"><label class="form_element_left">Start date:</label>';
                    echo addMonthYearPicker('volunteering[0][volStartDate]', '', $tooltips_json['volStartDate']);
                    echo '</div>';
                    echo '<div class="form_line_div"><label class="form_element_left">End date:</label>';
                    echo addMonthYearPickerDisabled('volunteering[0][volEndDate]', '', $tooltips_json['volEndDate']);
                    echo '</div>';echo '<div class="form_line_div"><label class="form_element_left">Job Title:</label><input class="form_element_middle" onkeypress="limitToLettersAndNumbers(event)" name="volunteering[0][volTitle]" value="" type="text" maxlength="35" data-toggle="tooltip" data-placement="right" title="' . $tooltips_json['volTitle'] . '" placeholder="' . $examples_json['volunteerTitle'] .'" /></div>';
                    echo '<div class="form_line_div"><label class="form_element_left">What did you do there?:</label><textarea class="form_element_middle" onkeypress="limitForTextAreas(event)" name="volunteering[0][volDescription]" value="" rows="4" cols="50" maxlength="500" data-toggle="tooltip" data-placement="right" title="' . $tooltips_json['volDescription'] . '" placeholder="' . $examples_json['volunteerRole'] .'" ></textarea></div>';
                    echo '<br></div>';
                }

                echo '<script>var volunteerTracker = ' . $volunteerTracker . ';</script>'; //variable for javascript
                ?>
            </div>
            <div class="form_line_div">
                <input type="button" class="add_or_remove_fields_button" id="more_fields_volunteer" onclick="addVolunteer();" value="Add Another Entry"/>
                <!-- Load the remove button as hidden if volunteer tracker is zero as this means there is only one entry -->
                 <?php
                if ($volunteerTracker == 0)    {
                    echo '<input type="button" class="add_or_remove_fields_button" id="remove_fields_volunteer" onclick="removeVolunteer();" value="Remove Last Entry" hidden/>';
                } else {
                    echo '<input type="button" class="add_or_remove_fields_button" id="remove_fields_volunteer" onclick="removeVolunteer();" value="Remove Last Entry"/>';
                }
                ?>
            </div>
        </div>
        <div class="carousel_bottom_nav_bar">
            <button id="left_button" data-target="#wizard_pages" data-slide-to="2">Back</button>
            <button id="middle_button" name="submit" value="save" onclick="saveQualities(false);">Save & Exit</button>
            <button id="right_button" data-target="#wizard_pages" data-slide-to="4">Next</button>
        </div>
    </div>
</div>
