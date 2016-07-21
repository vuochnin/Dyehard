package dyeHardProceduralAPI;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.Map.Entry;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.Collectibles.*;
import dyehard.Collision.CollidableGameObject;
import dyehard.DyeHardGame.State;
import dyehard.DyeHardGame;
import dyehard.UpdateManager;
import dyehard.GameScreens.BackgroundScreen;
//import dyehard.Collision.CollisionManager;
import dyehard.GameScreens.LogScreen;
import dyehard.Resources.ConfigurationFileParser;
import dyehard.Ui.DyehardEndMenu;
import dyehard.Ui.DyehardMenuUI;
import dyehard.Ui.DyehardUI;
import dyehard.Util.Colors;
import dyehard.World.GameState;
import dyehard.Player.Hero;
import dyehard.Weapons.*;
import dyehard.World.WormHole;


/**
 * @author vuochnin
 * @author Holden
 */
public class DHProceduralAPI extends DyeHardGame{

	//private boolean menuActive = false;
	private boolean endMenuActive = false;
	private static float Speed = 0.3f;
	private float distance = 0f;
	private static int lives = 5;
	
	private Hero hero;
	private DyehardUI ui;
	
	//private DyehardMenuUI menu;
	private DyehardEndMenu endMenu;
	private LogScreen start;			// "Click Anywhere to Start"
	
	/**
	 * @Override 
	 * Must override the initialize() method from the abstract super class, DyeHardGame
	 */
	public void initialize(){
		CollisionManager.register(this);

		window.requestFocusInWindow();
		apiSetGoalDistance();
		buildGame();
		
		//menu = new DyehardMenuUI();
		endMenu = new DyehardEndMenu();
		start = new LogScreen();
		
		DyeHardGame.setState(State.BEGIN);
	}
	
	
	
	/**
	 * @Override 
	 * Must override the update() method from the abstract super class, DyeHardGame
	 */
	public void update(){
		//checkControl();
		switch (getState()) {
        case PLAYING:
        	if(endMenuActive){
        		endMenu.active(false);
        	}
			UpdateManager.getInstance().update();
			distance += Speed;
			GameState.DistanceTravelled = (int) distance;
			DebrisGenerator.update();
			DyePackGenerator.update();
			EnemyGenerator.update();
	
			CollisionManager.update();
			
			IDManager.cleanup();
			updateGame();
			break;
        case QUIT:
        	window.close();
        	break;
        case RESTART:
        	apiRestartGame();
        	break;
        default:
        	break;
		}
		
		if(getState() == State.BEGIN){
			apiShowStartMenu(true);
			if(start.isShown()){
				if(apiIsMouseLeftClicked()){
					hero.currentWeapon.resetTimer();
					setState(State.PLAYING);
					apiShowStartMenu(false);
				}
			}
		}else if(getState() == State.GAMEOVER){
			if(apiIsMouseLeftClicked()){
				endMenu.select(mouse.getWorldX(), mouse.getWorldY(), true);
			}else
				endMenu.select(mouse.getWorldX(), mouse.getWorldY(), false);
		}
		
	}

	
	
	
	
	//--------------------------------------------------------------------------------------------	
	//--------------------- SOME POSSIBLE PROCEDURAL FUNCTIONS -----------------------------------
	//--------------------------------------------------------------------------------------------
	

	/**
	 * A callback function, for user to override.
	 * (Call Once)
	 */
	public void buildGame(){ }
	
	/**
	 * A callback function, for user to override.
	 * (Call many times)
	 */
	public void updateGame(){ }
	
	
	
	public void handleCollisions(String type1, String subtype1, int id1, String type2, String subtype2, int id2)
	{
		// User overrides this
	}
	
	/**
	 * Reports the number of objects in play
	 * @return
	 */
	public int apiObjectCount()
	{
		return IDManager.count();
	}

	/**
	 * Retrieves the ID of a registered object. Used in for loops.
	 * @param index
	 * @return
	 */
	public int apiGetID(int index)
	{
		return IDManager.getID(index);
	}

	/**
	 * presents the type of an object
	 * @param id
	 * @return
	 */
	public String apiGetType(int id){
		return CollisionManager.getType(id);
	}

	/**
	 * Presents the subtype of an object
	 * @param id
	 * @return
	 */
	public String apiGetSubtype(int id){
		return CollisionManager.getSubtype(id);
	}
	
	
	/**
	 * Fire the current weapon
	 */
	public void apiHerofirePaint(){					// Fire the paint
		hero.currentWeapon.fire();
	}
	
	/**
	 * Create new Hero object and set it to hero instance and 
	 * set the cursor to the center of that hero
	 */
	public int apiStartHero(){					// Create new Hero
		hero = new Hero();
		EnemyGenerator.initialize(hero);
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

		return IDManager.register(hero);
	}
	
	/**
	 * Makes the hero follow the mouse movement
	 */
	public void apiObjectFollowTheMouse(int id){
		apiMoveObjectTo(id, apiMousePositionX(), apiMousePositionY());
	}
	
	/**
	 * Gets the current color of the DyePack the hero is carrying
	 * @return the hero's dyepack color
	 */
	public Color apiGetHeroColor(){
		return hero.getColor();
	}
	
	/**
	 * Moves the object to a specific position
	 * @param x the X-coordinate position
	 * @param y the Y-coordinate position
	 */
	public void apiMoveObjectTo(int id, float x, float y){
		if(apiGetType(id) == "Hero")
			hero.moveTo(x, y);
		else
			IDManager.get(id).center = (new Vector2(x,y));
	}
	
	
	
	// -------------------- Utilities functions ------------------

	public float apiGetWorldWidth(){
		return world.getWidth();
	}
	
	public float apiGetWorldHeight(){
		return world.getHeight();
	}
	
	public float apiGetObjectPositionX(int id){
		return IDManager.get(id).center.getX();
	}
	
	public float apiGetObjectPositionY(int id){
		return IDManager.get(id).center.getY();
	}
	
	public void apiRestartGame() {
		background.destroy();
		background = new BackgroundScreen();
		endMenu.active(false);
		apiSetGoalDistance();
    	apiSetLivesTo(lives);
    	setState(State.BEGIN);
        System.gc();
        distance = 0;
        GameState.DistanceTravelled = 0;
        GameState.Score = 0;
        hero.center = hero.getStart();

        TimeManager.reset();
        
        IDManager.reset();
        
        buildGame();
	}
	
	public boolean apiUserLose(){
		return (GameState.RemainingLives <= 0);
	}
	
	public boolean apiUserWon(){
		return GameState.DistanceTravelled == GameState.TargetDistance;
	}
	
	public void apiQuitGame(){
		//window.close();
		System.exit(0);
	}
	
	/**
	 * Prints a message to the console.
	 * @param message the message to display
	 */
	public void apiEcho(String message)
	{
		System.out.println(message);
	}

	/**
	 * Tests for a collision between two objects
	 * @param id1 The ID of the first object
	 * @param id2 The ID of the second object
	 * @return True if the objects are touching, otherwise, false
	 */
	public boolean apiColliding(int id1, int id2)
	{
		return CollisionManager.rememberCollision(id1, id2) || CollisionManager.rememberCollision(id2, id1);
	}

//		/**
//		 * DEPRECATED
//		 * Do nothing.
//		 * Called within handleCollisions to prevent default behavior.
//		 */
//		public void doNothing(){
//			CollisionManager.setDirty();
//		}
	
	/**
	 * Destroys an object
	 * @param id the id of the object to be destroyed
	 */
	public void apiDestroy(int id)
	{
		CollidableGameObject obj = IDManager.get(id);
		obj.destroy();
		CollisionManager.setDirty();
	}
	
	/**
	 * Shifts a game object's position.
	 * @param id the id of the object to be moved
	 * @param deltaX horizontal position
	 * @param deltaY vertical position
	 */
	public void apiMoveObject(int id, float deltaX, float deltaY){
		CollidableGameObject obj = IDManager.get(id);
		
		obj.center = new Vector2(obj.center.getX() + deltaX,obj.center.getY() + deltaY);
	}
	
	/**
	 * Sets a game object's velocity.
	 * @param id the id of the object to be moved
	 * @param deltaX horizontal position
	 * @param deltaY vertical position
	 */
	public void apiSetObjectVelocity(int id, double x, double y){
		CollidableGameObject obj = IDManager.get(id);
		
		obj.velocity = new Vector2((float)x, (float)y);
	}
	
	
	/**
	 * Increases the score according to the argument.
	 * @param n - the number of score to increase by
	 */
	public void apiIncreaseScoreBy(int n){
		GameState.Score += n;
	}
	
	/**
	 * Decreases the score according to the argument.
	 * @param n - the number of score to decrease by
	 */
	public void apiDecreaseScoreBy(int n){
		GameState.Score -= n;
	}
	
	/**
	 * Sets the number of lives the hero has to the number, n
	 * and displays to the screen
	 * @param n the number of lives to set to
	 */
	public void apiSetLivesTo(int n){
		lives = n;
		ui.setRemainingLives(n);
	}
	
	/**
	 * Sets the distance the player must travel to beat the game to a default value
	 */
	public void apiSetGoalDistance(){
		GameState.TargetDistance = ConfigurationFileParser.getInstance().getWorldData().getWorldMapLength();
	}
	
	/**
	 * Sets the distance the player must travel to beat the game 
	 * @param distance The new distance required to beat the game
	 */
	public void apiSetGoalDistance(int distance){
		GameState.TargetDistance = distance;
	}
	
	/**
	 * Generate a random number between 0 and n inclusively
	 * @param n the upper range
	 * @return a random number between 0 and n inclusively
	 */
	public int apiRandomInt(int n){
		n++;
		Random rand = new Random();
		return rand.nextInt(n);
	}
	
	/**
	 * Generate a random number between min and max inclusively
	 * @param min the lower range
	 * @param max the upper range
	 * @return a random number between min and max inclusively
	 */
	public int apiRandomInt(int min, int max){
		max++;
		Random rand = new Random();
		return rand.nextInt(max - min) + min;
	}
	/**
	 * Generate a random floating point number between 0 (inclusive) and max (exclusive)
	 * @param n the upper range
	 * @return a random number between 0 and n
	 */
	public float apiRandomFloat(float max){
		Random rand = new Random();
		return rand.nextFloat() % max;
	}

	/**
	 * Generate a random floating point number between min (inclusive) and max (exclusive)
	 * @param max the upper range
	 * @return a random number between min and max
	 */
	public float apiRandomFloat(float min, float max){
		Random rand = new Random();
		return (rand.nextFloat() % (max - min)) + min;
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
	public void apiSetSingleTimer(String id, float seconds)
	{
		TimeManager.setTimer(id, seconds);
	}

	/**
	 * Reports whether a timer associated with an ID has finished
	 *
	 * @param id The ID of the timer to check
	 */
	public static boolean apiIsTimerFinished(String id)
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
	public static boolean apiRepeatingTimer(String id, float seconds)
	{
		return TimeManager.repeatingTimer(id, seconds);
	}
	// ---------- Utilities functions end ------------
	
	
	
	// ---------------- MOUSE / KEYBOARD ----------------------------
	
	/**
	 * Get the x-coordinate position of the mouse
	 * @return x-coordinate position of the mouse
	 */
	public float apiMousePositionX(){
		return mouse.getWorldX();
	}
	
	/**
	 * Get the y-coordinate position of the mouse
	 * @return y-coordinate position of the mouse
	 */
	public float apiMousePositionY(){
		return mouse.getWorldY();
	}
	
	/**
	 * Check if the MOUSE LEFT button is clicked
	 * @return	true/false
	 */
	public boolean apiIsMouseLeftClicked(){
		return (mouse.isButtonDown(1));
	}
	
	/**
     * Check if the given key is tapped on the keyboard. 
     * The key is triggered only once when the key is tapped.
     * (Note: the button 'tapped' and button 'down' or 'press' are different.)
     * @param key - the key on the keyboard. Ex: KeysEnum.a => the 'a' key.
     * @return - true if the key is tapped, false otherwise
     */
	public boolean apiIsKeyboardButtonTapped(KeysEnum key) {
        return keyboard.isButtonTapped(keyEventMap[key.ordinal()]);  //ordinal is like indexOf for enums->ints
    }
	
	/**
	 * Check if the LEFT arrow on the keyboard is down
	 * @return true if the left arrow key on the keyboard is down
	 */
	public boolean apiIsKeyboardLeftPressed(){
		return keyboard.isButtonDown(KeyEvent.VK_LEFT);
	}
	
	/**
	 * Check if the RIGHT arrow on the keyboard is down
	 * @return true if the right arrow key on the keyboard is down
	 */
	public boolean apiIsKeyboardRightPressed(){
		return keyboard.isButtonDown(KeyEvent.VK_RIGHT);
	}
	
	/**
	 * Check if the UP arrow on the keyboard is down
	 * @return true if the up arrow key on the keyboard is down
	 */
	public boolean apiIsKeyboardUpPressed(){
		return keyboard.isButtonDown(KeyEvent.VK_UP);
	}
	
	/**
	 * Check if the DOWN arrow on the keyboard is down
	 * @return true if the down arrow key on the keyboard is down
	 */
	public boolean apiIsKeyboardDownPressed(){
		return keyboard.isButtonDown(KeyEvent.VK_DOWN);
	}
	
	/**
	 * Check if the SPACE arrow on the keyboard is down
	 * @return true if the space key on the keyboard is down
	 */
	public boolean apiIsKeyboardSpacePressed(){
		return keyboard.isButtonDown(KeyEvent.VK_SPACE);
	}
	// ---------------- MOUSE / KEYBOARD end ----------------------------
	
	
	//-------------- DEBRIS ------------------------------------

	/**
	 * Sets up the debris generator with a default interval.
	 * <br><br>
	 * Debris spawn at random locations along the right edge.
	 */
	public void apiStartDebrisSpawner()
	{
		apiStartDebrisSpawner(1);
	}

	/**
	 * Sets up the debris generator using a specified interval.
	 * <br><br>
	 * Debris spawn at random locations along the right edge.
	 * @param interval The interval to spawn debris.
	 */
	public void apiStartDebrisSpawner(float interval)
	{
		DebrisGenerator.enable();
		DebrisGenerator.setInterval(interval);
	}

	/**
	 * Stops the automatic debris generator.
	 */
	public void apiStopDebrisSpawner()
	{
		DebrisGenerator.disable();
	}

	/**
	 * Spawn a single debris at a random height
	 */
	public int apiSpawnSingleDebris()
	{
		return DebrisGenerator.spawnDebris();
	}

	/**
	 * Spawn a single debris at a specific height (y-coordinate)
	 */
	public int apiSpawnSingleDebris(float height)
	{
		return DebrisGenerator.spawnDebris(height);
	}

	/**
	 * Reports the number of debris in play
	 * @return The number of debris
	 */
	public int apiDebrisCount()
	{
		return DebrisGenerator.debrisCount();
	}

	//-------------- DEBRIS end --------------------------------
	
	
	//-------------------- ENEMY --------------------------------
	
	/**
	 * Spawns random enemies at random locations on the right of the 
	 * game window with the default time interval of 3 seconds
	 */
	public void apiStartEnemySpawner(){
		apiStartEnemySpawner(3);
	}
	
	/**
	 * Spawns random enemies at random locations on the right of the 
	 * game window with the given time interval
	 */
	public void apiStartEnemySpawner(float interval){
		EnemyGenerator.enable();
		EnemyGenerator.setInterval(interval);
	}
	
	/**
	 * Spawn a single random enemy at a random position on the right of the 
	 * game window
	 */
	public void apiSpawnSingleEnemy(){
		EnemyGenerator.spawnEnemy();
	}
	
	/**
	 * Spawn a single random enemy at a specified position (x,y)
	 * @param x the x-coordinate position of the enemy
	 * @param y the y-coordinate position of the enemy
	 * @return an integer which represents the id of the enemy
	 */
	public int apiSpawnSingleEnemy(float x, float y){
		return EnemyGenerator.spawnEnemy(x, y);
	}

	/**
	 * Spawn a single enemy of the specified type at a random position on 
	 * the right of the game window
	 * @param type the type of the enemy (as a string)
	 * @return an integer which represents the id of the enemy
	 */
	public int apiSpawnSingleEnemy(String type){
		return EnemyGenerator.spawnEnemy(type);
	}
	
	/**
	 * Spawn an enemy with the specified type at the specified location (x, y)
	 * @param type the type of the enemy (as a string)
	 * @param x the x-coordinate position of the enemy
	 * @param y the y-coordinate position of the enemy
	 * @return an integer which represents the id of the enemy
	 */
	public int apiSpawnSingleEnemy(String type, float x, float y){
		return EnemyGenerator.spawnEnemy(type, x, y);
	}

	/**
	 * Disable the enemy spawner
	 */
	public void apiStopEnemySpawner(){
		EnemyGenerator.disable();
	}
	
	/**
	 * Reports the number of enemies instantiated 
	 * @return the number of enemies instantiated 
	 */
	public int apiEnemyCount(){
		return EnemyGenerator.enemyCount();
	}
	
	//------------------ ENEMY end --------------------------------

	//-------------- COLLECTIBLES -----------------------------------
	
	public void apiStartDyePackSpawner()
	{
		apiStartDyePackSpawner(2);
	}

	public void apiStartDyePackSpawner(float interval)
	{
		DyePackGenerator.initialize(100);
		DyePackGenerator.setInterval(interval);
		DyePackGenerator.setActive(true);
	}

	public void apiStopDyePackSpawner()
	{
		DyePackGenerator.setActive(false);
	}
	
	/**
	 * Spawns a single DyePack with the specified color (as a String) and location
	 * @param color [ "red" | "blue" | "green" | "teal" | "yellow" | "pink" ]. 
	 * Note: if the color specified is not in the list of color, function will pick red as the default
	 * @param x the x-coordinate position of the DyePack
	 * @param y the Y-coordinate position of the DyePack
	 * @return an integer which represents the id of the DyePack
	 */
	public int apiSpawnSingleDyePack(String color, float x, float y){
		return DyePackGenerator.spawnDyePack(color, x, y);
	}
	
	public int apiSpawnSinglePowerUp(float positionX, float positionY)
	{
		PowerUp spawned;
		switch (apiRandomInt(8))
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
		return  IDManager.register(spawned);
	}

	public int apiSpawnSinglePowerUp(String type, float positionX, float positionY)
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
		return  IDManager.register(spawned);
	}

	//-------------- COLLECTIBLES end -------------------------------

	//-------------- WORMHOLES / GATES --------------------------------------

	public void apiSpawnGates()
	{
//		new WormHole(hero, Colors.randomColor(), 40f, 15f, 120f, 5f);
//		new WormHole(hero, getHeroColor(), 40f, 15f, 120f, 20f);
//		new WormHole(hero, Colors.randomColor(), 40f, 15f, 120f, 35f);
//		new WormHole(hero, Colors.randomColor(), 40f, 15f, 120f, 50f);
		apiAddOneWormHole(Colors.randomColor(), 40f, 15f, 120f, 5f);
		apiAddOneWormHole(apiGetHeroColor(), 40f, 15f, 120f, 20f);
		apiAddOneWormHole(Colors.randomColor(), 40f, 15f, 120f, 35f);
		apiAddOneWormHole(Colors.randomColor(), 40f, 15f, 120f, 50f);
	}
	
	/**
	 * Adds a WormHole to the game with the given Color, Width, Height, X-coordinate, Y-coordinate
	 * @param color the color of the WormHole
	 * @param width the width of the WormHole
	 * @param height the height of the WormHole
	 * @param x the X-coordinate position of the WormHole
	 * @param y the Y-coordinate position of the WormHole
	 */
	public void apiAddOneWormHole(Color color, float width, float height, float x, float y){
		new WormHole(hero, color, width, height, x, y);
	}

	// -------------- WORMHOLES end ---------------------------------

	//-------------- WEAPONS -----------------------------------
	public void apiActivateSpreadFireWeapon(){
		// add a new weapon to the WeaponRack list
		//hero.registerWeapon(new SpreadFireWeapon(hero));
		hero.changeWeapon(new SpreadFireWeapon(hero));
	}
	
	public void apiActivateLimitedAmmoWeapon(){
		hero.changeWeapon(new LimitedAmmoWeapon(hero));
	}
	
	public void apiDefaultWeapon(){
		hero.changeWeapon(new OverHeatWeapon(hero));
	}

	//-------------- WEAPONS end-----------------------------------
	
	
	//------------------ MENU (not functioning yet)/ UI-------------------
	
	/**
	 * Displays the Winning menu on the screen
	 */
	public void apiShowWinMenu(boolean yes){
		setState(State.GAMEOVER);
		endMenu.setMenu(true);	// True for win menu
		endMenu.active(yes);
		if(yes)
			endMenuActive = true;
		else
			endMenuActive = false;
	}
	
	/**
	 * Displays the Losing menu on the screen
	 */
	public void apiShowLoseMenu(boolean yes){
		setState(State.GAMEOVER);
		endMenu.setMenu(false);		// False for win menu
		endMenu.active(yes);
		if(yes)
			endMenuActive = true;
		else
			endMenuActive = false;
	}
	
	/**
	 * Displays the Start menu on the screen
	 */
	public void apiShowStartMenu(boolean yes){
		start.showScreen(yes); // "Click Anywhere to Start"
	}
	
//	/**
//	 * Display the Menu on the screen (Ex: when the game is paused)
//	 */
//	public void showMenu(boolean yes){
//		menu.active(yes);
//		if(yes)
//			menuActive = true;
//		else
//			menuActive = false;
//	}
	
	/**
	 * Displays the score UI on the game screen. 
	 * @param display true to display, false otherwise
	 */
	public void apiDisplayScore(boolean display){
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
