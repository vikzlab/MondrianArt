// Name: Vikram Murali 


import java.util.*;
import java.awt.*;
import java.util.List;

// This class simulates the creation of Mondrian paintings.
// It provides methods to generate both basic and complex Mondrian styles on a given pixel canvas.
public class Mondrian {

    private static final Random rand = new Random();

    // Paints a basic Mondrian-style painting on a given pixel canvas
    // Fills the canvas with randomly divided and colored sections in a simpler pattern
    // Parameters:
    //  pixels: a 2D array of Color objects representing the canvas
    // Throws IllegalArgumentException if the dimensions of pixels are less than 300x300
    public void paintBasicMondrian(Color[][] pixels) {
        if (pixels.length < 300 || pixels[0].length < 300) {
            throw new IllegalArgumentException("Dimensions have to be Greater than or equal to 300");
        }
        int height = pixels.length;
        int width = pixels[0].length;
        divideCanvas(pixels, 0, width, 0, height, true);
    }

    // Paints a complex Mondrian-style painting on a given pixel canvas
    // Fills the canvas with randomly divided and colored sections allowing
    // more frequent color variations
    // Parameters:
    //  pixels: a 2D array of Color objects representing the canvas
    // Throws IllegalArgumentException if the dimensions of pixels are less than 300x300
    public void paintComplexMondrian(Color[][] pixels) {
        if (pixels.length < 300 || pixels[0].length < 300) {
            throw new IllegalArgumentException("Dimensions have to be Greater than or equal to 300");
        }
        int height = pixels.length;
        int width = pixels[0].length;
        divideCanvas(pixels, 0, width, 0, height, false);
    }

    // Divides the given canvas into smaller sections and colors them
    // This method handles both simple and complex Mondrian painting logic
    // Parameters:
    //    pixels: 2D array of Color objects representing the canvas
    //    x1, x2: Horizontal boundaries of the current section
    //    y1, y2: Vertical boundaries of the current section
    //    simple: Boolean flag to choose between simple and complex painting styles
    private void divideCanvas(Color[][] pixels, int x1, int x2, int y1, int y2, boolean simple) {
        int width = x2 - x1;
        int height = y2 - y1;
        int reqWidth = pixels[0].length / 4;
        int reqHeight = pixels.length / 4;
        Color colorFill;
        if (width > reqWidth || height > reqHeight) {
            if (width >= reqWidth && height >= reqHeight) {
                int splitX = x1 + 10 + rand.nextInt(x2 - x1 - 20);
                int splitY = y1 + 10 + rand.nextInt(y2 - y1 - 20);
                divideCanvas(pixels, x1, splitX, y1, splitY, simple);
                divideCanvas(pixels, splitX, x2, y1, splitY, simple);
                divideCanvas(pixels, x1, splitX, splitY, y2, simple);
                divideCanvas(pixels, splitX, x2, splitY, y2, simple);
            } else if (width >= reqWidth) {
                int splitX = x1 + 10 + rand.nextInt(x2 - x1 - 20);
                divideCanvas(pixels, x1, splitX, y1, y2, simple);
                divideCanvas(pixels, splitX, x2, y1, y2, simple);
            } else if (height >= reqHeight) {
                int splitY = y1 + 10 + rand.nextInt(y2 - y1 - 20);
                divideCanvas(pixels, x1, x2, y1, splitY, simple);
                divideCanvas(pixels, x1, x2, splitY, y2, simple);
            }
        } else {
            colorFill = chooseColor(simple, pixels, x1, x2, y1, y2);
            for (int col = x1 + 1; col < x2 - 1; col++) {
                for (int row = y1 + 1; row < y2 - 1; row++) {
                    pixels[row][col] = colorFill;
                }
            }
        }
    }

    // Chooses a color for filling a section of the canvas
    // Depending on the 'simple' flag, it may introduce more of one color 
    // to influence the painting style
    // Parameters:
    //  simple: If true, limits color variations. If false, introduces more 
    //  frequent color changes
    //  pixels: 2D array of the canvas to access dimensions
    //  x1, x2, y1, y2: Coordinates defining the section to be colored
    // Returns:
    //  Color: The selected color to fill the section
    private Color chooseColor(boolean simple, Color[][] pixels, int x1, int x2, int y1, int y2) {
        List<Color> addColors = new ArrayList<>();
        addColors.add(Color.RED);
        addColors.add(Color.YELLOW);
        addColors.add(Color.CYAN);
        addColors.add(Color.WHITE);
        if (!simple) {
            Color regionColor = colorRegion(pixels.length, pixels[0].length, (x2 + x1) / 2, (y2 + y1) / 2);
            for (int i = 0; i < 5; i++) {
                addColors.add(regionColor);
            }
        }
        return addColors.get(rand.nextInt(addColors.size()));
    }

    // Determines the predominant color for a region based on its position on the canvas
    // This is used to introduce variety in complex Mondrian paintings
    // Parameters:
    //  height, width: Dimensions of the canvas
    //  x, y: Central point of the region being colored
    // Returns:
    //   Color: The color determined based on the region's location
    private Color colorRegion(int height, int width, int x, int y) {
        if (x <= width / 2 && y > height / 2) {
            return Color.WHITE;
        } else if (x <= width / 2 && y <= height / 2) {
            return Color.RED;
        } else if (x > width / 2 && y <= height / 2) {
            return Color.CYAN;
        } else {
            return Color.YELLOW;
        }
    }
}