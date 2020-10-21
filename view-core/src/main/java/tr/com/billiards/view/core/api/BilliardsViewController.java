package tr.com.billiards.view.core.api;

import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;

public interface BilliardsViewController extends Initializable {
    void prepareScene(Rectangle2D parentSize);
}
