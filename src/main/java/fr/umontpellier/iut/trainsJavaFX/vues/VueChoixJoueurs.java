package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.TrainsIHM;
import fr.umontpellier.iut.trainsJavaFX.mecanique.plateau.Plateau;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe correspond à une nouvelle fenêtre permettant de choisir le nombre et les noms des joueurs de la partie.
 * <p>
 * Sa présentation graphique peut automatiquement être actualisée chaque fois que le nombre de joueurs change.
 * Lorsque l'utilisateur a fini de saisir les noms de joueurs, il demandera à démarrer la partie.
 */
public class VueChoixJoueurs extends Stage {
    private final ObservableList<String> nomsJoueurs;
    private Plateau plateauChoisi;


    private ImageView logoTrains;
    private Label indicationJoueurs;
    private TextField joueur;
    private Button ajouterJoueur;
    private ComboBox<Plateau> comboBox;
    private Button start;

    public VueChoixJoueurs() {
        nomsJoueurs = FXCollections.observableArrayList();

        VBox layout = new VBox();
        layout.setSpacing(10);


        //-------------------------------------
        // Création du logo
        Image image = new Image(getClass().getResourceAsStream("/images/logoTrains.png"));
        logoTrains = new ImageView(image);
        logoTrains.setFitHeight(91);
        logoTrains.setFitWidth(304);
        logoTrains.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
        layout.getChildren().add(logoTrains);


        //-------------------------------------
        // Création de la zone de saisie des noms des joueurs
        indicationJoueurs = new Label("Nom des joueurs :");
        indicationJoueurs.setStyle("-fx-font-size: 20; -fx-font-family: 'Arial'; -fx-text-fill: white;");
        layout.getChildren().add(indicationJoueurs);

        HBox nomsDesJoueurs = new HBox();

        joueur = new TextField();
        joueur.setPromptText("Nom du joueur");
        nomsDesJoueurs.getChildren().add(joueur);

        ajouterJoueur = new Button("Ajouter");
        ajouterJoueur.setOnAction(actionEvent -> {
            nomsJoueurs.add(joueur.getText());
            joueur.clear();
        });
        nomsDesJoueurs.getChildren().add(ajouterJoueur);
        //creer de l'espace sous la vbox avec un margin

        nomsDesJoueurs.setStyle("-fx-margin: 10;");
        layout.getChildren().add(nomsDesJoueurs);


        //-------------------------------------
        // Création de la zone de choix du plateau
        Label choixPlateau = new Label("Choix du plateau :");
        choixPlateau.setStyle("-fx-font-size: 20; -fx-font-family: 'Arial'; -fx-text-fill: white;");
        layout.getChildren().add(choixPlateau);
        comboBox = new ComboBox<>();
        comboBox.getItems().add(Plateau.OSAKA);
        comboBox.getItems().add(Plateau.TOKYO);


        layout.getChildren().add(comboBox);
        layout.setStyle("-fx-margin: 30;");

        //-------------------------------------
        // Création du bouton de démarrage de la partie
        Image imageStart = new Image(getClass().getResourceAsStream("/images/boutons/BoutonStartTrains.png"));

        start = new Button();
        start.setGraphic(new ImageView(imageStart));
        start.setOnAction(actionEvent -> setListeDesNomsDeJoueurs());
        start.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);" + "-fx-background-color: transparent;");
        layout.getChildren().add(start);
        start.setDisable(true);

        comboBox.setOnAction(actionEvent -> {
            plateauChoisi=comboBox.getValue();
            start.setDisable(false);
        });

        //-------------------------------------
        //definir le style
        layout.setStyle("-fx-background-color: #2b2b2b; -fx-padding: 10; -fx-alignment: center;" +
                "-fx-spacing: 10; -fx-font-size: 20; -fx-font-family: 'Arial'; -fx-text-fill: white;");


        // Ajouter un écouteur à la liste nomsJoueurs
        nomsJoueurs.addListener((ListChangeListener<String>) c -> {
            if (nomsJoueurs.size() == 4) {
                joueur.setDisable(true);
            } else {
                joueur.setDisable(false);
            }
        });


        //-------------------------------------
        // Création de la scène
        Scene scene = new Scene(layout);
        this.setScene(scene);
    }

    public List<String> getNomsJoueurs() {
        return nomsJoueurs;
    }

    /**
     * Définit l'action à exécuter lorsque la liste des participants est correctement initialisée
     */
    public void setNomsDesJoueursDefinisListener(ListChangeListener<String> quandLesNomsDesJoueursSontDefinis) {}

    /**
     * Vérifie que tous les noms des participants sont renseignés
     * et affecte la liste définitive des participants
     */
    protected void setListeDesNomsDeJoueurs() {
        ArrayList<String> tempNamesList = new ArrayList<>();
        for (int i = 1; i <= getNombreDeJoueurs() ; i++) {
            String name = getJoueurParNumero(i);
            if (name == null || name.equals("")) {
                tempNamesList.clear();
                break;
            }
            else
                tempNamesList.add(name);
        }
        if (!tempNamesList.isEmpty()) {
            hide();
            nomsJoueurs.clear();
            nomsJoueurs.addAll(tempNamesList);
        }
    }

    /**
     * Retourne le nombre de participants à la partie que l'utilisateur a renseigné
     */
    protected int getNombreDeJoueurs() {
        return nomsJoueurs.size();
    }

    /**
     * Retourne le nom que l'utilisateur a renseigné pour le ième participant à la partie
     * @param playerNumber : le numéro du participant
     */
    protected String getJoueurParNumero(int playerNumber) {
        return nomsJoueurs.get(playerNumber-1);
    }

    public Plateau getPlateau() {
        return plateauChoisi;
    }

}
