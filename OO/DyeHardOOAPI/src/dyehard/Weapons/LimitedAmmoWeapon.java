/*
 * 
 */
package dyehard.Weapons;

import dyehard.Player.Hero;
import dyehard.Resources.ConfigurationFileParser;

// TODO: Auto-generated Javadoc
/**
 * The Class LimitedAmmoWeapon.
 */
public class LimitedAmmoWeapon extends Weapon {
    
    /** The reload amount. */
    protected final int reloadAmount = ConfigurationFileParser.getInstance().getLimitedAmmoData().getLimitedReloadAmount();
    
    /** The max ammo. */
    protected final int maxAmmo = ConfigurationFileParser.getInstance().getLimitedAmmoData().getLimitedMaxAmmo();
    
    /** The current ammo. */
    protected int currentAmmo;

    /**
     * Instantiates a new limited ammo weapon.
     *
     * @param hero the hero
     */
    public LimitedAmmoWeapon(Hero hero) {
        super(hero);
        timer.setInterval(ConfigurationFileParser.getInstance().getLimitedAmmoData().getLimitedFiringRate());
        currentAmmo = maxAmmo;
    }

    /**
     * Reload.
     */
    public void reload() {
        currentAmmo += reloadAmount;

        if (currentAmmo > maxAmmo) {
            currentAmmo = maxAmmo;
        }
    }

    /* (non-Javadoc)
     * @see dyehard.Weapons.Weapon#fire()
     */
    @Override
    public void fire() {
        if (timer.isDone() && currentAmmo > 0) {
            super.fire();
            currentAmmo--;
        }
    }

    /* (non-Javadoc)
     * @see dyehard.Weapons.Weapon#currentValue()
     */
    @Override
    public int currentValue() {
        return currentAmmo;
    }

    /* (non-Javadoc)
     * @see dyehard.Weapons.Weapon#totalValue()
     */
    @Override
    public int totalValue() {
        return maxAmmo;
    }

    /* (non-Javadoc)
     * @see dyehard.Weapons.Weapon#toString()
     */
    @Override
    public String toString() {
        return "Limited Ammo " + currentAmmo + "/" + maxAmmo;
    }
}
