package dyehard.Enemies;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class EnemyManager.
 */
public class EnemyManager {
	
	/** The instance. */
	private static EnemyManager instance;
    
    /** The enemies. */
    private List<Enemy> enemies;
	
	static {
		instance = new EnemyManager();
	}
	
	/**
	 * Gets the single instance of EnemyManager.
	 *
	 * @return single instance of EnemyManager
	 */
	public static EnemyManager getInstance() {
		return instance;
	}

    /**
     * Instantiates a new enemy manager.
     */
    private EnemyManager() {
    	enemies = new ArrayList<Enemy>();
    }

    /**
     * Register enemy.
     *
     * @param e the e
     */
    public void registerEnemy(Enemy e) {
        e.initialize();
        enemies.add(e);
    }

    /**
     * Clear every enemy.
     */
    public void clear() {
        for (Enemy e : enemies) {
            e.destroy();
        }
        enemies.clear();
    }

    /**
     * Gets the enemies.
     *
     * @return the enemies
     */
    public List<Enemy> getEnemies() {
        return enemies;
    }
}
