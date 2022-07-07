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
    private List<Double> nbOfPossibleGains;

    @Expose
    private List<Double> nbOfPossibleLoss;

    public String getCompetencyName() {
        return competencyName;
    }

    public List<String> getRegexs() {
        if (regex == null) {
            return new ArrayList<>();
        }
        return regex;
    }

    public double getWeightForRegex(String regexToFind) {
        if (weights == null) {
            // no weight specified
            return 0;
        }

        int index = regex.indexOf(regexToFind);
        if (index >= 0 && index < weights.size()) {
            return weights.get(index);
        } else {
            // no weight specified for this regex
            return 0;
        }
    }

    public double getErrorWeightForRegex(String regexToFind) {
        if (errorWeights == null) {
            return getWeightForRegex(regexToFind) / 2;  // default value
        }

        int index = regex.indexOf(regexToFind);
        if (index >= 0 && index < errorWeights.size()) {
            return errorWeights.get(index);
        } else {
            // no error weight specified for this regex
            return getWeightForRegex(regexToFind) / 2;  // default value
        }
    }

    public double getNumberOfPossibleGainForRegex(String regexToFind) {
        if (nbOfPossibleGains == null) {
            return 1; // default value
        }

        int index = regex.indexOf(regexToFind);
        if (index >= 0 && index < nbOfPossibleGains.size()) {
            return nbOfPossibleGains.get(index);
        } else {
            return 1;  // default value
        }
    }

    public double getNumberOfPossibleLossForRegex(String regexToFind) {
        if (nbOfPossibleLoss == null) {
            return 2; // default value
        }

        int index = regex.indexOf(regexToFind);
        if (index >= 0 && index < nbOfPossibleLoss.size()) {
            return nbOfPossibleLoss.get(index);
        } else {
            return 2;  // default value
        }
    }
}
