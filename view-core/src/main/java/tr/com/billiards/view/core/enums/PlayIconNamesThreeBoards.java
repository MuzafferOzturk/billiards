package tr.com.billiards.view.core.enums;

public enum PlayIconNamesThreeBoards {
    PLAY("play_red.png"),
    PAUSE("pause.png");


    private String iconName;

    PlayIconNamesThreeBoards(String iconName) {
        this.iconName = iconName;
    }

    public String getIconName() {
        return iconName;
    }
}
