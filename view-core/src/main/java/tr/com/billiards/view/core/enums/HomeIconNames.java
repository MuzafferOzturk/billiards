package tr.com.billiards.view.core.enums;

public enum HomeIconNames {
    HOME("home.png"),
    PLAY("play.png");


    private String iconName;

    HomeIconNames(String iconName) {
        this.iconName = iconName;
    }

    public String getIconName() {
        return iconName;
    }
}
