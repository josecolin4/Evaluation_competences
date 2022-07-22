package src.json;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A competency that is a summary of other competencies.
 */
public class GlobalCompetency {

    @Expose
    private String name;

    @Expose
    private List<String> competencies;

    public String getName() {
        if (name == null) {
            return "";
        }
        return name;
    }

    public List<String> getCompetencies() {
        if (competencies == null) {
            return new ArrayList<>();
        }
        return competencies;
    }

    public double evaluate(HashMap<String, Double> alreadyEvaluated) {
        double result = 0.0;

        for (Map.Entry<String, Double> evaluation : alreadyEvaluated.entrySet()) {
            if (competencies.contains(evaluation.getKey())) {
                // TODO weighted average instead of a simple average
                result += evaluation.getValue() / competencies.size();
            }
        }

        return result;
    }
}
