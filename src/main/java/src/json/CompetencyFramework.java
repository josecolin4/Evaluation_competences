package src.json;

import com.google.gson.annotations.Expose;
import src.util.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CompetencyFramework {

    @Expose
    private List<RegexRule> regexForCompetencies;

    @Expose
    private List<GlobalCompetency> globalCompetencies;

    public HashMap<String, RegexRule> getAllRegexRules() {
        HashMap<String, RegexRule> regexList = new HashMap<>();
        for (RegexRule regexs : regexForCompetencies) {
            regexList.put(regexs.getCompetencyName(), regexs);
        }

        return regexList;
    }

    public List<GlobalCompetency> getGlobalCompetencies() {
        if (globalCompetencies == null) {
            return new ArrayList<>();
        }
        return globalCompetencies;
    }

    public List<String> getAllCompetenciesName() {
        List<String> globalCompetencies = this.globalCompetencies
                .stream()
                .map(GlobalCompetency::getName)
                .toList();

        List<String> competencies = regexForCompetencies
                .stream()
                .map(RegexRule::getCompetencyName)
                .toList();

        List<String> toReturn = new ArrayList<>();
        toReturn.addAll(competencies);
        toReturn.addAll(globalCompetencies);

        return toReturn;
    }

    public int getCompetencyIndex(String competencyName) {
        for (int i = 0; i < globalCompetencies.size(); i++) {
            if (globalCompetencies.get(i).getName().equals(competencyName)) {
                return i;
            }
        }
        for (int i = 0; i < regexForCompetencies.size(); i++) {
            if (regexForCompetencies.get(i).getCompetencyName().equals(competencyName)) {
                return i + globalCompetencies.size();
            }
        }

        return Integer.MAX_VALUE;
    }
}
