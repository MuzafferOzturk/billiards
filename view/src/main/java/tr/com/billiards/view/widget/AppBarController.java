package tr.com.billiards.view.widget;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tr.com.billiards.view.core.api.BilliardsViewController;
import tr.com.billiards.view.core.component.CustomImageView;
import tr.com.billiards.view.core.enums.HomeIconNames;
import tr.com.billiards.view.model.AppBarProperties;

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
    //endregion

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logger.info("initialize AppBarController");
    }

    private void setComponentSize(Rectangle2D parentSize) {
    }

    private void bindVisibleProperties() {
        AppBarProperties properties = AppBarProperties.getInstance();
        properties.homeIconVisiblePropertyProperty()
                .addListener((observableValue, aBoolean, newValue)
                        -> homeIcon.setImageName(Boolean.TRUE.equals(newValue)
                        ? HomeIconNames.HOME.getIconName()
                        : HomeIconNames.PLAY.getIconName()));
        settingsIcon.visibleProperty().bind(properties.settingsIconVisiblePropertyProperty());
        gameTime.visibleProperty().bind(properties.gameTimeVisiblePropertyProperty());
        actionBox.visibleProperty().bind(properties.actionVisiblePropertyProperty());
    }

    @Override
    public void prepareScene(Rectangle2D parentSize) {
        setComponentSize(parentSize);
        bindVisibleProperties();
    }
}
