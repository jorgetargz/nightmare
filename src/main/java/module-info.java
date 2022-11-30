module nightmare {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;

    requires jakarta.inject;
    requires jakarta.cdi;
    requires java.logging;

    exports nightmare.gui.main;
    exports nightmare.gui.main.common;
    exports nightmare.gui.screens.main;
    exports nightmare.gui.screens.common;

    exports nightmare.dungeon;
    exports nightmare.pj;
    exports nightmare.pnj;
    exports nightmare.items;
    exports nightmare.management;
    exports nightmare.spells;
    exports nightmare.util;
    exports nightmare;

    opens nightmare.gui.main;
    opens nightmare.gui.main.common;
    opens nightmare.gui.screens.main;
    opens nightmare.gui.screens.common;

    opens nightmare.dungeon;
    opens nightmare.pj;
    opens nightmare.pnj;
    opens nightmare.items;
    opens nightmare.management;
    opens nightmare.spells;
    opens nightmare.util;
    opens nightmare;



}