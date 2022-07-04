package src.evaluation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Result of the evaluation on a single StudentData for a single competency
 */
public class EvaluationResult {

    /**
     * For each regex of this evaluation, the list of matches, in the form of the whole script or the
     * whole command that matched.
     */
    private HashMap<String, List<Match>> matchesForRegex = new HashMap<>();

    /**
     * Init the number of matches at 0 for all regex
     */
    public void init(List<String> allRegex) {
        for (String regex : allRegex) {
            matchesForRegex.put(regex, new ArrayList<>());
        }
    }

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

    /**
     * Return a list of size 2 * nb_of_regex
     * first half of the list contains the nb of correct matches for each regex
     * second half of the list contains the nb of incorrect matches for each regex
     * @return
     */
    public List<Integer> getFormatedRegexMatchInfo() {
        List<Integer> result_ok = new ArrayList<>();
        List<Integer> result_ko = new ArrayList<>();

        for (Map.Entry<String, List<Match>> entry : matchesForRegex.entrySet()) {
            int nbCorrect = 0;
            int nbIncorrect = 0;
            for (Match match : entry.getValue()) {
                if (match.isSyntaxCorrect()) {
                    nbCorrect++;
                } else {
                    nbIncorrect++;
                }
            }

            result_ok.add(nbCorrect);
            result_ko.add(nbIncorrect);
        }

        result_ok.addAll(result_ko);
        return result_ok;
    }
}
