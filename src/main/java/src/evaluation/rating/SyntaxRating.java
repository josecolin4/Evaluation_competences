package src.evaluation.rating;

import src.evaluation.EvaluationResult;
import src.evaluation.Match;
import src.json.RegexRule;

public class SyntaxRating implements RatingStrategy {

    @Override
    public double rate(EvaluationResult result, RegexRule regexRule) {
        double rating = 0.0;

        for (String regex : result.getMatchesForRegex().keySet()) {
            for (Match match : result.getMatchesForRegex().get(regex)) {
                if (match.isSyntaxCorrect()) {
                    rating += regexRule.getWeightForRegex(regex);
                    break; // we count only one occurrence per regex
                }
            }
        }

        return Math.min(1,  rating);
    }

    @Override
    public String toString() {
        return "syntaxRating";
    }
}
