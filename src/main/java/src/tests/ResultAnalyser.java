package src.tests;

import src.json.StudentCompetency;
import src.json.StudentData;
import src.util.JsonUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Compares the profile given by the automatic evaluation and the profile made manually
 */
public class ResultAnalyser {

    public static void main(String[] args) {
        // TODO loop over students
        String studentName = "ext_Adam.Hassan";
        int session = 1;

        StudentData data = JsonUtils.getStudentDataFromJson(studentName, session); // TODO get the correct json file

        HashMap<String, Double> generatedProfile = null;    // TODO generate profile
        HashMap<String, Double> correctProfile = data.getHashMapProfile();

        // Comparaison
        if (generatedProfile.size() != correctProfile.size()) {
            throw new IllegalStateException("generated is of the wrong size");
        }

        double totalError = 0;
        for (String competencyName : correctProfile.keySet()) {
            double error = Math.abs(generatedProfile.get(competencyName) - correctProfile.get(competencyName));
            totalError += error;

            System.out.println("error on :\n " + competencyName + "\n is : " + error);
        }

        System.out.println("total error is : " + totalError + "\naverage error is : " + (totalError / correctProfile.size()));
    }

}
