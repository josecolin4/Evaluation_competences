package src.tests;


import src.evaluation.Evaluation;
import src.evaluation.EvaluationRegex;
import src.evaluation.rating.RatingStrategy;
import src.evaluation.rating.SyntaxRating;
import src.evaluation.rating.SyntaxWithErrorRating;
import src.json.GlobalCompetency;
import src.json.RegexRule;
import src.json.StudentData;
import src.util.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GenerateAllProfile {

    public static void main(String[] args) {
        generateAllProfile(new SyntaxWithErrorRating());
    }

    public static void generateAllProfile(RatingStrategy strategy) {
        HashMap<String, RegexRule> regexForCompetencies = JsonUtils.getRegexForCompetenciesFromJson().getAllRegex();
        List<String> globalCompetencies = JsonUtils.getRegexForCompetenciesFromJson().getGlobalCompetencies()
                .stream()
                .map(GlobalCompetency::getName)
                .toList();

        for (int session = 1; session <= 4; session++) {
            List<StudentData> studentDataList = JsonUtils.getAllStudentData(session);
            for (StudentData data : studentDataList) {

                Evaluation simpleEvaluation = new EvaluationRegex();
                List<String> toEvaluate = regexForCompetencies.keySet().stream().toList();
                toEvaluate = new ArrayList<>(toEvaluate); // copy content in an array list
                toEvaluate.addAll((globalCompetencies));
                HashMap<String, Double> generatedProfile = simpleEvaluation.evaluate(data, toEvaluate,false, strategy);

                JsonUtils.profileToJson(generatedProfile, data.getName(), session);
            }
        }
    }
}
