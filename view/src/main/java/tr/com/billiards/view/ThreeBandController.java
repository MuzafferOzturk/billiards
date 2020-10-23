package tr.com.billiards.view;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tr.com.billiards.view.core.api.BilliardsViewController;
import tr.com.billiards.view.core.enums.GameStatus;
import tr.com.billiards.view.core.helper.MainHelper;
import tr.com.billiards.view.model.AppBarProperties;

import java.net.URL;
import java.util.ResourceBundle;

public class ThreeBandController implements BilliardsViewController {

    //region FXML
    @FXML
    private AnchorPane threeBandRoot;
    //endregion

    private void setRequests() {
        getStage().setOnCloseRequest(windowEvent -> AppBarProperties.getInstance().setGameStatusObjectProperty(GameStatus.PAUSE_GAME));

        getStage().setOnShown(windowEvent -> {
            AppBarProperties.getInstance().setGameStatusObjectProperty(GameStatus.PLAYING_GAME);
            MainHelper.getInstance().setActiveScene(this);
        });
    }

    @Override
    public void prepareScene(Rectangle2D parentSize) {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AppBarProperties.getInstance().setGameStatusObjectProperty(GameStatus.PLAYING_GAME);
        MainHelper.getInstance().setActiveScene(this);
    }
}
