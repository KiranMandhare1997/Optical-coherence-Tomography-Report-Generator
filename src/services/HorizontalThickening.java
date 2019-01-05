/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Kiran Mandhare
 */
public class HorizontalThickening 
{
    public BufferedImage horizontalThickening(BufferedImage img)
    {
        //BufferedImage output=new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_BYTE_BINARY);
        boolean arr[][]=new boolean[img.getHeight()][img.getWidth()];
        for(int y=0;y<img.getHeight();y++)
        {
            for(int x=0;x<img.getWidth();x++)
                arr[y][x]=((img.getRGB(x, y) & 0xff) == 255);
        }
//         for(int y=0;y<img.getHeight();y++)
//        {
//            for(int x=0;x<img.getWidth();x++)
//                System.out.print((arr[y][x])?1:0);//=( (int)(0xff&output.getRGB(x, y)) == 255);
//            System.out.println();
//        }
        Color black=new Color(0,0,0);
        Color white=new Color(255,255,255);
        int w=white.getRGB();
        int b=black.getRGB();
        for(int y=0;y<img.getHeight();y++)
        {
            for(int x=0;x<img.getWidth()-3;x++)
            {
                //img.setRGB(x,y,(arr[y][x])?w:b);
                img.setRGB(x+1,y,(arr[y][x+1]|(arr[y][x]&arr[y][x+3])|(arr[y][x]&arr[y][x+2]))?w:b);
                img.setRGB(x+2,y,(arr[y][x+2]|(arr[y][x+1]&arr[y][x+3]))?w:b);
                //img.setRGB(x+3,y,(arr[y][x])?w:b);
            }
        }
         return img;
    }
}
