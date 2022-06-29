package src;

import org.antlr.runtime.tree.Tree;
import src.evaluation.EvaluationRegex;
import src.parser.BashParser;
import src.util.BashUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        String code = "#!/bin/bash\n" +
        "echo Quelle est votre ann\u00c3\u00a9e de naissance?\n" +
                "read ans\n" +
                "echo Vous \u00c3\u00aates ne en $ans, vous avez donc `expr $(date +%Y) - $ans` ans\n";
        //System.out.println(BashUtils.parsingCheck(code) ? "pas d'erreur" : "erreur");
        //visit(BashParser.parse(code), 0);
//        Pattern pattern = Pattern.compile("[a-zA-Z0-9_]+");
//        Matcher matcher = pattern.matcher("");
//        System.out.println("match : " + matcher.matches());
        System.out.println(EvaluationRegex.search(".*\\[ .* [0-9\\$].* -((eq)|(ne)|(gt)|(lt)|(ge)|(le)) [0-9\\$].* .* \\].+",
                "if [ $1 -gt 5 -a $1 -lt 10 ]\n",
                true));

        //System.out.println(BashUtils.isCommandSyntaxCorrect("echo oui\nls -l\necho voila\nif fi") ? "oui" : "non");
        //System.out.println(BashUtils.isCommandSyntaxCorrect("#!/bin/bash\nset -- $(ls -l tp5bis_exo3.sh)\necho $5\n") ? "oui" : "non");

        // test
//        String test = " echo oui\t\n\t set  ls -l\t\n";
//        System.out.println(test);
//        System.out.println();
//        System.out.println(BashUtils.removeUselessWhiteSpace(test));
    }

    public static void visit(Tree tree, int depth){
        for (int i = 0; i < tree.getChildCount(); i++) {
            for (int j = 0; j < depth; j++) System.out.print("  ");
            System.out.println("--> " + tree.getChild(i) + " " + BashParser.NODES_TYPES.get(tree.getChild(i).getType()));
            visit(tree.getChild(i), depth + 1);
        }
    }
}
