package src;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.Tree;
import src.parser.BashParser;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        BashParser parser = new BashParser();
        // Tree tree = parser.parse("for i in 1 2 3\n do\n echo i\n done");
        Tree tree = parser.parse("set 1 2 3");
        visit(tree, 0);
        // System.out.println(BashParser.NODES_TYPES);

//        Pattern pattern = Pattern.compile("set .+");
//        Matcher matcher = pattern.matcher("set");
//        System.out.println("match : " + matcher.matches());
    }

    public static void visit(Tree tree, int depth){
        for (int i = 0; i < tree.getChildCount(); i++) {
            for (int j = 0; j < depth; j++) System.out.print("  ");
            System.out.println("--> " + tree.getChild(i) + " " + BashParser.NODES_TYPES.get(tree.getChild(i).getType()));
            visit(tree.getChild(i), depth + 1);
        }
    }
}
