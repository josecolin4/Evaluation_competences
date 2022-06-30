package src.tests;

import src.evaluation.Evaluation;
import src.evaluation.EvaluationRegex;
import src.evaluation.rating.SyntaxRating;
import src.evaluation.rating.SyntaxWithErrorRating;
import src.json.RegexRule;
import src.json.StudentData;
import src.util.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateGlobalProfile {

    public static void main(String[] args) {
        // loop on every student using the list of student having a manual evaluation on session 1 (registered students)
        for (StudentData data : JsonUtils.getAllStudentData(1)) {
            generateGlobalProfile(data.getName());
        }
    }

    public static void generateGlobalProfile(String studentName) {
        HashMap<String, RegexRule> regexForCompetencies = JsonUtils.getRegexForCompetenciesFromJson().getAllRegex();
        List<HashMap<String, Double>> allProfiles = new ArrayList<>();

        // generate all for this student
        // TODO generalize to any number of sessions
        for (int session = 1; session <= 4; session++) {
            StudentData data = JsonUtils.getAllStudentData(session)
                    .stream()
                    .filter(studentData -> studentData.getName().equals(studentName))
                    .toList().get(0);

            Evaluation simpleEvaluation = new EvaluationRegex();
            allProfiles.add(simpleEvaluation.evaluate(data,
                    regexForCompetencies.keySet().stream().toList(), false, new SyntaxWithErrorRating()));
        }

        // generate global profile from all profiles

        // We consider a rating of 0 as non evaluated
        // This can cause problems because if a student has got a rating of 0 because
        // he failed to master the competency, we don't have a way to differentiate it with
        // a non evaluation

        HashMap<String, Double> globalProfile = new HashMap<>();
        for (String competency : regexForCompetencies.keySet()) {
            globalProfile.put(competency, 0.0);
        }

//        for (int i = 0; i < coefficients.size(); i++) {
//
//            HashMap<String, Double> profile = allProfiles.get(allProfiles.size() - i - 1);
//            for (String competency : profile.keySet()) {
//                // TODO if rating is equal to 0, then skip the session (increment i) only for this competency
//
//                globalProfile.merge(competency, profile.get(competency) * coefficients.get(i), Double::sum);
//            }
//        }

        // iterating from oldest session to newest
        for (HashMap<String, Double> profileForSession : allProfiles) {
            for (Map.Entry<String, Double> competency : profileForSession.entrySet()) {
                if (competency.getValue().equals(0.0)) {
                    // rating of 0, so we consider the competency to be non-evaluated and we skip the session
                    // TODO increase uncertainty for this competency
                    continue;
                }

                if (globalProfile.get(competency.getKey()).equals(0.0)) {
                    // first evaluation, putting the raw value in global profile
                    globalProfile.put(competency.getKey(), competency.getValue());
                } else {
                    // update global profile with the new value
                    // TODO instead of simply looking at the old value, look at old regex matches and
                    //  take them into account in the notation

                    final double OLD_RATING_RATIO = 0.3;
                    final double NEW_RATING_RATIO = 0.7;
                    globalProfile.put(competency.getKey(),
                                      globalProfile.get(competency.getKey()) * OLD_RATING_RATIO
                                              + competency.getValue() * NEW_RATING_RATIO);
                }
            }
        }

        // to json
        JsonUtils.globalProfileToJson(globalProfile, studentName);
    }
}
