package src.util;

import org.antlr.runtime.tree.CommonErrorNode;
import org.antlr.runtime.tree.Tree;
import src.parser.BashParser;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BashUtils {

    private static Process bashProcess;
    private static BufferedWriter bashStdin;

    static {
        ProcessBuilder builder = new ProcessBuilder("bash.exe");
        builder.directory(new File("test-env"));

        try {
            bashProcess = builder.start();
            bashStdin = new BufferedWriter(new OutputStreamWriter(bashProcess.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isCommandSyntaxCorrect(String code) {
        return syntaxCheck(code).equals("");
    }

    /**
     * Usage of the bash command
     * bash -n
     * to check the syntax of a script or command
     *
     * @param code
     * @return return value of bash -n
     */
    public static String syntaxCheck(String code) {
        // clear output file
        File fileOutput = new File("test-env/output");
        fileOutput.delete();

        try {
            // rewrite the script in working directory
            File fileScript = new File("test-env/script.sh");
            fileScript.delete();
            fileScript.createNewFile();
            FileWriter fileWriterScript = new FileWriter("test-env/script.sh");
            fileWriterScript.write(code);
            fileWriterScript.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            bashStdin.write("bash -n script.sh &> output;");
            bashStdin.newLine();
            bashStdin.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // wait for the process to finish printing the output
            while (!fileOutput.exists());

            BufferedReader reader = new BufferedReader(new FileReader("test-env/output"));
            StringBuilder result = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                result.append(line);
                line = reader.readLine();
            }

            return result.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean parsingCheck(String code) {
        // remove empty lines
        code = code.replaceAll("(?m)^[ \t]*\r?\n", "");

        // check if contains error node
        return !searchForError(BashParser.parse(code));
    }

    public static boolean searchForError(Tree tree) {
        if (tree instanceof CommonErrorNode) {
            return true;
        }

        boolean found = false;
        for (int i = 0; i < tree.getChildCount(); i++) {
            found = found || searchForError(tree.getChild(i));
        }
        return found;
    }

    /**
     * Format the code to remove useless white spaces :
     *      - double space
     *      - tabulation and spaces at start and end of line
     * @param code
     * @return
     */
    public static String removeUselessWhiteSpace(String code) {
        code = code.replaceAll("  +", " "); // remove extra spaces
        code = code.replaceAll("\n[ \t]+", "\n"); // clear start of line
        code = code.replaceAll("[ \t]+\n", "\n"); // clear end of line
        return code;
    }
}
