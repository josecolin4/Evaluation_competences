package src.evaluation.rating;

import src.evaluation.EvaluationResult;
import src.evaluation.Match;
import src.json.RegexRule;

public class SimpleRating implements RatingStrategy {

    @Override
    public double rate(EvaluationResult result, RegexRule regexRule) {
        double rating = 0.0;

        for (String regex : result.getMatchesForRegex().keySet()) {
            if (!result.getMatchesForRegex().get(regex).isEmpty()) {
                rating += regexRule.getWeightForRegex(regex);
            }
        }

        return Math.min(1,  rating);
    }

    @Override
    public String toString() {
        return "simpleRating";
    }
}
