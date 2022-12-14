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
    exports game.dungeon.spell;
    exports game.dungeon.structure;
    exports game.dungeon.structure.exceptions;
    exports game.dungeon.object.exceptions;
    exports game.character.exceptions;
    exports game.gui.screens.main;
    exports wizardNightmare;

    opens game.gui.main;
    opens game.gui.main.common;
    opens game.gui.screens.main to javafx.fxml;
    opens game.gui.screens.common;
    opens game.dungeon;
    opens game.util;


}