package src.evaluation.rating;

import src.evaluation.EvaluationResult;
import src.evaluation.Match;
import src.json.RegexRule;

import java.util.HashMap;
import java.util.List;

/**
 * Rate by the number of matches that also have a correct bash syntax.
 * We count up to 1 match per regex.
 */
public class SyntaxRating implements RatingStrategy {

    @Override
    public double rate(EvaluationResult result, RegexRule weights) {
        double rating = 0.0;

        for (String regex : result.getMatchesForRegex().keySet()) {
            for (Match match : result.getMatchesForRegex().get(regex)) {
                if (match.isSyntaxIsCorrect()) {
                    rating += weights.getWeightForRegex(regex);
                    break; // we count only one occurrence per regex
                }
            }
        }

        return Math.min(1,  rating);
    }
}
