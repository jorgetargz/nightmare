package game.gui.screens.common;

public enum Screens {

    WELCOME(ScreenConstants.FXML_WELCOME_SCREEN_FXML),
    LOGIN(ScreenConstants.FXML_LOGIN_SCREEN_FXML),
    NEWSPAPER_LIST(ScreenConstants.FXML_NEWSPAPER_LIST_SCREEN_FXML),
    NEWSPAPER_DELETE(ScreenConstants.FXML_NEWSPAPER_DELETE_SCREEN_FXML),
    ARTICLE_LIST(ScreenConstants.FXML_ARTICLE_LIST_SCREEN_FXML),
    ARTICLE_ADD(ScreenConstants.FXML_ARTICLE_ADD_SCREEN_FXML),
    READER_LIST(ScreenConstants.FXML_READER_LIST_SCREEN_FXML),
    READER_DELETE(ScreenConstants.FXML_READER_DELETE_SCREEN_FXML),
    SUBSCRIPTION_LIST(ScreenConstants.FXML_SUBSCRIPTION_LIST_SCREEN_FXML),
    RATING_LIST(ScreenConstants.FXML_RATING_LIST_SCREEN_FXML),
    RATING_ADD(ScreenConstants.FXML_RATING_ADD_SCREEN_FXML),

    NUEVAPARTIDA();

    private final String path;

    Screens(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }


}
