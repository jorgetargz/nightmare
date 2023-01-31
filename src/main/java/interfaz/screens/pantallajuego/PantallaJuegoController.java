package interfaz.screens.pantallajuego;

import console.ConsoleContainerManager;
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

import javax.swing.*;
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
        descripcion.setText(demiurge.getDungeon().getRoom(demiurge.getDungeon().getRoom(getPrincipalController().getCurrentRoom()).getID()).getDescription());
        cristales.setText("Cristales: " + demiurge.getWizard().getCrystalCarrier().size());
    }

    private void loadRoom() {
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
            try {
                Thread.sleep(2000);
                cargarPelea();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            imagenCriatura.setImage(null);
        }
        numberOfDoors = currentRoom.getNumberOfDoors();
        firstRoom.setVisible(numberOfDoors < 1);
        secondRoom.setVisible(numberOfDoors < 2);
        thirdRoom.setVisible(numberOfDoors < 3);
        fourthRoom.setVisible(numberOfDoors < 4);
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
        demiurge.getDungeon().getRoom(getPrincipalController().getCurrentRoom()).gather();
        getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "¡Has recogido cristales!", "Cristales recogidos!");
    }

    public void manageInventory() {
        // ConsoleContainerManager

        // TODO: de este método queda hacer la pantalla donde se pueda acceder a todos los métodos y hacer lo siguiente: Jimena 31.01
        // 1. delete from jewelry bag
        // 2. take from room
        // 3. take from jewelry bag
        // 4. drop in room
        // 5. leave in the jewelry bag
        // 6. exchange between wizard and room
        // 7. exchange between wizard and jewelry bag
    }

    private void cargarPelea() {
        Creature c = currentRoom.getCreature();

        if (c != null && c.getLife() > 0) {  // importante, cuando se vuelva de la pantalla pelea criatura vida = 0
            JOptionPane.showMessageDialog(null, "You, wizard, must fight with " + c.getName() + " !", "Fight!", JOptionPane.WARNING_MESSAGE);
            try {
                Thread.sleep(2000);
                getPrincipalController().cargarPantallaPelea();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void recargarPantalla(int roomId) { // general method
        getPrincipalController().setCurrentRoom(roomId);
        loadRoom();
    }

    public void recargarPantallaFromButt1() {// puerta 1
        int roomId = getRoomIdFromDoor(numberOfDoors, 0);
        if (roomId != -12) {
            recargarPantalla(roomId);
        }
    }

    public void recargarPantallaFromButt2() { // puerta 2
        int roomId = getRoomIdFromDoor(numberOfDoors, 1);
        if (roomId != -12) {
            recargarPantalla(roomId);
        }
    }

    public void recargarPantallaFromButt3() { // puerta 3
        int roomId = getRoomIdFromDoor(numberOfDoors, 2);
        if (roomId != -12) {
            recargarPantalla(roomId);
        }
    }

    public void recargarPantallaFromButt4() { // puerta 4
        int roomId = getRoomIdFromDoor(numberOfDoors, 3);
        if (roomId != -12) {
            recargarPantalla(roomId);
        }
    }

    private int getRoomIdFromDoor(int numberOfDoors, int button) {
        int idRoom = -12;
        for (int i = 0; i < numberOfDoors; i++) {
            Site room = demiurge.getDungeon().getRoom(getPrincipalController().getCurrentRoom()).openDoor(i);
            switch (i) {
                case 0, 1, 2, 3 -> {
                    if (i == button) {
                        idRoom = room.getID();
                    } else {
                        idRoom = -12;
                    }
                }
            }
        }
        return idRoom;
    }
}