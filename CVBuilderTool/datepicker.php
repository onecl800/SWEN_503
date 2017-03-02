<?php

    //bootstrap date picker - year and month (includes code to enable end date picker
    function addMonthYearPicker($inputName, $inputValue, $tooltipName) {
        $line = '';
        $line .= '<div class="input-group date form_element_middle month_year_picker">';
        $line .= '<input onkeydown="return false" type="text" class="form-control"  id="' . $inputName . '" name="' . $inputName . '" value="' . $inputValue . '" data-toggle="tooltip" data-placement="right" title="' . $tooltipName . '">';
        $line .= '<span class="input-group-addon">';
        $line .= '<span class="glyphicon glyphicon-calendar"></span></span></div>';
            
        return $line;
    }
        
    //bootstrap date picker - year only
    function addYearPicker($inputName, $inputValue, $tooltipName) {
        $line = '';
        $line .= '<div class="input-group date form_element_middle year_picker">';
        $line .= '<input onkeydown="return false" type="text" class="form-control" id="' . $inputName . '" name="' . $inputName . '" value="' . $inputValue . '" data-toggle="tooltip" data-placement="right" title="' . $tooltipName . '">';
        $line .= '<span class="input-group-addon">';
        $line .= '<span class="glyphicon glyphicon-calendar"></span></span></div>';
        
        return $line;
    } 
    
    
    //disabled bootstrap date picker - year and month
    function addMonthYearPickerDisabled($inputName, $inputValue, $tooltipName) {
        $line = '';
        $line .= '<div class="input-group date form_element_middle month_year_picker">';
        $line .= '<input disabled onkeydown="return false" type="text" class="form-control"  id="' . $inputName . '" name="' . $inputName . '" value="' . $inputValue . '" data-toggle="tooltip" data-placement="right" title="' . $tooltipName . '">';
        $line .= '<span class="input-group-addon">';
        $line .= '<span class="glyphicon glyphicon-calendar"></span></span></div>';
            
        return $line;
    }

?>