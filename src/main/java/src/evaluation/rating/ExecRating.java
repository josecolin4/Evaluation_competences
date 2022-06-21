package src.evaluation.rating;

import src.evaluation.EvaluationResult;
import src.evaluation.Match;
import src.json.RegexRule;

/**
 * Rate by the number of matches where the script is also executed without errors.
 * We count up to 1 match per regex.
 */
public class ExecRating implements RatingStrategy {

    @Override
    public double rate(EvaluationResult result, RegexRule weights) {
        double rating = 0.0;

        for (String regex : result.getMatchesForRegex().keySet()) {
            for (Match match : result.getMatchesForRegex().get(regex)) {
                // TODO
            }
        }

        return Math.min(1,  rating);
    }

    @Override
    public String toString() {
        return "syntaxRating";
    }
}
