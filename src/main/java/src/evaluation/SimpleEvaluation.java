package src.evaluation;

import org.antlr.runtime.tree.Tree;
import src.json.StudentData;

import java.util.HashMap;
import java.util.List;

/**
 * Simple evaluation method.
 * Works by associating nodes in the ast with specific competencies.
 * Go through the scrips and commands written by students and check for those nodes.
 * If a node is found and the competency associated with it is a knowledge, then we increase the mastery in that competency.
 * If it is a skill, then we check if the command or the script runs correctly, and then
 * we increase the mastery in that competency
 */
public class SimpleEvaluation extends Evaluation {

    @Override
    public HashMap<String, Double> evaluate(StudentData data, List<String> competenciesToEvaluate, boolean printWhenFound) {
        HashMap<String, Double> profile = new HashMap<>();

        // init at 0
        for (String competency : competenciesToEvaluate) {
            profile.put(competency, 0.0);
        }

        // get the AST for all the commands and scripts
        List<Tree> astList = getAllAST(data);

        // TODO loop through all competencies and add them to the profile
        for (String competency : competenciesToEvaluate) {

            // TODO generalise to all competencies
            if (competency.equals("Conna\u00c3\u00aetre_la_syntaxe_de_instruction_set")) {
                // in that case, we are only interested by the keyword set

                int occurence = 0;
                // TODO find better measurement
                for (Tree ast : astList) {
                    occurence += searchForName(ast, "set");
                }

                if (occurence == 1) {
                    profile.put("Conna\u00c3\u00aetre_la_syntaxe_de_instruction_set", 0.5);
                } else if (occurence > 1) {
                    profile.put("Conna\u00c3\u00aetre_la_syntaxe_de_instruction_set", 1.0);
                }
            }
        }

        return profile;
    }

    public static int searchForName(Tree tree, String target) {
        int occurence = 0;

        for (int i = 0; i < tree.getChildCount(); i++) {
            if (tree.getType() == 118) {
                // 118 is type of NAME

                if (tree.getText().equals(target)) {
                    occurence++;
                }
            }

            occurence += searchForName(tree.getChild(i), target);
        }
        return occurence;
    }

    @Override
    public HashMap<String, Double> evaluate(StudentData data) {
        return null;
    }
}
