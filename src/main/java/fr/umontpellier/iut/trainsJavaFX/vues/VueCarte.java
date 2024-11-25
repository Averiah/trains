package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.ICarte;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class VueCarte extends StackPane {

    private final ICarte carte;
    private Button bouton;
    private Image imageCarte;
    private String nomCarte;

    public VueCarte(ICarte carte) {
        this.carte = carte;
        bouton = new Button();
        nomCarte = carte.getNom().toLowerCase().replace(" ", "_"); // on remplace les espaces par des underscores
        imageCarte = new Image(("file:src/main/resources/images/cartes/" + nomCarte + ".jpg"));

        ImageView imageView = new javafx.scene.image.ImageView(imageCarte);

        // Définissez la hauteur et la largeur de l'image
        imageView.setFitHeight(130);
        imageView.setFitWidth(95);
        bouton.setGraphic(imageView);
        bouton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        getChildren().add(bouton);



        setCarteChoisieListener(event -> {
            if (quandCarteEstChoisie != null) {
                quandCarteEstChoisie.handle(event);
            }
        });
    }

    private EventHandler<MouseEvent> quandCarteEstChoisie;

    public void setCarteChoisieListener(EventHandler<MouseEvent> quandCarteEstChoisie) {
        bouton.setOnMouseClicked(quandCarteEstChoisie);
    }

    public ICarte getCarte() {
        return carte;
    }

    public String getNomCarte() {
        return carte.getNom();
    }

    public void setGraphic(ImageView image) {
        bouton.setGraphic(image);
    }
}
