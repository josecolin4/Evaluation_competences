package src.parser;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import src.libbash.java_libbashLexer;
import src.libbash.java_libbashParser;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

/**
 * Usage of libbash parser to parse bash commands and files.
 */
public class BashParser {

    /**
     * Types of node for building bash ast.
     */
    private static final HashMap<Integer, String> NODES = new HashMap<Integer, String>();

    static {
        // init all types of nodes
        for (Field f : java_libbashParser.class.getDeclaredFields()) {
            if (f.getType() == int.class) {
                int mod = f.getModifiers();
                if (Modifier.isPublic(mod) && Modifier.isFinal(mod) && Modifier.isStatic(mod)) {
                    try {
                        Integer i = (Integer) f.get(null);
                        NODES.put(i, f.getName());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public Tree parse(String code) {
        java_libbashLexer lexer = new java_libbashLexer(new ANTLRStringStream(code));
        try {
            return (Tree) (new java_libbashParser(new CommonTokenStream(lexer)).start().getTree());
        } catch (RecognitionException e) {
            System.out.println("Error while parsing : \n " + code);
            throw new RuntimeException(e);
        }
    }


}
