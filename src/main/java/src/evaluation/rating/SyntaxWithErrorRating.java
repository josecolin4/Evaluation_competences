package src.evaluation.rating;

import src.evaluation.EvaluationResult;
import src.evaluation.Match;
import src.json.RegexRule;

/**
 * Same that SyntaxRating but errors in syntax count as minus
 */
public class SyntaxWithErrorRating implements RatingStrategy {

    @Override
    public double rate(EvaluationResult result, RegexRule weights) {
        double rating = 0.0;

        for (String regex : result.getMatchesForRegex().keySet()) {
            int nbOfErrors = 0; // nb of syntax error for all the matches on this regex
            int nbOfCorrect = 0;
            for (Match match : result.getMatchesForRegex().get(regex)) {
                if (match.isSyntaxCorrect() && nbOfCorrect < 1) {
                    rating += weights.getWeightForRegex(regex);
                    nbOfCorrect++;
                } else if (!match.isSyntaxCorrect() && nbOfErrors < 2){
                    // TODO make this value not a constant
                    rating -= weights.getWeightForRegex(regex) / 2;
                    nbOfErrors++;
                }
            }
        }

        return Math.max(0, Math.min(1,  rating));
    }

    @Override
    public String toString() {
        return "syntaxRatingWithError";
    }
}
