package src.json;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class RegexForCompetency {

    @Expose
    private String name;

    @Expose
    private List<String> regex;

    public String getName() {
        return name;
    }

    public List<String> getRegex() {
        if (regex == null) {
            return new ArrayList<>();
        }
        return regex;
    }
}
