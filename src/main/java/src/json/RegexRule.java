package src.json;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * List of regex associated with a competency and different parameters
 * like weight for the rating, ...
 */
public class RegexRule {

    @Expose
    private String competencyName;

    @Expose
    private List<RegexInfo> regexList;

    public String getCompetencyName() {
        return competencyName;
    }

    public List<String> getRegexs() {
        if (regexList == null) {
            return new ArrayList<>();
        }
        return regexList.stream().map(RegexInfo::getRegex).collect(Collectors.toList());
    }

    public double getWeightForRegex(String regexToFind) {
        if (regexList == null) {
            // no weights specified, default value
            return 0;
        }

        List<RegexInfo> regexInfo = regexList.stream().filter(r -> r.getRegex().equals(regexToFind)).toList();
        if (!regexInfo.isEmpty() && regexInfo.get(0).getWeight() != 0) {
            return regexInfo.get(0).getWeight();
        } else {
            // no weights specified, default value
            return 0;
        }
    }

    public double getErrorWeightForRegex(String regexToFind) {
        if (regexList == null) {
            // no error weight specified for this regex, default value
            return getWeightForRegex(regexToFind) / 2;
        }

        List<RegexInfo> regexInfo = regexList.stream().filter(r -> r.getRegex().equals(regexToFind)).toList();
        if (!regexInfo.isEmpty() && regexInfo.get(0).getErrorWeight() != 0) {
            return regexInfo.get(0).getErrorWeight();
        } else {
            // no error weight specified for this regex, default value
            return getWeightForRegex(regexToFind) / 2;
        }
    }

    public double getNumberOfPossibleGainForRegex(String regexToFind) {
        if (regexList == null) {
            // no nb of possible gains specified, default value
            return 1;
        }

        List<RegexInfo> regexInfo = regexList.stream().filter(r -> r.getRegex().equals(regexToFind)).toList();
        if (!regexInfo.isEmpty() && regexInfo.get(0).getNbOfPossibleGain() != 0) {
            return regexInfo.get(0).getNbOfPossibleGain();
        } else {
            // no nb of possible gains specified, default value
            return 1;
        }
    }

    public double getNumberOfPossibleLossForRegex(String regexToFind) {
        if (regexList == null) {
            // no nb of possible loss specified, default value
            return 2;
        }

        List<RegexInfo> regexInfo = regexList.stream().filter(r -> r.getRegex().equals(regexToFind)).toList();
        if (!regexInfo.isEmpty() && regexInfo.get(0).getNbOfPossibleLoss() != 0) {
            return regexInfo.get(0).getNbOfPossibleLoss();
        } else {
            // no nb of possible loss specified, default value
            return 2;
        }
    }
}
