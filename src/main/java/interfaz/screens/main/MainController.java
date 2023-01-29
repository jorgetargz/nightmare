package interfaz.screens.main;

import game.DungeonLoaderXML;
import game.demiurge.Demiurge;
import game.demiurge.DungeonConfiguration;
import interfaz.screens.almacenParaGuardarCambiosPantallas.Reciclaje;
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

    private final Reciclaje reciclaje;

    @Inject
    public MainController(Instance<Object> instance, DungeonLoaderXML dungeonLoaderXML, Reciclaje reciclaje) {
        this.instance = instance;
        this.dungeonLoaderXML = dungeonLoaderXML;
        alert = new Alert(Alert.AlertType.NONE);
        this.reciclaje = reciclaje;
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

    public double getWidth() {
        return root.getScene().getWindow().getWidth();
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
            case ScreenConstants.MENU_ITEM_PANTALLA_INICIO -> cargarPantalla(Screens.WELCOME);
            default -> cargarPantalla(Screens.LOGIN);
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
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(ScreenConstants.XML_FILES, "*.xml"));
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            dungeonLoaderXML.load(demiurge, dungeonConfiguration, file);
            reciclaje.setDungeon(demiurge.getDungeon());
            reciclaje.setWizard(demiurge.getWizard());
            reciclaje.setHome(demiurge.getHome());
        }
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
    }

    public void cargarPantallaJuego() {
        cargarPantalla(Screens.PANTALLAJUEGO);
    }
}
