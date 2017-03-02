package testClasses;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by josh on 17/01/17.
 */
public abstract class SeleniumTestMethods {

    WebDriver driver;
    JavascriptExecutor js;

    public void login(String url, String testId) {
        //Login using testId and click "Create CV" to start an empty CV form
        driver.navigate().to(url);
        typeFieldById("inputid", testId);
        clickSubmitButtonByValue("Test");
        waitFor(1000);
    }

    public void typeFieldByName(String name, String text) {
        //find element by name and input string, returns the value in the element
        WebElement field = driver.findElement(By.name(name));
        field.sendKeys(text);
    }

    public void typeFieldById(String id, String text) {
        //find element by id and input string, returns the value in the element
        WebElement field = driver.findElement(By.id(id));
        field.sendKeys(text);
    }

    public void selectFieldByName(String name, String value) {
        //find element by name and input string, returns the value in the element
        Select selectField = new Select(driver.findElement(By.name(name)));
        selectField.selectByVisibleText(value);
    }

    public void selectCalendarMonthYearByName(String name, String month, int year) {
        //click through the bootstrap calendar (year and month) for a given field
        selectCalendarYearByName(name, year);
        WebElement calendarMonthsWidget = driver.findElement(By.xpath("//div[@class='datepicker-months']"));
        List<WebElement> monthsColumns = calendarMonthsWidget.findElements(By.tagName("td"));
        for (WebElement cell: monthsColumns) {
            List<WebElement> spanTags = cell.findElements(By.tagName("span"));
            for (WebElement span: spanTags) {
                if (span.getText().equals(month)) {
                    span.click();
                    break;
                }
            }
        }
    }

    public void selectCalendarYearByName(String name, int year) {
        //click through the bootstrap calendar (year) for a given field
        WebElement element = driver.findElement(By.name(name));
        element.click();
        WebElement calendarYearWidget = driver.findElement(By.xpath("//div[@class='datepicker-years']"));
        List<WebElement> yearColumns = calendarYearWidget.findElements(By.tagName("td"));
        for (WebElement cell: yearColumns) {
            List<WebElement> spanTags = cell.findElements(By.tagName("span"));
            int i = 0;
            while (i < spanTags.size()) {
                int tagYear = Integer.parseInt(spanTags.get(i).getText());
                if (year < tagYear) {
                    List<WebElement> prevAll = driver.findElements(By.xpath("//*[@class='prev' and @style='visibility: visible;']"));
                    for (WebElement prev: prevAll) {
                        try {
                            prev.click();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                    i = 0;
                    spanTags = cell.findElements(By.tagName("span"));
                } else if (tagYear == year) {
                    spanTags.get(i).click();
                    break;
                } else if (i == spanTags.size() - 1 && year > tagYear) {
                    driver.findElement(By.xpath("//th[@class='next']")).click();
                    i = 0;
                } else {
                    i++;
                }
            }
        }
    }

    public void clickButtonById(String id) {
        //click button by its id
        driver.findElement(By.id(id)).click();
    }

    public void clickSubmitButtonByValue(String value) {
        //click button by its value
        driver.findElement(By.xpath("//input[@type='submit' and @value='" + value + "']")).click();
    }

    public Boolean isActiveSlideById(String id) {
        //function that will check if the give id (eg. "slide2") is the current active slide in the carousel
        WebElement activeDiv = driver.findElement(By.xpath("//div[@class='item active']"));
        List<WebElement> childDivs = activeDiv.findElements(By.xpath("div"));
        if (childDivs.contains(driver.findElement(By.id(id)))) {
            return true;
        }
        return false;
    }

    public String getFieldByName(String name) {
        //returns the value in the field find by element name
        return driver.findElement(By.name(name)).getAttribute("value");
    }

    public String getFieldById(String id) {
        //returns the value in the field find by element name
        return driver.findElement(By.name(id)).getAttribute("value");
    }

    public void checkBoxClickById(String id) {
        //clicks on the check box given the id
        WebElement checkBox = driver.findElement(By.id(id));
        checkBox.click();
    }

    public Boolean isBoxCheckedById(String id) {
        //returns if the given check box has been selected
        WebElement checkBox = driver.findElement(By.id(id));
        return checkBox.isSelected();
    }

    public Boolean elementByNameHasAttribute(String elementName, String attribute) {
        //returns if the given element has a specific attribute tag
        String value = driver.findElement(By.name(elementName)).getAttribute(attribute);
        return (value != null);
    }

    public Boolean elementByIdHasAttribute(String id, String attribute) {
        //returns if the given element has a specific attribute tag
        String value = driver.findElement(By.id(id)).getAttribute(attribute);
        return (value != null);
    }

    public Boolean elementByValueHasAttribute(String elementValue, String attribute) {
        //returns if the given element has a specific attribute tag
        String value = driver.findElement(By.xpath("//input[@value='" + elementValue + "']")).getAttribute(attribute);
        return (value != null);
    }

    public String getElementAttributeValueByName(String elementName, String attribute) {
        //This method returns a the attribute value (eg. placeholder) find by the element's name
        return driver.findElement(By.name(elementName)).getAttribute(attribute);
    }

    public String getElementAttributeValueById(String id, String attribute) {
        //This method returns a the attribute value (eg. placeholder) find by the element's name
        return driver.findElement(By.id(id)).getAttribute(attribute);
    }

    public List<WebElement> getDropDownOptionsByName(String name) {
        //returns the list for a given drop down menu
        Select dropDownMenu = new Select(driver.findElement(By.name(name)));
        return dropDownMenu.getOptions();
    }

    public String getJsVar(String varName) {
        //returns the javascript variable as a String
        return js.executeScript("return " + varName + ";").toString();
    }

    public List<WebElement> getQualitiesElements() {
        return driver.findElements(By.xpath("//input[@class='checkboxOption']"));
    }

    public void waitFor(long milliSeconds) {
        //tell the driver to explicitly wait a given time in milliseconds
        try {
            synchronized (driver) {
                driver.wait(milliSeconds);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    public JavascriptExecutor getJs() {
        return js;
    }

    public void closeDriver() {
        driver.quit();
    }

    public void runExample(String url, String testId) {
        /*
         * This method will run through the whole app entering in an example user.
         */
        long longWaitTime = 1000;
        long shortWaitTime = 350;

        login(url, testId); //log in as testId and click Create CV button for empty fields

        clickSubmitButtonByValue("Create CV"); //click "Create CV" button
        waitFor(longWaitTime); //wait 1 second for page to load

        /*
         * Slide 1 - Personal Details
         */
        typeFieldByName("firstName", "Jason"); //type in field first name
        waitFor(shortWaitTime);
        typeFieldByName("lastName", "Bourne"); //type in field last name
        waitFor(shortWaitTime);
        typeFieldByName("age", "18"); //type in field age
        waitFor(shortWaitTime);
        clickButtonById("slide1");
        waitFor(shortWaitTime);
        clickButtonById("addLanguageButton"); //press + button to add 1 more language field
        waitFor(shortWaitTime);
        clickButtonById("addLanguageButton"); //press + button to add 1 more language field
        waitFor(shortWaitTime);
        typeFieldByName("languages[0][language]", "English"); //type in field language
        waitFor(shortWaitTime);
        typeFieldByName("languages[1][language]", "Spanish"); //type in field languages
        waitFor(shortWaitTime);
        typeFieldByName("languages[2][language]", "Maori"); //type in field languages
        waitFor(shortWaitTime);

        typeFieldByName("school", "Wellington High School"); //type in field school
        waitFor(shortWaitTime);
        selectFieldByName("license", "Full License"); //select "Full License" for license
        waitFor(shortWaitTime);
        typeFieldByName("phoneNum", "021 1116666"); //type in field phoneNum
        waitFor(shortWaitTime);
        typeFieldByName("mail", "jason@wellhighschool.com"); //type in field email
        waitFor(shortWaitTime);
        typeFieldByName("suburb", "Te Aro"); //type in field suburb
        waitFor(shortWaitTime);
        typeFieldByName("city", "Wellington"); //type in field city
        waitFor(shortWaitTime);

        /*
         * Slide 2 - Personal Statement
         */
        clickButtonById("statement_button"); //click next button
        waitFor(longWaitTime); //wait 1 second for slide animation
        String loremIpsum = "Lorem ipsum dolor sit amet, an justo vidisse nostrud vis, ex tamquam iuvaret rationibus his. Cetero disputando adversarium cu his, eam harum " +
                "adolescens scriptorem et. At mei possim denique dissentiunt, eam veritus copiosae pertinax an. Integre eripuit minimum mel id, cu iusto liberavisse ullamcorper has. Sed cu clita diceret.";
        typeFieldByName("statement", loremIpsum); //type in field statement
        waitFor(shortWaitTime);

        /*
         * Slide 3 - Employment
         */
        clickButtonById("employment_button"); //click next button
        waitFor(longWaitTime); //wait 1 second for slide animation

        typeFieldByName("employment[0][jobCompany]", "The Warehouse"); //type in field employment[0][jobCompany]
        waitFor(shortWaitTime);
        selectCalendarMonthYearByName("employment[0][startDate]", "Jan", 2013); //Using date picker for start date
        waitFor(shortWaitTime);
        selectCalendarMonthYearByName("employment[0][endDate]", "Mar", 2015); //Using date picker for end date
        waitFor(shortWaitTime);
        typeFieldByName("employment[0][title]", "Checkout"); //type in field employment[0][title]
        waitFor(shortWaitTime);
        typeFieldByName("employment[0][jobDescription]", "Working in checkout\nStock take\nClosing shop"); //type in field employment[0][jobDescription]
        waitFor(shortWaitTime);

        clickButtonById("more_fields_employment"); //click add more entry

        typeFieldByName("employment[1][jobCompany]", "Burger King"); //type in field employment[0][jobCompany]
        waitFor(shortWaitTime);
        selectCalendarMonthYearByName("employment[1][startDate]", "Feb", 2010); //Using date picker for start date
        waitFor(shortWaitTime);
        selectCalendarMonthYearByName("employment[1][endDate]", "May", 2012); //Using date picker for end date
        waitFor(shortWaitTime);
        typeFieldByName("employment[1][title]", "Crew Member"); //type in field employment[0][title]
        waitFor(shortWaitTime);
        typeFieldByName("employment[1][jobDescription]", "Making burgers\nCashier"); //type in field employment[0][jobDescription]
        waitFor(shortWaitTime);

        /*
         * Slide 4 - Volunteer Work
         */
        clickButtonById("volunteer_button"); //click next button
        waitFor(longWaitTime); //wait 1 second for slide animation

        typeFieldByName("volunteering[0][volCompany]", "Homeless Shelter"); //type in field volunteering[0][volCompany]
        waitFor(shortWaitTime);
        selectCalendarMonthYearByName("volunteering[0][volStartDate]", "Dec", 2016); //Using date picker for start date
        waitFor(shortWaitTime);
        typeFieldByName("volunteering[0][volTitle]", "Volunteer"); //type in field volunteering[0][volTitle]
        waitFor(shortWaitTime);
        typeFieldByName("volunteering[0][volDescription]", "Serving food to the homeless"); //type in field volunteering[0][volDescription]
        waitFor(shortWaitTime);

        clickButtonById("more_fields_volunteer"); //click add more entry

        typeFieldByName("volunteering[1][volCompany]", "SPCA"); //type in field volunteering[0][volCompany]
        waitFor(shortWaitTime);
        selectCalendarMonthYearByName("volunteering[1][volStartDate]", "Dec", 2012); //Using date picker for start date
        waitFor(shortWaitTime);
        selectCalendarMonthYearByName("volunteering[1][volEndDate]", "Nov", 2015); //Using date picker for start date
        waitFor(shortWaitTime);
        typeFieldByName("volunteering[1][volTitle]", "Animal Caretaker"); //type in field volunteering[0][volTitle]
        waitFor(shortWaitTime);
        typeFieldByName("volunteering[1][volDescription]", "Giving dogs hugs"); //type in field volunteering[0][volDescription]
        waitFor(shortWaitTime);

        /*
         * Slide 5 - Education
         */
        clickButtonById("education_button"); //click next button
        waitFor(longWaitTime); //wait 1 second for slide animation

        selectCalendarYearByName("education[0][schoolYear]", 2017); //Using date picker for school year
        selectFieldByName("education[0][schoolYearGrade]", "Currently studying NCEA Level 3");

        typeFieldByName("education[0][subjects][0][subject]", "English");
        selectFieldByName("education[0][subjects][0][grade]", "Currently studying NCEA Level 3");
        typeFieldByName("education[0][subjects][1][subject]", "Calculus");
        selectFieldByName("education[0][subjects][1][grade]", "Currently studying NCEA Level 3");
        typeFieldByName("education[0][subjects][2][subject]", "Physics");
        selectFieldByName("education[0][subjects][2][grade]", "Currently studying NCEA Level 3");
        typeFieldByName("education[0][subjects][3][subject]", "Computer Science");
        selectFieldByName("education[0][subjects][3][grade]", "Currently studying NCEA Level 3");
        typeFieldByName("education[0][subjects][4][subject]", "Chemistry");
        selectFieldByName("education[0][subjects][4][grade]", "Currently studying NCEA Level 3");
        waitFor(shortWaitTime);

        clickButtonById("more_fields_education"); //click add more education button

        selectCalendarYearByName("education[1][schoolYear]", 2016); //Using date picker for school year
        selectFieldByName("education[1][schoolYearGrade]", "NCEA Level 2 endorsed with Excellence");

        typeFieldByName("education[1][subjects][0][subject]", "Science");
        selectFieldByName("education[1][subjects][0][grade]", "NCEA Level 2 endorsed with Excellence");
        typeFieldByName("education[1][subjects][1][subject]", "Maths");
        selectFieldByName("education[1][subjects][1][grade]", "NCEA Level 2 endorsed with Excellence");
        typeFieldByName("education[1][subjects][2][subject]", "Music");
        selectFieldByName("education[1][subjects][2][grade]", "NCEA Level 2 endorsed with Merit");
        typeFieldByName("education[1][subjects][3][subject]", "English");
        selectFieldByName("education[1][subjects][3][grade]", "NCEA Level 2 endorsed with Merit");
        typeFieldByName("education[1][subjects][4][subject]", "Social Studies");
        selectFieldByName("education[1][subjects][4][grade]", "NCEA Level 2");
        waitFor(shortWaitTime);

        clickButtonById("more_fields_education"); //click add more education button

        selectCalendarYearByName("education[2][schoolYear]", 2015); //Using date picker for school year
        selectFieldByName("education[2][schoolYearGrade]", "NCEA Level 1");

        typeFieldByName("education[2][subjects][0][subject]", "Science");
        selectFieldByName("education[2][subjects][0][grade]", "NCEA Level 1 endorsed with Excellence");
        typeFieldByName("education[2][subjects][1][subject]", "Maths");
        selectFieldByName("education[2][subjects][1][grade]", "NCEA Level 1 endorsed with Merit");
        typeFieldByName("education[2][subjects][2][subject]", "English");
        selectFieldByName("education[2][subjects][2][grade]", "NCEA Level 1");
        typeFieldByName("education[2][subjects][3][subject]", "Japanese");
        selectFieldByName("education[2][subjects][3][grade]", "NCEA Level 1");
        typeFieldByName("education[2][subjects][4][subject]", "French");
        selectFieldByName("education[2][subjects][4][grade]", "NCEA Level 1");
        waitFor(shortWaitTime);

        /*
         * Slide 6 - Personal Attributes
         */
        clickButtonById("attributes_button"); //click next button
        waitFor(longWaitTime); //wait 1 second for slide animation

        //check 6 qualities and make sure that all other qualities are disabled
        List<String> qualityList = new ArrayList<>(Arrays.asList("confident", "flexible", "broad-minded", "analytical", "determined", "hardworking"));

        for (String quality : qualityList) {
            //checkBoxClickById(quality); //check attribute versatile
            driver.findElement(By.id(quality + "_box")).click();
            waitFor(shortWaitTime);
        }


        /*
         * Slide 7 - Achievement & Awards
         */
        clickButtonById("awards_button"); //click next button
        waitFor(longWaitTime); //wait 1 second for slide animation

        selectCalendarYearByName("awards[0][awardYear]", 2015); //select year 2000 for awards[0][awardYear]
        waitFor(shortWaitTime);
        typeFieldByName("awards[0][award]", "Regional chess winner\nDistinction in science for year 11"); //type in field awards[0][award]
        waitFor(shortWaitTime);

        clickButtonById("more_fields_achievements"); //click add more entry

        selectCalendarYearByName("awards[1][awardYear]", 2016); //select year 2000 for awards[0][awardYear]
        waitFor(shortWaitTime);
        typeFieldByName("awards[1][award]", "National young musical award"); //type in field awards[0][award]
        waitFor(shortWaitTime);

        selectCalendarYearByName("extracur[0][extracurYear]", 2016);
        waitFor(shortWaitTime);
        typeFieldByName("extracur[0][activity]", "Wellington hack-a-thon\nRock climbing");
        waitFor(shortWaitTime);

        clickButtonById("more_fields_extracurricular"); //click add more entry

        selectCalendarYearByName("extracur[1][extracurYear]", 2017);
        waitFor(shortWaitTime);
        typeFieldByName("extracur[1][activity]", "Rock climbing\nHouse DJ");
        waitFor(shortWaitTime);

        /*
         * Slide 8 - Referees
         */
        clickButtonById("referees_button"); //click next button
        waitFor(longWaitTime); //wait 1 second for slide animation

        typeFieldByName("referees[0][refName]", "Mrs. Marry Dell"); //type in field referees[0][refName]
        waitFor(shortWaitTime);
        typeFieldByName("referees[0][refPosition]", "Science Teacher"); //type in field referees[0][refPosition]
        waitFor(shortWaitTime);
        typeFieldByName("referees[0][refEmail]", "marry.dell@whs.com"); //type in field referees[0][refEmail]
        waitFor(shortWaitTime);
        typeFieldByName("referees[0][refNum]", "09876544321"); //type in field referees[0][refNum]
        waitFor(shortWaitTime);

        clickButtonById("more_fields_referees"); //click add more referee

        typeFieldByName("referees[1][refName]", "Bill Gates"); //type in field referees[1][refName]
        waitFor(shortWaitTime);
        typeFieldByName("referees[1][refPosition]", "Microsoft CEO"); //type in field referees[1][refPosition]
        waitFor(shortWaitTime);
        typeFieldByName("referees[1][refEmail]", "bill@ms.com"); //type in field referees[1][refEmail]
        waitFor(shortWaitTime);
        typeFieldByName("referees[1][refNum]", "+11 4884700273"); //type in field referees[1][refNum]
        waitFor(shortWaitTime);

        clickButtonById("more_fields_referees"); //click add more referee

        typeFieldByName("referees[2][refName]", "John Key"); //type in field referees[2][refName]
        waitFor(shortWaitTime);
        typeFieldByName("referees[2][refPosition]", "Ex-NZ Prime Minister"); //type in field referees[2][refPosition]
        waitFor(shortWaitTime);
        typeFieldByName("referees[2][refEmail]", "jkey@nz.govt.nz"); //type in field referees[2][refEmail]
        waitFor(shortWaitTime);
        typeFieldByName("referees[2][refNum]", "021 7640263047"); //type in field referees[2][refNum]
        waitFor(longWaitTime);
    }
}
