package src.json;

import com.google.gson.annotations.Expose;

public class Command {

    @Expose
    private String command;

    @Expose
    private String args;

    public String getCommand() {
        return command + " " + args; // concat command name & args
    }
}
