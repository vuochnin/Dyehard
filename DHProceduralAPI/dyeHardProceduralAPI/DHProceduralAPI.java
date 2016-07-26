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
 * @author vuochnin
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
	private LogScreen start;			// "Click Anywhere to Start"
	
	/**
	 * @Override 
	 * Must override the initialize() method from the abstract super class, DyeHardGame
	 */
	public void initialize(){
		CollisionManager.register(this);

		window.requestFocusInWindow();
		apiSetGoalDistance();
		endMenu = new DyehardEndMenu();
		start = new LogScreen();
		buildGame();
		apiShowStartScreen();
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
	        IDManager.collectStrayObjects();

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
        	window.close();
        	break;
        case RESTART:
        	apiRestartGame();
        	break;
        default:
        	break;
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
	 * @return the number of objects in play
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
	public void apiMoveObjectTo(int id, double x, double y){
		if(apiGetType(id) == "Hero")
			hero.moveTo((float)x, (float)y);
		else
			IDManager.get(id).center = (new Vector2((float)x,(float)y));
	}
	
	
	
	// -------------------- Utilities functions ------------------

	/**
	 * Gets a random DyeHard color (blue, red, yellow, teal, pink, green) 
	 * @return a random DyeHard color
	 */
	public Color apiGetRandomColor(){
		return Colors.randomColor();
	}
	
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
		endMenu.active(false);
    	setState(State.PLAYING);
    	background.destroy();
		background = new BackgroundScreen();
        distance = 0;
        GameState.DistanceTravelled = 0;
        GameState.Score = 0;
        hero.center = hero.getStart();

        TimeManager.reset();
        
        IDManager.reset();
        System.gc();
        buildGame();
	}
	
	public boolean apiUserLose(){
		return (GameState.RemainingLives <= 0);
	}
	
	public boolean apiUserWon(){
		if(GameState.DistanceTravelled >= GameState.TargetDistance)
			return true;
		else{
			if(winningScore > 0)
				return GameState.Score >= winningScore;
			return false;
		}	
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
	public void apiMoveObject(int id, double deltaX, double deltaY){
		CollidableGameObject obj = IDManager.get(id);
		
		obj.center = new Vector2(obj.center.getX() + (float) deltaX,obj.center.getY() + (float) deltaY);
	}
	
	/**
	 * Sets a game object's velocity.
	 * @param id the id of the object to be moved
	 * @param x horizontal velocity
	 * @param y vertical velocity
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
	
	public void apiSetWinningScore(int winScore){
		winningScore = winScore;
	}
	
	public void apiLoseALife(){
		GameState.RemainingLives--;
	}
	
	public void apiSpeedUp(boolean up){
		UpdateManager.getInstance().setSpeedUp(up);
		if(up)
			distance += (Speed * 3f); 	// Each time the method triggered, the distance increase by 3 times the speed
			GameState.DistanceTravelled = (int) distance;
	}
	
	/**
	 * Sets the number of lives the hero has to the number, n
	 * and displays to the screen
	 * @param n the number of lives to set to
	 */
	public void apiSetLivesTo(int n){
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
	 * <br><br>
	 * If there is already a timer associated with the ID,<br>
	 * * Resets the timer.
	 *
	 * @param id The string ID
	 * @param seconds The length of the timer in seconds
	 */
	public void apiSetSingleTimer(String id, double seconds)
	{
		TimeManager.setTimer(id, (float)seconds);
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
	public static boolean apiRepeatingTimer(String id, double seconds)
	{
		return TimeManager.repeatingTimer(id, (float)seconds);
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
	public void apiStartDebrisSpawner(double interval)
	{
		DebrisGenerator.enable();
		DebrisGenerator.setInterval((float)interval);
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
	public int apiSpawnSingleDebris(double height)
	{
		return DebrisGenerator.spawnDebris((float)height);
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
	public void apiStartEnemySpawner(double interval){
		EnemyGenerator.enable();
		EnemyGenerator.setInterval((float)interval);
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
	public int apiSpawnSingleEnemy(double x, double y){
		return EnemyGenerator.spawnEnemy((float)x, (float)y);
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
	public int apiSpawnSingleEnemy(String type, double x, double y){
		return EnemyGenerator.spawnEnemy(type, (float)x, (float)y);
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
	public int apiSpawnSingleDyePack(String color, double x, double y){
		return DyePackGenerator.spawnDyePack(color, (float)x, (float)y);
	}
	
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
			// Defaults to invincibility
			spawned = new Invincibility();
		}
		spawned.initialize(new Vector2((float)positionX, (float)positionY));
		return  IDManager.register(spawned);
	}

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
		default:
			// Defaults to invincibility
			spawned = new Invincibility();
		}
		spawned.initialize(new Vector2((float)positionX, (float)positionY));
		return  IDManager.register(spawned);
	}

	//-------------- COLLECTIBLES end -------------------------------

	//-------------- WORMHOLES / GATES --------------------------------------

	public void apiSpawnGates()
	{
		int safePortal = apiRandomInt(3);

		for (int i = 0; i < 4; i++)
		{
			Color c = i == safePortal ? apiGetHeroColor() : Colors.randomColor();
			apiAddOneWormHole(c, 40f, 15f, 120f, 5f + (i * 15));
		}
	}
	
	/**
	 * Adds a WormHole to the game with the given Color, Width, Height, X-coordinate, Y-coordinate
	 * @param color the color of the WormHole
	 * @param width the width of the WormHole
	 * @param height the height of the WormHole
	 * @param x the X-coordinate position of the WormHole
	 * @param y the Y-coordinate position of the WormHole
	 */
	public void apiAddOneWormHole(Color color, double width, double height, double x, double y){
		new WormHole(hero, color, (float)width, (float)height, (float)x, (float)y);
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
	
	public void apiShowDistanceMeter(boolean show){
		ui.displayDistanceMeter(show, GameState.TargetDistance);
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
