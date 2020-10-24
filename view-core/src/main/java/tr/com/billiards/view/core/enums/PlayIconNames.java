package tr.com.billiards.view.core.enums;

public enum PlayIconNames {
    PLAY("play_red.png"),
    PAUSE("pause.png");


    private String iconName;

    PlayIconNames(String iconName) {
        this.iconName = iconName;
    }

    public String getIconName() {
        return iconName;
    }
}
