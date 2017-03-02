import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import testClasses.Chrome;
import testClasses.Firefox;
import testClasses.SimpleJsonParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    static String url = "http://localhost/home/index.php";
    static long waitTime = 1000;

    public static void main(String[] args) {
        SimpleJsonParser placeholderJson = new SimpleJsonParser("../example.json");
        System.out.println(placeholderJson.getString("refNum"));
    }
}
