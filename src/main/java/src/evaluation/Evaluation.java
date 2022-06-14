package src.evaluation;

import org.antlr.runtime.tree.Tree;
import src.json.Command;
import src.json.StudentData;
import src.parser.BashParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Evaluation {

    public abstract HashMap<String, Double> evaluate(StudentData data, List<String> competenciesToEvaluate, boolean printWhenFound);

    public abstract HashMap<String, Double> evaluate(StudentData data);

    public List<Tree> getAllAST(StudentData data) {
        List<Tree> astList = new ArrayList<>();
        for (List<String> script : data.getScripts()) {

            // reformat the script to be a single string with \n
            String scriptOneLine = "";
            for (String line : script) {
                scriptOneLine += line;
            }

            astList.add(BashParser.parse(scriptOneLine));
        }

        for (Command command : data.getCommands()) {
            astList.add(BashParser.parse(command.getCommand()));
        }
        return astList;
    }
}
