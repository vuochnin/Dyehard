/**
 * File:		SammyCode2.java
 * Description: This is a simple tutorial that showcases the basics of Dyehard.
 * 				Compared to SammyCode.java, SammyCode2.java introduces the
 * 				logic of wormholes.
 * 				within the code.	
 * Author:		Sammy Nimnuch
 * Date:		5/28/16
 */
package SammyCode;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import Engine.BaseCode;
import TutorialFive.DyePackGenerator;
//import Engine.Vector2;
import dyehard.DyeHardGame;
import dyehard.UpdateManager;
import dyehard.Collision.CollisionManager;
import dyehard.Player.Hero;
import dyehard.Resources.ConfigurationFileParser;
import dyehard.Util.Colors;
import dyehard.World.GameState;
import dyehard.World.WormHole;
import dyehard.Ui.*;

public class SammyWormholesTutorial extends DyeHardGame {

	private Hero hero;		// Dye, the hero
	private DyehardUI ui;	// Object to handle the UI
	
	@Override
	protected void initialize() {
		// Automatically full screen, TODO: make it the active window
	 	window.toggleFullscreen();
		window.requestFocusInWindow();
		
		// TODO: Figure out why targetdistance is important
        GameState.TargetDistance = ConfigurationFileParser.getInstance().getWorldData().getWorldMapLength();
        
        // Instantiate hero and UI elements
        hero = new Hero();
        ui = new DyehardUI(hero);
        
        // move the cursor to the center of Dye(Hero)
        try {
            Robot robot = new Robot();
            robot.mouseMove( (int) hero.center.getX(), (int) hero.center.getY());
        } catch (AWTException e) {
            e.printStackTrace();
        }
        
        //WormHole testHole = new WormHole(hero, Color.BLUE, 5f, 5f, 0f, 5f);
        // NOTE: You NEED to use Dyehard.Util's Color. Cannot use Java.awt
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
