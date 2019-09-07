package com.hung.gameobject;

import java.applet.AudioClip;
import java.awt.*;
import java.awt.image.BufferedImage;

import com.hung.effect.CacheDataLoader;
import com.hung.effect.FrameImage;
import com.hung.userinterface.GameFrame;

public class GameWorld {

	private BufferedImage bufferedImage;

	public Megaman megaman;
	public PhysicalMap physicalMap;
	public BackgroundMap backgroundMap;
	public Camera camera;
	public BulletManager bulletManager;
	public ParticularObjectManager particularObjectManager;
	public FinalBoss finalBoss;
	public AudioClip backgroundSound;

	public static final int finalBossX = 3600;

	public static final int PAUSEGAME = 0;
	public static final int TUTORIAL = 1;
	public static final int GAMEPLAY = 2;
	public static final int GAMEOVER = 3;
	public static final int GAMEWIN = 4;

	public static final int INTROGAME = 0;
	public static final int MEETFINALBOSS = 1;

	public int openIntroGameY = 0;
	public int state = PAUSEGAME;
	public int previousState = state;
	public int tutorialState = INTROGAME;

	public int storyTutorial = 0;
	public String[] texts1 = new String[4];

	public String textTutorial;
	public int currentSize = 1;

	private boolean finalbossTrigger = true;
	ParticularObject boss;

	FrameImage avatar = CacheDataLoader.getInstance().getFrameImage("avatar");

	private int numberOfLife = 3;
	
	public GameWorld() {

		texts1[0] = "We are heros, and our mission is protecting our Home\nEarth....";
		texts1[1] = "There was a Monster from University on Earth in 10 years\n"
				+ "and we lived in the scare in that 10 years....";
		texts1[2] = "Now is the time for us, kill it and get freedom!....";
		texts1[3] = "      LET'S GO!.....";
		textTutorial = texts1[0];

		bufferedImage = new BufferedImage(GameFrame.MASTER_WIDTH, GameFrame.MASTER_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		physicalMap = new PhysicalMap(0,0,0,0,this);
		backgroundMap = new BackgroundMap(0, 0, 0, 0, this);
		backgroundSound = CacheDataLoader.getInstance().getSound("bgmusic");
		camera = new Camera(0,0,GameFrame.MASTER_WIDTH,GameFrame.MASTER_HEIGHT,this);

		particularObjectManager = new ParticularObjectManager(this);
		bulletManager = new BulletManager(this);

		megaman = new Megaman(400,400,this);
		particularObjectManager.addObject(megaman);
		initEnemies();
	}

	public void initEnemies(){
		ParticularObject redeye = new RedEyeDevil(1250, 410, this);
		redeye.setDirection(ParticularObject.DIR_LEFT);
		redeye.setTeamType(ParticularObject.ENEMY_TEAM);
		particularObjectManager.addObject(redeye);

		ParticularObject smallRedGun = new SmallRedGun(1600, 180, this);
		smallRedGun.setDirection(ParticularObject.DIR_LEFT);
		smallRedGun.setTeamType(ParticularObject.ENEMY_TEAM);
		particularObjectManager.addObject(smallRedGun);

		ParticularObject darkraise = new DarkRaise(2000, 200, this);
		darkraise.setTeamType(ParticularObject.ENEMY_TEAM);
		particularObjectManager.addObject(darkraise);

		ParticularObject darkraise2 = new DarkRaise(2800, 350, this);
		darkraise2.setTeamType(ParticularObject.ENEMY_TEAM);
		particularObjectManager.addObject(darkraise2);

		ParticularObject robotR = new RobotR(900, 400, this);
		robotR.setTeamType(ParticularObject.ENEMY_TEAM);
		particularObjectManager.addObject(robotR);

		ParticularObject robotR2 = new RobotR(3400, 350, this);
		robotR2.setTeamType(ParticularObject.ENEMY_TEAM);
		particularObjectManager.addObject(robotR2);


		ParticularObject redeye2 = new RedEyeDevil(2500, 500, this);
		redeye2.setDirection(ParticularObject.DIR_LEFT);
		redeye2.setTeamType(ParticularObject.ENEMY_TEAM);
		particularObjectManager.addObject(redeye2);

		ParticularObject redeye3 = new RedEyeDevil(3450, 500, this);
		redeye3.setDirection(ParticularObject.DIR_LEFT);
		redeye3.setTeamType(ParticularObject.ENEMY_TEAM);
		particularObjectManager.addObject(redeye3);

		ParticularObject redeye4 = new RedEyeDevil(500, 1190, this);
		redeye4.setDirection(ParticularObject.DIR_RIGHT);
		redeye4.setTeamType(ParticularObject.ENEMY_TEAM);
		particularObjectManager.addObject(redeye4);


		ParticularObject darkraise3 = new DarkRaise(750, 650, this);
		darkraise3.setTeamType(ParticularObject.ENEMY_TEAM);
		particularObjectManager.addObject(darkraise3);

		ParticularObject robotR3 = new RobotR(1500, 1150, this);
		robotR3.setTeamType(ParticularObject.ENEMY_TEAM);
		particularObjectManager.addObject(robotR3);



		ParticularObject smallRedGun2 = new SmallRedGun(1700, 980, this);
		smallRedGun2.setDirection(ParticularObject.DIR_LEFT);
		smallRedGun2.setTeamType(ParticularObject.ENEMY_TEAM);
		particularObjectManager.addObject(smallRedGun2);
	}
	
	public void update() {
		switch(state){
			case PAUSEGAME:

				break;
			case TUTORIAL:
				TutorialUpdate();

				break;
			case GAMEPLAY:
				particularObjectManager.updateObject();
				bulletManager.updateObject();

				physicalMap.update();
				camera.update();


				if(megaman.getPosX() > finalBossX && finalbossTrigger){
					finalbossTrigger = false;
					switchState(TUTORIAL);
					tutorialState = MEETFINALBOSS;
					storyTutorial = 0;
					openIntroGameY = 550;

					boss = new FinalBoss(finalBossX + 700, 460, this);
					boss.setTeamType(ParticularObject.ENEMY_TEAM);
					boss.setDirection(ParticularObject.DIR_LEFT);
					particularObjectManager.addObject(boss);

				}

				if(megaman.getState() == ParticularObject.DEATH){
					numberOfLife --;
					if(numberOfLife >= 0){
						megaman.setBlood(100);
						megaman.setPosY(megaman.getPosY() - 50);
						megaman.setState(ParticularObject.NOBEHURT);
						particularObjectManager.addObject(megaman);
					}else{
						switchState(GAMEOVER);
						backgroundSound.stop();
					}
				}
				if(!finalbossTrigger && boss.getState() == ParticularObject.DEATH)
					switchState(GAMEWIN);

				break;
			case GAMEOVER:

				break;
			case GAMEWIN:

				break;
		}
	}
	
	public void render() {

		Graphics2D g2 = (Graphics2D) bufferedImage.getGraphics();

		if(g2!=null){

			// NOTE: two lines below make the error splash white screen....
			// need to remove this line
			//g2.setColor(Color.WHITE);
			//g2.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);


			//physicalMap.draw(g2);

			switch(state){
				case PAUSEGAME:
					g2.setColor(Color.BLACK);
					g2.fillRect(0, 0, GameFrame.MASTER_WIDTH, GameFrame.MASTER_HEIGHT);
					g2.setColor(Color.WHITE);
					g2.drawString("PRESS ENTER TO CONTINUE", 400, 300);
					break;
				case TUTORIAL:
					backgroundMap.draw(g2);
					if(tutorialState == MEETFINALBOSS){
						particularObjectManager.draw(g2);
					}
					TutorialRender(g2);

					break;
				case GAMEWIN:
				case GAMEPLAY:
					backgroundMap.draw(g2);
					particularObjectManager.draw(g2);
					bulletManager.draw(g2);

					g2.setColor(Color.GRAY);
					g2.fillRect(19, 59, 102, 22);
					g2.setColor(Color.red);
					g2.fillRect(20, 60, megaman.getBlood(), 20);

					for(int i = 0; i < numberOfLife; i++){
						g2.drawImage(CacheDataLoader.getInstance().getFrameImage("hearth").getImage(), 20 + i*40, 18, null);
					}


					if(state == GAMEWIN){
						g2.drawImage(CacheDataLoader.getInstance().getFrameImage("gamewin").getImage(), 300, 300, null);
					}

					break;
				case GAMEOVER:
					g2.setColor(Color.BLACK);
					g2.fillRect(0, 0, GameFrame.MASTER_WIDTH, GameFrame.MASTER_HEIGHT);
					g2.setColor(Color.WHITE);
					g2.drawString("GAME OVER!", 450, 300);
					break;

			}


		}
	}
	public BufferedImage getBufferedImage(){
		return bufferedImage;
	}

	public void switchState(int state){
		previousState = this.state;
		this.state = state;
	}

	private void TutorialUpdate(){
		switch(tutorialState){
			case INTROGAME:

				if(storyTutorial == 0){
					if(openIntroGameY < 450) {
						openIntroGameY+=4;
					}else storyTutorial ++;

				}else{

					if(currentSize < textTutorial.length()) currentSize++;
				}
				break;
			case MEETFINALBOSS:
				if(storyTutorial == 0){
					if(openIntroGameY >= 450) {
						openIntroGameY-=1;
					}
					if(camera.getPosX() < finalBossX){
						camera.setPosX(camera.getPosX() + 2);
					}

					if(megaman.getPosX() < finalBossX + 150){
						megaman.setDirection(ParticularObject.DIR_RIGHT);
						megaman.run();
						megaman.update();
					}else{
						megaman.stopRun();
					}

					if(openIntroGameY < 450 && camera.getPosX() >= finalBossX && megaman.getPosX() >= finalBossX + 150){
						camera.lock();
						storyTutorial++;
						megaman.stopRun();
						physicalMap.physicalMap[14][120] = 1;
						physicalMap.physicalMap[15][120] = 1;
						physicalMap.physicalMap[16][120] = 1;
						physicalMap.physicalMap[17][120] = 1;

						backgroundMap.map[14][120] = 17;
						backgroundMap.map[15][120] = 17;
						backgroundMap.map[16][120] = 17;
						backgroundMap.map[17][120] = 17;
					}

				}else{

					if(currentSize < textTutorial.length()) currentSize++;
				}
				break;
		}
	}

	private void drawString(Graphics2D g2, String text, int x, int y){
		for(String str : text.split("\n"))
			g2.drawString(str, x, y+=g2.getFontMetrics().getHeight());
	}

	private void TutorialRender(Graphics2D g2){
		switch(tutorialState){
			case INTROGAME:
				int yMid = GameFrame.MASTER_HEIGHT/2 - 15;
				int y1 = yMid - GameFrame.MASTER_HEIGHT/2 - openIntroGameY/2;
				int y2 = yMid + openIntroGameY/2;

				g2.setColor(Color.BLACK);
				g2.fillRect(0, y1, GameFrame.MASTER_WIDTH, GameFrame.MASTER_HEIGHT/2);
				g2.fillRect(0, y2, GameFrame.MASTER_WIDTH, GameFrame.MASTER_HEIGHT/2);

				if(storyTutorial >= 1){
					g2.drawImage(avatar.getImage(), 600, 350, null);
					g2.setColor(Color.BLUE);
					g2.fillRect(280, 450, 350, 80);
					g2.setColor(Color.WHITE);
					String text = textTutorial.substring(0, currentSize - 1);
					drawString(g2, text, 290, 480);
				}

				break;
			case MEETFINALBOSS:
				yMid = GameFrame.MASTER_HEIGHT/2 - 15;
				y1 = yMid - GameFrame.MASTER_HEIGHT/2 - openIntroGameY/2;
				y2 = yMid + openIntroGameY/2;

				g2.setColor(Color.BLACK);
				g2.fillRect(0, y1, GameFrame.MASTER_WIDTH, GameFrame.MASTER_HEIGHT/2);
				g2.fillRect(0, y2, GameFrame.MASTER_WIDTH, GameFrame.MASTER_HEIGHT/2);
				break;
		}
	}
}
