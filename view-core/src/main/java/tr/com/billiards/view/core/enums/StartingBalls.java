package tr.com.billiards.view.core.enums;

public enum StartingBalls {
    YELLOW("/icons/yellow_ball.png"),
    WHITE("/icons/white_ball.png");

    private String ballImage;

    StartingBalls(String ballImage) {
        this.ballImage = ballImage;
    }

    public String getBallImage() {
        return ballImage;
    }
}
