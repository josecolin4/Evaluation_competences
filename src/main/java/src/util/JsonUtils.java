package src.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import src.json.CompetencyFramework;
import src.json.StudentCompetency;
import src.json.StudentData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class JsonUtils {

    private static final GsonBuilder gsonBuilder;
    private static Gson gson;

    private static CompetencyFramework regexForCompetencies;

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

    public static CompetencyFramework getCompetencyFramework() {
        if (regexForCompetencies != null) {
            return regexForCompetencies;
        }

        File file = new File("src/main/resources/regex_for_competencies.json");
        try (FileReader fileReader = new FileReader(file)) {
            regexForCompetencies = gson.fromJson(fileReader, CompetencyFramework.class);
            return regexForCompetencies;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void profileToJson(HashMap<String, Double> profile, String studentName, int session) {
        List<StudentCompetency> jsonProfile = new ArrayList<>();
        for (Map.Entry<String, Double> competency : profile.entrySet()) {
            jsonProfile.add(new StudentCompetency(competency.getKey(), competency.getValue()));
        }

        jsonProfile.sort(Comparator.comparingInt(competency ->
                regexForCompetencies.getCompetencyIndex(competency.getName())));

        File file = new File("src/main/resources/generated/session" + session + "/" + studentName + ".json");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(gson.toJson(jsonProfile).getBytes());
            fos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void profileWithRegexMatchToJson(List<Integer> regexMatches,
                                                   StudentCompetency competency, String studentName, int session) {
        File file = new File("src/main/resources/generated_regex_matches/session" + session + "/" + studentName + ".json");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(gson.toJson(competency).getBytes());
            fos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void globalProfileToJson(HashMap<String, Double> profile, String studentName) {
        List<StudentCompetency> jsonProfile = new ArrayList<>();
        for (Map.Entry<String, Double> competency : profile.entrySet()) {
            jsonProfile.add(new StudentCompetency(competency.getKey(), competency.getValue()));
        }

        File file = new File("src/main/resources/generated/global/" + studentName + ".json");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(gson.toJson(jsonProfile).getBytes());
            fos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
