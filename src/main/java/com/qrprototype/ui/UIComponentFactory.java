package com.qrprototype.ui;  // Déclare le package qui contient cette classe

import javafx.scene.text.Font;  // Importe la classe Font pour définir les polices de texte
import javafx.scene.text.FontWeight;  // Importe la classe FontWeight pour gérer l'épaisseur de la police (gras)
import javafx.scene.control.Button;  // Importe la classe Button pour créer des boutons
import javafx.scene.control.CheckBox;  // Importe la classe CheckBox pour créer des cases à cocher
import javafx.scene.control.TextField;  // Importe la classe TextField pour créer des champs de texte

import com.qrprototype.ui.constants.UIConstants;  // Importe les constantes de l'interface utilisateur

// Classe UIComponentFactory implémente l'interface ComponentFactory pour créer différents composants de l'UI
public class UIComponentFactory implements ComponentFactory {

    @Override
    // Méthode pour créer un champs de texte personnalisé avec un texte de substitution (placeholder)
    public TextField createCustomTextField(String placeholder) {
        TextField textField = new TextField();
        textField.setPromptText(placeholder);
        UIStyler.applyTextFieldDefaultStyle(textField);
        return textField;
    }

    @Override
    // Méthode pour créer un bouton personnalisé avec du texte
    public Button createCustomButton(String text) {
        Button button = new Button(text);  // Crée un nouveau bouton avec le texte spécifié
        // Définit la police du bouton en utilisant les constantes définies dans UIConstants
        button.setFont(Font.font(UIConstants.FontProperties.FONT_FAMILY, FontWeight.BOLD, 
        UIConstants.FontProperties.FONT_SIZE_BUTTON));  // Utilisation de la constante pour la taille et la police
        UIStyler.applyButtonStyle(button);  // Applique le style CSS au bouton via UIStyler
        return button;  // Retourne le bouton personnalisé
    }

    @Override
    // Méthode pour créer une case à cocher personnalisée avec du texte
    public CheckBox createCustomCheckBox(String text) {
        CheckBox checkBox = new CheckBox(text);  // Crée une nouvelle case à cocher avec le texte spécifié
        // Définit la police de la case à cocher en utilisant les constantes définies dans UIConstants
        checkBox.setFont(Font.font(UIConstants.FontProperties.FONT_FAMILY, FontWeight.NORMAL, 
        UIConstants.FontProperties.FONT_SIZE_CHECKBOX));  // Utilisation de la constante pour la taille et la police
        UIStyler.applyCheckBoxStyle(checkBox);  // Applique le style CSS à la case à cocher via UIStyler
        return checkBox;  // Retourne la case à cocher personnalisée
    }

    @Override
    // Méthode pour créer un label personnalisé
    public CustomLabel createCustomLabel(String text) {
        return new CustomLabel(text);  // Retourne un label personnalisé avec le texte fourni
    }
}
