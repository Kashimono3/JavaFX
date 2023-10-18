
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Wall {
    private List<Point> walls;
    private int maxX;
    private int maxY;

    public Wall(int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
        walls = new ArrayList<>();
    }

    public void generateRandomWalls() {
        Random random = new Random();

        // Generate a random number of walls (up to 20)
        int numWalls = random.nextInt(1) + 3;

        for (int i = 0; i < numWalls; i++) {
            int wallX = random.nextInt(maxX);
            int wallY = random.nextInt(maxY);
            Point wall = new Point(wallX, wallY, Color.GRAY);
            walls.add(wall);
        }
    }

    public boolean checkCollision(Point point) {
        for (Point wall : walls) {
            if (point.getX() == wall.getX() && point.getY() == wall.getY()) {
                return true;
            }
        }
        return false;
    }

    public void draw(GraphicsContext gc, int squareSize) {
        for (Point wall : walls) {
            gc.setFill(wall.getColor());
            gc.fillRoundRect(wall.getX() * squareSize, wall.getY() * squareSize, squareSize, squareSize, 10, 10);
        }
    }
    public boolean checkCollision(Food food) {
        for (Point wall : walls) {
            if (wall.getX() == food.getX() && wall.getY() == food.getY()) {
                return true;
            }
        }
        return false;
    }

}
