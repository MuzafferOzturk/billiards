package tr.com.billiards.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tr.com.billiards.view.core.PlayIconNamesSurvival;
import tr.com.billiards.view.core.api.BilliardsViewController;
import tr.com.billiards.view.core.component.CustomImageView;
import tr.com.billiards.view.core.component.CustomProgressBar;
import tr.com.billiards.view.core.enums.GameStatus;
import tr.com.billiards.view.core.enums.PlayIconNamesThreeBoards;
import tr.com.billiards.view.core.enums.StartingOrder;
import tr.com.billiards.view.core.helper.MainHelper;
import tr.com.billiards.view.model.AppBarProperties;
import tr.com.billiards.view.model.SettingsProperties;
import tr.com.billiards.view.widget.PlayerScoreBoard;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class SurvivalController implements BilliardsViewController {
    private int currentRound = 0;
    private PlayerScoreBoard activeBoard;
    private final Random random = new Random();
    private LinkedList<PlayerScoreBoard> scoreBoardLinkedList = new LinkedList<>();
    //region FXML
    @FXML
    private AnchorPane survivalRoot;
    @FXML
    private PlayerScoreBoard firstPlayerScoreBoard;
    @FXML
    private PlayerScoreBoard secondPlayerScoreBoard;
    @FXML
    private PlayerScoreBoard thirdPlayerScoreBoard;
    @FXML
    private PlayerScoreBoard fourthPlayerScoreBoard;
    @FXML
    private CustomProgressBar progressBox;
    @FXML
    private CustomImageView playPauseImage;
    //endregion

    private void setRequests() {
        getStage().setOnCloseRequest(windowEvent -> {
            AppBarProperties.getInstance().setGameStatusObjectProperty(GameStatus.PAUSE_GAME);
            AppBarProperties.getInstance().pauseGameTime();
            pauseGame();
        });

        getStage().setOnShown(windowEvent -> {
            AppBarProperties.getInstance().setGameStatusObjectProperty(GameStatus.PLAYING_GAME);
            MainHelper.getInstance().setActiveScene(this);
        });
    }

    private void setScoreBoardSize(Rectangle2D screenSize) {
        double perWidth = screenSize.getWidth() * 0.25;
        scoreBoardLinkedList.forEach(playerScoreBoard -> Platform.runLater(() -> {
            playerScoreBoard.setPrefWidth(perWidth);
            playerScoreBoard.setPrefHeight(screenSize.getHeight() - 50);
        }));
    }

    @Override
    public void prepareScene(Rectangle2D parentSize) {
        setScoreBoardSize(parentSize);
        setRequests();
    }

    @Override
    public Stage getStage() {
        return (Stage) survivalRoot.getScene().getWindow();
    }

    @Override
    public void plusButtonEvent() {
        if (!progressBox.getIsRunning())
            return;
        activeBoard.increasePlayerPoint();
        scoreBoardLinkedList
                .stream()
                .filter(playerScoreBoard -> playerScoreBoard != activeBoard)
                .forEach(PlayerScoreBoard::decreasePlayerPoint);
    }

    @Override
    public void minusButtonEvent() {
        if (!progressBox.getIsRunning())
            return;
        activeBoard.decreasePlayerPoint();
        scoreBoardLinkedList
                .stream()
                .filter(playerScoreBoard -> playerScoreBoard != activeBoard)
                .forEach(PlayerScoreBoard::increasePlayerPoint);
    }

    private void selectBoard(PlayerScoreBoard selectBoard, PlayerScoreBoard unSelectBoard) {
        unSelectBoard.unSelectScoreBoard();
        selectBoard.selectScoreBoard();
        activeBoard = selectBoard;
    }

    private void borderBilliardsCueIncrease() {
        currentRound = 0;
        int cueCount = Integer.parseInt(firstPlayerScoreBoard.getBilliardsCueCount()) + 1;
        scoreBoardLinkedList
                .forEach(playerScoreBoard
                        -> playerScoreBoard.setBilliardsCueCount(String.valueOf(cueCount)));
    }

    @Override
    public void okButtonEvent() {
        if (!progressBox.getIsRunning())
            return;
        progressBox.restartProgress();
        currentRound++;
        int maxNumRound = 4;
        int activeIndex = scoreBoardLinkedList.indexOf(activeBoard);
        int nextIndex = activeIndex + 1;
        if (currentRound >= maxNumRound) {
            borderBilliardsCueIncrease();
            nextIndex = 0;
        }
        selectBoard(scoreBoardLinkedList.get(nextIndex), scoreBoardLinkedList.get(activeIndex));
    }

    private void prepareGameTime() {
        AppBarProperties.getInstance().setGameTimeText(SettingsProperties.getInstance().getGameTime() + ":" + "00");
    }

    private void initializeProgress() {
        progressBox.setMaxTimeProperty(Integer.parseInt(SettingsProperties.getInstance().gamerRoundTimeProperty().get()));
    }

    private void playGame() {
        playPauseImage.setImageName(PlayIconNamesSurvival.PAUSE.getIconName());
        progressBox.startProgress();
        AppBarProperties.getInstance().startGameTime();
    }

    private void pauseGame() {
        playPauseImage.setImageName(PlayIconNamesSurvival.PLAY.getIconName());
        progressBox.pauseProgress();
        AppBarProperties.getInstance().pauseGameTime();
    }

    @FXML
    private void playPauseClick() {
        if (!AppBarProperties.getInstance().getGameTimeText().contains(":"))
            return;
        if (playPauseImage.getImageName().equalsIgnoreCase(PlayIconNamesSurvival.PAUSE.getIconName()))
            pauseGame();
        else
            playGame();
    }

    private void prepareOrder() {
        List<PlayerScoreBoard> scoreBoardList = new ArrayList<>();
        initializeScoreList(scoreBoardList);
        if (SettingsProperties.getInstance().getStartingOrder().equals(StartingOrder.ORDER))
            scoreBoardLinkedList = scoreBoardList.stream()
                            .sorted(Comparator.comparingInt(o
                                    -> Integer.parseInt(o.getPlayerNumber()))).collect(Collectors.toCollection(LinkedList::new));
        else {
            for (int i = 3; i >= 0; i--) {
                int index = i == 0 ? 0 : random.nextInt(i);
                PlayerScoreBoard scoreBoard = scoreBoardList.get(index);
                scoreBoardLinkedList.add(scoreBoard);
                scoreBoardList.remove(scoreBoard);
            }
        }
        activeBoard = scoreBoardLinkedList.getFirst();
    }

    private void prepareInitializePoint() {
        scoreBoardLinkedList
                .forEach(playerScoreBoard
                        -> playerScoreBoard.setPlayerPoint(SettingsProperties.getInstance().getGamePoint()));
    }

    private void initializeSettings() {
        prepareOrder();
        prepareGameTime();
        prepareInitializePoint();
        activeBoard.selectScoreBoard();
    }

    private void initializeScoreList(List<PlayerScoreBoard> scoreBoardList) {
        scoreBoardList.add(firstPlayerScoreBoard);
        scoreBoardList.add(secondPlayerScoreBoard);
        scoreBoardList.add(thirdPlayerScoreBoard);
        scoreBoardList.add(fourthPlayerScoreBoard);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AppBarProperties.getInstance().setGameStatusObjectProperty(GameStatus.PLAYING_GAME);
        MainHelper.getInstance().setActiveScene(this);
        initializeProgress();
        initializeSettings();
    }
}
