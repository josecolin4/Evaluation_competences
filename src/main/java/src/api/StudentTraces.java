package src.api;

import com.google.gson.annotations.Expose;
import src.json.Command;
import src.json.Script;

import java.util.ArrayList;
import java.util.List;

public class StudentTraces {

    @Expose
    private List<Script> scripts;

    @Expose
    private List<Command> commands;

    public StudentTraces(List<Script> scripts, List<Command> commands) {
        this.scripts = scripts;
        this.commands = commands;
    }

    public List<Script> getScripts() {
        if (scripts == null) {
            return new ArrayList<>();
        }

        return scripts;
    }

    public List<Command> getCommands() {
        if (commands == null) {
            return new ArrayList<>();
        }
        return commands;
    }
}
