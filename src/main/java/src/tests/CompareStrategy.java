package src.tests;

import src.evaluation.Evaluation;
import src.evaluation.EvaluationRegex;
import src.evaluation.rating.RatingStrategy;
import src.evaluation.rating.SimpleRating;
import src.evaluation.rating.SyntaxRating;
import src.evaluation.rating.SyntaxWithErrorRating;
import src.json.StudentData;
import src.util.JsonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CompareStrategy {

    public static void main(String[] args) {
        List<RatingStrategy> strategies = List.of(new SyntaxRating(), new SimpleRating(), new SyntaxWithErrorRating());
        List<String> toEvaluate = List.of(
                //"Banaliser_un_caract\u00c3\u00a8re"
                //"Conna\u00c3\u00aetre_la_syntaxe_IfThenElifElse"
                //"Conna\u00c3\u00aetre_la_syntaxe_de_instruction_while"
                //"Conna\u00c3\u00aetre_la_syntaxe_de_instruction_for"
                //"Conna\u00c3\u00aetre_la_syntaxe_de_instruction_set"
                "Conna\u00c3\u00aetre_les_variables_denvironnement"
                //"Conna\u00c3\u00aetre_les_variables_sp\u00c3\u00a9ciales"
                //"Afficher_le_contenu_de_un_fichier_Unix"
                //"Afficher_le_manuel_dune_commande_Unix",
                //"Interpr\u00c3\u00a9ter_de_fa\u00c3\u00a7on_num\u00c3\u00a9rique_la_valeur_dune_variable"
                //"Tester_l\u00c3\u00a9tat_dun_fichier"
                //"Affecter_une_valeur_\u00c3\u00a0_une_variable"
                //"Cr\u00c3\u00a9er_un_fichier_Unix"
                //"Modifier_les_droits_dun_fichier_Unix"
        );

        for (int session = 1; session <= 4; session++) {
            for (RatingStrategy strategy : strategies) {
                HashMap<String, Double> totalError = new HashMap<>();

                for (String competency : toEvaluate) {
                    totalError.put(competency, 0.0);
                }

                double nbProfileTested = 1;

                List<StudentData> studentDataList = JsonUtils.getAllStudentData(session);
                for (StudentData data : studentDataList) {

                    // keep only competencies to evaluate
                    data.setProfile(data.getProfile().stream().filter(studentCompetency -> toEvaluate.contains(studentCompetency.getName())).collect(Collectors.toList()));

                    Evaluation simpleEvaluation = new EvaluationRegex();
                    HashMap<String, Double> generatedProfile = simpleEvaluation.evaluate(data, toEvaluate, false, strategy);
                    HashMap<String, Double> correctProfile = data.getHashMapProfile();

                    try {
                        HashMap<String, Double> error = ResultAnalyser.mesureError(generatedProfile, correctProfile, false);
                        for (String competency : error.keySet()) {
                            totalError.put(competency, totalError.get(competency) + error.get(competency));
                        }
                        nbProfileTested++;
                    } catch (IllegalStateException e) {
                        // skip
                        System.out.println(data.getName() + " for session " + session + " didn't had a proper profile to compare to");
                    }
                }

                System.out.println("\naverage error at session " + session + " with strategy " + strategy.toString() + " :");
                for (String competency : toEvaluate) {
                    System.out.println(competency + " : " + totalError.get(competency) / nbProfileTested);
                }
            }
        }
    }
}
