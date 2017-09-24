package com.harsha.windowbuilder;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class SingleFrameViewer {

	public static final int WIDTH = 352;
	public static final int HEIGHT = 288; 
	
	
	SingleImageReader image;
	static BufferedImage[] imga;
	
	
	SingleFrameViewer(String filename) {
	
		File file = new File(filename);	
		long len = file.length();
		
		
			
			image = new SingleImageReader();
			image.loadImage(filename);
			
			JFrame frame = new JFrame();  
		    JLabel label = new JLabel();
		    label.setIcon(new ImageIcon(image.image));	   
		    frame.getContentPane().add(label, BorderLayout.CENTER);
		    frame.pack();
		    //frame.setMinimumSize(new Dimension(WIDTH+15, HEIGHT+80));
		    frame.setVisible(true);
							
	}
	
	public void loadVideo(File file){
		System.out.println(file.toString());

		 JDialog frame = new JDialog();
		 JLabel label = new JLabel();
		 
		 frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		 frame.add(label, BorderLayout.CENTER);
	    
	     frame.pack();
		
    	
   	 
   	 int number_of_frames = 0;
   			   	    	    	 
	    InputStream is;
		try {
			is = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(is);
			long len = file.length();	    
			 byte[] bytes = new byte[(int)len];
			 number_of_frames = bytes.length/(WIDTH*HEIGHT*3); 
			
			 imga = new BufferedImage[number_of_frames];
			 
			 int offset = 0;
			 int numRead = 0;
			 
			 while (offset < bytes.length && (numRead=bis.read(bytes, offset, bytes.length-offset)) >= 0) {
	            offset += numRead;
	        }
	        
			 int ind =  0;     //101376 * 1000;
	    	
			 for(int i=0; i < number_of_frames; i++){
		    		
		    		ind = WIDTH * HEIGHT * 3 * i;    	    		
		    		imga[i] = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		    		System.out.println(i);
		    		
		    		for(int y = 0; y < HEIGHT; y++){
					
		    			for(int x = 0; x < WIDTH; x++){
				 
							byte a = 1;
							byte r = bytes[ind];
							byte g = bytes[ind+HEIGHT*WIDTH];
							byte b = bytes[ind+HEIGHT*WIDTH*2]; 
							
							int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | ((b & 0xff));
							//int pix = ((a << 32) + (r << 16) + (g << 8) + b);
																	
							//newPixels[x][y] = pix;
							imga[i].setRGB(x, y, pix);
							ind++;					
							
						}			
					}
		    		
		    		
			 	}
			 
			
			 frame.setVisible(true);

		     
		     label.setIcon(new ImageIcon(imga[40]));
				frame.pack();

			     label.setIcon(new ImageIcon(imga[60]));
					frame.pack();
		     
		for (int i = 0; i < number_of_frames; i++){
				System.out.println("frame "+i);
				 try {
				    Thread.sleep(1000/10);                 
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
				
					   
			}						
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	    
	   
		
	}
	
	
	
}
