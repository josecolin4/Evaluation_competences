package src.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import src.json.StudentData;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JsonUtils {

    private static final GsonBuilder gsonBuilder;
    private static Gson gson;

    static {
        gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY);
        gsonBuilder.serializeNulls();
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();

        gson = gsonBuilder.create();
    }

    public static StudentData getStudentDataFromJson(String studentName, int session) {
        File file = new File("src/main/resources/students-data/session" + session + "/" + studentName + ".json");
        try (FileReader fileReader = new FileReader(file)) {
            return gson.fromJson(fileReader, StudentData.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
