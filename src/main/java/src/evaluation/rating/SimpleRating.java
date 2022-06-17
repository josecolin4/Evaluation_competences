package src.evaluation.rating;

import src.evaluation.EvaluationResult;
import src.evaluation.Match;
import src.json.RegexRule;

/**
 * Rate by the number of matches
 * We count up to 1 match per regex.
 */
public class SimpleRating implements RatingStrategy {

    @Override
    public double rate(EvaluationResult result, RegexRule weights) {
        double rating = 0.0;

        for (String regex : result.getMatchesForRegex().keySet()) {
            if (!result.getMatchesForRegex().get(regex).isEmpty()) {
                rating += weights.getWeightForRegex(regex);
            }
        }

        return Math.min(1,  rating);
    }
}
