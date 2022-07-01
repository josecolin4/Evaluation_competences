package src.json;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class RegexRule {

    @Expose
    private String competencyName;

    @Expose
    private List<String> regex;

    // TODO don't use List, group all of the info below into one class and do a Map<Regex, Info>

    @Expose
    private List<Double> weights;

    @Expose
    private List<Double> errorWeights;

    @Expose
    private List<Integer> nbOfPossibleGains;

    @Expose
    private List<Integer> nbOfPossibleLoss;

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

    public double getErrorWeightForRegex(String regex) {
        if (errorWeights == null) {
            return getWeightForRegex(regex) / 2;  // default value
        }

        int index = regex.indexOf(regex);
        if (index >= 0 && index < errorWeights.size()) {
            return errorWeights.get(index);
        } else {
            // no error weight specified for this regex
            return getWeightForRegex(regex) / 2;  // default value
        }
    }

    public int getNumberOfPossibleGainForRegex(String regex) {
        if (nbOfPossibleGains == null) {
            return 1; // default value
        }

        int index = regex.indexOf(regex);
        if (index >= 0 && index < nbOfPossibleGains.size()) {
            return nbOfPossibleGains.get(index);
        } else {
            return 1;  // default value
        }
    }

    public int getNumberOfPossibleLossForRegex(String regex) {
        if (nbOfPossibleLoss == null) {
            return 2; // default value
        }

        int index = regex.indexOf(regex);
        if (index >= 0 && index < nbOfPossibleLoss.size()) {
            return nbOfPossibleLoss.get(index);
        } else {
            return 2;  // default value
        }
    }
}
