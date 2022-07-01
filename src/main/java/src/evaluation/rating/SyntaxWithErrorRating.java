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
            int nbOfErrors = 0;
            int nbOfCorrect = 0;
            int maxNbOfError = weights.getNumberOfPossibleLossForRegex(regex);
            int maxNbOfCorrect = weights.getNumberOfPossibleGainForRegex(regex);
            for (Match match : result.getMatchesForRegex().get(regex)) {
                if (match.isSyntaxCorrect() && nbOfCorrect < maxNbOfCorrect) {
                    rating += weights.getWeightForRegex(regex);
                    nbOfCorrect++;
                } else if (!match.isSyntaxCorrect() && nbOfErrors < maxNbOfError){
                    rating -= weights.getErrorWeightForRegex(regex);
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
