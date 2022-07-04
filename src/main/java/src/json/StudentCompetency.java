package src.json;

import com.google.gson.annotations.Expose;

import java.util.List;

public class StudentCompetency {

    @Expose
    private String name;

    @Expose
    private double mastery;

    @Expose
    private List<Integer> regexMatches;

    public StudentCompetency(String name, double mastery) {
        this.name = name;
        this.mastery = mastery;
    }

    public StudentCompetency(String name, double mastery, List<Integer> regexMatches) {
        this.name = name;
        this.mastery = mastery;
        this.regexMatches = regexMatches;
    }

    public String getName() {
        return name;
    }

    public double getMastery() {
        return mastery;
    }
}
