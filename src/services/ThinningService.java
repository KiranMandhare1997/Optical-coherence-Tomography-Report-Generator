package services;

//import com.sun.prism.paint.Color;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
 
/**
 * Created by nayef on 1/26/15.
 */
public class ThinningService {

    public BufferedImage ThinningAlgorithm(BufferedImage img)
    {
        int givenImage[][]=new int[img.getHeight()][img.getWidth()];
        for(int y=0;y<img.getHeight();y++)
            {
                for(int x=0;x<img.getWidth();x++)
                {
                    givenImage[y][x]=( ((img.getRGB(x, y)& 0xff)==255)?1:0);
                }
            }
//        for(int i=0;i<givenImage.length;i++)
//        {
//            for(int j=0;j<givenImage[0].length;j++)
//            {
//                int pixel = givenImage[i][j];
//                System.out.print(pixel);
//            }
//            System.out.println();
//        }
        int output[][]=this.doZhangSuenThinning(givenImage,true);
//         System.out.println("Output");
//                 
//        for(int i=0;i<output.length;i++)
//        {
//            for(int j=0;j<output[0].length;j++)
//            {
//                int pixel = output[i][j];
//                System.out.print(pixel);
//            }
//            System.out.println();
//        }
        
        BufferedImage op=new BufferedImage(output[1].length,output.length,BufferedImage.TYPE_BYTE_BINARY);
         
        
        Color black=new Color(0,0,0);
        Color white=new Color(255,255,255);
        int w=white.getRGB();
        int b=black.getRGB();
        for(int y=0;y<img.getHeight();y++)
            {
                for(int x=0;x<img.getWidth();x++)
                {
                    int pixel=(givenImage[y][x]==1)?w:b;
                    op.setRGB(x,y,pixel);
                }
            }
         return op;
    }
    int[][] doZhangSuenThinning(final int[][] givenImage, boolean changeGivenImage) {
        int[][] binaryImage;
        if (changeGivenImage) {
            binaryImage = givenImage;
        } else {
            binaryImage = givenImage.clone();
        }
        int a, b;
        List<Point> pointsToChange = new LinkedList();
        boolean hasChange;
        do {
            hasChange = false;
            for (int y = 1; y + 1 < binaryImage.length; y++) {
                for (int x = 1; x + 1 < binaryImage[y].length; x++) {
                    a = getA(binaryImage, y, x);
                    b = getB(binaryImage, y, x);
                    if (binaryImage[y][x] == 1 && 2 <= b && b <= 6 && a == 1
                            && (binaryImage[y - 1][x] * binaryImage[y][x + 1] * binaryImage[y + 1][x] == 0)
                            && (binaryImage[y][x + 1] * binaryImage[y + 1][x] * binaryImage[y][x - 1] == 0)) {
                        pointsToChange.add(new Point(x, y));
//binaryImage[y][x] = 0;
                        hasChange = true;
                    }
                }
            }
            for (Point point : pointsToChange) {
                binaryImage[point.getY()][point.getX()] = 0;
            }
            pointsToChange.clear();
            for (int y = 1; y + 1 < binaryImage.length; y++) {
                for (int x = 1; x + 1 < binaryImage[y].length; x++) {
                    a = getA(binaryImage, y, x);
                    b = getB(binaryImage, y, x);
                    if (binaryImage[y][x] == 1 && 2 <= b && b <= 6 && a == 1
                            && (binaryImage[y - 1][x] * binaryImage[y][x + 1] * binaryImage[y][x - 1] == 0)
                            && (binaryImage[y - 1][x] * binaryImage[y + 1][x] * binaryImage[y][x - 1] == 0)) {
                        pointsToChange.add(new Point(x, y));
                        hasChange = true;
                    }
                }
            }
            for (Point point : pointsToChange) {
                binaryImage[point.getY()][point.getX()] = 0;
            }
            pointsToChange.clear();
        } while (hasChange);
        return binaryImage;
    }
 
    private int getA(int[][] binaryImage, int y, int x) {
        int count = 0;
//p2 p3
        if (y - 1 >= 0 && x + 1 < binaryImage[y].length && binaryImage[y - 1][x] == 0 && binaryImage[y - 1][x + 1] == 1) {
            count++;
        }
//p3 p4
        if (y - 1 >= 0 && x + 1 < binaryImage[y].length && binaryImage[y - 1][x + 1] == 0 && binaryImage[y][x + 1] == 1) {
            count++;
        }
//p4 p5
        if (y + 1 < binaryImage.length && x + 1 < binaryImage[y].length && binaryImage[y][x + 1] == 0 && binaryImage[y + 1][x + 1] == 1) {
            count++;
        }
//p5 p6
        if (y + 1 < binaryImage.length && x + 1 < binaryImage[y].length && binaryImage[y + 1][x + 1] == 0 && binaryImage[y + 1][x] == 1) {
            count++;
        }
//p6 p7
        if (y + 1 < binaryImage.length && x - 1 >= 0 && binaryImage[y + 1][x] == 0 && binaryImage[y + 1][x - 1] == 1) {
            count++;
        }
//p7 p8
        if (y + 1 < binaryImage.length && x - 1 >= 0 && binaryImage[y + 1][x - 1] == 0 && binaryImage[y][x - 1] == 1) {
            count++;
        }
//p8 p9
        if (y - 1 >= 0 && x - 1 >= 0 && binaryImage[y][x - 1] == 0 && binaryImage[y - 1][x - 1] == 1) {
            count++;
        }
//p9 p2
        if (y - 1 >= 0 && x - 1 >= 0 && binaryImage[y - 1][x - 1] == 0 && binaryImage[y - 1][x] == 1) {
            count++;
        }
        return count;
    }
 
    private int getB(int[][] binaryImage, int y, int x) {
        return binaryImage[y - 1][x] + binaryImage[y - 1][x + 1] + binaryImage[y][x + 1]
                + binaryImage[y + 1][x + 1] + binaryImage[y + 1][x] + binaryImage[y + 1][x - 1]
                + binaryImage[y][x - 1] + binaryImage[y - 1][x - 1];
    }
}
 
class Point {
    private int x;
    private int y;
 
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
 
    public int getX() {
        return x;
    }
 
    public void setX(int x) {
        this.x = x;
    }
 
    public int getY() {
        return y;
    }
 
    public void setY(int y) {
        this.y = y;
    }
}