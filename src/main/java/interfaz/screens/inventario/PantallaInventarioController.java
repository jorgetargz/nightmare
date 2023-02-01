package interfaz.screens.inventario;

import game.demiurge.Demiurge;
import game.objectContainer.Container;
import game.objectContainer.exceptions.ContainerFullException;
import game.objectContainer.exceptions.ContainerInvalidExchangeException;
import game.objectContainer.exceptions.ContainerUnacceptedItemException;
import interfaz.screens.common.BaseScreenController;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.util.Iterator;

public class PantallaInventarioController extends BaseScreenController {

    private Demiurge demiurge = null;

    public void initialize() {

    }

    @Override
    public void principalCargado() {
        demiurge = getPrincipalController().getDemiurge();
        Iterator it = demiurge.getContainerManager().getWearables().iterator();
        while (it.hasNext()) {
            comboWearables.getItems().add(it.next());
        }
        Iterator it2 = demiurge.getContainerManager().getBag().iterator();
        while (it2.hasNext()) {
            comboBag.getItems().add(it2.next());
        }
        Iterator it3 = demiurge.getContainerManager().getSite().iterator();
        while (it3.hasNext()) {
            comboSite.getItems().add(it3.next());
        }
    }

    @FXML
    private MFXComboBox comboWearables;

    @FXML
    private MFXComboBox comboBag;

    @FXML
    private MFXComboBox comboSite;

    public void deleteFromJewelryBag() {
        demiurge.getContainerManager().deleteItem(demiurge.getContainerManager().getBag(), 0);
    }

    public void takeFromRoom() {
        try {
            addToContainer(demiurge.getContainerManager().getSite(), demiurge.getContainerManager().getWearables());
        } catch (ContainerUnacceptedItemException | ContainerFullException e) {
            e.printStackTrace();
        }
    }

    public void takeFromJewelryBag() {
        try {
            addToContainer(demiurge.getContainerManager().getBag(), demiurge.getContainerManager().getWearables());
        } catch (ContainerUnacceptedItemException | ContainerFullException e) {
            e.printStackTrace();
        }
    }

    public void dropInRoom() {
        try {
            addToContainer(demiurge.getContainerManager().getWearables(), demiurge.getContainerManager().getSite());
        } catch (ContainerUnacceptedItemException | ContainerFullException e) {
            e.printStackTrace();
        }
    }

    public void leaveInJewelryBag() {
        try {
            addToContainer(demiurge.getContainerManager().getWearables(), demiurge.getContainerManager().getBag());
        } catch (ContainerUnacceptedItemException | ContainerFullException e) {
            e.printStackTrace();
        }
    }

    public void exchangeBetweenWizardAndRoom() {
        exchangeItems(demiurge.getContainerManager().getWearables(), demiurge.getContainerManager().getSite());
    }

    public void exchangeBetweenWizardAndJewelryBag() {
        exchangeItems(demiurge.getContainerManager().getWearables(), demiurge.getContainerManager().getBag());
    }

    private void addToContainer(Container source, Container receptor) throws ContainerUnacceptedItemException, ContainerFullException {
        int sourceItem = comboWearables.getSelectionModel().getSelectedIndex();
        demiurge.getContainerManager().addItem(source, sourceItem, receptor);
    }

    void exchangeItems(Container a, Container b) {
        int aItem = comboWearables.getSelectionModel().getSelectedIndex();
        int bItem = 0;
        if (b == demiurge.getContainerManager().getBag()) {
            bItem = comboBag.getSelectionModel().getSelectedIndex();
        } else if (b == demiurge.getContainerManager().getSite()) {
            bItem = comboSite.getSelectionModel().getSelectedIndex();
        }

        try {
            demiurge.getContainerManager().exchangeItem(a, aItem, b, bItem);
        } catch (ContainerInvalidExchangeException e) {
           getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "El tipo de los objetos es diferente", e.getMessage());
        }
    }

}