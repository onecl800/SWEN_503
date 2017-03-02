<div class="item">
    <div id="slide7" class="wizard_content">
        <div class="form_fields">
            <div class="form_line_div">
                <h2>Achievements/Awards (optional)</h2>
            </div>

            <div class="form_line_div">
                <p>Awards shows that you can set and achieve goals, and sends a great message to any employer.</p>
            </div>

            <div id="achievementsDiv">
                <?php
                //auto populate all achievement fields on loading of page
                $achievementsTracker = 0;
                if ($loadData) {
                    //load achievement data and populate fields
                    foreach ($user_json['awards'] as $achievementObj) {
                        echo '<div id="achievements_' . $achievementsTracker . '">';
                        echo '<div class="form_line_div"><label class="form_element_left">Year:</label>';
                        echo addYearPicker('awards['.$achievementsTracker.'][awardYear]', $achievementObj['awardYear'], $tooltips_json['awardYear']);
                        echo '</div>';
                        echo '<div class="form_line_div"><label class="form_element_left">Award</label><textarea class="form_element_middle" onkeypress="limitForTextAreas(event)" name="awards[' . $achievementsTracker . '][award]" rows="4" cols="50" maxlength="250" data-toggle="tooltip" data-placement="right" title="' . $tooltips_json['award'] . '" placeholder="' . $examples_json['award'] .'" >' . $achievementObj['award'] . '</textarea></div><br></div>';
                        ++$achievementsTracker;
                    }
                    --$achievementsTracker;
                } else {
                    //load empty achievement fields
                    echo '<div id="achievements_' . $achievementsTracker . '">';
                    echo '<div class="form_line_div"><label class="form_element_left">Year:</label>';
                    echo addYearPicker('awards['.$achievementsTracker.'][awardYear]', '', $tooltips_json['awardYear']);
                    echo '</div>';
                    echo '<div class="form_line_div"><label class="form_element_left">Award</label><textarea class="form_element_middle" onkeypress="limitForTextAreas(event)" name="awards[' . $achievementsTracker . '][award]" rows="4" cols="50" maxlength="250" data-toggle="tooltip" data-placement="right" title="' . $tooltips_json['award'] . '" placeholder="' . $examples_json['award'] .'" ></textarea></div><br></div>';
              }

                echo '<script>var achievementsTracker = ' . $achievementsTracker . ';</script>'; //variable for javascript
                ?>
            </div>
            <div class="form_line_div">
                <input type="button" class="add_or_remove_fields_button" id="more_fields_achievements" onclick="addAchievements();" value="Add Another Entry"/>
                <!-- Load the remove button as hidden if achievements tracker is zero as this means there is only one entry -->
                <?php
                if ($achievementsTracker == 0)    {
                    echo '<input type="button" class="add_or_remove_fields_button" id="remove_fields_achievements" onclick="removeAchievements();" value="Remove Last Entry" hidden/>';
                } else {
                    echo '<input type="button" class="add_or_remove_fields_button" id="remove_fields_achievements" onclick="removeAchievements();" value="Remove Last Entry"/>';
                }
                ?>
            </div>

            <div class="form_line_div">
                <h2>Extracurricular Activities (optional)</h2>
            </div>

            <div class="form_line_div">
                <p>Involvement in sports, the arts and leadership opportunities shows that you are motivated and willing to commit, and sends a great message to any employer.</p>
            </div>

            <div id="extracurricularDiv">
                <?php
                //auto populate all extracurricular fields on loading of page
                $extracurricularTracker = 0;
                if ($loadData) {
                    //load and populate extracurricular fields
                    foreach ($user_json['extracur'] as $extracurObj) {
                        echo '<div id="extracurricular_' . $extracurricularTracker . '">';
                        echo '<div class="form_line_div"><label class="form_element_left">Year:</label>';
                        echo addYearPicker('extracur['.$extracurricularTracker.'][extracurYear]', $extracurObj['extracurYear'], $tooltips_json['extracurYear']);
                        echo '</div>';
                        echo '<div class="form_line_div"><label class="form_element_left">Activity</label><textarea class="form_element_middle" onkeypress="limitForTextAreas(event)" name="extracur[' . $extracurricularTracker . '][activity]" rows="4" cols="50" maxlength="250" data-toggle="tooltip" data-placement="right" title="' . $tooltips_json['activity'] . '" placeholder="' . $examples_json['extraCurrActivity'] .'">' . $extracurObj['activity'] . '</textarea></div><br></div>';
                        //echo '<div class="form_line_div"><label class="form_element_left">Activity</label><input class="form_element_middle" name="extracur[' . $extracurricularTracker . '][activity]" type="text" value="' . $extracurObj['activity'] .'" data-toggle="tooltip" data-placement="right" title="' . $tooltips_json['activity'] . '"/></div><br></div>';
                        ++$extracurricularTracker;
                    }
                    --$extracurricularTracker;
                } else {
                    //load empty extacurricular fields
                    echo '<div id="extracurricular_' . $extracurricularTracker . '">';
                    echo '<div class="form_line_div"><label class="form_element_left">Year:</label>';
                    echo addYearPicker('extracur['.$extracurricularTracker.'][extracurYear]', '', $tooltips_json['extracurYear']);
                    echo '</div>';
                    echo '<div class="form_line_div"><label class="form_element_left">Activity</label><textarea class="form_element_middle" onkeypress="limitForTextAreas(event)" name="extracur[' . $extracurricularTracker . '][activity]" rows="4" cols="50" maxlength="250" data-toggle="tooltip" data-placement="right" title="' . $tooltips_json['activity'] . '" placeholder="' . $examples_json['extraCurrActivity'] .'"></textarea></div><br></div>';
                    //echo '<div class="form_line_div"><label class="form_element_left">Activity</label><input class="form_element_middle" name="extracur[' . $extracurricularTracker . '][activity]" type="text" value="" data-toggle="tooltip" data-placement="right" title="' . $tooltips_json['activity'] . '"/></div><br></div>';
                }

                echo '<script>var extracurricularTracker = ' . $extracurricularTracker . ';</script>'; //variable for javascript

                ?>
            </div>
            <div class="form_line_div">
                <input type="button" class="add_or_remove_fields_button" id="more_fields_extracurricular" onclick="addExtracurricular();" value="Add Another Entry"/>
                <!-- Load the remove button as hidden if extracurricular tracker is zero as this means there is only one entry -->
                <?php
                if ($extracurricularTracker == 0)    {
                    echo '<input type="button" class="add_or_remove_fields_button" id="remove_fields_extracurricular" onclick="removeExtracurricular();" value="Remove Last Entry" hidden/>';
                } else {
                    echo '<input type="button" class="add_or_remove_fields_button" id="remove_fields_extracurricular" onclick="removeExtracurricular();" value="Remove Last Entry"/>';
                }
                ?>
            </div>
        </div>
        <div class="carousel_bottom_nav_bar">
            <button id="left_button" data-target="#wizard_pages" data-slide-to="5">Back</button>
            <button id="middle_button" name="submit" value="save" onclick="saveQualities(false);">Save & Exit</button>
            <button id="right_button" data-target="#wizard_pages" data-slide-to="7">Next</button>
        </div>
    </div>
</div>
