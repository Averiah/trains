package fr.umontpellier.iut.trainsJavaFX.vues;


import javafx.geometry.Rectangle2D;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import fr.umontpellier.iut.trainsJavaFX.IJeu;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.Carte;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class VueDuJeu extends BorderPane {

    private final IJeu jeu;

    @FXML
    private VuePlateau plateau;

    @FXML
    private VueJoueurCourant joueurCourant;

    @FXML
    private VueMain vueCartesMain;

    @FXML
    private VueAutresJoueurs vueAutresJoueurs;

    @FXML
    private VueCartesAchat vueCartesAchat;

    private VueResultats vueResultats;


    public VueDuJeu(IJeu jeu) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/jeu.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.jeu = jeu;
    }

    public void creerBindings() {
        if (plateau != null) {
            plateau.prefWidthProperty().bind(getScene().widthProperty());
            plateau.prefHeightProperty().bind(getScene().heightProperty());
            plateau.creerBindings();
        }
        if (joueurCourant != null) {
            joueurCourant.createBindings();
        }
        if (vueCartesMain != null) {
            vueCartesMain.creerBindings();
        }
        if (vueAutresJoueurs != null) {
            vueAutresJoueurs.createBindings();
        }
        if (vueCartesAchat != null) {
            vueCartesAchat.creerBindings();
        }
        if (vueResultats != null) {
            vueResultats.createBindings();
        }
    }



//    public void setOnActionPasser(EventHandler<? super MouseEvent> value) {
//        plateau.setOnMouseClicked(value);
//        joueurCourant.setOnMouseClicked(value);
//        vueCartesMain.setOnMouseClicked(value);
//    }

    public IJeu getJeu() {
        return jeu;
    }

    EventHandler<? super MouseEvent> actionPasserParDefaut = (mouseEvent -> System.out.println("Vous avez choisi Passer"));
}
