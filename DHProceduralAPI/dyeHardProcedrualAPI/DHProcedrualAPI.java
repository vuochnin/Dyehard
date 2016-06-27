package dyeHardProcedrualAPI;

import java.awt.AWTException;
import java.awt.Robot;
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
	
	private Hero hero;
	private static Timer timer;
	
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
	
	// ---------- Utilities functions ------------
	public void handleCollisions(){					// Handle collisions
		CollisionManager.getInstance().update();
	}
	
	public void activateUpdateManager(){			// Handle UpdateManager
		UpdateManager.getInstance().update();
	}
	
	protected void keyboardUpdate(){
		keyboard.update();
	}
	
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
