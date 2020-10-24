package tr.com.billiards.view.core;

public enum PlayIconNamesSurvival {
    PLAY("play.png"),
    PAUSE("pause_white.png");

    private String iconName;

    PlayIconNamesSurvival(String iconName) {
        this.iconName = iconName;
    }

    public String getIconName() {
        return iconName;
    }
}
