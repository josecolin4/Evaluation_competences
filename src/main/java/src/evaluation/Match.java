package src.evaluation;

import src.util.BashUtils;

public class Match {

    private String code;

    private boolean syntaxIsCorrect;

    public Match(String code) {
        this.code = code;
    }

    public void checkSyntax() {
        syntaxIsCorrect = BashUtils.isCommandSyntaxCorrect(code);
    }

    public boolean isSyntaxCorrect() {
        return syntaxIsCorrect;
    }
}
