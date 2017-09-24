package com.harsha.windowbuilder;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;


public class GalleryDirectory {

	JFileChooser chooser;
	JList mList;
	
	
	public GalleryDirectory() {
	
		chooser = new JFileChooser();
		
	}
		
	public void setGalleryDirectory(){
		
	
	    chooser.setCurrentDirectory(new java.io.File("."));
	    chooser.setDialogTitle("choosertitle");
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    chooser.setAcceptAllFileFilterUsed(false);

	    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	      System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
	      System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
	    } else
	        {
	        JOptionPane.showMessageDialog(null,
	            "You must select one image to be the reference.", "Aborting...",
	            JOptionPane.WARNING_MESSAGE);
	        }	   	   
	}
	
	public File[] ReadDirectory() {
		
		File[] listOfFiles = null;
	   	try{
	   		File folder = new File(chooser.getSelectedFile().toString());
		    FilenameFilter rgbFilter = new TextFileFilter();
		    listOfFiles = folder.listFiles(rgbFilter);
		    	
	   	}catch(NullPointerException e){
	   		System.out.println("No directory selected.");
	   	}
	    
	    return listOfFiles;
	}
	
}
