/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conceptualoct;

import Preprocessing.BilateralFilter;
import Preprocessing.GrayImage;
import Preprocessing.Preprocessing;
import Segmentation.Segmentation;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
//import jcanny.JCanny;
//import services.HorizontalThickening;
//import services.ThinningService;
public class ConceptualOCT 
{
    private static final double CANNY_THRESHOLD_RATIO = 0.3; //Suggested range .2 - .4
    private static final int CANNY_STD_DEV = 1;             //Range 1-3
       //temporary variable
    public static void main(String[] args) throws IOException
    {     
        String inputPath=".\\images\\fluid\\";
        String outputPath=".\\images\\fluid\\derivedOuptput\\";
        String format="BMP";
        for(int img=1;img<8;img++)
        {
         try
            {
                CommonDataControlClass.readImage(inputPath+img+"."+format);
            } 
        catch(IOException ex) 
            {
                Logger.getLogger(ConceptualOCT.class.getName()).log(Level.SEVERE, null, ex);
                System.exit(1);
            }
        
        ImageIO.write(CommonDataControlClass.orignalImage,format,new File(outputPath+img+"_orignalImage."+format));
        Preprocessing preprocessedImage = new Preprocessing();
        Segmentation segmentedImage = new Segmentation();
        
         /********************* Filtered Image *************************************/
         GrayImage img_g=new GrayImage(CommonDataControlClass.orignalImage);
         BilateralFilter filterObj=new BilateralFilter(10,4);
         GrayImage filteredImage=filterObj.bruteForce(img_g);
        
         CommonDataControlClass.filteredImage=/*CommonDataControlClass.grayscaledImage*/filteredImage.getBufferedImage();
         ImageIO.write(CommonDataControlClass.filteredImage,format,new File(outputPath+img+"_filteredOutput."+format));  
         System.out.println("Image"+img+"Filtered Image writen"); 
        
        
        /********************* Grayscaling *****************************************/
        CommonDataControlClass.grayscaledImage = preprocessedImage.orignalimageToGrayscaledimage(CommonDataControlClass.filteredImage);
        ImageIO.write(CommonDataControlClass.grayscaledImage,format,new File(outputPath+img+"_grayscaledOutput."+format));
        System.out.println("GrayScaled Image writen");
        
        
        
        /********************* Noise removed Image *************************************/
        CommonDataControlClass.grayscaledImage=preprocessedImage.dotRemovalAlgorithm(CommonDataControlClass.grayscaledImage);
        ImageIO.write(CommonDataControlClass.grayscaledImage,format,new File(outputPath+img+"_non_dottedOutput."+format));
        System.out.println("Dot Removed and Image writen");
        
        
        
        /********************* Coloring Image *************************************/
        
        CommonDataControlClass.grayscaledImage=preprocessedImage.fillColor(CommonDataControlClass.grayscaledImage);
        ImageIO.write(CommonDataControlClass.grayscaledImage,format,new File(outputPath+img+"_coloredOutput."+format));
        System.out.println("coloured Image writen");
        
        int hor[]=new int[ CommonDataControlClass.grayscaledImage.getWidth()];
        for(int y=0; y< CommonDataControlClass.grayscaledImage.getHeight();y++)
        {
            for(int x=0;x<CommonDataControlClass.grayscaledImage.getWidth();x++)
            {
                hor[x] += ((CommonDataControlClass.grayscaledImage.getRGB(x, y) & 0xff)==0)?1:0;
            }
        }
        
        for(int i=0;i< CommonDataControlClass.grayscaledImage.getWidth();i++)
        {
            
            System.out.print("_"+hor[i]);
            
            
        }
        BufferedImage barChart=new BufferedImage(CommonDataControlClass.grayscaledImage.getWidth(),CommonDataControlClass.grayscaledImage.getHeight(),BufferedImage.TYPE_INT_RGB);
        for(int x=0;x<barChart.getWidth();x++)
        {
            for(int y=barChart.getHeight()-1;y>=barChart.getHeight()-hor[x];y--)
            {
                barChart.setRGB(x, y,Color.BLACK.getRGB());
            }
            for(int y=barChart.getHeight()-hor[x]-1;y>=0;y--)
            {
                barChart.setRGB(x, y, Color.WHITE.getRGB());
            }
        }
        ImageIO.write(barChart,format,new File(outputPath+img+"_graphColor."+format));
        
//        /********************* Filtered Image *************************************/
//        GrayImage img_g=new GrayImage(CommonDataControlClass.grayscaledImage);
//        BilateralFilter filterObj=new BilateralFilter(10,4);
//        GrayImage filteredImage=filterObj.bruteForce(img_g);
//        
//        CommonDataControlClass.filteredImage=/*CommonDataControlClass.grayscaledImage*/filteredImage.getBufferedImage();
//        ImageIO.write(CommonDataControlClass.filteredImage,format,new File(outputPath+img+"_filteredOutput."+format));  
//        System.out.println("Filtered Image writen");    
//        
//        
//        
//        /********************* Classifying pixels to buckets **********************/
//        CommonDataControlClass.knnClassifiedImage=preprocessedImage.generateClassifiedImage(CommonDataControlClass.filteredImage);
//        ImageIO.write(CommonDataControlClass.knnClassifiedImage,format,new File(outputPath+img+"_KMeans."+format));
//        System.out.println("KNN Classified Image writen");
//        
//        BufferedImage temporal1=preprocessedImage.findYDerivativeOfImage(CommonDataControlClass.knnClassifiedImage);
//        ImageIO.write(temporal1,format,new File(outputPath+img+"_derivedImage."+format));
//        temporal1=preprocessedImage.histogramEqualization(temporal1);
//        int pixels[]=new int[256];
//        
//        for(int y=0;y<temporal1.getHeight();y++)
//        {
//            for(int x=0;x<temporal1.getWidth();x++)
//            {
//                pixels[temporal1.getRGB(x, y) & 0xff]+=1;
//            }
//        }
//        System.out.println("Pixels.............");
//        for(int i=0;i<255;i++)
//        {
//            System.out.println(i+" -> "+pixels[i]);
//        }
//        
//        temporal1=preprocessedImage.histogramEqualization(temporal1);
//        ImageIO.write(temporal1,format,new File(outputPath+img+"_histogramEqualization."+format));
//        for(int y=0;y<temporal1.getHeight();y++)
//        {
//            for(int x=0;x<temporal1.getWidth();x++)
//            {
//                if((temporal1.getRGB(x, y)&0xff)>240)
//                    temporal1.setRGB(x, y,Color.WHITE.getRGB());
//                else
//                    temporal1.setRGB(x, y,Color.BLACK.getRGB());
//            }
//        }
//        //System.out.println("Temporal ------ >     "+preprocessedImage.calcThreshold(temporal1));
//        //temporal1=preprocessedImage.findYDerivativeOfImage(temporal1);
//        ImageIO.write(temporal1,format,new File(outputPath+img+"_histogramEqualizationz12345."+format));
//        System.out.println("Derived image");
//        //////////////////////////////////Temporaray area????????????????????????????
//        System.out.println("#######################################################");
//                
//        //int pixels[]=new int[256];
//        
//        for(int y=0;y<temporal1.getHeight();y++)
//        {
//            for(int x=0;x<temporal1.getWidth();x++)
//            {
//                pixels[temporal1.getRGB(x, y) & 0xff]+=1;
//            }
//        }
//        System.out.println("Pixels.............");
//        for(int i=0;i<255;i++)
//        {
//            System.out.println(i+" -> "+pixels[i]);
//        }
//        
//        
       //printTerminalImage(temporal1);
        
        ///////////////////////////????????????????????????????????????????????????????????????????
//        System.out.println("First leftmost column");
//         for (int y = 0; y < temp.getHeight(); y++) 
//        {  
//            
//            int pixel=temp.getRGB(0, y) &0xff;;
//             DecimalFormat formatter = new DecimalFormat("000");
//                String aFormatted = formatter.format(pixel);
//                System.out.println("" + aFormatted);
//        }
//        System.out.println();
//        BufferedImage img
//        for(int y=0;y<CommonDataControlClass.height;y++)
//            for(int x=0;x<CommonDataControlClass.width;x++)
//            {
//                
//            }
        
//        CommonDataControlClass.segmentedImage=segmentedImage.grayscaledImageToSegments(CommonDataControlClass.knnClassifiedImage,1,1,1);
//        ImageIO.write(CommonDataControlClass.segmentedImage,format,new File(outputPath+img+"_segmentedImage."+format));
//        System.out.println("Segmented Image writen");
//        
//        CommonDataControlClass.cannyDetectedImage = JCanny.CannyEdges(CommonDataControlClass.knnClassifiedImage, CANNY_STD_DEV, CANNY_THRESHOLD_RATIO);
//        ImageIO.write(CommonDataControlClass.cannyDetectedImage,format,new File(outputPath+img+"_cannyDetectedImage."+format));
//        System.out.println("Canny Detected Image writen");
//      output=preprocessedImage.grayToBinarizedImage(output);
//        for(int i=1;i<output.getHeight()-1;i++)
//        {
//            for(int j=1;j<output.getWidth()-1;j++)
//            {
//                int [][]kernel=
//            }
//        }
        
//        ThinningService t=new ThinningService();
//        HorizontalThickening ht=new HorizontalThickening();
//        CommonDataControlClass.cannyDetectedImage=ht.horizontalThickening(CommonDataControlClass.cannyDetectedImage);
//        
//        
//        ImageIO.write(CommonDataControlClass.cannyDetectedImage,format,new File(outputPath+img+"_thickeningImage."+format));
//        System.out.println("Thickened Image writen");
//        
//     CommonDataControlClass.cannyDetectedImage=t.ThinningAlgorithm(CommonDataControlClass.cannyDetectedImage);
//        
//        ImageIO.write(CommonDataControlClass.cannyDetectedImage,format,new File(outputPath+img+"_ThinningOutput."+format));
//        System.out.println("Thinning Image writen");
        
        System.out.println();
        System.out.println();
        System.out.println("******************************************************************");
        
       // printTerminalImage(CommonDataControlClass.segmentedImage);
//        try 
//        {
//            CommonDataControlClass.outputImage(CommonDataControlClass.cannyDetectedImage);
//        } catch (IOException ex) 
//        {
//            Logger.getLogger(ConceptualOCT.class.getName()).log(Level.SEVERE, null, ex);
//        }
        }   
    }
    static void printTerminalImage(BufferedImage grayscaledImage) 
    {
        for (int y = 0; y < grayscaledImage.getHeight(); y++) 
        {  
            
            for (int x = 0; x < grayscaledImage.getWidth(); x++) 
            {
                int pixel=grayscaledImage.getRGB(x, y) &0xff;
                DecimalFormat formatter = new DecimalFormat("000");
                String aFormatted = formatter.format(pixel);
                System.out.print("" + aFormatted);
            }
            System.out.println();
        }
    }
}
