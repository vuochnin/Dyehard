package dyeHardProceduralAPI;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.Collectibles.*;
import dyehard.Collision.CollidableGameObject;
import dyehard.DyeHardGame;
import dyehard.UpdateManager;
import dyehard.GameScreens.BackgroundScreen;
//import dyehard.Collision.CollisionManager;
import dyehard.GameScreens.LogScreen;
import dyehard.Resources.ConfigurationFileParser;
import dyehard.Ui.DyehardEndMenu;
import dyehard.Ui.DyehardUI;
import dyehard.Util.Colors;
import dyehard.Util.DyeHardSound;
import dyehard.World.GameState;
import dyehard.Player.Hero;
import dyehard.Weapons.*;
import dyehard.World.WormHole;


/**
 * DHProceduralAPI class is a procedural API for Dye Hard game.
 * This API is designed to use as introductory computer science lessons.
 * 
 * @author Vuochly (Nin) Ky
 * @author Holden
 */
public class DHProceduralAPI extends DyeHardGame{

	private boolean endMenuActive = false;
	private boolean showStartMenu = false;
	private static float Speed = 0.3f;
	private float distance = 0f;
	private static int winningScore = -1;
	
	private Hero hero;
	private DyehardUI ui;
	
	private DyehardEndMenu endMenu;
	private LogScreen start;		// "Click Anywhere to Start"
	
	/**
	 * @Override 
	 * Must override the initialize() method from the abstract super class, DyeHardGame
	 */
	public void initialize(){
		window.requestFocusInWindow();
		
		// Sets the distance the player must travel to beat the game to a default value
		GameState.TargetDistance = ConfigurationFileParser.getInstance().getWorldData().getWorldMapLength();
		
		endMenu = new DyehardEndMenu();
		start = new LogScreen();
		ApiDyePackGenerator.initialize(100);
		buildGame();
		if(showStartMenu)
			DyeHardGame.setState(State.BEGIN);
		else
			DyeHardGame.setState(State.PLAYING);
	}
	
	
	/**
	 * @Override 
	 * Must override the update() method from the abstract super class, DyeHardGame
	 */
	public void update(){
		switch (getState()) {
        case PLAYING:
        	if(endMenuActive){
        		endMenu.active(false);
        	}
			UpdateManager.getInstance().update();
			distance += Speed;
			GameState.DistanceTravelled = (int) distance;
			ApiDebrisGenerator.update();
			ApiDyePackGenerator.update();
			ApiEnemyGenerator.update();
			ApiCollisionManager.update();
			
			ApiIDManager.cleanup();
	        ApiIDManager.collectStrayObjects();

			updateGame();
			break;
        case BEGIN:
        	start.showScreen(true);
			if(start.isShown()){
				if(apiIsMouseLeftClicked()){
					hero.currentWeapon.resetTimer();
					setState(State.PLAYING);
					start.showScreen(false);
				}
			}
        	break;
        case GAMEOVER:
        	if(apiIsMouseLeftClicked()){
				endMenu.select(mouse.getWorldX(), mouse.getWorldY(), true);
			}else
				endMenu.select(mouse.getWorldX(), mouse.getWorldY(), false);
        	break;
        case QUIT:
        	apiQuitGame();
        	break;
        case RESTART:
        	apiRestartGame();
        	break;
        default:
        	break;
		}
	}
	
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
	
	//--------------------------------------------------------------------------	
	//--------------------- POSSIBLE PROCEDURAL FUNCTIONS ----------------------
	//--------------------------------------------------------------------------

	/**
	 * Presents the subtype of an object
	 * @param id the id of an object
	 * @return the subtype of the object as a String
	 */
	public String apiGetSubtype(int id){
		return ApiCollisionManager.getSubtype(id);
	}
	
	/**
	 * Fire the current weapon
	 */
	public void apiHerofirePaint(){	
		hero.currentWeapon.fire();
	}
	
	/**
	 * Gets the travelled distance of the hero
	 * @return the travelled distance as an Integer
	 */
	public int apiGetTravelledDistance(){
		return GameState.DistanceTravelled;
	}
	
	/**
	 * Create new Hero object and set it to hero instance and 
	 * set the cursor to the center of that hero
	 * @return an Integer which represent the ID of the Hero
	 */
	public int apiStartHero(){					// Create new Hero
		hero = new Hero();
		ApiEnemyGenerator.initialize(hero);
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

		return ApiIDManager.register(hero);
	}
	
	// -------------------- Utilities functions ------------------
	/**
	 * Reports the number of objects in play
	 * @return the number of objects in play
	 */
	public int apiObjectCount()
	{
		return ApiIDManager.count();
	}

	/**
	 * Retrieves the ID of a registered object. Used in for loops.
	 * @param index 
	 * @return an Integer which represents the ID of the object
	 */
	public int apiGetID(int index)
	{
		return ApiIDManager.getID(index);
	}

	/**
	 * presents the type of an object
	 * @param id the id of an object
	 * @return the type of the object as a String
	 */
	public String apiGetType(int id){
		return ApiCollisionManager.getType(id);
	}
	
	/**
	 * Makes the object specified by the id follow the mouse movement
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
	 * @param id the id of the object to be moved to
	 * @param x the X-coordinate position
	 * @param y the Y-coordinate position
	 */
	public void apiMoveObjectTo(int id, double x, double y){
		if(apiGetType(id) == "Hero")
			hero.moveTo((float)x, (float)y);
		else
			ApiIDManager.get(id).center = (new Vector2((float)x,(float)y));
	}
	
	/**
	 * Makes an object specified by the id to always appear on top of other objects
	 * @param id the object to bring to top
	 */
	public void apiAlwaysOnTop(int id){
		ApiIDManager.get(id).alwaysOnTop = true;
	}

	/**
	 * Gets a random DyeHard color (blue, red, yellow, teal, pink, green) 
	 * @return a random DyeHard color
	 */
	public Color apiGetRandomColor(){
		return Colors.randomColor();
	}
	
	/**
	 * Gets the world width of the game
	 * @return the width of the game world
	 */
	public float apiGetWorldWidth(){
		return world.getWidth();
	}
	
	/**
	 * Gets the world height of the game
	 * @return the height of the game world
	 */
	public float apiGetWorldHeight(){
		return world.getHeight();
	}
	
	/**
	 * Gets the X-coordinate position of the object specified by id
	 * @param id the ID of the object
	 * @return the x position as a floating point number
	 */
	public float apiGetObjectPositionX(int id){
		return ApiIDManager.get(id).center.getX();
	}
	
	/**
	 * Gets the Y-coordinate position of the object specified by id
	 * @param id the ID of the object
	 * @return the y position as a floating point number
	 */
	public float apiGetObjectPositionY(int id){
		return ApiIDManager.get(id).center.getY();
	}
	
	/**
	 * Restarts the game. Clean out every objects on the screen or 
	 * reset everything and start over
	 */
	public void apiRestartGame() {
		endMenu.active(false);
    	setState(State.PLAYING);
    	background.destroy();
		background = new BackgroundScreen();
		ApiTimeManager.reset();
        ApiIDManager.reset();
        System.gc();
        distance = 0;
        GameState.DistanceTravelled = 0;
        GameState.Score = 0;
        hero.center = hero.getStart();
        buildGame();
	}
	
	/**
	 * Checks if the user has lose in the game(lost all the lives)
	 * @return true if the user has lose, false otherwise
	 */
	public boolean apiUserLose(){
		return (GameState.RemainingLives <= 0);
	}
	
	/**
	 * Checks if the user has reach the target distance or target score
	 * @return true if user has win the game, false otherwise
	 */
	public boolean apiUserWon(){
		if(GameState.DistanceTravelled >= GameState.TargetDistance)
			return true;
		else{
			if(winningScore > 0)
				return GameState.Score >= winningScore;
			return false;
		}	
	}
	
	/**
	 * Quits the game
	 */
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
		return ApiCollisionManager.rememberCollision(id1, id2) || ApiCollisionManager.rememberCollision(id2, id1);
	}
	
	/**
	 * Destroys an object. (If the object is hero, the health will be decreased)
	 * @param id the id of the object to be destroyed
	 */
	public void apiDestroy(int id)
	{
		CollidableGameObject obj = ApiIDManager.get(id);
		if(obj instanceof Hero)
		{
			((Hero)obj).damageHero(hero, null);
		}
		else
		{
			obj.destroy();
		}
	}
	
	/**
	 * Shifts a game object's position.
	 * @param id the id of the object to be moved
	 * @param deltaX horizontal position
	 * @param deltaY vertical position
	 */
	public void apiMoveObject(int id, double deltaX, double deltaY){
		if(!(apiGetObjectPositionX(id)+deltaX < 0 && apiGetObjectPositionX(id)+deltaX > apiGetWorldWidth()
				&& apiGetObjectPositionY(id)+deltaY < 0 && apiGetObjectPositionY(id)+deltaY > apiGetWorldHeight()))
		{
			CollidableGameObject obj = ApiIDManager.get(id);
			
			obj.center = new Vector2(obj.center.getX() + (float) deltaX,obj.center.getY() + (float) deltaY);
		}
	}
	
	/**
	 * Sets a game object's velocity.
	 * @param id the id of the object to be moved
	 * @param x horizontal velocity
	 * @param y vertical velocity
	 */
	public void apiSetObjectVelocity(int id, double x, double y){
		CollidableGameObject obj = ApiIDManager.get(id);
		
		obj.velocity = new Vector2((float)x, (float)y);
	}
	
	/**
	 * Adjusts the score based on the argument.
	 * If n is positive, the score is increasing by n. (n, increasing)
	 * If n is negative, the score is decreasing by n. (-n, decreasing)
	 * @param n the value to adjust
	 */
	public void apiAdjustScoreBy(int n){
		GameState.Score += n;
	}
	
	/**
	 * Sets the winning score
	 * @param winScore the target score
	 */
	public void apiSetWinningScore(int winScore){
		winningScore = winScore;
	}
	
	/**
	 * Speeds up the game
	 * @param up true to speedup, false to set back to normal speed
	 */
	public void apiSpeedUp(boolean up){
		UpdateManager.getInstance().setSpeedUp(up);
		if(up)
			distance += (Speed * 3f); 	// Each time the method triggered, the distance increase by 3 times the speed
			GameState.DistanceTravelled = (int) distance;
	}
	
	/**
	 * Sets the number of lives the hero has to the number n
	 * and displays to the screen
	 * @param n the number of lives to set to
	 */
	public void apiSetLivesTo(int n){
		ui.setRemainingLives(n);
	}
	
	/**
	 * Sets the distance the player must travel to beat the game 
	 * @param distance The new distance required to beat the game
	 */
	public void apiSetGoalDistance(int distance){
		GameState.TargetDistance = distance;
		if(ui.distanceMeter != null)
		{
			ui.distanceMeter.maxValue = GameState.TargetDistance;
		}
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
	 * @param max the upper range
	 * @return a random number between 0 and n
	 */
	public float apiRandomFloat(double max){
		Random rand = new Random();
		return rand.nextFloat() * (float)max; //% max;
	}

	/**
	 * Generate a random floating point number between min (inclusive) and max (exclusive)
	 * @param max the upper range
	 * @return a random number between min and max
	 */
	public float apiRandomFloat(double min, double max){
		Random rand = new Random();
		return (float)(rand.nextFloat() % (max - min)) + (float)min;
	}
	/**
	 * Sets a timer associated with an ID.
	 * If there is already a timer associated with the ID, Resets the timer.
	 *
	 * @param id The string ID
	 * @param seconds The length of the timer in seconds
	 */
	public void apiSetSingleTimer(String id, double seconds)
	{
		ApiTimeManager.setTimer(id, (float)seconds);
	}

	/**
	 * Reports whether a timer associated with an ID has finished
	 *
	 * @param id The ID of the timer to check
	 */
	public static boolean apiIsTimerFinished(String id)
	{
		return ApiTimeManager.isTimerFinished(id);
	}

	/**
	 * Handles a repeating timer.
	 * @param id
	 * @param seconds
	 * @return Returns true every time the timer elapses, false otherwise.
	 */
	public static boolean apiRepeatingTimer(String id, double seconds)
	{
		return ApiTimeManager.repeatingTimer(id, (float)seconds);
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
     * The key is triggered only once when the key is first pressed.
     * Holding the key will return false -- the function only returns true once per key press.
     * (Note: the button 'tapped' and button 'down' or 'pressed' are different.)
     * @param key - the key on the keyboard. Ex: KeysEnum.a => the 'a' or 'A' key.
     * @return - true if the key is tapped, false otherwise
     */
	public boolean apiIsKeyboardButtonTapped(KeysEnum key) {
        return keyboard.isButtonTapped(keyEventMap[key.ordinal()]);  //ordinal is like indexOf for enums->ints
    }
	
	/**
     * Check if the given key is pressed on the keyboard. 
     * The key is triggered when the key is held down.
     * (Note: the button 'tapped' and button 'down' or 'pressed' are different.)
     * @param key - the key on the keyboard. Ex: KeysEnum.a => the 'a' or 'A' key.
     * @return - true if the key is pressed, false otherwise
     */
	public boolean apiIsKeyboardButtonPressed(KeysEnum key) {
        return keyboard.isButtonDown(keyEventMap[key.ordinal()]);  //ordinal is like indexOf for enums->ints
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
	public void apiStartDebrisSpawner(double interval)
	{
		ApiDebrisGenerator.enable();
		ApiDebrisGenerator.setInterval((float)interval);
	}

	/**
	 * Stops the automatic debris generator.
	 */
	public void apiStopDebrisSpawner()
	{
		ApiDebrisGenerator.disable();
	}

	/**
	 * Spawn a single debris at a random height
	 * @return an id of this debris
	 */
	public int apiSpawnSingleDebris()
	{
		return ApiDebrisGenerator.spawnDebris();
	}

	/**
	 * Spawn a single debris at a specific position, and disable initial movement
	 * @param x the x-coordinate position of the enemy
	 * @param y the y-coordinate position of the enemy
	 * @return an id of this debris
	 */
	public int apiSpawnSingleDebris(double x, double y)
	{
		int id = ApiDebrisGenerator.spawnDebris((float)x, (float)y);
		
		apiSetObjectVelocity(id, 0, 0);
		
		return id;
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
	public void apiStartEnemySpawner(double interval){
		ApiEnemyGenerator.enable();
		ApiEnemyGenerator.setInterval((float)interval);
	}
	
	/**
	 * Spawn a single random enemy at a random position on the right of the 
	 * game window
	 * @return an id of this enemy
	 */
	public int apiSpawnSingleEnemy(){
		return ApiEnemyGenerator.spawnEnemy();
	}
	
	/**
	 * Spawn a single random enemy at a specified position (x,y)
	 * @param x the x-coordinate position of the enemy
	 * @param y the y-coordinate position of the enemy
	 * @return an integer which represents the id of the enemy
	 */
	public int apiSpawnSingleEnemy(double x, double y){
		return ApiEnemyGenerator.spawnEnemy((float)x, (float)y);
	}

	/**
	 * Spawn a single enemy of the specified type at a random position on 
	 * the right of the game window
	 * @param type the type of the enemy as a string (case insensitive)
	 * @return an integer which represents the id of the enemy
	 */
	public int apiSpawnSingleEnemy(String type){
		return ApiEnemyGenerator.spawnEnemy(type);
	}
	
	/**
	 * Spawn an enemy with the specified type at the specified location (x, y)
	 * @param type the type of the enemy as a string (case insensitive)
	 * @param x the x-coordinate position of the enemy
	 * @param y the y-coordinate position of the enemy
	 * @return an integer which represents the id of the enemy
	 */
	public int apiSpawnSingleEnemy(String type, double x, double y){
		return ApiEnemyGenerator.spawnEnemy(type, (float)x, (float)y);
	}

	/**
	 * Disable the enemy spawner
	 */
	public void apiStopEnemySpawner(){
		ApiEnemyGenerator.disable();
	}
	
	//------------------ ENEMY end --------------------------------

	//-------------- COLLECTIBLES -----------------------------------
	
	/**
	 * Spawns DyePack object randomly on the right of the 
	 * game window every 2 seconds
	 */
	public void apiStartDyePackSpawner()
	{
		apiStartDyePackSpawner(2);
	}
	
	/**
	 * Spawns DyePack object randomly on the right of the
	 * game window with the specified interval
	 * @param interval the interval in second to spawns DyePack 
	 */
	public void apiStartDyePackSpawner(float interval)
	{
		ApiDyePackGenerator.initialize(100);
		ApiDyePackGenerator.setInterval(interval);
		ApiDyePackGenerator.setActive(true);
	}

	/**
	 * Disables the DyePack spawner
	 */
	public void apiStopDyePackSpawner()
	{
		ApiDyePackGenerator.setActive(false);
	}
	
	
	/**
	 * Spawns a single moving DyePack with a random height and color
	 * @return an integer which represents the id of the DyePack
	 */
	public int apiSpawnSingleDyePack(){
		
		int id = ApiDyePackGenerator.generateDyePack();
		return id;
	}
	
	/**
	 * Spawns a single DyePack with the specified color (as a String) and location
	 * and this also disables initial movement
	 * @param color [ "red" | "blue" | "green" | "teal" | "yellow" | "pink" ]. 
	 * Note: if the color specified is not in the list of color, function will pick red as the default
	 * @param x the x-coordinate position of the DyePack
	 * @param y the Y-coordinate position of the DyePack
	 * @return an integer which represents the id of the DyePack
	 */
	public int apiSpawnSingleDyePack(String color, double x, double y){
		int id = ApiDyePackGenerator.spawnDyePack(color, (float)x, (float)y);
		
		apiSetObjectVelocity(id, 0, 0);
		
		return id;
	}
	
	/**
	 * Spawns random PowerUp object at a specified location.
	 * Note: There are 4 types of PowerUp, SpeedUp, SlowDown, Ghost, and Invincibility
	 * @param positionX the x-coordinate position of the DyePack
	 * @param positionY the y-coordinate position of the DyePack
	 * @return an Integer to represent the ID of the PowerUp
	 */
	public int apiSpawnSinglePowerUp(double positionX, double positionY)
	{
		PowerUp spawned;
		switch (apiRandomInt(3))
		{
		case 0:
			spawned = new SpeedUp();
			break;
		case 1:
			spawned = new SlowDown();
			break;
		case 2:
			spawned = new Ghost();
			break;
		default:
			spawned = new Invincibility();
		}
		spawned.initialize(new Vector2((float)positionX, (float)positionY));
		return  ApiIDManager.register(spawned);
	}

	/**
	 * Spawn a PowerUp specified by the type at a specified location
	 * There are 4 types of PowerUp: SpeedUp, SlowDown, Ghost, and Invincibility
	 * @param type the type of the PowerUp as a string (case insensitive)
	 * @param positionX the x-coordinate position of the DyePack
	 * @param positionY the y-coordinate position of the DyePack
	 * @return an Integer to represent the ID of the PowerUp
	 */
	public int apiSpawnSinglePowerUp(String type, double positionX, double positionY)
	{
		PowerUp spawned;
		switch (type.toLowerCase())
		{
		case "speedup":
			spawned = new SpeedUp();
			break;
		case "slowdown":
			spawned = new SlowDown();
			break;
		case "ghost":
			spawned = new Ghost();
			break;
		case "invincibility":
			spawned = new Invincibility();
			break;
		default:
			// Defaults to invincibility and presents error message
			System.err.println("Unrecognized powerup type. Did you spell your input correctly?");
			System.err.println("Choose one of: 'SpeedUp' 'SlowDown' 'Ghost' 'Invincibility'");
			spawned = new Invincibility();
		}
		spawned.initialize(new Vector2((float)positionX, (float)positionY));
		return  ApiIDManager.register(spawned);
	}

	//-------------- COLLECTIBLES end -------------------------------

	//-------------- WORMHOLES / GATES --------------------------------------

	public void apiSpawnGates()
	{
		int safePortal = apiRandomInt(3);

		for (int i = 0; i < 4; i++)
		{
			Color c = i == safePortal ? apiGetHeroColor() : Colors.randomColor();
			apiAddOneWormHole(c, 40f, 13, 200f, 8 + (i * 15));
		}
	}
	
	/**
	 * Adds a WormHole to the game with the given Color, Width, Height, X-coordinate, Y-coordinate
	 * @param color the color of the WormHole
	 * @param width the width of the WormHole
	 * @param height the height of the WormHole
	 * @param x the X-coordinate of the center position of the WormHole
	 * @param y the Y-coordinate of the center position of the WormHole
	 */
	public void apiAddOneWormHole(Color color, double width, double height, double x, double y){
		WormHole wh = new WormHole(hero, color, (float)width, (float)height, (float)x, (float)y);
		wh.getPreview().overrideInvisible = true;
	}

	// -------------- WORMHOLES end ---------------------------------

	//-------------- WEAPONS -----------------------------------
	/**
	 * Switch the hero's weapon to Spread Fire weapon
	 */
	public void apiActivateSpreadFireWeapon(){
		// add a new weapon to the WeaponRack list
		//hero.registerWeapon(new SpreadFireWeapon(hero));
		hero.changeWeapon(new SpreadFireWeapon(hero));
	}
	
	/**
	 * Switch the hero's weapon to Limited Ammo weapon
	 */
	public void apiActivateLimitedAmmoWeapon(){
		hero.changeWeapon(new LimitedAmmoWeapon(hero));
	}
	
	/**
	 * Switch the hero's weapon to default (Over Head) weapon 
	 */
	public void apiDefaultWeapon(){
		hero.changeWeapon(new OverHeatWeapon(hero));
	}

	//-------------- WEAPONS end-----------------------------------
	
	
	//------------------ MENU / UI-------------------
	
	/**
	 * Displays the Winning menu on the screen
	 */
	public void apiShowWinMenu(boolean show){
		DyeHardSound.play(DyeHardSound.winSound);
		setState(State.GAMEOVER);
		endMenu.setMenu(true);	// True for win menu
		endMenu.active(show);
		if(show)
			endMenuActive = true;
		else
			endMenuActive = false;
	}
	
	/**
	 * Displays the Losing menu on the screen
	 */
	public void apiShowLoseMenu(boolean show){
		DyeHardSound.play(DyeHardSound.loseSound);
		setState(State.GAMEOVER);
		endMenu.setMenu(false);		// False for win menu
		endMenu.active(show);
		if(show)
			endMenuActive = true;
		else
			endMenuActive = false;
	}
	
	/**
	 * Displays the Start menu on the screen ("Click Anywhere to Start")
	 */
	public void apiShowStartScreen(){
		showStartMenu = true;
	}
	
	/**
	 * Displays the score UI on the game screen. 
	 * @param display true to display, false otherwise
	 */
	public void apiShowScore(boolean display){
		ui.displayScore(display);
	}
	
	/**
	 * Displays the distance meter on the game window
	 */
	public void apiShowDistanceMeter(){
		ui.displayDistanceMeter(GameState.TargetDistance);
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
