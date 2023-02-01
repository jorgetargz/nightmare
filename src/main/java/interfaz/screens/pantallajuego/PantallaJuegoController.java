package interfaz.screens.pantallajuego;

import game.Domain;
import game.character.Creature;
import game.demiurge.Demiurge;
import game.dungeon.Room;
import game.dungeon.Site;
import interfaz.screens.common.BaseScreenController;
import io.github.palexdev.materialfx.controls.MFXButton;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.logging.Logger;

public class PantallaJuegoController extends BaseScreenController {

    private final PantallaJuegoViewModel viewModel;

    private Demiurge demiurge = null;

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
    public Label descripcion;
    @FXML
    public ImageView imagenFondo;
    @FXML
    public MFXButton firstRoom;
    @FXML
    public MFXButton secondRoom;
    @FXML
    public MFXButton thirdRoom;
    @FXML
    public MFXButton fourthRoom;
    @FXML
    public MFXButton botonPelea;

    Room currentRoom;
    int numberOfDoors;


    @Inject
    public PantallaJuegoController(PantallaJuegoViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void initialize() {

    }

    @Override
    public void principalCargado() {
        demiurge = getPrincipalController().getDemiurge();
        loadInitialValues();
        loadRoom();
    }

    private void loadInitialValues() {
        singa.setText("Singa: " + demiurge.getHome().getSinga());
        energia.setText("Energía: " + demiurge.getWizard().getEnergy() + "/" + demiurge.getWizard().getEnergyMax());
        vida.setText("Vida: " + demiurge.getWizard().getLife() + "/" + demiurge.getWizard().getLifeMax());
        descripcion.setText(demiurge.getDungeon().getRoom(getPrincipalController().getCurrentRoom()).getDescription());
        cristales.setText("Cristales: " + demiurge.getWizard().getCrystalCarrier().size());
    }

    private void loadRoom() {
//        demiurge.getDungeonManager().openDoor(getPrincipalController().getCurrentRoom());
        currentRoom = demiurge.getDungeon().getRoom(getPrincipalController().getCurrentRoom());
        if (!currentRoom.isVisited()) {
            demiurge.getDungeon().getRoom(getPrincipalController().getCurrentRoom()).visit();
        }
        Creature creature = currentRoom.getCreature();
        if (creature != null) {
            Domain domain = creature.getDomain();
            switch (domain) {
                case ELECTRICITY -> loadImage("/images/criaturas/criatura_01.png", imagenCriatura);
                case ENERGY -> loadImage("/images/criaturas/criatura_02.png", imagenCriatura);
                case LIFE -> loadImage("/images/criaturas/criatura_03.png", imagenCriatura);
                case FIRE -> loadImage("/images/criaturas/criatura_04.png", imagenCriatura);
                case AIR -> loadImage("/images/criaturas/criatura_05.png", imagenCriatura);
            }
            botonPelea.setDisable(false);
        } else {
            imagenCriatura.setImage(null);
            botonPelea.setDisable(true);
        }
        numberOfDoors = currentRoom.getNumberOfDoors();
        firstRoom.setVisible(numberOfDoors >= 1);
        secondRoom.setVisible(numberOfDoors >= 2);
        thirdRoom.setVisible(numberOfDoors >= 3);
        fourthRoom.setVisible(numberOfDoors >= 4);
        setButtonTexts(numberOfDoors);
    }

    private void setButtonTexts(int numberOfDoors) {
        for (int i = 0; i < numberOfDoors; i++) {
            Site room = demiurge.getDungeon().getRoom(getPrincipalController().getCurrentRoom()).openDoor(i);
            switch (i) {
                case 0 -> firstRoom.setText(room.getDescription());
                case 1 -> secondRoom.setText(room.getDescription());
                case 2 -> thirdRoom.setText(room.getDescription());
                case 3 -> fourthRoom.setText(room.getDescription());
            }
        }
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

    public void gatherCrystals() {
        if (demiurge.getDungeon().getRoom(getPrincipalController().getCurrentRoom()).isEmpty()) {
            getPrincipalController().showAlert(Alert.AlertType.ERROR, "¡No hay cristales!", "No hay cristales en esta sala");
        } else {
            demiurge.getDungeonManager().gatherCrystals();
            getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "¡Has recogido cristales!", "Cristales recogidos!");
            cristales.setText("Cristales: " + demiurge.getWizard().getCrystalCarrier().size());
        }
    }

    public void manageInventory() {
        getPrincipalController().cargarPantallaInventario();
    }

    private void recargarPantalla(int roomId) { // general method
        getPrincipalController().setCurrentRoom(roomId);
        loadRoom();
        loadInitialValues();
    }

    public void recargarPantallaFromButt1() {// puerta 1
        int roomId = getRoomIdFromDoor(numberOfDoors, 0);
        if (roomId != -12) {
            if (roomId == -1) {
                getPrincipalController().goToCasaMago();
            } else {
                recargarPantalla(roomId-1);
            }
        }
    }

    public void recargarPantallaFromButt2() { // puerta 2
        int roomId = getRoomIdFromDoor(numberOfDoors, 1);
        if (roomId != -12) {
            if (roomId == -1) {
                getPrincipalController().goToCasaMago();
            } else {
                recargarPantalla(roomId-1);
            }
        }
    }

    public void recargarPantallaFromButt3() { // puerta 3
        int roomId = getRoomIdFromDoor(numberOfDoors, 2);
        if (roomId != -12) {
            if (roomId == -1) {
                getPrincipalController().goToCasaMago();
            } else {
                recargarPantalla(roomId-1);
            }
        }
    }

    public void recargarPantallaFromButt4() { // puerta 4
        int roomId = getRoomIdFromDoor(numberOfDoors, 3);
        if (roomId != -12) {
            if (roomId == -1) {
                getPrincipalController().goToCasaMago();
            } else {
                recargarPantalla(roomId-1);
            }
        }
    }

    private int getRoomIdFromDoor(int numberOfDoors, int button) {
        int idRoom = 0;
        for (int i = 0; i < numberOfDoors; i++) {
            Site room = demiurge.getDungeon().getRoom(getPrincipalController().getCurrentRoom()).openDoor(i);
            switch (i) {
                case 0, 1, 2, 3 -> {
                    if (i == button) {
                        idRoom = room.getID();
                        i = numberOfDoors;
                    } else {
                        idRoom = -12;
                    }
                }
            }
        }
        return idRoom;
    }

    public void cargarPelea() {
        Creature c = currentRoom.getCreature();
        if (c != null && c.getLife() > 0) {
            getPrincipalController().cargarPantallaPelea();
        }
    }
}