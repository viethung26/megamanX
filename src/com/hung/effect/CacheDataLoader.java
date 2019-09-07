package com.hung.effect;


import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;

import javax.imageio.ImageIO;

public class CacheDataLoader {
	
	private static CacheDataLoader instance;
	
	private String frameFile = "data/frame.txt";
	private String animationFile = "data/animation.txt";
	private String physMapFile = "data/phys_map.txt";
	private String backgroundFile = "data/background_map.txt";
	private String soundFile = "data/sounds.txt";

	private Hashtable<String, FrameImage> frameImages;
	private Hashtable<String, Animation> animations;
	private Hashtable<String, AudioClip> sounds;
	private int[][] physicalMap;
	private int[][] backGroundMap;
	private CacheDataLoader() {
		frameImages = new Hashtable<String, FrameImage>();
		animations = new Hashtable<String, Animation>();
		sounds = new Hashtable<String, AudioClip>();
	}
	
	public static CacheDataLoader getInstance() {
		if(instance==null) instance = new CacheDataLoader();
		return instance;
	}
	
	public FrameImage getFrameImage(String name) {
		return new FrameImage(frameImages.get(name));
	}

	public Animation getAnimation(String name) {
		return new Animation(animations.get(name));
	}
	
	public int[][] getPhysicalMap(){
		return instance.physicalMap;
	}

	public int[][] getBackGroundMap() {
		return instance.backGroundMap;
	}

	public AudioClip getSound(String name){
	    return sounds.get(name);
    }

	public void loadFrame() throws IOException {
		
		String line = null;
		FileReader fr = new FileReader(frameFile);
		BufferedReader br = new BufferedReader(fr);
		
		if(br.readLine()==null) {
			System.out.println("File null");
			throw new IOException();
		} 
		else {
			fr = new FileReader(frameFile);
			br = new BufferedReader(fr);
			
			while((line = br.readLine()).equals(""));
			
			int n = Integer.parseInt(line);
			for(int i=0;i<n;i++) {
				FrameImage frame = new FrameImage();
				String[] str;
				while((line = br.readLine()).equals(""));
				frame.setName(line.trim());
				
				while((line = br.readLine()).equals(""));
				str = line.split(" ");
				String path = str[1].trim();
				
				while((line = br.readLine()).equals(""));
				str = line.split(" ");
				int x = Integer.parseInt(str[1]);
				
				while((line = br.readLine()).equals(""));
				str = line.split(" ");
				int y = Integer.parseInt(str[1]);
				
				while((line = br.readLine()).equals(""));
				str = line.split(" ");
				int w = Integer.parseInt(str[1]);
				
				while((line = br.readLine()).equals(""));
				str = line.split(" ");
				int h = Integer.parseInt(str[1]);
			
				BufferedImage img = ImageIO.read(new File(path));
				frame.setImage(img.getSubimage(x, y, w, h));
				
				instance.frameImages.put(frame.getName(), frame);
			}
			br.close();
			
		}
	}
	
	public void loadAnimation() throws IOException {
		
		String line = null;
		FileReader fr = new FileReader(animationFile);
		BufferedReader br = new BufferedReader(fr);
		
		if(br.readLine()==null) {
			System.out.println("File null");
			throw new IOException();
		} 
		else {
			fr = new FileReader(animationFile);
			br = new BufferedReader(fr);
			
			while((line = br.readLine()).equals(""));
			
			int n = Integer.parseInt(line);
			for(int i=0;i<n;i++) {
				Animation animation = new Animation();
				String[] str;
				while((line = br.readLine()).equals(""));
				animation.setName(line.trim());
				
				while((line = br.readLine()).equals(""));
				str = line.split(" ");
				for(int j=0;j<str.length;j+=2) {
					animation.add(getFrameImage(str[j]), Double.parseDouble(str[j+1]));
				}
				instance.animations.put(animation.getName(),animation);
			}
			br.close();
			
		}
	}
	
	public void loadMap() throws IOException{
		FileReader fr = new FileReader(physMapFile);
		BufferedReader br = new BufferedReader(fr);
		String line = null;
		
		if((line = br.readLine())==null) {
			System.out.println("File error");
			throw new IOException();
		}
		else {
			fr = new FileReader(physMapFile);
			br = new BufferedReader(fr);
			line = br.readLine();
			int numberOfRows = Integer.parseInt(line);
			line = br.readLine();
			int numberOfColumns = Integer.parseInt(line);
			instance.physicalMap = new int[numberOfRows][numberOfColumns];
			
			for(int i=0;i<numberOfRows;i++) {
				line = br.readLine();
				String [] str = line.split(" ");
				for(int j=0;j<numberOfColumns;j++)
					instance.physicalMap[i][j] = Integer.parseInt(str[j]);
			}
			br.close();
		}
	}
		
	public void loadBackground() throws IOException{
		FileReader fr = new FileReader(backgroundFile);
		BufferedReader br = new BufferedReader(fr);
		String line = null;
		
		if((line = br.readLine())==null) {
			System.out.println("File error");
			throw new IOException();
		}
		else {
			fr = new FileReader(backgroundFile);
			br = new BufferedReader(fr);
			line = br.readLine();
			int numberOfRows = Integer.parseInt(line);
			line = br.readLine();
			int numberOfColumns = Integer.parseInt(line);
			instance.backGroundMap = new int[numberOfRows][numberOfColumns];
			
			for(int i=0;i<numberOfRows;i++) {
				line = br.readLine();
				String [] str = line.split(" ");
				for(int j=0;j<numberOfColumns;j++)
					instance.backGroundMap[i][j] = Integer.parseInt(str[j]);
			}
			br.close();
		}
	}

	public void loadSound() throws IOException{
	    FileReader fr = new FileReader(soundFile);
	    BufferedReader br = new BufferedReader(fr);
	    String line = null;
        if((line = br.readLine())==null) {
            System.out.println("File error");
            throw new IOException();
        }
	    int n = Integer.parseInt(line);
        for(int i=0;i<n;i++) {
            while ((line = br.readLine()).equals("")) ;
            String[] str = line.split(" ");
            AudioClip audioClip = null;
            try{
                audioClip = Applet.newAudioClip(new URL("file","",str[1]));
            }catch(Exception e){

            }
            instance.sounds.put(str[0],audioClip);
        }
        br.close();

    }

	public void loadData() throws IOException{
		
		loadFrame();
		loadAnimation();
		loadMap();
		loadBackground();
		loadSound();
	}
}
