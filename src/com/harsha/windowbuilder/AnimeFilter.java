package com.harsha.windowbuilder;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class AnimeFilter {

	SeedClustering seedCluster;
	
	LinkedList<String> animeList;
	
	public AnimeFilter() {
		animeList = new LinkedList<String>();
		
	}
	
	public LinkedList<String> filterAnime(LinkedList<String> fileList){
		
		try {
			
			for(int i = 0; i < fileList.size(); i++){
				System.out.println(i+"...");
				seedCluster = new SeedClustering(new File(fileList.get(i).toString()), 2000, 0);
				
				if(seedCluster.clusterList.size() == 1){
					animeList.add(seedCluster.clusterList.getFirst());
				}
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return animeList;
	}
	
}
