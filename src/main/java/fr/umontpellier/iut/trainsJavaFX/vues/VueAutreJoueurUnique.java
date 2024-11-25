package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.IJoueur;
import fr.umontpellier.iut.trainsJavaFX.mecanique.CouleurJoueur;
import fr.umontpellier.iut.trainsJavaFX.mecanique.Joueur;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

import static fr.umontpellier.iut.trainsJavaFX.GestionJeu.getJeu;

public class VueAutreJoueurUnique extends HBox {
    private final IJoueur joueur;

    @FXML
    private CouleurJoueur couleurJoueur;

    @FXML
    private Label nomJoueur;

    @FXML
    private Label scoreJoueur;

    @FXML
    private Label pointsRails;

    @FXML
    private Label nbCartes;

    public VueAutreJoueurUnique(IJoueur joueur) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/vueAutreJoueurUnique.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.joueur = joueur;
    }

    public void createBindings() {
        this.setStyle("-fx-background-color: " + CouleursJoueurs.couleursBackgroundJoueur.get(joueur.getCouleur()) + ";" + "-fx-border-radius: 20; -fx-background-radius: 20;");
        nomJoueur.setText(joueur.getNom());
        scoreJoueur.textProperty().bind(joueur.scoreProperty().asString());
        nbCartes.textProperty().bind(Bindings.size(joueur.defausseProperty()).asString());
        pointsRails.textProperty().bind(joueur.pointsRailsProperty().asString());
    }
}
