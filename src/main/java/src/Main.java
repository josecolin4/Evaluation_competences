package src;

import org.antlr.runtime.tree.Tree;
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
        Pattern pattern = Pattern.compile(".*expr [0-9\\$]+ [\\+-/\\\\*] [0-9\\$]+.*");
        Matcher matcher = pattern.matcher(code);
        System.out.println("match : " + matcher.matches());

        //System.out.println(BashUtils.isCommandSyntaxCorrect("echo oui\nls -l\necho voila\nif fi") ? "oui" : "non");
        //System.out.println(BashUtils.isCommandSyntaxCorrect("#!/bin/bash\nset -- $(ls -l tp5bis_exo3.sh)\necho $5\n") ? "oui" : "non");
    }

    public static void visit(Tree tree, int depth){
        for (int i = 0; i < tree.getChildCount(); i++) {
            for (int j = 0; j < depth; j++) System.out.print("  ");
            System.out.println("--> " + tree.getChild(i) + " " + BashParser.NODES_TYPES.get(tree.getChild(i).getType()));
            visit(tree.getChild(i), depth + 1);
        }
    }
}
