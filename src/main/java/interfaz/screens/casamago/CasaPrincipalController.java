package interfaz.screens.casamago;

import game.character.exceptions.WizardNotEnoughEnergyException;
import game.character.exceptions.WizardTiredException;
import game.demiurge.Demiurge;
import game.demiurge.DemiurgeContainerManager;
import game.demiurge.DemiurgeHomeManager;
import game.demiurge.exceptions.EndGameException;
import game.dungeon.HomeNotEnoughSingaException;
import game.objectContainer.Container;
import game.objectContainer.exceptions.*;
import game.util.ValueOverMaxException;
import interfaz.screens.common.BaseScreenController;
import interfaz.screens.common.Screens;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
        try {
            demiurge.getDungeonManager().enterDungeon();
            getPrincipalController().setCurrentRoom(0);
        } catch (WizardTiredException e) {
            sleep();
        } catch (EndGameException e) {
            getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "Fin del juego", "Has llegado al final del juego.");
        }
        getPrincipalController().cargarPantalla(Screens.PANTALLAJUEGO);
    }

    @FXML
    private void sleep() {
        demiurge.nextDay();
        getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "Dormir", "Has dormido una noche \ny has pasado al día " + demiurge.getDay());
        loadInfo();
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

    @FXML
    private void spells() {
        ButtonType improveSpell = new ButtonType("Improve a spell");
        ButtonType learnSpell = new ButtonType("Learn a spell");
        Alert alert;
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Spells");
        alert.setHeaderText("Manage spells");
        alert.setContentText("Select an option:");
        alert.getButtonTypes().clear();
        alert.getButtonTypes().add(improveSpell);
        alert.getButtonTypes().add(learnSpell);
        alert.showAndWait() // wait for the user to click a button
                .filter(response -> response == ButtonType.YES)
                .ifPresent(response -> {
                    if (response == improveSpell) {
                        getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "Spells", "Not implemented yet.");
                    } else if (response == learnSpell) {
                        getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "Spells", "Not implemented yet.");
                    }
                });
    }

    @FXML
    private void storage() {
        DemiurgeContainerManager containerManager = demiurge.getContainerManager();

        ButtonType deleteFromStorageButtonType = new ButtonType("Delete from storage (item will disappear)");
        ButtonType deleteFromJewelryBagButtonType = new ButtonType("Delete from jewelry bag (item will disappear)");
        ButtonType takeFromStorageButtonType = new ButtonType("Take from storage");
        ButtonType takeFromJewelryBagButtonType = new ButtonType("Take from jewelry bag");
        ButtonType leaveInTheChestButtonType = new ButtonType("Leave in the chest");
        ButtonType leaveInTheJewelryBagButtonType = new ButtonType("Leave in the jewelry bag");
        ButtonType exchangeBetweenWizardAndChestButtonType = new ButtonType("Exchange between wizard and chest");
        ButtonType exchangeBetweenWizardAndJewelryBagButtonType = new ButtonType("Exchange between wizard and jewelry bag");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Items");
        alert.setHeaderText("Manage items");
        alert.setContentText("Select an option:");
        alert.getButtonTypes().clear();

        DialogPane dialogPane = alert.getDialogPane();

        Button deleteFromStorage = new Button(deleteFromStorageButtonType.getText());
        deleteFromStorage.setOnAction(event -> {
            menuContainerDelete(containerManager.getSite());
            alert.close();
        });

        Button deleteFromJewelryBag = new Button(deleteFromJewelryBagButtonType.getText());
        deleteFromJewelryBag.setOnAction(event -> {
            menuContainerDelete(containerManager.getBag());
            alert.close();
        });

        Button takeFromStorage = new Button(takeFromStorageButtonType.getText());
        takeFromStorage.setOnAction(event -> {
            try {
                menuContainerAdd(containerManager.getSite(), containerManager.getWearables());
            } catch (ContainerUnacceptedItemException e) {
                getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "Items", "You can't take this item.");
            } catch (ContainerFullException e) {
                getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "Items", "Your bag is full.");
            }
            alert.close();
        });

        Button takeFromJewelryBag = new Button(takeFromJewelryBagButtonType.getText());
        takeFromJewelryBag.setOnAction(event -> {
            try {
                menuContainerAdd(containerManager.getBag(), containerManager.getWearables());
            } catch (ContainerUnacceptedItemException e) {
                getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "Items", "You can't take this item.");
            } catch (ContainerFullException e) {
                getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "Items", "Your bag is full.");
            }
            alert.close();
        });

        Button leaveInTheChest = new Button(leaveInTheChestButtonType.getText());
        leaveInTheChest.setOnAction(event -> {
            try {
                menuContainerAdd(containerManager.getWearables(), containerManager.getSite());
            } catch (ContainerUnacceptedItemException e) {
                getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "Items", "You can't leave this item.");
            } catch (ContainerFullException e) {
                getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "Items", "The chest is full.");
            }
            alert.close();
        });

        Button leaveInTheJewelryBag = new Button(leaveInTheJewelryBagButtonType.getText());
        leaveInTheJewelryBag.setOnAction(event -> {
            try {
                menuContainerAdd(containerManager.getWearables(), containerManager.getBag());
            } catch (ContainerUnacceptedItemException e) {
                getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "Items", "You can't leave this item.");
            } catch (ContainerFullException e) {
                getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "Items", "The jewelry bag is full.");
            }
            alert.close();
        });

        Button exchangeBetweenWizardAndChest = new Button(exchangeBetweenWizardAndChestButtonType.getText());
        exchangeBetweenWizardAndChest.setOnAction(event -> {
            menuContainerExchange(containerManager.getWearables(), containerManager.getSite());
            alert.close();
        });

        Button exchangeBetweenWizardAndJewelryBag = new Button(exchangeBetweenWizardAndJewelryBagButtonType.getText());
        exchangeBetweenWizardAndJewelryBag.setOnAction(event -> {
            menuContainerExchange(containerManager.getWearables(), containerManager.getBag());
            dialogPane.getScene().getWindow().hide();
            alert.close();
        });

        Button exit = new Button("Exit");
        exit.setOnAction(event -> {
            dialogPane.getScene().getWindow().hide();
            alert.close();
        });

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(20, 20, 20, 20));
        vbox.getChildren().addAll(deleteFromStorage, deleteFromJewelryBag, takeFromStorage, takeFromJewelryBag,
                leaveInTheChest, leaveInTheJewelryBag, exchangeBetweenWizardAndChest, exchangeBetweenWizardAndJewelryBag, exit);

        dialogPane.setContent(vbox);

        alert.show();
    }

    void menuContainerDelete(Container c) {
        DemiurgeContainerManager containerManager = demiurge.getContainerManager();
        int cItem = selectItem(c);
        if (cItem == -1)
            return;

        containerManager.deleteItem(c, cItem);
        loadInfo();
    }

    void menuContainerAdd(Container source, Container receptor) throws ContainerUnacceptedItemException, ContainerFullException {
        DemiurgeContainerManager containerManager = demiurge.getContainerManager();

        int sourceItem = selectItem(source);
        if (sourceItem == -1)
            return;
        containerManager.addItem(source, sourceItem, receptor);
        loadInfo();
    }

    void menuContainerExchange(Container a, Container b) {
        DemiurgeContainerManager containerManager = demiurge.getContainerManager();

        int aItem = selectItem(a);
        if (aItem == -1)
            return;
        int bItem = selectItem(b);
        if (bItem == -1)
            return;

        try {
            containerManager.exchangeItem(a, aItem, b, bItem);
            loadInfo();
        } catch (ContainerInvalidExchangeException e) {
            getPrincipalController().showAlert(Alert.AlertType.ERROR, "Error", "Invalid exchange.");
        }
    }

    int selectItem(Container container) {
        int available = container.size();
        AtomicInteger selection = new AtomicInteger();
        int position = 1;
        Iterator it;

        if (available == 0) {
            getPrincipalController().showAlert(Alert.AlertType.ERROR, "Error", "No items available.");
            return -1;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Items");
            alert.setHeaderText("Select item");
            alert.setContentText("Select an item:");
            alert.getButtonTypes().clear();

            DialogPane dialogPane = alert.getDialogPane();

            List<Button> buttons = new ArrayList<>();
            it = container.iterator();
            while (it.hasNext()) {

                Button button = new Button(position++ + ".- " + it.next().toString());
                button.setOnAction(event -> {
                    selection.set(Integer.parseInt(button.getText().split(".-")[0]) - 1);
                    dialogPane.getScene().getWindow().hide();
                    alert.close();
                });
                buttons.add(button);
            }

            Button exit = new Button("Exit");
            exit.setOnAction(event -> {
                dialogPane.getScene().getWindow().hide();
                alert.close();
                selection.set(-1);
            });

            buttons.add(exit);

            VBox vbox = new VBox();

            vbox.setSpacing(10);
            vbox.setPadding(new Insets(20, 20, 20, 20));
            vbox.getChildren().addAll(buttons);

            dialogPane.setContent(vbox);
            alert.showAndWait();

            return selection.get();
        }
    }


}