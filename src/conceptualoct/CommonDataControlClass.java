package conceptualoct;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.opencv.core.Core;

public class CommonDataControlClass 
{
    
    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
   public static int threshold;
   public static int width;
   public static int height;
   public static BufferedImage orignalImage;
   public static BufferedImage grayscaledImage;
   public static BufferedImage filteredImage;
   public static BufferedImage knnClassifiedImage;
   public static BufferedImage segmentedImage; //temporary (later changed to segments[])
   public static BufferedImage cannyDetectedImage;
   
   public static void readImage(String imageFileName) throws IOException
   {
        orignalImage=ImageIO.read(new File(imageFileName)); 
        width=orignalImage.getWidth();
        height=orignalImage.getHeight();
   }
   public static void outputImage(BufferedImage image) throws IOException
   {
         ImageIO.write(image,"bmp",new File("F:\\Project\\images\\new___output.bmp"));
   }
   public static BufferedImage clone(BufferedImage orignalImage)
   {
       BufferedImage clonedImage=new BufferedImage(orignalImage.getWidth(),orignalImage.getHeight(),BufferedImage.TYPE_INT_RGB);
       for (int y = 0; y <CommonDataControlClass.height; y++) 
       {
           for (int x = 0; x <CommonDataControlClass.width; x++) 
                 clonedImage.setRGB(x,y,orignalImage.getRGB(x,y));
       }
       return clonedImage;
   }  
}
