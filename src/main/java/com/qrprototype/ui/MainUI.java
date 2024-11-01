package com.qrprototype.ui;

import com.qrprototype.ui.constants.UIConstants;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

public class MainUI extends Application {

    public static TextField initialTextField; // Champ de texte pour la catégorie initiale
    private double scaleFactor = UIConstants.INITIAL_SCALE_FACTOR;  // Facteur de zoom initial
    private Scale scaleTransform;  // Transform pour le zoom
    private AnchorPane anchorRoot;  // Déclarez anchorRoot comme variable de classe

    @Override
    public void start(Stage primaryStage) {
        // Initialiser anchorRoot avant de l'utiliser
        anchorRoot = new AnchorPane();  // Ajoutez cette ligne pour initialiser anchorRoot
        // Appliquer la classe CSS à l'AnchorPane
        anchorRoot.getStyleClass().add("anchor-pane-background");

        // Crée un StackPane pour superposer l'AnchorPane
        StackPane root = new StackPane();
        root.getChildren().add(anchorRoot);  // Ajoute l'AnchorPane zoomable au StackPane

        // Crée la scène avec les dimensions définies et charge le fichier CSS pour le style
        Scene scene = new Scene(root, UIConstants.SCENE_WIDTH, UIConstants.SCENE_HEIGHT);
        scene.getStylesheets().add(getClass().getResource(UIConstants.CSS_PATH).toExternalForm());

        // Utilisation de la fabrique pour créer un TextField pour la catégorie initiale
        UIComponentFactory factory = new UIComponentFactory();
        initialTextField = factory.createCustomTextField(UIConstants.CREATE_CATEGORY_PLACEHOLDER);  // initialTextField pour la catégorie initiale
        UIStyler.applyTextFieldDefaultStyle(initialTextField);  // Appliquer les styles au TextField initial

        // Ajouter le champ de texte initial à l'AnchorPane 
        anchorRoot.getChildren().add(initialTextField);

        // Ajout de la gestion de la touche "Enter" dès la création du premier champ
        initialTextField.setOnAction(event -> {
            // Valider le texte avec la touche "Enter" et le convertir en label
            UIEventHandler.validateAndConvertToLabel(initialTextField, anchorRoot);
        });

        // Valide le texte à la perte du focus
        UIEventHandler.handleFocusLoss(initialTextField, anchorRoot);

        // Gère la perte de focus en cliquant sur l'AnchorPane
        UIEventHandler.handlePaneClickForValidation(anchorRoot, initialTextField, anchorRoot);

        // Positionne le champ de texte à une distance spécifiée du haut et du côté gauche
        AnchorPane.setTopAnchor(initialTextField, UIConstants.TOP_ANCHOR);
        AnchorPane.setLeftAnchor(initialTextField, UIConstants.LEFT_ANCHOR);

        // Utilise la méthode pour créer une nouvelle transformation d'échelle
        scaleTransform = new Scale(1.0, 1.0, 0, 0);
        anchorRoot.getTransforms().add(scaleTransform);  // Ajoute la transformation à l'AnchorPane

        // Ajoute la fonctionnalité de zoom
        addZoomFeature(scene, anchorRoot);

        // Configure la scène et la fenêtre principale
        primaryStage.setScene(scene);
        primaryStage.setTitle(UIConstants.APP_TITLE);
        primaryStage.show();

        anchorRoot.requestFocus();
    }

    // Méthode pour ajouter la fonctionnalité de zoom
    private void addZoomFeature(Scene scene, AnchorPane root) {
        // Gestion du zoom avec la molette de la souris tout en maintenant Ctrl
        scene.addEventFilter(ScrollEvent.SCROLL, event -> {
            if (event.isControlDown()) {
                double mouseX = event.getSceneX();  // Coordonnées X de la souris
                double mouseY = event.getSceneY();  // Coordonnées Y de la souris
                if (event.getDeltaY() > 0) {
                    zoom(1, mouseX, mouseY, root, scene);  // Zoom avant autour du curseur
                } else if (event.getDeltaY() < 0) {
                    zoom(-1, mouseX, mouseY, root, scene);  // Zoom arrière autour du curseur
                }
            }
        });

        // Gestion du zoom par le trackpad (ZoomEvent)
        scene.addEventFilter(ZoomEvent.ZOOM, event -> {
            double mouseX = event.getSceneX();  // Coordonnées X de la souris
            double mouseY = event.getSceneY();  // Coordonnées Y de la souris
            if (event.getZoomFactor() > 1) {
                zoom(1, mouseX, mouseY, root, scene);  // Zoom avant
            } else {
                zoom(-1, mouseX, mouseY, root, scene);  // Zoom arrière
            }
        });
    }

    // Méthode pour gérer le zoom autour du curseur tout en restant à l'intérieur de l'AnchorPane
    private void zoom(int direction, double mouseX, double mouseY, AnchorPane root, Scene scene) {
        double oldScale = scaleFactor;
        double dynamicZoomStep = UIConstants.ZOOM_STEP * scaleFactor;  // Ajuster le pas de zoom en fonction de la taille actuelle

        // Ajuster le facteur de zoom en fonction de la direction
        scaleFactor += direction * dynamicZoomStep;

        // Limite le facteur de zoom entre MIN_ZOOM et MAX_ZOOM
        if (scaleFactor > UIConstants.MAX_ZOOM) {
            scaleFactor = UIConstants.MAX_ZOOM;
        } else if (scaleFactor < UIConstants.MIN_ZOOM) {
            scaleFactor = UIConstants.MIN_ZOOM;
        }

        // Applique la transformation d'échelle au Pane
        scaleTransform.setX(scaleFactor);
        scaleTransform.setY(scaleFactor);

        // Calculer le décalage à appliquer pour centrer le zoom sur la position de la souris
        double f = (scaleFactor / oldScale) - 1;
        double dx = mouseX - root.getBoundsInParent().getMinX();
        double dy = mouseY - root.getBoundsInParent().getMinY();

        // Ajuster la position du Pane pour garder l'AnchorPane visible dans la scène
        double newTranslateX = root.getTranslateX() - f * dx;
        double newTranslateY = root.getTranslateY() - f * dy;

        // Appliquer les valeurs limitées avec clamp
        root.setTranslateX(clamp(newTranslateX, scene.getWidth(), root.getBoundsInParent().getWidth()));
        root.setTranslateY(clamp(newTranslateY, scene.getHeight(), root.getBoundsInParent().getHeight()));
    }

    private double clamp(double value, double sceneSize, double paneSize) {
        double minValue = Math.min(0, sceneSize - (paneSize * scaleFactor));
        double maxValue = 0;
        return Math.max(minValue, Math.min(value, maxValue));
    }

    // Point d'entrée pour lancer l'application JavaFX
    public static void main(String[] args) {
        launch(args);  // Appelle la méthode launch pour démarrer l'application JavaFX
    }
}
