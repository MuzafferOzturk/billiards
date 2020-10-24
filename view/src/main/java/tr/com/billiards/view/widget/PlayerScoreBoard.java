package tr.com.billiards.view.widget;

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
    private StringProperty playerNumber = new SimpleStringProperty("0");
    private StringProperty billiardsCueCount = new SimpleStringProperty("0");
    private StringProperty playerName = new SimpleStringProperty("Oyuncu");
    private StringProperty playerPoint = new SimpleStringProperty("0");
    private StringProperty average = new SimpleStringProperty("0.000");

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

    private void averageListener() {

    }

    public PlayerScoreBoard() {
        this.setSpacing(20);
        this.getChildren().add(createPlayerName());
        this.getChildren().add(createPlayerPoint());
        this.getChildren().add(createPlayerAverage());
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
}
