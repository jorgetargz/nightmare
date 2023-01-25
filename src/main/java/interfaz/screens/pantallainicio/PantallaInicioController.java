package interfaz.screens.pantallainicio;

import interfaz.screens.common.BaseScreenController;

public class PantallaInicioController extends BaseScreenController {
    public void empezarPartida() {
        this.getPrincipalController().cargarPantallaJuego();
    }
}
