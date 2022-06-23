package src.tests;

import src.evaluation.Evaluation;
import src.evaluation.EvaluationRegex;
import src.evaluation.rating.SyntaxRating;
import src.json.RegexRule;
import src.json.StudentData;
import src.util.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GenerateGlobalProfile {

    public static void main(String[] args) {
        generateGlobalProfile("ext_Abrina.Vandal");
    }

    public static void generateGlobalProfile(String studentName) {
        HashMap<String, RegexRule> regexForCompetencies = JsonUtils.getRegexForCompetenciesFromJson().getAllRegex();
        List<HashMap<String, Double>> allProfiles = new ArrayList<>();

        // generate all for this student
        for (int session = 1; session <= 4; session++) {
            StudentData data = JsonUtils.getAllStudentData(session)
                    .stream()
                    .filter(studentData -> studentData.getName().equals(studentName))
                    .toList().get(0);

            Evaluation simpleEvaluation = new EvaluationRegex();
            allProfiles.add(simpleEvaluation.evaluate(data,
                    regexForCompetencies.keySet().stream().toList(), false, new SyntaxRating()));
        }

        // generate global profile from all profiles
        // TODO probably better to have the first coefficient at 1, and the other coefficients matter
        //  only if the first one was not evaluated
        List<Double> coefficients = List.of(0.6, 0.3, 0.1);

        // We consider a rating of 0 as non evaluated
        // This can cause problems because if a student has got a rating of 0 because
        // he failed to master the competency, we don't have a way to differentiate it with
        // a non evaluation

        HashMap<String, Double> globalProfile = new HashMap<>();
        for (String competency : regexForCompetencies.keySet()) {
            globalProfile.put(competency, 0.0);
        }

        for (int i = 0; i < coefficients.size(); i++) {

            HashMap<String, Double> profile = allProfiles.get(allProfiles.size() - i - 1);
            for (String competency : profile.keySet()) {
                // TODO if rating is equal to 0, then skip the session (increment i) only for this competency

                globalProfile.merge(competency, profile.get(competency) * coefficients.get(i), Double::sum);
            }
        }

        // to json
        JsonUtils.globalProfileToJson(globalProfile, studentName);
    }
}
