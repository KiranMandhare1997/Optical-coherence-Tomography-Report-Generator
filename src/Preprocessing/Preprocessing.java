package Preprocessing;

import conceptualoct.CommonDataControlClass;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Kiran Mandhare
 */
class Point
{
   int x,y;
   Point(int x,int y)
   {
       this.x=x;
       this.y=y;
   }
   int getX()
   {
       return x;
   }
   int getY()
   {
       return y;   
   }
};
public class Preprocessing 
{
   public BufferedImage dotRemovalAlgorithm(BufferedImage img)
   {
       for(int y=0;y<img.getHeight();y++)
       {
           for(int x=0;x<img.getWidth();x++)
           {
               if((img.getRGB(x, y)&0xff)==255)
               {
                   int val=0;
                   int count=8;
                   try{ val+= img.getRGB(x-1, y-1)&0xff; }catch(ArrayIndexOutOfBoundsException  e){count--;}
                   try{ val+= img.getRGB(x  , y-1)&0xff; }catch(ArrayIndexOutOfBoundsException  e){count--;}
                   try{ val+= img.getRGB(x+1, y-1)&0xff; }catch(ArrayIndexOutOfBoundsException  e){count--;}
                   try{ val+= img.getRGB(x-1, y  )&0xff; }catch(ArrayIndexOutOfBoundsException  e){count--;}
                   try{ val+= img.getRGB(x+1, y  )&0xff; }catch(ArrayIndexOutOfBoundsException  e){count--;}
                   try{ val+= img.getRGB(x-1, y+1)&0xff; }catch(ArrayIndexOutOfBoundsException  e){count--;}
                   try{ val+= img.getRGB(x  , y+1)&0xff; }catch(ArrayIndexOutOfBoundsException  e){count--;}
                   try{ val+= img.getRGB(x+1, y+1)&0xff; }catch(ArrayIndexOutOfBoundsException  e){count--;}
                   
            
                   int percent=(int)((val*100)/(255*count));
                   //System.out.print(percent+"%");
                   if(percent<50)
                       img.setRGB(x, y,Color.BLACK.getRGB());
               }
           }
           //System.out.println();
       }
       return img;
   }
   public BufferedImage fillColor(BufferedImage img)
   {
       Stack<Point> s=new Stack<Point>();
       s.push(new Point(1,1));
       while(!s.isEmpty())
       {
           Point curr=s.pop();
           int x=curr.x;
           int y=curr.y;
           int val;
           try
           {
                val=img.getRGB(x,y)&0xff;
           }
           catch(ArrayIndexOutOfBoundsException e)
           {
               continue;
           }
           if(val!= 255)
           {
               img.setRGB(x, y,Color.WHITE.getRGB());
               s.push(new Point(x-1,y-1));
               s.push(new Point(x  ,y-1));
               s.push(new Point(x  ,y+1));
               s.push(new Point(x-1,y));
               s.push(new Point(x+1,y));
               s.push(new Point(x-1,y+1));
               s.push(new Point(x  ,y+1));
               s.push(new Point(x+1,y+1));
           }
       }
       s.push(new Point(0,img.getHeight()-1)); 
       while(!s.isEmpty())
       {
           Point curr=s.pop();
           int x=curr.x;
           int y=curr.y;
           int val;
           try
           {
                val=img.getRGB(x,y)&0xff;
           }
           catch(ArrayIndexOutOfBoundsException e)
           {
               continue;
           }
           if(val!= 255)
           {
               img.setRGB(x, y,Color.WHITE.getRGB());
               s.push(new Point(x-1,y-1));
               s.push(new Point(x  ,y-1));
               s.push(new Point(x  ,y+1));
               s.push(new Point(x-1,y));
               s.push(new Point(x+1,y));
               s.push(new Point(x-1,y+1));
               s.push(new Point(x  ,y+1));
               s.push(new Point(x+1,y+1));
           }
       }
       return img;
   }
//    void boundaryFill8(int x, int y, int fill_color, int boundary_color) 
//    {
//        if (getpixel(x, y) != boundary_color    &&      getpixel(x, y) != fill_color) 
//        {
//            putpixel(x, y, fill_color);
//            boundaryFill8(x + 1, y, fill_color, boundary_color);
//            boundaryFill8(x, y + 1, fill_color, boundary_color);
//            boundaryFill8(x - 1, y, fill_color, boundary_color);
//            boundaryFill8(x, y - 1, fill_color, boundary_color);
//            boundaryFill8(x - 1, y - 1, fill_color, boundary_color);
//            boundaryFill8(x - 1, y + 1, fill_color, boundary_color);
//            boundaryFill8(x + 1, y - 1, fill_color, boundary_color);
//            boundaryFill8(x + 1, y + 1, fill_color, boundary_color);
//        }
//    }
   public BufferedImage orignalimageToGrayscaledimage(BufferedImage orignalImage) 
   {
       BufferedImage grayscaledImage=new BufferedImage(orignalImage.getWidth(),orignalImage.getHeight(),BufferedImage.TYPE_INT_RGB);
        
       for (int y = 0; y <CommonDataControlClass.height; y++) 
       {
           for (int x = 0; x <CommonDataControlClass.width; x++) 
           {
                int pixel=orignalImage.getRGB(x,y);
                int alpha = (pixel >> 24) & 0xff;
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;
                
                int grayscalePixel=0;
                if(blue-red>20 && blue-green>=0)
                    grayscalePixel=0;
                else //if(green-blue>10)
                    grayscalePixel=255;
//                else
//                    grayscalePixel=(int)(red * 0.6 + green * 0.4 + blue * 0);  
                
                
                Color c=new Color(grayscalePixel,grayscalePixel,grayscalePixel);
                grayscaledImage.setRGB(x,y,c.getRGB());
            }
        }
        return grayscaledImage;
   } 
   public BufferedImage generateClassifiedImage(BufferedImage filteredImage)
   {
       int bSize=20;
        ArrayList<Point> buckets[]=new ArrayList[bSize];
        
        for(int i=0;i<bSize;i++)
            buckets[i]=new ArrayList<Point>();
        
        int prevMean[]={0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0};
        int newMean []={0, 12, 24, 36, 48, 60, 72, 84, 96,108,120,132,144,156,168,180,192,204,216,228};
        int [][]bucketInfo=new int[filteredImage.getHeight()][filteredImage.getWidth()];
        
        boolean change=true;
        
        while(change)
        {
            for(int y=0;y<filteredImage.getHeight();y++)
            {    
                for(int x=0;x<filteredImage.getWidth();x++)
                {
                    int pixel=filteredImage.getRGB(x, y) & 0xff;
                    int selected=0;
                    for(int i=1;i<bSize;i++)
                    {
                       if(Math.abs(pixel-newMean[selected])>Math.abs(pixel-newMean[i]))
                          selected=i;   
                    }
                    bucketInfo[y][x]=selected;
                    buckets[selected].add(new Point(x,y));
                }
            }
//            for(int i=0;i<bSize;i++)
//              System.out.println(newMean[i]+"  "+buckets[i].size());
            
            for (int i = 0; i < bSize; i++) 
            {
                int sum = 0;
                for (Point p : buckets[i]) 
                {
                    sum += filteredImage.getRGB(p.getX(), p.getY()) & 0xff;
                }
                prevMean[i] = newMean[i];
                if(buckets[i].isEmpty())
                    newMean[i]=0;
                else
                    newMean[i] = sum/(buckets[i].size());
            }
                
            //check change
            for (int i = 0; i < bSize; i++) 
            {
                if (prevMean[i] != newMean[i]) 
                {    
                    change = true;
                    break;
                } 
                else 
                {
                    change = false;
                }
            }
            for(int i=0;i<bSize;i++)
                buckets[i].clear();
        }
            
          
        //calculate distance from each point
        //put in bucket of minimum distance  
        Color c[]=new Color[100];
        c[0]=new Color(12,12,12);
        c[1]=new Color(24,24,24);
        c[2]=new Color(36,36,36);
        c[3]=new Color(48,48,48);
        c[4]=new Color(60,60,60);
        c[5]=new Color(72,72,72);
        c[6]=new Color(84,84,84);
        c[7]=new Color(96,96,96);
        c[8]=new Color(108,108,108);
        c[9]=new Color(120,120,120);
        c[10]=new Color(136,136,136);
        c[11]=new Color(148,148,148);
        c[12]=new Color(160,160,160);
        c[13]=new Color(172,172,172);
        c[14]=new Color(184,184,184);
        c[15]=new Color(196,196,196);
        c[16]=new Color(208,208,208);
        c[17]=new Color(220,220,220);
        c[18]=new Color(232,232,232);
        c[19]=new Color(255,255,255);
        BufferedImage temp=new BufferedImage(filteredImage.getWidth(),filteredImage.getHeight(),BufferedImage.TYPE_INT_RGB);

        for(int y=0;y<filteredImage.getHeight();y++)
        {
            for(int x=0;x<filteredImage.getWidth();x++)
            {
                temp.setRGB(x,y,c[bucketInfo[y][x]].getRGB());
            }
        }
        return temp;
   }
   public BufferedImage findYDerivativeOfImage(BufferedImage img)
   {
       int kernel[][]={
                        {-1,-2,-1},
                        {0 ,0, 0},
                        {1 ,2, 1}
                        };
       
       //BufferedImage temp=new BufferedImage(img.getWidth()+1,img.getHeight()+1,BufferedImage.TYPE_INT_RGB);
       int temp[][]=new int[img.getHeight()+2][img.getWidth()+2]; 

       for(int y=1;y<temp.length-1;y++)
       {
           for(int x=1;x<temp[0].length-1;x++)
           {
               temp[y][x]=img.getRGB(x-1,y-1)&0xff;
           }
       }
       
       int old_min = 1020;
       int old_max = -1020;
       int new_min = 0;
       int new_max = 255;
       
       int imgArr[][]=new int[img.getHeight()][img.getWidth()];
       BufferedImage tempFinal=new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_RGB);
       for(int y=1;y<img.getHeight()-1;y++)
       {
           for(int x=1;x<img.getWidth()-1;x++)
           {
               int val= temp[y-1][x-1] * kernel[0][0]
                        +temp[y-1][x]* kernel[0][1]
                       + temp[y-1][x+1]* kernel[0][2]
                       + temp[y][x-1] * kernel[1][0]
                       + temp[y][x]   * kernel[1][1]
                       +temp[y][x+1] * kernel[1][2]
                       +temp[y+1][x-1] *kernel[2][0]
                       +temp[y+1][x]  *kernel[2][1]
                       +temp[y+1][x+1]*kernel[2][2];
                       
               imgArr[y][x]=val;
               
               if(val<old_min)
                   old_min=val;
               else if(val>old_max)
                   old_max=val;
               
               //val=val/9;
              // System.out.println(val);
//               if(val<0)
//                   tempFinal.setRGB(x-1,y-1,Color.MAGENTA.getRGB());
//               else if(val>0)
//                   tempFinal.setRGB(x-1, y-1,Color.CYAN.getRGB());
//               else
//                   tempFinal.setRGB(x-1,y-1,Color.GREEN.getRGB());
              // System.out.print(val+" --> ");
               
              
            
              
               //System.out.print(val);
               //System.out.println();
//               try
//               {
//                    Color c=new Color(val,val,val);
//                    tempFinal.setRGB(x-1,y-1,c.getRGB());
//               }catch(Exception e)
//               {
//                   System.out.println(val);
//                   System.exit(1);
//               }
//                val=Math.abs(val);
//                Color c;
//                if(val<0)
//                    val=0;
//                else if(val>255)
//                    val=255;
                    //c=new Color(255,255,255);
               
               
//                  Color c1=new Color(0,0,0);
//                  if(val<128 || val>163)
//                      tempFinal.setRGB(x-1,y-1,c1.getRGB());
//                Color c2=new Color(0,0,0);
//                Color c3=new Color(0,255,0);
//                Color c4=new Color(255,255,255);
//                if(val>=0 && val<=40)
//                    tempFinal.setRGB(x-1,y-1,c1.getRGB());
//                else if(val>=41 && val<=120)
//                    tempFinal.setRGB(x-1,y-1,c2.getRGB());
//                else if(val>=121 && val<=130)
//                    tempFinal.setRGB(x-1,y-1,c3.getRGB());
//                else if(val>130)
//                    tempFinal.setRGB(x-1,y-1,c4.getRGB());
           }
           
           
           
           
           //System.out.println("bunjbuubkjb");
       }
       System.out.println("Old _min : "+old_min);
       System.out.println("Old _max : "+old_max);
       int oldRange=old_max-old_min;
       int newRange=new_max-new_min;
       for(int y=0;y<img.getHeight();y++)
       {
           for(int x=0;x<img.getWidth();x++)
           {
               int val=(((imgArr[y][x]-old_min)*(newRange))/oldRange)+new_min;
               Color c=new Color(val,val,val);
               tempFinal.setRGB(x,y,c.getRGB());
           }
       }
       return tempFinal;
//       for(int x=0;x<img.getWidth();x++)
//       {
//           temp.setRGB(x,0,0);
//           for(int y=1;y<img.getHeight();y++)
//           {
//               int val=Math.abs(img.getRGB(x,y)-img.getRGB(x, y-1));
//
//               if(val==0)
//                   temp.setRGB(x, y,Color.BLACK.getRGB());
//               else if(val==12)
//                   temp.setRGB(x, y,Color.WHITE.getRGB());
//           }
//       }
//       return temp;
   }
   public BufferedImage histogramEqualization(BufferedImage img)
   {
        int pixelToNewPixel[]=new int[256];
        float intensityCount[]=new float[256];    
        float CDF[]=new float[256];
        
        for(int y=0;y<img.getHeight();y++)
        {
            for(int x=0;x<img.getWidth();x++)
            {
                intensityCount[img.getRGB(x, y) & 0xff]+=1;
            }
        }
        float cdfInitializer=0;
        for(int i=0;i<256;i++)
        {
            cdfInitializer=intensityCount[i]+cdfInitializer;//(img.getWidth()*img.getHeight()));
            CDF[i]=cdfInitializer;
            pixelToNewPixel[i]=(int)Math.floor((255*(CDF[i]/(img.getWidth()*img.getHeight()))));
        }
        
        for(int i=0;i<256;i++)
            System.out.println(i+" *->* "+pixelToNewPixel[i]);
        
        BufferedImage tempFinal=new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_RGB);
        for(int y=0;y<img.getHeight();y++)
        {
            for(int x=0;x<img.getWidth();x++)
            {
                int val=pixelToNewPixel[img.getRGB(x, y)&0xff];
                Color c=new Color(val,val,val);
                tempFinal.setRGB(x, y,c.getRGB());
            }
        }
        return tempFinal;
    }
  }
 