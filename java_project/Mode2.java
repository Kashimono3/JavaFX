
import java.util.Random;

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

public class Mode2 {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 700;
    private static final int SQUARE_SIZE = 20;
    private double snakeSpeed;  // Tốc độ ban đầu của rắn
    private int scoreIncreaseCount;  // Số lần tăng điểm
    private static final int SPEED_INCREASE_THRESHOLD = 50;  // Ngưỡng để tăng tốc độ

    private Snake snake;
    private Canvas canvas;
    private GraphicsContext gc;
    private Timeline gameLoop;
    private Food food;
    private Background background;
    private Score score;
    private Image foodImage;

    // test mode    
    private Wall wall;
    private TimeAttackMode timeAttackMode;

    public Scene initGame() {
        // Create a new Snake and set up the game canvas
        snake = new Snake();
        score = new Score();
        
        snakeSpeed = 100.0;
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        food = new Food(20, 20);//, null, null);  // Adjust the range as needed
        food.randomizePosition(WIDTH / SQUARE_SIZE, HEIGHT / SQUARE_SIZE);
        foodImage = food.getImage();  // Lấy hình ảnh ngẫu nhiên từ Food
        background = new Background(WIDTH, HEIGHT, SQUARE_SIZE, Color.web("AAD751"), Color.web("A2D149"));
        //
        timeAttackMode = new TimeAttackMode();
        wall = new Wall(WIDTH / SQUARE_SIZE, HEIGHT / SQUARE_SIZE);
        // Generate initial walls
        wall.generateRandomWalls();
        
        // Set up the game loop
        gameLoop = new Timeline(new KeyFrame(Duration.millis(100), e -> gameUpdate()));
        gameLoop.setCycleCount(Animation.INDEFINITE);

        // Set up keyboard input
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
            while (wall.checkCollision(food)) {
                food.randomizePosition(WIDTH / SQUARE_SIZE, HEIGHT / SQUARE_SIZE);
            }
            snake.grow();
            food.randomizePosition(WIDTH / SQUARE_SIZE, HEIGHT / SQUARE_SIZE);
            foodImage = food.getImage(); 
            score.increaseScore(10);
            if (score.getScore() % 10 == 0 && score.getScore() < 200 ){
                
                wall.generateRandomWalls();
            }
            timeAttackMode.updateHighScore(snake.getBody().size());
            timeAttackMode.updateTime(true);
            updateSnakeSpeed();
        }
        if (timeAttackMode.getTimeRemaining() == 0) {
            gameLoop.stop();
            displayGameOver();
            return;
        }
        timeAttackMode.updateTime();
        if (snake.checkWallCollision(WIDTH / SQUARE_SIZE, HEIGHT / SQUARE_SIZE)|| wallCollision() ) {
            gameLoop.stop();
            displayGameOver();
            timeAttackMode.updateHighScore(snake.getBody().size());
            return;
        }

        if (snake.checkSelfCollision()) {
            
            gameLoop.stop();
            displayGameOver();
            timeAttackMode.updateHighScore(snake.getBody().size());
        }
     
       
        else {
           
            snake.updateHeadImage();
            renderGame();
        }
    }

    private void renderGame() {
        // Clear the canvas
        gc.clearRect(0, 0, WIDTH, HEIGHT);
        // Draw the background
        background.draw(gc);
        // Draw the snake
        snake.draw(gc, SQUARE_SIZE);
        // Draw the score
        drawScore();
        //
        wall.draw(gc, SQUARE_SIZE);
        // Draw the food using the random image
        
        gc.drawImage(foodImage, food.getX() * SQUARE_SIZE, food.getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 20));
        gc.fillText("Time: " + timeAttackMode.getTimeRemaining(), WIDTH - 150, 30);
    }

    private void handleKeyPress(KeyCode code) {
        // Handle keyboard input to set the snake's direction
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
        // Clear the canvas
        gc.clearRect(0, 0, WIDTH, HEIGHT);
    
        // Display "Game Over" text
        gc.setFill(Color.RED);
        gc.setFont(new Font("Arial", 50));
        gc.fillText("Game Over", WIDTH / 2 - 100, HEIGHT / 2);
        gc.fillText("High Score: " + score.getScore(), WIDTH / 2 - 100, HEIGHT / 2 + 50);
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
    private boolean wallCollision() {
        for (Point bodyPart : snake.getBody()) {
            if (wall.checkCollision(bodyPart)) {
                return true;
            }
        }
        return false;
    }

   

}
