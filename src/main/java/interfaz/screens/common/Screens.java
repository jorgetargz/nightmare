package interfaz.screens.common;

public enum Screens {

    MAIN("/fxml/mainScreen.fxml"),
    INICIO("/fxml/partida/inicio/pantallaInicio.fxml"),
    CASA("/fxml/casaMago/casaPrincipal.fxml"),
    PANTALLAJUEGO("/fxml/juego/pantallaJuego.fxml"),
    PANTALLAPELEAS("/fxml/juego/pantallaPeleas.fxml"),
    BBDD("/fxml/partida/bbdd/bbdd.fxml"),
    INVENTARIO("/fxml/juego/pantallaInventario.fxml");

    private final String path;

    Screens(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }


}
