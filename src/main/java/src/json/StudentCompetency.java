package src.json;

import com.google.gson.annotations.Expose;

public class StudentCompetency {

    @Expose
    private String name;

    @Expose
    private double mastery;

    public StudentCompetency(String name, double mastery) {
        this.name = name;
        this.mastery = mastery;
    }

    public String getName() {
        return name;
    }

    public double getMastery() {
        return mastery;
    }
}
