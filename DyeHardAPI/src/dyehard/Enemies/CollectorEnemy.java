package dyehard.Enemies;

import Engine.Vector2;
import dyehard.Enums.EnemyType;
import dyehard.Player.Hero;
import dyehard.Resources.ConfigurationFileParser;

// TODO: Auto-generated Javadoc
/**
 * The Class CollectorEnemy.
 */
public class CollectorEnemy extends Enemy {

    /**
     * Instantiates a new collector enemy.
     *
     * @param center the spawn center
     * @param currentHero the current hero
     */
    public CollectorEnemy(Vector2 center, Hero currentHero) {
        super(center, 0, 0, currentHero,
                "Textures/Enemies/minion_collector.png");

        width = ConfigurationFileParser.getInstance().getEnemyData(EnemyType.COLLECTOR_ENEMY).getWidth();
        height = ConfigurationFileParser.getInstance().getEnemyData(EnemyType.COLLECTOR_ENEMY).getHeight();
        sleepTimer = ConfigurationFileParser.getInstance().getEnemyData(EnemyType.COLLECTOR_ENEMY).getSleepTimer() * 1000f;
        speed = ConfigurationFileParser.getInstance().getEnemyData(EnemyType.COLLECTOR_ENEMY).getSpeed();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Collector";
    }
}