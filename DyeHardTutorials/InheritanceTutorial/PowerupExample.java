/**
 * The Class PowerupTemplate
 * 
 * PowerupTemplate is a blank class file that shows the required functions
 * needed to create a new Powerup.
 */

package InheritanceTutorial;

import Engine.BaseCode;
import dyehard.Collectibles.PowerUp;
import dyehard.Enums.PowerUpType;
import dyehard.Player.Hero;
import dyehard.Player.Hero.CurPowerUp;
import dyehard.Resources.ConfigurationFileParser;


public class PowerupExample extends PowerUp {

    /**
     * Default Constructor.
     * 
     * A few variables need to be specified:
     * 
     * duration				A set duration for the effect (multiplied by 1000).
     * texture				The location of the powerup's image.
     * applicationorder		Not sure what this is. Make it 100 for now.
     */
    public PowerupExample() {
    	// super() calls the Superclass (Powerup)'s default constructor.
    	// Please refer to PowerUp.java to see what exactly what it does.
        super();
        
        // Add code here
        
        
    }

    /** 
     * Copy Constructor
     * 
     * Create a copy of the PowerupExample object.
     */
    @Override
    public PowerUp clone() {
        return new PowerupExample();
    }

    /** 
     * apply()
     * 
     * This function applies the effect of the Powerup to the hero object, who
     * is passed in as an argument to this function. This is where the magic
     * happens -- by altering the hero object's properties!
     * 
     * For example, setting "hero.damageOn" to false makes Dye invulnerable to 
     * damage for the duration of the powerup.
     */
    @Override
    public void apply(Hero hero) {
    	// This is used to specify the hero's current state.
    	hero.curPowerUp = CurPowerUp.POWERUPEXAMPLE;
    	
        // Add code here
    	
    }

    /**
     * unapply()
     * 
     * This function will unapply the effects of the Powerup to the hero object.
     * This should be called when the powerup reaches the end of its duration.
     * 
     * Note that whatever was changed in apply() must be reverted here.
     */
    @Override
    public void unapply(Hero hero) {
    	// This is used to specify the hero's current state.
        hero.curPowerUp = CurPowerUp.NONE;
        
        // Add code here. 
        
    }

    /**
     * toString()
     * 
     * This is mainly for debugging and logging purposes. This is to show
     * the powerup type and the remaining duration (according to the 
     * super class' toString() method).
     */
    @Override
    public String toString() {
        return "PowerupExample: " + super.toString();
    }
}
