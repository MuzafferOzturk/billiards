package tr.com.billiards.view.widget;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class PlayerScoreBoard extends VBox {
    private final StringProperty playerNumber = new SimpleStringProperty("0");
    private final StringProperty billiardsCueCount = new SimpleStringProperty("1");
    private final StringProperty playerName = new SimpleStringProperty("Oyuncu");
    private final StringProperty playerPoint = new SimpleStringProperty("0");
    private final StringProperty average = new SimpleStringProperty("0.000");
    private final StringProperty borderBallImage = new SimpleStringProperty("/icons/yellow_ball.png");
    private final BooleanProperty averageVisible= new SimpleBooleanProperty(true);

    private HBox getNewHBox() {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    private HBox createPlayerName() {
        HBox hBox = getNewHBox();
        Text text = new Text();
        text.setFill(Paint.valueOf("white"));
        text.setText(playerName.get());
        text.setTextAlignment(TextAlignment.CENTER);
        this.widthProperty()
                .addListener((observableValue, number, numberNew)
                        -> text.setWrappingWidth(numberNew.doubleValue() - 10.0));
        this.heightProperty()
                .addListener((observableValue, number, numberNew)
                        -> {
                    hBox.setPrefHeight(numberNew.doubleValue() / 3);
                    hBox.setMaxHeight(numberNew.doubleValue() / 3);
                });
        text.textProperty().bindBidirectional(playerName);
        text.getStyleClass().add("name-label");
        hBox.getChildren().add(text);
        return hBox;
    }

    private HBox createLabel(StringProperty property, String styleClass) {
        HBox hBox = getNewHBox();
        Label label = new Label();
        label.setText(property.get());
        label.textProperty().bindBidirectional(property);
        label.getStyleClass().add(styleClass);
        hBox.getChildren().add(label);
        return hBox;
    }

    private HBox createPlayerPoint() {
        return createLabel(playerPoint, "point-label");
    }

    private HBox createPlayerAverage() {
        return createLabel(average, "average-label");
    }

    public void selectScoreBoard() {
        this.setStyle("-fx-background-color: rgba(220, 20, 60, 0.5);" +
                "-fx-background-image: url('" + borderBallImage.get() +"');" +
                "-fx-background-repeat: no-repeat no-repeat");
    }

    public void unSelectScoreBoard() {
        this.setStyle("-fx-background-color: rgba(0,0,0,0);" +
                "-fx-background-image: null");
    }

    private void addPlayerPoint(int addedNum) {
        int point = Integer.parseInt(playerPoint.get()) + addedNum;
        playerPoint.set(String.valueOf(point));
    }

    public void increasePlayerPoint() {
        addPlayerPoint(1);
    }

    public void decreasePlayerPoint() {
        addPlayerPoint(-1);
    }

    private void calcAverage(String playerPoint, String average) {
        float point = Float.parseFloat(playerPoint);
        float averageFloat = Float.parseFloat(average);
        averageProperty().set(String.format("%.3f", (point / averageFloat)));
    }

    private void averageListener() {
        playerPoint.addListener((observableValue, s, newPoint)
                -> calcAverage(newPoint, billiardsCueCount.get()));
//        billiardsCueCountProperty().addListener((observableValue, s, newCueCount)
//                -> calcAverage(playerPoint.get(), newCueCount));
    }

    public PlayerScoreBoard() {
        this.setSpacing(20);
        this.getChildren().add(createPlayerName());
        this.getChildren().add(createPlayerPoint());
        HBox averageBox = createPlayerAverage();
        averageVisibleProperty()
                .addListener((observableValue, aBoolean, newValue)
                        -> averageBox.setVisible(newValue));
        this.getChildren().add(averageBox);
        averageListener();
    }

    public String getPlayerNumber() {
        return playerNumber.get();
    }

    public StringProperty playerNumberProperty() {
        return playerNumber;
    }

    public void setPlayerNumber(String playerNumber) {
        this.playerNumber.set(playerNumber);
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

    public String getPlayerName() {
        return playerName.get();
    }

    public StringProperty playerNameProperty() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName.set(playerName);
    }

    public String getPlayerPoint() {
        return playerPoint.get();
    }

    public StringProperty playerPointProperty() {
        return playerPoint;
    }

    public void setPlayerPoint(String playerPoint) {
        this.playerPoint.set(playerPoint);
    }

    public String getAverage() {
        return average.get();
    }

    public StringProperty averageProperty() {
        return average;
    }

    public void setAverage(String average) {
        this.average.set(average);
    }

    public String getBorderBallImage() {
        return borderBallImage.get();
    }

    public StringProperty borderBallImageProperty() {
        return borderBallImage;
    }

    public void setBorderBallImage(String borderBallImage) {
        this.borderBallImage.set(borderBallImage);
    }

    public boolean isAverageVisible() {
        return averageVisible.get();
    }

    public BooleanProperty averageVisibleProperty() {
        return averageVisible;
    }

    public void setAverageVisible(boolean averageVisible) {
        this.averageVisible.set(averageVisible);
    }
}
