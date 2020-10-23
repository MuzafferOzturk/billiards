package tr.com.billiards.view.core.enums;

public enum Scenes {
    SETTINGS("/fxml/Settings.fxml"),
    THREE_BAND("/fxml/ThreeBand.fxml"),
    SURVIVAL("/fxml/Survival.fxml");

    private String path;

    Scenes(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
