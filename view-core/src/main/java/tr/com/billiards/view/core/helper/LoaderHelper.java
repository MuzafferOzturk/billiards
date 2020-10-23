package tr.com.billiards.view.core.helper;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tr.com.billiards.view.core.enums.Scenes;

public class LoaderHelper {
    private static Logger logger = LogManager.getLogger(LoaderHelper.class);
    private static LoaderHelper instance;

    private LoaderHelper() {
    }

    public static LoaderHelper getInstance() {
        if (instance == null)
            instance = new LoaderHelper();
        return instance;
    }

    private Stage getNewStage() {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initOwner(MainHelper.getInstance().getParentWindow());
        stage.sizeToScene();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setX(0);
        stage.setY(100);
        stage.setOpacity(1);
        return stage;
    }

    public Initializable loadScene(Scenes scenes) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource(scenes.getPath()));
            fxmlLoader.setResources(ResourceHelper.getInstance().getResourceBundle());
            Rectangle2D screenSize = Screen.getPrimary().getBounds();
            Scene scene = new Scene(fxmlLoader.load(), screenSize.getWidth(), screenSize.getHeight() - 100);
            scene.setFill(Color.TRANSPARENT);
            Stage stage = getNewStage();
            stage.setScene(scene);
            return (Initializable) fxmlLoader.getController();
        }
        catch (Exception ex) {
            logger.error("Scene {}, {}", scenes, ex.toString());
        }
        return null;
    }
}
