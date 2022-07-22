package src.tests;


import src.evaluation.Evaluation;
import src.evaluation.EvaluationRegex;
import src.evaluation.rating.RatingStrategy;
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
        List<String> toEvaluate = JsonUtils.getCompetencyFramework().getAllCompetenciesName();

        for (int session = 1; session <= 4; session++) {
            List<StudentData> studentDataList = JsonUtils.getAllStudentData(session);
            for (StudentData data : studentDataList) {

                Evaluation simpleEvaluation = new EvaluationRegex();
                HashMap<String, Double> generatedProfile = simpleEvaluation.evaluate(data.getTraces(), toEvaluate, strategy);

                JsonUtils.profileToJson(generatedProfile, data.getName(), session);
            }
        }
    }
}
