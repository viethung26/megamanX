package com.hung.gameobject;

import java.awt.Graphics2D;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ParticularObjectManager {

	protected List<ParticularObject> particularObjects;
	private GameWorld gameWorld;
	
	public ParticularObjectManager(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
		particularObjects = Collections.synchronizedList(new LinkedList<ParticularObject>());
	}
	
	public void addObject(ParticularObject particularObject) {
		synchronized(particularObjects) {
			particularObjects.add(particularObject);
		}
	}
	
	public void removeObject(ParticularObject particularObject) {
		synchronized(particularObjects) {
			for(int i=0;i<particularObjects.size();i++) {
				if(particularObjects.get(i) == particularObject) {
					particularObjects.remove(i);
				}
			}
		}
	}
	
	public ParticularObject getCollisionWithEnemy(ParticularObject object) {
		synchronized (particularObjects) {
			for(int i=0; i<particularObjects.size(); i++) {
				ParticularObject objectInList = particularObjects.get(i);
				if(object.getTeamType()!=objectInList.getTeamType() && 
						objectInList.getBoundforCollisionWithEnemy().intersects(object.getBoundforCollisionWithEnemy()))
					return objectInList;
			}
			return null;
		}
	}
	public void updateObject() {
		synchronized(particularObjects) {
			for(int i=0; i<particularObjects.size(); i++) {
				ParticularObject object = particularObjects.get(i);
				if(!object.isObjectOutOfCameraView()) object.update();
				if(object.getState() == ParticularObject.DEATH) particularObjects.remove(i);

			}
		}
	}
	
	public void draw(Graphics2D g2) {
		synchronized(particularObjects) {
			for(ParticularObject object: particularObjects) {
				if(!object.isObjectOutOfCameraView()) object.draw(g2);
			}
		}
	}
	public GameWorld getGameWorld() {
		return gameWorld;
	}
}
