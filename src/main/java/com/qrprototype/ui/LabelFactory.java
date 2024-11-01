package com.qrprototype.ui;  // Déclare le package où se trouve la classe

// Classe LabelFactory, responsable de la création de CustomLabel
public class LabelFactory {

    // Méthode statique qui crée et retourne une instance de CustomLabel
    public static CustomLabel createLabel(String text) {
        return new CustomLabel(text);  // Retourne une nouvelle instance de CustomLabel avec le texte fourni
    }
}
