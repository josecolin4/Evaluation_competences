package src.tests;

import src.json.StudentData;
import src.util.BashUtils;
import src.util.JsonUtils;

import java.util.List;

/**
 * Look at all the scripts to look for syntax errors
 */
public class TestSyntaxAllStudents {

    public static void main(String[] args) {
        for (int session = 1; session <= 4; session++) {
            List<StudentData> studentDataList = JsonUtils.getAllStudentData(session);
            for (StudentData data : studentDataList) {
                for (List<String> script : data.getScripts()) {
                    String code = "";
                    for (String line : script) {
                        code += line;
                    }

                    String result = BashUtils.syntaxCheck(code);
                    if (!result.equals("")) {
                        System.out.println("syntax incorrecte pour : " + data.getName() + "\n\n" + code + "\n" + result + "\n");
                    }
                }
            }
        }
    }
}
