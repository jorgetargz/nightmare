package interfaz.screens.casamago;

import game.character.exceptions.WizardNotEnoughEnergyException;
import game.character.exceptions.WizardTiredException;
import game.demiurge.Demiurge;
import game.demiurge.DemiurgeHomeManager;
import game.dungeon.HomeNotEnoughSingaException;
import game.objectContainer.Container;
import game.objectContainer.exceptions.ContainerEmptyException;
import game.objectContainer.exceptions.ContainerErrorException;
import game.util.ValueOverMaxException;
import interfaz.screens.common.BaseScreenController;
import interfaz.screens.common.Screens;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

public class CasaPrincipalController extends BaseScreenController {

    private Demiurge demiurge = null;

    @FXML
    private Label wizardInfoLabel;
    @FXML
    private Label homeInfoLabel;

    @Override
    public void principalCargado() {
        demiurge = getPrincipalController().getDemiurge();
        loadInfo();
    }

    private void loadInfo() {
        wizardInfoLabel.setText("Bienvenido, " + demiurge.getDungeonManager().wizardInfo() + ".");
        homeInfoLabel.setText(demiurge.getHomeManager().homeInfo());
    }

    @FXML
    private void goDungeon() {
        getPrincipalController().cargarPantalla(Screens.PANTALLAJUEGO);
    }

    @FXML
    private void sleep() {
        demiurge.nextDay();
        getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "Dormir", "Has dormido una noche \ny has pasado al día " + demiurge.getDay());
    }

    @FXML
    private void recoverLife() {
        DemiurgeHomeManager homeManager = demiurge.getHomeManager();
        if (homeManager.getLife() == homeManager.getLifeMax()) {
            getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "Recuperar vida", "Ya tienes la vida al máximo.");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Recuperar vida");
            alert.setHeaderText("¿Estás seguro de que quieres recuperar vida?");
            alert.setContentText("Tienes " + homeManager.getLife() + " de " + homeManager.getLifeMax() + " puntos de vida. " +
                    "\nEl coste de recuperar es de " + homeManager.getSingaPerLifePoint() + " singa " +
                    "\nactualmente tienes " + homeManager.getSinga() + " singa. ");
            alert.getButtonTypes().clear();
            alert.getButtonTypes().add(ButtonType.YES);
            alert.getButtonTypes().add(ButtonType.NO);
            alert.showAndWait() // wait for the user to click a button
                    .filter(response -> response == ButtonType.YES)
                    .ifPresent(response -> {
                        try {
                            homeManager.recover(1);
                            getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "Recuperar vida", "Has recuperado 1 punto de vida.");
                        } catch (HomeNotEnoughSingaException e) {
                            getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "Recuperar vida", "No tienes suficiente singa.");
                        } catch (ValueOverMaxException e) {
                            getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "Recuperar vida", "Ya tienes la vida al máximo.");
                        } catch (WizardTiredException e) {
                            sleep();
                        }
                        loadInfo();
                    });
        }
    }

    @FXML
    private void mergeCrystals() {
        DemiurgeHomeManager homeManager = demiurge.getHomeManager();
        Container carrier = homeManager.getCarrier();
        int crystals = carrier.size();
        if (crystals == 0) {
            getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "Fusionar cristales", "No tienes cristales para fusionar.");
        } else if (homeManager.getSingaSpace() == 0) {
            getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "Fusionar cristales", "Tu almacen de singa está lleno.");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Fusionar cristales");
            alert.setHeaderText("¿Estás seguro de que quieres fusionar cristales?");
            alert.setContentText("Tienes " + crystals + " cristales. " +
                    "\nActualmente tienes " + homeManager.getSinga() + " singa. " +
                    "\nActualmente tienes " + homeManager.getSingaSpace() + " espacio libre en tu almacen de singa. ");
            alert.getButtonTypes().clear();
            alert.getButtonTypes().add(ButtonType.YES);
            alert.getButtonTypes().add(ButtonType.NO);
            alert.showAndWait() // wait for the user to click a button
                    .filter(response -> response == ButtonType.YES)
                    .ifPresent(response -> {
                        try {
                            homeManager.mergeCrystal(0);
                            getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "Fusionar cristales", "Has fusionado 1 cristal.");
                        } catch (ContainerEmptyException e) {
                            getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "Fusionar cristales", "No tienes cristales para fusionar.");
                        } catch (ContainerErrorException e) {
                            getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "Fusionar cristales", "No se ha podido fusionar el cristal.");
                        } catch (WizardTiredException e) {
                            sleep();
                        }
                        loadInfo();
                    });
        }
    }

    @FXML
    private void upgradeMaxLife() {
        try {
            demiurge.getHomeManager().upgradeLifeMax();
        } catch (HomeNotEnoughSingaException e) {
            getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "Upgrade max life", "No tienes suficiente singa.");
        } catch (WizardNotEnoughEnergyException e) {
            getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "Upgrade max life", "No tienes suficiente energía.");
        } catch (WizardTiredException e) {
            sleep();
        }
    }

    @FXML
    private void upgradeEnergyMax() {
        try {
            demiurge.getHomeManager().upgradeEnergyMax();
        } catch (HomeNotEnoughSingaException e) {
            getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "Upgrade max energy", "No tienes suficiente singa.");
        } catch (WizardNotEnoughEnergyException e) {
            getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "Upgrade max energy", "No tienes suficiente energía.");
        } catch (WizardTiredException e) {
            sleep();
        }
    }

    @FXML
    private void upgradeComfort() {
        try {
            demiurge.getHomeManager().upgradeComfort();
        } catch (HomeNotEnoughSingaException e) {
            getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "Upgrade comfort", "No tienes suficiente singa.");
        } catch (WizardNotEnoughEnergyException e) {
            getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "Upgrade comfort", "No tienes suficiente energía.");
        } catch (WizardTiredException e) {
            sleep();
        }
    }

    @FXML
    private void upgradeSingaMax() {
        try {
            demiurge.getHomeManager().upgradeSingaMax();
        } catch (HomeNotEnoughSingaException e) {
            getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "Upgrade max singa", "No tienes suficiente singa.");
        } catch (WizardNotEnoughEnergyException e) {
            getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "Upgrade max singa", "No tienes suficiente energía.");
        } catch (WizardTiredException e) {
            sleep();
        }
    }

}