package src.tests;

import src.json.StudentData;
import src.util.JsonUtils;

import java.util.HashMap;

/**
 * Compares the profile given by the automatic evaluation and the profile made manually
 */
public class ResultAnalyser {

    public static double mesureError(HashMap<String, Double> generatedProfile, HashMap<String, Double> correctProfile,
                                     boolean printResult) {

        if (generatedProfile.size() != correctProfile.size()) {
            throw new IllegalStateException("generated is of the wrong size : " + generatedProfile.size()
                                            + "    expected : " + correctProfile.size());
        }

        double totalError = 0;
        for (String competencyName : correctProfile.keySet()) {
            double error = Math.abs(generatedProfile.get(competencyName) - correctProfile.get(competencyName));
            totalError += error;

            if (printResult) {
                System.out.println("error on :\n " + competencyName + "\n is : " + error);
            }
        }

        if (printResult) {
            System.out.println("total error is : " + totalError + "\naverage error is : " + (totalError / correctProfile.size()) + "\n");
        }
        return totalError;
    }
}
