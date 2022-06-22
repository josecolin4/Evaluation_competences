package src.tests;

import src.json.RegexRule;
import src.json.StudentData;
import src.util.JsonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class AnalyzeManualEvaluation {

    /**
     * Look for competencies in the dataset that are often evaluated (not left at 0)
     */
    public static void main(String[] args) {
        HashMap<String, Integer> nbOfEvaluation = new HashMap<>();

        for (int session = 1; session <= 4; session++) {
            List<StudentData> studentDataList = JsonUtils.getAllStudentData(session);
            for (StudentData data : studentDataList) {

                HashMap<String, Double> correctProfile = data.getHashMapProfile();
                for (String competency : correctProfile.keySet()) {
                    if (correctProfile.get(competency) > 0) {
                        nbOfEvaluation.merge(competency, 1, Integer::sum);
                    }
                }
            }
        }

        for (String competency : nbOfEvaluation.keySet()) {
            System.out.println("competency : " + competency + " is evaluated : " + nbOfEvaluation.get(competency));
        }
    }
}
