module game {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;

    requires jakarta.inject;
    requires jakarta.cdi;
    requires java.logging;
    requires lombok;

    exports game.util;
    exports game.demiurge;
    exports game.dungeon;
    exports game.character.exceptions;
    exports game.conditions;
    exports game.demiurge.exceptions;
    exports game.spell;
    exports loaderManual;
    exports game.spellContainer;
    exports game.objectContainer.exceptions;
    exports game.object;
    exports wizardNightmare;

    exports interfaz.main;
    exports interfaz.main.common;
    exports interfaz.screens.main;
    exports interfaz.screens.common;
    exports interfaz.screens.pantallainicio;
    exports interfaz.screens.pantallajuego;
    exports interfaz.screens.pantallapeleas;
    exports interfaz.screens.casamago;

    opens interfaz.main;
    opens interfaz.main.common;
    opens interfaz.screens.main;
    opens interfaz.screens.common;
    opens interfaz.screens.pantallainicio to javafx.fxml;
    opens interfaz.screens.pantallajuego to javafx.fxml;
    opens interfaz.screens.pantallapeleas to javafx.fxml;
    opens interfaz.screens.casamago to javafx.fxml;
    opens game.dungeon;
    opens game.util;


}