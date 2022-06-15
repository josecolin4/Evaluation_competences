package src.json;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Script {

    @Expose
    private List<String> lines;

    @Expose
    private String name;

    public List<String> getLines() {
        return lines;
    }
}
