package test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import testClasses.Chrome;
import testClasses.SimpleJsonParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by josh on 20/01/17.
 */
public class FunctionalityTest {
    long waitTime = 1000;
    //Firefox driver; //Firefox driver - has compatibility issues using Selenium to select dates via datepicker
    Chrome driver;
    String url;

    @Before
    public void setUp() throws Exception {
        url = "http://localhost/home/index.php";

        //driver = new Firefox(); //new Firefox driver instance
        driver = new Chrome(); //new Chrome driver instance
    }

    @Test
    public void allEndDateShouldDisableEnableOnCreateCV() {
        /*
         * This test ensures that the end date datepicker is disabled if its associated start date field is empty on Create CV
         */
        driver.login(url, "000"); //login using testId and click Create CV
        driver.clickSubmitButtonByValue("Create CV");
        driver.waitFor(waitTime);

        /*
         * Slide 3 - Employment
         */
        driver.clickButtonById("employment_button"); //click employment on nav bar
        driver.waitFor(waitTime);

        assertEquals("", driver.getFieldByName("employment[0][startDate]")); //Start Date field should be empty
        assertEquals("", driver.getFieldByName("employment[0][endDate]")); //End Date field should be empty
        assertTrue(driver.elementByNameHasAttribute("employment[0][endDate]", "disabled")); //End Date should be disabled
        driver.selectCalendarMonthYearByName("employment[0][startDate]", "Jan", 2017); //select start date as Jan 2017
        assertFalse(driver.elementByNameHasAttribute("employment[0][endDate]", "disabled")); //End Date should be enabled

        driver.clickButtonById("more_fields_employment"); //click add more entry

        assertEquals("", driver.getFieldByName("employment[1][startDate]")); //Start Date field should be empty
        assertEquals("", driver.getFieldByName("employment[1][endDate]")); //End Date field should be empty
        assertTrue(driver.elementByNameHasAttribute("employment[1][endDate]", "disabled")); //End Date should be disabled
        driver.selectCalendarMonthYearByName("employment[1][startDate]", "Jan", 2017); //select start date as Jan 2017
        assertFalse(driver.elementByNameHasAttribute("employment[1][endDate]", "disabled")); //End Date should be enabled

        /*
         * Slide 4 - Volunteer Work
         */
        driver.clickButtonById("volunteer_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation

        assertEquals("", driver.getFieldByName("volunteering[0][volStartDate]")); //Start Date field should be empty
        assertEquals("", driver.getFieldByName("volunteering[0][volEndDate]")); //End Date field should be empty
        assertTrue(driver.elementByNameHasAttribute("volunteering[0][volEndDate]", "disabled")); //End date should be disabled
        driver.selectCalendarMonthYearByName("volunteering[0][volStartDate]", "Jan", 2017); //select start date as Jan 2017
        assertFalse(driver.elementByNameHasAttribute("volunteering[0][volEndDate]", "disabled")); //End Date should be enabled

        driver.clickButtonById("more_fields_volunteer"); //click add more entry

        assertEquals("", driver.getFieldByName("volunteering[1][volStartDate]")); //Start Date field should be empty
        assertEquals("", driver.getFieldByName("volunteering[1][volEndDate]")); //End Date field should be empty
        assertTrue(driver.elementByNameHasAttribute("volunteering[1][volEndDate]", "disabled")); //End date should be disabled
        driver.selectCalendarMonthYearByName("volunteering[1][volStartDate]", "Jan", 2017); //select start date as Jan 2017
        assertFalse(driver.elementByNameHasAttribute("volunteering[1][volEndDate]", "disabled")); //End Date should be enabled
    }

    @Test
    public void allEndDateShouldDisableEnableOnEditCV() {
        /*
         * This test ensures that the end date datepicker is disabled if its associated start date field is empty on Edit CV
         */
        driver.login(url, "001"); //login using testId and click Edit CV
        driver.clickSubmitButtonByValue("Edit CV");
        driver.waitFor(waitTime);

        /*
         * Slide 3 - Employment
         */
        driver.clickButtonById("employment_button"); //click employment on nav bar
        driver.waitFor(waitTime);

        driver.clickButtonById("more_fields_employment"); //click add more entry

        assertEquals("", driver.getFieldByName("employment[2][startDate]")); //Start Date field should be empty
        assertEquals("", driver.getFieldByName("employment[2][endDate]")); //End Date field should be empty
        assertTrue(driver.elementByNameHasAttribute("employment[2][endDate]", "disabled")); //End Date should be disabled
        driver.selectCalendarMonthYearByName("employment[2][startDate]", "Jan", 2017); //select start date as Jan 2017
        assertFalse(driver.elementByNameHasAttribute("employment[2][endDate]", "disabled")); //End Date should be enabled

        /*
         * Slide 4 - Volunteer Work
         */
        driver.clickButtonById("volunteer_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation

        driver.clickButtonById("more_fields_volunteer"); //click add more entry

        assertEquals("", driver.getFieldByName("volunteering[2][volStartDate]")); //Start Date field should be empty
        assertEquals("", driver.getFieldByName("volunteering[2][volEndDate]")); //End Date field should be empty
        assertTrue(driver.elementByNameHasAttribute("volunteering[2][volEndDate]", "disabled")); //End date should be disabled
        driver.selectCalendarMonthYearByName("volunteering[2][volStartDate]", "Jan", 2017); //select start date as Jan 2017
        assertFalse(driver.elementByNameHasAttribute("volunteering[2][volEndDate]", "disabled")); //End Date should be enabled
    }

    @Test
    public void allPlaceholderExampleTextShouldBeCorrectOnCreateCV() {
        /*
         * This test ensures that all placeholder example text in each field (if applicable) is loaded correctly from example.json on Create CV
         */
        SimpleJsonParser placeholderJson = new SimpleJsonParser("../example.json");
        driver.login(url, "000"); //login using testId and click Create CV
        driver.clickSubmitButtonByValue("Create CV");
        driver.waitFor(waitTime);

        /*
         * Slide 1 - Personal Details
         */
        assertEquals(placeholderJson.getString("firstName"), driver.getElementAttributeValueByName("firstName", "placeholder")); //first name placeholder
        assertEquals(placeholderJson.getString("lastName"), driver.getElementAttributeValueByName("lastName", "placeholder")); //last name placeholder
        assertEquals(placeholderJson.getString("age"), driver.getElementAttributeValueByName("age", "placeholder")); //age placeholder
        assertEquals(placeholderJson.getString("languages"), driver.getElementAttributeValueByName("languages[0][language]", "placeholder")); //language 0 placeholder
        driver.clickButtonById("addLanguageButton"); //press + button to add 1 more language field
        assertEquals(placeholderJson.getString("languages"), driver.getElementAttributeValueByName("languages[1][language]", "placeholder")); //language 1 placeholder
        assertEquals(placeholderJson.getString("school"), driver.getElementAttributeValueByName("school", "placeholder")); //school placeholder
        assertEquals(placeholderJson.getString("phoneNum"), driver.getElementAttributeValueByName("phoneNum", "placeholder")); //phone number placeholder
        assertEquals(placeholderJson.getString("email"), driver.getElementAttributeValueByName("mail", "placeholder")); //email placeholder
        assertEquals(placeholderJson.getString("suburb"), driver.getElementAttributeValueByName("suburb", "placeholder")); //suburb placeholder
        assertEquals(placeholderJson.getString("city"), driver.getElementAttributeValueByName("city", "placeholder")); //city placeholder

        /*
         * Slide 2 - Personal Statement
         */
        driver.clickButtonById("statement_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation

        assertEquals(placeholderJson.getString("statement"), driver.getElementAttributeValueByName("statement", "placeholder")); //personal statement placeholder

        /*
         * Slide 3 - Employment
         */
        driver.clickButtonById("employment_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation

        assertEquals(placeholderJson.getString("company"), driver.getElementAttributeValueByName("employment[0][jobCompany]", "placeholder")); //company placeholder
        assertEquals(placeholderJson.getString("jobTitle"), driver.getElementAttributeValueByName("employment[0][title]", "placeholder")); //job title placeholder
        assertEquals(placeholderJson.getString("jobRole"), driver.getElementAttributeValueByName("employment[0][jobDescription]", "placeholder")); //job description placeholder
        driver.clickButtonById("more_fields_employment"); //click add more entry
        assertEquals(placeholderJson.getString("company"), driver.getElementAttributeValueByName("employment[1][jobCompany]", "placeholder")); //company placeholder
        assertEquals(placeholderJson.getString("jobTitle"), driver.getElementAttributeValueByName("employment[1][title]", "placeholder")); //job title placeholder
        assertEquals(placeholderJson.getString("jobRole"), driver.getElementAttributeValueByName("employment[1][jobDescription]", "placeholder")); //job description placeholder

        /*
         * Slide 4 - Volunteer Work
         */
        driver.clickButtonById("volunteer_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation

        assertEquals(placeholderJson.getString("volunteerOrganisation"), driver.getElementAttributeValueByName("volunteering[0][volCompany]", "placeholder")); //volunteer company placeholder
        assertEquals(placeholderJson.getString("volunteerTitle"), driver.getElementAttributeValueByName("volunteering[0][volTitle]", "placeholder")); //volunteer title placeholder
        assertEquals(placeholderJson.getString("volunteerRole"), driver.getElementAttributeValueByName("volunteering[0][volDescription]", "placeholder")); //volunteer description placeholder
        driver.clickButtonById("more_fields_volunteer"); //click add more entry
        assertEquals(placeholderJson.getString("volunteerOrganisation"), driver.getElementAttributeValueByName("volunteering[1][volCompany]", "placeholder")); //volunteer company placeholder
        assertEquals(placeholderJson.getString("volunteerTitle"), driver.getElementAttributeValueByName("volunteering[1][volTitle]", "placeholder")); //volunteer title placeholder
        assertEquals(placeholderJson.getString("volunteerRole"), driver.getElementAttributeValueByName("volunteering[1][volDescription]", "placeholder")); //volunteer description placeholder

        /*
         * Slide 5 - Education
         */
        driver.clickButtonById("education_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation

        for (int i = 0; i < 5; i++) {
            assertEquals(placeholderJson.getString("subject"), driver.getElementAttributeValueByName("education[0][subjects][" + i + "][subject]", "placeholder")); //education subject placeholder
        }
        driver.clickButtonById("addSubjectButton_0"); //click + to add a new subject line to education_0
        assertEquals(placeholderJson.getString("subject"), driver.getElementAttributeValueByName("education[0][subjects][5][subject]", "placeholder")); //education subject placeholder

        driver.clickButtonById("more_fields_education"); //click add more education button

        for (int i = 0; i < 5; i++) {
            assertEquals(placeholderJson.getString("subject"), driver.getElementAttributeValueByName("education[1][subjects][" + i + "][subject]", "placeholder")); //education subject placeholder
        }
        driver.clickButtonById("addSubjectButton_1"); //click + to add a new subject line to education_0
        assertEquals(placeholderJson.getString("subject"), driver.getElementAttributeValueByName("education[1][subjects][5][subject]", "placeholder")); //education subject placeholder

        /*
         * Slide 7 - Achievement & Awards
         */
        driver.clickButtonById("awards_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation

        assertEquals(placeholderJson.getString("award"), driver.getElementAttributeValueByName("awards[0][award]", "placeholder")); //awards placeholder
        driver.clickButtonById("more_fields_achievements"); //click add more entry
        assertEquals(placeholderJson.getString("award"), driver.getElementAttributeValueByName("awards[1][award]", "placeholder")); //awards placeholder

        assertEquals(placeholderJson.getString("extraCurrActivity"), driver.getElementAttributeValueByName("extracur[0][activity]", "placeholder")); //activity placeholder
        driver.clickButtonById("more_fields_extracurricular"); //click add more entry
        assertEquals(placeholderJson.getString("extraCurrActivity"), driver.getElementAttributeValueByName("extracur[1][activity]", "placeholder")); //activity placeholder

        /*
         * Slide 8 - Referees
         */
        driver.clickButtonById("referees_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation

        assertEquals(placeholderJson.getString("refName"), driver.getElementAttributeValueByName("referees[0][refName]", "placeholder")); //referee name placeholder
        assertEquals(placeholderJson.getString("refPos"), driver.getElementAttributeValueByName("referees[0][refPosition]", "placeholder")); //referee position placeholder
        assertEquals(placeholderJson.getString("refEmail"), driver.getElementAttributeValueByName("referees[0][refEmail]", "placeholder")); //referee email placeholder
        assertEquals(placeholderJson.getString("refNum"), driver.getElementAttributeValueByName("referees[0][refNum]", "placeholder")); //referee phone number placeholder

        driver.clickButtonById("more_fields_referees"); //click add more referee

        assertEquals(placeholderJson.getString("refName"), driver.getElementAttributeValueByName("referees[1][refName]", "placeholder")); //referee name placeholder
        assertEquals(placeholderJson.getString("refPos"), driver.getElementAttributeValueByName("referees[1][refPosition]", "placeholder")); //referee position placeholder
        assertEquals(placeholderJson.getString("refEmail"), driver.getElementAttributeValueByName("referees[1][refEmail]", "placeholder")); //referee email placeholder
        assertEquals(placeholderJson.getString("refNum"), driver.getElementAttributeValueByName("referees[1][refNum]", "placeholder")); //referee phone number placeholder
    }

    @Test
    public void allPlaceholderExampleTextShouldBeCorrectOnEditCV() {
        /*
         * This test ensures that all placeholder example text in each field (if applicable) is loaded correctly from example.json on Edit CV
         */
        SimpleJsonParser placeholderJson = new SimpleJsonParser("../example.json");
        driver.login(url, "001"); //login using testId and click Create CV
        driver.clickSubmitButtonByValue("Edit CV");
        driver.waitFor(waitTime);

        /*
         * Slide 1 - Personal Details
         */
        assertEquals(placeholderJson.getString("firstName"), driver.getElementAttributeValueByName("firstName", "placeholder")); //first name placeholder
        assertEquals(placeholderJson.getString("lastName"), driver.getElementAttributeValueByName("lastName", "placeholder")); //last name placeholder
        assertEquals(placeholderJson.getString("age"), driver.getElementAttributeValueByName("age", "placeholder")); //age placeholder
        assertEquals(placeholderJson.getString("languages"), driver.getElementAttributeValueByName("languages[0][language]", "placeholder")); //language 0 placeholder
        assertEquals(placeholderJson.getString("languages"), driver.getElementAttributeValueByName("languages[1][language]", "placeholder")); //language 1 placeholder
        assertEquals(placeholderJson.getString("languages"), driver.getElementAttributeValueByName("languages[2][language]", "placeholder")); //language 2 placeholder
        driver.clickButtonById("addLanguageButton"); //press + button to add 1 more language field
        assertEquals(placeholderJson.getString("languages"), driver.getElementAttributeValueByName("languages[3][language]", "placeholder")); //language 3 placeholder
        assertEquals(placeholderJson.getString("school"), driver.getElementAttributeValueByName("school", "placeholder")); //school placeholder
        assertEquals(placeholderJson.getString("phoneNum"), driver.getElementAttributeValueByName("phoneNum", "placeholder")); //phone number placeholder
        assertEquals(placeholderJson.getString("email"), driver.getElementAttributeValueByName("mail", "placeholder")); //email placeholder
        assertEquals(placeholderJson.getString("suburb"), driver.getElementAttributeValueByName("suburb", "placeholder")); //suburb placeholder
        assertEquals(placeholderJson.getString("city"), driver.getElementAttributeValueByName("city", "placeholder")); //city placeholder

        /*
         * Slide 2 - Personal Statement
         */
        driver.clickButtonById("statement_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation

        assertEquals(placeholderJson.getString("statement"), driver.getElementAttributeValueByName("statement", "placeholder")); //personal statement placeholder

        /*
         * Slide 3 - Employment
         */
        driver.clickButtonById("employment_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation

        assertEquals(placeholderJson.getString("company"), driver.getElementAttributeValueByName("employment[0][jobCompany]", "placeholder")); //company placeholder
        assertEquals(placeholderJson.getString("jobTitle"), driver.getElementAttributeValueByName("employment[0][title]", "placeholder")); //job title placeholder
        assertEquals(placeholderJson.getString("jobRole"), driver.getElementAttributeValueByName("employment[0][jobDescription]", "placeholder")); //job description placeholder

        assertEquals(placeholderJson.getString("company"), driver.getElementAttributeValueByName("employment[1][jobCompany]", "placeholder")); //company placeholder
        assertEquals(placeholderJson.getString("jobTitle"), driver.getElementAttributeValueByName("employment[1][title]", "placeholder")); //job title placeholder
        assertEquals(placeholderJson.getString("jobRole"), driver.getElementAttributeValueByName("employment[1][jobDescription]", "placeholder")); //job description placeholder
        driver.clickButtonById("more_fields_employment"); //click add more entry
        assertEquals(placeholderJson.getString("company"), driver.getElementAttributeValueByName("employment[2][jobCompany]", "placeholder")); //company placeholder
        assertEquals(placeholderJson.getString("jobTitle"), driver.getElementAttributeValueByName("employment[2][title]", "placeholder")); //job title placeholder
        assertEquals(placeholderJson.getString("jobRole"), driver.getElementAttributeValueByName("employment[2][jobDescription]", "placeholder")); //job description placeholder

        /*
         * Slide 4 - Volunteer Work
         */
        driver.clickButtonById("volunteer_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation

        assertEquals(placeholderJson.getString("volunteerOrganisation"), driver.getElementAttributeValueByName("volunteering[0][volCompany]", "placeholder")); //volunteer company placeholder
        assertEquals(placeholderJson.getString("volunteerTitle"), driver.getElementAttributeValueByName("volunteering[0][volTitle]", "placeholder")); //volunteer title placeholder
        assertEquals(placeholderJson.getString("volunteerRole"), driver.getElementAttributeValueByName("volunteering[0][volDescription]", "placeholder")); //volunteer description placeholder

        assertEquals(placeholderJson.getString("volunteerOrganisation"), driver.getElementAttributeValueByName("volunteering[1][volCompany]", "placeholder")); //volunteer company placeholder
        assertEquals(placeholderJson.getString("volunteerTitle"), driver.getElementAttributeValueByName("volunteering[1][volTitle]", "placeholder")); //volunteer title placeholder
        assertEquals(placeholderJson.getString("volunteerRole"), driver.getElementAttributeValueByName("volunteering[1][volDescription]", "placeholder")); //volunteer description placeholder
        driver.clickButtonById("more_fields_volunteer"); //click add more entry
        assertEquals(placeholderJson.getString("volunteerOrganisation"), driver.getElementAttributeValueByName("volunteering[2][volCompany]", "placeholder")); //volunteer company placeholder
        assertEquals(placeholderJson.getString("volunteerTitle"), driver.getElementAttributeValueByName("volunteering[2][volTitle]", "placeholder")); //volunteer title placeholder
        assertEquals(placeholderJson.getString("volunteerRole"), driver.getElementAttributeValueByName("volunteering[2][volDescription]", "placeholder")); //volunteer description placeholder

        /*
         * Slide 5 - Education
         */
        driver.clickButtonById("education_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation

        for (int i = 0; i < 5; i++) {
            assertEquals(placeholderJson.getString("subject"), driver.getElementAttributeValueByName("education[0][subjects][" + i + "][subject]", "placeholder")); //education subject placeholder
        }
        driver.clickButtonById("addSubjectButton_0"); //click + to add a new subject line to education_0
        assertEquals(placeholderJson.getString("subject"), driver.getElementAttributeValueByName("education[0][subjects][5][subject]", "placeholder")); //education subject placeholder

        for (int i = 0; i < 5; i++) {
            assertEquals(placeholderJson.getString("subject"), driver.getElementAttributeValueByName("education[1][subjects][" + i + "][subject]", "placeholder")); //education subject placeholder
        }
        driver.clickButtonById("addSubjectButton_1"); //click + to add a new subject line to education_1
        assertEquals(placeholderJson.getString("subject"), driver.getElementAttributeValueByName("education[1][subjects][5][subject]", "placeholder")); //education subject placeholder

        for (int i = 0; i < 5; i++) {
            assertEquals(placeholderJson.getString("subject"), driver.getElementAttributeValueByName("education[2][subjects][" + i + "][subject]", "placeholder")); //education subject placeholder
        }
        driver.clickButtonById("addSubjectButton_2"); //click + to add a new subject line to education_2
        assertEquals(placeholderJson.getString("subject"), driver.getElementAttributeValueByName("education[2][subjects][5][subject]", "placeholder")); //education subject placeholder

        driver.clickButtonById("more_fields_education"); //click add more education button

        for (int i = 0; i < 5; i++) {
            assertEquals(placeholderJson.getString("subject"), driver.getElementAttributeValueByName("education[3][subjects][" + i + "][subject]", "placeholder")); //education subject placeholder
        }
        driver.clickButtonById("addSubjectButton_3"); //click + to add a new subject line to education_0
        assertEquals(placeholderJson.getString("subject"), driver.getElementAttributeValueByName("education[3][subjects][5][subject]", "placeholder")); //education subject placeholder

        /*
         * Slide 7 - Achievement & Awards
         */
        driver.clickButtonById("awards_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation

        assertEquals(placeholderJson.getString("award"), driver.getElementAttributeValueByName("awards[0][award]", "placeholder")); //awards placeholder
        assertEquals(placeholderJson.getString("award"), driver.getElementAttributeValueByName("awards[1][award]", "placeholder")); //awards placeholder
        driver.clickButtonById("more_fields_achievements"); //click add more entry
        assertEquals(placeholderJson.getString("award"), driver.getElementAttributeValueByName("awards[2][award]", "placeholder")); //awards placeholder

        assertEquals(placeholderJson.getString("extraCurrActivity"), driver.getElementAttributeValueByName("extracur[0][activity]", "placeholder")); //activity placeholder
        assertEquals(placeholderJson.getString("extraCurrActivity"), driver.getElementAttributeValueByName("extracur[1][activity]", "placeholder")); //activity placeholder
        driver.clickButtonById("more_fields_extracurricular"); //click add more entry
        assertEquals(placeholderJson.getString("extraCurrActivity"), driver.getElementAttributeValueByName("extracur[2][activity]", "placeholder")); //activity placeholder

        /*
         * Slide 8 - Referees
         */
        driver.clickButtonById("referees_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation

        assertEquals(placeholderJson.getString("refName"), driver.getElementAttributeValueByName("referees[0][refName]", "placeholder")); //referee name placeholder
        assertEquals(placeholderJson.getString("refPos"), driver.getElementAttributeValueByName("referees[0][refPosition]", "placeholder")); //referee position placeholder
        assertEquals(placeholderJson.getString("refEmail"), driver.getElementAttributeValueByName("referees[0][refEmail]", "placeholder")); //referee email placeholder
        assertEquals(placeholderJson.getString("refNum"), driver.getElementAttributeValueByName("referees[0][refNum]", "placeholder")); //referee phone number placeholder

        assertEquals(placeholderJson.getString("refName"), driver.getElementAttributeValueByName("referees[1][refName]", "placeholder")); //referee name placeholder
        assertEquals(placeholderJson.getString("refPos"), driver.getElementAttributeValueByName("referees[1][refPosition]", "placeholder")); //referee position placeholder
        assertEquals(placeholderJson.getString("refEmail"), driver.getElementAttributeValueByName("referees[1][refEmail]", "placeholder")); //referee email placeholder
        assertEquals(placeholderJson.getString("refNum"), driver.getElementAttributeValueByName("referees[1][refNum]", "placeholder")); //referee phone number placeholder

        assertEquals(placeholderJson.getString("refName"), driver.getElementAttributeValueByName("referees[2][refName]", "placeholder")); //referee name placeholder
        assertEquals(placeholderJson.getString("refPos"), driver.getElementAttributeValueByName("referees[2][refPosition]", "placeholder")); //referee position placeholder
        assertEquals(placeholderJson.getString("refEmail"), driver.getElementAttributeValueByName("referees[2][refEmail]", "placeholder")); //referee email placeholder
        assertEquals(placeholderJson.getString("refNum"), driver.getElementAttributeValueByName("referees[2][refNum]", "placeholder")); //referee phone number placeholder

        driver.clickButtonById("more_fields_referees"); //click add more referee

        assertEquals(placeholderJson.getString("refName"), driver.getElementAttributeValueByName("referees[3][refName]", "placeholder")); //referee name placeholder
        assertEquals(placeholderJson.getString("refPos"), driver.getElementAttributeValueByName("referees[3][refPosition]", "placeholder")); //referee position placeholder
        assertEquals(placeholderJson.getString("refEmail"), driver.getElementAttributeValueByName("referees[3][refEmail]", "placeholder")); //referee email placeholder
        assertEquals(placeholderJson.getString("refNum"), driver.getElementAttributeValueByName("referees[3][refNum]", "placeholder")); //referee phone number placeholder

    }

    @Test
    public void allTooltipTextShouldBeCorrectOnCreateCV() {
        /*
         * This test ensures that all tooltip text in each field is loaded correctly from tooltips.json on Create CV
         */
        SimpleJsonParser tooltipsJson = new SimpleJsonParser("../tooltips.json");
        driver.login(url, "000"); //login using testId and click Create CV
        driver.clickSubmitButtonByValue("Create CV");
        driver.waitFor(waitTime);

        /*
         * Slide 1 - Personal Details
         */
        assertEquals(tooltipsJson.getString("firstName"), driver.getElementAttributeValueByName("firstName", "data-original-title")); //first name data-original-title
        assertEquals(tooltipsJson.getString("lastName"), driver.getElementAttributeValueByName("lastName", "data-original-title")); //last name data-original-title
        assertEquals(tooltipsJson.getString("age"), driver.getElementAttributeValueByName("age", "data-original-title")); //age data-original-title
        assertEquals(tooltipsJson.getString("languages"), driver.getElementAttributeValueByName("languages[0][language]", "data-original-title")); //language 0 data-original-title
        driver.clickButtonById("addLanguageButton"); //press + button to add 1 more language field
        assertEquals(tooltipsJson.getString("languages"), driver.getElementAttributeValueByName("languages[1][language]", "data-original-title")); //language 1 data-original-title
        assertEquals(tooltipsJson.getString("school"), driver.getElementAttributeValueByName("school", "data-original-title")); //school data-original-title
        assertEquals(tooltipsJson.getString("license"), driver.getElementAttributeValueByName("license", "data-original-title")); //license data-original-title
        assertEquals(tooltipsJson.getString("phoneNum"), driver.getElementAttributeValueByName("phoneNum", "data-original-title")); //phone number data-original-title
        assertEquals(tooltipsJson.getString("mail"), driver.getElementAttributeValueByName("mail", "data-original-title")); //email data-original-title
        assertEquals(tooltipsJson.getString("suburb"), driver.getElementAttributeValueByName("suburb", "data-original-title")); //suburb data-original-title
        assertEquals(tooltipsJson.getString("city"), driver.getElementAttributeValueByName("city", "data-original-title")); //city data-original-title

        /*
         * Slide 3 - Employment
         */
        driver.clickButtonById("employment_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation

        assertEquals(tooltipsJson.getString("jobCompany"), driver.getElementAttributeValueByName("employment[0][jobCompany]", "data-original-title")); //company data-original-title
        assertEquals(tooltipsJson.getString("startDate"), driver.getElementAttributeValueByName("employment[0][startDate]", "data-original-title")); //startDate data-original-title
        assertEquals(tooltipsJson.getString("endDate"), driver.getElementAttributeValueByName("employment[0][endDate]", "data-original-title")); //endDate data-original-title
        assertEquals(tooltipsJson.getString("title"), driver.getElementAttributeValueByName("employment[0][title]", "data-original-title")); //job title data-original-title
        assertEquals(tooltipsJson.getString("jobDescription"), driver.getElementAttributeValueByName("employment[0][jobDescription]", "data-original-title")); //job description data-original-title
        driver.clickButtonById("more_fields_employment"); //click add more entry
        assertEquals(tooltipsJson.getString("jobCompany"), driver.getElementAttributeValueByName("employment[1][jobCompany]", "data-original-title")); //company data-original-title
        assertEquals(tooltipsJson.getString("startDate"), driver.getElementAttributeValueByName("employment[1][startDate]", "data-original-title")); //startDate data-original-title
        assertEquals(tooltipsJson.getString("endDate"), driver.getElementAttributeValueByName("employment[1][endDate]", "data-original-title")); //endDate data-original-title
        assertEquals(tooltipsJson.getString("title"), driver.getElementAttributeValueByName("employment[1][title]", "data-original-title")); //job title data-original-title
        assertEquals(tooltipsJson.getString("jobDescription"), driver.getElementAttributeValueByName("employment[1][jobDescription]", "data-original-title")); //job description data-original-title

        /*
         * Slide 4 - Volunteer Work
         */
        driver.clickButtonById("volunteer_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation

        assertEquals(tooltipsJson.getString("volCompany"), driver.getElementAttributeValueByName("volunteering[0][volCompany]", "data-original-title")); //volunteer company data-original-title
        assertEquals(tooltipsJson.getString("volStartDate"), driver.getElementAttributeValueByName("volunteering[0][volStartDate]", "data-original-title")); //volunteer volStartDate data-original-title
        assertEquals(tooltipsJson.getString("volEndDate"), driver.getElementAttributeValueByName("volunteering[0][volEndDate]", "data-original-title")); //volunteer volEndDate data-original-title
        assertEquals(tooltipsJson.getString("volTitle"), driver.getElementAttributeValueByName("volunteering[0][volTitle]", "data-original-title")); //volunteer title data-original-title
        assertEquals(tooltipsJson.getString("volDescription"), driver.getElementAttributeValueByName("volunteering[0][volDescription]", "data-original-title")); //volunteer description data-original-title
        driver.clickButtonById("more_fields_volunteer"); //click add more entry
        assertEquals(tooltipsJson.getString("volCompany"), driver.getElementAttributeValueByName("volunteering[1][volCompany]", "data-original-title")); //volunteer company data-original-title
        assertEquals(tooltipsJson.getString("volStartDate"), driver.getElementAttributeValueByName("volunteering[1][volStartDate]", "data-original-title")); //volunteer volStartDate data-original-title
        assertEquals(tooltipsJson.getString("volEndDate"), driver.getElementAttributeValueByName("volunteering[1][volEndDate]", "data-original-title")); //volunteer volEndDate data-original-title
        assertEquals(tooltipsJson.getString("volTitle"), driver.getElementAttributeValueByName("volunteering[1][volTitle]", "data-original-title")); //volunteer title data-original-title
        assertEquals(tooltipsJson.getString("volDescription"), driver.getElementAttributeValueByName("volunteering[1][volDescription]", "data-original-title")); //volunteer description data-original-title

        /*
         * Slide 5 - Education
         */
        driver.clickButtonById("education_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation

        assertEquals(tooltipsJson.getString("schoolYear"), driver.getElementAttributeValueByName("education[0][schoolYear]", "data-original-title")); //education year data-original-title
        assertEquals(tooltipsJson.getString("schoolYearGrade"), driver.getElementAttributeValueByName("education[0][schoolYearGrade]", "data-original-title")); //education year grade data-original-title
        for (int i = 0; i < 5; i++) {
            assertEquals(tooltipsJson.getString("classes"), driver.getElementAttributeValueByName("education[0][subjects][" + i + "][subject]", "data-original-title")); //education subject data-original-title
            assertEquals(tooltipsJson.getString("grades"), driver.getElementAttributeValueByName("education[0][subjects][" + i + "][grade]", "data-original-title")); //education subject grade data-original-title
        }
        driver.clickButtonById("addSubjectButton_0"); //click + to add a new subject line to education_0
        assertEquals(tooltipsJson.getString("classes"), driver.getElementAttributeValueByName("education[0][subjects][5][subject]", "data-original-title")); //education subject data-original-title
        assertEquals(tooltipsJson.getString("grades"), driver.getElementAttributeValueByName("education[0][subjects][5][grade]", "data-original-title")); //education subject grade data-original-title

        driver.clickButtonById("more_fields_education"); //click add more education button

        assertEquals(tooltipsJson.getString("schoolYear"), driver.getElementAttributeValueByName("education[1][schoolYear]", "data-original-title")); //education year data-original-title
        assertEquals(tooltipsJson.getString("schoolYearGrade"), driver.getElementAttributeValueByName("education[1][schoolYearGrade]", "data-original-title")); //education year grade data-original-title
        for (int i = 0; i < 5; i++) {
            assertEquals(tooltipsJson.getString("classes"), driver.getElementAttributeValueByName("education[1][subjects][" + i + "][subject]", "data-original-title")); //education subject data-original-title
            assertEquals(tooltipsJson.getString("grades"), driver.getElementAttributeValueByName("education[1][subjects][" + i + "][grade]", "data-original-title")); //education subject grade data-original-title
        }
        driver.clickButtonById("addSubjectButton_1"); //click + to add a new subject line to education_1
        assertEquals(tooltipsJson.getString("classes"), driver.getElementAttributeValueByName("education[1][subjects][5][subject]", "data-original-title")); //education subject data-original-title
        assertEquals(tooltipsJson.getString("grades"), driver.getElementAttributeValueByName("education[1][subjects][5][grade]", "data-original-title")); //education subject grade data-original-title

        /*
         * Slide 7 - Achievement & Awards
         */
        driver.clickButtonById("awards_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation

        assertEquals(tooltipsJson.getString("awardYear"), driver.getElementAttributeValueByName("awards[0][awardYear]", "data-original-title")); //awards data-original-title
        assertEquals(tooltipsJson.getString("award"), driver.getElementAttributeValueByName("awards[0][award]", "data-original-title")); //awards data-original-title
        driver.clickButtonById("more_fields_achievements"); //click add more entry
        assertEquals(tooltipsJson.getString("awardYear"), driver.getElementAttributeValueByName("awards[1][awardYear]", "data-original-title")); //awards data-original-title
        assertEquals(tooltipsJson.getString("award"), driver.getElementAttributeValueByName("awards[1][award]", "data-original-title")); //awards data-original-title

        assertEquals(tooltipsJson.getString("extracurYear"), driver.getElementAttributeValueByName("extracur[0][extracurYear]", "data-original-title")); //activity data-original-title
        assertEquals(tooltipsJson.getString("activity"), driver.getElementAttributeValueByName("extracur[0][activity]", "data-original-title")); //activity data-original-title
        driver.clickButtonById("more_fields_extracurricular"); //click add more entry
        assertEquals(tooltipsJson.getString("extracurYear"), driver.getElementAttributeValueByName("extracur[1][extracurYear]", "data-original-title")); //activity data-original-title
        assertEquals(tooltipsJson.getString("activity"), driver.getElementAttributeValueByName("extracur[1][activity]", "data-original-title")); //activity data-original-title

        /*
         * Slide 8 - Referees
         */
        driver.clickButtonById("referees_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation

        assertEquals(tooltipsJson.getString("refName"), driver.getElementAttributeValueByName("referees[0][refName]", "data-original-title")); //referee name data-original-title
        assertEquals(tooltipsJson.getString("refPosition"), driver.getElementAttributeValueByName("referees[0][refPosition]", "data-original-title")); //referee position data-original-title
        assertEquals(tooltipsJson.getString("refEmail"), driver.getElementAttributeValueByName("referees[0][refEmail]", "data-original-title")); //referee email data-original-title
        assertEquals(tooltipsJson.getString("refNum"), driver.getElementAttributeValueByName("referees[0][refNum]", "data-original-title")); //referee phone number data-original-title

        driver.clickButtonById("more_fields_referees"); //click add more referee

        assertEquals(tooltipsJson.getString("refName"), driver.getElementAttributeValueByName("referees[1][refName]", "data-original-title")); //referee name data-original-title
        assertEquals(tooltipsJson.getString("refPosition"), driver.getElementAttributeValueByName("referees[1][refPosition]", "data-original-title")); //referee position data-original-title
        assertEquals(tooltipsJson.getString("refEmail"), driver.getElementAttributeValueByName("referees[1][refEmail]", "data-original-title")); //referee email data-original-title
        assertEquals(tooltipsJson.getString("refNum"), driver.getElementAttributeValueByName("referees[1][refNum]", "data-original-title")); //referee phone number data-original-title
    }

    @Test
    public void allTooltipTextShouldBeCorrectOnEditCV() {
        /*
         * This test ensures that all tooltip text in each field is loaded correctly from tooltips.json on Edit CV
         */
        SimpleJsonParser tooltipsJson = new SimpleJsonParser("../tooltips.json");
        driver.login(url, "001"); //login using testId and click Create CV
        driver.clickSubmitButtonByValue("Edit CV");
        driver.waitFor(waitTime);

        /*
         * Slide 1 - Personal Details
         */
        assertEquals(tooltipsJson.getString("firstName"), driver.getElementAttributeValueByName("firstName", "data-original-title")); //first name data-original-title
        assertEquals(tooltipsJson.getString("lastName"), driver.getElementAttributeValueByName("lastName", "data-original-title")); //last name data-original-title
        assertEquals(tooltipsJson.getString("age"), driver.getElementAttributeValueByName("age", "data-original-title")); //age data-original-title
        assertEquals(tooltipsJson.getString("languages"), driver.getElementAttributeValueByName("languages[0][language]", "data-original-title")); //language 0 data-original-title
        assertEquals(tooltipsJson.getString("languages"), driver.getElementAttributeValueByName("languages[1][language]", "data-original-title")); //language 0 data-original-title
        assertEquals(tooltipsJson.getString("languages"), driver.getElementAttributeValueByName("languages[2][language]", "data-original-title")); //language 0 data-original-title
        driver.clickButtonById("addLanguageButton"); //press + button to add 1 more language field
        assertEquals(tooltipsJson.getString("languages"), driver.getElementAttributeValueByName("languages[3][language]", "data-original-title")); //language 1 data-original-title
        assertEquals(tooltipsJson.getString("school"), driver.getElementAttributeValueByName("school", "data-original-title")); //school data-original-title
        assertEquals(tooltipsJson.getString("license"), driver.getElementAttributeValueByName("license", "data-original-title")); //license data-original-title
        assertEquals(tooltipsJson.getString("phoneNum"), driver.getElementAttributeValueByName("phoneNum", "data-original-title")); //phone number data-original-title
        assertEquals(tooltipsJson.getString("mail"), driver.getElementAttributeValueByName("mail", "data-original-title")); //email data-original-title
        assertEquals(tooltipsJson.getString("suburb"), driver.getElementAttributeValueByName("suburb", "data-original-title")); //suburb data-original-title
        assertEquals(tooltipsJson.getString("city"), driver.getElementAttributeValueByName("city", "data-original-title")); //city data-original-title

        /*
         * Slide 3 - Employment
         */
        driver.clickButtonById("employment_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation

        assertEquals(tooltipsJson.getString("jobCompany"), driver.getElementAttributeValueByName("employment[0][jobCompany]", "data-original-title")); //company data-original-title
        assertEquals(tooltipsJson.getString("startDate"), driver.getElementAttributeValueByName("employment[0][startDate]", "data-original-title")); //startDate data-original-title
        assertEquals(tooltipsJson.getString("endDate"), driver.getElementAttributeValueByName("employment[0][endDate]", "data-original-title")); //endDate data-original-title
        assertEquals(tooltipsJson.getString("title"), driver.getElementAttributeValueByName("employment[0][title]", "data-original-title")); //job title data-original-title
        assertEquals(tooltipsJson.getString("jobDescription"), driver.getElementAttributeValueByName("employment[0][jobDescription]", "data-original-title")); //job description data-original-title

        assertEquals(tooltipsJson.getString("jobCompany"), driver.getElementAttributeValueByName("employment[1][jobCompany]", "data-original-title")); //company data-original-title
        assertEquals(tooltipsJson.getString("startDate"), driver.getElementAttributeValueByName("employment[1][startDate]", "data-original-title")); //startDate data-original-title
        assertEquals(tooltipsJson.getString("endDate"), driver.getElementAttributeValueByName("employment[1][endDate]", "data-original-title")); //endDate data-original-title
        assertEquals(tooltipsJson.getString("title"), driver.getElementAttributeValueByName("employment[1][title]", "data-original-title")); //job title data-original-title
        assertEquals(tooltipsJson.getString("jobDescription"), driver.getElementAttributeValueByName("employment[1][jobDescription]", "data-original-title")); //job description data-original-title


        driver.clickButtonById("more_fields_employment"); //click add more entry
        assertEquals(tooltipsJson.getString("jobCompany"), driver.getElementAttributeValueByName("employment[2][jobCompany]", "data-original-title")); //company data-original-title
        assertEquals(tooltipsJson.getString("startDate"), driver.getElementAttributeValueByName("employment[2][startDate]", "data-original-title")); //startDate data-original-title
        assertEquals(tooltipsJson.getString("endDate"), driver.getElementAttributeValueByName("employment[2][endDate]", "data-original-title")); //endDate data-original-title
        assertEquals(tooltipsJson.getString("title"), driver.getElementAttributeValueByName("employment[2][title]", "data-original-title")); //job title data-original-title
        assertEquals(tooltipsJson.getString("jobDescription"), driver.getElementAttributeValueByName("employment[2][jobDescription]", "data-original-title")); //job description data-original-title

        /*
         * Slide 4 - Volunteer Work
         */
        driver.clickButtonById("volunteer_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation

        assertEquals(tooltipsJson.getString("volCompany"), driver.getElementAttributeValueByName("volunteering[0][volCompany]", "data-original-title")); //volunteer company data-original-title
        assertEquals(tooltipsJson.getString("volStartDate"), driver.getElementAttributeValueByName("volunteering[0][volStartDate]", "data-original-title")); //volunteer volStartDate data-original-title
        assertEquals(tooltipsJson.getString("volEndDate"), driver.getElementAttributeValueByName("volunteering[0][volEndDate]", "data-original-title")); //volunteer volEndDate data-original-title
        assertEquals(tooltipsJson.getString("volTitle"), driver.getElementAttributeValueByName("volunteering[0][volTitle]", "data-original-title")); //volunteer title data-original-title
        assertEquals(tooltipsJson.getString("volDescription"), driver.getElementAttributeValueByName("volunteering[0][volDescription]", "data-original-title")); //volunteer description data-original-title

        assertEquals(tooltipsJson.getString("volCompany"), driver.getElementAttributeValueByName("volunteering[1][volCompany]", "data-original-title")); //volunteer company data-original-title
        assertEquals(tooltipsJson.getString("volStartDate"), driver.getElementAttributeValueByName("volunteering[1][volStartDate]", "data-original-title")); //volunteer volStartDate data-original-title
        assertEquals(tooltipsJson.getString("volEndDate"), driver.getElementAttributeValueByName("volunteering[1][volEndDate]", "data-original-title")); //volunteer volEndDate data-original-title
        assertEquals(tooltipsJson.getString("volTitle"), driver.getElementAttributeValueByName("volunteering[1][volTitle]", "data-original-title")); //volunteer title data-original-title

        driver.clickButtonById("more_fields_volunteer"); //click add more entry
        assertEquals(tooltipsJson.getString("volCompany"), driver.getElementAttributeValueByName("volunteering[2][volCompany]", "data-original-title")); //volunteer company data-original-title
        assertEquals(tooltipsJson.getString("volStartDate"), driver.getElementAttributeValueByName("volunteering[2][volStartDate]", "data-original-title")); //volunteer volStartDate data-original-title
        assertEquals(tooltipsJson.getString("volEndDate"), driver.getElementAttributeValueByName("volunteering[2][volEndDate]", "data-original-title")); //volunteer volEndDate data-original-title
        assertEquals(tooltipsJson.getString("volTitle"), driver.getElementAttributeValueByName("volunteering[2][volTitle]", "data-original-title")); //volunteer title data-original-title
        assertEquals(tooltipsJson.getString("volDescription"), driver.getElementAttributeValueByName("volunteering[2][volDescription]", "data-original-title")); //volunteer description data-original-title

        /*
         * Slide 5 - Education
         */
        driver.clickButtonById("education_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation

        assertEquals(tooltipsJson.getString("schoolYear"), driver.getElementAttributeValueByName("education[0][schoolYear]", "data-original-title")); //education year data-original-title
        assertEquals(tooltipsJson.getString("schoolYearGrade"), driver.getElementAttributeValueByName("education[0][schoolYearGrade]", "data-original-title")); //education year grade data-original-title
        for (int i = 0; i < 5; i++) {
            assertEquals(tooltipsJson.getString("classes"), driver.getElementAttributeValueByName("education[0][subjects][" + i + "][subject]", "data-original-title")); //education subject data-original-title
            assertEquals(tooltipsJson.getString("grades"), driver.getElementAttributeValueByName("education[0][subjects][" + i + "][grade]", "data-original-title")); //education subject grade data-original-title
        }
        driver.clickButtonById("addSubjectButton_0"); //click + to add a new subject line to education_0
        assertEquals(tooltipsJson.getString("classes"), driver.getElementAttributeValueByName("education[0][subjects][5][subject]", "data-original-title")); //education subject data-original-title
        assertEquals(tooltipsJson.getString("grades"), driver.getElementAttributeValueByName("education[0][subjects][5][grade]", "data-original-title")); //education subject grade data-original-title

        assertEquals(tooltipsJson.getString("schoolYear"), driver.getElementAttributeValueByName("education[1][schoolYear]", "data-original-title")); //education year data-original-title
        assertEquals(tooltipsJson.getString("schoolYearGrade"), driver.getElementAttributeValueByName("education[1][schoolYearGrade]", "data-original-title")); //education year grade data-original-title
        for (int i = 0; i < 5; i++) {
            assertEquals(tooltipsJson.getString("classes"), driver.getElementAttributeValueByName("education[1][subjects][" + i + "][subject]", "data-original-title")); //education subject data-original-title
            assertEquals(tooltipsJson.getString("grades"), driver.getElementAttributeValueByName("education[1][subjects][" + i + "][grade]", "data-original-title")); //education subject grade data-original-title
        }
        driver.clickButtonById("addSubjectButton_1"); //click + to add a new subject line to education_1
        assertEquals(tooltipsJson.getString("classes"), driver.getElementAttributeValueByName("education[1][subjects][5][subject]", "data-original-title")); //education subject data-original-title
        assertEquals(tooltipsJson.getString("grades"), driver.getElementAttributeValueByName("education[1][subjects][5][grade]", "data-original-title")); //education subject grade data-original-title

        assertEquals(tooltipsJson.getString("schoolYear"), driver.getElementAttributeValueByName("education[2][schoolYear]", "data-original-title")); //education year data-original-title
        assertEquals(tooltipsJson.getString("schoolYearGrade"), driver.getElementAttributeValueByName("education[2][schoolYearGrade]", "data-original-title")); //education year grade data-original-title
        for (int i = 0; i < 5; i++) {
            assertEquals(tooltipsJson.getString("classes"), driver.getElementAttributeValueByName("education[2][subjects][" + i + "][subject]", "data-original-title")); //education subject data-original-title
            assertEquals(tooltipsJson.getString("grades"), driver.getElementAttributeValueByName("education[2][subjects][" + i + "][grade]", "data-original-title")); //education subject grade data-original-title
        }
        driver.clickButtonById("addSubjectButton_2"); //click + to add a new subject line to education_2
        assertEquals(tooltipsJson.getString("classes"), driver.getElementAttributeValueByName("education[2][subjects][5][subject]", "data-original-title")); //education subject data-original-title
        assertEquals(tooltipsJson.getString("grades"), driver.getElementAttributeValueByName("education[2][subjects][5][grade]", "data-original-title")); //education subject grade data-original-title

        driver.clickButtonById("more_fields_education"); //click add more education button

        assertEquals(tooltipsJson.getString("schoolYear"), driver.getElementAttributeValueByName("education[3][schoolYear]", "data-original-title")); //education year data-original-title
        assertEquals(tooltipsJson.getString("schoolYearGrade"), driver.getElementAttributeValueByName("education[3][schoolYearGrade]", "data-original-title")); //education year grade data-original-title
        for (int i = 0; i < 5; i++) {
            assertEquals(tooltipsJson.getString("classes"), driver.getElementAttributeValueByName("education[3][subjects][" + i + "][subject]", "data-original-title")); //education subject data-original-title
            assertEquals(tooltipsJson.getString("grades"), driver.getElementAttributeValueByName("education[3][subjects][" + i + "][grade]", "data-original-title")); //education subject grade data-original-title
        }
        driver.clickButtonById("addSubjectButton_3"); //click + to add a new subject line to education_3
        assertEquals(tooltipsJson.getString("classes"), driver.getElementAttributeValueByName("education[3][subjects][5][subject]", "data-original-title")); //education subject data-original-title
        assertEquals(tooltipsJson.getString("grades"), driver.getElementAttributeValueByName("education[3][subjects][5][grade]", "data-original-title")); //education subject grade data-original-title

        /*
         * Slide 7 - Achievement & Awards
         */
        driver.clickButtonById("awards_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation

        assertEquals(tooltipsJson.getString("awardYear"), driver.getElementAttributeValueByName("awards[0][awardYear]", "data-original-title")); //awards data-original-title
        assertEquals(tooltipsJson.getString("award"), driver.getElementAttributeValueByName("awards[0][award]", "data-original-title")); //awards data-original-title

        assertEquals(tooltipsJson.getString("awardYear"), driver.getElementAttributeValueByName("awards[1][awardYear]", "data-original-title")); //awards data-original-title
        assertEquals(tooltipsJson.getString("award"), driver.getElementAttributeValueByName("awards[1][award]", "data-original-title")); //awards data-original-title
        driver.clickButtonById("more_fields_achievements"); //click add more entry
        assertEquals(tooltipsJson.getString("awardYear"), driver.getElementAttributeValueByName("awards[2][awardYear]", "data-original-title")); //awards data-original-title
        assertEquals(tooltipsJson.getString("award"), driver.getElementAttributeValueByName("awards[2][award]", "data-original-title")); //awards data-original-title

        assertEquals(tooltipsJson.getString("extracurYear"), driver.getElementAttributeValueByName("extracur[0][extracurYear]", "data-original-title")); //activity data-original-title
        assertEquals(tooltipsJson.getString("activity"), driver.getElementAttributeValueByName("extracur[0][activity]", "data-original-title")); //activity data-original-title

        assertEquals(tooltipsJson.getString("extracurYear"), driver.getElementAttributeValueByName("extracur[1][extracurYear]", "data-original-title")); //activity data-original-title
        assertEquals(tooltipsJson.getString("activity"), driver.getElementAttributeValueByName("extracur[1][activity]", "data-original-title")); //activity data-original-title
        driver.clickButtonById("more_fields_extracurricular"); //click add more entry
        assertEquals(tooltipsJson.getString("extracurYear"), driver.getElementAttributeValueByName("extracur[2][extracurYear]", "data-original-title")); //activity data-original-title
        assertEquals(tooltipsJson.getString("activity"), driver.getElementAttributeValueByName("extracur[2][activity]", "data-original-title")); //activity data-original-title

        /*
         * Slide 8 - Referees
         */
        driver.clickButtonById("referees_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation

        assertEquals(tooltipsJson.getString("refName"), driver.getElementAttributeValueByName("referees[0][refName]", "data-original-title")); //referee name data-original-title
        assertEquals(tooltipsJson.getString("refPosition"), driver.getElementAttributeValueByName("referees[0][refPosition]", "data-original-title")); //referee position data-original-title
        assertEquals(tooltipsJson.getString("refEmail"), driver.getElementAttributeValueByName("referees[0][refEmail]", "data-original-title")); //referee email data-original-title
        assertEquals(tooltipsJson.getString("refNum"), driver.getElementAttributeValueByName("referees[0][refNum]", "data-original-title")); //referee phone number data-original-title

        assertEquals(tooltipsJson.getString("refName"), driver.getElementAttributeValueByName("referees[1][refName]", "data-original-title")); //referee name data-original-title
        assertEquals(tooltipsJson.getString("refPosition"), driver.getElementAttributeValueByName("referees[1][refPosition]", "data-original-title")); //referee position data-original-title
        assertEquals(tooltipsJson.getString("refEmail"), driver.getElementAttributeValueByName("referees[1][refEmail]", "data-original-title")); //referee email data-original-title
        assertEquals(tooltipsJson.getString("refNum"), driver.getElementAttributeValueByName("referees[1][refNum]", "data-original-title")); //referee phone number data-original-title

        assertEquals(tooltipsJson.getString("refName"), driver.getElementAttributeValueByName("referees[2][refName]", "data-original-title")); //referee name data-original-title
        assertEquals(tooltipsJson.getString("refPosition"), driver.getElementAttributeValueByName("referees[2][refPosition]", "data-original-title")); //referee position data-original-title
        assertEquals(tooltipsJson.getString("refEmail"), driver.getElementAttributeValueByName("referees[2][refEmail]", "data-original-title")); //referee email data-original-title
        assertEquals(tooltipsJson.getString("refNum"), driver.getElementAttributeValueByName("referees[2][refNum]", "data-original-title")); //referee phone number data-original-title

        driver.clickButtonById("more_fields_referees"); //click add more referee

        assertEquals(tooltipsJson.getString("refName"), driver.getElementAttributeValueByName("referees[3][refName]", "data-original-title")); //referee name data-original-title
        assertEquals(tooltipsJson.getString("refPosition"), driver.getElementAttributeValueByName("referees[3][refPosition]", "data-original-title")); //referee position data-original-title
        assertEquals(tooltipsJson.getString("refEmail"), driver.getElementAttributeValueByName("referees[3][refEmail]", "data-original-title")); //referee email data-original-title
        assertEquals(tooltipsJson.getString("refNum"), driver.getElementAttributeValueByName("referees[3][refNum]", "data-original-title")); //referee phone number data-original-title
    }

    @Test
    public void allInputFieldsShouldHaveCorrectCharacterRestrictionsOnCreateCV() {
        /*
         * This test ensures that all input fields have the correct character restrictions imposed on Create CV
         */
        String testString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ`1234567890-=~!@#$%^&*()_+[]\\{}|;':\",./<>?";
        String limitToLetters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-'";
        String limitToNumbers = "1234567890";
        String limitToLettersAndNumbers = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890-',.";
        String limitToPhoneNumbers = "etxETX1234567890-#()+.";
        String limitToEmails = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890-@_.";
        String limitForTextAreas = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ`1234567890-=~!@#$%^&*()_+[]\\{}|;':\",./?";
        driver.login(url, "000"); //login using testId and click Create CV
        driver.clickSubmitButtonByValue("Create CV");
        driver.waitFor(waitTime);

        driver.getJs().executeScript("document.getElementsByName('firstName')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("firstName", testString); //type in field first name
        assertEquals(limitToLetters, driver.getFieldByName("firstName")); //first name field should be limitToLetters
        driver.getJs().executeScript("document.getElementsByName('lastName')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("lastName", testString); //type in field last name
        assertEquals(limitToLetters, driver.getFieldByName("lastName")); //last name field should be limitToLetters
        driver.getJs().executeScript("document.getElementsByName('age')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("age", testString); //type in field age
        assertEquals(limitToNumbers, driver.getFieldByName("age")); //age field should be limitToNumbers

        driver.getJs().executeScript("document.getElementsByName('languages[0][language]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("languages[0][language]", testString); //type in field language
        assertEquals(limitToLetters, driver.getFieldByName("languages[0][language]")); //first language field should be limitToLetters

        driver.getDriver().findElement(By.id("slide1")).click(); //click away from the text box to remove tooltip
        driver.waitFor(200);
        driver.clickButtonById("addLanguageButton"); //press + button to add 1 more language field

        driver.getJs().executeScript("document.getElementsByName('languages[1][language]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("languages[1][language]", testString); //type in field languages[1][language]
        assertEquals(limitToLetters, driver.getFieldByName("languages[1][language]")); //languages[1][language] field should be limitToLetters

        driver.getJs().executeScript("document.getElementsByName('school')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("school", testString); //type in field school
        assertEquals(limitToLettersAndNumbers, driver.getFieldByName("school")); //school field should be limitToLettersAndNumbers

        driver.getJs().executeScript("document.getElementsByName('phoneNum')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("phoneNum", testString); //type in field phoneNum
        assertEquals(limitToPhoneNumbers, driver.getFieldByName("phoneNum")); //phone number field should be limitToPhoneNumbers
        driver.getJs().executeScript("document.getElementsByName('mail')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("mail", testString); //type in field email
        assertEquals(limitToEmails, driver.getFieldByName("mail")); //email field should be limitToEmails
        driver.getJs().executeScript("document.getElementsByName('suburb')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("suburb", testString); //type in field suburb
        assertEquals(limitToLetters, driver.getFieldByName("suburb")); //suburb field should be limitToLetters
        driver.getJs().executeScript("document.getElementsByName('city')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("city", testString); //type in field city
        assertEquals(limitToLetters, driver.getFieldByName("city")); //city field should be limitToLetters

        /*
         * Slide 2 - Personal Statement
         */
        driver.clickButtonById("statement_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation
        assertTrue(driver.isActiveSlideById("slide2")); //current active slide should be #2

        driver.getJs().executeScript("document.getElementsByName('statement')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("statement", testString); //type in field statement
        assertEquals(limitForTextAreas, driver.getFieldByName("statement")); //personal statement field should be limitForTextAreas

        /*
         * Slide 3 - Employment
         */
        driver.clickButtonById("employment_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation
        assertTrue(driver.isActiveSlideById("slide3")); //current active slide should be #3

        driver.getJs().executeScript("document.getElementsByName('employment[0][jobCompany]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("employment[0][jobCompany]", testString); //type in field employment[0][jobCompany]
        assertEquals(limitToLettersAndNumbers, driver.getFieldByName("employment[0][jobCompany]")); //Company field should be limitToLettersAndNumbers
        driver.getJs().executeScript("document.getElementsByName('employment[0][title]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("employment[0][title]", testString); //type in field employment[0][title]
        assertEquals(limitToLettersAndNumbers, driver.getFieldByName("employment[0][title]")); //Job Title field should be limitToLettersAndNumbers
        driver.getJs().executeScript("document.getElementsByName('employment[0][jobDescription]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("employment[0][jobDescription]", testString); //type in field employment[0][jobDescription]
        assertEquals(limitForTextAreas, driver.getFieldByName("employment[0][jobDescription]")); //Job Description field should be limitForTextAreas

        driver.clickButtonById("more_fields_employment"); //click add more entry

        driver.getJs().executeScript("document.getElementsByName('employment[1][jobCompany]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("employment[1][jobCompany]", testString); //type in field employment[1][jobCompany]
        assertEquals(limitToLettersAndNumbers, driver.getFieldByName("employment[1][jobCompany]")); //New Company field should be limitToLettersAndNumbers
        driver.getJs().executeScript("document.getElementsByName('employment[1][title]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("employment[1][title]", testString); //type in field employment[1][title]
        assertEquals(limitToLettersAndNumbers, driver.getFieldByName("employment[1][title]")); //New Job Title field should be limitToLettersAndNumbers
        driver.getJs().executeScript("document.getElementsByName('employment[1][jobDescription]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("employment[1][jobDescription]", testString); //type in field employment[1][jobDescription]
        assertEquals(limitForTextAreas, driver.getFieldByName("employment[1][jobDescription]")); //Job Description field should be limitForTextAreas

        /*
         * Slide 4 - Volunteer Work
         */
        driver.clickButtonById("volunteer_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation
        assertTrue(driver.isActiveSlideById("slide4")); //current active slide should be #4

        driver.getJs().executeScript("document.getElementsByName('volunteering[0][volCompany]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("volunteering[0][volCompany]", testString); //type in field volunteering[0][volCompany]
        assertEquals(limitToLettersAndNumbers, driver.getFieldByName("volunteering[0][volCompany]")); //Volunteer Company field should be limitToLettersAndNumbers
        driver.getJs().executeScript("document.getElementsByName('volunteering[0][volTitle]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("volunteering[0][volTitle]", testString); //type in field volunteering[0][volTitle]
        assertEquals(limitToLettersAndNumbers, driver.getFieldByName("volunteering[0][volTitle]")); //Volunteer Title field should be limitToLettersAndNumbers
        driver.getJs().executeScript("document.getElementsByName('volunteering[0][volDescription]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("volunteering[0][volDescription]", testString); //type in field volunteering[0][volDescription]
        assertEquals(limitForTextAreas, driver.getFieldByName("volunteering[0][volDescription]")); //Volunteer Description field should be limitForTextAreas

        driver.clickButtonById("more_fields_volunteer"); //click add more entry

        driver.getJs().executeScript("document.getElementsByName('volunteering[1][volCompany]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("volunteering[1][volCompany]", testString); //type in field volunteering[0][volCompany]
        assertEquals(limitToLettersAndNumbers, driver.getFieldByName("volunteering[1][volCompany]")); //Volunteer Company field should be limitToLettersAndNumbers
        driver.getJs().executeScript("document.getElementsByName('volunteering[1][volTitle]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("volunteering[1][volTitle]", testString); //type in field volunteering[0][volTitle]
        assertEquals(limitToLettersAndNumbers, driver.getFieldByName("volunteering[1][volTitle]")); //Volunteer Title field should be limitToLettersAndNumbers
        driver.getJs().executeScript("document.getElementsByName('volunteering[1][volDescription]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("volunteering[1][volDescription]", testString); //type in field volunteering[0][volDescription]
        assertEquals(limitForTextAreas, driver.getFieldByName("volunteering[1][volDescription]")); //Volunteer Description field should be limitForTextAreas

        /*
         * Slide 5 - Education
         */
        driver.clickButtonById("education_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation
        assertTrue(driver.isActiveSlideById("slide5")); //current active slide should be #5

        for (int i = 0; i < 5; i++) {
            driver.getJs().executeScript("document.getElementsByName('education[0][subjects][" + i + "][subject]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
            driver.typeFieldByName("education[0][subjects][" + i + "][subject]", testString);
            assertEquals(limitToLetters, driver.getFieldByName("education[0][subjects][" + i + "][subject]")); //subject field should be limitToLettersAndNumbers
        }
        driver.clickButtonById("addSubjectButton_0");
        driver.getJs().executeScript("document.getElementsByName('education[0][subjects][5][subject]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("education[0][subjects][5][subject]", testString);
        assertEquals(limitToLetters, driver.getFieldByName("education[0][subjects][5][subject]")); //subject field should be limitToLettersAndNumbers

        driver.clickButtonById("more_fields_education");

        for (int i = 0; i < 5; i++) {
            driver.getJs().executeScript("document.getElementsByName('education[1][subjects][" + i + "][subject]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
            driver.typeFieldByName("education[1][subjects][" + i + "][subject]", testString);
            assertEquals(limitToLetters, driver.getFieldByName("education[1][subjects][" + i + "][subject]")); //subject field should be limitToLettersAndNumbers
        }
        driver.clickButtonById("addSubjectButton_1");
        driver.getJs().executeScript("document.getElementsByName('education[1][subjects][5][subject]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("education[1][subjects][5][subject]", testString);
        assertEquals(limitToLetters, driver.getFieldByName("education[1][subjects][5][subject]")); //subject field should be limitToLettersAndNumbers

        /*
         * Slide 7 - Achievement & Awards
         */
        driver.clickButtonById("awards_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation
        assertTrue(driver.isActiveSlideById("slide7")); //current active slide should be #7

        driver.getJs().executeScript("document.getElementsByName('awards[0][award]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("awards[0][award]", testString); //type in field awards[0][award]
        assertEquals(limitForTextAreas, driver.getFieldByName("awards[0][award]")); //Award field should be limitForTextAreas

        driver.clickButtonById("more_fields_achievements"); //click add more entry

        driver.getJs().executeScript("document.getElementsByName('awards[1][award]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("awards[1][award]", testString); //type in field awards[1][award]
        assertEquals(limitForTextAreas, driver.getFieldByName("awards[1][award]")); //new Award field should be limitForTextAreas

        driver.getJs().executeScript("document.getElementsByName('extracur[0][activity]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("extracur[0][activity]", testString);
        assertEquals(limitForTextAreas, driver.getFieldByName("extracur[0][activity]")); //Extracurricular activity should be limitForTextAreas

        driver.clickButtonById("more_fields_extracurricular"); //click add more entry

        driver.getJs().executeScript("document.getElementsByName('extracur[1][activity]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("extracur[1][activity]", testString);
        assertEquals(limitForTextAreas, driver.getFieldByName("extracur[1][activity]")); //new Extracurricular activity should be limitForTextAreas

        /*
         * Slide 8 - Referees
         */
        driver.clickButtonById("referees_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation
        assertTrue(driver.isActiveSlideById("slide8")); //current active slide should be #8

        driver.getJs().executeScript("document.getElementsByName('referees[0][refName]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("referees[0][refName]", testString); //type in field referees[0][refName]
        assertEquals(limitToLetters, driver.getFieldByName("referees[0][refName]")); //Referee name field should be limitToLetters
        driver.getJs().executeScript("document.getElementsByName('referees[0][refPosition]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("referees[0][refPosition]", testString); //type in field referees[0][refPosition]
        assertEquals(limitToLettersAndNumbers, driver.getFieldByName("referees[0][refPosition]")); //Referee Position field should be limitToLetters
        driver.getJs().executeScript("document.getElementsByName('referees[0][refEmail]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("referees[0][refEmail]", testString); //type in field referees[0][refEmail]
        assertEquals(limitToEmails, driver.getFieldByName("referees[0][refEmail]")); //Referee Email field should be limitToEmails
        driver.getJs().executeScript("document.getElementsByName('referees[0][refNum]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("referees[0][refNum]", testString); //type in field referees[0][refNum]
        assertEquals(limitToPhoneNumbers, driver.getFieldByName("referees[0][refNum]")); //Referee Phone Number field should be limitToPhoneNumbers

        driver.clickButtonById("more_fields_referees"); //click add more referee

        driver.getJs().executeScript("document.getElementsByName('referees[1][refName]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("referees[1][refName]", testString); //type in field referees[1][refName]
        assertEquals(limitToLetters, driver.getFieldByName("referees[1][refName]")); //Referee name field should be limitToLetters
        driver.getJs().executeScript("document.getElementsByName('referees[1][refPosition]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("referees[1][refPosition]", testString); //type in field referees[1][refPosition]
        assertEquals(limitToLettersAndNumbers, driver.getFieldByName("referees[1][refPosition]")); //Referee Position field should be limitToLetters
        driver.getJs().executeScript("document.getElementsByName('referees[1][refEmail]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("referees[1][refEmail]", testString); //type in field referees[1][refEmail]
        assertEquals(limitToEmails, driver.getFieldByName("referees[1][refEmail]")); //Referee Email field should be limitToEmails
        driver.getJs().executeScript("document.getElementsByName('referees[1][refNum]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("referees[1][refNum]", testString); //type in field referees[1][refNum]
        assertEquals(limitToPhoneNumbers, driver.getFieldByName("referees[1][refNum]")); //Referee Phone Number field should be limitToPhoneNumbers
    }

    @Test
    public void allInputFieldsShouldHaveCorrectCharacterRestrictionsOnEditCV() {
        /*
         * This test ensures that all input fields have the correct character restrictions imposed on Edit CV
         */
        String testString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ`1234567890-=~!@#$%^&*()_+[]\\{}|;':\",./<>?";
        String limitToLetters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-'";
        String limitToNumbers = "1234567890";
        String limitToLettersAndNumbers = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890-',.";
        String limitToPhoneNumbers = "etxETX1234567890-#()+.";
        String limitToEmails = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890-@_.";
        String limitForTextAreas = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ`1234567890-=~!@#$%^&*()_+[]\\{}|;':\",./?";
        driver.login(url, "001"); //login using testId and click Create CV
        driver.clickSubmitButtonByValue("Edit CV");
        driver.waitFor(waitTime);

        driver.getJs().executeScript("document.getElementsByName('firstName')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("firstName")).clear();
        driver.typeFieldByName("firstName", testString); //type in field first name
        assertEquals(limitToLetters, driver.getFieldByName("firstName")); //first name field should be limitToLetters
        driver.getJs().executeScript("document.getElementsByName('lastName')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("lastName")).clear();
        driver.typeFieldByName("lastName", testString); //type in field last name
        assertEquals(limitToLetters, driver.getFieldByName("lastName")); //last name field should be limitToLetters
        driver.getJs().executeScript("document.getElementsByName('age')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("age")).clear();
        driver.typeFieldByName("age", testString); //type in field age
        assertEquals(limitToNumbers, driver.getFieldByName("age")); //age field should be limitToNumbers

        driver.getJs().executeScript("document.getElementsByName('languages[0][language]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("languages[0][language]")).clear();
        driver.typeFieldByName("languages[0][language]", testString); //type in field language
        assertEquals(limitToLetters, driver.getFieldByName("languages[0][language]")); //first language field should be limitToLetters
        driver.getJs().executeScript("document.getElementsByName('languages[1][language]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("languages[1][language]")).clear();
        driver.typeFieldByName("languages[1][language]", testString); //type in field language
        assertEquals(limitToLetters, driver.getFieldByName("languages[1][language]")); //first language field should be limitToLetters
        driver.getJs().executeScript("document.getElementsByName('languages[2][language]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("languages[2][language]")).clear();
        driver.typeFieldByName("languages[2][language]", testString); //type in field language
        assertEquals(limitToLetters, driver.getFieldByName("languages[2][language]")); //first language field should be limitToLetters

        driver.getDriver().findElement(By.id("slide1")).click(); //click away from the text box to remove tooltip
        driver.waitFor(200);
        driver.clickButtonById("addLanguageButton"); //press + button to add 1 more language field

        driver.getJs().executeScript("document.getElementsByName('languages[3][language]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("languages[3][language]", testString); //type in field languages[1][language]
        assertEquals(limitToLetters, driver.getFieldByName("languages[3][language]")); //languages[1][language] field should be limitToLetters

        driver.getJs().executeScript("document.getElementsByName('school')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("school")).clear();
        driver.typeFieldByName("school", testString); //type in field school
        assertEquals(limitToLettersAndNumbers, driver.getFieldByName("school")); //school field should be limitToLettersAndNumbers
        driver.getJs().executeScript("document.getElementsByName('phoneNum')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("phoneNum")).clear();
        driver.typeFieldByName("phoneNum", testString); //type in field phoneNum
        assertEquals(limitToPhoneNumbers, driver.getFieldByName("phoneNum")); //phone number field should be limitToPhoneNumbers
        driver.getJs().executeScript("document.getElementsByName('mail')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("mail")).clear();
        driver.typeFieldByName("mail", testString); //type in field email
        assertEquals(limitToEmails, driver.getFieldByName("mail")); //email field should be limitToEmails
        driver.getJs().executeScript("document.getElementsByName('suburb')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("suburb")).clear();
        driver.typeFieldByName("suburb", testString); //type in field suburb
        assertEquals(limitToLetters, driver.getFieldByName("suburb")); //suburb field should be limitToLetters
        driver.getJs().executeScript("document.getElementsByName('city')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("city")).clear();
        driver.typeFieldByName("city", testString); //type in field city
        assertEquals(limitToLetters, driver.getFieldByName("city")); //city field should be limitToLetters

        /*
         * Slide 2 - Personal Statement
         */
        driver.clickButtonById("statement_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation
        assertTrue(driver.isActiveSlideById("slide2")); //current active slide should be #2

        driver.getJs().executeScript("document.getElementsByName('statement')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("statement")).clear();
        driver.typeFieldByName("statement", testString); //type in field statement
        assertEquals(limitForTextAreas, driver.getFieldByName("statement")); //personal statement field should be limitForTextAreas

        /*
         * Slide 3 - Employment
         */
        driver.clickButtonById("employment_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation
        assertTrue(driver.isActiveSlideById("slide3")); //current active slide should be #3

        driver.getJs().executeScript("document.getElementsByName('employment[0][jobCompany]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("employment[0][jobCompany]")).clear();
        driver.typeFieldByName("employment[0][jobCompany]", testString); //type in field employment[0][jobCompany]
        assertEquals(limitToLettersAndNumbers, driver.getFieldByName("employment[0][jobCompany]")); //Company field should be limitToLettersAndNumbers
        driver.getJs().executeScript("document.getElementsByName('employment[0][title]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("employment[0][title]")).clear();
        driver.typeFieldByName("employment[0][title]", testString); //type in field employment[0][title]
        assertEquals(limitToLettersAndNumbers, driver.getFieldByName("employment[0][title]")); //Job Title field should be limitToLettersAndNumbers
        driver.getJs().executeScript("document.getElementsByName('employment[0][jobDescription]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("employment[0][jobDescription]")).clear();
        driver.typeFieldByName("employment[0][jobDescription]", testString); //type in field employment[0][jobDescription]
        assertEquals(limitForTextAreas, driver.getFieldByName("employment[0][jobDescription]")); //Job Description field should be limitForTextAreas

        driver.getJs().executeScript("document.getElementsByName('employment[1][jobCompany]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("employment[1][jobCompany]")).clear();
        driver.typeFieldByName("employment[1][jobCompany]", testString); //type in field employment[1][jobCompany]
        assertEquals(limitToLettersAndNumbers, driver.getFieldByName("employment[0][jobCompany]")); //Company field should be limitToLettersAndNumbers
        driver.getJs().executeScript("document.getElementsByName('employment[1][title]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("employment[1][title]")).clear();
        driver.typeFieldByName("employment[1][title]", testString); //type in field employment[1][title]
        assertEquals(limitToLettersAndNumbers, driver.getFieldByName("employment[0][title]")); //Job Title field should be limitToLettersAndNumbers
        driver.getJs().executeScript("document.getElementsByName('employment[1][jobDescription]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("employment[1][jobDescription]")).clear();
        driver.typeFieldByName("employment[1][jobDescription]", testString); //type in field employment[1][jobDescription]
        assertEquals(limitForTextAreas, driver.getFieldByName("employment[1][jobDescription]")); //Job Description field should be limitForTextAreas

        driver.clickButtonById("more_fields_employment"); //click add more entry

        driver.getJs().executeScript("document.getElementsByName('employment[2][jobCompany]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("employment[2][jobCompany]", testString); //type in field employment[2][jobCompany]
        assertEquals(limitToLettersAndNumbers, driver.getFieldByName("employment[2][jobCompany]")); //New Company field should be limitToLettersAndNumbers
        driver.getJs().executeScript("document.getElementsByName('employment[2][title]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("employment[2][title]", testString); //type in field employment[2][title]
        assertEquals(limitToLettersAndNumbers, driver.getFieldByName("employment[2][title]")); //New Job Title field should be limitToLettersAndNumbers
        driver.getJs().executeScript("document.getElementsByName('employment[2][jobDescription]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("employment[2][jobDescription]", testString); //type in field employment[2][jobDescription]
        assertEquals(limitForTextAreas, driver.getFieldByName("employment[2][jobDescription]")); //Job Description field should be limitForTextAreas

        /*
         * Slide 4 - Volunteer Work
         */
        driver.clickButtonById("volunteer_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation
        assertTrue(driver.isActiveSlideById("slide4")); //current active slide should be #4

        driver.getJs().executeScript("document.getElementsByName('volunteering[0][volCompany]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("volunteering[0][volCompany]")).clear();
        driver.typeFieldByName("volunteering[0][volCompany]", testString); //type in field volunteering[0][volCompany]
        assertEquals(limitToLettersAndNumbers, driver.getFieldByName("volunteering[0][volCompany]")); //Volunteer Company field should be limitToLettersAndNumbers
        driver.getJs().executeScript("document.getElementsByName('volunteering[0][volTitle]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("volunteering[0][volTitle]")).clear();
        driver.typeFieldByName("volunteering[0][volTitle]", testString); //type in field volunteering[0][volTitle]
        assertEquals(limitToLettersAndNumbers, driver.getFieldByName("volunteering[0][volTitle]")); //Volunteer Title field should be limitToLettersAndNumbers
        driver.getJs().executeScript("document.getElementsByName('volunteering[0][volDescription]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("volunteering[0][volDescription]")).clear();
        driver.typeFieldByName("volunteering[0][volDescription]", testString); //type in field volunteering[0][volDescription]
        assertEquals(limitForTextAreas, driver.getFieldByName("volunteering[0][volDescription]")); //Volunteer Description field should be limitForTextAreas

        driver.getJs().executeScript("document.getElementsByName('volunteering[1][volCompany]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("volunteering[1][volCompany]")).clear();
        driver.typeFieldByName("volunteering[1][volCompany]", testString); //type in field volunteering[1][volCompany]
        assertEquals(limitToLettersAndNumbers, driver.getFieldByName("volunteering[1][volCompany]")); //Volunteer Company field should be limitToLettersAndNumbers
        driver.getJs().executeScript("document.getElementsByName('volunteering[1][volTitle]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("volunteering[1][volTitle]")).clear();
        driver.typeFieldByName("volunteering[1][volTitle]", testString); //type in field volunteering[1][volTitle]
        assertEquals(limitToLettersAndNumbers, driver.getFieldByName("volunteering[1][volTitle]")); //Volunteer Title field should be limitToLettersAndNumbers
        driver.getJs().executeScript("document.getElementsByName('volunteering[1][volDescription]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("volunteering[1][volDescription]")).clear();
        driver.typeFieldByName("volunteering[1][volDescription]", testString); //type in field volunteering[1][volDescription]
        assertEquals(limitForTextAreas, driver.getFieldByName("volunteering[1][volDescription]")); //Volunteer Description field should be limitForTextAreas

        driver.clickButtonById("more_fields_volunteer"); //click add more entry

        driver.getJs().executeScript("document.getElementsByName('volunteering[2][volCompany]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("volunteering[2][volCompany]", testString); //type in field volunteering[2][volCompany]
        assertEquals(limitToLettersAndNumbers, driver.getFieldByName("volunteering[2][volCompany]")); //Volunteer Company field should be limitToLettersAndNumbers
        driver.getJs().executeScript("document.getElementsByName('volunteering[2][volTitle]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("volunteering[2][volTitle]", testString); //type in field volunteering[2][volTitle]
        assertEquals(limitToLettersAndNumbers, driver.getFieldByName("volunteering[2][volTitle]")); //Volunteer Title field should be limitToLettersAndNumbers
        driver.getJs().executeScript("document.getElementsByName('volunteering[2][volDescription]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("volunteering[2][volDescription]", testString); //type in field volunteering[2][volDescription]
        assertEquals(limitForTextAreas, driver.getFieldByName("volunteering[2][volDescription]")); //Volunteer Description field should be limitForTextAreas

        /*
         * Slide 5 - Education
         */
        driver.clickButtonById("education_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation
        assertTrue(driver.isActiveSlideById("slide5")); //current active slide should be #5

        for (int i = 0; i < 5; i++) {
            driver.getJs().executeScript("document.getElementsByName('education[0][subjects][" + i + "][subject]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
            driver.getDriver().findElement(By.name("education[0][subjects][" + i + "][subject]")).clear();
            driver.typeFieldByName("education[0][subjects][" + i + "][subject]", testString);
            assertEquals(limitToLetters, driver.getFieldByName("education[0][subjects][" + i + "][subject]")); //subject field should be limitToLettersAndNumbers
        }
        driver.clickButtonById("addSubjectButton_0");
        driver.getJs().executeScript("document.getElementsByName('education[0][subjects][5][subject]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("education[0][subjects][5][subject]", testString);
        assertEquals(limitToLetters, driver.getFieldByName("education[0][subjects][5][subject]")); //subject field should be limitToLettersAndNumbers

        for (int i = 0; i < 5; i++) {
            driver.getJs().executeScript("document.getElementsByName('education[1][subjects][" + i + "][subject]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
            driver.getDriver().findElement(By.name("education[1][subjects][" + i + "][subject]")).clear();
            driver.typeFieldByName("education[1][subjects][" + i + "][subject]", testString);
            assertEquals(limitToLetters, driver.getFieldByName("education[1][subjects][" + i + "][subject]")); //subject field should be limitToLettersAndNumbers
        }
        driver.clickButtonById("addSubjectButton_1");
        driver.getJs().executeScript("document.getElementsByName('education[1][subjects][5][subject]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("education[1][subjects][5][subject]", testString);
        assertEquals(limitToLetters, driver.getFieldByName("education[1][subjects][5][subject]")); //subject field should be limitToLettersAndNumbers

        for (int i = 0; i < 5; i++) {
            driver.getJs().executeScript("document.getElementsByName('education[2][subjects][" + i + "][subject]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
            driver.getDriver().findElement(By.name("education[2][subjects][" + i + "][subject]")).clear();
            driver.typeFieldByName("education[2][subjects][" + i + "][subject]", testString);
            assertEquals(limitToLetters, driver.getFieldByName("education[2][subjects][" + i + "][subject]")); //subject field should be limitToLettersAndNumbers
        }
        driver.clickButtonById("addSubjectButton_2");
        driver.getJs().executeScript("document.getElementsByName('education[2][subjects][5][subject]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("education[2][subjects][5][subject]", testString);
        assertEquals(limitToLetters, driver.getFieldByName("education[2][subjects][5][subject]")); //subject field should be limitToLettersAndNumbers

        driver.clickButtonById("more_fields_education");

        for (int i = 0; i < 5; i++) {
            driver.getJs().executeScript("document.getElementsByName('education[3][subjects][" + i + "][subject]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
            driver.typeFieldByName("education[3][subjects][" + i + "][subject]", testString);
            assertEquals(limitToLetters, driver.getFieldByName("education[3][subjects][" + i + "][subject]")); //subject field should be limitToLettersAndNumbers
        }
        driver.clickButtonById("addSubjectButton_3");
        driver.getJs().executeScript("document.getElementsByName('education[3][subjects][5][subject]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("education[3][subjects][5][subject]", testString);
        assertEquals(limitToLetters, driver.getFieldByName("education[3][subjects][5][subject]")); //subject field should be limitToLettersAndNumbers

        /*
         * Slide 7 - Achievement & Awards
         */
        driver.clickButtonById("awards_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation
        assertTrue(driver.isActiveSlideById("slide7")); //current active slide should be #7

        driver.getJs().executeScript("document.getElementsByName('awards[0][award]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("awards[0][award]")).clear();
        driver.typeFieldByName("awards[0][award]", testString); //type in field awards[0][award]
        assertEquals(limitForTextAreas, driver.getFieldByName("awards[0][award]")); //Award field should be limitForTextAreas

        driver.getJs().executeScript("document.getElementsByName('awards[1][award]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("awards[1][award]")).clear();
        driver.typeFieldByName("awards[1][award]", testString); //type in field awards[1][award]
        assertEquals(limitForTextAreas, driver.getFieldByName("awards[1][award]")); //Award field should be limitForTextAreas

        driver.clickButtonById("more_fields_achievements"); //click add more entry

        driver.getJs().executeScript("document.getElementsByName('awards[2][award]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("awards[2][award]", testString); //type in field awards[1][award]
        assertEquals(limitForTextAreas, driver.getFieldByName("awards[2][award]")); //new Award field should be limitForTextAreas

        driver.getJs().executeScript("document.getElementsByName('extracur[0][activity]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("extracur[0][activity]")).clear();
        driver.typeFieldByName("extracur[0][activity]", testString);
        assertEquals(limitForTextAreas, driver.getFieldByName("extracur[0][activity]")); //Extracurricular activity should be limitForTextAreas

        driver.getJs().executeScript("document.getElementsByName('extracur[1][activity]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("extracur[1][activity]")).clear();
        driver.typeFieldByName("extracur[1][activity]", testString);
        assertEquals(limitForTextAreas, driver.getFieldByName("extracur[1][activity]")); //Extracurricular activity should be limitForTextAreas

        driver.clickButtonById("more_fields_extracurricular"); //click add more entry

        driver.getJs().executeScript("document.getElementsByName('extracur[2][activity]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("extracur[2][activity]", testString);
        assertEquals(limitForTextAreas, driver.getFieldByName("extracur[2][activity]")); //new Extracurricular activity should be limitForTextAreas

        /*
         * Slide 8 - Referees
         */
        driver.clickButtonById("referees_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation
        assertTrue(driver.isActiveSlideById("slide8")); //current active slide should be #8

        driver.getJs().executeScript("document.getElementsByName('referees[0][refName]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("referees[0][refName]")).clear();
        driver.typeFieldByName("referees[0][refName]", testString); //type in field referees[0][refName]
        assertEquals(limitToLetters, driver.getFieldByName("referees[0][refName]")); //Referee name field should be limitToLetters
        driver.getJs().executeScript("document.getElementsByName('referees[0][refPosition]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("referees[0][refPosition]")).clear();
        driver.typeFieldByName("referees[0][refPosition]", testString); //type in field referees[0][refPosition]
        assertEquals(limitToLettersAndNumbers, driver.getFieldByName("referees[0][refPosition]")); //Referee Position field should be limitToLetters
        driver.getJs().executeScript("document.getElementsByName('referees[0][refEmail]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("referees[0][refEmail]")).clear();
        driver.typeFieldByName("referees[0][refEmail]", testString); //type in field referees[0][refEmail]
        assertEquals(limitToEmails, driver.getFieldByName("referees[0][refEmail]")); //Referee Email field should be limitToEmails
        driver.getJs().executeScript("document.getElementsByName('referees[0][refNum]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("referees[0][refNum]")).clear();
        driver.typeFieldByName("referees[0][refNum]", testString); //type in field referees[0][refNum]
        assertEquals(limitToPhoneNumbers, driver.getFieldByName("referees[0][refNum]")); //Referee Phone Number field should be limitToPhoneNumbers

        driver.getJs().executeScript("document.getElementsByName('referees[1][refName]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("referees[1][refName]")).clear();
        driver.typeFieldByName("referees[1][refName]", testString); //type in field referees[1][refName]
        assertEquals(limitToLetters, driver.getFieldByName("referees[1][refName]")); //Referee name field should be limitToLetters
        driver.getJs().executeScript("document.getElementsByName('referees[1][refPosition]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("referees[1][refPosition]")).clear();
        driver.typeFieldByName("referees[1][refPosition]", testString); //type in field referees[1][refPosition]
        assertEquals(limitToLettersAndNumbers, driver.getFieldByName("referees[1][refPosition]")); //Referee Position field should be limitToLetters
        driver.getJs().executeScript("document.getElementsByName('referees[1][refEmail]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("referees[1][refEmail]")).clear();
        driver.typeFieldByName("referees[1][refEmail]", testString); //type in field referees[1][refEmail]
        assertEquals(limitToEmails, driver.getFieldByName("referees[1][refEmail]")); //Referee Email field should be limitToEmails
        driver.getJs().executeScript("document.getElementsByName('referees[1][refNum]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("referees[1][refNum]")).clear();
        driver.typeFieldByName("referees[1][refNum]", testString); //type in field referees[1][refNum]
        assertEquals(limitToPhoneNumbers, driver.getFieldByName("referees[1][refNum]")); //Referee Phone Number field should be limitToPhoneNumbers

        driver.getJs().executeScript("document.getElementsByName('referees[2][refName]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("referees[2][refName]")).clear();
        driver.typeFieldByName("referees[2][refName]", testString); //type in field referees[2][refName]
        assertEquals(limitToLetters, driver.getFieldByName("referees[2][refName]")); //Referee name field should be limitToLetters
        driver.getJs().executeScript("document.getElementsByName('referees[2][refPosition]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("referees[2][refPosition]")).clear();
        driver.typeFieldByName("referees[2][refPosition]", testString); //type in field referees[2][refPosition]
        assertEquals(limitToLettersAndNumbers, driver.getFieldByName("referees[2][refPosition]")); //Referee Position field should be limitToLetters
        driver.getJs().executeScript("document.getElementsByName('referees[2][refEmail]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("referees[2][refEmail]")).clear();
        driver.typeFieldByName("referees[2][refEmail]", testString); //type in field referees[2][refEmail]
        assertEquals(limitToEmails, driver.getFieldByName("referees[2][refEmail]")); //Referee Email field should be limitToEmails
        driver.getJs().executeScript("document.getElementsByName('referees[2][refNum]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.getDriver().findElement(By.name("referees[2][refNum]")).clear();
        driver.typeFieldByName("referees[2][refNum]", testString); //type in field referees[2][refNum]
        assertEquals(limitToPhoneNumbers, driver.getFieldByName("referees[2][refNum]")); //Referee Phone Number field should be limitToPhoneNumbers

        driver.clickButtonById("more_fields_referees"); //click add more referee

        driver.getJs().executeScript("document.getElementsByName('referees[3][refName]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("referees[3][refName]", testString); //type in field referees[3][refName]
        assertEquals(limitToLetters, driver.getFieldByName("referees[3][refName]")); //Referee name field should be limitToLetters
        driver.getJs().executeScript("document.getElementsByName('referees[3][refPosition]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("referees[3][refPosition]", testString); //type in field referees[3][refPosition]
        assertEquals(limitToLettersAndNumbers, driver.getFieldByName("referees[3][refPosition]")); //Referee Position field should be limitToLetters
        driver.getJs().executeScript("document.getElementsByName('referees[3][refEmail]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("referees[3][refEmail]", testString); //type in field referees[3][refEmail]
        assertEquals(limitToEmails, driver.getFieldByName("referees[3][refEmail]")); //Referee Email field should be limitToEmails
        driver.getJs().executeScript("document.getElementsByName('referees[3][refNum]')[0].removeAttribute('maxLength');"); //remove maxLength attribute
        driver.typeFieldByName("referees[3][refNum]", testString); //type in field referees[3][refNum]
        assertEquals(limitToPhoneNumbers, driver.getFieldByName("referees[3][refNum]")); //Referee Phone Number field should be limitToPhoneNumbers
    }

    @After
    public void tearDown() throws Exception {
        driver.closeDriver();
    }
}
