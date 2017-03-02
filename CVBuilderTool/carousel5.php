<div class="item">
    <div id="slide5" class="wizard_content">
        <div class="form_fields">
            <div class="form_line_div">
                <h2 class="req" id="req">Education</h2>
            </div>
            <div class="educationDivClass">
                <?php
                // This variable tracks the number of education years on the education screen of the carousel
                $educationTracker = 0;
                // This variable tracks the default number of subjects to show in each education section
                $defaultEducationSubjects = 5;
                // This variable is an array that stores the number of subjects in each education section
                // i.e. if there are 5 subjects in the first education section (index 0) and 4 in the second (index 1)
                // they would be stored like this: [5,4]
                $subjectsTracker = [];

                function loadEducationDropdown($name, $tooltip, $selected = ' ')
                {
                    //loading dropdown box from file and auto select field if given
                    if ($file = fopen('gradesdropdown.txt', 'r')) {
                        echo '<select name="'.$name.'" data-toggle="tooltip" data-placement="right" title="'.$tooltip.'">';
                        //echo '<option name="' . $name . '" value=" " selected disabled hidden>Current status (optional)</option>';
                        while (!feof($file)) {
                            $line = fgets($file);
                            $line = preg_replace('/\s+/', ' ', trim($line));
                            if ($selected == $line) {
                                echo '<option name="'.$name.'" selected = "selected">'.$line.'</option>';
                            } else {
                                echo '<option name="'.$name.'">'.$line.'</option>';
                            }
                        }
                        fclose($file);
                        echo '</select>';
                    }
                }

                function loadSubjectLine($name, $tooltip, $selected = '', $example)
                {
                    //loading subject field and auto populate if text is given
                    echo '<input class="form_element_middle" onkeypress="limitToLetters(event)" name="'.$name.'" value="'.$selected.'" type="text" maxlength="40" data-toggle="tooltip" data-placement="right" title="'.$tooltip.'" placeholder="' . $example . '"/>';
                }

                //auto populate all education fields on loading of page
                if ($loadData) {
                    //loading education data
                    foreach ($user_json['education'] as $educationObj) {
                        if ($educationTracker > 0) {
                            echo '<br>';
                        }
                        echo '<div id="education_' . $educationTracker . '">';
                        echo '<div class="form_line_div"><label class="form_element_left">Year:</label>';
                        echo addYearPicker('education[' . $educationTracker . '][schoolYear]', $educationObj['schoolYear'], $tooltips_json['schoolYear']);
                        loadEducationDropdown('education[' . $educationTracker . '][schoolYearGrade]', $tooltips_json['schoolYearGrade'], $educationObj['schoolYearGrade']);
                        echo '</div><br>';
                        $i = 0;

                        foreach ($educationObj['subjects'] as $subjectObj) {
                            echo '<div class="form_line_div" id="education_'.$educationTracker.'_subjects_'.$i.'"><label class="form_element_left">Subject:</label>';
                            loadSubjectLine('education['.$educationTracker.'][subjects]['.$i.'][subject]', $tooltips_json['classes'], $subjectObj['subject'], $examples_json['subject']);
                            loadEducationDropdown('education['.$educationTracker.'][subjects]['.$i.'][grade]', $tooltips_json['grades'], $subjectObj['grade']);
                            if ($i == 0) {
                                // add the "+" button to the first subject line
                                echo '<input class="addOrRemoveButton" id="addSubjectButton_'.$educationTracker.'" type="button" value="+" onclick="addSubject('.$educationTracker.');"/>';
                            } elseif ($i == count($educationObj['subjects']) - 1) {
                                echo '<input class="addOrRemoveButton" id="removeSubjectButton_'.$educationTracker.'" type="button" value="-" onclick="removeSubject('.$educationTracker.');"/>';
                            }
                            echo '</div>';
                            ++$i; // increment the tracker
                        }
                        // add the number of subjects to the subjects array which keeps track of how many subjects are per year
                        array_push($subjectsTracker, $i - 1);
                        echo '</div>';
                        ++$educationTracker;
                    }
                    --$educationTracker;
                } else {
                    //loading empty education fields
                    echo '<div id="education_' . $educationTracker . '">';
                    echo '<div class="form_line_div"><label class="form_element_left">Year:</label>';
                    echo addYearPicker('education[' . $educationTracker . '][schoolYear]', '', $tooltips_json['schoolYear']);
                    loadEducationDropdown('education[' . $educationTracker . '][schoolYearGrade]', $tooltips_json['schoolYearGrade']);
                    echo '</div><br>';
                    for ($i = 0; $i < $defaultEducationSubjects; ++$i) {
                        echo '<div class="form_line_div" id="education_'.$educationTracker.'_subjects_'.$i.'"><label class="form_element_left">Subject:</label>';
                        loadSubjectLine('education['.$educationTracker.'][subjects]['.$i.'][subject]', $tooltips_json['classes'], '', $examples_json['subject']);
                        loadEducationDropdown('education['.$educationTracker.'][subjects]['.$i.'][grade]', $tooltips_json['grades']);
                        if ($i == 0) {
                            echo '<input class="addOrRemoveButton" id="addSubjectButton_'.$educationTracker.'" type="button" value="+" onclick="addSubject('.$educationTracker.');"/>';
                        } elseif ($i == $defaultEducationSubjects - 1) {
                            echo '<input class="addOrRemoveButton" id="removeSubjectButton_'.$educationTracker.'" type="button" value="-" onclick="removeSubject('.$educationTracker.');"/>';
                        }
                        echo '</div>';
                    }
                    // 8 is the default number of subjects at this point in time but we may change this
                    array_push($subjectsTracker, $defaultEducationSubjects - 1);

                    echo '</div>';
                }

                echo '<script>var educationTracker = '.$educationTracker.';';
                echo 'var defaultEducationSubjects = '.$defaultEducationSubjects.';';
                echo 'var subjectsTracker = ['.implode(',', $subjectsTracker).'];</script>';
                //echo 'var subjectsTracker = ' . $subjectsTracker . ';</script>'; //variable for javascript
                ?>
            </div>
            <div class="form_line_div">
                <input type="button" class="add_or_remove_fields_button" id="more_fields_education" onclick="addEducation();" value="Add Another Year"/>
                <!-- Load the remove button as hidden if education tracker is zero as this means there is only one entry -->
                <?php
                if ($educationTracker == 0)    {
                    echo '<input type="button" class="add_or_remove_fields_button" id="remove_fields_education" onclick="removeEducation();" value="Remove Last Year" hidden/>';
                } else {
                    echo '<input type="button" class="add_or_remove_fields_button" id="remove_fields_education" onclick="removeEducation();" value="Remove Last Year"/>';
                }
                ?>
            </div>
        </div>
        <div class="carousel_bottom_nav_bar">
            <button id="left_button" data-target="#wizard_pages" data-slide-to="3">Back</button>
            <button id="middle_button" name="submit" value="save" onclick="saveQualities(false);">Save & Exit</button>
            <button id="right_button" data-target="#wizard_pages" data-slide-to="5">Next</button>
        </div>
    </div>
</div>
