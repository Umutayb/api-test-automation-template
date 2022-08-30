package common;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Scenario;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import models.cucumber.CucumberReport;
import org.json.simple.JSONArray;
import models.commons.Receivers;
import java.util.ArrayList;
import java.util.Properties;
import resources.Colors;
import utils.*;

import java.util.List;
import java.io.*;

import static resources.Colors.RESET;

public abstract class Utilities extends Caller {
    public static Scenario scenario;
    public static Properties properties;

    public static ObjectMapper mapper = new ObjectMapper();
    public static ObjectUtilities objectUtils = new ObjectUtilities();
    public static NumericUtilities numUtils = new NumericUtilities();
    public static StringUtilities strUtils = new StringUtilities();

    public enum Color {CYAN, RED, GREEN, YELLOW, PURPLE, GRAY, BLUE}

    Printer log = new Printer(Utilities.class);

    public Utilities(){
        properties = FileUtilities.properties;
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    public String highlighted(Color color, String text){return (objectUtils.getFieldValue(color.name(), Colors.class) + text + RESET);}

    public List<Receivers.Receiver> getReceivers() {
        try(FileReader file = new FileReader(properties.getProperty("receivers-directory"))) {
            return mapper.readValue(file, Receivers.class).receivers();
        }
        catch (IOException e) {throw new RuntimeException(e);}
    }

    public List<CucumberReport> getCucumberReport(String directory){
        try {
            List<CucumberReport> reports = new ArrayList<>();
            FileReader reportFile = new FileReader(directory);
            JSONParser parser = new JSONParser();
            JSONArray array = (JSONArray) parser.parse(reportFile);
            for (Object jsonObject: array) {
                String json = jsonObject.toString();
                reports.add(mapper.readValue(json , CucumberReport.class));
            }
            return  reports;
        }
        catch (IOException | ParseException e) {throw new RuntimeException(e);}
    }
}
