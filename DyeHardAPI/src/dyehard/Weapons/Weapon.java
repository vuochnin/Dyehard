/*
 * 
 */
package dyehard.Weapons;

import java.util.ArrayList;

import dyehard.UpdateManager;
import dyehard.Updateable;
import dyehard.Enemies.Enemy;
import dyehard.Enums.ManagerStateEnum;
import dyehard.Player.DyeMeter.Progressable;
import dyehard.Resources.ConfigurationFileParser;
import dyehard.Player.Hero;
import dyehard.Util.DyeHardSound;
import dyehard.Util.Timer;

// TODO: Auto-generated Javadoc
/**
 * The Class Weapon.
 */
public class Weapon implements Updateable, Progressable {
    
    /** The bullet speed. */
    protected static float bulletSpeed = 1f;
    
    /** The bullet size. */
    protected static float bulletSize = 1.5f;
    
    /** The hero. */
    protected Hero hero;
    
    /** The enemies. */
    protected ArrayList<Enemy> enemies;
    
    /** The fire rate. */
    protected float fireRate = ConfigurationFileParser.getInstance().getWeaponOverheatData().getOverheatFiringRate();
    
    /** The timer. */
    protected Timer timer;

    /**
     * Instantiates a new weapon.
     *
     * @param hero to get a reference to
     */
    public Weapon(Hero hero) {
        this.hero = hero;
        timer = new Timer(fireRate);
        UpdateManager.getInstance().register(this);
    }

    /**
     * Fire.
     */
    // Fire the weapon
    public void fire() {
        if (timer.isDone()) {
           // DyeHardSound.playMulti(DyeHardSound.paintSpraySound);
            DyeHardSound.playMulti(DyeHardSound.shotgunSound);
            //BaseCode.resources.playSoundLooping("Audio/shotgun.mp3");
            new Bullet(hero);
            timer.reset();
        }
    }

    /**
     * Reset timer.
     */
    public void resetTimer() {
        timer.reset();
    }

    /**
     * Sets the enemies.
     *
     * @param enemies the new enemies
     */
    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    /* (non-Javadoc)
     * @see dyehard.Updateable#update()
     */
    @Override
    public void update() {
        return;
    }

    /* (non-Javadoc)
     * @see dyehard.Updateable#updateState()
     */
    @Override
    public ManagerStateEnum updateState() {
        return hero.updateState();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Default";
    }

    /* (non-Javadoc)
     * @see dyehard.Player.DyeMeter.Progressable#currentValue()
     */
    @Override
    public int currentValue() {
        return 1;
    }

    /* (non-Javadoc)
     * @see dyehard.Player.DyeMeter.Progressable#totalValue()
     */
    @Override
    public int totalValue() {
        return 1;
    }

    /* (non-Javadoc)
     * @see dyehard.Updateable#setSpeed(float)
     */
    @Override
    public void setSpeed(float factor) {
        // TODO Auto-generated method stub

    }
}
