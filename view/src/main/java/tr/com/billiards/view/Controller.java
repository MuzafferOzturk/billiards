package tr.com.billiards.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;

public class Controller {
    @FXML
    private TextField textField;

    @FXML
    private void testClick() {
        textField.textProperty().setValue(LocalDateTime.now().toString());
    }
}
