package src.evaluation;

import src.json.StudentData;

import java.util.HashMap;

public interface Evaluation {

    HashMap<String, Integer> evaluate(StudentData data);
}
