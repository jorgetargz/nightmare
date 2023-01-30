package interfaz.screens.pantallajuego;

import game.demiurge.Demiurge;
import interfaz.screens.common.BaseScreenController;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class PantallaJuegoController extends BaseScreenController {

    private final PantallaJuegoViewModel viewModel;

    @FXML
    public Label labelTiempo;
    @FXML
    public ImageView imagenCriatura;
    @FXML
    public ImageView imagenCristales;
    @FXML
    public Label singa;
    @FXML
    public Label cristales;
    @FXML
    public Label energia;
    @FXML
    public Label vida;
    @FXML
    public Label descrpicion;
    @FXML
    public ImageView imagenFondo;

    private Demiurge demiurge;

    @Inject
    public PantallaJuegoController(PantallaJuegoViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void initialize() {

    }

    @Override
    public void principalCargado() {
       demiurge = getPrincipalController().getDemiurge();
        singa.setText("Singa: " + demiurge.getHome().getSinga());
        energia.setText("Energía: " + demiurge.getWizard().getEnergy() + "/" + demiurge.getWizard().getEnergyMax());
        vida.setText("Vida: " + demiurge.getWizard().getLife() + "/" + demiurge.getWizard().getLifeMax());
        descrpicion.setText(demiurge.getDungeon().getRoom(demiurge.getDungeon().getRoom(getPrincipalController().getCurrentRoom()).getID()).getDescription());
    }
}