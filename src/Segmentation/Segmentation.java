/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Segmentation;

import conceptualoct.CommonDataControlClass;
import java.awt.Color;
import java.awt.image.BufferedImage;
public class Segmentation 
{
    public BufferedImage grayscaledImageToSegments(BufferedImage grayscaledImage,int dummyT1,int dummyT2,int dummyT3)
    {
        BufferedImage segmentedImage=CommonDataControlClass.clone(grayscaledImage);
        
        Color color = new Color(255,0, 0); //REd
        int seg=1;
        
        for (int y = 1; y < CommonDataControlClass.height - 1; y++) 
        {
            int prev = ((grayscaledImage.getRGB(0, y - 1) >> 16) & (0xff));
            int curr = ((grayscaledImage.getRGB(0, y    ) >> 16) & (0xff));
            
            //int next = ((grayscaledImage.getRGB(0, y + 1) >> 16) & (0xff));            
            //System.out.println(prev+"  "+curr);
            // if(prev>125 && curr>125)
            
            if(curr>125 && Math.abs(prev-curr)>20)
            {
                //System.out.print("******************************");
                segmentedImage.setRGB(0,y,color.getRGB());
                int colIterator=1;
                int rowIterator=y;
                
                boolean selectedUp=false,selectedDown=false;
                
                // int selectedNextPixel;                
                //Queue q=new Queue();
                try
                {
                    //System.out.println("Segment "+(seg++)+" **************************************************************");
                    
                    while(rowIterator>0 && colIterator>0 && rowIterator<CommonDataControlClass.height-1 && colIterator<CommonDataControlClass.width-1)
                    {   
                        //q.addPixelToQueue(new Pixel(colIterator,rowIterator));
                        
                        grayscaledImage.setRGB(colIterator, rowIterator,0x000);
                        
                        int p1 = ((grayscaledImage.getRGB(colIterator + 1, rowIterator - 1) >> 16) & (0xff));
                        int p2 = ((grayscaledImage.getRGB(colIterator + 1, rowIterator) >> 16) & (0xff));
                        int p3 = ((grayscaledImage.getRGB(colIterator + 1, rowIterator + 1) >> 16) & (0xff));
                        
                        int disTopRight =    Math.abs(p1-curr);
                        int disNextAdj  =    Math.abs(p2-curr);
                        int disDownRight=    Math.abs(p3-curr);    
                      
                        
                        if(disTopRight>disDownRight)
                        {
                            if(disTopRight>disNextAdj)
                            {
                                colIterator++;
                                rowIterator--;
                            }
                            else
                            {
                                colIterator++;
                            }
                            
                        }
                        else
                        {
                            if(disDownRight>disNextAdj)
                            {
                                colIterator++;
                                rowIterator++;
                            }
                            else
                            {
                                colIterator++;
                            }
                            
                        }
           
                        segmentedImage.setRGB(colIterator,rowIterator,color.getRGB());
                        curr = (grayscaledImage.getRGB(colIterator,rowIterator) >> 16) & (0xff);
                      
                }
            }
            catch(Exception e){}
            }
            
        }
        return segmentedImage;
    }
    public BufferedImage grayscaledImageToSegments(BufferedImage grayscaledImage,int z)
    {
        int arr[]=new int[256];
        Color c[]=new Color[126];
        c[0]=new Color(0,0,0);
        for(int i=1;i<c.length;i++)
        {
            int k=i+124;
            c[i]=new Color(k,k,k);
        }
        
        BufferedImage segmentedImage=CommonDataControlClass.clone(grayscaledImage);
        for(int y=0;y<CommonDataControlClass.height;y++)
        {
            for(int x=0;x<CommonDataControlClass.width;x++)
            {
               
                
                int pixel=(grayscaledImage.getRGB(x,y) >> 16) & (0xff);
                
                arr[pixel]+=1;
                if(pixel<125)
                    segmentedImage.setRGB(x, y,c[0].getRGB());
                else if(pixel>=125 && pixel<=135)
                   segmentedImage.setRGB(x, y,c[1].getRGB());
                else if(pixel>=136 && pixel<=145)
                    segmentedImage.setRGB(x, y, c[11].getRGB());
                else if(pixel>=146 && pixel<=155)
                   segmentedImage.setRGB(x, y,c[21].getRGB());     
                else if(pixel>=156 && pixel<=165)
                   segmentedImage.setRGB(x, y,c[31].getRGB());         
                else if(pixel>=166 && pixel<=175)
                   segmentedImage.setRGB(x, y,c[41].getRGB());             
                else if(pixel>=176 && pixel<=185)
                   segmentedImage.setRGB(x, y,c[51].getRGB());  
                else if(pixel>=186 && pixel<=195)
                   segmentedImage.setRGB(x, y,c[61].getRGB()); 
                else if(pixel>=196 && pixel<=205)
                    segmentedImage.setRGB(x, y,c[71].getRGB()); 
                else if(pixel>=206 && pixel<=215)
                    segmentedImage.setRGB(x, y,c[81].getRGB()); 
                else if(pixel>=216 && pixel<=225)
                    segmentedImage.setRGB(x, y,c[91].getRGB()); 
                else if(pixel>=226 && pixel<=235)
                    segmentedImage.setRGB(x, y,c[101].getRGB()); 
                else if(pixel>=236 && pixel<=245)
                    segmentedImage.setRGB(x, y,c[111].getRGB()); 
                else if(pixel>=246 && pixel<=255)
                    segmentedImage.setRGB(x, y,c[121].getRGB()); 
                   
            }
        }
//        for(int i=0;i<=255;i++)
//            System.out.println(i+"  "+arr[i]);
        return segmentedImage;
    }
    public BufferedImage grayscaledImageToSegments(BufferedImage grayscaledImage)
    {
        BufferedImage segmentedImage=CommonDataControlClass.clone(grayscaledImage);
        
        Color color = new Color(255, 0, 0); //REd
        int seg=1;
        for (int y = 1; y < CommonDataControlClass.height - 1; y++) 
        {
          
            int prev = ((grayscaledImage.getRGB(0, y - 1) >> 16) & (0xff));
            int curr = ((grayscaledImage.getRGB(0, y    ) >> 16) & (0xff));
            //int next = ((grayscaledImage.getRGB(0, y + 1) >> 16) & (0xff));
            
           // System.out.println(prev+"  "+curr);
           // if(prev>125 && curr>125)
            if(Math.abs(prev-curr)==12) 
            {
                //System.out.print("******************************");
                segmentedImage.setRGB(0,y,color.getRGB());
                int colIterator=1;
                int rowIterator=y;
                
                boolean selectedUp=false,selectedDown=false;
                
               // int selectedNextPixel;                
                //Queue q=new Queue();
                try
                {
                    //System.out.println("Segment "+(seg++)+" **************************************************************");
                    
                    while(rowIterator>0 && colIterator>0 && rowIterator<CommonDataControlClass.height-1 && colIterator<CommonDataControlClass.width-1)
                    {   
                        //q.addPixelToQueue(new Pixel(colIterator,rowIterator));
                        
                        grayscaledImage.setRGB(colIterator, rowIterator,0x000000000000000000000000000000000);
                        int p1 = ((grayscaledImage.getRGB(colIterator + 1, rowIterator - 1) >> 16) & (0xff));
                        
                        int p2 = ((grayscaledImage.getRGB(colIterator + 1, rowIterator) >> 16) & (0xff));
                        int p3 = ((grayscaledImage.getRGB(colIterator + 1, rowIterator + 1) >> 16) & (0xff));

                        int p5=2000,p4=2000;
                        
                        if(!selectedDown)
                            p4 = ((grayscaledImage.getRGB(colIterator, rowIterator - 1) >> 16) & (0xff));
                        
                        if(!selectedUp)
                            p5=((grayscaledImage.getRGB(colIterator,rowIterator + 1) >> 16) & (0xff));
                        
                        int disTopRight =    Math.abs(p1-curr);
                        int disNextAdj  =    Math.abs(p2-curr);
                        int disDownRight=    Math.abs(p3-curr);    
                        int disTop      =    Math.abs(p4-curr);
                        int disDown     =    Math.abs(p5-curr);
                        
                        //System.out.println(disTop+"  "+disTopRight+"   "+disNextAdj+"   "+disDownRight+"   "+disDown);
                        //System.out.println(rowIterator+"  "+colIterator);
                        int arr[]={disTop,disTopRight,disNextAdj,disDownRight,disDown};
                        int sel=0;//secondSel;
                       
                        for(int i=1;i<5;i++)
                        {
                            if(arr[sel]>arr[i])
                            {
                                //secondSel=sel;
                                sel=i;
                            }
                        }
    
                        if(sel==0)
                        {
                            selectedUp=true;
                            rowIterator--;
                        }
                        else if(sel==1)
                        {
                            selectedUp=selectedDown=false;
                            colIterator++;
                            rowIterator--;
                        }
                        else if(sel==2){
                            selectedUp=selectedDown=false;
                            colIterator++;
                        }
                        else if(sel==3)
                        {
                            selectedUp=selectedDown=false;
                            colIterator++;
                            rowIterator++;
                        }
                        else if(sel==4)
                        {
                            selectedDown=true;
                            rowIterator++;
                        }
                        segmentedImage.setRGB(colIterator,rowIterator,color.getRGB());
                        curr = (grayscaledImage.getRGB(colIterator,rowIterator) >> 16) & (0xff);
                      
                }
            }
            catch(Exception e){}
            }
            
        }
        return segmentedImage;
    }
}

