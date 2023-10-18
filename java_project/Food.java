import javafx.scene.image.Image;

import java.util.Random;

public class Food {


    private static final String[] FRUIT_IMAGE_PATHS = {
            "/img/food/ic_apple.png",
            "/img/food/ic_tomato.png",
            "/img/food/ic_orange.png",
            "/img/food/ic_cherry.png"
            // Add more image paths for other fruits as needed
            
    };

    private Image[] fruitImages;
    protected int x;
    protected int y;
    private Snake snake; 

    public Food(int maxX, int maxY) {
        // Load images for the fruit
        fruitImages = new Image[FRUIT_IMAGE_PATHS.length];
        for (int i = 0; i < FRUIT_IMAGE_PATHS.length; i++) {
            fruitImages[i] = new Image(FRUIT_IMAGE_PATHS[i]);
        }

        randomizePosition(maxX, maxY);

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getImage() {
        Random random = new Random();
        int index = random.nextInt(fruitImages.length);
        
        return fruitImages[index];
    }

    public void randomizePosition(int maxX, int maxY) {
        Random random = new Random();
        this.x = random.nextInt(maxX);
        this.y = random.nextInt(maxY);
    }


}
