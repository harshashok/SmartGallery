package com.harsha.windowbuilder;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import javax.media.jai.InterpolationNearest;
import javax.media.jai.JAI;
import javax.media.jai.iterator.RandomIter;
import javax.media.jai.iterator.RandomIterFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.sun.media.jai.widget.DisplayJAI;

public class SeedClustering {
	
	public static final int WIDTH = 352;
	public static final int HEIGHT = 288;
	public static final int baseSize = 300;
	
	int THRESHOLD = 2000;
	
	private Color[][] signature;
	
	public LinkedList<String> clusterList;
	
	static
	{
	  System.setProperty("com.sun.media.jai.disableMediaLib", "true");
	}

public SeedClustering(File reference, int threshold, int option) throws IOException {
		
		
	    RenderedImage ref = rescale((RenderedImage)loadImage(reference.toString()));
	  	THRESHOLD = threshold;
	  	
	  	if(THRESHOLD < 0 )
	  		THRESHOLD = 0;
	  	
	    signature = calcSignature(ref);
	    
	    File[] others = getOtherImageFiles(reference);
	    	   
	    RenderedImage[] rothers = new RenderedImage[others.length];
	    double[] distances = new double[others.length];
	    
	    for (int o = 0; o < others.length; o++)
	      {	      
	    	rothers[o] = rescale((RenderedImage)loadImage(others[o].toString()));
	    	distances[o] = calcDistance(rothers[o]);	    		    	
	      }
	    
	    
	    for (int p1 = 0; p1 < others.length - 1; p1++)
	        for (int p2 = p1 + 1; p2 < others.length; p2++)
	        {
	          if (distances[p1] > distances[p2])
	            {
	            double tempDist = distances[p1];
	            distances[p1] = distances[p2];
	            distances[p2] = tempDist;
	            RenderedImage tempR = rothers[p1];
	            rothers[p1] = rothers[p2];
	            rothers[p2] = tempR;
	            File tempF = others[p1];
	            others[p1] = others[p2];
	            others[p2] = tempF;
	            }
	        }
	    
	    
	    clusterList = new LinkedList<String>();
	    
	    for (int o = 0; o < others.length; o++)
	      {
	      
	    	
	    	
	    	if(distances[o] <= THRESHOLD)
	    	clusterList.add(others[o].toString());
	    		    	
	    	
	      	//otherPanel.add(ldist);
	      
	      }
	    
	    if(clusterList.size() > 20){
	    	clusterList = new LinkedList<String>(clusterList.subList(0, 20));
	    }
	    System.out.println("cluster first image: "+clusterList.getFirst());
	  
	    
	    System.out.println("SEEDCLUSTER threshold:"+THRESHOLD);
	    if (clusterList.size() == 1 || THRESHOLD <= 0){
	    	//TODO: Check if frame is a video file.
	    	final String filechk = clusterList.getFirst();
	    	
	    	
	    	
	    	
	    	if(PlayGround.map.containsKey(filechk)){
	    		System.out.println("VIDEO FOUND!!");
	    			
	    		Runnable r1 = new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						DisplayVideo dv = new DisplayVideo();
						dv.showVideo(PlayGround.map.get(filechk));
					}
				};
				Thread thr = new Thread(r1);
				thr.start();
	    		
	    		
	    	}else {
	    		SingleFrameViewer viewer = new SingleFrameViewer(clusterList.getFirst());
	    	}
	    	
	    	
	    }	    
	    else if(option == 1){
	    	CollageGallery gallery = new CollageGallery(clusterList, clusterList.size(), threshold - 500);
	    }
	    	
    	
	    
	}



private RenderedImage rescale(RenderedImage i)
{
    float scaleW = ((float) baseSize) / i.getWidth();
    float scaleH = ((float) baseSize) / i.getHeight();
  
    ParameterBlock pb = new ParameterBlock();
    pb.addSource(i);
    pb.add(scaleW);
    pb.add(scaleH);
    pb.add(0.0F);
    pb.add(0.0F);
    pb.add(new InterpolationNearest());
  
    return JAI.create("scale", pb);
}

private Color[][] calcSignature(RenderedImage i)
{
	
	Color[][] sig = new Color[5][5];

	float[] prop = new float[]		  
	  {1f / 10f, 3f / 10f, 5f / 10f, 7f / 10f, 9f / 10f};
	for (int x = 0; x < 5; x++)
	  for (int y = 0; y < 5; y++)
	    sig[x][y] = averageAround(i, prop[x], prop[y]);
	return sig;
}

private Color averageAround(RenderedImage i, double px, double py)
{
  
  RandomIter iterator = RandomIterFactory.create(i, null);
  
  double[] pixel = new double[3];
  double[] accum = new double[3];
  
  int sampleSize = 15;
  int numPixels = 0;
 
  for (double x = px * baseSize - sampleSize; x < px * baseSize + sampleSize; x++)
    {
    for (double y = py * baseSize - sampleSize; y < py * baseSize + sampleSize; y++)
      {
      iterator.getPixel((int) x, (int) y, pixel);
      accum[0] += pixel[0];
      accum[1] += pixel[1];
      accum[2] += pixel[2];
      numPixels++;
      }
    }
  
  accum[0] /= numPixels;
  accum[1] /= numPixels;
  accum[2] /= numPixels;
  return new Color((int) accum[0], (int) accum[1], (int) accum[2]);
}

private double calcDistance(RenderedImage other)
{
	  
	  Color[][] sigOther = calcSignature(other);
	  
	  double dist = 0;
	  for (int x = 0; x < 5; x++)
	    for (int y = 0; y < 5; y++)
	      {
	      int r1 = signature[x][y].getRed();
	      int g1 = signature[x][y].getGreen();
	      int b1 = signature[x][y].getBlue();
	      int r2 = sigOther[x][y].getRed();
	      int g2 = sigOther[x][y].getGreen();
	      int b2 = sigOther[x][y].getBlue();
	      double tempDist = Math.sqrt((r1 - r2) * (r1 - r2) + (g1 - g2)
	          * (g1 - g2) + (b1 - b2) * (b1 - b2));
	      dist += tempDist;
	      }
	  return dist;
}

public static BufferedImage loadImage(String filepath){
		
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		try {
			File file = new File(filepath);
			InputStream is = new FileInputStream(file);	    
			
			long len = file.length();
		    byte[] bytes = new byte[(int)len];
		    
		    
		    int offset = 0;
	        int numRead = 0;
	        while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	            offset += numRead;
	        }
	        
	        int ind = 0;	    		    	
	    	
	    	
	    	for(int y = 0; y < HEIGHT; y++){	    						
				for(int x = 0; x < WIDTH; x++){
			 
					
					byte a = 0;
					byte r = bytes[ind];
					byte g = bytes[ind+HEIGHT*WIDTH];
					byte b = bytes[ind+HEIGHT*WIDTH*2]; 
					
					int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
					//int pix = ((a << 24) + (r << 16) + (g << 8) + b);
					
					
					image.setRGB(x,y,pix);					
					ind++;
					
				}
			}
	    		    	
			
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		return image;
	}


	private File[] getOtherImageFiles(File reference)
	{
		  File dir = new File(reference.getParent());
		  
		  File[] others = dir.listFiles(new JPEGImageFileFilter());
		  return others;
	}

	
}
