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
 * The Class Invincibility.
 */
public class Invincibility extends PowerUp {

    // public static PowerUpMeter meter = new PowerUpMeter(2, Game.Pink);

    /**
     * Instantiates a new invincibility.
     */
    public Invincibility() {
        super();
        duration = ConfigurationFileParser.getInstance()
        		.getPowerupType(PowerUpType.INVINCIBILITY).getDuration() * 1000;
        texture = BaseCode.resources
                .loadImage("Textures/PowerUp_Invincibility.png");
        applicationOrder = 100;
        // label.setText("Invin");
    }

    /* (non-Javadoc)
     * @see dyehard.Collectibles.PowerUp#apply(dyehard.Player.Hero)
     */
    @Override
    public void apply(Hero hero) {
        hero.damageOn = false;
        // hero.texture = BaseCode.resources
        // .loadImage("Textures/Hero/Dye_Invincible.png");
        // hero.size.set(Configuration.heroWidth * 2,
        // Configuration.heroHeight * 1.3333f);
        hero.isInvin = true;
        hero.curPowerUp = CurPowerUp.INVIN;
    }

    /* (non-Javadoc)
     * @see dyehard.Collectibles.PowerUp#unapply(dyehard.Player.Hero)
     */
    @Override
    public void unapply(Hero hero) {
        // hero.texture = BaseCode.resources.loadImage("Textures/Hero/Dye.png");
        // hero.size.set(Configuration.heroWidth, Configuration.heroHeight);
        hero.isInvin = false;
        hero.curPowerUp = CurPowerUp.NONE;
        hero.damageOn = true;
    }

    /* (non-Javadoc)
     * @see dyehard.Collectibles.PowerUp#clone()
     */
    @Override
    public PowerUp clone() {
        return new Invincibility();
    }

    /* (non-Javadoc)
     * @see dyehard.Collectibles.PowerUp#toString()
     */
    @Override
    public String toString() {
        return "Invincibility: " + super.toString();
    }
}
