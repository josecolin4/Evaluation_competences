package src.json;

import com.google.gson.annotations.Expose;

import java.util.HashMap;
import java.util.List;

public class RegexForCompetencies {

    @Expose
    private List<RegexRule> regexForCompetencies;

    public HashMap<String, RegexRule> getAllRegex() {
        HashMap<String, RegexRule> regexList = new HashMap<>();
        for (RegexRule regexs : regexForCompetencies) {
            regexList.put(regexs.getCompetencyName(), regexs);
        }

        return regexList;
    }
}
