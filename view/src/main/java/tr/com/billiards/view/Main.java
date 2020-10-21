package tr.com.billiards.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tr.com.billiards.view.core.MainHelper;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/main.fxml"));
        fxmlLoader.setResources(null);
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Billiards");
        Rectangle2D screenSize = Screen.getPrimary().getBounds();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
//        primaryStage.setFullScreen(true);
        primaryStage.setMaximized(true);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        MainHelper.getInstance().setParentWindow(scene.getWindow());
        primaryStage.show();
        MainController mainController = fxmlLoader.getController();
        mainController.prepareScene(screenSize);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
