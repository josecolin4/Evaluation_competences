package src.util;

import java.io.*;

public class BashUtils {

    /**
     * Usage of the bash command
     *  bash -n
     * to check the syntax of a script or command
     * @param code
     * @return
     */
    public static boolean isCommandSyntaxCorrect(String code) {
        ProcessBuilder builder = new ProcessBuilder( "bash.exe");
        Process p = null;
        try {
            p = builder.start();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        BufferedWriter p_stdin =
                new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));

        try {
            p_stdin.write(code);
            p_stdin.newLine();
            p_stdin.flush();
        }
        catch (IOException e) {
            System.out.println(e);
        }


        StringBuffer output = new StringBuffer();
        try {
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while (reader.ready() && line != null) {
                line = reader.readLine();
                output.append(line).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // finally close the shell by execution exit command
        try {
            p_stdin.write("exit");
            p_stdin.newLine();
            p_stdin.flush();
        }
        catch (IOException e) {
            System.out.println(e);
        }

        // DEBUG
        System.out.println(output);

        return true;
    }
}
