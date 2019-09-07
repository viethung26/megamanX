package com.hung.gameobject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.hung.effect.CacheDataLoader;

public class PhysicalMap extends GameObject {
	int [][] physicalMap;
	final int SIZE = 30;
	int numberOfRows,numberOfColumns;	
	public PhysicalMap(float posX, float posY, float width, float height, GameWorld gameWorld) {
		super(posX,posY,width,height,gameWorld);
		physicalMap = CacheDataLoader.getInstance().getPhysicalMap();
		if(physicalMap!=null) {
			numberOfRows = physicalMap.length;
			numberOfColumns = physicalMap[0].length;
		}
	}
	
	public void update() {

	}
	
	public void draw(Graphics2D g2) {
		g2.setColor(Color.GRAY);
		Camera camera = getGameWorld().camera;
		for(int i=0;i<numberOfRows;i++) 
			for(int j=0;j<numberOfColumns;j++)
				if(physicalMap[i][j]==1) 
					g2.fillRect((int)getPosX() + j*SIZE - (int)camera.getPosX(),(int)getPosY() + i*SIZE+ - (int)camera.getPosY(), SIZE, SIZE);			
	}
	
	public Rectangle haveCollisionWithLand(Rectangle rect) {
		int posX1 = rect.x/SIZE -2; //limit for checking from the left of Character
		int posX2 = (rect.x + rect.width)/SIZE +2; //limit for checking from the right of Character
		if(posX1 < 0) posX1=0;
		if(posX2 >= physicalMap[0].length) posX2 = physicalMap[0].length - 1;
		int posY1 = (rect.y + rect.height)/SIZE - 2;
		int posY2 = (rect.y + rect.height)/SIZE +2; //limit for checking from the bottom of Character
		if(posY1<0) posY1=0;
		if(posY2 >= physicalMap.length) posY2 = physicalMap.length-1;
		for(int y = posY1; y<physicalMap.length;y++) {
			for(int x= posX1; x<=posX2;x++) {
				if(physicalMap[y][x]==1) {
					Rectangle rectOfLand = new Rectangle((int)(x*SIZE + getPosX()),
							(int)(y*SIZE + getPosY()),SIZE,SIZE);
					if(rect.intersects(rectOfLand)) return rectOfLand;
				}
			}
		}
		return null;
	}
	
	public Rectangle haveCollisionWithTop(Rectangle rect) {
		int posX1 = rect.x/SIZE -2; //limit for checking from the left of Character
		int posX2 = (rect.x + rect.width)/SIZE +2; //limit for checking from the right of Character
		if(posX1 < 0) posX1=0;
		if(posX2 >= physicalMap[0].length) posX2 = physicalMap[0].length - 1;
		int posY1 = (rect.y)/SIZE - 2;  //limit for checking from the top of Character
		int posY2 = rect.y/SIZE +2; //limit for checking from the bottom of Character
		if(posY1<0) posY1=0;
		if(posY2 >= physicalMap.length) posY2 = physicalMap.length-1;
		
		for(int y = posY2; y>= posY1;y--) {
			for(int x= posX1; x<=posX2;x++) {
				if(physicalMap[y][x]==1) {
					Rectangle rectOfTop = new Rectangle((int)(x*SIZE + getPosX()),
							(int)(y*SIZE + getPosY()),SIZE,SIZE);
					if(rect.intersects(rectOfTop)) return rectOfTop;
				}
			}
		}
		return null;
	}
	
	public Rectangle haveCollisionWithRightWall(Rectangle rect) {
		
		int posX1 = (rect.x + rect.height)/SIZE - 2; //limit for checking from the left of Character
		int posX2 = (rect.x + rect.height)/SIZE + 2;//limit for checking from the right of Character
		if(posX1 < 0) posX1=0;
		if(posX2 >= physicalMap[0].length) posX2 = physicalMap[0].length -1;
		
		int posY1 = rect.y/SIZE -2; //limit for checking from the top of Character
		int posY2 = (rect.y + rect.height)/SIZE +2; //limit for checking from the bottom of Character
		if(posY1 < 0) posY1=0;
		if(posY2 >= physicalMap.length) posY2 = physicalMap.length-1;
		
		for(int y = posY1; y <= posY2; y++) {
			for(int x= posX1; x<= posX2; x++) {
				if(physicalMap[y][x]==1) {
					Rectangle rectOfRightWall = new Rectangle((int)(x*SIZE + getPosX()),
							(int)(y*SIZE + getPosY()),SIZE,SIZE);
					if(rect.intersects(rectOfRightWall)) return rectOfRightWall;
				}
			}
		}
		return null;
	}
	
public Rectangle haveCollisionWithLeftWall(Rectangle rect) {
		
		int posX1 = rect.x/SIZE - 2; //limit for checking from the left of Character
		int posX2 = rect.x/SIZE + 2;//limit for checking from the right of Character
		if(posX1 < 0) posX1=0;
		if(posX2 >= physicalMap[0].length) posX2 = physicalMap[0].length - 1;
		
		int posY1 = rect.y/SIZE -2; //limit for checking from the top of Character
		int posY2 = (rect.y + rect.height)/SIZE +2; //limit for checking from the bottom of Character
		if(posY1 < 0) posY1=0;
		if(posY2 >= physicalMap.length) posY2 = physicalMap.length;
		
		for(int y = posY1; y < posY2; y++) {
			for(int x= posX2; x>= posX1; x--) {
				if(physicalMap[y][x]==1) {
					Rectangle rectOfRightWall = new Rectangle((int)(x*SIZE + getPosX()),
							(int)(y*SIZE + getPosY()),SIZE,SIZE);
					if(rect.intersects(rectOfRightWall)) return rectOfRightWall;
				}
			}
		}
		return null;
	}
	
}
