package src.tests;

import src.evaluation.Evaluation;
import src.evaluation.EvaluationRegex;
import src.evaluation.rating.SyntaxRating;
import src.evaluation.rating.SyntaxWithErrorRating;
import src.json.StudentData;
import src.util.JsonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class TestAllStudents {

    public static void main(String[] args) {
        double minError = 0.2;

        List<String> toEvaluate = List.of(
                //"Banaliser_un_caract\u00c3\u00a8re"
                //"Conna\u00c3\u00aetre_la_syntaxe_IfThenElifElse"
                //"Conna\u00c3\u00aetre_la_syntaxe_de_instruction_while"
                //"Conna\u00c3\u00aetre_la_syntaxe_de_instruction_for"
                //"Conna\u00c3\u00aetre_la_syntaxe_de_instruction_set"
                //"Conna\u00c3\u00aetre_les_variables_denvironnement"
                //"Conna\u00c3\u00aetre_les_variables_sp\u00c3\u00a9ciales"
                //"Obtenir_la_valeur_affect\u00c3\u00a9e_\u00c3\u00a0_une_variable"
                //"Modifier_la_valeur_de_une_variable"
                //"Afficher_la_valeur_dune_variable"
                "Conna\u00c3\u00aetre_la_notion_de_variable"
                //"R\u00c3\u00a9cup\u00c3\u00a9rer_des_donn\u00c3\u00a9es_saisies_par_lutilisateur"
                //"Conna\u00c3\u00aetre_la_notion_de_redirection_entr\u00c3\u00a9e_et_sortie"
                //"Afficher_le_contenu_de_un_fichier_Unix"
                //"Afficher_le_manuel_dune_commande_Unix",
                //"Supprimer_un_fichier_Unix"
                //"Renommer_un_fichier_Unix"
                //"Modifier_les_droits_dun_fichier_Unix"
                //"Conna\u00c3\u00aetre_la_syntaxe_dollar(cmd)"
                //"Conna\u00c3\u00aetre_le_caract\u00c3\u00a8re_de_substitution_de_commande"
                //"Interpr\u00c3\u00a9ter_de_fa\u00c3\u00a7on_num\u00c3\u00a9rique_la_valeur_dune_variable"
                //"Tester_l\u00c3\u00a9tat_dun_fichier"
                //"Affecter_une_valeur_\u00c3\u00a0_une_variable"
                //"Cr\u00c3\u00a9er_un_fichier_Unix"
                //"Modifier_les_droits_dun_fichier_Unix"
                //"Conna\u00c3\u00aetre_les_op\u00c3\u00a9rateurs_logiques"
                //"Conna\u00c3\u00aetre_les_op\u00c3\u00a9rateurs_de_comparaison_num\u00c3\u00a9rique"
                //"Conna\u00c3\u00aetre_les_op\u00c3\u00a9rateurs_de_comparaison_de_caract\u00c3\u00a8res"
                //"Conna\u00c3\u00aetre_la_syntaxe_de_instruction_seq"
        );

        for (int session = 1; session <= 4; session++) {
            System.out.println("\nsession n°" + session + "\n");

            HashMap<String, Double> totalError = new HashMap<>();
            HashMap<String, Integer> nbOFStudentAboveMinError = new HashMap<>();
            for (String competency : toEvaluate) {
                totalError.put(competency, 0.0);
                nbOFStudentAboveMinError.put(competency, 0);
            }

            double nbProfileTested = 1;

            List<StudentData> studentDataList = JsonUtils.getAllStudentData(session);
            for (StudentData data : studentDataList) {

                // keep only competencies to evaluate
                data.setProfile(data.getProfile().stream().filter(studentCompetency -> toEvaluate.contains(studentCompetency.getName())).collect(Collectors.toList()));

                Evaluation simpleEvaluation = new EvaluationRegex();
                HashMap<String, Double> generatedProfile = simpleEvaluation.evaluate(data, toEvaluate, false, new SyntaxWithErrorRating());
                HashMap<String, Double> correctProfile = data.getHashMapProfile();

                try {
                    HashMap<String, Double> error = ResultAnalyser.mesureError(generatedProfile, correctProfile, false);
                    for (String competency : error.keySet()) {
                        totalError.put(competency, totalError.get(competency) + error.get(competency));

                        if (error.get(competency) > minError) {
                            nbOFStudentAboveMinError.put(competency, nbOFStudentAboveMinError.get(competency) + 1);
                            System.out.println(data.getName() + " : session " + session + " : " + competency + " : " + error.get(competency));
                        }
                    }
                    nbProfileTested++;
                } catch (IllegalStateException e) {
                    // skip
                    System.out.println(data.getName() + " for session " + session
                            + " didn't had a proper profile to compare to : " + e.getMessage());
                }
            }

            System.out.println("\naverage error at session " + session + " :");
            for (String competency : toEvaluate) {
                System.out.println(competency + " : " + totalError.get(competency) / nbProfileTested);
            }

            System.out.println("\nnb of big errors ( >= " + minError + " ) : ");
            for (String competency : toEvaluate) {
                System.out.println(competency + " : " + nbOFStudentAboveMinError.get(competency));
            }

        }
    }
}
