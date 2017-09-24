package com.harsha.windowbuilder;



import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

public class CollageGallery implements MouseListener{


	public static int num;
	public static final int num2 = 3;
	
	int threshold = 2000;
	//BufferedImage img;
	
	LinkedList<String> filelist;
	
	SingleImageReader images;
	
	/**
	 * Create the frame.
	 */
	public CollageGallery(LinkedList<String> fileList, int size, int threshold) {
				
		
		this.threshold = threshold;
		System.out.println("THRESHOLD: "+this.threshold);
		JDialog frame = new JDialog();
		JPanel panel = new JPanel();
		
		
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		num = (int) Math.ceil(Math.sqrt(size));
		
		filelist = new LinkedList<String>(fileList);
		
		panel.setBounds(100, 100, 176*num, 144*num);
		panel.setLayout(new GridLayout(num, num, 0, 0));
		
		/*img = new BufferedImage(176, 144, BufferedImage.TYPE_INT_RGB);					
		try {
		    img = ImageIO.read(new File("test.png"));
		    System.out.println("read file");
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/ 
		
		panel.addMouseListener(this);
		
		
		
		for(int i=0; i<num*num; i++){						
			
			JLabel label = new JLabel(" ");
			
			if(i < size){
				images = new SingleImageReader();					
				images.loadImage(fileList.get(i));
				images.image = images.getScaledImage(images.image, 176, 144);
													
				label.setIcon(new ImageIcon(images.image));
							
			}
			panel.add(label);
			//frame.getContentPane().add(label);
							
		}	
		
		JScrollPane scroll = new JScrollPane(panel);
		frame.add(scroll);
		frame.pack();
		frame.setVisible(true);
	
	
	
	}
		

	@Override
	public void mouseClicked(MouseEvent event) {
		// TODO Auto-generated method stub
		System.out.println("MouseClick");
		if(event.getClickCount() >=2){
			System.out.println("Mouse position: "+ event.getX()+"  "+event.getY());
			
			ClickImage click = new ClickImage(176*num, 144*num, event.getX(), event.getY());
			System.out.println(click.getPosImage());
			
			System.out.println(filelist.get(click.getPosImage()));
			
			   File file = new File(filelist.get(click.getPosImage()));
			  
			   try {
				   	SeedClustering seed = new SeedClustering(file, threshold, 1);
			} catch (IOException e) {				
				e.printStackTrace();
			}
			   			   		
			
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("MouseEntered.");
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("MouseExited.");
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	

}
