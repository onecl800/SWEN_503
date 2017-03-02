<?php
    $saveData = true;
    $testData = false;

    if ($testData) {
        $id = 'testUser1';
        $_POST['submit'] = 'Create PDF';
    } else {
        $id = $_POST['id'];
    }

    if ($saveData) {
        //Saving POST data from wizard to json
        $GLOBALS['json'] = json_encode($_POST);

        $dir = 'users/'.$id;

        if (!is_dir($dir)) {
            mkdir($dir);
        }

        $fp = fopen($dir.'/'.$id.'.json', 'w');
        fwrite($fp, $GLOBALS['json']);
        fclose($fp);
    }

    if ($_POST['submit'] == 'save') {
        header('Location: index.php'); //redirect back to test id page (index.php)
    } elseif ($_POST['submit'] == 'finish') {
        createPDF($id); //generate PDF from json file
    }

    function createPDF($id)
    {
        /*
        This function generates a PDF document based on the information from /user/id/id.json file where id is the unique user identifier.
        */
        $filename = 'users/'.$id.'/'.$id.'.json';
        $file = file_get_contents($filename);
        $GLOBALS['json'] = json_decode($file, true);

        $GLOBALS['font'] = 'helvetica';
        $nameFontSize = 24;
        $GLOBALS['headingFontSize'] = 14;
        $GLOBALS['bodyFontSize'] = 10;
        $GLOBALS['leftColWidth'] = 30;
        $GLOBALS['midColWidthShort'] = 50;
        $GLOBALS['midColWidthLong'] = 100;
        $GLOBALS['rightColWidthLong'] = 150;
        $GLOBALS['rightColWidthShort'] = 75;

        //PDF Generation using tcpdf
        require_once 'TCPDF/tcpdf.php';
        require_once 'TCPDF/config/tcpdf_config.php';

        // create new PDF document
        $GLOBALS['pdf'] = new TCPDF(PDF_PAGE_ORIENTATION, PDF_UNIT, PDF_PAGE_FORMAT, true, 'UTF-8', false);

        // set document information
        $GLOBALS['pdf']->SetCreator(PDF_CREATOR);
        $GLOBALS['pdf']->SetAuthor($GLOBALS['json']['firstName'].' '.$GLOBALS['json']['lastName']);
        $GLOBALS['pdf']->SetTitle('Curriculum Vitae');

        // remove default header/footer
        $GLOBALS['pdf']->setPrintHeader(false);
        //$GLOBALS['pdf']->setPrintFooter(false);

        // set default monospaced font
        $GLOBALS['pdf']->SetDefaultMonospacedFont(PDF_FONT_MONOSPACED);

        // set margins
        $GLOBALS['pdf']->SetMargins(PDF_MARGIN_LEFT, PDF_MARGIN_TOP, PDF_MARGIN_RIGHT);
        $GLOBALS['pdf']->SetFooterMargin(PDF_MARGIN_FOOTER); //added footer margin and page number indicator

        // set auto page breaks
        $GLOBALS['pdf']->SetAutoPageBreak(true, PDF_MARGIN_BOTTOM);

        // ---------------------------------------------------------

        // add a page
        $GLOBALS['pdf']->AddPage();

        // first and lastname at center of page
        $GLOBALS['pdf']->SetFont($GLOBALS['font'], 'B', $nameFontSize);
        $GLOBALS['pdf']->Write(0, $GLOBALS['json']['firstName'].' '.$GLOBALS['json']['lastName'], '', 0, 'C', true, 0, false, false, 0);

        //This is the initial state, before employment
        $GLOBALS['pdf']->startTransaction();

        // Personal details
        $GLOBALS['pdf']->SetFont($GLOBALS['font'], '', $GLOBALS['bodyFontSize']);
        $address = '';
        if ($GLOBALS['json']['suburb'] != '') {
            $address = $address.$GLOBALS['json']['suburb'].', ';
        }
        if ($GLOBALS['json']['city'] != '') {
            $address = $address.$GLOBALS['json']['city'].', ';
        }
        $address = $address.$GLOBALS['json']['country'];
        $GLOBALS['pdf']->Write(0, $address, '', 0, 'C', true, 0, false, false, 0);

        $GLOBALS['pdf']->Write(0, ' ', '', 0, 'C', true, 0, false, false, 0);

        // set cell padding
        $GLOBALS['pdf']->setCellPaddings(1, 1, 1, 1);

        if ($GLOBALS['json']['age'] != '') {
            $GLOBALS['pdf']->MultiCell($GLOBALS['leftColWidth'], 0, 'Age:', 0, 'L', 0, 0);
            $GLOBALS['pdf']->MultiCell($GLOBALS['rightColWidthShort'], 0, $GLOBALS['json']['age'], 0, 'L', 0, 1);
        }

        $GLOBALS['pdf']->MultiCell($GLOBALS['leftColWidth'], 0, 'School:', 0, 'L', 0, 0);
        $GLOBALS['pdf']->MultiCell($GLOBALS['rightColWidthShort'], 0, $GLOBALS['json']['school'], 0, 'L', 0, 1);
        if ($GLOBALS['json']['license'] != 'No License') {
            $GLOBALS['pdf']->MultiCell($GLOBALS['leftColWidth'], 0, 'License:', 0, 'L', 0, 0);
            $GLOBALS['pdf']->MultiCell($GLOBALS['rightColWidthShort'], 0, $GLOBALS['json']['license'], 0, 'L', 0, 1);
        }
        $GLOBALS['pdf']->MultiCell($GLOBALS['leftColWidth'], 0, 'Phone:', 0, 'L', 0, 0);
        $GLOBALS['pdf']->MultiCell($GLOBALS['rightColWidthShort'], 0, $GLOBALS['json']['phoneNum'], 0, 'L', 0, 1);
        $GLOBALS['pdf']->MultiCell($GLOBALS['leftColWidth'], 0, 'E-mail:', 0, 'L', 0, 0);
        $GLOBALS['pdf']->MultiCell($GLOBALS['rightColWidthShort'], 0, $GLOBALS['json']['mail'], 0, 'L', 0, 1);

        /**If at least 1 language was added, write all the inputs that contain characters*/
        if (sectionIsNotEmpty('languages', 'language')) {
            $GLOBALS['pdf']->MultiCell($GLOBALS['leftColWidth'], 0, 'Language:', 0, 'L', 0, 0);
            $txt = '';
            foreach ($GLOBALS['json']['languages'] as $languageObj) {
                if ($languageObj['language'] != '') {
                    $txt = $txt.$languageObj['language']."\n";
                }
            }
            $GLOBALS['pdf']->MultiCell($GLOBALS['rightColWidthShort'], 0, $txt, 0, 'L', 0, 1);
        }

        $GLOBALS['pdf']->Write(0, '', '', 0, 1, true, 0, false, false, 0); // empty line

        // personal statement
        if ($GLOBALS['json']['statement'] != '') {
            $GLOBALS['pdf']->SetFont($GLOBALS['font'], 'B', $GLOBALS['headingFontSize']);
            $GLOBALS['pdf']->Write(0, 'Personal Statement', '', 0, 1, true, 0, false, false, 0);
            $GLOBALS['pdf']->writeHTML('<hr>', false, false, false, false, ''); // horizontal black line
            $GLOBALS['pdf']->ln(3); // Spacing before text
            $GLOBALS['pdf']->SetFont($GLOBALS['font'], '', $GLOBALS['bodyFontSize']);
            $GLOBALS['pdf']->Write(0, $GLOBALS['json']['statement'], '', 0, 1, true, 0, false, false, 0);
            $GLOBALS['pdf']->Write(0, ' ', '', 0, 'C', true, 0, false, false, 0); // empty line
        }

        // This leaves a 'checkpoint' that can be reverted to
        $GLOBALS['pdf']->startTransaction();

        // This is to track what the current number of written pages is
        $GLOBALS['pageTracker'] = $GLOBALS['pdf']->PageNo();

        // This is to keep track of whether a section has been fully written or not
        $GLOBALS['sectionWritten'] = false;

        /**Many sections are of a dynamic length and the first iteration requires the title to be written.
        Whilst writing a section you may require a pagebreak in the middle.
        Due to the structure of the code this tracker is needed to:
            A. Determine whether the title needs to be written
            B. Keep track of which index in the respective section's array you are up to.
        */
        $GLOBALS['iterations'] = 0;

        addEmploymentSection();

        addVolunteerSection();

        addEducationSection();

        addPersonalQualities();

        addAchievements();

        addExtracurricular();

        addReferees();

        //Close and output PDF document
        $GLOBALS['pdf']->Output($GLOBALS['json']['firstName'].'_'.$GLOBALS['json']['lastName'].'_CV.pdf', 'I');
    }

    /**Function for controlling page breaks to keep sections together
        Takes two parameters:
              1. The number of pages before writing the section onto the pdf
              2. The number of pages after writing the section onto the pdf
    */
    function checkPageBreak($pageNumBeforeWrite, $pageNumAfterWrite)
    {
        /**If the number of pages before does not equal the number of pages after:
            then a pagebreak is in the middle of the section
        */
        if ($pageNumBeforeWrite != $pageNumAfterWrite) {
            // Roll back to a state before writing
            $GLOBALS['pdf'] = $GLOBALS['pdf']->rollbackTransaction();
            // Start a new page
            $GLOBALS['pdf']->AddPage();
            // Update the current number of pages
            ++$GLOBALS['pageTracker'];
            // Due to the rollback, this section has not been written, hence this is false
            $GLOBALS['sectionWritten'] = false;
        }

        /**Otherwise the number of pages must equal
        So update the iteration number because it is done
        And save the new state

        Although the sectionWritten boolean has been changed to true;
        there is a 'while' loop inside the code that prevents exiting of the section until all items inside the array have been written
        */
        else {
            $GLOBALS['sectionWritten'] = true;
            ++$GLOBALS['iterations'];
            $GLOBALS['pdf']->startTransaction();
        }
    }

    //Adds the employment section
    function addEmploymentSection()
    {
        $GLOBALS['sectionWritten'] = false;
        $GLOBALS['iterations'] = 0;

        /**Check if the section is empty*/
        if (sectionIsNotEmpty('employment', 'jobCompany')) {
            /**The structure of the code is that there are nested 'while' loops.
              The boolean here will be changed to true upon the section becoming fully written
            */
            while ($GLOBALS['sectionWritten'] == false) {
                /**While the iterations number is less than the number of items inside the array*/
                while ($GLOBALS['iterations'] < count($GLOBALS['json']['employment'])) {

                    // If it is the first item (ie. iteration 0) then write the title of the section
                    if ($GLOBALS['iterations'] == 0) {
                        $GLOBALS['pdf']->SetFont($GLOBALS['font'], 'B', $GLOBALS['headingFontSize']);
                        $GLOBALS['pdf']->Write(0, 'Employment', '', 0, 1, true, 0, false, false, 0);
                        $GLOBALS['pdf']->writeHTML('<hr>', false, false, false, false, ''); // horizontal black line
                        $GLOBALS['pdf']->ln(3); // Spacing before text
                    }
                    //Check that this is not an empty field
                    if ($GLOBALS['json']['employment'][$GLOBALS['iterations']]['jobCompany'] != '') {
                        //Writes the rest of the information in the section
                        $GLOBALS['pdf']->SetFont($GLOBALS['font'], 'B', $GLOBALS['bodyFontSize']);
                        if ($GLOBALS['json']['employment'][$GLOBALS['iterations']]['endDate'] == '') {
                            $endDate = 'current';
                        } else {
                            $endDate = $GLOBALS['json']['employment'][$GLOBALS['iterations']]['endDate'];
                        }

                        if ($GLOBALS['json']['employment'][$GLOBALS['iterations']]['startDate'] == $endDate) {
                            $txt = $GLOBALS['json']['employment'][$GLOBALS['iterations']]['title'].' | '.$GLOBALS['json']['employment'][$GLOBALS['iterations']]['jobCompany'].' | '.$GLOBALS['json']['employment'][$GLOBALS['iterations']]['startDate'];
                        } else {
                            $txt = $GLOBALS['json']['employment'][$GLOBALS['iterations']]['title'].' | '.$GLOBALS['json']['employment'][$GLOBALS['iterations']]['jobCompany'].' | '.$GLOBALS['json']['employment'][$GLOBALS['iterations']]['startDate'].' - '.$endDate;
                        }
                        $GLOBALS['pdf']->Write(0, $txt, '', 0, 1, true, 0, false, false, 0);
                        $GLOBALS['pdf']->SetFont($GLOBALS['font'], '', $GLOBALS['bodyFontSize']);
                        $GLOBALS['pdf']->Write(0, $GLOBALS['json']['employment'][$GLOBALS['iterations']]['jobDescription'], '', 0, 1, true, 0, false, false, 0);
                        $GLOBALS['pdf']->Write(0, ' ', '', 0, 'C', true, 0, false, false, 0); // empty line

                        checkPageBreak($GLOBALS['pageTracker'], $GLOBALS['pdf']->PageNo());
                    } else {
                        ++$GLOBALS['iterations'];
                    }
                }
            }
        }
    }

    //Adds the volunteer section
    function addVolunteerSection()
    {
        $GLOBALS['sectionWritten'] = false;
        $GLOBALS['iterations'] = 0;

        /**Check if the section is empty*/
        if (sectionIsNotEmpty('volunteering', 'volCompany')) {
            while ($GLOBALS['sectionWritten'] == false) {
                /**While the iterations number is less than the number of items inside the array*/
                while ($GLOBALS['iterations'] < count($GLOBALS['json']['volunteering'])) {

                    // If it is the first item (ie. iteration 0) then write the title of the section
                    if ($GLOBALS['iterations'] == 0) {
                        $GLOBALS['pdf']->SetFont($GLOBALS['font'], 'B', $GLOBALS['headingFontSize']);
                        $GLOBALS['pdf']->Write(0, 'Volunteer', '', 0, 1, true, 0, false, false, 0);
                        $GLOBALS['pdf']->writeHTML('<hr>', false, false, false, false, ''); // horizontal black line
                        $GLOBALS['pdf']->ln(3); // Spacing before text
                    }

                    //Check that this is not an empty field
                    if ($GLOBALS['json']['volunteering'][$GLOBALS['iterations']]['volCompany'] != '') {
                        // Writes in the rest of the information
                        $GLOBALS['pdf']->SetFont($GLOBALS['font'], 'B', $GLOBALS['bodyFontSize']);
                        if ($GLOBALS['json']['volunteering'][$GLOBALS['iterations']]['volEndDate'] == '') {
                            $endDate = 'current';
                        } else {
                            $endDate = $GLOBALS['json']['volunteering'][$GLOBALS['iterations']]['volEndDate'];
                        }

                        if ($GLOBALS['json']['volunteering'][$GLOBALS['iterations']]['volStartDate'] == $endDate) {
                            $txt = $GLOBALS['json']['volunteering'][$GLOBALS['iterations']]['volTitle'].' | '.$GLOBALS['json']['volunteering'][$GLOBALS['iterations']]['volCompany'].' | '.$GLOBALS['json']['volunteering'][$GLOBALS['iterations']]['volStartDate'];
                        } else {
                            $txt = $GLOBALS['json']['volunteering'][$GLOBALS['iterations']]['volTitle'].' | '.$GLOBALS['json']['volunteering'][$GLOBALS['iterations']]['volCompany'].' | '.$GLOBALS['json']['volunteering'][$GLOBALS['iterations']]['volStartDate'].' - '.$endDate;
                        }

                        $GLOBALS['pdf']->Write(0, $txt, '', 0, 1, true, 0, false, false, 0);
                        $GLOBALS['pdf']->SetFont($GLOBALS['font'], '', $GLOBALS['bodyFontSize']);
                        $GLOBALS['pdf']->Write(0, $GLOBALS['json']['volunteering'][$GLOBALS['iterations']]['volDescription'], '', 0, 1, true, 0, false, false, 0);
                        $GLOBALS['pdf']->Write(0, ' ', '', 0, 'C', true, 0, false, false, 0); // empty line

                        checkPageBreak($GLOBALS['pageTracker'], $GLOBALS['pdf']->PageNo());
                    } else {
                        ++$GLOBALS['iterations'];
                    }
                }
            }
        }
    }

    /**Adds in the education section*/
    function addEducationSection()
    {
        $GLOBALS['sectionWritten'] = false;
        $GLOBALS['iterations'] = 0;

        while ($GLOBALS['sectionWritten'] == false) {
            /**While the iterations number is less than the number of items inside the array*/
            while ($GLOBALS['iterations'] < count($GLOBALS['json']['education'])) {
                if ($GLOBALS['iterations'] == 0) {
                    $GLOBALS['pdf']->SetFont($GLOBALS['font'], 'B', $GLOBALS['headingFontSize']);
                    $GLOBALS['pdf']->Write(0, 'Education', '', 0, 1, true, 0, false, false, 0);
                    $GLOBALS['pdf']->writeHTML('<hr>', false, false, false, false, ''); // horizontal black line
                    $GLOBALS['pdf']->ln(3); // Spacing before text
                    $GLOBALS['pdf']->SetFont($GLOBALS['font'], '', $GLOBALS['bodyFontSize']);
                }
                $GLOBALS['pdf']->MultiCell($GLOBALS['leftColWidth'], 0, 'Year:', 0, 'L', 0, 0);
                $GLOBALS['pdf']->MultiCell($GLOBALS['midColWidthShort'], 0, $GLOBALS['json']['education'][$GLOBALS['iterations']]['schoolYear'], 0, 'L', 0, 0);
                $GLOBALS['pdf']->MultiCell($GLOBALS['midColWidthLong'], 0, $GLOBALS['json']['education'][$GLOBALS['iterations']]['schoolYearGrade'], 0, 'L', 0, 1);

                $GLOBALS['pdf']->MultiCell($GLOBALS['leftColWidth'], 0, 'Subjects:', 0, 'L', 0, 0);
                $GLOBALS['pdf']->MultiCell($GLOBALS['midColWidthShort'], 0, $GLOBALS['json']['education'][$GLOBALS['iterations']]['subjects'][0]['subject'], 0, 'L', 0, 0);
                $GLOBALS['pdf']->MultiCell($GLOBALS['midColWidthLong'], 0, $GLOBALS['json']['education'][$GLOBALS['iterations']]['subjects'][0]['grade'], 0, 'L', 0, 1);
                for ($i = 1; $i < count($GLOBALS['json']['education'][$GLOBALS['iterations']]['subjects']); ++$i) {
                    if ($GLOBALS['json']['education'][$GLOBALS['iterations']]['subjects'][$i]['subject'] != '') {
                        $GLOBALS['pdf']->MultiCell($GLOBALS['leftColWidth'], 0, '', 0, 'L', 0, 0);
                        $GLOBALS['pdf']->MultiCell($GLOBALS['midColWidthShort'], 0, $GLOBALS['json']['education'][$GLOBALS['iterations']]['subjects'][$i]['subject'], 0, 'L', 0, 0);
                        $GLOBALS['pdf']->MultiCell($GLOBALS['midColWidthLong'], 0, $GLOBALS['json']['education'][$GLOBALS['iterations']]['subjects'][$i]['grade'], 0, 'L', 0, 1);
                    }
                }
                $GLOBALS['pdf']->Write(0, ' ', '', 0, 'C', true, 0, false, false, 0); // empty line

                checkPageBreak($GLOBALS['pageTracker'], $GLOBALS['pdf']->PageNo());
            }
        }
    }

    function addPersonalQualities()
    {
        $GLOBALS['sectionWritten'] = false;
        $GLOBALS['iterations'] = 0;
        while ($GLOBALS['sectionWritten'] == false) {
            $GLOBALS['pdf']->SetFont($GLOBALS['font'], 'B', $GLOBALS['headingFontSize']);
            $GLOBALS['pdf']->Write(0, 'Personal Qualities', '', 0, 1, true, 0, false, false, 0);
            $GLOBALS['pdf']->writeHTML('<hr>', false, false, false, false, ''); // horizontal black line
            $GLOBALS['pdf']->ln(3); // Spacing before text
            $GLOBALS['pdf']->SetFont($GLOBALS['font'], '', $GLOBALS['bodyFontSize']);
            $counter = 0;
            if (isset($GLOBALS['json']['qualities'])) {
                foreach ($GLOBALS['json']['qualities'] as $personalQualitiesObj) {
                    $newLine = 0;
                    ++$counter;
                    if ($counter == 3 || $counter == count($GLOBALS['json']['qualities'])) {
                        $newLine = 1;
                    }
                    $GLOBALS['pdf']->MultiCell(60, 0, $personalQualitiesObj['quality'], 0, 'L', 0, $newLine);
                }
                $GLOBALS['pdf']->Write(0, ' ', '', 0, 'C', true, 0, false, false, 0);  // empty line
            }
            checkPageBreak($GLOBALS['pageTracker'], $GLOBALS['pdf']->PageNo());
        }
    }

    function addAchievements()
    {
        $GLOBALS['sectionWritten'] = false;
        $GLOBALS['iterations'] = 0;

        /**Check if the section is empty*/
        if (sectionIsNotEmpty('awards', 'awardYear')) {
            while ($GLOBALS['sectionWritten'] == false) {
                while ($GLOBALS['iterations'] < count($GLOBALS['json']['awards'])) {
                    if ($GLOBALS['iterations'] == 0) {
                        $GLOBALS['pdf']->SetFont($GLOBALS['font'], 'B', $GLOBALS['headingFontSize']);
                        $GLOBALS['pdf']->Write(0, 'Awards', '', 0, 1, true, 0, false, false, 0);
                        $GLOBALS['pdf']->writeHTML('<hr>', false, false, false, false, ''); // horizontal black line
                        $GLOBALS['pdf']->ln(3); // Spacing before text
                    }
                    $GLOBALS['pdf']->SetFont($GLOBALS['font'], '', $GLOBALS['bodyFontSize']);

                    //Check that this is not an empty field
                    if ($GLOBALS['json']['awards'][$GLOBALS['iterations']]['awardYear'] != '') {
                        $GLOBALS['pdf']->MultiCell($GLOBALS['leftColWidth'], 0, 'Year:', 0, 'L', 0, 0);
                        $GLOBALS['pdf']->MultiCell($GLOBALS['rightColWidthLong'], 0, $GLOBALS['json']['awards'][$GLOBALS['iterations']]['awardYear'], 0, 'L', 0, 1);
                        $GLOBALS['pdf']->MultiCell($GLOBALS['leftColWidth'], 0, 'Awards:', 0, 'L', 0, 0);
                        $GLOBALS['pdf']->MultiCell($GLOBALS['rightColWidthLong'], 0, $GLOBALS['json']['awards'][$GLOBALS['iterations']]['award'], 0, 'L', 0, 1);
                        $GLOBALS['pdf']->Write(0, '', '', 0, 1, true, 0, false, false, 0); // empty line

                        checkPageBreak($GLOBALS['pageTracker'], $GLOBALS['pdf']->PageNo());
                    } else {
                        ++$GLOBALS['iterations'];
                    }
                }
            }
        }
    }

    function addExtracurricular()
    {
        $GLOBALS['sectionWritten'] = false;
        $GLOBALS['iterations'] = 0;

        /**Check if the section is empty*/
        if (sectionIsNotEmpty('extracur', 'extracurYear')) {
            while ($GLOBALS['sectionWritten'] == false) {
                while ($GLOBALS['iterations'] < count($GLOBALS['json']['extracur'])) {
                    if ($GLOBALS['iterations'] == 0) {
                        $GLOBALS['pdf']->SetFont($GLOBALS['font'], 'B', $GLOBALS['headingFontSize']);
                        $GLOBALS['pdf']->Write(0, 'Extracurricular Activities', '', 0, 1, true, 0, false, false, 0);
                        $GLOBALS['pdf']->writeHTML('<hr>', false, false, false, false, ''); // horizontal black line
                        $GLOBALS['pdf']->ln(3); // Spacing before text
                    }
                    $GLOBALS['pdf']->SetFont($GLOBALS['font'], '', $GLOBALS['bodyFontSize']);

                    //Check that this is not an empty field
                    if ($GLOBALS['json']['extracur'][$GLOBALS['iterations']]['extracurYear'] != '') {
                        $GLOBALS['pdf']->MultiCell($GLOBALS['leftColWidth'], 0, 'Year:', 0, 'L', 0, 0);
                        $GLOBALS['pdf']->MultiCell($GLOBALS['rightColWidthLong'], 0, $GLOBALS['json']['extracur'][$GLOBALS['iterations']]['extracurYear'], 0, 'L', 0, 1);
                        $GLOBALS['pdf']->MultiCell($GLOBALS['leftColWidth'], 0, 'Activities:', 0, 'L', 0, 0);
                        $GLOBALS['pdf']->MultiCell($GLOBALS['rightColWidthLong'], 0, $GLOBALS['json']['extracur'][$GLOBALS['iterations']]['activity'], 0, 'L', 0, 1);
                        $GLOBALS['pdf']->Write(0, '', '', 0, 1, true, 0, false, false, 0); // empty line

                        checkPageBreak($GLOBALS['pageTracker'], $GLOBALS['pdf']->PageNo());
                    } else {
                        ++$GLOBALS['iterations'];
                    }
                }
            }
        }
    }

    function addReferees()
    {
        $GLOBALS['sectionWritten'] = false;
        $GLOBALS['iterations'] = 0;
        while ($GLOBALS['sectionWritten'] == false) {
            while ($GLOBALS['iterations'] < count($GLOBALS['json']['referees'])) {
                if ($GLOBALS['iterations'] == 0) {
                    $GLOBALS['pdf']->SetFont($GLOBALS['font'], 'B', $GLOBALS['headingFontSize']);
                    $GLOBALS['pdf']->Write(0, 'Referees', '', 0, 1, true, 0, false, false, 0);
                    $GLOBALS['pdf']->writeHTML('<hr>', false, false, false, false, ''); // horizontal black line
                    $GLOBALS['pdf']->ln(3); // Spacing before text
                }
                $GLOBALS['pdf']->SetFont($GLOBALS['font'], '', $GLOBALS['bodyFontSize']);

                $GLOBALS['pdf']->MultiCell($GLOBALS['leftColWidth'], 0, 'Name:', 0, 'L', 0, 0);
                $GLOBALS['pdf']->MultiCell($GLOBALS['rightColWidthLong'], 0, $GLOBALS['json']['referees'][$GLOBALS['iterations']]['refName'], 0, 'L', 0, 1);
                $GLOBALS['pdf']->MultiCell($GLOBALS['leftColWidth'], 0, 'Position:', 0, 'L', 0, 0);
                $GLOBALS['pdf']->MultiCell($GLOBALS['rightColWidthLong'], 0, $GLOBALS['json']['referees'][$GLOBALS['iterations']]['refPosition'], 0, 'L', 0, 1);
                $GLOBALS['pdf']->MultiCell($GLOBALS['leftColWidth'], 0, 'E-mail:', 0, 'L', 0, 0);
                $GLOBALS['pdf']->MultiCell($GLOBALS['rightColWidthLong'], 0, $GLOBALS['json']['referees'][$GLOBALS['iterations']]['refEmail'], 0, 'L', 0, 1);
                $GLOBALS['pdf']->MultiCell($GLOBALS['leftColWidth'], 0, 'Phone:', 0, 'L', 0, 0);
                $GLOBALS['pdf']->MultiCell($GLOBALS['rightColWidthLong'], 0, $GLOBALS['json']['referees'][$GLOBALS['iterations']]['refNum'], 0, 'L', 0, 1);
                $GLOBALS['pdf']->Write(0, '', '', 0, 1, true, 0, false, false, 0); // empty line

                checkPageBreak($GLOBALS['pageTracker'], $GLOBALS['pdf']->PageNo());
            }
        }
    }

    /**Checks if a section contains items to be written and return true if it does*/
    function sectionIsNotEmpty($sectionName, $sectionField)
    {
        for ($i = 0; $i < count($GLOBALS['json'][$sectionName]); ++$i) {
            if ($GLOBALS['json'][$sectionName][$i][$sectionField] != '') {
                return true;
            }
        }

        return false;
    }
