package src.evaluation;

import src.api.StudentTraces;
import src.evaluation.rating.RatingStrategy;
import src.evaluation.utils.RegexUtils;
import src.json.GlobalCompetency;
import src.json.RegexRule;
import src.util.JsonUtils;

import java.util.HashMap;
import java.util.List;

public class EvaluationRegex implements Evaluation {

    /**
     * Evaluation based on matching student traces with regex. This gives a rating for each competency
     * based on the rating strategy.
     */
    @Override
    public HashMap<String, Double> evaluate(StudentTraces traces, List<String> competenciesToEvaluate,
                                            RatingStrategy rating) {

        HashMap<String, Double> profile = new HashMap<>();


        HashMap<String, RegexRule> regexRuleList = JsonUtils.getCompetencyFramework().getAllRegexRules();

        for (String competency : competenciesToEvaluate) {
            if (regexRuleList.containsKey(competency)) {
                EvaluationResult result = RegexUtils.searchForAllMatches(regexRuleList.get(competency).getRegexs(), traces);
                profile.put(competency, rating.rate(result, regexRuleList.get(competency)));
            } else {
                profile.put(competency, 0.0);
            }
        }

        // now evaluate global competencies
        for (GlobalCompetency globalCompetency : JsonUtils.getCompetencyFramework().getGlobalCompetencies()) {
            if (competenciesToEvaluate.contains(globalCompetency.getName())) {
                profile.put(globalCompetency.getName(), globalCompetency.evaluate(profile));
            }
        }

        return profile;
    }
}
