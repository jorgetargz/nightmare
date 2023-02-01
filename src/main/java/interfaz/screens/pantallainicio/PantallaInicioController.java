package interfaz.screens.pantallainicio;

import game.demiurge.Demiurge;
import interfaz.screens.common.BaseScreenController;
import javafx.scene.control.Alert;

public class PantallaInicioController extends BaseScreenController {

    private Demiurge demiurge = null;


    public void initialize() {
    }

    @Override
    public void principalCargado() {
        demiurge = getPrincipalController().getDemiurge();
    }

    public void empezarPartida() {
        if (demiurge.getDungeon() == null) {
            getPrincipalController().showAlert(Alert.AlertType.ERROR, "Error", "No se ha cargado el mapa");
            return;
        }
        this.getPrincipalController().cargarPantallaJuego();
    }
}
