import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class SnakeGame {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 700;
    private static final int SQUARE_SIZE = 20;
    private double snakeSpeed;
    private int scoreIncreaseCount;
    private static final int SPEED_INCREASE_THRESHOLD = 50;

    private Snake snake;
    private Canvas canvas;
    private GraphicsContext gc;
    private Timeline gameLoop;
    private Food food;
    private Background background;
    private Score score;
    private Image foodImage;
    // test mode




    public Scene initGame() {
        snake = new Snake();
        score = new Score();
        snakeSpeed = 100.0;
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        food = new Food(20, 20);
        food.randomizePosition(WIDTH / SQUARE_SIZE, HEIGHT / SQUARE_SIZE);
        foodImage = food.getImage();
//Test mode
       
//
        background = new Background(WIDTH, HEIGHT, SQUARE_SIZE, Color.web("AAD751"), Color.web("A2D149"));

        gameLoop = new Timeline(new KeyFrame(Duration.millis(100), e -> gameUpdate()));
        gameLoop.setCycleCount(Animation.INDEFINITE);

        Scene gameScene = new Scene(new BorderPane(canvas));
        gameScene.setOnKeyPressed(event -> handleKeyPress(event.getCode()));

        return gameScene;
    }

    public void startGameLoop() {
        gameLoop.play();
    }

    private void gameUpdate() {
        snake.move();
        
        if (snake.getBody().getFirst().getX() == food.getX() && snake.getBody().getFirst().getY() == food.getY()) {
            
            snake.grow();
           food.randomizePosition(WIDTH / SQUARE_SIZE, HEIGHT / SQUARE_SIZE);
            foodImage = food.getImage();
            score.increaseScore(10);
            updateSnakeSpeed();
            
        }
       

        if (snake.checkWallCollision(WIDTH / SQUARE_SIZE, HEIGHT / SQUARE_SIZE)) {
            gameLoop.stop();
            displayGameOver();
            return;
        }

        if (snake.checkSelfCollision()) {
            gameLoop.stop();
            displayGameOver();
        } else {
            snake.updateHeadImage();
            renderGame();
        }


    }

    private void renderGame() {
        gc.clearRect(0, 0, WIDTH, HEIGHT);
        background.draw(gc);
        snake.draw(gc, SQUARE_SIZE);
        drawScore();
       
        gc.drawImage(foodImage, food.getX() * SQUARE_SIZE, food.getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
    }

    private void handleKeyPress(KeyCode code) {
        int newDirection = -1;
        int currentDirection = snake.getCurrentDirection();
        switch (code) {
            case UP:
                if (currentDirection != Snake.DOWN)
                    newDirection = Snake.UP;
                break;
            case DOWN:
                if (currentDirection != Snake.UP)
                    newDirection = Snake.DOWN;
                break;
            case LEFT:
                if (currentDirection != Snake.RIGHT)
                    newDirection = Snake.LEFT;
                break;
            case RIGHT:
                if (currentDirection != Snake.LEFT)
                    newDirection = Snake.RIGHT;
                break;
        }
        if (newDirection != -1) {
            snake.setCurrentDirection(newDirection);
        }
    }

    private void displayGameOver() {
        gc.clearRect(0, 0, WIDTH, HEIGHT);
        gc.setFill(Color.RED);
        gc.setFont(new Font("Arial", 50));
        gc.fillText("Game Over", WIDTH / 2 - 100, HEIGHT / 2);
        
    }

    private void drawScore() {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 20));
        gc.fillText("Score: " + score.getScore(), 10, 30);
    }


    private void updateSnakeSpeed() {
        
        if (scoreIncreaseCount > 0 && scoreIncreaseCount % SPEED_INCREASE_THRESHOLD == 0) {
            snakeSpeed += 10.0; 
            gameLoop.setRate(snakeSpeed / 100.0);
        }
    }
    
}