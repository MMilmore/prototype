package com.qrprototype.ui;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

// Classe utilitaire qui centralise l'application des styles CSS aux différents composants de l'interface utilisateur
public class UIStyler {

    // Méthode polyvalente pour appliquer un style CSS à n'importe quel composant Node
    public static void applyStyle(Node node, String styleClass) {
        node.getStyleClass().add(styleClass);  // Ajoute la classe CSS à n'importe quel Node
    }

    // Méthode pour appliquer un style de fond à un AnchorPane
    public static void applyAnchorPaneBackgroundStyle(AnchorPane anchorPane) {
        applyStyle(anchorPane, "anchor-pane-background");  // Applique le style de fond à l'AnchorPane
    }

    // Méthode pour appliquer le style par défaut à un TextField
    public static void applyTextFieldDefaultStyle(TextField textField) {
        applyStyle(textField, "text-field-default");  // Applique le style par défaut au TextField
    }

    // Méthode pour appliquer le style aux boutons (Button)
    public static void applyButtonStyle(Button button) {
        applyStyle(button, "button-style");  // Applique le style de bouton
    }

    // Méthode pour appliquer le style aux cases à cocher (CheckBox)
    public static void applyCheckBoxStyle(CheckBox checkBox) {
        applyStyle(checkBox, "checkbox-style");  // Applique le style de CheckBox
    }

    // Méthode pour appliquer le style à un CustomLabel
    public static void applyCustomLabelStyle(CustomLabel label) {
        applyStyle(label, "label-style");  // Applique le style de CustomLabel
    }
}