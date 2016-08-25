package dyehard.Enemies;

import Engine.Vector2;
import dyehard.Enums.EnemyType;
import dyehard.Player.Hero;
import dyehard.Resources.ConfigurationFileParser;

// TODO: Auto-generated Javadoc
/**
 * The Class ShootingEnemy.
 */
public class ShootingEnemy extends Enemy {
	
    /**
     * Instantiates a new shooting enemy.
     *
     * @param center the center
     * @param currentHero the current hero
     */
    public ShootingEnemy(Vector2 center, Hero currentHero) {
        super(center, 0, 0, currentHero, "Textures/Enemies/minion_shooter.png");

        width = ConfigurationFileParser.getInstance().getEnemyData(EnemyType.SHOOTING_ENEMY).getWidth();
        height = ConfigurationFileParser.getInstance().getEnemyData(EnemyType.SHOOTING_ENEMY).getHeight();
        sleepTimer = ConfigurationFileParser.getInstance().getEnemyData(EnemyType.SHOOTING_ENEMY).getSleepTimer() * 1000f;
        speed = ConfigurationFileParser.getInstance().getEnemyData(EnemyType.SHOOTING_ENEMY).getSpeed();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Shooting";
    }
}