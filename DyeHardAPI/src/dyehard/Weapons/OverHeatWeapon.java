/*
 * 
 */
package dyehard.Weapons;

import dyehard.Player.Hero;
import dyehard.Resources.ConfigurationFileParser;

// TODO: Auto-generated Javadoc
/**
 * The Class OverHeatWeapon.
 */
public class OverHeatWeapon extends Weapon {
    
    /** The heat limit. */
    protected final float heatLimit = ConfigurationFileParser.getInstance().getWeaponOverheatData().getOverheatHeatLimit();
    
    /** The cooldown rate. */
    protected final float cooldownRate = ConfigurationFileParser.getInstance().getWeaponOverheatData().getOverheatCooldownRate();
    
    /** The current heat level. */
    public float currentHeatLevel;
    
    /** The overheated. */
    public boolean overheated;

    /**
     * Instantiates a new over heat weapon.
     *
     * @param hero to get a reference to
     */
    public OverHeatWeapon(Hero hero) {
        super(hero);
        timer.setInterval(ConfigurationFileParser.getInstance().getWeaponOverheatData().getOverheatFiringRate());
        overheated = false;
        currentHeatLevel = 0;
    }

    /* (non-Javadoc)
     * @see dyehard.Weapons.Weapon#update()
     */
    @Override
    public void update() {
        if (currentHeatLevel > heatLimit) {
            overheated = true;
        }
        if (currentHeatLevel >= 0) {
            currentHeatLevel = currentHeatLevel - cooldownRate;
        }
        if (currentHeatLevel <= 0) {
            currentHeatLevel = 0;
            overheated = false;
        }
    }

    /* (non-Javadoc)
     * @see dyehard.Weapons.Weapon#fire()
     */
    @Override
    public void fire() {
        if (timer.isDone() && !overheated) {
            super.fire();
            currentHeatLevel += 1;
        }
    }

    /* (non-Javadoc)
     * @see dyehard.Weapons.Weapon#currentValue()
     */
    @Override
    public int currentValue() {
        return (int) currentHeatLevel;
    }

    /* (non-Javadoc)
     * @see dyehard.Weapons.Weapon#totalValue()
     */
    @Override
    public int totalValue() {
        return (int) heatLimit;
    }

    /* (non-Javadoc)
     * @see dyehard.Weapons.Weapon#toString()
     */
    @Override
    public String toString() {
        return "Overheat " + String.format("%.0f", currentHeatLevel) + " / "
                + String.format("%.0f", heatLimit);
    }
}
