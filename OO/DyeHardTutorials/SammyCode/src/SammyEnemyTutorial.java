/**
 * File:		SammyEnemyTutorial.java
 * Description: This is a simple tutorial that showcases the basics of Dyehard.
 * 				This tutorial introduces enemies
 * 				within the code.	
 * Author:		Sammy Nimnuch
 * Date:		5/28/16
 */
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import Engine.BaseCode;
//import Engine.Vector2;
import dyehard.DyeHardGame;
import dyehard.UpdateManager;
import dyehard.Collision.CollisionManager;
import dyehard.Player.Hero;
import dyehard.Resources.ConfigurationFileParser;
import dyehard.World.GameState;
import dyehard.Ui.*;
import dyehard.Enemies.*;

public class SammyEnemyTutorial extends DyeHardGame {

	private Hero hero;		// Dye, the hero
	private DyehardUI ui;	// Object to handle the UI
	
	private EnemyGenerator eGen; // Object to spawn enemies
	
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
        
	}

	@Override
	protected void update() {
		keyboard.update();
		
        UpdateManager.getInstance().update();
        DyePackGenerator.getInstance().update();
        CollisionManager.getInstance().update();
                       
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
