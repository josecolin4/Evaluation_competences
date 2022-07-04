package src.evaluation.utils;

import src.evaluation.EvaluationResult;
import src.json.Command;
import src.json.StudentData;
import src.util.BashUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

    public static EvaluationResult searchForAllMatches(List<String> allRegex, StudentData data, boolean printWhenFound) {
        EvaluationResult result = new EvaluationResult();
        result.init(allRegex);
        for (String regex : allRegex) {

            for (List<String> script : data.getScripts()) {
                String code = "";
                for (String line : script) {
                    code += line;
                }
                if (search(regex, code, printWhenFound)) {
                    result.addMatchForRegex(regex, code);
                }
            }

            for (Command command : data.getCommands()) {
                if (search(regex, command.getCommand(), printWhenFound)) {
                    result.addMatchForRegex(regex, command.getCommand());
                }
            }
        }

        result.checkSyntax();
        return result;
    }

    public static boolean search(String regex, String code, boolean printWhenFound) {
        code = BashUtils.removeUselessWhiteSpace(code);

        // remove \n to avoid problems with matcher
        code = code.replace("\n", ";");

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(code);

        if (matcher.matches()) {
            if (printWhenFound) {
                System.out.println(regex + "matched with command : " + code);
            }
            return true;
        } else {
            return false;
        }
    }
}
