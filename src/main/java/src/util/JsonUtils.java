package src.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import src.json.RegexForCompetencies;
import src.json.StudentData;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public static List<StudentData> getAllStudentData(int session) {
        List<StudentData> data = new ArrayList<>();

        File dir = new File("src/main/resources/students-data/session" + session);
        if (dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                try (FileReader fileReader = new FileReader(file)) {
                     data.add(gson.fromJson(fileReader, StudentData.class));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return data;
    }

    public static RegexForCompetencies getRegexForCompetenciesFromJson() {
        File file = new File("src/main/resources/regex_for_competencies.json");
        try (FileReader fileReader = new FileReader(file)) {
            return gson.fromJson(fileReader, RegexForCompetencies.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    public static void bashNodeToJson(List<BashNode> nodes) {
//        File file = new File("src/main/resources/nodes_with_competencies.json");
//        try (FileOutputStream fos = new FileOutputStream(file)) {
//            Nodes allNodes = new Nodes(nodes);
//            fos.write(gson.toJson(allNodes, Nodes.class).getBytes());
//            fos.flush();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
