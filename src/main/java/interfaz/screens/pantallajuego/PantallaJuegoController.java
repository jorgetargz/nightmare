package interfaz.screens.pantallajuego;

import interfaz.screens.almacenParaGuardarCambiosPantallas.Reciclaje;
import interfaz.screens.common.BaseScreenController;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class PantallaJuegoController extends BaseScreenController {

    private final PantallaJuegoViewModel viewModel;
    private final Reciclaje reciclaje;

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

    @Inject
    public PantallaJuegoController(PantallaJuegoViewModel viewModel, Reciclaje reciclaje) {
        this.viewModel = viewModel;
        this.reciclaje = reciclaje;
    }

    public void initialize() {
        singa.setText("Singa: " + reciclaje.getHome().getSinga());
        energia.setText("Energía: " + reciclaje.getWizard().getEnergy() + "/" + reciclaje.getWizard().getEnergyMax());
        vida.setText("Vida: " + reciclaje.getWizard().getLife() + "/" + reciclaje.getWizard().getLifeMax());
        descrpicion.setText(reciclaje.getDungeon().getRoom(reciclaje.getCurrentRoom()).getDescription());
    }


}