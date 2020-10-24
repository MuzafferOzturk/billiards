package tr.com.billiards.view.core.helper;

import javafx.scene.Node;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import tr.com.billiards.view.core.api.BilliardsViewController;

public class MainHelper {
    private static MainHelper instance;
    private Window parentWindow;
    private Node activeNode;
    private BilliardsViewController settingsScene;
    private BilliardsViewController activeScene;

    public static MainHelper getInstance() {
        if (instance == null)
            instance = new MainHelper();
        return instance;
    }

    public void fireActiveStageCloseRequest() {
        if (getActiveScene() != null)
            getActiveScene().getStage()
                    .fireEvent(new WindowEvent(getActiveScene().getStage(), WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    public Window getParentWindow() {
        return parentWindow;
    }

    public void setParentWindow(Window parentWindow) {
        this.parentWindow = parentWindow;
    }

    public Node getActiveNode() {
        return activeNode;
    }


    private void activeNodeStyleClear() {
        if (activeNode != null)
            activeNode.setStyle("-fx-border-color: transparent;");
    }

    private void activeNodeStyleDraw() {
        if (activeNode != null)
            activeNode.setStyle("-fx-border-color: crimson; -fx-border-width: 5; -fx-border-radius: 5");
    }

    public void setActiveNode(Node activeNode) {
        activeNodeStyleClear();
        this.activeNode = activeNode;
        activeNodeStyleDraw();
    }

    public BilliardsViewController getSettingsScene() {
        return settingsScene;
    }

    public void setSettingsScene(BilliardsViewController settingsScene) {
        this.settingsScene = settingsScene;
    }

    public BilliardsViewController getActiveScene() {
        return activeScene;
    }

    public void setActiveScene(BilliardsViewController activeScene) {
        this.activeScene = activeScene;
    }
}
