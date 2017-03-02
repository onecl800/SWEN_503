This project is a CV building web application aimed at New Zealand high school students studying NCEA.
It consists of 3 web pages that are linked together.

Descriptino of the 3 main web pages that make up the application, 

1.	index.php -> this page is the testing page that allows the user to enter a test NCEA National Student Number (NSN) and load the data for that ID number.

2.	landing_page.php -> this page is the second page the user will encounter, after an ID is entered into the index.php page and the user clicks the test button on that page.
					 -> this page has two buttons,  "Create CV" and "Edit CV". "Create CV" creates a new blank CV with the given ID number while "Edit CV" loads the data from 
					 -> the ID's corresponding JSON file on the server and populates the fields with this data. The useris then able to edit and fields they would like to change.

3.	create_cv.php -> this is the main appication page that contains a carousel of screens with sets of fields of CV content.

