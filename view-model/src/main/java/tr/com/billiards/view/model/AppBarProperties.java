package tr.com.billiards.view.model;

import javafx.beans.property.*;
import tr.com.billiards.view.core.enums.GameStatus;

public class AppBarProperties {
    private static AppBarProperties instance;
    private ObjectProperty<GameStatus> gameStatusObjectProperty = new SimpleObjectProperty<>(GameStatus.NO_GAME);
    private BooleanProperty homeIconVisibleProperty = new SimpleBooleanProperty(true);
    private BooleanProperty settingsIconVisibleProperty = new SimpleBooleanProperty(true);
    private BooleanProperty gameTimeVisibleProperty = new SimpleBooleanProperty(false);
    private BooleanProperty actionVisibleProperty = new SimpleBooleanProperty(false);
    private StringProperty gameTimeText = new SimpleStringProperty("");

    private void prepareGameStatus() {
        gameStatusObjectProperty
                .addListener((observableValue, gameStatus, gameStatusNew)
                        -> {
                    homeIconVisibleProperty.setValue(!gameStatusNew.equals(GameStatus.PAUSE_GAME));
                    settingsIconVisibleProperty.setValue(!gameStatusNew.equals(GameStatus.PLAYING_GAME));
                    gameTimeVisibleProperty.setValue(!gameStatusNew.equals(GameStatus.NO_GAME));
                    actionVisibleProperty.setValue(gameStatusNew.equals(GameStatus.PLAYING_GAME));
                });
    }

    private AppBarProperties() {
        prepareGameStatus();
    }

    public static AppBarProperties getInstance() {
        if (instance == null)
            instance = new AppBarProperties();
        return instance;
    }

    public GameStatus getGameStatusObjectProperty() {
        return gameStatusObjectProperty.get();
    }

    public ObjectProperty<GameStatus> gameStatusObjectPropertyProperty() {
        return gameStatusObjectProperty;
    }

    public void setGameStatusObjectProperty(GameStatus gameStatusObjectProperty) {
        this.gameStatusObjectProperty.set(gameStatusObjectProperty);
    }

    public boolean isHomeIconVisibleProperty() {
        return homeIconVisibleProperty.get();
    }

    public BooleanProperty homeIconVisiblePropertyProperty() {
        return homeIconVisibleProperty;
    }

    public void setHomeIconVisibleProperty(boolean homeIconVisibleProperty) {
        this.homeIconVisibleProperty.set(homeIconVisibleProperty);
    }

    public boolean isSettingsIconVisibleProperty() {
        return settingsIconVisibleProperty.get();
    }

    public BooleanProperty settingsIconVisiblePropertyProperty() {
        return settingsIconVisibleProperty;
    }

    public void setSettingsIconVisibleProperty(boolean settingsIconVisibleProperty) {
        this.settingsIconVisibleProperty.set(settingsIconVisibleProperty);
    }

    public boolean isGameTimeVisibleProperty() {
        return gameTimeVisibleProperty.get();
    }

    public BooleanProperty gameTimeVisiblePropertyProperty() {
        return gameTimeVisibleProperty;
    }

    public void setGameTimeVisibleProperty(boolean gameTimeVisibleProperty) {
        this.gameTimeVisibleProperty.set(gameTimeVisibleProperty);
    }

    public boolean isActionVisibleProperty() {
        return actionVisibleProperty.get();
    }

    public BooleanProperty actionVisiblePropertyProperty() {
        return actionVisibleProperty;
    }

    public void setActionVisibleProperty(boolean actionVisibleProperty) {
        this.actionVisibleProperty.set(actionVisibleProperty);
    }

    public String getGameTimeText() {
        return gameTimeText.get();
    }

    public StringProperty gameTimeTextProperty() {
        return gameTimeText;
    }

    public void setGameTimeText(String gameTimeText) {
        this.gameTimeText.set(gameTimeText);
    }
}
