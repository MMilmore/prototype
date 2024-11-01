// Classe dédiée pour les dialogues de confirmation et les actions associées aux labels
package com.qrprototype.ui;

import javafx.scene.control.Alert;  // Importe la classe Alert pour afficher des boîtes de dialogue d'alerte
import javafx.scene.control.Button;  // Importe la classe Button pour gérer les boutons dans les dialogues
import javafx.scene.control.ButtonBar;  // Importe ButtonBar pour organiser les boutons dans une boîte de dialogue
import javafx.scene.control.ButtonType;  // Importe ButtonType pour spécifier les types de boutons dans la boîte de dialogue
import javafx.scene.input.KeyCode;  // Importe KeyCode pour capturer les entrées du clavier
import javafx.scene.layout.Region;  // Importe Region pour définir la taille minimale de la boîte de dialogue
import com.qrprototype.ui.constants.UIConstants;  // Importe les constantes définies dans UIConstants pour les messages et boutons
import javafx.scene.control.TextField;  // Importe TextField pour gérer les champs de texte
import javafx.scene.layout.AnchorPane;  // Importe AnchorPane pour gérer la disposition des composants graphiques

// Classe UIDialogs qui gère l'affichage des boîtes de dialogue de confirmation et les actions sur les labels
public class UIDialogs {

    // Méthode statique pour afficher une boîte de dialogue de confirmation
    public static boolean showConfirmationDialog(String title, String header, String content) {
        // Crée une boîte de dialogue d'alerte de type CONFIRMATION
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle(title);  // Définit le titre de la boîte de dialogue
        confirmationAlert.setHeaderText(header);  // Définit l'en-tête de la boîte de dialogue
        confirmationAlert.setContentText(content);  // Définit le texte de contenu de la boîte de dialogue

        // Utilisation des constantes de UIConstants pour les textes des boutons
        ButtonType okButtonType = new ButtonType(UIConstants.DialogButtons.OK_BUTTON_TEXT, ButtonBar.ButtonData.OK_DONE);  // Définit un bouton OK avec texte constant
        ButtonType cancelButtonType = new ButtonType(UIConstants.DialogButtons.CANCEL_BUTTON_TEXT, ButtonBar.ButtonData.CANCEL_CLOSE);  // Définit un bouton Annuler avec texte constant
        confirmationAlert.getButtonTypes().setAll(okButtonType, cancelButtonType);  // Ajoute les deux boutons à la boîte de dialogue

        // Définit la hauteur minimale de la boîte de dialogue pour qu'elle ne soit pas trop petite
        confirmationAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        // Récupère les boutons OK et Annuler pour éventuellement leur appliquer des actions spécifiques
        Button okButton = (Button) confirmationAlert.getDialogPane().lookupButton(okButtonType);  // Récupère le bouton OK
        Button cancelButton = (Button) confirmationAlert.getDialogPane().lookupButton(cancelButtonType);  // Récupère le bouton Annuler

        // Ajoute un écouteur pour capturer la touche "ENTER" et déclencher les actions des boutons correspondants
        confirmationAlert.getDialogPane().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {  // Si la touche ENTER est pressée
                if (okButton.isFocused()) {  // Si le bouton OK est en focus
                    okButton.fire();  // Déclenche l'action associée au bouton OK
                } else if (cancelButton.isFocused()) {  // Si le bouton Annuler est en focus
                    cancelButton.fire();  // Déclenche l'action associée au bouton Annuler
                }
            }
        });

        // Attend la réponse de l'utilisateur et renvoie true si le bouton OK est cliqué
        return confirmationAlert.showAndWait().filter(response -> response == okButtonType).isPresent();
    }

    // Méthode générique pour gérer la modification ou la suppression d'un label
public static void handleLabelAction(CustomLabel label, TextField textField, String title, String header, String content, boolean isDeleteAction) {
    if (showConfirmationDialog(title, header, content)) {
        if (isDeleteAction) {
            AnchorPane parentPane = (AnchorPane) label.getParent();  // Récupère l'AnchorPane parent du label
            parentPane.getChildren().remove(label);  // Supprime le label de l'AnchorPane

            // Si l'AnchorPane ne contient plus d'autres labels (ou seulement un champ de texte), réinitialiser le TextField
            if (parentPane.getChildren().isEmpty() || parentPane.getChildren().size() == 1) {
                textField.clear();  // Vide le texte du champ de texte
                textField.setVisible(true);  // Rend le champ de texte visible
                textField.requestFocus();  // Donne le focus au champ de texte
            }
        } else {
            // Logique de modification (le champ de texte se réaffiche avec le texte du label)
            textField.setText(label.getText());
            textField.setVisible(true);
            textField.requestFocus();
            ((AnchorPane) label.getParent()).getChildren().remove(label);
        }
    }
}
}