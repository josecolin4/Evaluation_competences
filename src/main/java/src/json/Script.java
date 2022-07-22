package src.json;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Script {

    @Expose
    private List<String> lines;

    @Expose
    private String name;

    public String getCode() {
        StringBuilder code = new StringBuilder();
        for (String line : lines) {
            code.append(line);
        }
        return code.toString();
    }

    public List<String> getLines() {
        return lines;
    }

    public String getName() {
        return name;
    }
}
