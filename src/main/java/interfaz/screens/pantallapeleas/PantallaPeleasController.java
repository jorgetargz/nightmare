package interfaz.screens.pantallapeleas;

import game.Domain;
import game.character.Creature;
import game.demiurge.Demiurge;
import interfaz.screens.common.BaseScreenController;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.logging.Logger;

public class PantallaPeleasController extends BaseScreenController {

    private Demiurge demiurge = null;
    
    private ObjectProperty<Boolean> isWizardTurn;

    @FXML
    private ImageView imagenMago;

    @FXML
    private ImageView imagenCriatura;

    @FXML
    private MFXButton botonHuir;

    @FXML
    private MFXButton botonVolverSala;

    @Override
    public void principalCargado() {
        demiurge = getPrincipalController().getDemiurge();
        setUpCreature();
        isWizardTurn.addListener ((observableValue, oldState, newState) -> {
            if (newState) {
                
            } else  {
                
            }
        });
    }

    private void setUpCreature() {
        Creature creature = demiurge.getDungeon().getRoom(getPrincipalController().getCurrentRoom()).getCreature();
        if (creature != null) {
            Domain domain = creature.getDomain();
            switch (domain) {
                case ELECTRICITY -> loadImage("/images/criaturas/criatura_01.png", imagenCriatura);
                case ENERGY -> loadImage("/images/criaturas/criatura_02.png", imagenCriatura);
                case LIFE -> loadImage("/images/criaturas/criatura_03.png", imagenCriatura);
                case FIRE -> loadImage("/images/criaturas/criatura_04.png", imagenCriatura);
                case AIR -> loadImage("/images/criaturas/criatura_05.png", imagenCriatura);
            }
        } else {
            getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "error", "La sala no tiene una criatura");
        }
        isWizardTurn.set(true);
    }

    private void loadImage(String path, ImageView imageView) {
        try (var inputStream = getClass().getResourceAsStream(path)) {
            assert inputStream != null;
            Image logoImage = new Image(inputStream);
            imageView.setImage(logoImage);
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).severe(e.getMessage());
        }
    }

    public void huir() {
        if (demiurge.getDungeonManager().canRunAway()) {
            getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "Fin de la pelea", "Has escapado de la pelea");
            getPrincipalController().cargarPantallaJuego();


            // TODO: 21.00 hacer la pelea entera jejeje
        }

    }


    //run
    //attack --> selectAttack
}
