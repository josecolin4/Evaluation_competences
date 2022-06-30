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
                "if test -r -a -w $1\n" +
                "then\n" +
                "\techo Le fichier $1 existe et est accessible en lecture et écriture.\n" +
                "else\n" +
                "\techo Le fichier $1 n\\'existe pas,o\\ù n est pas accessible en lecture,o\\ù n est pas \n" +
                "fi\n";
        //System.out.println(BashUtils.parsingCheck(code) ? "pas d'erreur" : "erreur");
        visit(BashParser.parse(code), 0);
//        Pattern pattern = Pattern.compile("[a-zA-Z0-9_]+");
//        Matcher matcher = pattern.matcher("");
//        System.out.println("match : " + matcher.matches());
//        System.out.println(EvaluationRegex.search(".*\\[ .* [0-9\\$].* -((eq)|(ne)|(gt)|(lt)|(ge)|(le)) [0-9\\$].* .* \\].+",
//                "if [ $1 -gt 5 -a $1 -lt 10 ]\n",
//                true));

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
