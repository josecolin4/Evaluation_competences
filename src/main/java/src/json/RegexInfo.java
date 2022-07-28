package src.json;

import com.google.gson.annotations.Expose;

public class RegexInfo {

    @Expose
    private String regex;

    @Expose
    private double weight;

    @Expose
    private double errorWeight;

    @Expose
    private double nbOfPossibleGain;

    @Expose
    private double nbOfPossibleLoss;

    public String getRegex() {
        return regex;
    }

    public double getWeight() {
        return weight;
    }

    public double getErrorWeight() {
        return errorWeight;
    }

    public double getNbOfPossibleGain() {
        return nbOfPossibleGain;
    }

    public double getNbOfPossibleLoss() {
        return nbOfPossibleLoss;
    }
}
