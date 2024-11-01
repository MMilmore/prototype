package com.qrprototype.ui;  // Déclare que cette interface fait partie du package 'com.qrprototype.ui'

import javafx.scene.control.Button;  // Importe la classe Button pour créer des boutons dans l'interface graphique
import javafx.scene.control.CheckBox;  // Importe la classe CheckBox pour créer des cases à cocher dans l'interface graphique
import javafx.scene.control.TextField;  // Importe la classe TextField pour créer des champs de texte dans l'interface graphique

// Définition de l'interface ComponentFactory, qui sert de plan pour la création de composants d'interface utilisateur (UI)
public interface ComponentFactory {
    
    // Méthode pour créer un champs de texte personnalisé avec un texte de "placeholder" (texte indicatif)
    // Elle doit être implémentée par une classe concrète
    TextField createCustomTextField(String placeholder); 

    // Méthode pour créer un bouton personnalisé avec un texte spécifique
    // Elle doit être implémentée par une classe concrète
    Button createCustomButton(String text);

    // Méthode pour créer une case à cocher personnalisée avec un texte spécifique
    // Elle doit être implémentée par une classe concrète
    CheckBox createCustomCheckBox(String text);

    // Méthode pour créer un label personnalisé avec un texte spécifique
    // Elle doit être implémentée par une classe concrète
    CustomLabel createCustomLabel(String text);
}
