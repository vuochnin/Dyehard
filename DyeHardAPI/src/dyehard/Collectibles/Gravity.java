/*
 * 
 */
package dyehard.Collectibles;

import Engine.BaseCode;
import dyehard.Enums.PowerUpType;
import dyehard.Player.Hero;
import dyehard.Player.Hero.CurPowerUp;
import dyehard.Resources.ConfigurationFileParser;

// TODO: Auto-generated Javadoc
/**
 * The Class Gravity.
 */
public class Gravity extends PowerUp {

    /** The magnitude. */
    protected float magnitude = ConfigurationFileParser.getInstance()
    				.getPowerupType(PowerUpType.GRAVITY).getMagnitude();
//    protected float magnitude = ConfigurationFileParser
//            .getPowerUpData(ConfigurationFileParser.PowerUpType.GRAVITY).magnitude;

    /**
 * Instantiates a new gravity.
 */
public Gravity() {
        duration = ConfigurationFileParser.getInstance()
        		.getPowerupType(PowerUpType.GRAVITY).getDuration() * 100;       
        texture = BaseCode.resources.loadImage("Textures/PowerUp_Gravity.png");
        applicationOrder = 2;
        // label.setText("Gravity");
    }

    /* (non-Javadoc)
     * @see dyehard.Collectibles.PowerUp#apply(dyehard.Player.Hero)
     */
    @Override
    public void apply(Hero hero) {
        hero.currentGravity.set(0f, -magnitude);
        hero.curPowerUp = CurPowerUp.GRAVITY;
    }

    /* (non-Javadoc)
     * @see dyehard.Collectibles.PowerUp#unapply(dyehard.Player.Hero)
     */
    @Override
    public void unapply(Hero hero) {
        hero.currentGravity.set(0f, 0f);
        hero.curPowerUp = CurPowerUp.NONE;
    }

    /* (non-Javadoc)
     * @see dyehard.Collectibles.PowerUp#clone()
     */
    @Override
    public PowerUp clone() {
        return new Gravity();
    }

    /* (non-Javadoc)
     * @see dyehard.Collectibles.PowerUp#toString()
     */
    @Override
    public String toString() {
        return "Gravity: " + super.toString();
    }

}
