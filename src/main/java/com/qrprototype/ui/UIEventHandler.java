// Classe qui gère les événements d'interface utilisateur (clics, focus, etc.)
package com.qrprototype.ui;

// Import des classes nécessaires pour la gestion des événements et des composants graphiques
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.util.Comparator;
import java.util.Optional;

import com.qrprototype.ui.constants.UIConstants;
import javafx.scene.Node;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;

// Classe UIEventHandler qui gère les événements d'interface utilisateur
public class UIEventHandler {

    // Méthode générique pour gérer les événements sur n'importe quel Node
    public static <T extends Event> void handleEvent(Node node, EventType<T> eventType, EventHandler<? super T> eventHandler) {
        node.addEventHandler(eventType, eventHandler);
    }

    // Gestion de la perte de focus et du clic extérieur pour éviter les conflits
    public static void handleFocusLoss(TextField textField, AnchorPane anchorRoot) {
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue && !textField.getText().isEmpty()) {
                // Valide uniquement si le focus est perdu et que le texte n'est pas vide
                validateAndConvertToLabel(textField, anchorRoot);
            }
        });
    }

    // Méthode générique pour gérer les clics sur un Node
    public static void handleMouseClick(Node node, Runnable action) {
        handleEvent(node, javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> action.run());
    }

    // Gestion du clic sur l'AnchorPane pour valider la saisie
    public static void handlePaneClickForValidation(AnchorPane pane, TextField textField, AnchorPane anchorRoot) {
        pane.setOnMouseClicked(event -> {
            if (textField.isFocused()) {
                // Retirer le focus du TextField avant de valider
                textField.getParent().requestFocus();
                if (!textField.getText().isEmpty()) {
                    // Valider après avoir retiré le focus pour éviter le double appel
                    validateAndConvertToLabel(textField, anchorRoot);
                }
            }
        });
    }

    // Méthode pour gérer l'affichage du menu contextuel sur un CustomLabel
    public static void handleContextMenu(CustomLabel label, ContextMenu contextMenu) {
        handleEvent(label, javafx.scene.input.ContextMenuEvent.CONTEXT_MENU_REQUESTED,
            event -> contextMenu.show(label, event.getScreenX(), event.getScreenY()));
    }

    public static void validateAndConvertToLabel(TextField textField, AnchorPane anchorRoot) {
        String text = textField.getText();  // Récupère le texte saisi dans le TextField
        if (!text.isEmpty()) {
            CustomLabel label = LabelFactory.createLabel(text);  // Crée un CustomLabel avec le texte saisi
            UIStyler.applyCustomLabelStyle(label);
    
            // Positionner le label à l'emplacement du TextField
            AnchorPane.setTopAnchor(label, AnchorPane.getTopAnchor(textField));
            AnchorPane.setLeftAnchor(label, AnchorPane.getLeftAnchor(textField));
    
            // Ajouter le label à l'AnchorPane parent du TextField
            anchorRoot.getChildren().add(label);
    
            // Création du menu contextuel avec les options "Modifier" et "Supprimer"
            ContextMenu contextMenu = new ContextMenu();
            MenuItem addTextFieldItem = new MenuItem(UIConstants.ADD_MENU_ITEM);
            MenuItem modifyItem = new MenuItem(UIConstants.MODIFY_MENU_ITEM);
            MenuItem deleteItem = new MenuItem(UIConstants.DELETE_MENU_ITEM);
            contextMenu.getItems().addAll(addTextFieldItem, modifyItem, deleteItem);
    
            // Ajout du gestionnaire du menu contextuel
            UIEventHandler.handleContextMenu(label, contextMenu);
    
            addTextFieldItem.setOnAction(event -> {
                // Calculer la position verticale pour le nouveau TextField
                double lastTopAnchor = anchorRoot.getChildren().stream()
                    .filter(node -> node instanceof TextField)
                    .mapToDouble(node -> AnchorPane.getTopAnchor((TextField) node))
                    .max().orElse(UIConstants.TOP_ANCHOR);
            
                double newTopAnchor = lastTopAnchor + UIConstants.TEXT_FIELD_TOP_ANCHOR_OFFSET;
            
                // Appeler la méthode pour ajouter un nouveau TextField avec la nouvelle position calculée
                addNewTextField(anchorRoot, textField, newTopAnchor);
            });
    
            // Action sur "Modifier"
            modifyItem.setOnAction(event -> {
                UIDialogs.handleLabelAction(label, textField, UIConstants.DialogMessages.MODIFY_CONFIRMATION_TITLE,
                        UIConstants.DialogMessages.MODIFY_CONFIRMATION_HEADER, UIConstants.DialogMessages.MODIFY_CONFIRMATION_CONTENT, false);
            });
    
            // Action sur "Supprimer"
            deleteItem.setOnAction(event -> {
                // Gestion du dialogue de confirmation pour la suppression
                UIDialogs.handleLabelAction(label, textField, UIConstants.DialogMessages.DELETE_CONFIRMATION_TITLE,
                        UIConstants.DialogMessages.DELETE_CONFIRMATION_HEADER, UIConstants.DialogMessages.DELETE_CONFIRMATION_CONTENT, true);

                // Supprimez le label sélectionné
                anchorRoot.getChildren().remove(label);

                // Réorganiser les labels restants après la suppression
                double removedFieldTopAnchor = AnchorPane.getTopAnchor(label);
                for (Node node : anchorRoot.getChildren()) {
                    if (node instanceof CustomLabel) {
                        CustomLabel remainingLabel = (CustomLabel) node;
                        Double currentTopAnchor = AnchorPane.getTopAnchor(remainingLabel);

                        // Si un label est situé sous celui supprimé, le déplacer vers le haut
                        if (currentTopAnchor != null && currentTopAnchor > removedFieldTopAnchor) {
                            AnchorPane.setTopAnchor(remainingLabel, currentTopAnchor - UIConstants.TEXT_FIELD_TOP_ANCHOR_OFFSET);
                        }
                    }
                }

                // Gérer la suppression de la catégorie initiale
                double labelTopAnchor = AnchorPane.getTopAnchor(label);
                double initialTextFieldTopAnchor = AnchorPane.getTopAnchor(MainUI.initialTextField);

                // Si le label supprimé est à la même position que initialTextField, alors il s'agit de la catégorie initiale
                if (labelTopAnchor == initialTextFieldTopAnchor) {
                    // Si la catégorie supprimée est la catégorie initiale, réassigner les caractéristiques à la catégorie juste en dessous
                    UIEventHandler.handleInitialTextFieldDeleteAction(anchorRoot, MainUI.initialTextField);
                }

                // Vérifiez s'il n'y a plus de labels après la suppression
                boolean hasOtherLabels = anchorRoot.getChildren().stream().anyMatch(node -> node instanceof CustomLabel);

                // Si aucun autre label n'existe, rendre le champ de texte visible
                if (!hasOtherLabels) {
                    textField.setVisible(true);  // Réaffichez le champ de texte initial si tous les labels sont supprimés
                    UIEventHandler.handlePaneClickForValidation(anchorRoot, textField, anchorRoot);
                }
            });

            // Cache le TextField après validation
            textField.setVisible(false);
            textField.clear();
        }
    }

    public static void addNewTextField(AnchorPane anchorPane, TextField referenceTextField, double newTopAnchor) {
        // Obtenez la position actuelle du TextField de référence
        double referenceTopAnchor = AnchorPane.getTopAnchor(referenceTextField);
    
        // Calculer la nouvelle position verticale en fonction du TextField de référence
        double newCalculatedTopAnchor = referenceTopAnchor + UIConstants.TEXT_FIELD_TOP_ANCHOR_OFFSET;
    
        // Créer un nouveau champ de texte
        TextField newTextField = new TextField();
        newTextField.setPromptText(referenceTextField.getPromptText());  // Copier le placeholder du TextField de référence
        newTextField.setStyle(referenceTextField.getStyle());  // Appliquer le même style
    
        // Positionner le nouveau champ de texte en dessous du TextField de référence
        AnchorPane.setTopAnchor(newTextField, newCalculatedTopAnchor);
        AnchorPane.setLeftAnchor(newTextField, AnchorPane.getLeftAnchor(referenceTextField));
    
        // Ajouter le nouveau TextField à l'AnchorPane
        anchorPane.getChildren().add(newTextField);
    
        // Redonner le focus au nouveau champ de texte
        newTextField.requestFocus();
    
        // Unification de la validation avec la touche "Enter" et la perte de focus
        newTextField.setOnAction(event -> {
            validateAndConvertToLabel(newTextField, anchorPane);
        });
    
        // Ajouter la gestion du clic extérieur pour valider ce champ de texte
        handlePaneClickForValidation(anchorPane, newTextField, anchorPane);
    
        // Appliquer le style défini dans UIStyler pour ce nouveau champ
        UIStyler.applyTextFieldDefaultStyle(newTextField);
    }

    public static void moveCategoriesDown(AnchorPane anchorPane, TextField referenceTextField) {
        // Obtenez la position en haut de la catégorie de référence
        double referenceTopAnchor = AnchorPane.getTopAnchor(referenceTextField);
    
        // Parcourir tous les enfants de l'AnchorPane
        for (Node node : anchorPane.getChildren()) {
            if (node instanceof TextField && node != referenceTextField) {
                TextField textField = (TextField) node;
                Double currentTopAnchor = AnchorPane.getTopAnchor(textField);
    
                // Si le TextField est en dessous de la catégorie de référence, le déplacer vers le bas
                if (currentTopAnchor != null && currentTopAnchor > referenceTopAnchor) {
                    AnchorPane.setTopAnchor(textField, currentTopAnchor + UIConstants.TEXT_FIELD_TOP_ANCHOR_OFFSET);
                }
            }
        }
    }

    public static void moveCategoriesUp(AnchorPane anchorPane, Node removedNode) {
        double removedFieldTopAnchor = AnchorPane.getTopAnchor(removedNode);

        for (Node node : anchorPane.getChildren()) {
            if (node instanceof CustomLabel || node instanceof TextField) {
                Double currentTopAnchor = AnchorPane.getTopAnchor(node);

                // Si un élément est situé sous celui supprimé, le déplacer vers le haut
                if (currentTopAnchor != null && currentTopAnchor > removedFieldTopAnchor) {
                    AnchorPane.setTopAnchor(node, currentTopAnchor - UIConstants.TEXT_FIELD_TOP_ANCHOR_OFFSET);
                }
            }
        }
    }

    public static void handleInitialTextFieldDeleteAction(AnchorPane anchorPane, TextField initialTextField) {
        if (!anchorPane.getChildren().contains(initialTextField)) {
            // Rechercher la catégorie suivante
            Optional<Node> nextTextField = anchorPane.getChildren().stream()
                .filter(node -> node instanceof TextField)
                .min(Comparator.comparingDouble(node -> AnchorPane.getTopAnchor((TextField) node)));

            if (nextTextField.isPresent()) {
                TextField newInitialTextField = (TextField) nextTextField.get();
                AnchorPane.setTopAnchor(newInitialTextField, UIConstants.TOP_ANCHOR);
                AnchorPane.setLeftAnchor(newInitialTextField, UIConstants.LEFT_ANCHOR);
                
                // Utiliser le placeholder existant pour la nouvelle catégorie initiale
                newInitialTextField.setPromptText(UIConstants.CREATE_CATEGORY_PLACEHOLDER);
                // Appliquer le style générique via UIStyler
                UIStyler.applyTextFieldDefaultStyle(newInitialTextField);

                // Mettre à jour la référence du champ de texte initial
                MainUI.initialTextField = newInitialTextField;
            }
        }
    }
}
