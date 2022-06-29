package src.evaluation;

import src.evaluation.rating.RatingStrategy;
import src.json.Command;
import src.json.GlobalCompetency;
import src.json.RegexRule;
import src.json.StudentData;
import src.util.BashUtils;
import src.util.JsonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Works like SimpleEvaluation but using regex on the command and scripts text instead of an AST.
 */
public class EvaluationRegex extends Evaluation {

    @Override
    public HashMap<String, Double> evaluate(StudentData data, List<String> competenciesToEvaluate, boolean printWhenFound,
                                            RatingStrategy rating) {

        HashMap<String, Double> profile = new HashMap<>();

        // init at 0
        for (String competency : competenciesToEvaluate) {
            profile.put(competency, 0.0);
        }

        HashMap<String, RegexRule> regexList = JsonUtils.getRegexForCompetenciesFromJson().getAllRegex();

        for (String competency : competenciesToEvaluate) {
            if (regexList.containsKey(competency)) {

                EvaluationResult result = new EvaluationResult();
                for (String regex : regexList.get(competency).getRegexs()) {

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
                profile.put(competency, rating.rate(result, regexList.get(competency)));
            }
        }

        // now evaluate global competencies
        for (GlobalCompetency globalCompetency : JsonUtils.getRegexForCompetenciesFromJson().getGlobalCompetencies()) {
            if (competenciesToEvaluate.contains(globalCompetency.getName())) {
                profile.put(globalCompetency.getName(), globalCompetency.evaluate(profile));
            }
        }

        return profile;
    }

    @Override
    public HashMap<String, Double> evaluate(StudentData data) {
        return null;
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
