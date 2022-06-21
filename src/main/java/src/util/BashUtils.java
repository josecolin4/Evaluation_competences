package src.util;

import java.io.*;

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
}
