package src.tests;


import src.evaluation.Evaluation;
import src.evaluation.EvaluationRegex;
import src.evaluation.rating.RatingStrategy;
import src.evaluation.rating.SyntaxRating;
import src.json.RegexRule;
import src.json.StudentData;
import src.util.JsonUtils;

import java.util.HashMap;
import java.util.List;

public class GenerateAllProfile {

    public static void main(String[] args) {
        generateAllProfile(new SyntaxRating());
    }

    public static void generateAllProfile(RatingStrategy strategy) {
        HashMap<String, RegexRule> regexForCompetencies = JsonUtils.getRegexForCompetenciesFromJson().getAllRegex();

        for (int session = 1; session <= 4; session++) {
            List<StudentData> studentDataList = JsonUtils.getAllStudentData(session);
            for (StudentData data : studentDataList) {

                Evaluation simpleEvaluation = new EvaluationRegex();
                HashMap<String, Double> generatedProfile = simpleEvaluation.evaluate(data,
                        regexForCompetencies.keySet().stream().toList(), false, strategy);

                JsonUtils.profileToJson(generatedProfile, data.getName(), session);
            }
        }
    }
}
