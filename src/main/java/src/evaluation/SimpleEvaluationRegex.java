package src.evaluation;

import src.json.Command;
import src.json.StudentData;
import src.util.JsonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Works like SimpleEvaluation but using regex on the command and scripts text instead of an AST.
 */
public class SimpleEvaluationRegex extends Evaluation {

    @Override
    public HashMap<String, Double> evaluate(StudentData data, List<String> competenciesToEvaluate, boolean printWhenFound) {
        HashMap<String, Double> profile = new HashMap<>();

        // init at 0
        for (String competency : competenciesToEvaluate) {
            profile.put(competency, 0.0);
        }

        HashMap<String, List<String>> regexList = JsonUtils.getRegexForCompetenciesFromJson().getAllRegex();

        for (String competency : competenciesToEvaluate) {
            if (regexList.containsKey(competency)) {
                for (String regex : regexList.get(competency)) {

                    // TODO implement a better strategy
                    int occurence = 0;
                    for (List<String> script : data.getScripts()) {

                        for (String line : script) {

                            occurence += search(regex, line, printWhenFound) ? 1 : 0;
                        }
                    }

                    for (Command command : data.getCommands()) {
                        occurence += search(regex, command.getCommand(), printWhenFound) && command.getScore() ? 1 : 0;
                    }

                    // TODO find better measurement
                    if (occurence == 1) {
                        profile.put("Conna\u00c3\u00aetre_la_syntaxe_de_instruction_set", 0.5);
                    } else if (occurence > 1) {
                        profile.put("Conna\u00c3\u00aetre_la_syntaxe_de_instruction_set", 1.0);
                    }
                }
            }
        }

        return profile;
    }

    @Override
    public HashMap<String, Double> evaluate(StudentData data) {
        return null;
    }

    public boolean search(String regex, String codeLine, boolean printWhenFound) {
        // remove \n to avoid problems with matcher
        codeLine = codeLine.replace("\n", "");

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(codeLine);

        if (matcher.matches()) {
            if (printWhenFound) {
                System.out.println(codeLine);
            }
            return true;
        } else {
            return false;
        }
        //return matcher.matches() ? 1 : 0;  // we count only one occurrence per lign of code
    }
}
