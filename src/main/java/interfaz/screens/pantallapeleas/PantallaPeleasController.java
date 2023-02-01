package interfaz.screens.pantallapeleas;

import game.Domain;
import game.actions.Attack;
import game.character.Creature;
import game.character.exceptions.CharacterKilledException;
import game.character.exceptions.WizardNotEnoughEnergyException;
import game.character.exceptions.WizardTiredException;
import game.demiurge.Demiurge;
import interfaz.screens.common.BaseScreenController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.logging.Logger;

public class PantallaPeleasController extends BaseScreenController {

    private Demiurge demiurge = null;

    private final ObjectProperty<Boolean> isWizardTurn;

    public PantallaPeleasController() {
        isWizardTurn = new SimpleObjectProperty<>(true);
    }

    @FXML
    private ImageView imagenMago;

    @FXML
    private ImageView imagenCriatura;

    @FXML
    private MFXButton botonHuir;

    @FXML
    private MFXButton botonVolverSala;

    @FXML
    private MFXButton botonAtacar;

    @FXML
    private MFXComboBox<Attack> comboAtaques;

    @Override
    public void principalCargado() {
        demiurge = getPrincipalController().getDemiurge();
        setUpCreature();
        demiurge.getWizard().getAttacksIterator().forEachRemaining(attack -> comboAtaques.getItems().add(attack));

        isWizardTurn.addListener((observableValue, oldState, newState) -> {
            if (newState) {
                wizardTurn();
            } else {
                creatureTurn();
            }
        });
    }

    private void wizardTurn() {
        botonHuir.setDisable(false);
        botonVolverSala.setDisable(false);
        botonAtacar.setDisable(false);
        comboAtaques.setDisable(false);
    }

    private void creatureTurn() {
        botonHuir.setDisable(true);
        botonVolverSala.setDisable(true);
        botonAtacar.setDisable(true);
        comboAtaques.setDisable(true);
        try {
            demiurge.getDungeonManager().creatureAttack();
            //show an alert with the damage done
            getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "Ataque de la criatura", "Tienes " + demiurge.getDungeonManager().wizardLifeInfo() + " puntos de vida");
            isWizardTurn.set(true);
        } catch (CharacterKilledException e) {
            getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "Fin de la pelea", "El mago ha muerto");
            getPrincipalController().goToCasaMago();
        }
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
        demiurge.getDungeon().getRoom(getPrincipalController().getCurrentRoom()).getCreature().view();
        demiurge.getDungeonManager().setCreature(creature);
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
        } else {
            getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "No se ha podido huir", "No se ha podido huir de la pelea\nTurno de la criatura");
            isWizardTurn.set(false);
        }

    }

    public void realizarAtaque() {
        if (comboAtaques.getValue() == null) {
            getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "No se ha podido atacar", "No se ha podido atacar\nNo has seleccionado un ataque");
        } else {
            try {
                demiurge.getDungeonManager().wizardAttack(comboAtaques.getValue());
                getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "Ataque del mago", "La criatura tiene " + demiurge.getDungeonManager().creatureLifeInfo() + " puntos de vida");
                isWizardTurn.set(false);
            } catch (WizardTiredException e) {
                getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "No se ha podido atacar", "No se ha podido atacar\nEl mago está cansado");
            } catch (WizardNotEnoughEnergyException e) {
                getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "No se ha podido atacar", "No se ha podido atacar\nEl mago no tiene suficiente energía");
            } catch (CharacterKilledException e) {
                getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "Fin de la pelea", "Has matado a la criatura");
                demiurge.getDungeon().getRoom(getPrincipalController().getCurrentRoom()).setCreature(null);
                getPrincipalController().cargarPantallaJuego();
            }
        }
    }
}
