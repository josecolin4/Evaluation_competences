package src.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BashUtils {

    /**
     * Usage of the bash command
     *  bash -n
     * to check the syntax of a script or command
     * @param code
     * @return
     */
    public static boolean isCommandSyntaxCorrect(String code) {
        StringBuffer output = new StringBuffer();
        Process p;
        try {
            p = Runtime.getRuntime().exec(code);
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}
