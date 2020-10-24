package tr.com.billiards.view.model;

import javafx.application.Platform;
import javafx.beans.property.*;
import tr.com.billiards.view.core.enums.GameStatus;
import tr.com.billiards.view.core.helper.ResourceHelper;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AppBarProperties {
    private static AppBarProperties instance;
    private final ObjectProperty<GameStatus> gameStatusObjectProperty = new SimpleObjectProperty<>(GameStatus.NO_GAME);
    private final BooleanProperty homeIconVisibleProperty = new SimpleBooleanProperty(true);
    private final BooleanProperty settingsIconVisibleProperty = new SimpleBooleanProperty(true);
    private final BooleanProperty gameTimeVisibleProperty = new SimpleBooleanProperty(false);
    private final BooleanProperty actionVisibleProperty = new SimpleBooleanProperty(false);
    private final ReadOnlyStringWrapper gameTimeText = new ReadOnlyStringWrapper("00:00");
    private final ReadOnlyBooleanWrapper controlGameTime = new ReadOnlyBooleanWrapper(false);
    private final BooleanProperty runGameTime = new SimpleBooleanProperty(false);

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

    private void decreaseGameTime() {
        boolean gameEnd = false;
        String time = gameTimeText.get();
        int minute = Integer.parseInt(time.split(":")[0]);
        int second = Integer.parseInt(time.split(":")[1]) - 1;
        if (second == -1) {
            second = 59;
            minute--;
            if (minute == -1){
                minute = 0;
                runGameTime.set(false);
                gameEnd = true;
            }
        }
        if (!gameEnd)
            gameTimeText.set(minute + ":" + second);
        else
            gameTimeText.set(ResourceHelper.getInstance().getResourceBundle().getString("game_end"));
    }

    private void prepareGameTime() {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.scheduleAtFixedRate(() -> {
            Platform.runLater(() -> {
                if (runGameTime.get())
                    controlGameTime.set(!controlGameTime.get());
            });
        }, 0, 1000, TimeUnit.MILLISECONDS);
        controlGameTime.getReadOnlyProperty().addListener((observableValue, aBoolean, t1) -> decreaseGameTime());
    }

    private AppBarProperties() {
        prepareGameStatus();
        prepareGameTime();
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
        pauseGameTime();
        this.gameTimeText.set(gameTimeText);
    }

    public void startGameTime() {
        if (!SettingsProperties.getInstance().gameTimeProperty().get().equalsIgnoreCase("0"))
            runGameTime.setValue(true);
    }

    public void pauseGameTime() {
        runGameTime.setValue(false);
    }
}
