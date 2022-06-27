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
            for (Match match : result.getMatchesForRegex().get(regex)) {
                if (match.isSyntaxCorrect()) {
                    rating += weights.getWeightForRegex(regex);
                    break; // we count only one occurrence per regex
                } else if (nbOfErrors <= 2){
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
