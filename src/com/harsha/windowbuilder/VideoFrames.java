package com.harsha.windowbuilder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;

import javax.swing.ImageIcon;

class VideoFrames{
	int height;
	int width;
	
	
	int frameCalc;

	public VideoFrames(int frameNumber) {
		// TODO Auto-generated constructor stub
		width = 352;
		height = 288;
		
		frameCalc = 300/frameNumber;
	}
	
	public LinkedList<String> getFrames(String videoPath){
		
		File file = new File(videoPath);
		
		new File(file.getParent()+"\\source").mkdir();
	    long len = file.length();
		LinkedList<String>  pathNames = new LinkedList<String>();
		DisplayVideo dv = new DisplayVideo();
		byte []bytes = dv.getBytes(videoPath);
		long numFrames = len / (3 * height * width); 
		long eachFrameByteSize = len/numFrames;
		
			for (int z=0; z < numFrames; z = z + 60) {
			    int ind = z * height * width * 3;
			    byte []eachFrameBytes = new byte[(int)eachFrameByteSize];
			    byte []r = new byte[(int)(eachFrameByteSize/3.0)];
			    byte []g = new byte[(int)(eachFrameByteSize/3.0)];
			    byte []b = new byte[(int)(eachFrameByteSize/3.0)];
			    int index = 0;
			    			    
			    
			    int id = videoPath.lastIndexOf("\\");		
			    String videoName = videoPath.toString().substring(id+1);
			    
			    
				String outputFileName = file.getParent()+"\\source\\"+videoName.replaceAll(".rgb", "image" + z + ".rgb");
				pathNames.add(outputFileName);
				
				
				
				PlayGround.map.put(outputFileName, videoPath);
			    for(int y = 0; y < height; y++){
				    for(int x = 0; x < width; x++){
				    	
					    byte a = 1;
					    r[index] = bytes[ind];
					    g[index] = bytes[ind+height*width];
					    b[index] = bytes[ind+height*width*2]; 
					    
					    index++;
					    ind++;
				    } //end of for(x)
			    } // end of for(y)
			    int j=0;
			    for(int i=0; i<r.length; i++){
			    	eachFrameBytes[j++] = r[i];
			    }
			    for(int i=0; i<g.length; i++){
			    	eachFrameBytes[j++] = g[i];
			    }
			    for(int i=0; i<b.length; i++){
			    	eachFrameBytes[j++] = b[i];
			    }
			    
	            try {
				    OutputStream os = new FileOutputStream(outputFileName);
				    os.write(eachFrameBytes);
				    
			    } catch (FileNotFoundException e) {
				    // TODO Auto-generated catch block
				    e.printStackTrace();
			    } catch (IOException e){
			    	
			    }
	                    
		}
	
		return pathNames;
	}
	
}