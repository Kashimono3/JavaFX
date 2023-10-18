import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Background {
    private int width;
    private int height;
    private int squareSize;
    private Color color1;
    private Color color2;

    public Background(int width, int height, int squareSize, Color color1, Color color2) {
        this.width = width;
        this.height = height;
        this.squareSize = squareSize;
        this.color1 = color1;
        this.color2 = color2;
    }

    public void draw(GraphicsContext gc) {
        for (int i = 0; i < width / squareSize; i++) {
            for (int j = 0; j < height / squareSize; j++) {
                gc.setFill((i + j) % 2 == 0 ? color1 : color2);
                gc.fillRect(i * squareSize, j * squareSize, squareSize, squareSize);
            }
        }
    }
}
