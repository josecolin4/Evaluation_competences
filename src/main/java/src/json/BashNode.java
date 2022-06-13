package src.json;

import com.google.gson.annotations.Expose;

import java.util.List;

public class BashNode {

    @Expose
    private String name;

    @Expose
    private List<String> associatedCompetency;

    public BashNode(String name) {
        this.name = name;
    }
}
