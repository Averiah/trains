package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.GestionJeu;
import fr.umontpellier.iut.trainsJavaFX.IJeu;
import fr.umontpellier.iut.trainsJavaFX.mecanique.CouleurJoueur;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.Carte;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Stack;

import static fr.umontpellier.iut.trainsJavaFX.GestionJeu.getJeu;

public class VueMain extends VBox {
    @FXML
    private Label instruction;

    @FXML
    private Button passer;

    @FXML
    private Button jeterCartesFerraille;

    @FXML
    private HBox cartesMain;

    @FXML
    private StackPane pioche;

    @FXML
    private StackPane defausse;

    @FXML
    private Label nbCartesDefausse;

//test R
    public VueMain() {
        this(null);
    }

    public VueMain(IJeu jeu) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/vueMain.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void creerBindings() {
        //---------------------main---------------------
        getJeu().joueurCourantProperty().addListener((observable, oldValue, newValue) -> {

            cartesMain.getChildren().clear();
            instruction.textProperty().bind(getJeu().instructionProperty());

            for (Carte carte : newValue.mainProperty().getValue()) { // on crée un bouton pour chaque carte de la main du joueur
                VueCarte vueCarte = new VueCarte(carte);
                vueCarte.setCarteChoisieListener(event -> {
                    newValue.uneCarteDeLaMainAEteChoisie(carte.getNom());
                    cartesMain.getChildren().remove(vueCarte);
                    for(Carte c: newValue.cartesEnJeuProperty()){
                        if(carte.getNom().equals(c.getNom())){
                            newValue.uneCarteEnJeuAEteChoisie(carte.getNom());
                        }
                    }
                });
                cartesMain.getChildren().add(vueCarte); // on ajoute le bouton à la liste des boutons
            }

            passer.setOnMouseClicked(event -> { // on crée un bouton pour passer
                getJeu().passerAEteChoisi();
                actionPasserParDefaut.handle(event);
            });
        });

        //---------------------pioche---------------------
        String dos = "images/cartes/back.jpg";
        ImageView vueDos = new ImageView(dos);
        vueDos.setFitHeight(110);
        vueDos.setFitWidth(90);
        vueDos.setPreserveRatio(true);

        // on crée un bouton pour piocher
        GestionJeu.getJeu().joueurCourantProperty().addListener((observable, oldValue, newValue) -> {
            for(Carte c: newValue.piocheProperty()){
                VueCarte vueCarte = new VueCarte(c);
                vueCarte.setGraphic(vueDos);

                vueCarte.setCarteChoisieListener(event -> {
                    newValue.laPiocheAEteChoisie();

                    if(!newValue.piocheProperty().isEmpty()){
                        pioche.getChildren().remove(vueCarte);
                    }

                });
                pioche.getChildren().add(vueCarte);
            }
            nbCartesDefausse.textProperty().bind(Bindings.size(newValue.defausseProperty()).asString());
        });

        //---------------------défausse---------------------
        String urlDef = "images/boutons/defausse.png";
        ImageView imageDefausse = new ImageView(urlDef);
        imageDefausse.setFitHeight(110);
        imageDefausse.setFitWidth(90);
        imageDefausse.setRotate(180);
        imageDefausse.setPreserveRatio(true);


        // on crée un bouton pour défausser
        GestionJeu.getJeu().joueurCourantProperty().addListener((observable, oldValue, newValue) -> {
            for(Carte c: newValue.defausseProperty()){
                VueCarte vueCarte = new VueCarte(c);
                vueCarte.setGraphic(imageDefausse);
                vueCarte.setCarteChoisieListener(event -> {
                    newValue.laDefausseAEteChoisie();

                    if(!newValue.defausseProperty().isEmpty()){
                        defausse.getChildren().remove(vueCarte);
                    }
                });
                defausse.getChildren().add(vueCarte);
            }
        });
    }

    EventHandler<? super MouseEvent> actionPasserParDefaut = (mouseEvent -> System.out.println("Vous avez choisi Passer"));
}
