package src.tests;

import src.evaluation.Evaluation;
import src.evaluation.SimpleEvaluationRegex;
import src.json.StudentData;
import src.util.JsonUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Test1CompetencyAllStudents {

    public static void main(String[] args) {
        int nbProfileWithBigError = 0;
        int nbProfileWithoutBigError = 0;
        String toEvaluate = "Conna\u00c3\u00aetre_la_syntaxe_de_instruction_set";

        for (int session = 1; session <= 4; session++) {
            System.out.println("session n°" + session + "\n");

            double totalError = 0.0;
            double nbProfileTested = 1;

            List<StudentData> studentDataList = JsonUtils.getAllStudentData(session);
            for (StudentData data : studentDataList) {

                // keep only 1 competency
                data.setProfile(data.getProfile().stream().filter(studentCompetency -> studentCompetency.getName().equals(toEvaluate)).collect(Collectors.toList()));

                Evaluation simpleEvaluation = new SimpleEvaluationRegex();
                HashMap<String, Double> generatedProfile = simpleEvaluation.evaluate(data, new ArrayList<>(Collections.singleton(toEvaluate)), false);    // TODO generate profile
                HashMap<String, Double> correctProfile = data.getHashMapProfile();

                try {
                    double error = ResultAnalyser.mesureError(generatedProfile, correctProfile, false);
                    totalError += error;
                    if (error > 0.2) {
                        System.out.println(data.getName() + " : session " + session + " : " + error);
                        nbProfileWithBigError++;
                    } else {
                        nbProfileWithoutBigError++;
                    }

                    nbProfileTested++;
                } catch (IllegalStateException e) {
                    // skip
                    System.out.println(data.getName() + " for session " + session + " didn't had a proper profile to compare to");
                }
            }

            System.out.println();
            System.out.println("average error at session " + session + " is " + totalError / nbProfileTested);
        }
        System.out.println();
        System.out.println("nb of profile with an error > 0.2 : " + nbProfileWithBigError);
        System.out.println("nb of profile with an error < 0.2 : " + nbProfileWithoutBigError);
    }
}
