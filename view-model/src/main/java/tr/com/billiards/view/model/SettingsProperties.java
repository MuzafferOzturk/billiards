package tr.com.billiards.view.model;

import javafx.beans.property.*;
import tr.com.billiards.view.core.enums.StartingBalls;
import tr.com.billiards.view.core.enums.StartingOrder;

public class SettingsProperties {
    private static SettingsProperties instance;
    private StringProperty gameTime = new SimpleStringProperty("10");
    private StringProperty gamerRoundTime = new SimpleStringProperty("30");
    private StringProperty billiardsCueCount = new SimpleStringProperty("100");
    private StringProperty gamePoint = new SimpleStringProperty("30");
    private ObjectProperty<StartingOrder> startingOrder = new SimpleObjectProperty<>(StartingOrder.ORDER);
    private ObjectProperty<StartingBalls> startingBalls = new SimpleObjectProperty<>(StartingBalls.YELLOW);
    private BooleanProperty tieUp = new SimpleBooleanProperty(false);

    private SettingsProperties() {
    }

    public String getGameTime() {
        return gameTime.get();
    }

    public StringProperty gameTimeProperty() {
        return gameTime;
    }

    public void setGameTime(String gameTime) {
        this.gameTime.set(gameTime);
    }

    public String getGamerRoundTime() {
        return gamerRoundTime.get();
    }

    public StringProperty gamerRoundTimeProperty() {
        return gamerRoundTime;
    }

    public void setGamerRoundTime(String gamerRoundTime) {
        this.gamerRoundTime.set(gamerRoundTime);
    }

    public String getBilliardsCueCount() {
        return billiardsCueCount.get();
    }

    public StringProperty billiardsCueCountProperty() {
        return billiardsCueCount;
    }

    public void setBilliardsCueCount(String billiardsCueCount) {
        this.billiardsCueCount.set(billiardsCueCount);
    }

    public String getGamePoint() {
        return gamePoint.get();
    }

    public StringProperty gamePointProperty() {
        return gamePoint;
    }

    public void setGamePoint(String gamePoint) {
        this.gamePoint.set(gamePoint);
    }

    public StartingOrder getStartingOrder() {
        return startingOrder.get();
    }

    public ObjectProperty<StartingOrder> startingOrderProperty() {
        return startingOrder;
    }

    public void setStartingOrder(StartingOrder startingOrder) {
        this.startingOrder.set(startingOrder);
    }

    public StartingBalls getStartingBalls() {
        return startingBalls.get();
    }

    public ObjectProperty<StartingBalls> startingBallsProperty() {
        return startingBalls;
    }

    public void setStartingBalls(StartingBalls startingBalls) {
        this.startingBalls.set(startingBalls);
    }

    public boolean isTieUp() {
        return tieUp.get();
    }

    public BooleanProperty tieUpProperty() {
        return tieUp;
    }

    public void setTieUp(boolean tieUp) {
        this.tieUp.set(tieUp);
    }

    public static SettingsProperties getInstance() {
        if (instance == null)
            instance = new SettingsProperties();
        return instance;
    }
}
