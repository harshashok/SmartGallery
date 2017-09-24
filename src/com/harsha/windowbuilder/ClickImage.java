package com.harsha.windowbuilder;

public class ClickImage {

	int widthX;
	int heightY;
	
	int posX;
	int posY;
	
	int blocksX;
	int blocksY;
	
	public static final int WIDTH = 176;
	public static final int HEIGHT = 144;
	
	public ClickImage(int widthX, int heightY) {	
		this.widthX = widthX;
		this.heightY = heightY;	
		this.posX = -1;
		this.posY = -1;
		this.blocksX = widthX/WIDTH;
		this.blocksY = heightY/HEIGHT;
		
	}
	
	public ClickImage(int widthX, int heightY, int posX, int posY){
		this.widthX = widthX;
		this. heightY = heightY;
		this.posX = posX;
		this.posY = posY;
		this.blocksX = widthX/WIDTH;
		this.blocksY = heightY/HEIGHT;
	}
	
	public int getPosImage(){
		return getPosImage(posX, posY);
	}
	
	public int getPosImage(int x, int y){
		this.posX = x;
		this.posY = y;
		
		int position = -1;
		if((posX >=0 && posY >= 0) && (posX <=widthX && posY <= heightY )){
				
			int offsetX = posX/WIDTH;
			int offsetY = posY/HEIGHT;					
			
			position = offsetY*blocksY + offsetX;					
		}
		
		return position;
	}
	
}
