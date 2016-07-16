package dyeHardProceduralAPI;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.Collectibles.*;
import dyehard.Collision.CollidableGameObject;
import dyehard.DyeHardGame;
import dyehard.UpdateManager;
//import dyehard.Collision.CollisionManager;
import dyehard.GameScreens.LogScreen;
import dyehard.GameScreens.StartScreen;
import dyehard.Resources.ConfigurationFileParser;
import dyehard.Ui.DyehardEndMenu;
import dyehard.Ui.DyehardMenuUI;
import dyehard.Ui.DyehardUI;
import dyehard.Util.Colors;
import dyehard.World.GameState;
import dyehard.Player.Hero;
import dyehard.Weapons.*;
import dyehard.World.WormHole;
//import dyehard.Updateable;

/**
 * @author vuochnin
 * @author Holden
 */
public class DHProceduralAPI extends DyeHardGame{
	
	private Hero hero;
	private EnemyGenerator enemyGenerator;
	private DyehardUI ui;
	
	private DyehardMenuUI menu;
	private DyehardEndMenu endMenu;
	private LogScreen start;			// "Click Anywhere to Start"
	
	
	
	/**
	 * @Override 
	 * Must override the initialize() method from the abstract super class, DyeHardGame
	 */
	public void initialize(){
		CollisionManager.register(this);
		
		
		window.requestFocusInWindow();
		setGoalDistance();
		buildGame();
		
		menu = new DyehardMenuUI();
		endMenu = new DyehardEndMenu();
		start = new LogScreen();
		
		
	}
	
	public void buildGame(){
//		startHero();
//		
//		setLivesTo(6);
//		displayScore(true);
//		
//		startDebrisSpawner(1.5f);
//		startDyePackSpawner();
//		startEnemySpawner();
//
//		spawnGates();
	}
	
	/**
	 * @Override 
	 * Must override the update() method from the abstract super class, DyeHardGame
	 */
	public void update(){
		
		UpdateManager.getInstance().update();
		CollisionManager.update();
		DebrisGenerator.update();
		DyePackGenerator.update();
		if(enemyGenerator != null)
			enemyGenerator.update();
		updateGame();
	}
	
	public void updateGame()
	{
//		heroFollowTheMouse();
//		
//		// TEST Change the weapon according to the keyboard inputs
//		if(isKeyboardLeftPressed()){
//			activateSpreadFireWeapon();
//		}
//		if(isKeyboardRightPressed()){
//			activateLimitedAmmoWeapon();
//		}
//		if(isKeyboardDownPressed()){
//			defaultWeapon();
//		}
//		if(isKeyboardButtonTapped(KeysEnum.p)){
//			increaseScoreBy(2);
//		}
//		if(isKeyboardButtonTapped(KeysEnum.E)){
//			spawnSingleEnemy("charger");			//TEST SpawnSingleEnemy()
//		}
//
//
//
//		if(repeatingTimer("powerup", 4))
//		{
//			spawnSinglePowerUp(100, 30);
//		}
//
//		// Fire the paint
//		if(isMouseLeftClicked() || isKeyboardSpacePressed()){
//			firePaint();
//		}
	}
	
	public void handleCollisions(String type1, String subtype1, int id1, String type2, String subtype2, int id2)
	{
		// User overrides this
	}
	
	/**
	 * Reports the number of objects in play
	 * @return
	 */
	public int objectCount()
	{
		return CollisionManager.objectCount();
	}
	
	/**
	 * presents the type of an object
	 * @param id
	 * @return
	 */
	public String getType(int id){
		return CollisionManager.getType(id);
	}
	/**
	 * Presents the subtype of an object
	 * @param id
	 * @return
	 */
	public String getSubtype(int id){
		return CollisionManager.getSubtype(id);
	}
	
//--------------------------------------------------------------------------------------------	
//--------------------- SOME POSSIBLE PROCEDURAL FUNCTIONS -----------------------------------
//--------------------------------------------------------------------------------------------
	
	
	/**
	 * Fire the current weapon
	 */
	public void firePaint(){					// Fire the paint
		hero.currentWeapon.fire();
	}
	
	/**
	 * Create new Hero object and set it to hero instance and 
	 * set the cursor to the center of that hero
	 */
	public void startHero(){					// Create new Hero
		hero = new Hero();
		enemyGenerator = new EnemyGenerator(hero);
		// TODO: Look into possibility of separating individual UI elements into functions
		ui = new DyehardUI(hero);
		
		// Move cursor to the center of the hero
		try{
			Robot robot = new Robot();
			
			robot.mouseMove(window.getLocationOnScreen().x + (int)(hero.center.getX() 
								* window.getWidth() / BaseCode.world.getWidth()), 
							window.getLocationOnScreen().y + window.getHeight() - (int)(hero.center.getX() 
								* window.getWidth() / BaseCode.world.getWidth()));
		} catch(AWTException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the current color of the DyePack the hero is carrying
	 * @return the hero's dyepack color
	 */
	public Color getHeroColor(){
		return hero.getColor();
	}
	
	/**
	 * Moves the hero to a specific position
	 * @param x the X-coordinate position
	 * @param y the Y-coordinate position
	 */
	public void moveTo(float x, float y){
		hero.moveTo(x, y);
	}
	
	/**
	 * Makes the hero follow the mouse movement
	 */
	public void heroFollowTheMouse(){
		moveTo(mousePositionX(), mousePositionY());
	}
	
	/**
	 * Moves the hero to the left direction
	 */
	public void moveLeft()
	{
		Vector2 result = hero.center;
		result.add( new Vector2(-1,0));
		moveTo(result.getX(), result.getY());
	}
	
	/**
	 * Moves the hero to the right direction
	 */
	public void moveRight()
	{
		Vector2 result = hero.center;
		result.add( new Vector2(1,0));
		moveTo(result.getX(), result.getY());
	}	
	
	/**
	 * Moves the hero to the upward direction
	 */
	public void moveUp()
	{
		Vector2 result = hero.center;
		result.add( new Vector2(0,1));
		moveTo(result.getX(), result.getY());
	}
	
	/**
	 * Moves the hero to the downward direction
	 */
	public void moveDown()
	{
		Vector2 result = hero.center;
		result.add( new Vector2(0,-1));
		moveTo(result.getX(), result.getY());
	}
	
	// ---------------- MOUSE / KEYBOARD ----------------------------
	
	/**
	 * Get the x-coordinate position of the mouse
	 * @return x-coordinate position of the mouse
	 */
	public float mousePositionX(){
		return mouse.getWorldX();
	}
	
	/**
	 * Get the y-coordinate position of the mouse
	 * @return y-coordinate position of the mouse
	 */
	public float mousePositionY(){
		return mouse.getWorldY();
	}
	
	/**
	 * Check if the MOUSE LEFT button is clicked
	 * @return	true/false
	 */
	public boolean isMouseLeftClicked(){
		return (mouse.isButtonDown(1));
	}
	
	/**
     * Check if the given key is tapped on the keyboard. 
     * The key is triggered only once when the key is tapped.
     * (Note: the button 'tapped' and button 'down' or 'press' are different.)
     * @param key - the key on the keyboard. Ex: KeysEnum.a => the 'a' key.
     * @return - true if the key is tapped, false otherwise
     */
	public boolean isKeyboardButtonTapped(KeysEnum key) {
        return keyboard.isButtonTapped(keyEventMap[key.ordinal()]);  //ordinal is like indexOf for enums->ints
    }
	
	/**
	 * Check if the LEFT arrow on the keyboard is down
	 * @return true if the left arrow key on the keyboard is down
	 */
	public boolean isKeyboardLeftPressed(){
		return keyboard.isButtonDown(KeyEvent.VK_LEFT);
	}
	
	/**
	 * Check if the RIGHT arrow on the keyboard is down
	 * @return true if the right arrow key on the keyboard is down
	 */
	public boolean isKeyboardRightPressed(){
		return keyboard.isButtonDown(KeyEvent.VK_RIGHT);
	}
	
	/**
	 * Check if the UP arrow on the keyboard is down
	 * @return true if the up arrow key on the keyboard is down
	 */
	public boolean isKeyboardUpPressed(){
		return keyboard.isButtonDown(KeyEvent.VK_UP);
	}
	
	/**
	 * Check if the DOWN arrow on the keyboard is down
	 * @return true if the down arrow key on the keyboard is down
	 */
	public boolean isKeyboardDownPressed(){
		return keyboard.isButtonDown(KeyEvent.VK_DOWN);
	}
	
	/**
	 * Check if the SPACE arrow on the keyboard is down
	 * @return true if the space key on the keyboard is down
	 */
	public boolean isKeyboardSpacePressed(){
		return keyboard.isButtonDown(KeyEvent.VK_SPACE);
	}
	// ---------------- MOUSE / KEYBOARD end ----------------------------
	
	// -------------------- Utilities functions ------------------
	
	/**
	 * Do nothing.
	 * Called within handleCollisions to prevent default behavior.
	 */
	public void doNothing(){
		CollisionManager.setDirty();
	}
	
	/**
	 * Destroys an object
	 * @param id the id of the object to be destroyed
	 */
	public void destroy(int id)
	{
		CollidableGameObject obj = CollisionManager.findByID(id);
		obj.destroy();
		CollisionManager.setDirty();
	}
	
	/**
	 * Moves a game object. Disables default collision behavior if called inside handleCollisions.
	 * @param id the id of the object to be moved
	 * @param x horizontal position
	 * @param y vertical position
	 */
	public void move(int id, float x, float y){
		CollidableGameObject obj = CollisionManager.findByID(id);
		
		obj.center = new Vector2(obj.center.getX() + x,obj.center.getY() + y);
		
		CollisionManager.setDirty();
	}
	
	
	/**
	 * Increases the score according to the argument.
	 * @param n - the number of score to increase by
	 */
	public void increaseScoreBy(int n){
		GameState.Score += n;
	}
	
	/**
	 * Sets the number of lives the hero has to the number, n
	 * and displays to the screen
	 * @param n the number of lives to set to
	 */
	public void setLivesTo(int n){
		ui.setRemainingLives(n);
	}
	
	/**
	 * Sets the distance the player must travel to beat the game to a default value
	 */
	public void setGoalDistance(){
		GameState.TargetDistance = ConfigurationFileParser.getInstance().getWorldData().getWorldMapLength();
	}
	
	/**
	 * Sets the distance the player must travel to beat the game 
	 * @param distance The new distance required to beat the game
	 */
	public void setGoalDistance(int distance){
		GameState.TargetDistance = distance;
	}
	
	/**
	 * Generate a random number between 0 and n
	 * @param n the upper range
	 * @return a random number between 0 and n
	 */
	public int randomInt(int n){
		Random rand = new Random();
		return rand.nextInt(n);
	}

	/**
	 * Sets a timer associated with an ID.
	 * <br><br>
	 * If there is already a timer associated with the ID,<br>
	 * * Resets the timer.
	 *
	 * @param id The string ID
	 * @param seconds The length of the timer in seconds
	 */
	public void setSingleTimer(String id, float seconds)
	{
		TimeManager.setTimer(id, seconds);
	}

	/**
	 * Reports whether a timer associated with an ID has finished
	 *
	 * @param id The ID of the timer to check
	 */
	public static boolean isTimerFinished(String id)
	{
		return TimeManager.isTimerFinished(id);
	}

	/**
	 * Handles a repeating timer.
	 * <br><br>
	 * Returns true every time the timer elapses, false otherwise.
	 *
	 * @param id
	 * @param seconds
	 */
	public static boolean repeatingTimer(String id, float seconds)
	{
		return TimeManager.repeatingTimer(id, seconds);
	}
	// ---------- Utilities functions end ------------
	
	
	
	//-------------- DEBRIS ------------------------------------

	/**
	 * Sets up the debris generator with a default interval.
	 * <br><br>
	 * Debris spawn at random locations along the right edge.
	 */
	public void startDebrisSpawner()
	{
		startDebrisSpawner(1);
	}

	/**
	 * Sets up the debris generator using a specified interval.
	 * <br><br>
	 * Debris spawn at random locations along the right edge.
	 * @param interval The interval to spawn debris.
	 */
	public void startDebrisSpawner(float interval)
	{
		DebrisGenerator.enable();
		DebrisGenerator.setInterval(interval);
	}

	/**
	 * Stops the automatic debris generator.
	 */
	public void stopDebrisSpawner()
	{
		DebrisGenerator.disable();
	}

	/**
	 * Spawn a single debris at a random height
	 */
	public void spawnSingleDebris()
	{
		DebrisGenerator.spawnDebris();
	}

	/**
	 * Spawn a single debris at a specific height (y-coordinate)
	 */
	public void spawnSingleDebris(float height)
	{
		DebrisGenerator.spawnDebris(height);
	}

	/**
	 * Reports the number of debris in play
	 * @return The number of debris
	 */
	public int debrisCount()
	{
		return DebrisGenerator.debrisCount();
	}

	//-------------- DEBRIS end --------------------------------
	
	
	//-------------------- ENEMY --------------------------------
	
	/**
	 * Spawn a random enemy at a random position on the right of the 
	 * game window with the default interval of 3 seconds
	 */
	public void startEnemySpawner(){
		startEnemySpawner(3);
	}
	
	/**
	 * Spawn a random enemy at a random position on the right of the 
	 * game window with the given interval
	 */
	public void startEnemySpawner(float interval){
		EnemyGenerator.enable();
		EnemyGenerator.setInterval(interval);
	}
	
	/**
	 * Spawn a single random enemy at a random position on the right of the 
	 * game window
	 */
	public void spawnSingleEnemy(){
		enemyGenerator.spawnEnemy();
	}
	
	/**
	 * Spawn a single random enemy at a random position on the right of the 
	 * game window with the given y-coordinate position
	 */
	public void spawnSingleEnemy(float height){
		enemyGenerator.spawnEnemy(height);
	}

	/**
	 * Spawn a single enemy of the given type at a random position on the right of the 
	 * game window
	 */
	public void spawnSingleEnemy(String type){
		enemyGenerator.spawnEnemy(type);
	}


	/**
	 * Disable the enemy spawner
	 */
	public void stopEnemySpawner(){
		EnemyGenerator.disable();
	}
	//------------------ ENEMY end --------------------------------

	//-------------- COLLECTIBLES -----------------------------------

	public void startDyePackSpawner()
	{
		startDyePackSpawner(2);
	}

	public void startDyePackSpawner(float interval)
	{
		DyePackGenerator.initialize(100);
		DyePackGenerator.setInterval(interval);
		DyePackGenerator.setActive(true);
	}

	public void stopDyePackSpawner()
	{
		DyePackGenerator.setActive(false);
	}

	public void spawnSinglePowerUp(float positionX, float positionY)
	{
		PowerUp spawned;
		switch (randomInt(8))
		{
		case 0:
			spawned = new Unarmed();
			break;
		case 1:
			spawned = new SpeedUp();
			break;
		case 2:
			spawned = new SlowDown();
			break;
		case 3:
			spawned = new Overload();
			break;
		case 4:
			spawned = new Gravity();
			break;
		case 5:
			spawned = new Magnetism();
			break;
		case 6:
			spawned = new Ghost();
			break;
		case 7:
			spawned = new Repel();
			break;
		default:
			// Defaults to invincibility
			spawned = new Invincibility();
		}
		spawned.initialize(new Vector2(positionX, positionY));
	}

	public void spawnSinglePowerUp(String type, float positionX, float positionY)
	{
		PowerUp spawned;
		switch (type.toLowerCase())
		{
		case "unarmed":
			spawned = new Unarmed();
			break;
		case "speedup":
			spawned = new SpeedUp();
			break;
		case "slowdown":
			spawned = new SlowDown();
			break;
		case "overload":
			spawned = new Overload();
			break;
		case "gravity":
			spawned = new Gravity();
			break;
		case "magnet":
			spawned = new Magnetism();
			break;
		case "ghost":
			spawned = new Ghost();
			break;
		case "repel":
			spawned = new Repel();
			break;
		default:
			// Defaults to invincibility
			spawned = new Invincibility();
		}
		spawned.initialize(new Vector2(positionX, positionY));
	}

	//-------------- COLLECTIBLES end -------------------------------

	//-------------- WORMHOLES / GATES --------------------------------------

	public void spawnGates()
	{
//		new WormHole(hero, Colors.randomColor(), 40f, 15f, 120f, 5f);
//		new WormHole(hero, getHeroColor(), 40f, 15f, 120f, 20f);
//		new WormHole(hero, Colors.randomColor(), 40f, 15f, 120f, 35f);
//		new WormHole(hero, Colors.randomColor(), 40f, 15f, 120f, 50f);
		addOneWormHole(Colors.randomColor(), 40f, 15f, 120f, 5f);
		addOneWormHole(getHeroColor(), 40f, 15f, 120f, 20f);
		addOneWormHole(Colors.randomColor(), 40f, 15f, 120f, 35f);
		addOneWormHole(Colors.randomColor(), 40f, 15f, 120f, 50f);
	}
	
	/**
	 * Adds a WormHole to the game with the given Color, Width, Height, X-coordinate, Y-coordinate
	 * @param color the color of the WormHole
	 * @param width the width of the WormHole
	 * @param height the height of the WormHole
	 * @param x the X-coordinate position of the WormHole
	 * @param y the Y-coordinate position of the WormHole
	 */
	public void addOneWormHole(Color color, float width, float height, float x, float y){
		new WormHole(hero, color, width, height, x, y);
	}

	// -------------- WORMHOLES end ---------------------------------

	//-------------- WEAPONS -----------------------------------
	public void activateSpreadFireWeapon(){
		// add a new weapon to the WeaponRack list
		//hero.registerWeapon(new SpreadFireWeapon(hero));
		hero.changeWeapon(new SpreadFireWeapon(hero));
	}
	
	public void activateLimitedAmmoWeapon(){
		hero.changeWeapon(new LimitedAmmoWeapon(hero));
	}
	
	public void defaultWeapon(){
		hero.changeWeapon(new OverHeatWeapon(hero));
	}

	//-------------- WEAPONS end-----------------------------------
	
	
	//------------------ MENU (not functioning yet)/ UI-------------------
	
	/**
	 * Displays the Winning menu on the screen
	 */
	public void showWinMenu(){
		endMenu.setMenu(true);
		endMenu.active(true);
	}
	
	/**
	 * Displays the Losing menu on the screen
	 */
	public void showLoseMenu(){
		endMenu.setMenu(false);
		endMenu.active(true);
	}
	
	/**
	 * Displays the Start menu on the screen
	 */
	public void showStartMenu(){
		start.showScreen(true); // "Click Anywhere to Start"
	}
	
	/**
	 * Display the Menu on the screen (Ex: when the game is paused)
	 */
	public void showMenu(){
		menu.active(true);
	}
	
	/**
	 * Displays the score UI on the game screen. 
	 * @param display true to display, false otherwise
	 */
	public void displayScore(boolean display){
		ui.displayScore(display);
	}
	//------------------ MENU / UI end -----------------------------------
	
	
	/**
     * Used in the wrapper API function isKeyboardButtonDown, this array 
     * maps a shorter abbreviation like 'RIGHT' to the virtual key event 'KeyEvent.VK_RIGHT'
     * (Source: SpaceSmasherFunctionalAPI/SpaceSmasherProceduralAPI)
     */
	public static final int[] keyEventMap = {
		KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN, 
		KeyEvent.VK_SPACE, KeyEvent.VK_ENTER, KeyEvent.VK_ESCAPE, KeyEvent.VK_SHIFT, 
		KeyEvent.VK_LESS, KeyEvent.VK_GREATER,
		KeyEvent.VK_0, KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3, KeyEvent.VK_4,
        KeyEvent.VK_5, KeyEvent.VK_6, KeyEvent.VK_7, KeyEvent.VK_8, KeyEvent.VK_9, 
        KeyEvent.VK_A, KeyEvent.VK_B, KeyEvent.VK_C, KeyEvent.VK_D, KeyEvent.VK_E, KeyEvent.VK_F,
        KeyEvent.VK_G, KeyEvent.VK_H, KeyEvent.VK_I, KeyEvent.VK_J, KeyEvent.VK_K, KeyEvent.VK_L,
        KeyEvent.VK_M, KeyEvent.VK_N, KeyEvent.VK_O, KeyEvent.VK_P, KeyEvent.VK_Q, KeyEvent.VK_R,
        KeyEvent.VK_S, KeyEvent.VK_T, KeyEvent.VK_U, KeyEvent.VK_V, KeyEvent.VK_W, KeyEvent.VK_X,
        KeyEvent.VK_Y, KeyEvent.VK_Z,
        KeyEvent.VK_A, KeyEvent.VK_B, KeyEvent.VK_C, KeyEvent.VK_D, KeyEvent.VK_E, KeyEvent.VK_F,
        KeyEvent.VK_G, KeyEvent.VK_H, KeyEvent.VK_I, KeyEvent.VK_J, KeyEvent.VK_K, KeyEvent.VK_L,
        KeyEvent.VK_M, KeyEvent.VK_N, KeyEvent.VK_O, KeyEvent.VK_P, KeyEvent.VK_Q, KeyEvent.VK_R,
        KeyEvent.VK_S, KeyEvent.VK_T, KeyEvent.VK_U, KeyEvent.VK_V, KeyEvent.VK_W, KeyEvent.VK_X,
        KeyEvent.VK_Y, KeyEvent.VK_Z
	};
}
