package com.hung.effect;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class FrameImage {
	private String name;
	private BufferedImage image;
	public FrameImage(BufferedImage image,String name) {
		this.image = image;
		this.name = name;	
	}
	public FrameImage(FrameImage frame) {
		image = new BufferedImage(frame.getImageWidth(),frame.getImageHeight(),frame.getImage().getType());
		Graphics g = image.getGraphics();
		g.drawImage(frame.getImage(), 0, 0, null);
	}
	public void draw(Graphics2D g2,int x, int y) {
		g2.drawImage(image, x - image.getWidth()/2, y - image.getHeight()/2, null);
	}
	public int getImageWidth() {
		return image.getWidth();
	}
	public int getImageHeight() {
		return image.getHeight();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
}
