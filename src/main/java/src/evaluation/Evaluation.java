package src.evaluation;

import src.json.StudentData;

import java.util.HashMap;
import java.util.List;

public interface Evaluation {

    /**
     * Evaluate only the given competencies.
     *
     * @param data
     * @param competenciesToEvaluate
     * @return
     */
    HashMap<String, Double> evaluate(StudentData data, List<String> competenciesToEvaluate);

    /**
     * Evaluate all the competencies.
     *
     * @param data
     * @return
     */
    HashMap<String, Double> evaluate(StudentData data);
}
