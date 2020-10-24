package tr.com.billiards.view;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tr.com.billiards.view.core.api.BilliardsViewController;
import tr.com.billiards.view.core.enums.StartingBalls;
import tr.com.billiards.view.core.enums.StartingOrder;
import tr.com.billiards.view.core.helper.MainHelper;
import tr.com.billiards.view.model.AppBarProperties;
import tr.com.billiards.view.model.SettingsProperties;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements BilliardsViewController {
    private static Logger logger = LogManager.getLogger(SettingsController.class);
    private BilliardsViewController preActiveScene = null;
    //region FXML
    @FXML
    private FlowPane settingsRoot;
    @FXML
    private Label gameTimeLabel;
    @FXML
    private CheckBox gameTimeUnlimited;
    @FXML
    private CheckBox gamerRoundTimeUnlimited;
    @FXML
    private Label gamerRoundTimeLabel;
    @FXML
    private CheckBox billiardsCueCountUnlimited;
    @FXML
    private Label billiardsCueCountLabel;
    @FXML
    private Label gamePointLabel;
    @FXML
    private CheckBox gamePointUnlimited;
    @FXML
    private ToggleGroup startingOrderGroup;
    @FXML
    private ToggleGroup startingBallGroup;
    //endregion

    @Override
    public void prepareScene(Rectangle2D parentSize) {
        setRequests();
    }

    private void bindData() {
        gameTimeLabel.setText(SettingsProperties.getInstance().getGameTime());
        gameTimeLabel.textProperty().bindBidirectional(SettingsProperties.getInstance().gameTimeProperty());
        gamerRoundTimeLabel.setText(SettingsProperties.getInstance().getGamerRoundTime());
        gamerRoundTimeLabel.textProperty().bindBidirectional(SettingsProperties.getInstance().gamerRoundTimeProperty());
        billiardsCueCountLabel.setText(SettingsProperties.getInstance().getBilliardsCueCount());
        billiardsCueCountLabel.textProperty().bindBidirectional(SettingsProperties.getInstance().billiardsCueCountProperty());
        gamePointLabel.setText(SettingsProperties.getInstance().getGamePoint());
        gamePointLabel.textProperty().bindBidirectional(SettingsProperties.getInstance().gamePointProperty());
        startingBallGroup.selectedToggleProperty().addListener((observableValue, toggle, newToggle)
                -> SettingsProperties.getInstance().startingBallsProperty().set(StartingBalls.valueOf(newToggle.getUserData().toString())));
        startingOrderGroup.selectedToggleProperty().addListener((observableValue, toggle, newToggle)
                -> SettingsProperties.getInstance().startingOrderProperty().set(StartingOrder.valueOf(newToggle.getUserData().toString())));
    }

    private void showActionIcons(boolean isShown) {
        AppBarProperties.getInstance().setActionVisibleProperty(isShown);
        AppBarProperties.getInstance().setSettingsIconVisibleProperty(!isShown);
    }

    private void setRequests() {
        getStage().setOnCloseRequest(windowEvent -> {
            if (MainHelper.getInstance().getActiveScene() == this)
                MainHelper.getInstance().setActiveScene(null);
            showActionIcons(false);
        });

        getStage().setOnShown(windowEvent -> {
            if (MainHelper.getInstance().getActiveScene() == null)
                MainHelper.getInstance().setActiveScene(this);
            showActionIcons(true);
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bindData();
    }

    @FXML
    private void setActiveNode(MouseEvent event) {
        MainHelper.getInstance().setActiveNode(((Node) event.getTarget()).getParent());
    }

    @Override
    public Stage getStage() {
        return (Stage) settingsRoot.getScene().getWindow();
    }

    private Label getActiveLabel() {
        Node node = MainHelper.getInstance().getActiveNode();
        if (node instanceof Label)
            return (Label) node;
        return null;
    }

    private void addActiveButtonLabel(int count) {
        Label label = getActiveLabel();
        if (label != null) {
            int labelNumber = Integer.parseInt(label.getText()) + count;
            if (labelNumber > 0)
                label.setText(String.valueOf(labelNumber));
        }
    }

    @Override
    public void plusButtonEvent() {
        addActiveButtonLabel(1);
    }

    @Override
    public void minusButtonEvent() {
        addActiveButtonLabel(-1);
    }

    @Override
    public void okButtonEvent() {
        //Do nothing
    }
}
