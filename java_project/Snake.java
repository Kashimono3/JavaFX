import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import java.util.LinkedList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Snake {
    public static final int RIGHT = 0;
    public static final int LEFT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;
    public static final int DEFAULT_SPEED = 0;

    private LinkedList<Point> body;
    private int currentDirection;
    private boolean growNextMove;
    private Image headUpImage;
    private Image headDownImage;
    private Image headLeftImage;
    private Image headRightImage;
    private Image headImage;
    private Image bodyImage;
    private Color originalColor; // Store the original snake color
    private int currentSpeed;


    public Snake() {
        body = new LinkedList<>();

        headUpImage = new Image("/img/snake/head_up_image.png");
        headDownImage = new Image("/img/snake/head_down_image.png");
        headLeftImage = new Image("/img/snake/head_left_image.png");
        headRightImage = new Image("/img/snake/head_right_image.png");
        bodyImage = new Image("/img/snake/body.png");
        // Initialize the snake with a default length and position
        body.add(new Point(5, 5, Color.BLUE)); // Initially set the color to blue
        currentDirection = RIGHT;
        growNextMove = false;
        
        originalColor = Color.BLUE; // Store the default color as the original color

    }

    public void move() {
        Point head = body.getFirst();
        Point newHead = new Point(head.getX(), head.getY(), head.getColor()); // Keep the color when moving

        switch (currentDirection) {
            case RIGHT:
                newHead.moveRight();
                break;
            case LEFT:
                newHead.moveLeft();
                break;
            case UP:
                newHead.moveUp();
                break;
            case DOWN:
                newHead.moveDown();
                break;
        }

        body.addFirst(newHead);

        if (!growNextMove) {
            body.removeLast();
        } else {
            growNextMove = false;
        }


        changeColor(originalColor);
    }

    public void grow() {
        growNextMove = true;
    }

    public void setCurrentDirection(int direction) {
        currentDirection = direction;
    }

    public LinkedList<Point> getBody() {
        return body;
    }

    public void draw(GraphicsContext gc, int squareSize) {
        Point head = body.getFirst();
        if (headImage != null) {
            gc.drawImage(headImage, head.getX() * squareSize, head.getY() * squareSize, squareSize, squareSize);
        } else {
            gc.setFill(head.getColor());
            gc.fillRoundRect(head.getX() * squareSize, head.getY() * squareSize, squareSize, squareSize, 10, 10);
        }

        for (int i = 1; i < body.size(); i++) {
            Point bodyPart = body.get(i);
            gc.setFill(Color.BLUE);  // Set the color to blue
            gc.fillRoundRect(bodyPart.getX() * squareSize, bodyPart.getY() * squareSize, squareSize, squareSize, 10, 10);
        }
    }

    public boolean checkSelfCollision() {
        Point head = body.getFirst();
        for (int i = 1; i < body.size(); i++) {
            Point segment = body.get(i);
            if (head.getX() == segment.getX() && head.getY() == segment.getY()) {
                return true;
            }
        }
        return false;
    }

    public boolean checkWallCollision(int maxX, int maxY) {
        Point head = body.getFirst();
        int headX = (int) head.getX();
        int headY = (int) head.getY();

        return headX < 0 || headY < 0 || headX >= maxX || headY >= maxY;
    }

    public int getCurrentDirection() {
        return currentDirection;
    }

    public void updateHeadImage() {
        switch (currentDirection) {
            case UP:
                headImage = headUpImage;
                break;
            case DOWN:
                headImage = headDownImage;
                break;
            case LEFT:
                headImage = headLeftImage;
                break;
            case RIGHT:
                headImage = headRightImage;
                break;
        }
    }

    public void changeColor(Color color) {
        for (Point bodyPart : body) {
            bodyPart.setColor(color);
        }
    }

 
   

}
