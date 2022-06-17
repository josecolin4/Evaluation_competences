package src.evaluation;

public class Match {

    private String code;

    private boolean syntaxIsCorrect;

    public Match(String code) {
        this.code = code;
    }

    public void checkSyntax() {
        // TODO
        syntaxIsCorrect = true;
    }

    public boolean isSyntaxIsCorrect() {
        return syntaxIsCorrect;
    }
}
