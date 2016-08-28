import java.awt.Color;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.Enemies.Enemy;
import dyehard.Enums.EnemyType;
import dyehard.Player.Hero;
import dyehard.Resources.ConfigurationFileParser;

public class EnemyExample extends Enemy {

    /**
     * Instantiates a new regular enemy.
     *
     * @param center, the center of the enemy in worldspace
     * @param currentHero, the hero (Dye)
     * 
     * a few things need to be defined:
     * 
     * width		the width of the enemy
     * height		the height of the enemy
     * speed		the speed of the enemy
     * sleepTimer	the time before the enemy goes into CHASEHERO state,
     * 				reoccuring every sleepTimer milliseconds
     */
    public EnemyExample(Vector2 center, Hero currentHero) {
        
    	// super() calls the parent class' constructor.
    	// Uses the Regular_AnimSheet_Left texture
    	super(center, 0, 0, currentHero,
                "Textures/Enemies/Regular_AnimSheet_Left.png");
    	
    	// This is used for animation.
        setUsingSpriteSheet(true);
        setSpriteSheet(texture, 212, 170, 5, 5);
        
        // Add code here
    }

    /**
    * Update() is called every frame.
    * 
    * This is where the main Enemy logic happens. Each enemy has its own state,
    * either BEGIN, CHASEHERO, PLAYING, or DEAD. The state can be used to
    * group up logic within each state.
    * 
    * You can play around with flags and values, such as velocity, soundOn,
    * Texture, and more.
    */
    @Override
    public void update() {
    	// super.update() calls the parent Enemy.java's update() and its parent's
    	// update(), which is a Collidable. This is important for figuring out 
    	// the enemyState (Enemy) and applying proper collision (Collidable)
        super.update();

    }

    /**
     * setColor() is primarily used to dye a color when Dye shoots them.
     * 
     * For the most part, this code should be untouched... Unless you want the
     * enemy to perform a new specific action immediately if they are dyed.
     */
    @Override
    public void setColor(Color color) {
        if (this.color != color) {
            this.color = color;
            
            // Not sure what this is for
            int temp = getCurFrame();
            setCurFrame(temp);
        }
    }

    /**
     * Debug purposes
     */
    @Override
    public String toString() {
        return "Shooting - EnemyExample";
    }
}