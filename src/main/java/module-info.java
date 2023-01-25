module game {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;

    requires jakarta.inject;
    requires jakarta.cdi;
    requires java.logging;

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

    exports game.gui.main;
    exports game.gui.screens.main;

    opens game.gui.main;
    opens game.gui.main.common;
    opens game.gui.screens.main to javafx.fxml;
    opens game.gui.screens.common;
    opens game.dungeon;
    opens game.util;


}