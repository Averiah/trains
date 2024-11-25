package fr.umontpellier.iut.trainsJavaFX.vues;


import fr.umontpellier.iut.trainsJavaFX.GestionJeu;
import fr.umontpellier.iut.trainsJavaFX.IJeu;
import fr.umontpellier.iut.trainsJavaFX.mecanique.Joueur;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.ListeDeCartes;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;


import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.Carte;
import javafx.scene.layout.VBox;

import static fr.umontpellier.iut.trainsJavaFX.GestionJeu.getJeu;


public class VueCartesAchat extends ScrollPane {
    @FXML
    private VBox CartesAchetable;

    @FXML
    private ScrollPane scrollPane;

    //liste de cartes achetables
    private ListeDeCartes cartesAchetable;

    public VueCartesAchat() {
        this(null);
    }

    public VueCartesAchat(IJeu jeu) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/vueCartesAchat.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cartesAchetable = getJeu().getReserve();
    }

    public void creerBindings() {
        getJeu().joueurCourantProperty().addListener((observable, oldValue, newValue) -> {
            CartesAchetable.getChildren().clear();
            cartesAchetable = getJeu().getReserve();

            for (int i = 0; i < cartesAchetable.size(); i += 3) {
                HBox hbox = new HBox();
                Joueur joueur = (Joueur) newValue;

                for (int j = i; j < i + 3 && j < cartesAchetable.size(); j++) {
                    VueCarte vueCarte = new VueCarte(cartesAchetable.get(j));

                    vueCarte.setCarteChoisieListener(event -> {
                        getJeu().uneCarteDeLaReserveEstAchetee(vueCarte.getNomCarte());
                        getJeu().uneCarteAChoisirChoisie(vueCarte.getNomCarte());
                        System.out.println("tentative d'achat de : " + vueCarte.getNomCarte());
                    });
//                    vueCarte.setCarteChoisieListener(event -> {
//                        getJeu().uneCarteAChoisirChoisie(vueCarte.getNomCarte());
//                    });

                    hbox.getChildren().add(vueCarte);
                }
                CartesAchetable.getChildren().add(hbox);
            }

            if (cartesAchetable.isEmpty()) {
                Label label = new Label("Il n'y a plus de cartes achetables");
                CartesAchetable.getChildren().add(label);
            }
            else {
                scrollPane.setContent(CartesAchetable);
            }

        });
    }

    public Button trouverBoutonCarte(Carte carteATrouver) {
        for (Node boutonCarte : CartesAchetable.getChildren()) {
            if (((Button) boutonCarte).getText().equals(carteATrouver.getNom())) {
                return (Button) boutonCarte;
            }
        }
        return null;
    }

    EventHandler<? super MouseEvent> actionPasserParDefaut = (mouseEvent -> System.out.println("Vous avez choisi Passer"));

}
