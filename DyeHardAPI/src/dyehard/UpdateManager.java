package dyehard;

import java.util.HashSet;
import java.util.Set;

import dyehard.Enums.ManagerStateEnum;

// TODO: Auto-generated Javadoc
/**
 * The Class UpdateManager.
 */
public class UpdateManager {
    
	/** The instance. */
	private static UpdateManager instance;
	
	static {
		instance = new UpdateManager();
	}
    
    /** The speed up. */
    private boolean speedUp = false;

    /** The game objects. */
    private Set<Updateable> gameObjects;
    
    /** The newly registered objects. */
    private Set<Updateable> newlyRegisteredObjects;
    
    /**
     * Gets the single instance of UpdateManager.
     *
     * @return single instance of UpdateManager
     */
    public static UpdateManager getInstance() {
    	return instance;
    }
    
    /**
     * Sets the speed up.
     *
     * @param isSpeedUp the new speed up
     */
    public void setSpeedUp(boolean isSpeedUp) {
    	speedUp = isSpeedUp;
    }
    
    /**
     * Gets the speed up.
     *
     * @return the speed up
     */
    public boolean getSpeedUp() {
    	return speedUp;
    }
    
    /**
     * Instantiates a new update manager.
     */
    private UpdateManager() {
    	gameObjects = new HashSet<Updateable>();
    	newlyRegisteredObjects = new HashSet<Updateable>();
    }

    /**
     * Update.
     */
    public void update() {
        for (Updateable o : gameObjects) {
            if (o != null && o.updateState() == ManagerStateEnum.ACTIVE) {
                o.update();
                if (speedUp) {
                    o.setSpeed(10f);
                } else if (!speedUp) {
                    o.setSpeed(0.1f);
                }
            }
        }

        gameObjects.addAll(newlyRegisteredObjects);
        newlyRegisteredObjects.clear();

        Set<Updateable> destroyed = new HashSet<Updateable>();
        for (Updateable o : gameObjects) {
            if (o == null || o.updateState() == ManagerStateEnum.DESTROYED) {
                destroyed.add(o);
            }
        }

        gameObjects.removeAll(destroyed);
    }

    /**
     * Register.
     *
     * @param o is the object to be registered
     */
    public void register(Updateable o) {
        if (o != null) {
            newlyRegisteredObjects.add(o);
        }
    }
}
