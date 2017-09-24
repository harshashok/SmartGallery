package com.harsha.windowbuilder;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class SingleImageReader {

	private static final int WIDTH = 352;
	private static final int HEIGHT = 288;
	BufferedImage image;
	String tag;
	
	public SingleImageReader() {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		
	}
	
	public void loadImage(String filepath){
		
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
	    	
	    	//image.getScaledInstance(176, 144, 0);
			
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	public BufferedImage getScaledImage(BufferedImage srcImg, int w, int h){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
	    Graphics2D g2 = resizedImg.createGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();
	    return resizedImg;
	}
}
