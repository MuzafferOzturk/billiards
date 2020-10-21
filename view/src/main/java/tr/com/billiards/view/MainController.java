package tr.com.billiards.view;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tr.com.billiards.view.core.api.BilliardsViewController;
import tr.com.billiards.view.core.enums.GameStatus;
import tr.com.billiards.view.model.AppBarProperties;
import tr.com.billiards.view.widget.AppBarController;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements BilliardsViewController {
    private static Logger logger = LogManager.getLogger(MainController.class);
    //region FXML
    @FXML
    private AnchorPane appBar;
    //endregion

    //region Controller
    @FXML
    private AppBarController appBarController;
    //enregion

    @Override
    public void prepareScene(Rectangle2D parentSize) {
        appBarController.prepareScene(parentSize);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logger.info("initialize");
    }

    @FXML
    private void playGame() {
        AppBarProperties.getInstance().setGameStatusObjectProperty(GameStatus.PLAYING_GAME);
    }

    @FXML
    private void pauseGame() {
        AppBarProperties.getInstance().setGameStatusObjectProperty(GameStatus.PAUSE_GAME);
    }

    @FXML
    private void noGame() {
        AppBarProperties.getInstance().setGameStatusObjectProperty(GameStatus.NO_GAME);
    }

}
