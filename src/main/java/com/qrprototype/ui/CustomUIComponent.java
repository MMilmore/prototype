package com.qrprototype.ui; // Déclare le package où se trouve la classe

// Interface CustomUIComponent
// Cette interface définit deux méthodes : render() et applyStyles().
// Elle est destinée à être implémentée par des composants UI personnalisés qui nécessitent un rendu spécifique et l'application de styles CSS.

public interface CustomUIComponent {

    // Méthode pour rendre le composant à l'écran
    // Chaque composant qui implémente cette interface doit fournir sa propre logique de rendu spécifique à l'écran.
    void render();  // Interface sans implémentation, c'est aux classes implémentant de définir cette méthode

    // Méthode pour appliquer les styles CSS au composant
    // Chaque composant implémentant cette interface devra définir comment les styles CSS doivent être appliqués.
    void applyStyles();  // Interface sans implémentation, à définir dans les classes implémentant
}
