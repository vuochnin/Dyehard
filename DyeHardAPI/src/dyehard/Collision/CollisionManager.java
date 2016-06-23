package dyehard.Collision;

import java.util.HashSet;
import java.util.Set;

import dyehard.Enums.ManagerStateEnum;

// TODO: Auto-generated Javadoc
/**
 * Collision Manager provides a single class to handle collisions between
 * objects.
 * 
 * Registering a Collidable with CollidableManager indicates that it is ready
 * to start colliding with registered Actors and vice versa.
 * 
 * Calling CollidableManager.update() will call update on the registered
 * Collidables and then perform the collision checks handling the necessary
 * interactions between actors and Collidables.
 * 
 * @author Rodelle Ladia Jr.
 * 
 */
public class CollisionManager {
	
	/** The instance. */
	private static CollisionManager instance;
	
	static {
		instance = new CollisionManager();
	}
    
    /** The collidables. */
    private Set<CollidableGameObject> collidables;
    
    /** The new collidables. */
    private Set<CollidableGameObject> newCollidables;
    
    /**
     * Gets the single instance of CollisionManager.
     *
     * @return single instance of CollisionManager
     */
    public static CollisionManager getInstance() {
    	return instance;
    }
    
    /**
     * Instantiates a new collision manager.
     */
    private CollisionManager() {
    	collidables = new HashSet<CollidableGameObject>();
    	newCollidables = new HashSet<CollidableGameObject>();
    }

    /**
     * Indicates that Actors are ready to start colliding with other registered
     * actors and collidables.
     *
     * @param c is the Actor to add to collidables
     */
    public void registerActor(CollidableGameObject c) {
        if (c != null) {
            newCollidables.add(c);
        }
    }

    /**
     * Registers an object that can collide with registered actors. Registered
     * collidables do not collide with other registered collidables.
     *
     * @param c is the collidable to add to collidables
     */
    public void registerCollidable(CollidableGameObject c) {
        if (c != null) {
            newCollidables.add(c);
        }
    }

    /**
     * Update.
     */
    public void update() {

        for (CollidableGameObject c1 : collidables) {
            if (c1.collideState() != ManagerStateEnum.ACTIVE) {
                continue;
            } else {
                for (CollidableGameObject c2 : collidables) {
                    if (c2.collideState() != ManagerStateEnum.ACTIVE) {
                        continue;
                    }

                    else if (c1 != c2 && c1.collided(c2)) {
                        c1.handleCollision(c2);
                        c2.handleCollision(c1);
                    }
                }
            }
        }

        collidables.addAll(newCollidables);
        newCollidables.clear();
        removeInactiveObjects(collidables);
    }

    /**
     * Removes the inactive objects.
     *
     * @param set is the set of objects that will be iterated over
     */
    private void removeInactiveObjects(Set<CollidableGameObject> set) {
        Set<CollidableGameObject> destroyed = new HashSet<CollidableGameObject>();
        for (CollidableGameObject o : set) {
            if (o == null || o.collideState() == ManagerStateEnum.DESTROYED) {
                destroyed.add(o);
            }
        }

        set.removeAll(destroyed);
    }

    /**
     * Gets the collidables.
     *
     * @return the collidables set
     */
    public final Set<CollidableGameObject> getCollidables() {
        return collidables;
    }
}
