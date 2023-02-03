package interfaz.screens.main;

import game.DungeonLoaderXML;
import game.demiurge.Demiurge;
import game.demiurge.DungeonConfiguration;
import game.object.ItemCreationErrorException;
import game.objectContainer.exceptions.ContainerFullException;
import game.objectContainer.exceptions.ContainerUnacceptedItemException;
import game.spell.SpellUnknowableException;
import game.util.ValueOverMaxException;
import interfaz.screens.common.BaseScreenController;
import interfaz.screens.common.ScreenConstants;
import interfaz.screens.common.Screens;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController extends BaseScreenController implements Initializable {

    final Instance<Object> instance;
    private final DungeonLoaderXML dungeonLoaderXML;

    private Stage primaryStage;
    private final Alert alert;
    private double xOffset;
    private double yOffset;
    private Demiurge demiurge = new Demiurge();
    private DungeonConfiguration dungeonConfiguration = new DungeonConfiguration();
    private int currentRoom;

    public int getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(int currentRoom) {
        this.currentRoom = currentRoom;
    }

    @FXML
    private BorderPane root;
    @FXML
    private HBox windowHeader;
    @FXML
    private MFXFontIcon closeIcon;
    @FXML
    private MFXFontIcon minimizeIcon;
    @FXML
    private MFXFontIcon alwaysOnTopIcon;
    @FXML
    private MenuBar menuPrincipal;

    @Inject
    public MainController(Instance<Object> instance, DungeonLoaderXML dungeonLoaderXML) {
        this.instance = instance;
        this.dungeonLoaderXML = dungeonLoaderXML;
        alert = new Alert(Alert.AlertType.NONE);
    }

    public void setStage(Stage stage) {
        primaryStage = stage;
    }

    public Demiurge getDemiurge() {
        return demiurge;
    }

    public DungeonConfiguration getDungeonConfiguration() {
        return dungeonConfiguration;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Window header icons events handlers and window drag
        closeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> showAlertConfirmClose());
        minimizeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> ((Stage) root.getScene().getWindow()).setIconified(true));
        alwaysOnTopIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            boolean newVal = !primaryStage.isAlwaysOnTop();
            alwaysOnTopIcon.pseudoClassStateChanged(PseudoClass
                    .getPseudoClass(ScreenConstants.ALWAYS_ON_TOP), newVal);
            primaryStage.setAlwaysOnTop(newVal);
        });

        windowHeader.setOnMousePressed(event -> {
            xOffset = primaryStage.getX() - event.getScreenX();
            yOffset = primaryStage.getY() - event.getScreenY();
        });
        windowHeader.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() + xOffset);
            primaryStage.setY(event.getScreenY() + yOffset);
        });

        // Cargar pantalla de inicio
        cargarPantalla(Screens.INICIO);
    }

    private void showAlertConfirmClose() {
        Alert alertCerrar = new Alert(Alert.AlertType.WARNING);
        alertCerrar.getButtonTypes().remove(ButtonType.OK);
        alertCerrar.getButtonTypes().add(ButtonType.CANCEL);
        alertCerrar.getButtonTypes().add(ButtonType.YES);
        alertCerrar.setHeaderText(ScreenConstants.EXIT);
        alertCerrar.setTitle(ScreenConstants.EXIT);
        alertCerrar.setContentText(ScreenConstants.SURE_EXIT);
        alertCerrar.initOwner(primaryStage.getOwner());
        Optional<ButtonType> res = alertCerrar.showAndWait();
        res.ifPresent(buttonType -> {
            if (buttonType == ButtonType.YES) {
                Platform.exit();
            }
        });
    }

    public void showAlert(Alert.AlertType alertType, String titulo, String mensaje) {
        alert.setAlertType(alertType);
        alert.setTitle(titulo);
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void cargarPantalla(Screens pantalla) {
        Pane panePantalla;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(controller -> instance.select(controller).get());
            panePantalla = fxmlLoader.load(getClass().getResourceAsStream(pantalla.getPath()));
            BaseScreenController pantallaController = fxmlLoader.getController();
            pantallaController.setPrincipalController(this);
            pantallaController.principalCargado();
            root.setCenter(panePantalla);
        } catch (IOException e) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    private void menuOnClick(ActionEvent actionEvent) {
        switch (((MenuItem) actionEvent.getSource()).getId()) {
            default -> {
                cargarPantalla(Screens.INICIO);
            }
        }
    }

    @FXML
    private void acercaDe() {
        showAlert(Alert.AlertType.INFORMATION, ScreenConstants.ABOUT, ScreenConstants.AUTHOR_DATA);
    }

    @FXML
    private void exit() {
        showAlertConfirmClose();
    }


    @FXML
    private void generarInforme() {
    }

    @FXML
    private void help() {
    }

    @FXML
    private void cargarPartidaXML() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(ScreenConstants.LOAD_XML);
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(ScreenConstants.XML_FILES, "*.xml"));
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file == null) {
            file = new File("xml/dungeon-V.02.xml");
            showAlert(Alert.AlertType.ERROR, ScreenConstants.ERROR, ScreenConstants.ERROR_LOADING_XML);
        }
        loadManagers(file);
    }

    private void loadManagers(File file) {
        demiurge.loadEnvironment((demiurge, dungeonConfiguration) -> {
            try {
                dungeonLoaderXML.load(demiurge, dungeonConfiguration, file);
            } catch (Exception | ContainerUnacceptedItemException | SpellUnknowableException | ValueOverMaxException |
                     ContainerFullException | ItemCreationErrorException e) {
                showAlert(Alert.AlertType.ERROR, ScreenConstants.ERROR, "Error al cargar el XML");
            }
        });
    }

    @FXML
    private void cargarPartidaBBDD() {
    }

    @FXML
    private void nuevaPartida() {
        cargarPantalla(Screens.INICIO);
    }

    @FXML
    private void guardar() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(ScreenConstants.SAVE_XML);
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(ScreenConstants.XML_FILES, "*.xml"));
        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            try {
                dungeonLoaderXML.save(demiurge, dungeonConfiguration, file);
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, ScreenConstants.ERROR, "Error al guardar el XML");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, ScreenConstants.ERROR, ScreenConstants.ERROR_SAVING_XML);
        }
    }

    public void cargarPantallaJuego() {
        cargarPantalla(Screens.CASA);
    }

    public void goToCasaMago() {
        cargarPantalla(Screens.CASA);
    }

    public void cargarPantallaPelea() {
        cargarPantalla(Screens.PANTALLAPELEAS);
    }

    public void cargarPantallaInventario() {
        cargarPantalla(Screens.INVENTARIO);
    }
}
