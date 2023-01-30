package interfaz.screens.pantallapeleas;

import game.demiurge.Demiurge;
import interfaz.screens.common.BaseScreenController;

public class PantallaPeleasController extends BaseScreenController {

    private Demiurge demiurge = null;

    @Override
    public void principalCargado() {
        demiurge = getPrincipalController().getDemiurge();
    }

    //run
    //attack --> selectAttack
}
