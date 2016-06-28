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
import dyehard.Enemies.ChargerEnemy;
import dyehard.Enemies.CollectorEnemy;
import dyehard.Enemies.EnemyManager;
import dyehard.Enemies.RegularEnemy;
import dyehard.Enemies.ShootingEnemy;
import dyehard.Resources.ConfigurationFileParser;
import dyehard.World.GameState;
import dyehard.Player.Hero;
import dyehard.Util.Timer;
import dyehard.Weapons.*;
//import dyehard.Updateable;

/**
 * @author vuochnin
 *
 */
public class DHProcedrualAPI extends DyeHardGame{
	
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
	
	private Hero hero;
	//private static Timer timer;
	
	/**
	 * @Override 
	 * Must override the initialize() method from the abstract super class, DyeHardGame
	 */
	public void initialize(){
		// initializeGame() will be added later
		
		
		requestFocusInWindow();
		
		setTargetDistance();
		hero = addNewHero();
		
		// move the cursor to the center of the hero
		setCursorToCenterOfHero();
		
		
		//timer = new Timer(2000);
	}
	
	
	/**
	 * @Override 
	 * Must override the update() method from the abstract super class, DyeHardGame
	 */
	public void update(){
		// buildGame() will be added later
		
		
		// following the DyeHardUser code
		keyboardUpdate();
		heroFollowTheMouse();
		activateUpdateManager();
		handleCollisions();
		
		if(isMouseLeftClicked()){
			firePaint();
		}
		
	}
	
//----------------------SOME POSSIBLE PROCEDURAL FUNCTIONS -----------------------------------
	/**
	 * Fire the current weapon
	 */
	public void firePaint(){								// Fire the paint
		hero.currentWeapon.fire();
	}
	
	public Hero addNewHero(){					// Create new Hero
		return new Hero();
	}
	
	// Make hero follow the mouse movement
	protected void heroFollowTheMouse(){
		moveHero(mouse.getWorldX(), mouse.getWorldY());
	}
	
	public void moveHero(float x, float y){
		hero.moveTo(x, y);
	}
	
	/**
	 * Check if the left mouse button is pressed
	 * @return	true/false
	 */
	public boolean isMouseLeftClicked(){
		return (mouse.isButtonDown(1));
	}
	
	/**
     * API utility method
     * Check if the key we're investigating is currenly being pressed
     * (Source: SpaceSmasherFunctionalAPI/SpaceSmasherProceduralAPI)
     * @param key - the key we're investigating
     * @return - true if the key we're investigating is currently being pressed
     */
	public boolean isKeyboardButtonDown(KeysEnum key) {
        return keyboard.isButtonDown(keyEventMap[key.ordinal()]);  //ordinal is like indexOf for enums->ints
    }
	
	
	// ---------- Utilities functions ------------
	public void requestFocusInWindow(){
		window.requestFocusInWindow();
	}
	
	public void setTargetDistance(){
		GameState.TargetDistance = ConfigurationFileParser.getInstance().getWorldData().getWorldMapLength();
	}
	
	public void setCursorToCenterOfHero(){
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
	
	protected void keyboardUpdate(){	// following the DyeHardUser code
		keyboard.update();
	}
	
	public void handleCollisions(){					// Handle collisions
		CollisionManager.getInstance().update();
	}
	
	public void activateUpdateManager(){			// Handle UpdateManager
		UpdateManager.getInstance().update();
	}
	
	// ---------- Utilities functions end ------------
	
	
	
	//-------------- WEAPONS -----------------------------------
	public void addSpreadFireWeapon(){
		hero.registerWeapon(new SpreadFireWeapon(hero));
	}
	
	public void addOverHeatWeapon(){
		hero.registerWeapon(new OverHeatWeapon(hero));
	}
	
	public void addLimitedAmmoWeapon(){
		hero.registerWeapon(new LimitedAmmoWeapon(hero));
	}
	//-------------- WEAPONS end-----------------------------------
}
