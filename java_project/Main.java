import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showGameScreen();
    }


    private void showGameScreen() {
        // Create an instance of the SnakeGame class (assuming SnakeGame is your game logic class)
        Mode2 snakeGame = new Mode2();

        // Initialize the game, set up the scene, and start the game loop
        Scene gameScene = snakeGame.initGame();
        primaryStage.setScene(gameScene);
        primaryStage.setTitle("Snake Game");
        primaryStage.show();

        // Start the game loop
        snakeGame.startGameLoop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
