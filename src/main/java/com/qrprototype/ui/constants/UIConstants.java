// UIConstants.java
package com.qrprototype.ui.constants;

import javafx.scene.transform.Scale;

public class UIConstants {

    // Titre de l'application
    public static final String APP_TITLE = "qrprototype";

    // Dimensions de la fenêtre principale
    public static final double SCENE_WIDTH = 900.0; // Largeur de la fenêtre principale en pixels
    public static final double SCENE_HEIGHT = 600.0; // Hauteur de la fenêtre principale en pixels

    // Position de la zone de texte principale
    public static final double TOP_ANCHOR = 50.0; // Distance entre le haut de la fenêtre et la zone de texte en pixels
    public static final double LEFT_ANCHOR = 50.0; // Distance entre la bordure gauche de la fenêtre et la zone de texte en pixels

    // Distance entre les labels de catégories principales
    public static final double TEXT_FIELD_TOP_ANCHOR_OFFSET = 100.0;

    // Texte du "placeholder" dans le champ de texte
    public static final String CREATE_CATEGORY_PLACEHOLDER = "Créer une catégorie"; // Texte affiché dans le champ de texte lorsqu'il est vide

    // Propriétés de zoom
    public static final double INITIAL_SCALE_FACTOR = 1.0;  // Facteur de zoom initial
    public static final double MAX_ZOOM = 1000.0;  // Zoom maximum
    public static final double MIN_ZOOM = 1.0;  // Zoom minimum
    public static final double ZOOM_STEP = 0.05;  // Pas de zoom pour affiner les transitions
    public static final String CSS_PATH = "/styles.css";  // Chemin du fichier CSS

    // Constantes pour la transformation (translation, rotation, etc.)
    public static final double TRANSLATION_STEP = 10.0;  // Step utilisé pour déplacer un élément
    public static final double ROTATION_ANGLE = 15.0;  // Angle de rotation standard

    // Décalage standard par rapport à la souris lors du zoom
    public static final double MOUSE_OFFSET_FACTOR = 0.5;  // Décalage pour ajuster l'ancrage du zoom

    // Choix d'actions du menu contextuel
    public static final String ADD_MENU_ITEM = "Ajouter"; // Texte de l'option "Ajouter" dans le menu contextuel
    public static final String MODIFY_MENU_ITEM = "Modifier"; // Texte de l'option "Modifier" dans le menu contextuel
    public static final String DELETE_MENU_ITEM = "Supprimer"; // Texte de l'option "Supprimer" dans le menu contextuel

    // Méthode pour créer une nouvelle instance de transformation d'échelle (Scale)
    public static Scale createDefaultScaleTransform() {
        return new Scale(1.0, 1.0, 0, 0);  // Échelle par défaut avec pivot au coin supérieur gauche
    }

    // Sous-classe pour les constantes d'animations des labels
    public static class AnimationProperties {
        public static final double SCALE_UP_FACTOR = 1.2;  // Facteur d'agrandissement
        public static final double SCALE_DOWN_FACTOR = 1.0;  // Facteur pour revenir à la taille normale
        public static final double ANIMATION_DURATION = 10.0;  // Durée de 1000ms (1 seconde)
    }

    // Sous-classe pour les propriétés de police
    public static class FontProperties {
        public static final String FONT_FAMILY = "Arial"; // Police du texte des composants (par exemple: labels, boutons)
        public static final double FONT_SIZE = 20.0; // Taille de la police pour les labels
        public static final double FONT_SIZE_BUTTON = 16.0; // Taille de la police des boutons du menu contextuel
        public static final double FONT_SIZE_CHECKBOX = 14.0; // Taille de la police des cases à cocher (non utilisé pour l'instant)
    }

    // Sous-classe pour les messages des fenêtres de confirmation 
    public static class DialogMessages {
        // Fenêtre de confirmation pour l'action "Modifier"
        public static final String MODIFY_CONFIRMATION_TITLE = "Confirmation de modification"; // Texte du titre de la fenêtre
        public static final String MODIFY_CONFIRMATION_HEADER = "Voulez-vous vraiment modifier la catégorie ?"; // Texte de l'en-tête de la fenêtre
        public static final String MODIFY_CONFIRMATION_CONTENT = "Cette action modifiera la catégorie existante."; // Texte du contenu de la fenêtre
        // Fenêtre de confirmation pour l'action "Supprimer"
        public static final String DELETE_CONFIRMATION_TITLE = "Confirmation de suppression"; // Texte du titre de la fenêtre
        public static final String DELETE_CONFIRMATION_HEADER = "Voulez-vous vraiment supprimer la catégorie ?"; // Texte de l'en-tête de la fenêtre
        public static final String DELETE_CONFIRMATION_CONTENT = "Cette action est irréversible."; // Texte du contenu de la fenêtre
    }

    // Constantes pour les boutons de confirmation
    public static class DialogButtons {
        public static final String OK_BUTTON_TEXT = "Oui";  // Texte du bouton OK
        public static final String CANCEL_BUTTON_TEXT = "Non";  // Texte du bouton Annuler
    }
}
