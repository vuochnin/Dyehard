/*
 * 
 */
package dyehard.Collectibles;

import java.util.ArrayList;
import java.util.List;

import Engine.BaseCode;
import dyehard.Enemies.Enemy;
import dyehard.Enemies.EnemyManager;
import dyehard.Enums.PowerUpType;
import dyehard.Player.Hero;
import dyehard.Player.Hero.CurPowerUp;
import dyehard.Resources.ConfigurationFileParser;

// TODO: Auto-generated Javadoc
/**
 * The Class SpeedUp.
 */
public class SpeedUp extends PowerUp {
    
    // public static PowerUpMeter meter = new PowerUpMeter(0, DyeHard.Green);
    
    /** The magnitude. */
    protected float magnitude = ConfigurationFileParser.getInstance().getPowerupType(PowerUpType.SPEEDUP).getMagnitude();

    /**
     * Instantiates a new speed up.
     */
    public SpeedUp() {
        super();
        duration = ConfigurationFileParser.getInstance().getPowerupType(PowerUpType.SPEEDUP).getDuration() * 1000;
        texture = BaseCode.resources.loadImage("Textures/PowerUp_SpeedUp.png");
        enemySpeedModifier = magnitude;
        isApplied = false;
        applicationOrder = 20;
        // label.setText("Speed");
    }

    /** The is applied. */
    protected boolean isApplied;
    
    /** The enemy speed modifier. */
    protected final float enemySpeedModifier;
    
    /** The affected enemies. */
    protected List<Enemy> affectedEnemies;

    /* (non-Javadoc)
     * @see dyehard.Collectibles.PowerUp#apply(dyehard.Player.Hero)
     */
    @Override
    public void apply(Hero hero) {
        if (isApplied) { // only applies once
            return;
        }

        affectedEnemies = new ArrayList<Enemy>(EnemyManager.getInstance().getEnemies());

        for (Enemy e : affectedEnemies) {
            e.speed *= enemySpeedModifier;
        }

        isApplied = true;
        hero.curPowerUp = CurPowerUp.SPEED;
    }

    /* (non-Javadoc)
     * @see dyehard.Collectibles.PowerUp#unapply(dyehard.Player.Hero)
     */
    @Override
    public void unapply(Hero hero) {
        if (affectedEnemies != null) {
            for (Enemy e : affectedEnemies) {
                e.speed /= enemySpeedModifier;
            }
        }
        hero.curPowerUp = CurPowerUp.NONE;
    }

    /* (non-Javadoc)
     * @see dyehard.Collectibles.PowerUp#clone()
     */
    @Override
    public PowerUp clone() {
        return new SpeedUp();
    }

    /* (non-Javadoc)
     * @see dyehard.Collectibles.PowerUp#toString()
     */
    @Override
    public String toString() {
        return "Speed Up: " + super.toString();
    }
}
