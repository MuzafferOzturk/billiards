package tr.com.billiards.view.core.component;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;

public class CustomImageView extends ImageView {
    private StringProperty imageName = new SimpleStringProperty("");


    public CustomImageView() {
        this.getStyleClass().add("custom-image-view");
    }

    public String getImageName() {
        return imageName.get();
    }

    public StringProperty imageNameProperty() {
        return imageName;
    }

    private void setImage(InputStream stream) {
        setImage(new Image(stream));
    }

    public void setImageName(String imageName) {
        InputStream stream = getClass().getResourceAsStream("/icons/" + imageName);
        if (stream != null)
            setImage(stream);
        this.imageName.set(imageName);
    }
}
