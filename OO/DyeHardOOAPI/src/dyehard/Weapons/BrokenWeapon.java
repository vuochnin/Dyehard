/*
 * 
 */
package dyehard.Weapons;

import dyehard.Player.Hero;

// TODO: Auto-generated Javadoc
/**
 * The Class BrokenWeapon.
 */
public class BrokenWeapon extends Weapon {

    /**
     * Instantiates a new broken weapon.
     *
     * @param hero the hero
     */
    public BrokenWeapon(Hero hero) {
        super(hero);
    }

    /* (non-Javadoc)
     * @see dyehard.Weapons.Weapon#currentValue()
     */
    @Override
    public int currentValue() {
        return 0;
    }

    /* (non-Javadoc)
     * @see dyehard.Weapons.Weapon#totalValue()
     */
    @Override
    public int totalValue() {
        return 1;
    }

    /* (non-Javadoc)
     * @see dyehard.Weapons.Weapon#fire()
     */
    @Override
    public void fire() {
        // Play an audio file to indicate broken weapon
        // The broken weapon does not fire
        return;
    }
}
