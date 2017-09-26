package com.hung.effect;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation {
	
	String name;
	boolean isRepeated;
	ArrayList <FrameImage> frames; 
	ArrayList <Double> delayTimes;
	ArrayList <Boolean> ignoreFrames;
	int currentFrame;
	private boolean drawRectFrame;
	long beginTime;
	
	public Animation() {
	
		beginTime = 0;
		currentFrame = 0;
		frames = new ArrayList<FrameImage>();
		delayTimes = new ArrayList<Double>();
		ignoreFrames = new ArrayList<Boolean>();
		isRepeated = true;
		drawRectFrame = false;
		
	}
	
	public Animation(Animation animation) {
		beginTime = animation.beginTime;
		currentFrame = animation.currentFrame;
		drawRectFrame = animation.drawRectFrame;
		isRepeated = animation.isRepeated;
		
		for(FrameImage img: animation.frames) {
			frames.add(img);
		}
		
		for(Double delay: animation.delayTimes) {
			delayTimes.add(delay);
		}
		
		for(boolean b: animation.ignoreFrames) {
			ignoreFrames.add(b);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsRepeated() {
		return isRepeated;
	}

	public void setIsRepeated(Boolean isRepeated) {
		this.isRepeated = isRepeated;
	}

	public ArrayList<FrameImage> getFrames() {
		return frames;
	}

	public void setFrames(ArrayList<FrameImage> frames) {
		this.frames = frames;
	}

	public ArrayList<Double> getDelayTimes() {
		return delayTimes;
	}

	public void setDelayTimes(int id,double time) {
		delayTimes.set(id, time);
	}

	public boolean isIgnoreFrames(int id) {
		return ignoreFrames.get(id);
	}

	public void setIgnoreFrames(int id) {
		if(id>=0 && id< ignoreFrames.size())
			ignoreFrames.set(id, true);
	}
	
	public void unIgnoreFrames(int id) {
		if(id>=0 && id< ignoreFrames.size())
			ignoreFrames.set(id, false);
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(int currentFrame) {
		if(currentFrame>=0 && currentFrame<frames.size())
			this.currentFrame = currentFrame;
		else 
			this.currentFrame = 0;
	}

	public boolean isDrawRectFrame() {
		return drawRectFrame;
	}
	

	public long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(long beginTime) {
		this.beginTime = beginTime;
	}

	public void setDrawRectFrame(Boolean drawRectFrame) {
		this.drawRectFrame = drawRectFrame;
	}
	
	public BufferedImage getCurrentImage() {
		return frames.get(currentFrame).getImage();
	}
	
	public boolean isLastFrame() {
		if(currentFrame==frames.size()-1) return true;
		else return false;
	}
	// Process method group
	public void reset() {
		currentFrame = 0;
		beginTime = 0;
		for(int i=0;i<ignoreFrames.size();i++){
			ignoreFrames.set(i, false);
		}
	}
	public void add(FrameImage frame, double timeToNextFrame){
        ignoreFrames.add(false);
        frames.add(frame);
        delayTimes.add(new Double(timeToNextFrame));
	}
	public void update(long currentTime) {
		if(beginTime == 0) beginTime = currentTime;
		else {
			if(currentTime - beginTime >= delayTimes.get(currentFrame)) {
				nextFrame();
				beginTime=currentTime;	
			}
		}
	}
	public void nextFrame() {
		currentFrame++;
		if(currentFrame>=frames.size()) 
			if(isRepeated) currentFrame = 0;
		if(ignoreFrames.get(currentFrame)) nextFrame();
	}
	
	public void flipAllFrames() {
		for(int i=0;i<frames.size();i++) {
			BufferedImage image = frames.get(i).getImage();
			AffineTransform affineTransform = AffineTransform.getScaleInstance(-1, 1);
			affineTransform.translate(-image.getWidth()/2, 0);
			AffineTransformOp op = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BILINEAR);
			image = op.filter(image, null);
			frames.get(i).setImage(image);
		}
	}
	
	public void draw(Graphics2D g2, int x, int y) {
		BufferedImage image = getCurrentImage();
		g2.drawImage(image,x-image.getWidth()/2,y-image.getHeight()/2,null);
		if(isDrawRectFrame())
			g2.drawRect(x-image.getWidth()/2, y-image.getHeight()/2, image.getWidth(), image.getHeight());
	}
	
}
