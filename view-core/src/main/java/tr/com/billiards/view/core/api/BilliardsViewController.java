package tr.com.billiards.view.core.api;

import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.stage.Stage;

public interface BilliardsViewController extends Initializable {
    void prepareScene(Rectangle2D parentSize);

    Stage getStage();

    void plusButtonEvent();

    void minusButtonEvent();

    void okButtonEvent();
}
