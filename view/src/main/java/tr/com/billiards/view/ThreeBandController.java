package tr.com.billiards.view;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tr.com.billiards.view.core.api.BilliardsViewController;
import tr.com.billiards.view.core.component.CustomProgressBar;
import tr.com.billiards.view.core.enums.GameStatus;
import tr.com.billiards.view.core.helper.MainHelper;
import tr.com.billiards.view.model.AppBarProperties;
import tr.com.billiards.view.model.SettingsProperties;
import tr.com.billiards.view.widget.PlayerScoreBoard;

import java.net.URL;
import java.util.ResourceBundle;

public class ThreeBandController implements BilliardsViewController {

    //region FXML
    @FXML
    private AnchorPane threeBandRoot;
    @FXML
    private PlayerScoreBoard firstPlayerScoreBoard;
    @FXML
    private PlayerScoreBoard secondPlayerScoreBoard;
    @FXML
    private CustomProgressBar progressBox;
    //endregion

    private void setRequests() {
        getStage().setOnCloseRequest(windowEvent -> AppBarProperties.getInstance().setGameStatusObjectProperty(GameStatus.PAUSE_GAME));

        getStage().setOnShown(windowEvent -> {
            AppBarProperties.getInstance().setGameStatusObjectProperty(GameStatus.PLAYING_GAME);
            MainHelper.getInstance().setActiveScene(this);
        });
    }

    private void setScoreBoardSize(Rectangle2D screenSize) {
        firstPlayerScoreBoard.setPrefWidth(screenSize.getWidth() * 0.30);
        secondPlayerScoreBoard.setPrefWidth(screenSize.getWidth() * 0.30);
    }

    @Override
    public void prepareScene(Rectangle2D parentSize) {
        setScoreBoardSize(parentSize);
        setRequests();
    }

    @Override
    public Stage getStage() {
        return (Stage) threeBandRoot.getScene().getWindow();
    }

    @Override
    public void plusButtonEvent() {

    }

    @Override
    public void minusButtonEvent() {

    }

    @Override
    public void okButtonEvent() {

    }

    private void setGameTime() {
        AppBarProperties.getInstance().setGameTimeText(SettingsProperties.getInstance().getGameTime() + ":" + "00");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AppBarProperties.getInstance().setGameStatusObjectProperty(GameStatus.PLAYING_GAME);
        MainHelper.getInstance().setActiveScene(this);
        setGameTime();
        progressBox.setMaxTimeProperty(Integer.parseInt(SettingsProperties.getInstance().gamerRoundTimeProperty().get()));
        progressBox.startProgress();
    }
}
