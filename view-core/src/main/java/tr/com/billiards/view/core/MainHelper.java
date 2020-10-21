package tr.com.billiards.view.core;

import javafx.stage.Window;

public class MainHelper {
    private static MainHelper instance;
    private Window parentWindow;


    public static MainHelper getInstance() {
        if (instance == null)
            instance = new MainHelper();
        return instance;
    }

    public Window getParentWindow() {
        return parentWindow;
    }

    public void setParentWindow(Window parentWindow) {
        this.parentWindow = parentWindow;
    }
}
