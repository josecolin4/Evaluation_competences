package src.json;

import com.google.gson.annotations.Expose;

public class Command {

    @Expose
    private String command;

    @Expose
    private String args;

    @Expose
    private int score;

    public String getCommand() {
        return command + " " + args;
    }

    public boolean getScore() {
        return score == 1;
    }
}
