/**
 * File:		UserCode.java
 * Description: Simple UserCode example that initializes
 * 				the window to fullscreen, adds mouse and
 * 				key listeners, instantiates a Hero, and
 * 				creates a Robot object that moves the mouse
 * 				to the center of the hero.  In this example
 * 				You can move Dye and shoot. It is simple and 
 * 				shows you how to get started using the engine 
 * 				very quickly.
 * Author:		Paul Kessler
 * Date:		2/29/2016
 */
package TutorialFive;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import Engine.BaseCode;
import dyehard.DyeHardGame;
import dyehard.UpdateManager;
import dyehard.Collision.CollisionManager;
import dyehard.Player.Hero;
import dyehard.Resources.ConfigurationFileParser;
import dyehard.World.GameState;

public class UserCode extends DyeHardGame {

	private Hero hero;
	private EnemyGenerator enemyGenerator;
	
	@Override
	protected void initialize() {
		window.toggleFullscreen(); // Look how to run in windowed mode
		window.requestFocusInWindow();
        
        GameState.TargetDistance = ConfigurationFileParser.getInstance().getWorldData().getWorldMapLength();
        hero = new Hero();
        enemyGenerator = new EnemyGenerator(hero);
        
        // move the cursor to the center of Dye(Hero)
        try {
            Robot robot = new Robot();

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
        CollisionManager.getInstance().update();
        DyePackGenerator.getInstance().update();
        DebrisGenerator.getInstance().update();
        enemyGenerator.update();
        
        hero.moveTo(mouse.getWorldX(), mouse.getWorldY());

        if ((keyboard.isButtonDown(KeyEvent.VK_F))
                || (mouse.isButtonDown(1))) {
            hero.currentWeapon.fire();
        }
        
        
        //isButtonTapped requires extra registration via addKeyListener in initialize above!
        
        // press escape to exit the game
        if(keyboard.isButtonDown(KeyEvent.VK_ESCAPE)) {
        	window.close();
        }
	}
}
