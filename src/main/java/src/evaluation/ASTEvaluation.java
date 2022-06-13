package src.evaluation;

import src.json.StudentData;

import java.util.HashMap;
import java.util.List;

/**
 * Evaluation of students competency by transforming their code into Abstract Syntax Tree and analysing it
 */
public class ASTEvaluation implements Evaluation {

    @Override
    public HashMap<String, Double> evaluate(StudentData data, List<String> competenciesToEvaluate) {
        return null;
    }

    @Override
    public HashMap<String, Double> evaluate(StudentData data) {
        return null;
    }
}
