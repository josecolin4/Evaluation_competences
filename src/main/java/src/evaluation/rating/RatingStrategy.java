package src.evaluation.rating;

import src.evaluation.EvaluationResult;
import src.json.RegexRule;

public interface RatingStrategy {

    /**
     * Given an EvaluationResult, gives a grade between 0 and 1.
     * @param result
     * @return
     */
    double rate(EvaluationResult result, RegexRule weights);
}
