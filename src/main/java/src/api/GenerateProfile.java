package src.api;

import src.evaluation.Evaluation;
import src.evaluation.EvaluationRegex;
import src.evaluation.rating.RatingStrategy;
import src.evaluation.rating.SyntaxWithErrorRating;
import src.json.CompetencyFramework;
import src.util.JsonUtils;

import java.util.HashMap;

public class GenerateProfile {

    private static final Evaluation EVALUATION_METHOD = new EvaluationRegex();
    private static final RatingStrategy RATING_STRATEGY = new SyntaxWithErrorRating();
    private static final CompetencyFramework COMPETENCY_FRAMEWORK = JsonUtils.getCompetencyFramework();

    public static StudentProfile generate(String studentName, StudentTraces traces) {
        HashMap<String, Double> profile = EVALUATION_METHOD.evaluate(traces,
                                                                     COMPETENCY_FRAMEWORK.getAllCompetenciesName(),
                                                                     RATING_STRATEGY);
        return new StudentProfile(profile);
    }
}
