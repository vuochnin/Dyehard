/**
 * File:		SammyEnemyTutorial.java
 * Description: This is a simple tutorial that showcases the basics of Dyehard.
 * 				This tutorial introduces enemies
 * 				within the code.	
 * Author:		Sammy Nimnuch
 * Date:		5/28/16
 */
package SammyCode;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import Engine.BaseCode;
import TutorialFive.DebrisGenerator;
import TutorialFive.DyePackGenerator;
//import Engine.Vector2;
import dyehard.DyeHardGame;
import dyehard.UpdateManager;
import dyehard.Collision.CollisionManager;
import dyehard.Player.Hero;
import dyehard.Resources.ConfigurationFileParser;
import dyehard.World.GameState;
import dyehard.World.WormHole;
import dyehard.Ui.*;
import dyehard.Util.Colors;
import dyehard.Enemies.*;
import dyehard.Obstacles.Laser;

public class SammyAllTogether extends DyeHardGame {

	private Hero hero;		// Dye, the hero
	private DyehardUI ui;	// Object to handle the UI
	private EnemyGenerator eGen; // Object to spawn enemies
	private Laser laserObj; // The laser is this fiery-wall on the left side
	
	@Override
	protected void initialize() {
		// Automatically full screen, TODO: make it the active window
	 	window.toggleFullscreen();
		window.requestFocusInWindow();
		
		// TODO: Figure out why targetdistance is important
        GameState.TargetDistance = ConfigurationFileParser.getInstance().getWorldData().getWorldMapLength();
        
        // Instantiate hero, UI elements, and Enemy Generator
        hero = new Hero();
        ui = new DyehardUI(hero);
        eGen = new EnemyGenerator(hero);
        
        // move the cursor to the center of Dye(Hero)
        try {
            Robot robot = new Robot();
            robot.mouseMove( (int) hero.center.getX(), (int) hero.center.getY());
        } catch (AWTException e) {
            e.printStackTrace();
        }
        
        // Wormholes
        WormHole wh1 = new WormHole(hero, Colors.Blue, 30f, 15f, 150f, 0f);
        WormHole wh2 = new WormHole(hero, Colors.Red, 30f, 15f, 150f, 15f);
        WormHole wh3 = new WormHole(hero, Colors.Green, 30f, 15f, 150f, 30f);
        WormHole wh4 = new WormHole(hero, Colors.Yellow, 30f, 15f, 150f, 45f);
        WormHole wh5 = new WormHole(hero, Colors.Teal, 30f, 15f, 150f, 60f);
        
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
        
        eGen.update(); // This will spawn enemies on its own
	}
}
