package src;

import org.antlr.runtime.tree.Tree;
import src.parser.BashParser;

public class Main {

    public static void main(String[] args) {
        BashParser parser = new BashParser();
        Tree tree = parser.parse("for i in 1 2 3\n do\n echo i\n done");
        System.out.println(tree.toStringTree());
        for (int i = 0; i < tree.getChildCount(); i++) {
            System.out.println(" --- ");
            System.out.println(tree.getChild(i).toStringTree());
        }
    }
}
