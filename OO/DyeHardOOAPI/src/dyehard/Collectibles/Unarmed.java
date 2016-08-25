/*
 * 
 */
package dyehard.Collectibles;

import Engine.BaseCode;
import dyehard.Enums.PowerUpType;
import dyehard.Player.Hero;
import dyehard.Player.Hero.CurPowerUp;
import dyehard.Resources.ConfigurationFileParser;
import dyehard.Weapons.BrokenWeapon;

// TODO: Auto-generated Javadoc
/**
 * The Class Unarmed.
 */
public class Unarmed extends PowerUp {
	
    /**
     * Instantiates a new unarmed.
     */
    public Unarmed() {
        super();
        duration = ConfigurationFileParser.getInstance().getPowerupType(PowerUpType.UNARMED).getDuration() * 1000;
        texture = BaseCode.resources.loadImage("Textures/PowerUp_Unarmed.png");
        timer.setInterval(duration);
        applicationOrder = 0;
        // label.setText("Unarmed");
    }

    /* (non-Javadoc)
     * @see dyehard.Collectibles.PowerUp#apply(dyehard.Player.Hero)
     */
    @Override
    public void apply(Hero hero) {
        hero.currentWeapon = new BrokenWeapon(hero);
        hero.curPowerUp = CurPowerUp.UNARMED;
    }

    /* (non-Javadoc)
     * @see dyehard.Collectibles.PowerUp#unapply(dyehard.Player.Hero)
     */
    @Override
    public void unapply(Hero hero) {
        hero.currentWeapon = hero.defaultWeapon;
        hero.curPowerUp = CurPowerUp.NONE;
    }

    /* (non-Javadoc)
     * @see dyehard.Collectibles.PowerUp#clone()
     */
    @Override
    public PowerUp clone() {
        return new Unarmed();
    }

    /* (non-Javadoc)
     * @see dyehard.Collectibles.PowerUp#toString()
     */
    @Override
    public String toString() {
        return "Unarmed: " + super.toString();
    }
}
