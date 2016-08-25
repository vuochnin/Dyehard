package dyehard.Collision;

import Engine.BaseCode;
import dyehard.DyeHardGameObject;
import dyehard.Enums.ManagerStateEnum;

// TODO: Auto-generated Javadoc
/**
 * The Class CollidableGameObject.
 */
public abstract class CollidableGameObject extends DyeHardGameObject {
    
    /** The collidable state. */
    protected ManagerStateEnum collidableState;

//    public boolean heroUp = false;
//    public boolean heroDown = false;
//    public boolean heroLeft = false;
//    public boolean heroRight = false;

/** The Constant OFFSET. */
private static final float OFFSET = 30f;

    /**
     * Instantiates a new collidable game object.
     */
    public CollidableGameObject() {
        collidableState = ManagerStateEnum.ACTIVE;
        CollisionManager.getInstance().registerCollidable(this);
    }

    /**
     * Collide state.
     *
     * @return the manager state enum collidableState
     */
    public ManagerStateEnum collideState() {
        return collidableState;
    }

    /**
     * Handle collision.
     *
     * @param other is the object to check collision with
     */
    public abstract void handleCollision(CollidableGameObject other);

    /* (non-Javadoc)
     * @see Engine.Primitive#update()
     */
    @Override
    public void update() {
        super.update();
        if (!isInsideWorld(this)) {
            destroy();
        }
    }

    /**
     * Update gate.
     */
    public void updateGate() {
        super.update();
    }

    /**
     * Checks if object is inside world.
     *
     * @param o is the object being checked
     * @return true, if inside world
     */
    private static boolean isInsideWorld(CollidableGameObject o) {
        // The Collidable is destroyed once it's too far from the map to the
        // left, top, or bottom portion of the map. offset is 30f for now
        if ((o.center.getX() < (BaseCode.world.getPositionX() - OFFSET))
        		//|| (o.center.getX() > (BaseCode.world.getWidth() + OFFSET)) // Breaks wormholes
                || (o.center.getY() < (BaseCode.world.getWorldPositionY() - OFFSET))
                || (o.center.getY() > (BaseCode.world.getHeight() + OFFSET))) {
            return false;
        }

        return true;
    }

    // public void revertCollideStatus(Hero hero) {
    // if (heroUp) {
    // heroUp = false;
    // hero.collideDown = false;
    // System.out.println(hero.collideDown);
    // }
    // if (heroDown) {
    // heroDown = false;
    // hero.collideUp = false;
    // System.out.println(hero.collideUp);
    // }
    // if (heroLeft) {
    // heroLeft = false;
    // hero.collideRight = false;
    // System.out.println(hero.collideRight);
    // }
    // if (heroRight) {
    // heroRight = false;
    // hero.collideRight = false;
    // System.out.println(hero.collideLeft);
    // }
    // }

    /* (non-Javadoc)
     * @see dyehard.DyeHardGameObject#destroy()
     */
    @Override
    public void destroy() {
        super.destroy();
        collidableState = ManagerStateEnum.DESTROYED;
    }
}
