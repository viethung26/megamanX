package com.hung.effect;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

public class CacheDataLoader {
	
	private static CacheDataLoader instance;
	
	private String frameFile = "data/frame.txt";
	private String animationFile = "data/animation.txt";
	
	private Hashtable<String, FrameImage> frameImages;
	private Hashtable<String, Animation> animations;
	
	private CacheDataLoader() {
		frameImages = new Hashtable<String, FrameImage>();
		animations = new Hashtable<String, Animation>();
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
			}
			br.close();
			
		}
	}
	
	public void loadData() throws IOException{
		
		loadFrame();
		loadAnimation();
	}
}
