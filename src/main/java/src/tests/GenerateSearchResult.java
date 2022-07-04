package src.tests;

import src.evaluation.Evaluation;
import src.evaluation.EvaluationRegex;
import src.evaluation.EvaluationResult;
import src.evaluation.utils.RegexUtils;
import src.json.StudentCompetency;
import src.json.StudentData;
import src.util.JsonUtils;

import java.util.List;

public class GenerateSearchResult {

    public static void main(String[] args) {
        String competencyName = "Conna\u00c3\u00aetre_la_notion_de_variable";
        List<String> allRegex = JsonUtils.getRegexForCompetenciesFromJson().getAllRegex().get(competencyName).getRegexs();

        for (int session = 1; session <= 4; session++) {

            for (StudentData data : JsonUtils.getAllStudentData(session)) {
                // generate EvaluationResult
                EvaluationResult evaluationResult = RegexUtils.searchForAllMatches(allRegex, data, false);
                List<Integer> results = evaluationResult.getFormatedRegexMatchInfo();

                // generate json with results and grade
                Evaluation evaluation = new EvaluationRegex();
                StudentCompetency manualyEvaluatedCompetency =
                        new StudentCompetency(competencyName, data.getHashMapProfile().get(competencyName), results);
                JsonUtils.profileWithRegexMatchToJson(results, manualyEvaluatedCompetency, data.getName(), session);
            }

        }
    }
}
