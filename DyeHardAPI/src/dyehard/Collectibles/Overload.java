/*
 * 
 */
package dyehard.Collectibles;

import Engine.BaseCode;
import dyehard.Enums.PowerUpType;
import dyehard.Player.Hero;
import dyehard.Player.Hero.CurPowerUp;
import dyehard.Resources.ConfigurationFileParser;
import dyehard.Weapons.LimitedAmmoWeapon;
import dyehard.Weapons.OverHeatWeapon;

// TODO: Auto-generated Javadoc
/**
 * The Class Overload.
 */
public class Overload extends PowerUp {
    // public static PowerUpMeter meter = new PowerUpMeter(2, Game.Pink);

    /**
     * Instantiates a new overload.
     */
    public Overload() {
        super();
        duration = ConfigurationFileParser.getInstance()
        			.getPowerupType(PowerUpType.OVERLOAD).getDuration() * 1000;
        texture = BaseCode.resources.loadImage("Textures/PowerUp_Overload.png");
        applicationOrder = 30;
        // label.setText("Overload");
    }

    /* (non-Javadoc)
     * @see dyehard.Collectibles.PowerUp#apply(dyehard.Player.Hero)
     */
    @Override
    public void apply(Hero hero) {
        hero.curPowerUp = CurPowerUp.OVERLOAD;
        if (hero.currentWeapon instanceof OverHeatWeapon) {
            ((OverHeatWeapon) hero.currentWeapon).currentHeatLevel = 0f;
            ((OverHeatWeapon) hero.currentWeapon).overheated = false;
        } else if (hero.currentWeapon instanceof LimitedAmmoWeapon) {
            ((LimitedAmmoWeapon) hero.currentWeapon).reload();
        }
    }

    /* (non-Javadoc)
     * @see dyehard.Collectibles.PowerUp#unapply(dyehard.Player.Hero)
     */
    @Override
    public void unapply(Hero hero) {
        hero.curPowerUp = CurPowerUp.NONE;
    }

    /* (non-Javadoc)
     * @see dyehard.Collectibles.PowerUp#clone()
     */
    @Override
    public PowerUp clone() {
        return new Overload();
    }

    /* (non-Javadoc)
     * @see dyehard.Collectibles.PowerUp#toString()
     */
    @Override
    public String toString() {
        return "Overload: " + super.toString();
    }
}
