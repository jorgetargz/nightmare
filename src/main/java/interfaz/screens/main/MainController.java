package interfaz.screens.main;

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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController extends BaseScreenController implements Initializable {

    final Instance<Object> instance;

    private Stage primaryStage;
    private final Alert alert;
    private double xOffset;
    private double yOffset;

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
    public MainController(Instance<Object> instance) {
        this.instance = instance;
        alert = new Alert(Alert.AlertType.NONE);
    }

    public void setStage(Stage stage) {
        primaryStage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        menuPrincipal.setVisible(true);
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

    private void cargarPantalla(Screens pantalla) {
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
            case ScreenConstants.MENU_ITEM_LIST_NEWSPAPERS -> cargarPantalla(Screens.NEWSPAPER_LIST);
            case ScreenConstants.MENU_ITEM_DELETE_NEWSPAPERS -> cargarPantalla(Screens.NEWSPAPER_DELETE);
            case ScreenConstants.MENU_ITEM_LIST_ARTICLES -> cargarPantalla(Screens.ARTICLE_LIST);
            case ScreenConstants.MENU_ITEM_ADD_ARTICLES -> cargarPantalla(Screens.ARTICLE_ADD);
            case ScreenConstants.MENU_ITEM_LIST_READERS -> cargarPantalla(Screens.READER_LIST);
            case ScreenConstants.MENU_ITEM_DELETE_READERS -> cargarPantalla(Screens.READER_DELETE);
            case ScreenConstants.MENU_ITEM_LIST_SUBSCRIPTIONS -> cargarPantalla(Screens.SUBSCRIPTION_LIST);
            case ScreenConstants.MENU_ITEM_LIST_RATINGS -> cargarPantalla(Screens.RATING_LIST);
            case ScreenConstants.MENU_ITEM_ADD_RATINGS -> cargarPantalla(Screens.RATING_ADD);
            default -> cargarPantalla(Screens.LOGIN);
        }
    }

    @FXML
    private void cambiarcss() {
        if (primaryStage.getScene().getRoot().getStylesheets().stream().findFirst().isEmpty()
                || (primaryStage.getScene().getRoot().getStylesheets().stream().findFirst().isPresent()
                && primaryStage.getScene().getRoot().getStylesheets().stream().findFirst().get().contains(ScreenConstants.STYLE))) {
            try {
                primaryStage.getScene().getRoot().getStylesheets().clear();
                primaryStage.getScene().getRoot().getStylesheets().add(getClass().getResource(ScreenConstants.CSS_DARKMODE_CSS).toExternalForm());
            } catch (Exception e) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
            }
        } else {
            try {
                primaryStage.getScene().getRoot().getStylesheets().clear();
                primaryStage.getScene().getRoot().getStylesheets().add(getClass().getResource(ScreenConstants.CSS_STYLE_CSS).toExternalForm());
            } catch (Exception e) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    @FXML
    private void acercaDe() {
        showAlert(Alert.AlertType.INFORMATION, ScreenConstants.ABOUT, ScreenConstants.AUTHOR_DATA);
    }

    @FXML
    private void logout() {
        menuPrincipal.setVisible(false);
        cargarPantalla(Screens.LOGIN);
    }

    @FXML
    private void exit() {
        showAlertConfirmClose();
    }


    public void generarInforme() {
    }

    public void help() {
    }

    public void cargarPartidaXML() {

    }

    public void cargarPartidaBBDD() {
    }

    public void nuevaPartida() {
        cargarPantalla(Screens.INICIO);
    }

    public void guardar() {
    }

    public void cargarPantallaJuego() {
        cargarPantalla(Screens.PANTALLAJUEGO); 
    }
}
