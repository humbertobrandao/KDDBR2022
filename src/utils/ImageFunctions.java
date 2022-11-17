package utils;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 *
 * @author humberto
 */
public class ImageFunctions {

    public static int[][][] getSubImage(BufferedImage image, int x0, int x1, int y0, int y1, int[][][] tmp) {

        if (tmp == null) {
            tmp = new int[3][x1 - x0][y1 - y0];
        }

        for (int x = x0; x < x1; x++) {
            for (int y = y0; y < y1; y++) {
                int pixel = image.getRGB(x, y);

                //Creating a Color object from pixel value
                Color color = new Color(pixel, true);

                //Retrieving the R G B values
                tmp[Dimension.RED][x - x0][y - y0] = color.getRed();
                tmp[Dimension.GREEN][x - x0][y - y0] = color.getGreen();
                tmp[Dimension.BLUE][x - x0][y - y0] = color.getBlue();
            }
        }
        return tmp;
    }

    public static int[][][] getSubImage(int[][][] image, int x, int y, int w, int h, int[][][] tmp) {
        return ImageFunctions.getSubImage(image, new Point(x, y), w, h, tmp);
    }

    public static int[][][] getSubImage(int[][][] image, Point center, int w, int h, int[][][] tmp) {

        int x0 = ((int) center.getX()) - (int) (w / 2);
        int x1 = x0 + w;
        int y0 = ((int) center.getY()) - (int) (h / 2);
        int y1 = y0 + h;

//        if( x0 >= x1 || y0 >= y1){
//            throw new RuntimeException("Invalid dimensions");
//        }
        int diff_x = x1 - x0;
        int diff_y = y1 - y0;

        if (tmp == null) {
            tmp = new int[3][diff_x][diff_y];
        }
        for (int x = x0; x < x1; x++) {
            for (int y = y0; y < y1; y++) {
                tmp[Dimension.RED][x - x0][y - y0] = image[Dimension.RED][x][y];
                tmp[Dimension.GREEN][x - x0][y - y0] = image[Dimension.GREEN][x][y];
                tmp[Dimension.BLUE][x - x0][y - y0] = image[Dimension.BLUE][x][y];
            }
        }
        return tmp;
    }

    public static int[][][] getOriginalImageFromTheRight(BufferedImage img, int[][][] tmp) {
        return ImageFunctions.getSubImage(img, 120, 240, 0, 120, tmp);
    }

    public static int[][][] getOriginalImageFromTheLeft(BufferedImage img, int[][][] tmp) {
        return ImageFunctions.getSubImage(img, 0, 120, 0, 120, tmp);
    }

    public static int[] linearisation(int[][][] image, int[] vet) {
        if (vet == null) {
            int size = image.length * image[0].length * image[0][0].length;
            vet = new int[size];
        }
        int index = 0;
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[i].length; j++) {
                for (int k = 0; k < image[i][j].length; k++) {
                    vet[index] = image[i][j][k];
                    index++;
                }
            }
        }
        return vet;
    }

    public static int[] linearisation(int[][][] image) {
        int size = image.length * image[0].length * image[0][0].length;
        int[] vet = new int[size];

        int index = 0;
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[i].length; j++) {
                for (int k = 0; k < image[i][j].length; k++) {
                    vet[index] = image[i][j][k];
                    index++;
                }
            }
        }
        return vet;
    }

    public static double distance(int[] array1, int[] array2) {
        double sum = 0.0;
        for (int i = 0; i < array1.length; i++) {
            sum += Math.pow((array1[i] - array2[i]), 2.0);
        }
        return Math.sqrt(sum);
    }
    
    public static int[][][] getSubImage(BufferedImage image, int x0, int x1, int y0, int y1){ 
        if( x0 >= x1 || y0 >= y1){
            throw new RuntimeException("Invalid dimensions");
        }
        
        int diff_x = x1-x0;
        int diff_y = y1-y0;
        
        int[][][] img = new int[3][diff_x][diff_y];
        for (int x = x0; x < x1; x++) {
                for (int y = y0; y < y1; y++) {
                    int pixel = image.getRGB(x, y);
                    
                    //Creating a Color object from pixel value
                    Color color = new Color(pixel, true);
                                        
                    //Retrieving the R G B values
                    img[Dimension.RED]  [x-x0][y-y0] = color.getRed();
                    img[Dimension.GREEN][x-x0][y-y0] = color.getGreen();
                    img[Dimension.BLUE] [x-x0][y-y0] = color.getBlue();           
                }
        }
        return img;
    }
    
    public static int[][][] getSubImage(int[][][] image, int x, int y, int w, int h){
        return ImageFunctions.getSubImage(image, new Point(x,y), w, h);
    }
    
    public static int[][][] getSubImage(int[][][] image, Point center, int w, int h){
        
        int x0 = ((int)center.getX()) - (int)(w/2);
        int x1 = x0 + w;
        int y0 = ((int)center.getY()) - (int)(h/2);
        int y1 = y0 + h;
                
        if( x0 >= x1 || y0 >= y1){
            throw new RuntimeException("Invalid dimensions");
        }
        
        int diff_x = x1-x0;
        int diff_y = y1-y0;
        
        int[][][] img = new int[3][diff_x][diff_y];
        for (int x = x0; x < x1; x++) {
            for (int y = y0; y < y1; y++) {
                img[Dimension.RED][x - x0][y - y0] = image[Dimension.RED][x][y];
                img[Dimension.GREEN][x - x0][y - y0] = image[Dimension.GREEN][x][y];
                img[Dimension.BLUE][x - x0][y - y0] = image[Dimension.BLUE][x][y];
            }
        }
        return img;
    }
    
    public static int[][][] getOriginalImageFromTheRight(BufferedImage img){
        return ImageFunctions.getSubImage(img, 120, 240, 0, 120);
    }
    
    public static int[][][] getOriginalImageFromTheLeft(BufferedImage img){
        return ImageFunctions.getSubImage(img, 0, 120, 0, 120);
    }

}
