package src.json;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class RegexRule {

    @Expose
    private String competencyName;

    @Expose
    private List<String> regex;

    @Expose
    private List<Double> weights;

    public String getCompetencyName() {
        return competencyName;
    }

    public List<String> getRegexs() {
        if (regex == null) {
            return new ArrayList<>();
        }
        return regex;
    }

    public double getWeightForRegex(String regex) {
        if (weights == null) {
            // no weight specified
            return 0;
        }

        int index = regex.indexOf(regex);
        if (index >= 0 && index < weights.size()) {
            return weights.get(index);
        } else {
            // no weight specified for this regex
            return 0;
        }
    }
}
