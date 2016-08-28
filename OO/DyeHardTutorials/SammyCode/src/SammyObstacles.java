/**
 * File:		SammyObstacles.java
 * Description: This is a simple tutorial that showcases the basics of Dyehard.
 * 				This tutorial introduces collidable objects that Dye can
 * 				interact with.
 * Author:		Sammy Nimnuch
 * Date:		5/28/16
 */
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import Engine.BaseCode;
import dyehard.DyeHardGame;
import dyehard.UpdateManager;
import dyehard.Collision.CollisionManager;
import dyehard.Obstacles.Laser;
import dyehard.Player.Hero;
import dyehard.Resources.ConfigurationFileParser;
import dyehard.World.GameState;
import dyehard.Ui.*;

public class SammyObstacles extends DyeHardGame {

	private Hero hero;		// Dye, the hero
	private DyehardUI ui;	// Object to handle the UI
	private Laser laserObj; // The laser is this fiery-wall on the left side
							// of the screen which hurts Dye if she touches it.
	
	@Override
	protected void initialize() {
		// Automatically full screen, TODO: make it the active window
	 	window.toggleFullscreen();
		window.requestFocusInWindow();
		
		// TODO: Figure out why targetdistance is important
        GameState.TargetDistance = ConfigurationFileParser.getInstance().getWorldData().getWorldMapLength();
        
        // Instantiate hero, UI elements, and Laser
        hero = new Hero();
        ui = new DyehardUI(hero);
        laserObj = new Laser(hero);
        
        
        // move the cursor to the center of Dye(Hero)
        try {
            Robot robot = new Robot();
            robot.mouseMove( (int) hero.center.getX(), (int) hero.center.getY());
        } catch (AWTException e) {
            e.printStackTrace();
        }
        
	}

	@Override
	protected void update() {
		keyboard.update();
		
        UpdateManager.getInstance().update();
        DyePackGenerator.getInstance().update();
        CollisionManager.getInstance().update();
        DebrisGenerator.getInstance().update();
                       
        hero.moveTo(mouse.getWorldX(), mouse.getWorldY());

        if ((keyboard.isButtonDown(KeyEvent.VK_F))
                || (mouse.isButtonDown(1))) {
            hero.currentWeapon.fire();
        }
                
        // press escape to exit the game
        if(keyboard.isButtonDown(KeyEvent.VK_ESCAPE)) {
        	window.close();
        }
        
	}
}
