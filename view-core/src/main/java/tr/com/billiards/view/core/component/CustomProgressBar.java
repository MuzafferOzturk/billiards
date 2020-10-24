package tr.com.billiards.view.core.component;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomProgressBar extends HBox {
    private final ProgressBar progressBar = new ProgressBar();
    private final ProgressIndicator progressIndicator = new ProgressIndicator();
    private final IntegerProperty maxTimeProperty = new SimpleIntegerProperty(30);
    private final ReadOnlyDoubleWrapper currentValue = new ReadOnlyDoubleWrapper(30);
    private Task currentValueDecreaseTask;
    private ExecutorService service = Executors.newSingleThreadExecutor();
    private ProgressBarFinishEvent finishEvent;
    private AtomicBoolean isRunning = new AtomicBoolean(false);

    private void initializeCurrentValueTask() {
        currentValueDecreaseTask = new Task() {
            @Override
            protected Object call() throws Exception {
                while (true) {
                    if (isRunning.get() && currentValue.get() > 0) {
                        currentValue.setValue(currentValue.get() - 1);
                        if (currentValue.get() <= 0) {
                            if (finishEvent != null)
                                finishEvent.apply();
                        }
                    }
                    Thread.sleep(1000);
                }
            }
        };
    }

    private void bindProgress() {
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                currentValue
                        .addListener((observableValue, number, numberNew)
                                -> updateProgress(numberNew.doubleValue(), maxTimeProperty.doubleValue()));
                return null;
            }
        };
        task.run();
        progressIndicator.progressProperty().bind(task.progressProperty());
        progressBar.progressProperty().bind(task.progressProperty());
    }

    private AnchorPane createProgressLabel() {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(progressBar);
        Label progressLabel = new Label();
        currentValue.addListener((observableValue, number, numberNew)
                -> Platform.runLater(() -> progressLabel.setText(String.valueOf(numberNew.intValue()))));
        HBox labelBox = new HBox();
        labelBox.setAlignment(Pos.CENTER);
        labelBox.getChildren().add(progressLabel);
        AnchorPane.setBottomAnchor(labelBox, 0.0);
        AnchorPane.setLeftAnchor(labelBox, 0.0);
        AnchorPane.setRightAnchor(labelBox, 0.0);
        AnchorPane.setTopAnchor(labelBox, 0.0);
        progressLabel.textAlignmentProperty().set(TextAlignment.CENTER);
        anchorPane.getChildren().add(labelBox);
        return anchorPane;
    }

    public CustomProgressBar() {
        this.setStyle("-fx-background-color: rgb(51, 51, 51)");
        this.getStylesheets().add(getClass().getResource("/css/customProgressBar.css").toExternalForm());
        progressIndicator.setMinHeight(100);
        progressIndicator.setMaxHeight(100);
        progressBar.setMinHeight(100);
        getChildren().add(createProgressLabel());
        getChildren().add(progressIndicator);
        this.widthProperty()
                .addListener((observableValue, number, numberNew)
                        -> progressBar.setMinWidth(numberNew.doubleValue() - 100));
        initializeCurrentValueTask();
        bindProgress();
        service.execute(currentValueDecreaseTask);
    }

    public void startProgress() {
        isRunning.set(true);
    }

    public void pauseProgress() {
        isRunning.set(false);
    }

    public void restartProgress() {
        pauseProgress();
        this.currentValue.setValue(maxTimeProperty.getValue());
        startProgress();
    }

    public int getMaxTimeProperty() {
        return maxTimeProperty.get();
    }

    public IntegerProperty maxTimePropertyProperty() {
        return maxTimeProperty;
    }

    public void setMaxTimeProperty(int maxTimeProperty) {
        this.maxTimeProperty.set(maxTimeProperty);
        this.currentValue.setValue(maxTimeProperty);
    }

    public ProgressBarFinishEvent getFinishEvent() {
        return finishEvent;
    }

    public void setFinishEvent(ProgressBarFinishEvent finishEvent) {
        this.finishEvent = finishEvent;
    }

    public boolean getIsRunning() {
        return isRunning.get();
    }
}
