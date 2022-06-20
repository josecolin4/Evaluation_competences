package src.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class BashUtils {

    /**
     * Usage of the bash command
     * bash -n
     * to check the syntax of a script or command
     *
     * @param code
     * @return
     */
    public synchronized static boolean isCommandSyntaxCorrect(String code) {
        File file = new File("test-env/flag");
        file.delete();

        ProcessBuilder builder = new ProcessBuilder("bash.exe");
        builder.directory(new File("test-env"));

        Process p = null;
        try {
            p = builder.start();

            BufferedWriter p_stdin =
                    new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));

            // clear output file
            p_stdin.write("> output;");
            p_stdin.newLine();

            // rewrite the script in working directory
            p_stdin.write("echo \"" + code + "\"" + " > script.sh;");
            p_stdin.newLine();

            p_stdin.write("bash -n script.sh &> output;");
            p_stdin.newLine();

            p_stdin.write("> flag;");
            p_stdin.newLine();

            p_stdin.write("exit;");
            p_stdin.newLine();

            p_stdin.write("exit");
            p_stdin.newLine();
            p_stdin.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


        // check validity of the script (check if output of bash -n is empty)
        try {
            // wait for the process to finish printing the output
            file = new File("test-env/flag");
            while (!file.exists());
            p.destroy();

            BufferedReader reader = new BufferedReader(new FileReader("test-env/output"));
            return reader.readLine() == null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
