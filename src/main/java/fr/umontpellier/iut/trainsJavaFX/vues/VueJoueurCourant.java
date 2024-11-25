package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.GestionJeu;
import fr.umontpellier.iut.trainsJavaFX.IJeu;
import fr.umontpellier.iut.trainsJavaFX.mecanique.Jeu;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

import static fr.umontpellier.iut.trainsJavaFX.GestionJeu.getJeu;


/**
 * Cette classe présente les éléments appartenant au joueur courant.
 * <p>
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueJoueurCourant extends VBox {

    @FXML
    private Label score;

    @FXML
    private Label argent;

    @FXML
    private Label nbJetonsRail;

    @FXML
    private Label pointsRail;

    @FXML
    private Label nomJoueur;

    @FXML
    private HBox HBoxLabelJoueur;

    public VueJoueurCourant() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/vueJoueurCourant.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createBindings(){
        GestionJeu.getJeu().joueurCourantProperty().addListener((observable, oldValue, newValue) -> {
                score.textProperty().bind(newValue.scoreProperty().asString());
                argent.textProperty().bind(newValue.argentProperty().asString());
                nbJetonsRail.textProperty().bind(newValue.nbJetonsRailsProperty().asString());
                pointsRail.textProperty().bind(newValue.pointsRailsProperty().asString());

                //mettre la couleur du background du joueur courant
                HBoxLabelJoueur.setStyle("-fx-background-color: " + CouleursJoueurs.couleursBackgroundJoueur.get(newValue.getCouleur()) + ";");
                //nomJoueur.setStyle("-fx-background-color: " + CouleursJoueurs.couleursBackgroundJoueur.get(newValue.getCouleur()) + ";");

        });
        GestionJeu.getJeu().joueurCourantProperty().addListener((source, ancienneValeur, nouvelleValeur) ->
                nomJoueur.setText(nouvelleValeur.getNom()));

    }

    public int trouverCourant() {
        for (int i = 0; i < GestionJeu.getJeu().getJoueurs().size(); i++) {
            if (GestionJeu.getJeu().getJoueurs().get(i).getNom().equals(GestionJeu.getJeu().joueurCourantProperty().getName())) {
                return i;
            }
        }
        return -1;
    }

}
