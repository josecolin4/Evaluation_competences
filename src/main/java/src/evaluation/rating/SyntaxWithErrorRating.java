package src.evaluation.rating;

import src.evaluation.EvaluationResult;
import src.evaluation.Match;
import src.json.RegexRule;

public class SyntaxWithErrorRating implements RatingStrategy {

    @Override
    public double rate(EvaluationResult result, RegexRule regexRule) {
        double rating = 0.0;

        for (String regex : result.getMatchesForRegex().keySet()) {
            int nbOfErrors = 0;
            int nbOfCorrect = 0;
            double maxNbOfError = regexRule.getNumberOfPossibleLossForRegex(regex);
            double maxNbOfCorrect = regexRule.getNumberOfPossibleGainForRegex(regex);
            for (Match match : result.getMatchesForRegex().get(regex)) {
                if (match.isSyntaxCorrect()) {
                    nbOfCorrect++;
                } else {
                    nbOfErrors++;
                }
            }
            rating += regexRule.getWeightForRegex(regex) * Math.min(maxNbOfCorrect, nbOfCorrect)
                      - regexRule.getErrorWeightForRegex(regex) * Math.min(maxNbOfError, nbOfErrors);
        }

        return Math.max(0, Math.min(1,  rating));
    }

    @Override
    public String toString() {
        return "syntaxRatingWithError";
    }
}
