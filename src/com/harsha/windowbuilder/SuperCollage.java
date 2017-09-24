package com.harsha.windowbuilder;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class SuperCollage extends JFrame{

	
	SingleImageReader images;
	
	public SuperCollage(LinkedList<String> fileList) {
	
		setBounds(100, 100, 7*176, 144*fileList.size()/7);
		Container cp = getContentPane();
		cp.setLayout(new GridLayout(fileList.size()/7, 7));
		
		
		JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 101, 7*176-50, 276);
        //scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(scrollPane);
		
        JPanel borderlaoutpanel = new JPanel();
        scrollPane.setViewportView(borderlaoutpanel);
        borderlaoutpanel.setLayout(new BorderLayout(0, 0));
		
		for(int i=0; i<fileList.size(); i++){						
			
			images = new SingleImageReader();					
			images.loadImage(fileList.get(i));
			images.image = images.getScaledImage(images.image, 176, 144);
		
			
			JLabel lab = new JLabel(" ");			
			lab.setIcon(new ImageIcon(images.image));
			getContentPane().add(lab);
						
		}							
	}
		
}
