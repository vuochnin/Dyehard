/**
 * File:		UserCode.java
 * Description: Simple UserCode example that initializes the window to 
 * 				fullscreen, adds mouse and key listeners, instantiates a 
 * 				Hero, andcreates a Robot object that moves the mouse to the
 * 				center of the hero.  In this example You can move Dye and
 * 				shoot.It is simple and shows you how to get started using the
 * 				engine very quickly. 		
 * Author:		Paul Kessler
 * Date:		2/29/2016
 */
package SammyCode;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import Engine.BaseCode;
//import Engine.Vector2;
import dyehard.DyeHardGame;
import dyehard.UpdateManager;
import dyehard.Player.Hero;
import dyehard.Resources.ConfigurationFileParser;
import dyehard.Util.Colors;
import dyehard.World.GameState;
import dyehard.World.WormHole;

public class UserCode extends DyeHardGame {

	private Hero hero;
	
	@Override
	protected void initialize() {
		// Automatically full screen, and make it the active window
	 	window.toggleFullscreen();
		window.requestFocusInWindow();
		
		// TODO: Figure out what file is being parsed
        GameState.TargetDistance = ConfigurationFileParser.getInstance().getWorldData().getWorldMapLength();
        
        hero = new Hero();
        
        // TODO: Why figure all this out? Why not just hero.center.getX()?
        // move the cursor to the center of Dye(Hero)
        try {
            Robot robot = new Robot();
            //robot.mouseMove((int) hero.center.getX(), (int)hero.center.getY());

            robot.mouseMove(
                    window.getLocationOnScreen().x
                            + (int) (hero.center.getX() * window.getWidth() / BaseCode.world
                                    .getWidth()),
                    window.getLocationOnScreen().y
                            + window.getHeight()
                            - (int) (hero.center.getX() * window.getWidth() / BaseCode.world
                                    .getWidth()));
        } catch (AWTException e) {
            e.printStackTrace();
        }
       
	}

	@Override
	protected void update() {
		keyboard.update();
		
        UpdateManager.getInstance().update();
                
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
