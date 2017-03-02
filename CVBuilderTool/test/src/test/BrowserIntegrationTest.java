package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import testClasses.Chrome;
import testClasses.Firefox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by josh on 20/01/17.
 */
public class BrowserIntegrationTest {
    long waitTime = 1000;
    //Firefox driver; //Firefox driver - has compatibility issues using Selenium to select dates via datepicker
    Chrome driver;
    String url;
    List<String> licenseMenu = new ArrayList<>(Arrays.asList("No License", "Learner's License", "Restricted License", "Full License"));
    List<String> gradeMenu = new ArrayList<>(Arrays.asList("", "Currently studying NCEA Level 1", "Currently studying NCEA Level 2", "Currently studying NCEA Level 3",
            "NCEA Level 1", "NCEA Level 1 endorsed with Merit", "NCEA Level 1 endorsed with Excellence", "NCEA Level 2", "NCEA Level 2 endorsed with Merit",
            "NCEA Level 2 endorsed with Excellence", "NCEA Level 3", "NCEA Level 3 endorsed with Merit", "NCEA Level 3 endorsed with Excellence"));
    List<String> attributeNames = new ArrayList<>(Arrays.asList("accurate", "adaptable", "alert", "ambitious", "amiable", "analytical", "articulate", "assertive", "attentive", "broad-minded", "businesslike", "calm",
            "capable", "careful", "competent", "confident", "conscientious", "consistent", "cooperative", "dedicated", "dependable", "determined", "efficient", "energetic", "enterprising", "flexible",
            "hardworking", "honest", "independent", "industrious", "innovative", "motivated", "optimistic", "organized", "patient", "people-oriented", "persevering", "practical", "productive", "realistic", "reliable",
            "resourceful", "responsible", "versatile"));

    @Before
    public void setUp() throws Exception {
        url = "http://localhost/home/index.php";

        //driver = new Firefox(); //new Firefox driver instance
        driver = new Chrome(); //new Chrome driver instance
    }

    @Test
    public void integrationTestOnCreateCVStartOnBlankFields() {
        /*
         * This integration test will check if all the fields are its correct default values on clicking "Create CV"
         */

        driver.login(url, "000"); //log in as "001" and click Create CV button for empty fields

        assertTrue(driver.elementByValueHasAttribute("Edit CV", "disabled")); //if there are no existing user.json, button should be disabled.

        driver.clickSubmitButtonByValue("Create CV"); //click "Create CV" button
        driver.waitFor(waitTime); //wait 1 second for page to load

        /*
         * Slide 1 - Personal Details
         */
        assertTrue(driver.isActiveSlideById("slide1")); //start on slide 1 of carousel

        assertEquals("", driver.getFieldByName("firstName")); //first name field should be empty
        assertEquals("", driver.getFieldByName("lastName")); //last name field should be empty
        assertEquals("", driver.getFieldByName("age")); //age field should be empty
        assertEquals("", driver.getFieldByName("languages[0][language]")); //first language field should be empty
        assertEquals(0, Integer.parseInt(driver.getJsVar("languageTracker"))); //javascript languageTracker should be 0
        assertEquals("", driver.getFieldByName("school")); //school field should be empty
        assertEquals("No License", driver.getFieldByName("license")); //License field has "No license" selected
        for (int i = 0; i < licenseMenu.size(); i++) { //test if all the license drop down menus are correct
            String actualText = driver.getDropDownOptionsByName("license").get(i).getText();
            String expectedText = licenseMenu.get(i);
            if (!actualText.equals(expectedText)) {
                fail("License menu list test failed! Expected text (" + expectedText + ") does not match actual text (" + actualText + ")!");
                break;
            }
        }
        assertEquals("", driver.getFieldByName("phoneNum")); //phone number field should be empty
        assertEquals("", driver.getFieldByName("mail")); //email field should be empty
        assertEquals("", driver.getFieldByName("suburb")); //suburb field should be empty
        assertEquals("", driver.getFieldByName("city")); //city field should be empty
        assertEquals("New Zealand", driver.getFieldById("country")); //Country field has "New Zealand" selected

        /*
         * Slide 2 - Personal Statement
         */
        driver.clickButtonById("statement_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation
        assertTrue(driver.isActiveSlideById("slide2")); //current active slide should be #2

        assertEquals("", driver.getFieldByName("statement")); //personal statement field should be empty

        /*
         * Slide 3 - Employment
         */
        driver.clickButtonById("employment_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation
        assertTrue(driver.isActiveSlideById("slide3")); //current active slide should be #3

        assertEquals("", driver.getFieldByName("employment[0][jobCompany]")); //Company field should be empty
            assertEquals("", driver.getFieldByName("employment[0][startDate]")); //Start Date field should be empty
            assertEquals("", driver.getFieldByName("employment[0][endDate]")); //End Date field should be empty
        //assertTrue(driver.elementByNameHasAttribute("employment[0][endDate]", "disabled"); //End Date should be disabled because there should be no start date entered
        assertEquals("", driver.getFieldByName("employment[0][title]")); //Job Title field should be empty
        assertEquals("", driver.getFieldByName("employment[0][jobDescription]")); //Job Description field should be empty
        assertEquals(0, Integer.parseInt(driver.getJsVar("employmentTracker"))); //javascript employmentTracker should be 0

        /*
         * Slide 4 - Volunteer Work
         */
        driver.clickButtonById("volunteer_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation
        assertTrue(driver.isActiveSlideById("slide4")); //current active slide should be #4

        assertEquals("", driver.getFieldByName("volunteering[0][volCompany]")); //Volunteer Company field should be empty
        assertEquals("", driver.getFieldByName("volunteering[0][volStartDate]")); //Start Date field should be empty
        assertEquals("", driver.getFieldByName("volunteering[0][volEndDate]")); //End Date field should be empty
        //assertTrue(driver.elementByNameHasAttribute("volunteer[0][endDate]", "disabled"); //End Date should be disabled because there should be no start date entered
        assertEquals("", driver.getFieldByName("volunteering[0][volTitle]")); //Volunteer Title field should be empty
        assertEquals("", driver.getFieldByName("volunteering[0][volDescription]")); //Volunteer Description field should be empty
        assertEquals(0, Integer.parseInt(driver.getJsVar("volunteerTracker"))); //javascript volunteerTracker should be 0

        /*
         * Slide 5 - Education
         */
        driver.clickButtonById("education_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation
        assertTrue(driver.isActiveSlideById("slide5")); //current active slide should be #5

        assertEquals("", driver.getFieldByName("education[0][schoolYear]")); //School year field should be empty
        assertEquals("", driver.getFieldByName("education[0][schoolYearGrade]")); //School year grade has "" selected
        for (int i = 0; i < gradeMenu.size(); i++) { //test if all the school year grade drop down menus are correct
            String actualText = driver.getDropDownOptionsByName("education[0][schoolYearGrade]").get(i).getText();
            String expectedText = gradeMenu.get(i);
            if (!actualText.equals(expectedText)) {
                fail("schoolYearGrade menu list test failed! Expected text (" + expectedText + ") does not match actual text (" + actualText + ")!");
                break;
            }
        }
        for (int i = 0; i < 5; i++) { //test all 6 subject lines
            assertEquals("", driver.getFieldByName("education[0][subjects][" + i + "][subject]")); //each subject class should be empty
            for (int j = 0; j < gradeMenu.size(); j++) { //test all grade drop down text are correct for each subject line
                String actualText = driver.getDropDownOptionsByName("education[0][subjects][" + i + "][grade]").get(i).getText();
                String expectedText = gradeMenu.get(i);
                if (!actualText.equals(expectedText)) {
                    fail("education[0][subjects][" + i + "][grade] menu list test failed! Expected text (" + expectedText + ") does not match actual text (" + actualText + ")!");
                    break;
                }
            }
        }
        assertEquals(0, Integer.parseInt(driver.getJsVar("educationTracker"))); //javascript educationTracker should be 0
        assertEquals(4, Integer.parseInt(driver.getJsVar("subjectsTracker[0]"))); //javascript subjectsTracker[0] should be 4

        /*
         * Slide 6 - Personal Attributes
         */
        driver.clickButtonById("attributes_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation
        assertTrue(driver.isActiveSlideById("slide6")); //current active slide should be #6

        assertFalse(driver.isBoxCheckedById("accurate")); //accurate should be unchecked
        assertFalse(driver.isBoxCheckedById("adaptable")); //adaptable should be unchecked
        assertFalse(driver.isBoxCheckedById("alert")); //adaptable should be unchecked
        assertFalse(driver.isBoxCheckedById("ambitious")); //adaptable should be unchecked
        assertFalse(driver.isBoxCheckedById("amiable")); //adaptable should be unchecked
        assertFalse(driver.isBoxCheckedById("analytical")); //analytical should be unchecked
        assertFalse(driver.isBoxCheckedById("articulate")); //articulate should be unchecked
        assertFalse(driver.isBoxCheckedById("assertive")); //assertive should be unchecked
        assertFalse(driver.isBoxCheckedById("attentive")); //attentive should be unchecked
        assertFalse(driver.isBoxCheckedById("broad-minded")); //broad-minded should be unchecked
        assertFalse(driver.isBoxCheckedById("businesslike")); //businesslike should be unchecked
        assertFalse(driver.isBoxCheckedById("calm")); //calm should be unchecked
        assertFalse(driver.isBoxCheckedById("capable")); //capable should be unchecked
        assertFalse(driver.isBoxCheckedById("careful")); //careful should be unchecked
        assertFalse(driver.isBoxCheckedById("competent")); //competent should be unchecked
        assertFalse(driver.isBoxCheckedById("confident")); //confident should be unchecked
        assertFalse(driver.isBoxCheckedById("conscientious")); //conscientious should be unchecked
        assertFalse(driver.isBoxCheckedById("consistent")); //consistent should be unchecked
        assertFalse(driver.isBoxCheckedById("cooperative")); //cooperative should be unchecked
        assertFalse(driver.isBoxCheckedById("dedicated")); //dedicated should be unchecked
        assertFalse(driver.isBoxCheckedById("dependable")); //dependable should be unchecked
        assertFalse(driver.isBoxCheckedById("determined")); //determined should be unchecked
        assertFalse(driver.isBoxCheckedById("efficient")); //efficient should be unchecked
        assertFalse(driver.isBoxCheckedById("energetic")); //energetic should be unchecked
        assertFalse(driver.isBoxCheckedById("enterprising")); //enterprising should be unchecked
        assertFalse(driver.isBoxCheckedById("flexible")); //flexible should be unchecked
        assertFalse(driver.isBoxCheckedById("hardworking")); //hardworking should be unchecked
        assertFalse(driver.isBoxCheckedById("honest")); //honest should be unchecked
        assertFalse(driver.isBoxCheckedById("independent")); //independent should be unchecked
        assertFalse(driver.isBoxCheckedById("industrious")); //industrious should be unchecked
        assertFalse(driver.isBoxCheckedById("innovative")); //innovative should be unchecked
        assertFalse(driver.isBoxCheckedById("motivated")); //motivated should be unchecked
        assertFalse(driver.isBoxCheckedById("optimistic")); //optimistic should be unchecked
        assertFalse(driver.isBoxCheckedById("organized")); //organized should be unchecked
        assertFalse(driver.isBoxCheckedById("patient")); //patient should be unchecked
        assertFalse(driver.isBoxCheckedById("people-oriented")); //people-oriented should be unchecked
        assertFalse(driver.isBoxCheckedById("persevering")); //persevering should be unchecked
        assertFalse(driver.isBoxCheckedById("practical")); //practical should be unchecked
        assertFalse(driver.isBoxCheckedById("productive")); //productive should be unchecked
        assertFalse(driver.isBoxCheckedById("realistic")); //realistic should be unchecked
        assertFalse(driver.isBoxCheckedById("reliable")); //reliable should be unchecked
        assertFalse(driver.isBoxCheckedById("resourceful")); //resourceful should be unchecked
        assertFalse(driver.isBoxCheckedById("responsible")); //responsible should be unchecked
        assertFalse(driver.isBoxCheckedById("versatile")); //versatile should be unchecked

        /*
         * Slide 7 - Achievement & Awards
         */
        driver.clickButtonById("awards_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation
        assertTrue(driver.isActiveSlideById("slide7")); //current active slide should be #7

        assertEquals("", driver.getFieldByName("awards[0][awardYear]")); //Award Year field should be empty
        assertEquals("", driver.getFieldByName("awards[0][award]")); //Award field should be empty
        assertEquals(0, Integer.parseInt(driver.getJsVar("achievementsTracker"))); //javascript achievementsTracker should be 0
        assertEquals("", driver.getFieldByName("extracur[0][extracurYear]")); //Extracurricular Year field should be empty
        assertEquals("", driver.getFieldByName("extracur[0][activity]")); //Extracurricular activity should be empty
        assertEquals(0, Integer.parseInt(driver.getJsVar("extracurricularTracker"))); //javascript extracurricularTracker should be 0

        /*
         * Slide 8 - Referees
         */
        driver.clickButtonById("referees_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation
        assertTrue(driver.isActiveSlideById("slide8")); //current active slide should be #8

        assertEquals("", driver.getFieldByName("referees[0][refName]")); //Referee name field should be empty
        assertEquals("", driver.getFieldByName("referees[0][refPosition]")); //Referee Position field should be empty
        assertEquals("", driver.getFieldByName("referees[0][refEmail]")); //Referee Email field should be empty
        assertEquals("", driver.getFieldByName("referees[0][refNum]")); //Referee Phone Number field should be empty
        assertEquals(0, Integer.parseInt(driver.getJsVar("refereesTracker"))); //javascript refereesTracker should be 0
    }

    @Test
    public void integrationTestOnCreateCVAllBasicFunctionalities() {
        /*
         * This integration test will enter text, press add more field buttons once and verify for all fields.
         */

        driver.login(url, "000"); //log in as "001" and click Create CV button for empty fields

        assertTrue(driver.elementByValueHasAttribute("Edit CV", "disabled")); //if there are no existing user.json, button should be disabled.

        driver.clickSubmitButtonByValue("Create CV"); //click "Create CV" button
        driver.waitFor(waitTime); //wait 1 second for page to load

        /*
         * Slide 1 - Personal Details
         */
        assertTrue(driver.isActiveSlideById("slide1")); //start on slide 1 of carousel

        driver.typeFieldByName("firstName", "Jason"); //type in field first anem
        assertEquals("Jason", driver.getFieldByName("firstName")); //first name field should be Jason
        driver.typeFieldByName("lastName", "Bourne"); //type in field last name
        assertEquals("Bourne", driver.getFieldByName("lastName")); //last name field should be Bourne
        driver.typeFieldByName("age", "50"); //type in field age
        assertEquals("50", driver.getFieldByName("age")); //age field should be 50
        driver.typeFieldByName("languages[0][language]", "English"); //type in field language
        assertEquals("English", driver.getFieldByName("languages[0][language]")); //first language field should be English
        assertEquals(0, Integer.parseInt(driver.getJsVar("languageTracker"))); //javascript languageTracker should be 0

        driver.getDriver().findElement(By.id("slide1")).click(); //click away from the text box to remove tooltip
        driver.waitFor(250);
        driver.clickButtonById("addLanguageButton"); //press + button to add 1 more language field
        assertEquals(1, Integer.parseInt(driver.getJsVar("languageTracker"))); //javascript languageTracker should be 1
        driver.typeFieldByName("languages[1][language]", "Spanish"); //type in field languages[1][language]
        assertEquals("Spanish", driver.getFieldByName("languages[1][language]")); //languages[1][language] field should be Spanish

        driver.getDriver().findElement(By.id("slide1")).click();
        driver.waitFor(200);

        driver.clickButtonById("addLanguageButton"); //press + button to add 1 more language field
        assertEquals(2, Integer.parseInt(driver.getJsVar("languageTracker"))); //javascript languageTracker should be 2
        driver.clickButtonById("removeLanguageButton"); //press - button to remove 1 language field
        assertEquals(1, Integer.parseInt(driver.getJsVar("languageTracker"))); //javascript languageTracker should be 1
        driver.clickButtonById("removeLanguageButton"); //press - button to remove 1 language field
        assertEquals(0, Integer.parseInt(driver.getJsVar("languageTracker"))); //javascript languageTracker should be 0

        driver.typeFieldByName("school", "Wellington High School"); //type in field school
        assertEquals("Wellington High School", driver.getFieldByName("school")); //school field should be Wellington High School
        driver.selectFieldByName("license", "Full License"); //select "Full License" for license
        assertEquals("Full License", driver.getFieldByName("license")); //License field has "Full license" selected
        for (int i = 0; i < licenseMenu.size(); i++) { //test if all the license drop down menus are correct
            String actualText = driver.getDropDownOptionsByName("license").get(i).getText();
            String expectedText = licenseMenu.get(i);
            if (!actualText.equals(expectedText)) {
                fail("License menu list test failed! Expected text (" + expectedText + ") does not match actual text (" + actualText + ")!");
                break;
            }
        }
        driver.typeFieldByName("phoneNum", "1234567890"); //type in field phoneNum
        assertEquals("1234567890", driver.getFieldByName("phoneNum")); //phone number field should be 1234567890
        driver.typeFieldByName("mail", "jason@spy.com"); //type in field email
        assertEquals("jason@spy.com", driver.getFieldByName("mail")); //email field should be jason@spy.com
        driver.typeFieldByName("suburb", "Remuera"); //type in field suburb
        assertEquals("Remuera", driver.getFieldByName("suburb")); //suburb field should be Remuera
        driver.typeFieldByName("city", "Auckland"); //type in field city
        assertEquals("Auckland", driver.getFieldByName("city")); //city field should be Auckland
        driver.selectFieldByName("country", "Iceland"); //select "Iceland" for country
        assertEquals("Iceland", driver.getFieldById("country")); //Country field has "Iceland" selected

        /*
         * Slide 2 - Personal Statement
         */
        driver.clickButtonById("statement_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation
        assertTrue(driver.isActiveSlideById("slide2")); //current active slide should be #2

        String loremIpsum = "Lorem ipsum dolor sit amet, an justo vidisse nostrud vis, ex tamquam iuvaret rationibus his. Cetero disputando adversarium cu his, eam harum " +
                "adolescens scriptorem et. At mei possim denique dissentiunt, eam veritus copiosae pertinax an. Integre eripuit minimum mel id, cu iusto liberavisse ullamcorper has. Sed cu clita diceret.";
        driver.typeFieldByName("statement", loremIpsum); //type in field statement
        assertEquals(loremIpsum, driver.getFieldByName("statement")); //personal statement field should be the lorem ipsum text

        /*
         * Slide 3 - Employment
         */
        driver.clickButtonById("employment_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation
        assertTrue(driver.isActiveSlideById("slide3")); //current active slide should be #3

        driver.typeFieldByName("employment[0][jobCompany]", "Disneyland"); //type in field employment[0][jobCompany]
        assertEquals("Disneyland", driver.getFieldByName("employment[0][jobCompany]")); //Company field should be Disney Land
        driver.selectCalendarMonthYearByName("employment[0][startDate]", "Jan", 2013); //Using date picker for start date
        assertEquals("January 2013", driver.getFieldByName("employment[0][startDate]")); //Start Date field should be January 2013
        driver.selectCalendarMonthYearByName("employment[0][endDate]", "Mar", 2015); //Using date picker for end date
        assertEquals("March 2015", driver.getFieldByName("employment[0][endDate]")); //End Date field should be March 2015
        //assertTrue(driver.elementByNameHasAttribute("employment[0][endDate]", "disabled"); //End Date should be disabled because there should be no start date entered
        driver.typeFieldByName("employment[0][title]", "Ride Operator"); //type in field employment[0][title]
        assertEquals("Ride Operator", driver.getFieldByName("employment[0][title]")); //Job Title field should be Ride Operator
        driver.typeFieldByName("employment[0][jobDescription]", "Operating the farris wheel\nCleaning puke"); //type in field employment[0][jobDescription]
        assertEquals("Operating the farris wheel\nCleaning puke", driver.getFieldByName("employment[0][jobDescription]")); //Job Description field should be Operating the farris wheel Cleaning puke

        assertEquals(0, Integer.parseInt(driver.getJsVar("employmentTracker"))); //javascript employmentTracker should be 0
        driver.clickButtonById("more_fields_employment"); //click add more entry
        assertEquals(1, Integer.parseInt(driver.getJsVar("employmentTracker"))); //javascript employmentTracker should be 1
        assertEquals("", driver.getFieldByName("employment[1][jobCompany]")); //New Company field should be Disney Land
        assertEquals("", driver.getFieldByName("employment[1][startDate]")); //New Start Date field should be empty
        assertEquals("", driver.getFieldByName("employment[1][endDate]")); //New End Date field should be empty
        assertEquals("", driver.getFieldByName("employment[1][title]")); //New Job Title field should be empty
        assertEquals("", driver.getFieldByName("employment[1][jobDescription]")); //Job Description field should be empty
        driver.clickButtonById("remove_fields_employment"); //click remove last entry
        assertEquals(0, Integer.parseInt(driver.getJsVar("employmentTracker"))); //javascript employmentTracker should be 0

        /*
         * Slide 4 - Volunteer Work
         */
        driver.clickButtonById("volunteer_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation
        assertTrue(driver.isActiveSlideById("slide4")); //current active slide should be #4

        driver.typeFieldByName("volunteering[0][volCompany]", "Homeless Shelter"); //type in field volunteering[0][volCompany]
        assertEquals("Homeless Shelter", driver.getFieldByName("volunteering[0][volCompany]")); //Volunteer Company field should be Homeless Shelter
        driver.selectCalendarMonthYearByName("volunteering[0][volStartDate]", "Dec", 2000); //Using date picker for start date
        assertEquals("December 2000", driver.getFieldByName("volunteering[0][volStartDate]")); //Start Date field should be December 2000
        driver.selectCalendarMonthYearByName("volunteering[0][volEndDate]", "Nov", 2005); //Using date picker for start date
        assertEquals("November 2005", driver.getFieldByName("volunteering[0][volEndDate]")); //End Date field should be November 2005
        //assertTrue(driver.elementByNameHasAttribute("volunteer[0][endDate]", "disabled"); //End Date should be disabled because there should be no start date entered
        driver.typeFieldByName("volunteering[0][volTitle]", "Volunteer"); //type in field volunteering[0][volTitle]
        assertEquals("Volunteer", driver.getFieldByName("volunteering[0][volTitle]")); //Volunteer Title field should be Volunteer
        driver.typeFieldByName("volunteering[0][volDescription]", "Serving food to the homeless"); //type in field volunteering[0][volDescription]
        assertEquals("Serving food to the homeless", driver.getFieldByName("volunteering[0][volDescription]")); //Volunteer Description field should be empty

        assertEquals(0, Integer.parseInt(driver.getJsVar("volunteerTracker"))); //javascript volunteerTracker should be 0
        driver.clickButtonById("more_fields_volunteer"); //click add more entry
        assertEquals("", driver.getFieldByName("volunteering[1][volCompany]")); //Volunteer Company field should be empty
        assertEquals("", driver.getFieldByName("volunteering[1][volStartDate]")); //Start Date field should be empty
        assertEquals("", driver.getFieldByName("volunteering[1][volEndDate]")); //End Date field should be empty
        //assertTrue(driver.elementByNameHasAttribute("volunteer[1][endDate]", "disabled"); //End Date should be disabled because there should be no start date entered
        assertEquals("", driver.getFieldByName("volunteering[1][volTitle]")); //Volunteer Title field should be empty
        assertEquals("", driver.getFieldByName("volunteering[1][volDescription]")); //Volunteer Description field should be empty
        driver.clickButtonById("remove_fields_volunteer"); //click remove last entry
        assertEquals(0, Integer.parseInt(driver.getJsVar("volunteerTracker"))); //javascript volunteerTracker should be 0

        /*
         * Slide 5 - Education
         */
        driver.clickButtonById("education_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation
        assertTrue(driver.isActiveSlideById("slide5")); //current active slide should be #5

        driver.selectCalendarYearByName("education[0][schoolYear]", 2017); //Using date picker for school year
        assertEquals("2017", driver.getFieldByName("education[0][schoolYear]")); //School year field should be empty
        driver.selectFieldByName("education[0][schoolYearGrade]", "Currently studying NCEA Level 3"); //select "Currently studying NCEA Level 3" for education[0][schoolYearGrade]
        assertEquals("Currently studying NCEA Level 3", driver.getFieldByName("education[0][schoolYearGrade]")); //School year grade has "Currently studying NCEA Level 3" selected
        for (int i = 0; i < gradeMenu.size(); i++) { //test if all the school year grade drop down menus are correct
            String actualText = driver.getDropDownOptionsByName("education[0][schoolYearGrade]").get(i).getText();
            String expectedText = gradeMenu.get(i);
            if (!actualText.equals(expectedText)) {
                fail("schoolYearGrade menu list test failed! Expected text (" + expectedText + ") does not match actual text (" + actualText + ")!");
                break;
            }
        }
        for (int i = 0; i < 5; i++) { //test all 5 subject lines
            driver.typeFieldByName("education[0][subjects][" + i + "][subject]", "English"); //type in English# for each subject
            assertEquals("English", driver.getFieldByName("education[0][subjects][" + i + "][subject]")); //each subject class should be English#
            driver.selectFieldByName("education[0][subjects][" + i + "][grade]", "NCEA Level 1"); //select NCEA Level 1 for all subjects
            assertEquals("NCEA Level 1", driver.getFieldByName("education[0][subjects][" + i + "][grade]"));
            for (int j = 0; j < gradeMenu.size(); j++) { //test all grade drop down text are correct for each subject line
                String actualText = driver.getDropDownOptionsByName("education[0][subjects][" + i + "][grade]").get(i).getText();
                String expectedText = gradeMenu.get(i);
                if (!actualText.equals(expectedText)) {
                    fail("education[0][subjects][" + i + "][grade] menu list test failed! Expected text (" + expectedText + ") does not match actual text (" + actualText + ")!");
                    break;
                }
            }
        }

        assertEquals(4, Integer.parseInt(driver.getJsVar("subjectsTracker[0]"))); //javascript subjectsTracker[0] should be 4
        driver.clickButtonById("addSubjectButton_0"); //click + to add a new subject line to education_0
        assertEquals(5, Integer.parseInt(driver.getJsVar("subjectsTracker[0]"))); //javascript subjectsTracker[0] should be 5
        assertEquals("", driver.getFieldByName("education[0][subjects][5][subject]")); //the new subject should be empty
        assertEquals("", driver.getFieldByName("education[0][subjects][5][grade]")); //the new subject should be empty
        driver.getDriver().findElement(By.id("slide5")).click(); //click away from the text box to remove tooltip
        driver.clickButtonById("removeSubjectButton_0"); //click + to add a new subject line to education_0
        assertEquals(4, Integer.parseInt(driver.getJsVar("subjectsTracker[0]"))); //javascript subjectsTracker[0] should be 4

        assertEquals(0, Integer.parseInt(driver.getJsVar("educationTracker"))); //javascript educationTracker should be 0
        driver.clickButtonById("more_fields_education"); //click add more education button
        assertEquals(1, Integer.parseInt(driver.getJsVar("educationTracker"))); //javascript educationTracker should be 1

        assertEquals("", driver.getFieldByName("education[1][schoolYear]")); //School year field should be empty
        assertEquals("", driver.getFieldByName("education[1][schoolYearGrade]")); //School year grade has "" selected

        for (int i = 0; i < gradeMenu.size(); i++) { //test if all the school year grade drop down menus are correct
            String actualText = driver.getDropDownOptionsByName("education[1][schoolYearGrade]").get(i).getText();
            String expectedText = gradeMenu.get(i);
            if (!actualText.equals(expectedText)) {
                fail("schoolYearGrade menu list test failed! Expected text (" + expectedText + ") does not match actual text (" + actualText + ")!");
                break;
            }
        }
        for (int i = 0; i < 5; i++) { //test all 6 subject lines
            assertEquals("", driver.getFieldByName("education[1][subjects][" + i + "][subject]")); //each subject class should be empty
            for (int j = 0; j < gradeMenu.size(); j++) { //test all grade drop down text are correct for each subject line
                String actualText = driver.getDropDownOptionsByName("education[1][subjects][" + i + "][grade]").get(i).getText();
                String expectedText = gradeMenu.get(i);
                if (!actualText.equals(expectedText)) {
                    fail("education[1][subjects][" + i + "][grade] menu list test failed! Expected text (" + expectedText + ") does not match actual text (" + actualText + ")!");
                    break;
                }
            }
        }
        assertEquals(4, Integer.parseInt(driver.getJsVar("subjectsTracker[1]"))); //javascript subjectsTracker[1] should be 4
        driver.clickButtonById("addSubjectButton_1"); //click + to add a new subject line to education_0
        assertEquals(5, Integer.parseInt(driver.getJsVar("subjectsTracker[1]"))); //javascript subjectsTracker[1] should be 5
        assertEquals("", driver.getFieldByName("education[1][subjects][5][subject]")); //the new subject should be empty
        assertEquals("", driver.getFieldByName("education[1][subjects][5][grade]")); //the new subject should be empty
        driver.clickButtonById("removeSubjectButton_1"); //click + to add a new subject line to education_1
        assertEquals(4, Integer.parseInt(driver.getJsVar("subjectsTracker[1]"))); //javascript subjectsTracker[1] should be 4

        driver.clickButtonById("remove_fields_education"); //click remove last entry button
        assertEquals(0, Integer.parseInt(driver.getJsVar("educationTracker"))); //javascript educationTracker should be 1

        /*
         * Slide 6 - Personal Attributes
         */
        driver.clickButtonById("attributes_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation
        assertTrue(driver.isActiveSlideById("slide6")); //current active slide should be #6

        /*
        driver.checkBoxClickById("accurate"); //check attribute accurate
        assertTrue(driver.isBoxCheckedById("accurate")); //accurate should be checked
        driver.checkBoxClickById("accurate"); //uncheck attribute accurate
        assertFalse(driver.isBoxCheckedById("accurate")); //accurate should be unchecked

        driver.checkBoxClickById("adaptable"); //check attribute adaptable
        assertTrue(driver.isBoxCheckedById("adaptable")); //adaptable should be checked
        driver.checkBoxClickById("adaptable"); //uncheck attribute adaptable
        assertFalse(driver.isBoxCheckedById("adaptable")); //adaptable should be unchecked

        driver.checkBoxClickById("alert"); //check attribute alert
        assertTrue(driver.isBoxCheckedById("alert")); //adaptable should be checked
        driver.checkBoxClickById("alert"); //uncheck attribute alert
        assertFalse(driver.isBoxCheckedById("alert")); //adaptable should be unchecked

        driver.checkBoxClickById("ambitious"); //check attribute ambitious
        assertTrue(driver.isBoxCheckedById("ambitious")); //adaptable should be checked
        driver.checkBoxClickById("ambitious"); //uncheck attribute ambitious
        assertFalse(driver.isBoxCheckedById("ambitious")); //adaptable should be unchecked

        driver.checkBoxClickById("amiable"); //check attribute amiable
        assertTrue(driver.isBoxCheckedById("amiable")); //adaptable should be checked
        driver.checkBoxClickById("amiable"); //uncheck attribute amiable
        assertFalse(driver.isBoxCheckedById("amiable")); //adaptable should be unchecked

        driver.checkBoxClickById("analytical"); //check attribute analytical
        assertTrue(driver.isBoxCheckedById("analytical")); //analytical should be checked
        driver.checkBoxClickById("analytical"); //uncheck attribute analytical
        assertFalse(driver.isBoxCheckedById("analytical")); //analytical should be unchecked

        driver.checkBoxClickById("articulate"); //check attribute articulate
        assertTrue(driver.isBoxCheckedById("articulate")); //articulate should be checked
        driver.checkBoxClickById("articulate"); //uncheck attribute articulate
        assertFalse(driver.isBoxCheckedById("articulate")); //articulate should be unchecked

        driver.checkBoxClickById("assertive"); //check attribute assertive
        assertTrue(driver.isBoxCheckedById("assertive")); //assertive should be checked
        driver.checkBoxClickById("assertive"); //uncheck attribute assertive
        assertFalse(driver.isBoxCheckedById("assertive")); //assertive should be unchecked

        driver.checkBoxClickById("attentive"); //check attribute attentive
        assertTrue(driver.isBoxCheckedById("attentive")); //attentive should be checked
        driver.checkBoxClickById("attentive"); //uncheck attribute attentive
        assertFalse(driver.isBoxCheckedById("attentive")); //attentive should be unchecked

        driver.checkBoxClickById("broad-minded"); //check attribute broad-minded
        assertTrue(driver.isBoxCheckedById("broad-minded")); //broad-minded should be checked
        driver.checkBoxClickById("broad-minded"); //uncheck attribute broad-minded
        assertFalse(driver.isBoxCheckedById("broad-minded")); //broad-minded should be unchecked

        driver.checkBoxClickById("businesslike"); //check attribute businesslike
        assertTrue(driver.isBoxCheckedById("businesslike")); //businesslike should be checked
        driver.checkBoxClickById("businesslike"); //uncheck attribute businesslike
        assertFalse(driver.isBoxCheckedById("businesslike")); //businesslike should be unchecked

        driver.checkBoxClickById("calm"); //check attribute calm
        assertTrue(driver.isBoxCheckedById("calm")); //calm should be checked
        driver.checkBoxClickById("calm"); //uncheck attribute calm
        assertFalse(driver.isBoxCheckedById("calm")); //calm should be unchecked

        driver.checkBoxClickById("capable"); //check attribute capable
        assertTrue(driver.isBoxCheckedById("capable")); //capable should be checked
        driver.checkBoxClickById("capable"); //uncheck attribute capable
        assertFalse(driver.isBoxCheckedById("capable")); //capable should be unchecked

        driver.checkBoxClickById("careful"); //check attribute careful
        assertTrue(driver.isBoxCheckedById("careful")); //careful should be checked
        driver.checkBoxClickById("careful"); //uncheck attribute careful
        assertFalse(driver.isBoxCheckedById("careful")); //careful should be unchecked

        driver.checkBoxClickById("competent"); //check attribute competent
        assertTrue(driver.isBoxCheckedById("competent")); //competent should be checked
        driver.checkBoxClickById("competent"); //uncheck attribute competent
        assertFalse(driver.isBoxCheckedById("competent")); //competent should be unchecked

        driver.checkBoxClickById("confident"); //check attribute confident
        assertTrue(driver.isBoxCheckedById("confident")); //confident should be checked
        driver.checkBoxClickById("confident"); //uncheck attribute confident
        assertFalse(driver.isBoxCheckedById("confident")); //confident should be unchecked

        driver.checkBoxClickById("conscientious"); //check attribute conscientious
        assertTrue(driver.isBoxCheckedById("conscientious")); //conscientious should be checked
        driver.checkBoxClickById("conscientious"); //uncheck attribute conscientious
        assertFalse(driver.isBoxCheckedById("conscientious")); //conscientious should be unchecked

        driver.checkBoxClickById("consistent"); //check attribute consistent
        assertTrue(driver.isBoxCheckedById("consistent")); //consistent should be checked
        driver.checkBoxClickById("consistent"); //uncheck attribute consistent
        assertFalse(driver.isBoxCheckedById("consistent")); //consistent should be unchecked

        driver.checkBoxClickById("cooperative"); //check attribute cooperative
        assertTrue(driver.isBoxCheckedById("cooperative")); //cooperative should be checked
        driver.checkBoxClickById("cooperative"); //uncheck attribute cooperative
        assertFalse(driver.isBoxCheckedById("cooperative")); //cooperative should be unchecked

        driver.checkBoxClickById("dedicated"); //check attribute dedicated
        assertTrue(driver.isBoxCheckedById("dedicated")); //dedicated should be checked
        driver.checkBoxClickById("dedicated"); //uncheck attribute
        assertFalse(driver.isBoxCheckedById("dedicated")); //dedicated should be unchecked

        driver.checkBoxClickById("dependable"); //check attribute dependable
        assertTrue(driver.isBoxCheckedById("dependable")); //dependable should be checked
        driver.checkBoxClickById("dependable"); //uncheck attribute dependable
        assertFalse(driver.isBoxCheckedById("dependable")); //dependable should be unchecked

        driver.checkBoxClickById("determined"); //check attribute determined
        assertTrue(driver.isBoxCheckedById("determined")); //determined should be checked
        driver.checkBoxClickById("determined"); //uncheck attribute determined
        assertFalse(driver.isBoxCheckedById("determined")); //determined should be unchecked

        driver.checkBoxClickById("efficient"); //check attribute efficient
        assertTrue(driver.isBoxCheckedById("efficient")); //efficient should be checked
        driver.checkBoxClickById("efficient"); //uncheck attribute efficient
        assertFalse(driver.isBoxCheckedById("efficient")); //efficient should be unchecked

        driver.checkBoxClickById("energetic"); //check attribute energetic
        assertTrue(driver.isBoxCheckedById("energetic")); //energetic should be checked
        driver.checkBoxClickById("energetic"); //uncheck attribute energetic
        assertFalse(driver.isBoxCheckedById("energetic")); //energetic should be unchecked

        driver.checkBoxClickById("enterprising"); //check attribute enterprising
        assertTrue(driver.isBoxCheckedById("enterprising")); //enterprising should be checked
        driver.checkBoxClickById("enterprising"); //uncheck attribute enterprising
        assertFalse(driver.isBoxCheckedById("enterprising")); //enterprising should be unchecked

        driver.checkBoxClickById("flexible"); //check attribute flexible
        assertTrue(driver.isBoxCheckedById("flexible")); //flexible should be checked
        driver.checkBoxClickById("flexible"); //uncheck attribute flexible
        assertFalse(driver.isBoxCheckedById("flexible")); //flexible should be unchecked

        driver.checkBoxClickById("hardworking"); //check attribute hardworking
        assertTrue(driver.isBoxCheckedById("hardworking")); //hardworking should be checked
        driver.checkBoxClickById("hardworking"); //uncheck attribute hardworking
        assertFalse(driver.isBoxCheckedById("hardworking")); //hardworking should be unchecked

        driver.checkBoxClickById("honest"); //check attribute honest
        assertTrue(driver.isBoxCheckedById("honest")); //honest should be checked
        driver.checkBoxClickById("honest"); //uncheck attribute honest
        assertFalse(driver.isBoxCheckedById("honest")); //honest should be unchecked

        driver.checkBoxClickById("independent"); //check attribute independent
        assertTrue(driver.isBoxCheckedById("independent")); //independent should be checked
        driver.checkBoxClickById("independent"); //uncheck attribute independent
        assertFalse(driver.isBoxCheckedById("independent")); //independent should be unchecked

        driver.checkBoxClickById("industrious"); //check attribute industrious
        assertTrue(driver.isBoxCheckedById("industrious")); //industrious should be checked
        driver.checkBoxClickById("industrious"); //uncheck attribute industrious
        assertFalse(driver.isBoxCheckedById("industrious")); //industrious should be unchecked

        driver.checkBoxClickById("innovative"); //check attribute innovative
        assertTrue(driver.isBoxCheckedById("innovative")); //innovative should be checked
        driver.checkBoxClickById("innovative"); //uncheck attribute innovative
        assertFalse(driver.isBoxCheckedById("innovative")); //innovative should be unchecked

        driver.checkBoxClickById("motivated"); //check attribute motivated
        assertTrue(driver.isBoxCheckedById("motivated")); //motivated should be checked
        driver.checkBoxClickById("motivated"); //uncheck attribute motivated
        assertFalse(driver.isBoxCheckedById("motivated")); //motivated should be unchecked

        driver.checkBoxClickById("optimistic"); //check attribute optimistic
        assertTrue(driver.isBoxCheckedById("optimistic")); //optimistic should be checked
        driver.checkBoxClickById("optimistic"); //uncheck attribute optimistic
        assertFalse(driver.isBoxCheckedById("optimistic")); //optimistic should be unchecked

        driver.checkBoxClickById("organized"); //check attribute organized
        assertTrue(driver.isBoxCheckedById("organized")); //organized should be checked
        driver.checkBoxClickById("organized"); //uncheck attribute organized
        assertFalse(driver.isBoxCheckedById("organized")); //organized should be unchecked

        driver.checkBoxClickById("patient"); //check attribute patient
        assertTrue(driver.isBoxCheckedById("patient")); //patient should be checked
        driver.checkBoxClickById("patient"); //uncheck attribute patient
        assertFalse(driver.isBoxCheckedById("patient")); //patient should be unchecked

        driver.checkBoxClickById("people-oriented"); //check attribute people-oriented
        assertTrue(driver.isBoxCheckedById("people-oriented")); //people-oriented should be checked
        driver.checkBoxClickById("people-oriented"); //uncheck attribute people-oriented
        assertFalse(driver.isBoxCheckedById("people-oriented")); //people-oriented should be unchecked

        driver.checkBoxClickById("persevering"); //check attribute persevering
        assertTrue(driver.isBoxCheckedById("persevering")); //persevering should be checked
        driver.checkBoxClickById("persevering"); //uncheck attribute persevering
        assertFalse(driver.isBoxCheckedById("persevering")); //persevering should be unchecked

        driver.checkBoxClickById("practical"); //check attribute practical
        assertTrue(driver.isBoxCheckedById("practical")); //practical should be checked
        driver.checkBoxClickById("practical"); //uncheck attribute practical
        assertFalse(driver.isBoxCheckedById("practical")); //practical should be unchecked

        driver.checkBoxClickById("productive"); //check attribute productive
        assertTrue(driver.isBoxCheckedById("productive")); //productive should be checked
        driver.checkBoxClickById("productive"); //uncheck attribute productive
        assertFalse(driver.isBoxCheckedById("productive")); //productive should be unchecked

        driver.checkBoxClickById("realistic"); //check attribute realistic
        assertTrue(driver.isBoxCheckedById("realistic")); //realistic should be checked
        driver.checkBoxClickById("realistic"); //uncheck attribute realistic
        assertFalse(driver.isBoxCheckedById("realistic")); //realistic should be unchecked

        driver.checkBoxClickById("reliable"); //check attribute reliable
        assertTrue(driver.isBoxCheckedById("reliable")); //reliable should be checked
        driver.checkBoxClickById("reliable"); //uncheck attribute reliable
        assertFalse(driver.isBoxCheckedById("reliable")); //reliable should be unchecked

        driver.checkBoxClickById("resourceful"); //check attribute resourceful
        assertTrue(driver.isBoxCheckedById("resourceful")); //resourceful should be checked
        driver.checkBoxClickById("resourceful"); //uncheck attribute resourceful
        assertFalse(driver.isBoxCheckedById("resourceful")); //resourceful should be unchecked

        driver.checkBoxClickById("responsible"); //check attribute responsible
        assertTrue(driver.isBoxCheckedById("responsible")); //responsible should be checked
        driver.checkBoxClickById("responsible"); //uncheck attribute responsible
        assertFalse(driver.isBoxCheckedById("responsible")); //responsible should be unchecked

        driver.checkBoxClickById("versatile"); //check attribute versatile
        assertTrue(driver.isBoxCheckedById("versatile")); //versatile should be checked
        driver.checkBoxClickById("versatile"); //uncheck attribute versatile
        assertFalse(driver.isBoxCheckedById("versatile")); //versatile should be unchecked
        */

        for (String attributeName: attributeNames) {
            //driver.checkBoxClickById(attributeName); //check attribute
            driver.getDriver().findElement(By.id(attributeName + "_box")).click();
            assertTrue(driver.isBoxCheckedById(attributeName + "_checkbox")); //quality should be checked
            driver.getDriver().findElement(By.id(attributeName + "_box")).click();
            //driver.checkBoxClickById(attributeName); //uncheck attribute
            assertFalse(driver.isBoxCheckedById(attributeName)); //quality should be unchecked
        }

        //check 6 qualities and make sure that all other qualities are disabled
        List<String> qualityList = new ArrayList<>(Arrays.asList("versatile", "responsible", "resourceful", "organized", "optimistic", "hardworking"));
        for (String quality: qualityList) {
            driver.getDriver().findElement(By.id(quality + "_box")).click();
            //driver.checkBoxClickById(quality); //check attribute versatile
        }
        List<WebElement> qualitiesElements = driver.getQualitiesElements();
        for (WebElement qualityCheckBox: qualitiesElements) {
            if (!qualityList.contains(qualityCheckBox.getAttribute("id"))) {
                assertTrue(driver.elementByIdHasAttribute(qualityCheckBox.getAttribute("id") + "_checkbox", "disabled")); //quality is disabled
            }
        }

        //uncheck one quality and make sure all other qualities are enabled
        driver.checkBoxClickById(qualityList.remove(0));
        for (WebElement qualityCheckBox: qualitiesElements) {
            if (!qualityList.contains(qualityCheckBox.getAttribute("id"))) {
                assertFalse(driver.elementByIdHasAttribute(qualityCheckBox.getAttribute("id") + "_checkbox", "disabled")); //quality is enabled
            }
        }

        /*
         * Slide 7 - Achievement & Awards
         */
        driver.clickButtonById("awards_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation
        assertTrue(driver.isActiveSlideById("slide7")); //current active slide should be #7

        driver.selectCalendarYearByName("awards[0][awardYear]", 2000); //select year 2000 for awards[0][awardYear]
        assertEquals("2000", driver.getFieldByName("awards[0][awardYear]")); //Award Year field should be 2000
        driver.typeFieldByName("awards[0][award]", "Golf gold\nSwimming Silver"); //type in field awards[0][award]
        assertEquals("Golf gold\nSwimming Silver", driver.getFieldByName("awards[0][award]")); //Award field should be Golf gold Swimming Silver

        assertEquals(0, Integer.parseInt(driver.getJsVar("achievementsTracker"))); //javascript achievementsTracker should be 0
        driver.clickButtonById("more_fields_achievements"); //click add more entry
        assertEquals(1, Integer.parseInt(driver.getJsVar("achievementsTracker"))); //javascript achievementsTracker should be 1
        assertEquals("", driver.getFieldByName("awards[1][awardYear]")); //new Award Year field should be empty
        assertEquals("", driver.getFieldByName("awards[1][award]")); //new Award field should be empty
        driver.clickButtonById("remove_fields_achievements"); //click remove last entry
        assertEquals(0, Integer.parseInt(driver.getJsVar("achievementsTracker"))); //javascript achievementsTracker should be 0

        driver.selectCalendarYearByName("extracur[0][extracurYear]", 1999); //select year 1999 for extracur[0][extracurYear]
        assertEquals("1999", driver.getFieldByName("extracur[0][extracurYear]")); //Extracurricular Year field should be 1999
        driver.typeFieldByName("extracur[0][activity]", "Hunting\nJavalin");
        assertEquals("Hunting\nJavalin", driver.getFieldByName("extracur[0][activity]")); //Extracurricular activity should be Hunting Javalin

        assertEquals(0, Integer.parseInt(driver.getJsVar("extracurricularTracker"))); //javascript extracurricularTracker should be 0
        driver.clickButtonById("more_fields_extracurricular"); //click add more entry
        assertEquals(1, Integer.parseInt(driver.getJsVar("extracurricularTracker"))); //javascript extracurricularTracker should be 1
        assertEquals("", driver.getFieldByName("extracur[1][extracurYear]")); //new Extracurricular Year field should be empty
        assertEquals("", driver.getFieldByName("extracur[1][activity]")); //new Extracurricular activity should be empty
        driver.clickButtonById("remove_fields_extracurricular"); //click remove last entry
        assertEquals(0, Integer.parseInt(driver.getJsVar("extracurricularTracker"))); //javascript extracurricularTracker should be 0

        /*
         * Slide 8 - Referees
         */
        driver.clickButtonById("referees_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation
        assertTrue(driver.isActiveSlideById("slide8")); //current active slide should be #8

        driver.typeFieldByName("referees[0][refName]", "Marry"); //type in field referees[0][refName]
        assertEquals("Marry", driver.getFieldByName("referees[0][refName]")); //Referee name field should be Marry
        driver.typeFieldByName("referees[0][refPosition]", "Boss"); //type in field referees[0][refPosition]
        assertEquals("Boss", driver.getFieldByName("referees[0][refPosition]")); //Referee Position field should be Boss
        driver.typeFieldByName("referees[0][refEmail]", "M@CIA.com"); //type in field referees[0][refEmail]
        assertEquals("M@CIA.com", driver.getFieldByName("referees[0][refEmail]")); //Referee Email field should be M@CIA.com
        driver.typeFieldByName("referees[0][refNum]", "09876544321"); //type in field referees[0][refNum]
        assertEquals("09876544321", driver.getFieldByName("referees[0][refNum]")); //Referee Phone Number field should be 09876544321

        assertEquals(0, Integer.parseInt(driver.getJsVar("refereesTracker"))); //javascript refereesTracker should be 0
        driver.clickButtonById("more_fields_referees"); //click add more referee
        assertEquals(1, Integer.parseInt(driver.getJsVar("refereesTracker"))); //javascript refereesTracker should be 1
        assertEquals("", driver.getFieldByName("referees[1][refName]")); //new Referee name field should be empty
        assertEquals("", driver.getFieldByName("referees[1][refPosition]")); //new Referee Position field should be empty
        assertEquals("", driver.getFieldByName("referees[1][refEmail]")); //new Referee Email field should be empty
        assertEquals("", driver.getFieldByName("referees[1][refNum]")); //new Referee Phone Number field should be empty
        driver.clickButtonById("remove_fields_referees"); //click remove last referee
        assertEquals(0, Integer.parseInt(driver.getJsVar("refereesTracker"))); //javascript refereesTracker should be 0

    }

    @Test
    public void integrationTestOnEditCVAllFieldsLoadCorrectly() {
        /*
         * This integration test will load an existing user "001" and check all loaded fields are correct
         * and test all button functionalities
         */

        driver.login(url, "001"); //log in as "001" and click Create CV button for empty fields

        driver.clickSubmitButtonByValue("Edit CV"); //click "Create CV" button
        driver.waitFor(waitTime); //wait 1 second for page to load

        /*
         * Slide 1 - Personal Details
         */
        assertTrue(driver.isActiveSlideById("slide1")); //start on slide 1 of carousel

        assertEquals("Jason", driver.getFieldByName("firstName")); //first name field should be Jason
        assertEquals("Bourne", driver.getFieldByName("lastName")); //last name field should be Bourne
        assertEquals("18", driver.getFieldByName("age")); //age field should be 18

        assertEquals("English", driver.getFieldByName("languages[0][language]")); //first language field should be English
        assertEquals("Spanish", driver.getFieldByName("languages[1][language]")); //first language field should be Spanish
        assertEquals("Maori", driver.getFieldByName("languages[2][language]")); //first language field should be Maori
        assertEquals(2, Integer.parseInt(driver.getJsVar("languageTracker"))); //javascript languageTracker should be 2
        driver.clickButtonById("addLanguageButton"); //press + button to add 1 more language field
        assertEquals(3, Integer.parseInt(driver.getJsVar("languageTracker"))); //javascript languageTracker should be 3
        driver.clickButtonById("removeLanguageButton"); //press - button to remove 1 language field
        assertEquals(2, Integer.parseInt(driver.getJsVar("languageTracker"))); //javascript languageTracker should be 2

        assertEquals("Wellington High School", driver.getFieldByName("school")); //school field should be empty
        assertEquals("Full License", driver.getFieldByName("license")); //License field has "Full license" selected
        for (int i = 0; i < licenseMenu.size(); i++) { //test if all the license drop down menus are correct
            String actualText = driver.getDropDownOptionsByName("license").get(i).getText();
            String expectedText = licenseMenu.get(i);
            if (!actualText.equals(expectedText)) {
                fail("License menu list test failed! Expected text (" + expectedText + ") does not match actual text (" + actualText + ")!");
                break;
            }
        }
        assertEquals("021 1116666", driver.getFieldByName("phoneNum")); //phone number field should be 021 1116666
        assertEquals("jason@wellhighschool.com", driver.getFieldByName("mail")); //email field should be jason@wellhighschool.com
        assertEquals("Te Aro", driver.getFieldByName("suburb")); //suburb field should be Te Aro
        assertEquals("Wellington", driver.getFieldByName("city")); //city field should be Wellington
        assertEquals("New Zealand", driver.getFieldById("country")); //Country field has "New Zealand" selected

        /*
         * Slide 2 - Personal Statement
         */
        driver.clickButtonById("statement_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation
        assertTrue(driver.isActiveSlideById("slide2")); //current active slide should be #2

        String loremIpsum = "Lorem ipsum dolor sit amet, an justo vidisse nostrud vis, ex tamquam iuvaret rationibus his. Cetero disputando adversarium cu his, eam harum " +
                "adolescens scriptorem et. At mei possim denique dissentiunt, eam veritus copiosae pertinax an. Integre eripuit minimum mel id, cu iusto liberavisse ullamcorper has. Sed cu clita diceret.";

        assertEquals(loremIpsum, driver.getFieldByName("statement")); //personal statement field should be loremIpsum

        /*
         * Slide 3 - Employment
         */
        driver.clickButtonById("employment_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation
        assertTrue(driver.isActiveSlideById("slide3")); //current active slide should be #3

        assertEquals("The Warehouse", driver.getFieldByName("employment[0][jobCompany]")); //Company field should be The Warehouse
        assertEquals("January 2013", driver.getFieldByName("employment[0][startDate]")); //Start Date field should be January 2015
        assertEquals("March 2015", driver.getFieldByName("employment[0][endDate]")); //End Date field should be March 2013
        assertEquals("Checkout", driver.getFieldByName("employment[0][title]")); //Job Title field should be Checkout
        assertEquals("Working in checkout\nStock take\nClosing shop", driver.getFieldByName("employment[0][jobDescription]")); //Job Description field should be Working in checkoutStock takeClosing shop

        assertEquals("Burger King", driver.getFieldByName("employment[1][jobCompany]")); //Company field should be Burger King
        assertEquals("February 2010", driver.getFieldByName("employment[1][startDate]")); //Start Date field should be February 2010
        assertEquals("May 2012", driver.getFieldByName("employment[1][endDate]")); //End Date field should be May 2012
        assertEquals("Crew Member", driver.getFieldByName("employment[1][title]")); //Job Title field should be Crew Member
        assertEquals("Making burgers\nCashier", driver.getFieldByName("employment[1][jobDescription]")); //Job Description field should be Making burgersCashier

        assertEquals(1, Integer.parseInt(driver.getJsVar("employmentTracker"))); //javascript employmentTracker should be 1
        driver.clickButtonById("more_fields_employment"); //click add more entry
        assertEquals(2, Integer.parseInt(driver.getJsVar("employmentTracker"))); //javascript employmentTracker should be 2
        assertEquals("", driver.getFieldByName("employment[2][jobCompany]")); //New Company field should be Disney Land
        assertEquals("", driver.getFieldByName("employment[2][startDate]")); //New Start Date field should be empty
        assertEquals("", driver.getFieldByName("employment[2][endDate]")); //New End Date field should be empty
        assertEquals("", driver.getFieldByName("employment[2][title]")); //New Job Title field should be empty
        assertEquals("", driver.getFieldByName("employment[2][jobDescription]")); //Job Description field should be empty
        driver.clickButtonById("remove_fields_employment"); //click remove last entry
        assertEquals(1, Integer.parseInt(driver.getJsVar("employmentTracker"))); //javascript employmentTracker should be 1

        /*
         * Slide 4 - Volunteer Work
         */
        driver.clickButtonById("volunteer_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation
        assertTrue(driver.isActiveSlideById("slide4")); //current active slide should be #4

        assertEquals("Homeless Shelter", driver.getFieldByName("volunteering[0][volCompany]")); //Volunteer Company field should be empty
        assertEquals("December 2016", driver.getFieldByName("volunteering[0][volStartDate]")); //Start Date field should be December 2016
        assertEquals("", driver.getFieldByName("volunteering[0][volEndDate]")); //End Date field should be empty
        assertEquals("Volunteer", driver.getFieldByName("volunteering[0][volTitle]")); //Volunteer Title field should be Volunteer
        assertEquals("Serving food to the homeless", driver.getFieldByName("volunteering[0][volDescription]")); //Volunteer Description field should be Serving food to the homeless

        assertEquals("SPCA", driver.getFieldByName("volunteering[1][volCompany]")); //Volunteer Company field should be SPCA
        assertEquals("December 2012", driver.getFieldByName("volunteering[1][volStartDate]")); //Start Date field should be December 2012
        assertEquals("November 2015", driver.getFieldByName("volunteering[1][volEndDate]")); //End Date field should be November 2015
        assertEquals("Animal Caretaker", driver.getFieldByName("volunteering[1][volTitle]")); //Volunteer Title field should be Animal Caretaker
        assertEquals("Giving dogs hugs", driver.getFieldByName("volunteering[1][volDescription]")); //Volunteer Description field should be Giving dogs hugs

        assertEquals(1, Integer.parseInt(driver.getJsVar("volunteerTracker"))); //javascript volunteerTracker should be 1
        driver.clickButtonById("more_fields_volunteer"); //click add more entry
        assertEquals("", driver.getFieldByName("volunteering[2][volCompany]")); //Volunteer Company field should be empty
        assertEquals("", driver.getFieldByName("volunteering[2][volStartDate]")); //Start Date field should be empty
        assertEquals("", driver.getFieldByName("volunteering[2][volEndDate]")); //End Date field should be empty
        //assertTrue(driver.elementByNameHasAttribute("volunteer[2][endDate]", "disabled"); //End Date should be disabled because there should be no start date entered
        assertEquals("", driver.getFieldByName("volunteering[2][volTitle]")); //Volunteer Title field should be empty
        assertEquals("", driver.getFieldByName("volunteering[2][volDescription]")); //Volunteer Description field should be empty
        driver.clickButtonById("remove_fields_volunteer"); //click remove last entry
        assertEquals(1, Integer.parseInt(driver.getJsVar("volunteerTracker"))); //javascript volunteerTracker should be 1

        /*
         * Slide 5 - Education
         */
        driver.clickButtonById("education_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation
        assertTrue(driver.isActiveSlideById("slide5")); //current active slide should be #5

        assertEquals("2017", driver.getFieldByName("education[0][schoolYear]")); //School year field should be 2017
        assertEquals("Currently studying NCEA Level 3", driver.getFieldByName("education[0][schoolYearGrade]")); //School year grade has "Currently studying NCEA Level 3" selected
        for (int i = 0; i < gradeMenu.size(); i++) { //test if all the school year grade drop down menus are correct
            String actualText = driver.getDropDownOptionsByName("education[0][schoolYearGrade]").get(i).getText();
            String expectedText = gradeMenu.get(i);
            if (!actualText.equals(expectedText)) {
                fail("schoolYearGrade menu list test failed! Expected text (" + expectedText + ") does not match actual text (" + actualText + ")!");
                break;
            }
        }
        assertEquals("English", driver.getFieldByName("education[0][subjects][0][subject]")); //each subject class should be English
        assertEquals("Currently studying NCEA Level 3", driver.getFieldByName("education[0][subjects][0][grade]")); //each subject grade should be Currently studying NCEA Level 3
        assertEquals("Calculus", driver.getFieldByName("education[0][subjects][1][subject]")); //each subject class should be Calculus
        assertEquals("Currently studying NCEA Level 3", driver.getFieldByName("education[0][subjects][1][grade]")); //each subject grade should be Currently studying NCEA Level 3
        assertEquals("Physics", driver.getFieldByName("education[0][subjects][2][subject]")); //each subject class should be Physics
        assertEquals("Currently studying NCEA Level 3", driver.getFieldByName("education[0][subjects][2][grade]")); //each subject grade should be Currently studying NCEA Level 3
        assertEquals("Computer Science", driver.getFieldByName("education[0][subjects][3][subject]")); //each subject class should be Computer Science
        assertEquals("Currently studying NCEA Level 3", driver.getFieldByName("education[0][subjects][3][grade]")); //each subject grade should be Currently studying NCEA Level 3
        assertEquals("Chemistry", driver.getFieldByName("education[0][subjects][4][subject]")); //each subject class should be Chemistry
        assertEquals("Currently studying NCEA Level 3", driver.getFieldByName("education[0][subjects][4][grade]")); //each subject grade should be Currently studying NCEA Level 3

        assertEquals(4, Integer.parseInt(driver.getJsVar("subjectsTracker[0]"))); //javascript subjectsTracker[0] should be 4
        driver.clickButtonById("addSubjectButton_0"); //click + to add a new subject line to education_0
        assertEquals(5, Integer.parseInt(driver.getJsVar("subjectsTracker[0]"))); //javascript subjectsTracker[0] should be 5
        assertEquals("", driver.getFieldByName("education[0][subjects][5][subject]")); //the new subject should be empty
        assertEquals("", driver.getFieldByName("education[0][subjects][5][grade]")); //the new subject should be empty
        driver.clickButtonById("removeSubjectButton_0"); //click + to add a new subject line to education_0
        assertEquals(4, Integer.parseInt(driver.getJsVar("subjectsTracker[0]"))); //javascript subjectsTracker[0] should be 4

        assertEquals("2016", driver.getFieldByName("education[1][schoolYear]")); //School year field should be 2016
        assertEquals("NCEA Level 2 endorsed with Excellence", driver.getFieldByName("education[1][schoolYearGrade]")); //School year grade has "NCEA Level 2 endorsed with Excellence" selected

        assertEquals("Science", driver.getFieldByName("education[1][subjects][0][subject]")); //each subject class should be empty
        assertEquals("NCEA Level 2 endorsed with Excellence", driver.getFieldByName("education[1][subjects][0][grade]")); //each subject grade should be NCEA Level 2 endorsed with Excellence
        assertEquals("Maths", driver.getFieldByName("education[1][subjects][1][subject]")); //each subject class should be empty
        assertEquals("NCEA Level 2 endorsed with Excellence", driver.getFieldByName("education[1][subjects][1][grade]")); //each subject grade should be NCEA Level 2 endorsed with Excellence
        assertEquals("Music", driver.getFieldByName("education[1][subjects][2][subject]")); //each subject class should be empty
        assertEquals("NCEA Level 2 endorsed with Merit", driver.getFieldByName("education[1][subjects][2][grade]")); //each subject grade should be NCEA Level 2 endorsed with Merit
        assertEquals("English", driver.getFieldByName("education[1][subjects][3][subject]")); //each subject class should be empty
        assertEquals("NCEA Level 2 endorsed with Merit", driver.getFieldByName("education[1][subjects][3][grade]")); //each subject grade should be NCEA Level 2 endorsed with Merit
        assertEquals("Social Studies", driver.getFieldByName("education[1][subjects][4][subject]")); //each subject class should be empty
        assertEquals("NCEA Level 2", driver.getFieldByName("education[1][subjects][4][grade]")); //each subject grade should be NCEA Level 2

        assertEquals(4, Integer.parseInt(driver.getJsVar("subjectsTracker[1]"))); //javascript subjectsTracker[1] should be 4
        driver.clickButtonById("addSubjectButton_1"); //click + to add a new subject line to education_1
        assertEquals(5, Integer.parseInt(driver.getJsVar("subjectsTracker[1]"))); //javascript subjectsTracker[1] should be 5
        assertEquals("", driver.getFieldByName("education[1][subjects][5][subject]")); //the new subject should be empty
        assertEquals("", driver.getFieldByName("education[1][subjects][5][grade]")); //the new subject should be empty
        driver.clickButtonById("removeSubjectButton_1"); //click + to add a new subject line to education_1
        assertEquals(4, Integer.parseInt(driver.getJsVar("subjectsTracker[1]"))); //javascript subjectsTracker[1] should be 4

        assertEquals("2015", driver.getFieldByName("education[2][schoolYear]")); //School year field should be 2015
        assertEquals("NCEA Level 1", driver.getFieldByName("education[2][schoolYearGrade]")); //School year grade has "" NCEA Level 1

        assertEquals("Science", driver.getFieldByName("education[2][subjects][0][subject]")); //each subject class should be empty
        assertEquals("NCEA Level 1 endorsed with Excellence", driver.getFieldByName("education[2][subjects][0][grade]")); //each subject grade should be NCEA Level 1 endorsed with Excellence
        assertEquals("Maths", driver.getFieldByName("education[2][subjects][1][subject]")); //each subject class should be empty
        assertEquals("NCEA Level 1 endorsed with Merit", driver.getFieldByName("education[2][subjects][1][grade]")); //each subject grade should be NCEA Level 1 endorsed with Merit
        assertEquals("English", driver.getFieldByName("education[2][subjects][2][subject]")); //each subject class should be empty
        assertEquals("NCEA Level 1", driver.getFieldByName("education[2][subjects][2][grade]")); //each subject grade should be NCEA Level 1
        assertEquals("Japanese", driver.getFieldByName("education[2][subjects][3][subject]")); //each subject class should be empty
        assertEquals("NCEA Level 1", driver.getFieldByName("education[2][subjects][3][grade]")); //each subject grade should be NCEA Level 1
        assertEquals("French", driver.getFieldByName("education[2][subjects][4][subject]")); //each subject class should be empty
        assertEquals("NCEA Level 1", driver.getFieldByName("education[2][subjects][4][grade]")); //each subject grade should be NCEA Level 1

        assertEquals(4, Integer.parseInt(driver.getJsVar("subjectsTracker[2]"))); //javascript subjectsTracker[2] should be 4
        driver.clickButtonById("addSubjectButton_2"); //click + to add a new subject line to education_2
        assertEquals(5, Integer.parseInt(driver.getJsVar("subjectsTracker[2]"))); //javascript subjectsTracker[2] should be 5
        assertEquals("", driver.getFieldByName("education[2][subjects][5][subject]")); //the new subject should be empty
        assertEquals("", driver.getFieldByName("education[2][subjects][5][grade]")); //the new subject should be empty
        driver.clickButtonById("removeSubjectButton_2"); //click + to add a new subject line to education_2
        assertEquals(4, Integer.parseInt(driver.getJsVar("subjectsTracker[2]"))); //javascript subjectsTracker[2] should be 4

        assertEquals(2, Integer.parseInt(driver.getJsVar("educationTracker"))); //javascript educationTracker should be 2
        driver.clickButtonById("more_fields_education"); //click add more education button
        assertEquals(3, Integer.parseInt(driver.getJsVar("educationTracker"))); //javascript educationTracker should be 3

        assertEquals("", driver.getFieldByName("education[3][schoolYear]")); //School year field should be empty
        assertEquals("", driver.getFieldByName("education[3][schoolYearGrade]")); //School year grade has "" selected

        for (int i = 0; i < gradeMenu.size(); i++) { //test if all the school year grade drop down menus are correct
            String actualText = driver.getDropDownOptionsByName("education[3][schoolYearGrade]").get(i).getText();
            String expectedText = gradeMenu.get(i);
            if (!actualText.equals(expectedText)) {
                fail("schoolYearGrade menu list test failed! Expected text (" + expectedText + ") does not match actual text (" + actualText + ")!");
                break;
            }
        }
        for (int i = 0; i < 5; i++) { //test all 5 subject lines
            assertEquals("", driver.getFieldByName("education[3][subjects][" + i + "][subject]")); //each subject class should be empty
            for (int j = 0; j < gradeMenu.size(); j++) { //test all grade drop down text are correct for each subject line
                String actualText = driver.getDropDownOptionsByName("education[3][subjects][" + i + "][grade]").get(i).getText();
                String expectedText = gradeMenu.get(i);
                if (!actualText.equals(expectedText)) {
                    fail("education[3][subjects][" + i + "][grade] menu list test failed! Expected text (" + expectedText + ") does not match actual text (" + actualText + ")!");
                    break;
                }
            }
        }
        assertEquals(4, Integer.parseInt(driver.getJsVar("subjectsTracker[3]"))); //javascript subjectsTracker[1] should be 4
        driver.clickButtonById("addSubjectButton_3"); //click + to add a new subject line to education_0
        assertEquals(5, Integer.parseInt(driver.getJsVar("subjectsTracker[3]"))); //javascript subjectsTracker[1] should be 5
        assertEquals("", driver.getFieldByName("education[3][subjects][5][subject]")); //the new subject should be empty
        assertEquals("", driver.getFieldByName("education[3][subjects][5][grade]")); //the new subject should be empty
        driver.clickButtonById("removeSubjectButton_3"); //click + to add a new subject line to education_1
        assertEquals(4, Integer.parseInt(driver.getJsVar("subjectsTracker[3]"))); //javascript subjectsTracker[1] should be 4

        driver.clickButtonById("remove_fields_education"); //click remove last entry button
        assertEquals(2, Integer.parseInt(driver.getJsVar("educationTracker"))); //javascript educationTracker should be 2

        /*
         * Slide 6 - Personal Attributes
         */
        driver.clickButtonById("attributes_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation
        assertTrue(driver.isActiveSlideById("slide6")); //current active slide should be #6

        List<String> qualityList = new ArrayList<>(Arrays.asList("confident", "flexible", "broad-minded", "analytical", "determined", "hardworking"));

        //uncheck one quality and make sure all other qualities are enabled
        List<WebElement> qualitiesElements = driver.getQualitiesElements();
        for (WebElement qualityCheckBox: qualitiesElements) {
            String id = qualityCheckBox.getAttribute("id");
            System.out.printf("id: " + id);
            if (qualityList.contains(id.replace("_checkbox", ""))) {
                System.out.println(" - checked");
                assertTrue(driver.isBoxCheckedById(id)); //quality is checked
            } else {
                System.out.println(" - unchecked");
                assertFalse(driver.isBoxCheckedById(id)); //quality is unchecked
                assertTrue(driver.elementByIdHasAttribute(id, "disabled")); //unchecked quality should be disabled
            }
        }

        driver.checkBoxClickById(qualityList.remove(3)); //uncheck 4th quality and check all other checkboxes are enabled
        for (WebElement qualityCheckBox: qualitiesElements) {
            String id = qualityCheckBox.getAttribute("id");
            System.out.printf("id: " + id);
            if (qualityList.contains(id)) {
                assertTrue(driver.isBoxCheckedById(id)); //quality is checked
            } else {
                assertFalse(driver.isBoxCheckedById(id)); //quality is unchecked
                assertFalse(driver.elementByIdHasAttribute(id, "disabled")); //unchecked quality should be disabled
            }
        }

        /*
         * Slide 7 - Achievement & Awards
         */
        driver.clickButtonById("awards_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation
        assertTrue(driver.isActiveSlideById("slide7")); //current active slide should be #7

        assertEquals("2015", driver.getFieldByName("awards[0][awardYear]")); //Award Year field should be 2015
        assertEquals("Regional chess winner\nDistinction in science for year 11", driver.getFieldByName("awards[0][award]")); //Award field should be Regional chess winnerDistinction in science for year 11

        assertEquals("2016", driver.getFieldByName("awards[1][awardYear]")); //Award Year field should be 2016
        assertEquals("National young musical award", driver.getFieldByName("awards[1][award]")); //Award field should be National young musical award

        assertEquals(1, Integer.parseInt(driver.getJsVar("achievementsTracker"))); //javascript achievementsTracker should be 1
        driver.clickButtonById("more_fields_achievements"); //click add more entry
        assertEquals(2, Integer.parseInt(driver.getJsVar("achievementsTracker"))); //javascript achievementsTracker should be 2
        assertEquals("", driver.getFieldByName("awards[2][awardYear]")); //new Award Year field should be empty
        assertEquals("", driver.getFieldByName("awards[2][award]")); //new Award field should be empty
        driver.clickButtonById("remove_fields_achievements"); //click remove last entry
        assertEquals(1, Integer.parseInt(driver.getJsVar("achievementsTracker"))); //javascript achievementsTracker should be 1

        assertEquals("2016", driver.getFieldByName("extracur[0][extracurYear]")); //Extracurricular Year field should be 2016
        assertEquals("Wellington hack-a-thon\nRock climbing", driver.getFieldByName("extracur[0][activity]")); //Extracurricular activity should be Wellington hack-a-thonRock climbing

        assertEquals("2017", driver.getFieldByName("extracur[1][extracurYear]")); //Extracurricular Year field should be 2017
        assertEquals("Rock climbing\nHouse DJ", driver.getFieldByName("extracur[1][activity]")); //Extracurricular activity should be Rock climbingHouse DJ

        assertEquals(1, Integer.parseInt(driver.getJsVar("extracurricularTracker"))); //javascript extracurricularTracker should be 1
        driver.clickButtonById("more_fields_extracurricular"); //click add more entry
        assertEquals(2, Integer.parseInt(driver.getJsVar("extracurricularTracker"))); //javascript extracurricularTracker should be 2
        assertEquals("", driver.getFieldByName("extracur[2][extracurYear]")); //new Extracurricular Year field should be empty
        assertEquals("", driver.getFieldByName("extracur[2][activity]")); //new Extracurricular activity should be empty
        driver.clickButtonById("remove_fields_extracurricular"); //click remove last entry
        assertEquals(1, Integer.parseInt(driver.getJsVar("extracurricularTracker"))); //javascript extracurricularTracker should be 1

        /*
         * Slide 8 - Referees
         */
        driver.clickButtonById("referees_button"); //click next button
        driver.waitFor(waitTime); //wait 1 second for slide animation
        assertTrue(driver.isActiveSlideById("slide8")); //current active slide should be #8

        assertEquals("Mrs Marry Dell", driver.getFieldByName("referees[0][refName]")); //Referee name field should be Mrs. Marry Dell
        assertEquals("Science Teacher", driver.getFieldByName("referees[0][refPosition]")); //Referee Position field should be Science Teacher
        assertEquals("marry.dell@whs.com", driver.getFieldByName("referees[0][refEmail]")); //Referee Email field should be marry.dell@whs.com
        assertEquals("09876544321", driver.getFieldByName("referees[0][refNum]")); //Referee Phone Number field should be 09876544321

        assertEquals("Bill Gates", driver.getFieldByName("referees[1][refName]")); //Referee name field should be Bill Gates
        assertEquals("Microsoft CEO", driver.getFieldByName("referees[1][refPosition]")); //Referee Position field should be Microsoft CEO
        assertEquals("bill@ms.com", driver.getFieldByName("referees[1][refEmail]")); //Referee Email field should be bill@ms.com
        assertEquals("+11 4884700273", driver.getFieldByName("referees[1][refNum]")); //Referee Phone Number field should be +11 4884700273

        assertEquals("John Key", driver.getFieldByName("referees[2][refName]")); //Referee name field should be empty
        assertEquals("Ex-NZ Prime Minister", driver.getFieldByName("referees[2][refPosition]")); //Referee Position field should be Ex-NZ Prime Minister
        assertEquals("jkey@nz.govt.nz", driver.getFieldByName("referees[2][refEmail]")); //Referee Email field should be jkey@nz.govt.nz
        assertEquals("021 7640263047", driver.getFieldByName("referees[2][refNum]")); //Referee Phone Number field should be 021 7640263047

        assertEquals(2, Integer.parseInt(driver.getJsVar("refereesTracker"))); //javascript refereesTracker should be 2
        driver.clickButtonById("more_fields_referees"); //click add more referee
        assertEquals(3, Integer.parseInt(driver.getJsVar("refereesTracker"))); //javascript refereesTracker should be 3
        assertEquals("", driver.getFieldByName("referees[3][refName]")); //new Referee name field should be empty
        assertEquals("", driver.getFieldByName("referees[3][refPosition]")); //new Referee Position field should be empty
        assertEquals("", driver.getFieldByName("referees[3][refEmail]")); //new Referee Email field should be empty
        assertEquals("", driver.getFieldByName("referees[3][refNum]")); //new Referee Phone Number field should be empty
        driver.clickButtonById("remove_fields_referees"); //click remove last referee
        assertEquals(2, Integer.parseInt(driver.getJsVar("refereesTracker"))); //javascript refereesTracker should be 2
    }

    @After
    public void tearDown() throws Exception {
        driver.closeDriver();
    }
}
