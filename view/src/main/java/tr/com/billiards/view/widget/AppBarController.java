package tr.com.billiards.view.widget;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tr.com.billiards.view.core.api.BilliardsViewController;
import tr.com.billiards.view.core.component.CustomImageView;
import tr.com.billiards.view.core.enums.GameStatus;
import tr.com.billiards.view.core.enums.Scenes;
import tr.com.billiards.view.core.helper.LoaderHelper;
import tr.com.billiards.view.core.helper.MainHelper;
import tr.com.billiards.view.model.AppBarProperties;
import tr.com.billiards.view.model.SettingsProperties;

import java.net.URL;
import java.util.ResourceBundle;

public class AppBarController implements BilliardsViewController {
    private static Logger logger = LogManager.getLogger(AppBarController.class);
    //region FXMl
    @FXML
    private AnchorPane appBarRoot;
    @FXML
    private CustomImageView homeIcon;
    @FXML
    private CustomImageView settingsIcon;
    @FXML
    private HBox actionBox;
    @FXML
    private Label gameTime;
    @FXML
    private CustomImageView minusIcon;
    @FXML
    private CustomImageView okIcon;
    @FXML
    private CustomImageView plusIcon;
    @FXML
    private CustomImageView playIcon;
    //endregion

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logger.info("initialize AppBarController");
    }

    private void setComponentSize(Rectangle2D parentSize) {
    }

    private void bindVisibleProperties() {
        AppBarProperties properties = AppBarProperties.getInstance();
        homeIcon.visibleProperty().bind(properties.homeIconVisiblePropertyProperty());
        playIcon.visibleProperty().bind(homeIcon.visibleProperty().not());
        settingsIcon.visibleProperty().bind(properties.settingsIconVisiblePropertyProperty());
        gameTime.visibleProperty().bind(properties.gameTimeVisiblePropertyProperty());
        actionBox.visibleProperty().bind(properties.actionVisiblePropertyProperty());
    }

    private void bindData() {
        gameTime.textProperty().bindBidirectional(AppBarProperties.getInstance().gameTimeTextProperty());
    }

    @Override
    public void prepareScene(Rectangle2D parentSize) {
        setComponentSize(parentSize);
        bindVisibleProperties();
        bindData();
    }


    private void initializeSettingsPane() {
        Rectangle2D screenSize = Screen.getPrimary().getBounds();
        BilliardsViewController controller = (BilliardsViewController) LoaderHelper.getInstance()
                .loadScene(Scenes.SETTINGS, screenSize.getWidth(), screenSize.getHeight() - 100);
        controller.prepareScene(null);
        MainHelper.getInstance().setSettingsScene(controller);
    }

    @FXML
    private void settingsClicked() {
        if (MainHelper.getInstance().getSettingsScene() == null)
            initializeSettingsPane();
        Stage stage = MainHelper.getInstance().getSettingsScene().getStage();
        if (stage.isShowing())
            stage.close();
        else
            stage.show();
    }

    @FXML
    private void playIconClick() {
        if (MainHelper.getInstance().getActiveScene() != null)
            MainHelper.getInstance().getActiveScene().getStage().show();
    }

    private void finishTheGame() {
        MainHelper.getInstance().getActiveScene().getStage().close();
        MainHelper.getInstance().setActiveScene(null);
        AppBarProperties.getInstance().setGameStatusObjectProperty(GameStatus.NO_GAME);
        AppBarProperties.getInstance().setGameTimeText(SettingsProperties.getInstance().getGameTime() + ":00");
    }

    @FXML
    private void homeIconClick() {
        if (AppBarProperties.getInstance().getGameStatusObjectProperty().equals(GameStatus.PLAYING_GAME)) {
            if (!AppBarProperties.getInstance().gameTimeTextProperty().get().contains(":"))
                finishTheGame();
            else
                AppBarProperties.getInstance().setGameStatusObjectProperty(GameStatus.PAUSE_GAME);
        }
        MainHelper.getInstance().fireActiveStageCloseRequest();
    }

    @FXML
    private void plusIconClick() {
        if (!AppBarProperties.getInstance().getGameTimeText().contains(":"))
            return;
        if (MainHelper.getInstance().getActiveScene() != null)
            MainHelper.getInstance().getActiveScene().plusButtonEvent();
    }

    @FXML
    private void minusIconClick() {
        if (!AppBarProperties.getInstance().getGameTimeText().contains(":"))
            return;
        if (MainHelper.getInstance().getActiveScene() != null)
            MainHelper.getInstance().getActiveScene().minusButtonEvent();
    }

    @FXML
    private void okIconClick() {
        if (!AppBarProperties.getInstance().getGameTimeText().contains(":"))
            return;
        if (MainHelper.getInstance().getActiveScene() != null)
            MainHelper.getInstance().getActiveScene().okButtonEvent();
    }

    @Override
    public Stage getStage() {
        return (Stage) appBarRoot.getScene().getWindow();
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
