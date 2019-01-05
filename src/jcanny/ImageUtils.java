/**
 * Copyright 2016 Robert Streetman
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package jcanny;

import java.awt.image.BufferedImage;

/**
 * This class contains utility methods for transforming image data.
 * 
 * @author robert
 */

public class ImageUtils {
    
    /**
     * Send this method a BufferedImage to get an RGB array (int, value 0-255).
     * 
     * @param img   BufferedImage, the input image from which to extract RGB
     * @return rgb  int[][][], a 3-dimension array of RGB values from image
     * 
     */
    public static int[][][] RGBArray(BufferedImage img) {
        int[][][] rgb = null;
        int height = img.getHeight();
        int width = img.getWidth();
        
        if (height > 0 && width > 0) {
            rgb = new int[height][width][3];

            for (int row = 0; row < height; row++) {
                for (int column = 0; column < width; column++) {
                    rgb[row][column] = intRGB(img.getRGB(column, row));
                }
            }
        }
        
        return rgb;
    }
    
    /**
     * Send this method an array of RGB pixels (int) to get a BufferedImage.
     * 
     * @param raw   int[][][] representing RGB pixels of image.
     * @return img  BufferedImage built from RGB array
     */
    public static BufferedImage RGBImg(int[][][] raw) {
        BufferedImage img = null;
        int height = raw.length;
        int width = raw[0].length;
        
        if (height > 0 && width > 0 || raw[0][0].length == 3) {
            img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            for (int row = 0; row < height; row++) {
                for (int column = 0; column < width; column++) {
                    img.setRGB(column, row, (raw[row][column][0] << 16) | (raw[row][column][1] << 8) | (raw[row][column][2]));
                }
            }
        }
        
        return img;
    }
    
    /**
     * Send this method a BufferedImage to get a grayscale array (int, value 0-255.
     * 
     * @param img   BufferedImage, the input image from which to extract grayscale
     * @return gs   int[][] array of grayscale pixel values from image.
     */
    public static int[][] GSArray(BufferedImage img) {
        int[][] gs = null;
        int height = img.getHeight();
        int width = img.getWidth();
        
        if (height > 0 && width > 0) {
            gs = new int[height][width];

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int bits = img.getRGB(j, i);
                    //Not sure if precision is needed, but adding for now
                    long avg = Math.round((((bits >> 16) & 0xff) + ((bits >> 8) & 0xff) + (bits & 0xff)) / 3.0);
                    gs[i][j] = (int) avg;
                }
            }
        }
        
        return gs;
    }
    
    /**
     * Send this method an array of grayscale pixels (int) to get a BufferedImage
     * 
     * @param raw   int[][] representing grayscale pixels of image.
     * @return img  BufferedImage built from grayscale array 
     */
    public static BufferedImage GSImg(int[][] raw) {
        BufferedImage img = null;
        int height = raw.length;
        int width = raw[0].length;
        
        if (height > 0 && width > 0) {
            img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    img.setRGB(j, i, (raw[i][j] << 16) | (raw[i][j] << 8) | (raw[i][j]));
                }
            }
        }
        
        return img;
    }
    
    /*
     * Accepts BufferedImage, returns double[][][] array of HSV values
     */
    /**
     * Currently unsupported - Send this method a BufferedImage to get a double[][][] HSV array.
     * 
     * @deprecated
     */
    
    /**
     * Send this method a 32-bit pixel value from BufferedImage to get the RGB
     * 
     * @param bits  int, 32-bit BufferedImage pixel value
     * @return rgb  int[], RGB values extracted from pixel  
     */
    private static int[] intRGB(int bits) {
        int[] rgb = { (bits >> 16) & 0xff, (bits >> 8) & 0xff, bits & 0xff };
        
        //Don't propagate bad pixel values
        for (int i = 0; i < 3; i++) {
            if (rgb[i] < 0) {
                rgb[i] = 0;
            } else if (rgb[i] > 255) {
                rgb[i] = 255;
            }
        }
        
        return rgb;
    }
    
    /*
     * Accepts 3 integer values, returns double of lowest value
     */
    private static double Min(int a, int b, int c) {
        if (a <= b && a <= c) {
            return (double) a;
        } else if (b <= c && b <= a) {
            return (double) b;
        } else {
            return (double) c;
        }
    }
}
