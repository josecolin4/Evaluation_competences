package src.evaluation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Result of the evaluation on a single StudentData for a single competency
 */
public class EvaluationResult {

    /**
     * For each regex of this evaluation, the list of matches, in the form of the whole script or the
     * whole command that matched.
     */
    private HashMap<String, List<Match>> matchesForRegex = new HashMap<>();

    public void addMatchForRegex(String regex, String code) {
        List<Match> existentMatches = matchesForRegex.get(regex);
        if (existentMatches == null) {
            matchesForRegex.put(regex, new ArrayList<>(List.of(new Match(code))));
        } else {
            existentMatches.add(new Match(code));
        }
    }

    public void checkSyntax() {
        for (List<Match> allMatches : matchesForRegex.values()) {
            for (Match match : allMatches) {
                match.checkSyntax();
            }
        }
    }

    public HashMap<String, List<Match>> getMatchesForRegex() {
        return matchesForRegex;
    }
}
