package src.evaluation.utils;

import src.evaluation.EvaluationResult;
import src.api.StudentTraces;
import src.json.Command;
import src.json.Script;
import src.util.BashUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

    /**
     * Parse through the traces to look for match for all the regex.
     */
    public static EvaluationResult searchForAllMatches(List<String> allRegex, StudentTraces traces) {
        EvaluationResult result = new EvaluationResult();
        result.init(allRegex);
        for (String regex : allRegex) {
            for (Script script : traces.getScripts()) {
                if (search(regex, script.getCode())) {
                    result.addMatchForRegex(regex, script.getCode());
                }
            }
            for (Command command : traces.getCommands()) {
                if (search(regex, command.getCode())) {
                    result.addMatchForRegex(regex, command.getCode());
                }
            }
        }

        result.checkSyntax();
        return result;
    }

    public static boolean search(String regex, String code) {
        code = BashUtils.removeUselessWhiteSpace(code);

        // change \n to ; to have a single type of line separator
        code = code.replace("\n", ";");

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(code);

        return matcher.matches();
    }
}
