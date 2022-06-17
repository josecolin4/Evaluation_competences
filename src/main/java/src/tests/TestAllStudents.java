package src.tests;

import src.evaluation.Evaluation;
import src.evaluation.SimpleEvaluationRegex;
import src.json.StudentData;
import src.util.JsonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class TestAllStudents {

    public static void main(String[] args) {
        double minError = 0.2;

        List<String> toEvaluate = List.of(
                "Conna\u00c3\u00aetre_la_syntaxe_de_instruction_set"
                //"Conna\u00c3\u00aetre_les_variables_denvironnement",
                //"Conna\u00c3\u00aetre_les_variables_sp\u00c3\u00a9ciales",
                //"Afficher_le_manuel_dune_commande_Unix",
                //"Affecter_une_valeur_\u00c3\u00a0_une_variable"
        );

        for (int session = 1; session <= 4; session++) {
            System.out.println("\nsession n°" + session + "\n");

            HashMap<String, Double> totalError = new HashMap<>();
            HashMap<String, Integer> nbOFStudentAbove06 = new HashMap<>();
            for (String competency : toEvaluate) {
                totalError.put(competency, 0.0);
                nbOFStudentAbove06.put(competency, 0);
            }

            double nbProfileTested = 1;

            List<StudentData> studentDataList = JsonUtils.getAllStudentData(session);
            for (StudentData data : studentDataList) {

                // keep only competencies to evaluate
                data.setProfile(data.getProfile().stream().filter(studentCompetency -> toEvaluate.contains(studentCompetency.getName())).collect(Collectors.toList()));

                Evaluation simpleEvaluation = new SimpleEvaluationRegex();
                HashMap<String, Double> generatedProfile = simpleEvaluation.evaluate(data, toEvaluate, false);    // TODO generate profile
                HashMap<String, Double> correctProfile = data.getHashMapProfile();

                try {
                    HashMap<String, Double> error = ResultAnalyser.mesureError(generatedProfile, correctProfile, false);
                    for (String competency : error.keySet()) {
                        totalError.put(competency, totalError.get(competency) + error.get(competency));
                        if (error.get(competency) >= 0.6) {
                            nbOFStudentAbove06.put(competency, nbOFStudentAbove06.get(competency) + 1);
                        }

                        if (error.get(competency) > minError) {
                            System.out.println(data.getName() + " : session " + session + " : " + competency + " : " + error.get(competency));
                        }
                    }
                    nbProfileTested++;
                } catch (IllegalStateException e) {
                    // skip
                    System.out.println(data.getName() + " for session " + session + " didn't had a proper profile to compare to");
                }
            }

            System.out.println("\naverage error at session " + session + " :");
            for (String competency : toEvaluate) {
                System.out.println(competency + " : " + totalError.get(competency) / nbProfileTested);
            }

            System.out.println("\nnb of big errors ( >= 0.6 ) : ");
            for (String competency : toEvaluate) {
                System.out.println(competency + " : " + nbOFStudentAbove06.get(competency));
            }

        }
    }
}
