package src;

import org.antlr.runtime.tree.Tree;
import src.parser.BashParser;

public class Main {

    public static void main(String[] args) {
        BashParser parser = new BashParser();
        // Tree tree = parser.parse("for i in 1 2 3\n do\n echo i\n done");
        Tree tree = parser.parse("set 1 2 3");
        visit(tree, 0);
        System.out.println(BashParser.NODES_TYPES);
    }

    public static void visit(Tree tree, int depth){
        for (int i = 0; i < tree.getChildCount(); i++) {
            for (int j = 0; j < depth; j++) System.out.print("  ");
            System.out.println("--> " + tree.getChild(i) + " " + BashParser.NODES_TYPES.get(tree.getChild(i).getType()));
            visit(tree.getChild(i), depth + 1);
        }
    }
}
