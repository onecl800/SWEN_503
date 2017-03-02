package testClasses;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Josh on 22/01/2017.
 */
public class SimpleJsonParser {

    private JSONObject jsonObject;

    public SimpleJsonParser(String fileName) {
        FileInputStream fisTargetFile;
        try {
            fisTargetFile = new FileInputStream(new File(fileName));
            String jsonStr = IOUtils.toString(fisTargetFile, "UTF-8");

            jsonObject = new JSONObject(jsonStr); // Parse the JSON to a JSONObject
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            // JSON Parsing error
            e.printStackTrace();
        }

    }

    public String getString(String key) {
        return jsonObject.getString(key);
    }

    public JSONArray getArray(String key) {
        return jsonObject.getJSONArray(key);
    }

}
