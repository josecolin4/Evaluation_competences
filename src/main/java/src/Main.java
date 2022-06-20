package src;

import org.antlr.runtime.tree.Tree;
import src.parser.BashParser;
import src.util.BashUtils;

public class Main {

    public static void main(String[] args) {
//        BashParser parser = new BashParser();
//        Tree tree = parser.parse("if condition\n then; echo bonjour; fi");
//        visit(tree, 0);

//        Pattern pattern = Pattern.compile("(.*;)?set(;.*)?");
//        Matcher matcher = pattern.matcher("ihrgihdorghdrgd;set;efsefs");
//        System.out.println("match : " + matcher.matches());

        System.out.println(BashUtils.isCommandSyntaxCorrect("echo oui\nls -l\necho voila\nif fi") ? "oui" : "non");
    }

    public static void visit(Tree tree, int depth){
        for (int i = 0; i < tree.getChildCount(); i++) {
            for (int j = 0; j < depth; j++) System.out.print("  ");
            System.out.println("--> " + tree.getChild(i) + " " + BashParser.NODES_TYPES.get(tree.getChild(i).getType()));
            visit(tree.getChild(i), depth + 1);
        }
    }
}
