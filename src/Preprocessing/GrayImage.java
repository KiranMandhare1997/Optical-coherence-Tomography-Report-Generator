/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Preprocessing;

/**
 *
 * @author Kiran Mandhare
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.image.BufferedImage;

/**
 *
 * @author palasjiri
 */
public class GrayImage {

    int w, h;
    int pixels[][];

    public GrayImage(int w, int h) {
        this.w = w;
        this.h = h;
        pixels = new int[w][h];
    }
    public GrayImage(BufferedImage orignalImage)
    {
        this.w=orignalImage.getWidth();
        this.h=orignalImage.getHeight();
        this.pixels = new int[w][h];
        for(int y=0;y<this.h;y++)
        {
            for(int x=0;x<this.w;x++)
            {
                pixels[x][y] = orignalImage.getRGB(x, y);
            }
        }
    }
    public final void setPixel(int x, int y, int sample) {
        pixels[x][y] = sample;
    }

    public BufferedImage getBufferedImage() 
    {
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < h; y++) {
    
            for (int x = 0; x < w; x++) {
                //int value = pixels[x][y] << 16 | pixels[x][y] << 8 | pixels[x][y];
                image.setRGB(x, y, pixels[x][y]);
            }
        }
        return image;
    }
    
    public int[][] getRPixels() {
        int[][] r = new int[w][h];
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r[0].length; j++) {
                r[i][j] = pixels[i][j] >> 0 & 0xFF; 
            }
            
        }
        return r;
    }
    
    public int[][] getGPixels() {
        int[][] g = new int[w][h];
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g[0].length; j++) {
                g[i][j] = pixels[i][j] >> 8 & 0xFF; 
            }            
        }
        return g;
    }
    public int[][] getBPixels() {
        int[][] b = new int[w][h];
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                b[i][j] = pixels[i][j] >> 16 & 0xFF; 
            }
            
        }
        return b;
    }
    

}
