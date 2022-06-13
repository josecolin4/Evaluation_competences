package src.tests;

import src.evaluation.Evaluation;
import src.evaluation.SimpleEvaluation;
import src.json.StudentData;
import src.util.JsonUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * test on a single competency
 */
public class Test1Competency {

    public static void main(String[] args) {
        String studentName = "ext_Ambre.Rouillon";
        int session = 1;
        String toEvaluate = "Conna\u00c3\u00aetre_la_syntaxe_de_instruction_set";

        StudentData data = JsonUtils.getStudentDataFromJson(studentName, session);

        // keep only 1 competency
        data.setProfile(data.getProfile().stream().filter(studentCompetency -> studentCompetency.getName().equals(toEvaluate)).collect(Collectors.toList()));

        Evaluation simpleEvaluation = new SimpleEvaluation();
        HashMap<String, Double> generatedProfile = simpleEvaluation.evaluate(data, new ArrayList<>(Collections.singleton(toEvaluate)));    // TODO generate profile
        HashMap<String, Double> correctProfile = data.getHashMapProfile();

        ResultAnalyser.mesureError(generatedProfile, correctProfile);
    }
}
