package com.harsha.windowbuilder;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.ScrollPane;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;



import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JLabel;

import java.awt.GridLayout;

import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;

public class PlayGround extends JFrame {

	private JPanel contentPane;
	DefaultListModel<String> listModel;
	private JScrollPane scrollPane;
	private JList list;
	private JLabel lblLabel;
	
	private JList vidlist;
	DefaultListModel<String> vidlistModel;
	
	ListSelectionModel listSelectionModel;
	ListSelectionModel vidlistSelectionModel;
	
	SingleImageReader imageRead;
	
	LinkedList<String> fileList;
	LinkedList<String> animeList;
	LinkedList<String> videoList;
	
	public static HashMap<String, String> map;
	
	static boolean videoSelected;
	
	private JButton btnGalleryView;
	private JButton btnOpenImagevideo;
	JButton btnNext;
	JButton btnPrevious;
	JButton btnSeed;
	private JSlider slider;
	private JTextField textField;
	private JButton btnAnime;
	private JLabel lblMultimediaCsciProject;
	private JLabel lblHashokusceduKgopaluscedu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//EventQueue.invokeLater(new Runnable() {
		//	public void run() {
				try {
					PlayGround frame = new PlayGround();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			//}
		//});
	}

	/**
	 * Create the frame.
	 */
	public PlayGround() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 768);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
				
		
		fileList = new LinkedList<String>();
		animeList = new LinkedList<String>();
		videoList = new LinkedList<String>();
		
		
		map = new HashMap<String, String>();
		
		listModel = new DefaultListModel<String>();
		vidlistModel = new DefaultListModel<String>();
		
		contentPane.setLayout(null);
				
		videoSelected = false;
						
		vidlist = new JList(vidlistModel);
		vidlist.setBounds(10, 49, 220, 669);
		contentPane.add(vidlist);
		vidlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		vidlist.setSelectedIndex(0);
				
		
		JButton btnAddDirectory = new JButton("Add Image/Directory");
		btnAddDirectory.setBounds(10, 11, 220, 33);
		contentPane.add(btnAddDirectory);
		
		AddDirectoryListener directoryListner = new AddDirectoryListener(btnAddDirectory);
		btnAddDirectory.addActionListener(directoryListner);
		
		
		JPanel panel = new JPanel();
		panel.setBounds(656, 84, 352, 288);
		contentPane.add(panel);
		
		lblLabel = new JLabel(" ");
		panel.add(lblLabel);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(254, 49, 234, 669);
		contentPane.add(scrollPane);
		
		list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		
		scrollPane.setViewportView(list);
		
		btnGalleryView = new JButton("Gallery View");
		btnGalleryView.setBounds(568, 487, 114, 23);
		contentPane.add(btnGalleryView);
		GalleryViewListener galleryListener = new GalleryViewListener();
		btnGalleryView.addActionListener(galleryListener);
		
		
		
		
		btnNext = new JButton("Next");
		btnNext.setBounds(919, 399, 89, 23);
		contentPane.add(btnNext);
					
		btnPrevious = new JButton("Previous");
		btnPrevious.setBounds(656, 399, 89, 23);
		contentPane.add(btnPrevious);
		
		btnOpenImagevideo = new JButton("Open Image/Video");
		btnOpenImagevideo.setBounds(755, 399, 154, 23);
		contentPane.add(btnOpenImagevideo);
						
		btnSeed = new JButton("Use Seed");		
		btnSeed.setBounds(755, 487, 154, 23);
		contentPane.add(btnSeed);
		
		ButtonListener buttonListener = new ButtonListener();
		btnPrevious.addActionListener(buttonListener);
		btnNext.addActionListener(buttonListener);
		btnOpenImagevideo.addActionListener(buttonListener);
		btnSeed.addActionListener(buttonListener);

		
		
		slider = new JSlider(JSlider.HORIZONTAL, 0, 4000, 2000);
		slider.setBounds(919, 487, 200, 26);
		contentPane.add(slider);
		slider.setMajorTickSpacing(100);
		slider.setMinorTickSpacing(10);
		slider.setPaintTicks(true);
		
		JLabel lblThreshold = new JLabel("Threshold : ");
		lblThreshold.setBounds(929, 524, 79, 14);
		contentPane.add(lblThreshold);
		
		textField = new JTextField("2000");
		textField.setBounds(1033, 524, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		btnAnime = new JButton("Anime");
		btnAnime.setBounds(568, 535, 89, 23);
		contentPane.add(btnAnime);			
		btnAnime.addActionListener(buttonListener);
		
	    SliderListener sliderListener = new SliderListener();	
	    slider.addChangeListener(sliderListener);
		
		listSelectionModel = list.getSelectionModel();
		ListSelectorListener listSelectorListener = new ListSelectorListener();
		listSelectionModel.addListSelectionListener(listSelectorListener);
		
		vidlistSelectionModel = vidlist.getSelectionModel();
		VidListSelectorListener vidlistSelectorListener = new VidListSelectorListener();
		vidlistSelectionModel.addListSelectionListener(vidlistSelectorListener);
		
		//Credits.
		lblMultimediaCsciProject = new JLabel("Multimedia CSCI-576 Project by Harshavardhana Ashok & Karthik Gopal");
		lblMultimediaCsciProject.setBounds(656, 621, 463, 14);
		contentPane.add(lblMultimediaCsciProject);
		
		lblHashokusceduKgopaluscedu = new JLabel("hashok@usc.edu  |  kgopal@usc.edu");
		lblHashokusceduKgopaluscedu.setBounds(723, 646, 267, 14);
		contentPane.add(lblHashokusceduKgopaluscedu);
		
		
	}
	
	class SliderListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			// TODO Auto-generated method stub
			JSlider source = (JSlider)e.getSource();
		    if (!source.getValueIsAdjusting()) {
		        int thresh = (int)source.getValue();
		        System.out.println(thresh);		        
		        textField.setText(String.valueOf(thresh));
		    }
		}
		
	}
	
	class AddDirectoryListener implements ActionListener {

		private JButton button;
		
		public AddDirectoryListener(JButton button) {
			this.button = button;
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			
			LinkedList<String> test = new LinkedList<String>();
			
			GalleryDirectory addDirectory = new GalleryDirectory();
			addDirectory.setGalleryDirectory();
						
			File[] files = addDirectory.ReadDirectory();			
			for (File i : files){
				
				if(i.length() > 304128){
					int index = i.toString().lastIndexOf("\\");			
					videoList.add(i.toString());
					//TODO: Add to Map each video's imageFile.
					
					VideoFrames vf = new VideoFrames(4);
					
					
					
					
					fileList.addAll(vf.getFrames(i.toString()));	
					
					vidlistModel.addElement(i.toString().substring(index+1));
				}else {
					int index = i.toString().lastIndexOf("\\");			
					fileList.add(i.toString());
					listModel.addElement(i.toString().substring(index+1));
				}
				
									
			}
			System.out.println("SIZE fileList: "+fileList.size());
			System.out.println("SIZE videoList: "+videoList.size());
			
		}	
		
		
	}
	
	class ListSelectorListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent event) {
			 int firstIndex = event.getFirstIndex();
	            int lastIndex = event.getLastIndex();
	            boolean isAdjusting = event.getValueIsAdjusting();
	            
	            videoSelected = false;
	            
	            if(!isAdjusting){
	            	System.out.println(firstIndex+"  "+lastIndex);
	            	int selection = list.getSelectedIndex();
	            	System.out.println(list.getSelectedValue());
	            	System.out.println(listModel.get(selection));
	            	
	            	loadImage(selection);
	            }
		}
		
		void loadImage(int selection){
			
			imageRead = new SingleImageReader();
			imageRead.loadImage(fileList.get(selection));					
			
			lblLabel.setIcon(new ImageIcon(imageRead.image));
			
		}
		
		
	}
	
	class VidListSelectorListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent event) {
			 int firstIndex = event.getFirstIndex();
	            int lastIndex = event.getLastIndex();
	            boolean isAdjusting = event.getValueIsAdjusting();
	            
	            videoSelected = true;
	            
	            if(!isAdjusting){
	            	System.out.println(firstIndex+"  "+lastIndex);
	            	int selection = vidlist.getSelectedIndex();
	            	System.out.println(vidlist.getSelectedValue());
	            	System.out.println(vidlistModel.get(selection));
	            	System.out.println("In video selection");
	            	loadVideo(selection);
	            }
		}
		
		
		
		void loadVideo(int selection){
			
			imageRead = new SingleImageReader();
			imageRead.loadImage(videoList.get(selection));					
			System.out.println("Image load problems");
			lblLabel.setIcon(new ImageIcon(imageRead.image));
		}
		
		
	}
	
	class GalleryViewListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
						
				CollageGallery collage = new CollageGallery(fileList, fileList.size(), 2000);										
			//SuperCollage collage = new SuperCollage(fileList);				       
			//collage.setVisible(true);						
		}		
	}
	
	class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) { 
			
			if(event.getSource() == btnNext){				
				list.setSelectedIndex(list.getSelectedIndex()+1);
			}
			
			if(event.getSource() == btnPrevious){
				list.setSelectedIndex(list.getSelectedIndex()-1);
			}
			
			if(event.getSource() == btnOpenImagevideo){
				//TODO: Open Single Image/Video in a Panel.
				System.out.println("Open");
				if(videoSelected){
					System.out.println();
					//SingleFrameViewer viewer = new SingleFrameViewer(videoList.get(vidlist.getSelectedIndex()).toString());
					Runnable r1 = new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							DisplayVideo dv = new DisplayVideo();
							dv.showVideo(videoList.get(vidlist.getSelectedIndex()).toString());
						}
					};
					Thread thr = new Thread(r1);
					thr.start();
					
				}else {
					SingleFrameViewer viewer = new SingleFrameViewer(fileList.get(list.getSelectedIndex()).toString());
				}
						
				
			}
			
			if(event.getSource() == btnAnime){
				AnimeFilter anime = new AnimeFilter();
				animeList = anime.filterAnime(fileList);
				
				
				CollageGallery c = new CollageGallery(animeList, animeList.size(), 2000);
			}
			
			if(event.getSource() == btnSeed){
				String seed = fileList.get(list.getSelectedIndex()).toString();
				try {
					SeedClustering seedCluster = new SeedClustering(new File(seed), Integer.parseInt(textField.getText())+500, 1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
			
		}
		
		
	}
}


