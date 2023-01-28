package interfaz.screens.common;

public enum Screens {

    WELCOME(ScreenConstants.FXML_WELCOME_SCREEN_FXML),
    LOGIN(ScreenConstants.FXML_LOGIN_SCREEN_FXML),
    MAIN("/fxml/mainScreen.fxml"),
    INICIO("/fxml/partida/inicio/pantallaInicio.fxml"),
    CASAPRINCIPAL("/fxml/casaMago/casaPrincipal.fxml"),
    ALMACEN("/fxml/casaMago/almacen.fxml"),
    BIBLIOTECA("/fxml/casaMago/biblioteca.fxml"),
    PANTALLAJUEGO("/fxml/juego/pantallaJuego.fxml"),
    PANTALLAPELEAS("/fxml/juego/pantallaPeleas.fxml"),
    BBDD("/fxml/partida/bbdd/bbdd.fxml");

    private final String path;

    Screens(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }


}
