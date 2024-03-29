package interfaz.main;

import interfaz.main.common.Constants;
import interfaz.screens.common.Screens;
import interfaz.screens.main.MainController;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainFX {

    @Inject
    FXMLLoader fxmlLoader;

    public void start(@Observes @StartupScene Stage stage) {
        try {
            Parent fxmlParent = fxmlLoader.load(getClass().getResourceAsStream(Screens.MAIN.getPath()));
            MainController controller = fxmlLoader.getController();
            controller.setStage(stage);

            Scene scene = new Scene(fxmlParent);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.getScene().getStylesheets().add(getClass().getResource(Constants.CSS_STYLE_CSS).toExternalForm());
            stage.setTitle("Nightmare");
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

}
