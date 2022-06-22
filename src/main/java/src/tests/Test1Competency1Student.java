package src.tests;

import src.evaluation.Evaluation;
import src.evaluation.EvaluationRegex;
import src.evaluation.rating.SyntaxRating;
import src.json.StudentData;
import src.util.JsonUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * test on a single competency
 */
public class Test1Competency1Student {

    public static void main(String[] args) {
        String studentName = "ext_Alexis.Duval";
        int session = 1;
        String toEvaluate = "Conna\u00c3\u00aetre_la_syntaxe_de_instruction_set";

        StudentData data = JsonUtils.getStudentDataFromJson(studentName, session);

        // keep only 1 competency
        data.setProfile(data.getProfile().stream().filter(studentCompetency -> studentCompetency.getName().equals(toEvaluate)).collect(Collectors.toList()));

        Evaluation simpleEvaluation = new EvaluationRegex();
        HashMap<String, Double> generatedProfile = simpleEvaluation.evaluate(data, new ArrayList<>(Collections.singleton(toEvaluate)),
                true, new SyntaxRating());
        HashMap<String, Double> correctProfile = data.getHashMapProfile();

        ResultAnalyser.mesureError(generatedProfile, correctProfile, true);
    }
}
