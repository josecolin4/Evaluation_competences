package src.json;

import com.google.gson.annotations.Expose;

public class Command {

    @Expose
    private String command;

    @Expose
    private String args;

    public String getCode() {
        return command + " " + args;
    }
}
