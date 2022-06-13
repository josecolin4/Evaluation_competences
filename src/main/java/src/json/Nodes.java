package src.json;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Nodes {

    @Expose
    private List<BashNode> nodes;

    public Nodes(List<BashNode> nodes) {
        this.nodes = nodes;
    }
}
