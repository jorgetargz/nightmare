package game.gui.screens.common;

import game.gui.screens.main.MainController;


public class BaseScreenController {

    private MainController mainController;

    public MainController getPrincipalController() {
        return mainController;
    }

    public void setPrincipalController(MainController mainController) {
        this.mainController = mainController;
    }

    public void principalCargado() {

    }
}
