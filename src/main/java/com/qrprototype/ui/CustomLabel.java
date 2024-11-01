package com.qrprototype.ui;

import javafx.animation.ScaleTransition;  // Importe la classe pour créer des animations de transition de taille
import javafx.scene.control.Label;  // Importe la classe Label de JavaFX pour créer des étiquettes de texte
import com.qrprototype.ui.constants.UIConstants;  // Importe les constantes définies dans UIConstants
import javafx.util.Duration;  // Importe la classe Duration pour définir la durée des animations

// Classe CustomLabel qui étend Label et implémente l'interface CustomUIComponent
// Cette classe permet de créer des labels personnalisés avec des styles et des animations spécifiques
public class CustomLabel extends Label implements CustomUIComponent {

    // Constructeur de la classe CustomLabel qui initialise le label avec un texte et applique les styles et animations par défaut
    public CustomLabel(String text) {
        super(text);  // Appelle le constructeur parent de Label pour définir le texte du label
        applyStyles();  // Applique les styles définis pour ce label
        setupHoverAnimation();  // Définit une animation qui s'active lorsque le curseur survole le label
    }

    // Méthode de l'interface CustomUIComponent pour gérer l'affichage du label
    @Override
    public void render() {
        // Logique d'affichage si nécessaire (actuellement vide)
    }

    // Méthode de l'interface CustomUIComponent pour appliquer les styles CSS au label
    @Override
    public void applyStyles() {
        // Définit la police du label en utilisant les constantes de UIConstants
        this.setFont(javafx.scene.text.Font.font(UIConstants.FontProperties.FONT_FAMILY,  // Utilise la famille de police définie
            javafx.scene.text.FontWeight.BOLD,  // Définit le texte en gras
            UIConstants.FontProperties.FONT_SIZE));  // Définit la taille de la police
        this.getStyleClass().add("label-style");  // Ajoute une classe CSS pour appliquer le style défini dans le fichier CSS
    }

    // Méthode privée pour configurer une animation de transition lorsque le curseur survole le label
    private void setupHoverAnimation() {
        // Créer une animation de transition pour agrandir le label lorsque le curseur est dessus
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(UIConstants.AnimationProperties.ANIMATION_DURATION), this);  // Crée une transition avec une durée définie
        scaleUp.setToX(UIConstants.AnimationProperties.SCALE_UP_FACTOR);  // Définit l'agrandissement sur l'axe X
        scaleUp.setToY(UIConstants.AnimationProperties.SCALE_UP_FACTOR);  // Définit l'agrandissement sur l'axe Y
    
        // Créer une animation de transition pour ramener le label à sa taille normale lorsque le curseur sort
        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(UIConstants.AnimationProperties.ANIMATION_DURATION), this);  // Crée une transition avec la même durée
        scaleDown.setToX(UIConstants.AnimationProperties.SCALE_DOWN_FACTOR);  // Définit la réduction à la taille normale sur l'axe X
        scaleDown.setToY(UIConstants.AnimationProperties.SCALE_DOWN_FACTOR);  // Définit la réduction à la taille normale sur l'axe Y
    
        // Lorsque le curseur survole le label, l'animation d'agrandissement commence
        this.setOnMouseEntered(event -> scaleUp.playFromStart());  // Déclenche l'animation lorsque la souris entre sur le label
    
        // Lorsque le curseur quitte le label, l'animation de rétrécissement commence
        this.setOnMouseExited(event -> scaleDown.playFromStart());  // Déclenche l'animation lorsque la souris quitte le label
    }
}
