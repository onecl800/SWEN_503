<div class="item">
    <div id="slide2" class="wizard_content">
        <div class="form_fields">
            <div class="form_line_div">
                <h2>Personal Statement (optional)</h2>
            </div>
            <div class="form_line_div">
                <p>If you are not submitting a cover letter it is a good idea to write a personal statemenent that outlines some of your skills and future goals.</p>
            </div>
            <div>
                <p><?php echo $tooltips_json['statement']; ?></p>
            </div>
            <div class="form_line_div">
                <textarea class="form_element_middle" id="personal_statement_text_field" name="statement" maxlength="500" rows="10" cols="50" placeholder="<?php echo $examples_json['statement'] ?>"><?php echo $statement; ?></textarea>
            </div>
        </div>
        <div class="carousel_bottom_nav_bar">
            <button id="left_button" data-target="#wizard_pages" data-slide-to="0">Back</button>
            <button id="middle_button" name="submit" value="save" onclick="saveQualities(false);">Save & Exit</button>
            <button id="right_button" data-target="#wizard_pages" data-slide-to="2">Next</button>
        </div>
    </div>
</div>