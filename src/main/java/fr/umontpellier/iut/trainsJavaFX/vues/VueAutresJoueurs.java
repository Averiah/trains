package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.IJeu;
import fr.umontpellier.iut.trainsJavaFX.IJoueur;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

import static fr.umontpellier.iut.trainsJavaFX.GestionJeu.getJeu;

/**
 * Cette classe présente les éléments des joueurs autres que le joueur courant,
 * en cachant ceux que le joueur courant n'a pas à connaitre.
 * <p>
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueAutresJoueurs extends VBox {
    public VueAutresJoueurs() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/vueAutreJoueurs.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createBindings(){
        getJeu().joueurCourantProperty().addListener((observable, oldValue, newValue) -> {
            getChildren().clear();
            for (IJoueur joueur : getJeu().getJoueurs()) {
                if(joueur != getJeu().joueurCourantProperty().get()){
                    VueAutreJoueurUnique vueAutreJoueurUnique = new VueAutreJoueurUnique(joueur);
                    vueAutreJoueurUnique.createBindings();
                    getChildren().add(vueAutreJoueurUnique);
                    this.setSpacing(10);
                }
            }
        });
    }
}
