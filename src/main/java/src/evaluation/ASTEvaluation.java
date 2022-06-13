package src.evaluation;

import src.json.StudentData;

import java.util.HashMap;

/**
 * Evaluation of students competency by transforming their code into Abstract Syntax Tree and analysing it
 */
public class ASTEvaluation implements Evaluation {

    @Override
    public HashMap<String, Integer> evaluate(StudentData data) {
        HashMap<String, Integer> profile = new HashMap<>();

        // TODO loop through all competencies and add them to the profile

        return null;
    }
}
