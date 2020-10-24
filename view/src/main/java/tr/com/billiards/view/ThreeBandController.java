package tr.com.billiards.view;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tr.com.billiards.view.core.api.BilliardsViewController;
import tr.com.billiards.view.core.component.CustomImageView;
import tr.com.billiards.view.core.component.CustomProgressBar;
import tr.com.billiards.view.core.enums.GameStatus;
import tr.com.billiards.view.core.enums.PlayIconNames;
import tr.com.billiards.view.core.enums.StartingBalls;
import tr.com.billiards.view.core.enums.StartingOrder;
import tr.com.billiards.view.core.helper.MainHelper;
import tr.com.billiards.view.model.AppBarProperties;
import tr.com.billiards.view.model.SettingsProperties;
import tr.com.billiards.view.widget.PlayerScoreBoard;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class ThreeBandController implements BilliardsViewController {
    private int maxNumRound = 2;
    private int currentRound = 0;
    private PlayerScoreBoard activeBoard;
    private final Random random = new Random();
    //region FXML
    @FXML
    private AnchorPane threeBandRoot;
    @FXML
    private PlayerScoreBoard firstPlayerScoreBoard;
    @FXML
    private PlayerScoreBoard secondPlayerScoreBoard;
    @FXML
    private VBox statusBox;
    @FXML
    private CustomProgressBar progressBox;
    @FXML
    private CustomImageView playPauseImage;
    @FXML
    private Label billiardsCueCountLabel;
    //endregion

    private void setRequests() {
        getStage().setOnCloseRequest(windowEvent -> AppBarProperties.getInstance().setGameStatusObjectProperty(GameStatus.PAUSE_GAME));

        getStage().setOnShown(windowEvent -> {
            AppBarProperties.getInstance().setGameStatusObjectProperty(GameStatus.PLAYING_GAME);
            MainHelper.getInstance().setActiveScene(this);
        });
    }

    private void setScoreBoardSize(Rectangle2D screenSize) {
        double perWidth = screenSize.getWidth() * 0.33;
        firstPlayerScoreBoard.setPrefWidth(perWidth);
        secondPlayerScoreBoard.setPrefWidth(perWidth);
//        firstPlayerScoreBoard.setPrefHeight(screenSize.getHeight() - 100);
//        secondPlayerScoreBoard.setPrefHeight(screenSize.getHeight() - 100);
        statusBox.setPrefWidth(perWidth);
    }

    @Override
    public void prepareScene(Rectangle2D parentSize) {
        setScoreBoardSize(parentSize);
        setRequests();
    }


    @Override
    public Stage getStage() {
        return (Stage) threeBandRoot.getScene().getWindow();
    }

    @Override
    public void plusButtonEvent() {
        if (!progressBox.getIsRunning())
            return;
        activeBoard.increasePlayerPoint();
    }

    @Override
    public void minusButtonEvent() {
        if (!progressBox.getIsRunning())
            return;
        activeBoard.decreasePlayerPoint();
    }

    private void selectBoard(PlayerScoreBoard selectBoard, PlayerScoreBoard unSelectBoard) {
        unSelectBoard.unSelectScoreBoard();
        selectBoard.selectScoreBoard();
        activeBoard = selectBoard;
    }

    private void borderBilliardsCueIncrease() {
        currentRound = 0;
        int cueCount = Integer.parseInt(billiardsCueCountLabel.getText()) + 1;
        billiardsCueCountLabel.setText(String.valueOf(cueCount));
        firstPlayerScoreBoard.billiardsCueCountProperty().set(String.valueOf(cueCount));
        secondPlayerScoreBoard.billiardsCueCountProperty().set(String.valueOf(cueCount));
    }

    @Override
    public void okButtonEvent() {
        if (!progressBox.getIsRunning())
            return;
        progressBox.restartProgress();
        if (activeBoard == firstPlayerScoreBoard)
            selectBoard(secondPlayerScoreBoard, firstPlayerScoreBoard);
        else
            selectBoard(firstPlayerScoreBoard, secondPlayerScoreBoard);
        currentRound++;
        if (currentRound >= maxNumRound)
            borderBilliardsCueIncrease();
    }

    private void playGame() {
        playPauseImage.setImageName(PlayIconNames.PAUSE.getIconName());
        progressBox.startProgress();
    }

    private void pauseGame() {
        playPauseImage.setImageName(PlayIconNames.PLAY.getIconName());
        progressBox.pauseProgress();
    }

    @FXML
    private void playPauseClick() {
        if (playPauseImage.getImageName().equalsIgnoreCase(PlayIconNames.PAUSE.getIconName()))
            pauseGame();
        else
            playGame();
    }

    private void prepareGameTime() {
        AppBarProperties.getInstance().setGameTimeText(SettingsProperties.getInstance().getGameTime() + ":" + "00");
    }

    private void initializeProgress() {
        progressBox.setMaxTimeProperty(Integer.parseInt(SettingsProperties.getInstance().gamerRoundTimeProperty().get()));
    }

    private void prepareOrder() {
        activeBoard = firstPlayerScoreBoard;
        if (SettingsProperties.getInstance().getStartingOrder().equals(StartingOrder.RANDOM)
                && random.nextInt(2) == 1)
                activeBoard = secondPlayerScoreBoard;
    }

    private void prepareBall() {
        activeBoard.setBorderBallImage(SettingsProperties.getInstance().getStartingBalls().getBallImage());
        String otherBall = SettingsProperties.getInstance().getStartingBalls()
                .equals(StartingBalls.YELLOW) ? StartingBalls.WHITE.getBallImage() : StartingBalls.YELLOW.getBallImage();
        if  (activeBoard == firstPlayerScoreBoard)
            secondPlayerScoreBoard.setBorderBallImage(otherBall);
        else
            firstPlayerScoreBoard.setBorderBallImage(otherBall);
    }

    private void initializeSettings() {
        prepareOrder();
        prepareBall();
        prepareGameTime();
        activeBoard.selectScoreBoard();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AppBarProperties.getInstance().setGameStatusObjectProperty(GameStatus.PLAYING_GAME);
        MainHelper.getInstance().setActiveScene(this);
        initializeProgress();
        initializeSettings();
    }
}
