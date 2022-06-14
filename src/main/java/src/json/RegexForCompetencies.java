package src.json;

import com.google.gson.annotations.Expose;

import java.util.HashMap;
import java.util.List;

public class RegexForCompetencies {

    @Expose
    private List<RegexForCompetency> competencies;

    public List<RegexForCompetency> getCompetencies() {
        return competencies;
    }

    public HashMap<String, List<String>> getAllRegex() {
        HashMap<String, List<String>> regexList = new HashMap<>();
        for (RegexForCompetency regex : competencies) {
            regexList.put(regex.getName(), regex.getRegex());
        }

        return regexList;
    }
}
