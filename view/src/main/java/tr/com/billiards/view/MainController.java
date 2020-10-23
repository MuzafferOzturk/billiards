package tr.com.billiards.view;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tr.com.billiards.view.core.api.BilliardsViewController;
import tr.com.billiards.view.core.enums.GameStatus;
import tr.com.billiards.view.core.enums.Scenes;
import tr.com.billiards.view.core.helper.LoaderHelper;
import tr.com.billiards.view.core.helper.MainHelper;
import tr.com.billiards.view.model.AppBarProperties;
import tr.com.billiards.view.widget.AppBarController;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements BilliardsViewController {
    private static Logger logger = LogManager.getLogger(MainController.class);
    //region FXML
    @FXML
    private AnchorPane appBar;
    @FXML
    private AnchorPane mainRoot;
    @FXML
    private VBox startGameVBox;
    @FXML
    private VBox stopGameVBox;
    //endregion

    //region Controller
    @FXML
    private AppBarController appBarController;
    //enregion

    @Override
    public void prepareScene(Rectangle2D parentSize) {
        appBarController.prepareScene(parentSize);
    }

    private void bindData() {
        startGameVBox.visibleProperty().bind(stopGameVBox.visibleProperty().not());
        AppBarProperties.getInstance().gameStatusObjectPropertyProperty().
                addListener((observableValue, gameStatus, gameStatusNew)
                        -> stopGameVBox.visibleProperty().setValue(gameStatusNew.equals(GameStatus.PAUSE_GAME)));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bindData();
        logger.info("initialize");
    }


    @Override
    public Stage getStage() {
        return (Stage) mainRoot.getScene().getWindow();
    }

    private void startGame(Scenes scenes) {
        if (AppBarProperties.getInstance().getGameStatusObjectProperty().equals(GameStatus.PAUSE_GAME))
            logger.info("PAUSE GAME!!!");
        if (MainHelper.getInstance().getActiveScene() != null)
            MainHelper.getInstance().getActiveScene().getStage().close();
        BilliardsViewController controller = (BilliardsViewController) LoaderHelper.getInstance().loadScene(scenes);
        controller.prepareScene(null);
        controller.getStage().show();
    }

    @FXML
    private void threeBandClick() {
        startGame(Scenes.THREE_BAND);
    }

    @FXML
    private void survivalClick() {
        startGame(Scenes.SURVIVAL);
    }

    @FXML
    private void stopGame() {
        AppBarProperties.getInstance().setGameStatusObjectProperty(GameStatus.NO_GAME);
        MainHelper.getInstance().setActiveScene(null);
    }

    @Override
    public void plusButtonEvent() {
        //Do nothing
    }

    @Override
    public void minusButtonEvent() {
        //Do nothing
    }

    @Override
    public void okButtonEvent() {
        //Do nothing
    }
}
