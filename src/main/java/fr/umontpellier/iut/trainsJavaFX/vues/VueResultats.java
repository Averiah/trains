package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.IJoueur;
import fr.umontpellier.iut.trainsJavaFX.TrainsIHM;
import fr.umontpellier.iut.trainsJavaFX.mecanique.Joueur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VueResultats extends BorderPane {

    private TrainsIHM ihm;

    @FXML
    private HBox vue1er;
    @FXML
    private HBox vue2eme;
    @FXML
    private HBox vue3eme;
    @FXML
    private HBox vue4eme;

    @FXML
    private Label nomJoueur1er;
    @FXML
    private Label scoreJoueur1er;
    @FXML
    private Label nomJoueur2eme;
    @FXML
    private Label scoreJoueur2eme;
    @FXML
    private Label nomJoueur3eme;
    @FXML
    private Label scoreJoueur3eme;
    @FXML
    private Label nomJoueur4eme;
    @FXML
    private Label scoreJoueur4eme;


    public VueResultats(TrainsIHM ihm) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/vueResultats.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.ihm = ihm;
    }

    public void createBindings() {
        ihm.getJeu().finDePartieProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                List<IJoueur> joueurs = new ArrayList<>(ihm.getJeu().getJoueurs());

                // Trier les joueurs par score en ordre décroissant
                joueurs.sort((joueur1, joueur2) -> Integer.compare(joueur2.getScoreTotal(), joueur1.getScoreTotal()));

                if (joueurs.size() == 2) {
                    vue3eme.setVisible(false);
                    vue4eme.setVisible(false);
                } else if (joueurs.size() == 3) {
                    vue4eme.setVisible(false);
                }

                //bind le label du nom du joueur
                nomJoueur1er.textProperty().bind(joueurs.get(0).nomProperty());
                scoreJoueur1er.textProperty().bind(joueurs.get(0).scoreProperty().asString());
                nomJoueur2eme.textProperty().bind(joueurs.get(1).nomProperty());
                scoreJoueur2eme.textProperty().bind(joueurs.get(1).scoreProperty().asString());
                if (joueurs.size() == 3) {
                    nomJoueur3eme.textProperty().bind(joueurs.get(2).nomProperty());
                    scoreJoueur3eme.textProperty().bind(joueurs.get(2).scoreProperty().asString());
                }
                if (joueurs.size() == 4) {
                    nomJoueur4eme.textProperty().bind(joueurs.get(3).nomProperty());
                    scoreJoueur4eme.textProperty().bind(joueurs.get(3).scoreProperty().asString());
                }

                // Afficher une nouvelle fenetre
                ihm.getPrimaryStage().close();
                ihm.getPrimaryStage().setScene(new Scene(this));
                ihm.getPrimaryStage().show();
            }
        });
    }
}
