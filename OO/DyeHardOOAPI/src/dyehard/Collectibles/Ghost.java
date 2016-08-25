package dyehard.Collectibles;

import Engine.BaseCode;
import dyehard.Enums.PowerUpType;
import dyehard.Player.Hero;
import dyehard.Player.Hero.CurPowerUp;
import dyehard.Resources.ConfigurationFileParser;

/**
 * The Class Ghost.
 * The Ghost Powerup makes Dye invulnerable to damage, and turns off her 
 * collision, allowing her to pass freely through objects.
 */
public class Ghost extends PowerUp {
    // public static PowerUpMeter meter = new PowerUpMeter(1, Game.Blue);

    /**
     * Instantiates a new ghost objectg.
     */
    public Ghost() {
        super();
        duration = ConfigurationFileParser.getInstance().getPowerupType(PowerUpType.GHOST).getDuration() * 1000;
        texture = BaseCode.resources.loadImage("Textures/PowerUp_Ghost.png");
        applicationOrder = 90;
        // label.setText("Ghost");
    }

    /* (non-Javadoc)
     * @see dyehard.Collectibles.PowerUp#clone()
     */
    @Override
    public PowerUp clone() {
        return new Ghost();
    }

    /* (non-Javadoc)
     * @see dyehard.Collectibles.PowerUp#apply(dyehard.Player.Hero)
     */
    @Override
    public void apply(Hero hero) {
        hero.collisionOn = false;
        hero.damageOn = false;
        hero.curPowerUp = CurPowerUp.GHOST;
    }

    /* (non-Javadoc)
     * @see dyehard.Collectibles.PowerUp#unapply(dyehard.Player.Hero)
     */
    @Override
    public void unapply(Hero hero) {
        hero.collisionOn = true;
        hero.damageOn = true;
        hero.curPowerUp = CurPowerUp.NONE;
    }

    /* (non-Javadoc)
     * @see dyehard.Collectibles.PowerUp#toString()
     */
    @Override
    public String toString() {
        return "Ghost: " + super.toString();
    }
}
