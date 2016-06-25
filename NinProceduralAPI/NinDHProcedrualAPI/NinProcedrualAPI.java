package NinDHProcedrualAPI;

import java.awt.AWTException;
import java.awt.Robot;

import Engine.BaseCode;
import dyehard.DyeHardGame;
import dyehard.UpdateManager;
import dyehard.Collision.CollisionManager;
import dyehard.Resources.ConfigurationFileParser;
import dyehard.World.GameState;
import dyehard.Player.Hero;

/**
 * @author vuochnin
 *
 */
public class NinProcedrualAPI extends DyeHardGame{
	
	private Hero hero;
	
	/**
	 * @Override 
	 * Must override the initialize() method from the abstract super class, DyeHardGame
	 */
	public void initialize(){
		// initializeGame() will be added later
		
		
		window.requestFocusInWindow();
		
		GameState.TargetDistance = ConfigurationFileParser.getInstance().getWorldData().getWorldMapLength();
		hero = new Hero();
		
		// move the cursor to the center of the hero
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
	 * @Override 
	 * Must override the update() method from the abstract super class, DyeHardGame
	 */
	public void update(){
		// buildGame() will be added later
		
		keyboard.update();
		// Make hero follow the mouse movement
		hero.moveTo(mouse.getWorldX(), mouse.getWorldY());
		
		//UpdateManager.getInstance().update();
		//CollisionManager.getInstance().update();
		
		if(isLeftMouseClicked()){
			fire();
		}
	}
	
	/**
	 * Check if the left mouse button is pressed
	 * @return	true/false
	 */
	public boolean isLeftMouseClicked(){
		return (mouse.isButtonDown(1));
	}
	
	/**
	 * Fire the current weapon
	 */
	public void fire(){
		hero.currentWeapon.fire();
	}
	
}
