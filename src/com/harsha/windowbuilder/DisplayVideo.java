package com.harsha.windowbuilder;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.lang.System.*;


class DisplayVideo{
	
	long numFrames;
	int width;
    int height;
	
	DisplayVideo() {
		numFrames = 0;
		width = 352;
	    height = 288;
	}
	
	public static BufferedImage resizePixels(int[] pixels, int width, int height) {
		BufferedImage imgScaled = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    double x_ratio = 1.0;
	    double y_ratio = 1.0;
	    double px, py ; 
	    for (int i=0;i<height;i++) {
	        for (int j=0;j<width;j++) {
	            px = Math.floor(j*x_ratio) ;
	            py = Math.floor(i*y_ratio) ;
	            imgScaled.setRGB(j,i, pixels[(int)((py*width)+px)]);
	        }
	    }
	    return imgScaled;
	}
	
	public BufferedImage[] getVideo(String pathName){
		
        BufferedImage[] img = null;
        
	    byte []bytes = getBytes(pathName);
		img = new BufferedImage[(int) numFrames];
   
		int []pix = new int[height*width];
		int index = 0;
		for (int z=0; z < numFrames; z++) {
		    int ind = z * height * width * 3;
		    img[z] = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		    index = 0;
		    for(int y = 0; y < height; y++){
			    for(int x = 0; x < width; x++){
			    	
				    byte a = 0;
				    byte r = bytes[ind];
				    byte g = bytes[ind+height*width];
				    byte b = bytes[ind+height*width*2]; 
				    
				    pix[index++] = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
				    ind++;
			    } //end of for(x)
		    } // end of for(y)
		    img[z] = resizePixels(pix, width, height);
		} //end of for(z)	
	    return img;
	}
	
	public byte[] getBytes(String pathName){
		File file = new File(pathName);
	    InputStream is = null;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    long len = file.length();
	    byte[] bytes = new byte[(int)len];
	    numFrames = len / (3 * height * width);
	    int offset = 0;
        int numRead = 0;
        try {
			while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
			    offset += numRead;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return bytes;
	}
	
	public void showVideo(String pathName) {
	   	
		int frameRate = 30;
		System.out.println("Path name is " + pathName);
		
	    BufferedImage []img = getVideo(pathName);
	
		
		JFrame frame = new JFrame();
	   
	    // Use a label to display the image
	   
	    JLabel label = new JLabel();
	    frame.getContentPane().add(label, BorderLayout.CENTER);
	    frame.setVisible(true);

	    for (int i = 0; i < numFrames; ++i) {
	        try {
	            label.setIcon(new ImageIcon(img[i]));
	            frame.pack(); 
	            long curTime = java.lang.System.currentTimeMillis();
	            long nextTime = curTime + (long)33.33;
	           
	            Thread.sleep(1000/frameRate);
	        } catch (/*Interrupted*/Exception ex) {
	            Thread.currentThread().interrupt();
	        }
	    }
    }
}