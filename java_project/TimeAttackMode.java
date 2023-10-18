public class TimeAttackMode {
    private int timeRemaining;
    private int highScore;

    private static final int MAX_TIME = 120;

    public TimeAttackMode() {
        timeRemaining = MAX_TIME;
        highScore = 0;
    }

    public void updateTime() {
        if (timeRemaining > 0) {
            timeRemaining--;
        } else {
            // Time's up
            return;
        }
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public void resetTime() {
        timeRemaining = MAX_TIME;
    }

    public int getHighScore() {
        return highScore;
    }

    public void updateHighScore(int score) {
        if (score > highScore) {
            highScore = score;
        }
    }
    public void updateTime(boolean snakeAteFood) {
        if (timeRemaining > 0) {
            timeRemaining--;
            if (snakeAteFood) {
                // Nếu rắn ăn thành công, thêm 30 giây vào thời gian còn lại
                timeRemaining += 30;
                if (timeRemaining > MAX_TIME) {
                    timeRemaining = MAX_TIME;
                }
            }
        } else {
            // Time's up
            return;
        }
    }
}
