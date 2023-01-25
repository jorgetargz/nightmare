package interfaz.screens.pantallajuego;

import interfaz.screens.common.BaseScreenController;
import jakarta.inject.Inject;

public class PantallaJuegoController extends BaseScreenController {

    private final PantallaJuegoViewModel viewModel;

    @Inject
    public PantallaJuegoController(PantallaJuegoViewModel viewModel) {
        this.viewModel = viewModel;
    }
}