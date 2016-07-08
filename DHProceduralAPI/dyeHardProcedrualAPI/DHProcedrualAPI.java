package dyeHardProcedrualAPI;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Random;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.DyeHardGame;
import dyehard.UpdateManager;
import dyehard.Collision.CollisionManager;
import dyehard.Resources.ConfigurationFileParser;
import dyehard.Ui.DyehardUI;
import dyehard.World.GameState;
import dyehard.Player.Hero;
import dyehard.Weapons.*;
//import dyehard.Updateable;

/**
 * @author vuochnin
 * @author Holden
 */
public class DHProcedrualAPI extends DyeHardGame{
	
	private Hero hero;
	private EnemyGenerator enemyGenerator;
	
	/**
	 * @Override 
	 * Must override the initialize() method from the abstract super class, DyeHardGame
	 */
	public void initialize(){
		window.requestFocusInWindow();
		setGoalDistance();
		buildGame();
	}
	
	public void buildGame(){
		startHero();
		startDebrisSpawner();
		//startEnemySpawner();
		for(float i = 0; i < 60; i += 5)
		{
			//spawnSingleDebris(i);
		}
	}
	
	/**
	 * @Override 
	 * Must override the update() method from the abstract super class, DyeHardGame
	 */
	public void update(){
		keyboard.update();
		UpdateManager.getInstance().update();
		CollisionManager.getInstance().update();
		DebrisGenerator.update();
		enemyGenerator.update();
		
		updateGame();
	}
	
	public void updateGame()
	{
		heroFollowTheMouse();
		
		// TEST Change the weapon according to the keyboard inputs
		if(isKeyboardLeftPressed()){
			activateSpreadFireWeapon();
		}
		if(isKeyboardRightPressed()){
			activateLimitedAmmoWeapon();
		}
		if(isKeyboardDownPressed()){
			defaultWeapon();
		}
		
		if(isKeyboardUpPressed()){
			spawnSingleEnemy(25);			//TEST SpawnSingleEnemy()
		}
		
		// Fire the paint
		if(isMouseLeftClicked() || isKeyboardButtonDown(KeysEnum.SPACE)){
			firePaint();
		}
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
	
	public void moveTo(float x, float y){
		hero.moveTo(x, y);
	}
	
	// Make hero follow the mouse movement
	public void heroFollowTheMouse(){
		moveTo(mousePositionX(), mousePositionY());
	}
	
	public void moveLeft()
	{
		Vector2 result = hero.center;
		result.add( new Vector2(-1,0));
		moveTo(result.getX(), result.getY());
	}
	
	public void moveRight()
	{
		Vector2 result = hero.center;
		result.add( new Vector2(1,0));
		moveTo(result.getX(), result.getY());
	}	
	public void moveUp()
	{
		Vector2 result = hero.center;
		result.add( new Vector2(0,1));
		moveTo(result.getX(), result.getY());
	}
	
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
     * API utility method
     * Check if the key we're investigating is currently being pressed
     * (Source: SpaceSmasherFunctionalAPI/SpaceSmasherProceduralAPI)
     * @param key - the key we're investigating
     * @return - true if the key we're investigating is currently being pressed
     */
	public boolean isKeyboardButtonDown(KeysEnum key) {
        return keyboard.isButtonDown(keyEventMap[key.ordinal()]);  //ordinal is like indexOf for enums->ints
    }
	
	/**
	 * Check if the LEFT arrow on the keyboard is pressed
	 * @return true if the left arrow key on the keyboard is pressed
	 */
	public boolean isKeyboardLeftPressed(){
		return isKeyboardButtonDown(KeysEnum.LEFT);
	}
	
	/**
	 * Check if the RIGHT arrow on the keyboard is pressed
	 * @return true if the right arrow key on the keyboard is pressed
	 */
	public boolean isKeyboardRightPressed(){
		return isKeyboardButtonDown(KeysEnum.RIGHT);
	}
	
	/**
	 * Check if the UP arrow on the keyboard is pressed
	 * @return true if the up arrow key on the keyboard is pressed
	 */
	public boolean isKeyboardUpPressed(){
		return isKeyboardButtonDown(KeysEnum.UP);
	}
	
	/**
	 * Check if the DOWN arrow on the keyboard is pressed
	 * @return true if the down arrow key on the keyboard is pressed
	 */
	public boolean isKeyboardDownPressed(){
		return isKeyboardButtonDown(KeysEnum.DOWN);
	}
	// ---------------- MOUSE / KEYBOARD end ----------------------------
	
	// ---------- Utilities functions ------------

//	/**
//	 * Set the number of lives the hero has
//	 * @param numOfLives The new number of lives
//	 */
//	public void setLives(int numOfLives){
//		//GameState.RemainingLives = numOfLives;
//	}
	
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
	
	public void spawnSingleEnemy(){
		enemyGenerator.spawnEnemy();
	}
	
	public void spawnSingleEnemy(float height){
		enemyGenerator.spawnEnemy(height);
	}
	
	/**
	 * Disable the enemy spawner
	 */
	public void stopEnemySpawner(){
		EnemyGenerator.disable();
	}
	//------------------ ENEMY end --------------------------------
	
	
	
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
