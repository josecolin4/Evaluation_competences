package src.evaluation;

import src.util.BashUtils;

public class Match {

    private String code;

    private boolean syntaxIsCorrect;

    public Match(String code) {
        this.code = code;
    }

    public void checkSyntax() {
        syntaxIsCorrect = BashUtils.parsingCheck(code);
    }

    public boolean isSyntaxCorrect() {
        return syntaxIsCorrect;
    }
}
