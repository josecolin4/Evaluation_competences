package src.evaluation;

import src.api.StudentTraces;
import src.evaluation.rating.RatingStrategy;

import java.util.HashMap;
import java.util.List;

public interface Evaluation {

    HashMap<String, Double> evaluate(StudentTraces traces, List<String> competenciesToEvaluate, RatingStrategy rating);
}
