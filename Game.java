/**********************************************************************************
* Name:          Blobs Tower Defense                    
                                                                   
* Author:        Tyler Lemarquand          '      `.
                 Harris Zheng                                  
                                                            
* Date:          April 18, 2017                              
                                                                
* Description:   A simple tower defense game consisting of blobs and towers!
**********************************************************************************/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileReader;
//import sun.audio.*;

public class Game extends Canvas implements MouseListener, MouseMotionListener {

	private BufferStrategy strategy; // take advantage of accelerated graphics
	private boolean waitingForStartButton = true; // true if game held up until start button is pressed

	private boolean gameRunning = true;				// true if game is currently running
	private ArrayList<Entity> entities = new ArrayList<Entity>(); 	// list of entities in game
	
	private ArrayList<Entity> removeEntities = new ArrayList<Entity>(); // list of entities  to remove this loop


	private String message = "Press the Start Button to Start!"; // message to display while waiting for a key press
	
	private String playAgain = "Press the Start Button to Play Again!"; // message to display to ask user to play again

	protected int[][] grid = new int[12][16]; // stores the type/ID of terrain
	
	public static final int screenWidth = 1210; // screen width
	public static final int screenHeight = 720; // screen height
	public static final int tileDimension = 60; // height and width of each game tile

	int mouseX = 0; // mouse position X
	int mouseY = 0; // mouse position Y

	private int fps; // frames per second
	private int frameRate; // frame rate
	
	public int coins = 450; // initial amount of money at beginning of game
	public int life =   10; // amount of lives player has at beginning of game
	
	protected int monsterCount = 0; // counts numbers of monsters on terrain
	private int previousMonsterCount = 0; // amount of monsters before a monster is removed 
	private int afterMonsterCount = 0; // amount of monsters after a monster is removed
	private int counter = 0; // ensures monsterCount works properly
	private int monsterID = 0; // determines the type of monster
	private int wave = 1; // monster waves
	private int waveCount = 0; // tracks how many monsters are produced before reaching the next wave
	private boolean monsterProduction = true; // makes sure no more monsters are produced after all waves are run
	protected int healthDepletion = 0; // determines how much health the enemy will 
	                                   // lose at the instance of a tower firing

	protected int reward = 0; // amount of money received for each kill of monster
	protected boolean removed = false; // determines if monster is removed from arraylist or not
	protected boolean earnCoins = true; // determines if reward is given for removed monster
	
	protected int towerType = -1; // type of tower
	protected boolean fire = false; // checks if tower fires
	private boolean[] hasTower = new boolean[192]; // checks if tile already has a tower in place
	private boolean placingTower = false; // checks Whether or not a tower is currently
											// being dragged/placed

	boolean helpShown = false;			// True if the help screen is up	
	private boolean endGame = false;	// True after a player wins or loses
	boolean selling = false;			// True if currently dragging sell button
	private boolean win = false;		// True if all waves are defeated
	private boolean lose = false;		// True if player runs out of lives
	

	Image start = new ImageIcon("interface/playbutton3.png").getImage(); // start button image
	Image heart = new ImageIcon("interface/heart.png").getImage(); // heart/lives image
	Image coin = new ImageIcon("interface/coin.png").getImage(); // coin/money image
	Image tower1 = new ImageIcon("tower/tower1.png").getImage(); // tower Lv1 image
	Image tower2 = new ImageIcon("tower/tower2.png").getImage(); // tower Lv2 image
	Image tower3 = new ImageIcon("tower/tower3.png").getImage(); // tower Lv3 image
	Image title = new ImageIcon("interface/Blobs.png").getImage(); // Title image
	Image help = new ImageIcon("interface/help.png").getImage(); // help button image
	Image startScreen = new ImageIcon("interface/blobstart.png").getImage(); // starting screen image
	Image sell = new ImageIcon("interface/sell.png").getImage(); // sell button image

	// Music Stuff
	//private AudioPlayer MGP = AudioPlayer.player;
	//private AudioStream BGMLose = null;
	//private AudioStream BGMGame = null;
	//private AudioStream BGMWin = null;
	//private AudioStream BGMStart = null;
	//private AudioStream hey = null;
	
	private int trackMusic = 0; // makes sure audiostreams only spawn once

	
	/* Main Program */
	public static void main(String[] args) {
		new Game();
	} // main

	
	/*
	 * Construct our game and set it running.
	 */

	public Game() {

		// Register for mouse events on the panel.
		addMouseListener(this);
		addMouseMotionListener(this);

		// create a frame to contain game
		JFrame container = new JFrame("Blobs Tower Defence");

		// get hold the content of the frame
		JPanel panel = (JPanel) container.getContentPane();

		// set up the resolution of the game
		panel.setPreferredSize(new Dimension(screenWidth, screenHeight));
		panel.setLayout(null);

		// set up canvas size (this) and add to frame
		setBounds(0, 0, screenWidth, screenHeight);
		panel.add(this);

		// Tell AWT not to bother repainting canvas since that will
		// be done using graphics acceleration
		setIgnoreRepaint(true);

		// make the window visible
		container.pack();
		container.setResizable(false);
		container.setVisible(true);

		// if user closes window, shutdown game and jre
		container.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			} // windowClosing
		});

		// request focus so key events are handled by this canvas
		requestFocus();

		// create buffer strategy to take advantage of accelerated graphics
		createBufferStrategy(2);
		strategy = getBufferStrategy();

		// start the game
		gameLoop();

		

	} // constructor
	
	/* Plays music/sound effects at set times */
	private void music() {
		Thread sound;
		sound = new Thread() {

			public void run() {
				try {
					//BGMStart = new AudioStream(new FileInputStream("music/getlucky.wav"));
					//BGMGame = new AudioStream(new FileInputStream("music/harderbetterfasterstronger.wav"));
//					BGMLose = new AudioStream(new FileInputStream("music/youdied.wav"));
//					BGMWin = new AudioStream(new FileInputStream("music/youalive.wav"));

//					if (!endGame && !waitingForStartButton) {
//						MGP.stop(hey);
//						hey = BGMGame;
//						MGP.start(BGMGame);
//					} else if (lose && waitingForStartButton) {
//						MGP.stop(hey);
//						hey = BGMLose;
//						MGP.start(BGMLose);
//					} else if (win && waitingForStartButton) {
//						MGP.stop(hey);
//						hey = BGMWin;
//						MGP.start(BGMWin);
//						win = false;
//					} else if (!endGame && waitingForStartButton) {
//						MGP.stop(hey);
//						hey = BGMStart;
//						MGP.start(BGMStart);
//					} // if

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				} // try

			} // run
		}; // Thread
        
		sound.start();
        
	} // music

	/* Constructs the interface of the game */
	private void setInterface(Graphics g) {
		String image = "";
		String displayCoin;			
		String displayLife;			
		String displayWave;
		int y = 0;
		int x = 0;
		int row;
		int col;
		int id;
		int height = 50;
		int width = 250;
		int ovalHeight = height * 3;
		int interfaceX = screenWidth - 250;

		// set up the grid and place tiles
		try {
			Scanner scan = new Scanner(new FileReader("placement.txt"));
			for (row = 0; row < 12; row++) {
				y = row * tileDimension;
				for (col = 0; col < 16; col++) {
					x = col * tileDimension;
					id = Interface.getID(scan);
					grid[row][col] = id;

					switch (id) {
					case 0:
						image = "background/grass.jpg";
						break;

					case 1:
						image = "background/rock.jpg";
						break;

					default:
						break;
					} // switch

					Entity background = new Interface(this, image, x, y);
					background.draw(g);

				} // for
			} // for
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		} // try
		
		// draw the side menu
		g.setColor(Color.yellow);
		g.fillRect(interfaceX, 0, width, 150);
		g.setColor(Color.orange);
		g.fillRect(interfaceX, 150, width, height);
		g.fillRect(interfaceX, 200, width, height);
		g.setColor(Color.black);
		g.drawRect(interfaceX, 250, width, 100);
		g.drawRect(interfaceX, 350, width, 100);
		g.drawRect(interfaceX, 450, width, 100);
		g.drawRect(interfaceX, 250, width, 720);
		g.setColor(Color.pink);
		g.fillRect(interfaceX, 260, width, 80);
		g.fillRect(interfaceX, 360, width, 80);
		g.fillRect(interfaceX, 460, width, 80);
		g.setColor(Color.green);
		g.fillOval(interfaceX, screenHeight - ovalHeight, 250, ovalHeight);
		g.drawImage(start, 1035, 590, null);
		g.drawImage(heart, 970, 170, null);
		g.drawImage(coin, 970, 210, null);
		g.drawImage(tower1, 970, 270, null);
		g.drawImage(tower2, 970, 370, null);
		g.drawImage(tower3, 975, 470, null);
		g.drawImage(title, 960, 0, null);
		g.drawImage(help, 960, 0, null);
		g.drawImage(sell, 965, 40, null);

		Font font = new Font("Verdana", Font.BOLD, 36);
		g.setColor(Color.black);
		g.setFont(font);
			
		displayLife = Integer.toString(life);
		displayCoin = Integer.toString(coins);
	
		g.drawString(displayLife, interfaceX + tileDimension, 195);
		g.drawString(displayCoin, interfaceX + tileDimension, 190 + height);		
		
		font = new Font("Verdana", Font.BOLD, 18);
		g.setFont(font);
		g.setColor(Color.black);
		g.drawString("Tower Lv1", interfaceX + tileDimension + 20, 290);
		g.drawString("Tower Lv2", interfaceX + tileDimension + 20, 390);
		g.drawString("Tower Lv3", interfaceX + tileDimension + 20, 490);
		g.drawImage(coin, interfaceX + tileDimension + 20, 300, null);
		g.drawImage(coin, interfaceX + tileDimension + 20, 400, null);
		g.drawImage(coin, interfaceX + tileDimension + 20, 500, null);
		g.drawString("150", interfaceX + tileDimension + 60, 325);
		g.drawString("300", interfaceX + tileDimension + 60, 425);
		g.drawString("500", interfaceX + tileDimension + 60, 525);
		
		font = new Font("Verdana", Font.BOLD, 36);
		g.setFont(font);


	} // setInterface

	/*
	 * gameLoop 
	 * input: none 
	 * output: none 
	 * purpose: main game loop. run throughout gameplay. 
	 * responsible for: - calculating the speed of the game
	 * 					- loop for update moves 
	 * 					- moving the game entities 
	 * 					- drawing the screen
	 * contents (entities, text, money) - updating game events - checking input
	 */

	public void gameLoop() {
		long lastLoopTime = System.currentTimeMillis();
	
		// checks what music to be played
		//music();
		
		// keep loop running until game ends
		while (gameRunning) {
			
			// calculate time since last update, used to move and spawn entities
			long delta = System.currentTimeMillis() - lastLoopTime;
			lastLoopTime = System.currentTimeMillis();
			
			// set background to grey
			Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
			g.setColor(Color.gray);
			g.fillRect(0, 0, screenWidth, screenHeight);
			
			// draw layout of the game
			setInterface(g);

			// add money for each enemy kill 
			if (afterMonsterCount < previousMonsterCount && earnCoins && afterMonsterCount != 0 && removed){
				coins += reward; 
				removed = false;
			} // if
			
			// spawn monsters after player clicks on start button 
			if (!waitingForStartButton) {
				spawnMonsters(delta);
				endGame = false;
				
				if (trackMusic == 0){
					//music();
					trackMusic = 1;
				} // if
				
			} // if
			
			// Display wave number
			Font font = new Font("Verdana", Font.BOLD, 24);
			g.setFont(font);
			g.drawString("Wave:" + wave, 10, 20);
			font = new Font("Verdana", Font.BOLD, 36);
			g.setFont(font);

			// move each entity
			for (int i = 0; i < entities.size(); i++) {
				Entity entity = (Entity) entities.get(i);
				entity.move(delta);
			} // for

			// draw each entity
			for (int i = 0; i < entities.size(); i++) {
				Entity entity = (Entity) entities.get(i);
				entity.draw(g);
			} // for

			
			// Check if a tower is being placed, and draw the tower as it is dragged 
			if (placingTower) {
				if (towerType == 1) {
					g.drawImage(tower1, mouseX - 30, mouseY - 30, null);
				} // if

				if (towerType == 2) {
					g.drawImage(tower2, mouseX - 30, mouseY - 30, null);
				} // if

				if (towerType == 3) {
					g.drawImage(tower3, mouseX - 30, mouseY - 30, null);
				} // if

			} // if
			
			// If dragging sell button, draw sell button on mouse
			if (selling) {
				g.drawImage(sell, mouseX - 8, mouseY - 8, null);
			} // if
			
			// brute force collisions, compare every entity against every other entity.
			// if collisions are detected, notify both entities.
			// draw shooting graphics and determine the reward the enemy that is
			// being shot at gives
			for (int i = 0; i < entities.size(); i++) {
				for (int j = 0; j < entities.size(); j++) {
					Entity me = (Entity) entities.get(i);
					Entity them = (Entity) entities.get(j);

					fire = me.collidesWith(them);

					// If enemy is first within tower range, fire at it
					if (me instanceof TowerEntity) {
						healthDepletion = me.getDamage();
						if (them instanceof EnemyEntity) {
							if (fire) {
								me.collidedWith(them);
								them.collidedWith(me);
								getMoney(them.getID());
								g.setStroke(new BasicStroke(getStroke(me.getID())));
								g.setColor(Color.yellow);
								g.drawLine((int) me.getX() + tileDimension / 2, (int) me.getY(),
										(int) them.getX() + tileDimension / 2, (int) them.getY() + tileDimension / 2);
								j = entities.size() - 1;
							} // if
						} // if
					} // if

				} // for j
			} // for i

			// check if player wins game 
			if (waveCount == 140 && monsterCount == 0){
					notifyWin();
					if (trackMusic == 1){
						//music();
						trackMusic = 0;
					} // if
			} // if
			

			// remove dead entities
			entities.removeAll(removeEntities);
			removeEntities.clear();
			
			counter = 0;


			// if waiting for start button, draw this message
			if (waitingForStartButton) {
				Color transparentWhite = new Color(50, 150, 255, 127);
				g.drawImage(startScreen, 0, 0, null);
				g.setColor(transparentWhite);
				g.fillRect(90, 250, 780, 140);
				g.setColor(Color.black);
				g.drawString(message, (screenWidth - 250 - g.getFontMetrics().stringWidth(message)) / 2, 300);
				if (endGame){
					g.setColor(Color.black);
					g.drawString(playAgain, (screenWidth - 250 - g.getFontMetrics().stringWidth(playAgain)) / 2, 360);
					
				} // if
		
			} // if

			// Draw help screen if needed
			if (helpShown) {
				int stroke = 15;
				int textPos = 317;
				g.setColor(Color.white);
				g.fillRect(screenWidth - 908, 0, 650, 220);
				g.setColor(Color.pink);
				g.setStroke(new BasicStroke(stroke));
				g.drawRect(screenWidth - 908 , 0, 650, 220);
				g.setColor(Color.black);
				font = new Font("Verdana", Font.BOLD, 18);
				g.setFont(font);
				g.drawString("Drag and drop towers to place them. ", textPos, 20);
				g.drawString("Drag and drop money symbol on any tower to sell it for 100!", textPos, 40);
				g.drawString("Towers will shoot at enemies in a 3x3 range.", textPos, 60);
				g.drawString("More expensive towers will do more damage.", textPos, 80);
				g.drawString("Killing enemies will give you money:", textPos, 100);
				g.drawString("Green Blob: +10  Blue Blob: +20  Red Blob: +30", textPos, 120);
				g.drawString("If an enemy reaches the end of the path, you will lose a life.", textPos, 140);
				g.drawString("If you run out of lives, you lose!", textPos, 160);
				g.drawString("If you survive all 4 waves, you win!", textPos, 180);
				g.drawString("Click the question mark to close or open these instructions.", textPos, 200);
			} // if

			// clear graphics and flip buffer
			g.dispose();
			strategy.show();

			// slow game down to a reasonable refresh rate
			try {
				Thread.sleep(10);
			} catch (Exception e) {
			} // try

		} // while

	} // gameLoop
	
	/* Determine reward of monsters if they are killed */
	private void getMoney(int monsterID) {
		switch (monsterID){
			case 1: reward = 10;
					break;

			case 2: reward = 20;
					break;

			case 3: reward = 30;
					break;
		} // switch
	} // getMoney
	
	/* Determine the thickness of shots from towers 
	   more powerful and expensive towers produce thicker lines */
	private int getStroke(int towerID){
		int stroke = 0;
		switch (towerID){
		case 1: stroke = 1;
				break;
				
		case 2: stroke = 3;
				break;
				
		case 3: stroke = 5;
				break;
		} // switch
		
		return stroke;
	} // getStroke
	
	/* Spawn monsters */
	private void spawnMonsters(long delta) {

		String string = "";
		frameRate += delta;
		
		// generate monsters at intervals 
		if (frameRate > fps && monsterProduction) {
			monsterCount++;
			waveCount++;
			
			// track wave number and the rate of monster production
			if (waveCount > 0 && waveCount < 20) {
				fps = 1000;
			} else if (waveCount > 20 && waveCount < 50) {
				wave = 2;
				fps = 2000;
			} else if (waveCount > 50 && waveCount < 90) {
				wave = 3;
				fps = 1500;
			} else if (waveCount > 90 && waveCount < 140) {
				wave = 4;
				fps = 750;
			} // if

			// tougher monsters are more likely to be generated as wave increases
			switch (wave) {
			case 1:
				monsterID = 1;
				break;

			case 2:
				monsterID = (int) Math.floor(Math.random() * 1 + 1.8);
				break;

			case 3:
				monsterID = (int) Math.floor(Math.random() * 2 + 1.3);
				break;

			case 4:
				monsterID = (int) Math.floor(Math.random() * 2 + 1.7);
				break;

			} // switch
			
			// associates image with corresponding monster type
			switch (monsterID) {
			case 1:
				string = "sprite/ll.png";
				break;

			case 2:
				string = "sprite/mm.png";
				break;

			case 3:
				string = "sprite/hh.png";
				break;

			default:
				string = "sprite/ll.png";
				break;

			} // switch
			Entity monster = new EnemyEntity(this, string, 120, -60, monsterID);
			entities.add(monster);
			
			
			previousMonsterCount = monsterCount;
			frameRate = 0;
		} // if
		
		// 10 second pause in production of monsters after each wave completion
		// however, completion of last wave ceases monster production
		switch (waveCount) {
		case 20:
			fps = 10000;
			break;

		case 50:
			fps = 10000;
			break;

		case 90:
			fps = 10000;
			break;

		case 140:
			monsterProduction = false; 
			break;
		
		default: break;
			
		} // switch
		
	} // spawnMonsters

	/* Notification that the player has died */
	public void notifyDeath(){
		message = "You have died! You just got blob-pied!";
		waitingForStartButton = true;
		endGame = true;
		lose = true;
		entities.clear();
		//music();

	} // notifyDeath

	/* Notification that the player has won */
	public void notifyWin(){
		message = "Victory! Tastes just like sweet hickory!";
		waitingForStartButton = true;
		win = true;
		endGame = true;
		entities.clear();
	} // notifyWin

	/* Remove an entity from the game. It will no longer be called or moved */
	public void removeEntity(Entity entity) {
		removeEntities.add(entity);
		removed = true;
		
		// ensures monsterCount only decreases by one 
		// even in situations when multiple towers knock out monster simultaneously
		if (counter == 0){
			monsterCount--;
			counter++;		
		} // if
		
		afterMonsterCount = monsterCount;
	} // removeEntity

	/* starts a new game, clears and resets data */
	private void startGame() {
		fps = 1000;
		monsterCount = 0;
		waveCount = 0;
		towerType = -1; 
		monsterID = 0; 
		wave = 1; 
		fire = false; 
		hasTower = new boolean[192]; 
		placingTower = false; 
		coins = 450; 
		life = 10; 
		trackMusic = 0;
		helpShown = false;
		endGame = false;
		helpShown = false;
		monsterProduction = true;
		win = false;
		lose = false;
	} // startGame

	/* Detects mouse pressed events and acts accordingly */
	public void mousePressed(MouseEvent e) {
		mouseX = (int) e.getPoint().getX();
		mouseY = (int) e.getPoint().getY();

		// If a tower button is pressed, allow the player to drag it's image into position
		if (e.getX() > (screenWidth - 250) && e.getX() < (screenWidth)) {

			if (e.getY() > 260 && e.getY() < 310) {
				placingTower = true;
				towerType = 1;
			} // if

			if (e.getY() > 360 && e.getY() < 410) {
				placingTower = true;
				towerType = 2;
			} // if

			if (e.getY() > 460 && e.getY() < 510) {
				placingTower = true;
				towerType = 3;
			} // if

		} // if

		// If sell button is dragged onto a tower, sell the tower
		if (e.getX() > (screenWidth - 250) && e.getX() < (screenWidth - 220)) {
			
			if (e.getY() > 40 && e.getY() < 70) {
				selling = true;
			} // if
			
		} // if

	} // mousePressed

	/* Detects mouse released events and acts accordingly */
	public void mouseReleased(MouseEvent e) {

		// checks if releasing mouse in a valid area 
		if (mouseX >= 0 && mouseX <= 960 && mouseY >= 0 && mouseY <= 720) {
			
			// add a tower to terrain if valid
			if(placingTower) {
				addTower(mouseX, mouseY);
			} // if
			
			// sell tower if valid
			if(selling) {
				sellTower(mouseX, mouseY);
			} // if
			
		} // if

		placingTower = false;
		selling = false;
		
	} // mouseReleased

	/* Places a tower on the map and spends coins */
	public void addTower(int x, int y) {
		String imageType = "";
		int calculation = ((x / tileDimension) + 1) + (y / tileDimension) * 16;
		int beforeSpending = coins;
		int number = 0;

		for (int i = 0; i < 12; i++) {

			for (int j = 0; j < 16; j++) {
				number++;

				// Check if this tile already has a tower
				if (number == calculation) {
					if (grid[i][j] == 1 || hasTower[calculation] == true) {
						return;
					} // if

				} // if

			} // for

		} // for

		// Check what type of tower is being placed and act accordingly
		if (towerType == 1) {
			imageType = "tower/tower1.png";
			coins -= 150;
		} else if (towerType == 2) {
			imageType = "tower/tower2.png";
			coins -= 300;
		} else if (towerType == 3) {
			coins -= 500;
			imageType = "tower/tower3.png";
		} // if

		// Check if tower is affordable, and place it if it is
		if (coins >= 0) {
			Entity tower = new TowerEntity(this, imageType, x - x
					% tileDimension, y - y % tileDimension, towerType);
			entities.add(tower);
			hasTower[calculation] = true;
		} else {
			coins = beforeSpending;
		} // if

		// Reset towerType
		towerType = 0;

	} // addTower
	
	/* Removes tower from map and gives coins */
	public void sellTower(int x, int y) {
		String imageType = "";
		int calculation = ((x / tileDimension) + 1) + (y / tileDimension) * 16;
		int number = 0;

		for (int i = 0; i < 12; i++) {

			for (int j = 0; j < 16; j++) {
				number++;

				// Check if this tile already has a tower
				if (number == calculation) {
					if (grid[i][j] == 1 || hasTower[calculation] == false) {
						return;
					} // if
					hasTower[calculation] = false;
				} // if

			} // for

		} // for
		
		
		for (int i = 0; i < entities.size(); i++) {
			Entity me = (Entity) entities.get(i);

			if (me.x >= (int) mouseX - 60 && me.x <= (int) mouseX) {
				if (me.y >= (int) mouseY - 60 && me.y <= (int) mouseY) {

					if (me instanceof TowerEntity) {
						entities.remove(me);
						coins += 100;
					} // if
				} // if
			} // if

		} // for i
		
	} // sellTower


	/* Detects mouse entered events and acts accordingly */
	public void mouseEntered(MouseEvent e) {
	} // mouseEntered

	/* Detects mouse exit events and acts accordingly */
	public void mouseExited(MouseEvent e) {
	} // mouseExited

	/* Detects mouse click events and acts accordingly */
	public void mouseClicked(MouseEvent e) {
		
		// start game if start button is pressed
		if (e.getY() > (screenHeight - 150) && e.getX() > (screenWidth - 250)) {
			if (waitingForStartButton){
				startGame();
				waitingForStartButton = false;
			} // if
			
		} // if
		
		// show and hide help screen
		if (e.getX() > (screenWidth - 250) && e.getX() < (screenWidth - 220)) {

			if (e.getY() > 0 && e.getY() < 30) {

				if (helpShown) {
					helpShown = false;
				} else {
					helpShown = true;
				} // if

			} // if

		} // if
				
	} // mouseClicked

	// Detects mouse move events and acts accordingly
	public void mouseMoved(MouseEvent e) {
	} // mouseMoved

	// Detects mouse drag events and acts accordingly
	public void mouseDragged(MouseEvent e) {
		mouseX = (int) e.getPoint().getX();
		mouseY = (int) e.getPoint().getY();
	} // mouseDragged

} // Game
