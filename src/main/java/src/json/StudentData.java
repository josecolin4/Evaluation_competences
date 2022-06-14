package src.json;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudentData {

    @Expose
    private String name;

    @Expose
    private int session;

    @Expose
    private List<List<String>> scripts;

    @Expose
    private List<StudentCompetency> profile;

    @Expose
    private List<Command> commands;

    public String getName() {
        return name;
    }

    public int getSession() {
        return session;
    }

    public List<List<String>> getScripts() {
        if (scripts == null) {
            return new ArrayList<>();
        }
        return scripts;
    }

    public List<StudentCompetency> getProfile() {
        if (profile == null) {
            return new ArrayList<>();
        }

        return profile;
    }

    public HashMap<String, Double> getHashMapProfile() {
        return getHashMapProfile(profile);
    }

    public static HashMap<String, Double> getHashMapProfile(List<StudentCompetency> profile) {
        HashMap<String, Double> hashMapProfile = new HashMap<>();
        for (StudentCompetency competency : profile) {
            hashMapProfile.put(competency.getName(), competency.getMastery());
        }
        return hashMapProfile;
    }

    public List<Command> getCommands() {
        if (commands == null) {
            return new ArrayList<>();
        }
        return commands;
    }

    public void setProfile(List<StudentCompetency> profile) {
        this.profile = profile;
    }
}
