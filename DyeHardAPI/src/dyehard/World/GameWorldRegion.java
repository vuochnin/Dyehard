/*
 * 
 */
package dyehard.World;

// TODO: Auto-generated Javadoc
/**
 * The Class GameWorldRegion.
 */
public abstract class GameWorldRegion {
    
    /** The width. */
    protected float width;
    
    /** The position. */
    protected float position;
    
    /** The speed. */
    protected float speed;

    /**
     * Left edge.
     *
     * @return the the pos of the left edge
     */
    public float leftEdge() {
        return position - width / 2;
    }

    /**
     * Right edge.
     *
     * @return the pos of the right edge
     */
    public float rightEdge() {
        return position + width / 2;
    }

    /**
     * Gets the width.
     *
     * @return the width
     */
    public float getWidth() {
        return width;
    }

    /**
     * Move left.
     */
    public void moveLeft() {
        position += speed;
    }

    /**
     * Move left.
     *
     * @param factor to move left
     */
    // for debugging speedup purpose
    public void moveLeft(float factor) {
        // #TODO maybe take out for final version
        position += speed * factor;
    }

    // Instructs the region to construct its components using the leftEdge as
    /**
     * Initialize.
     *
     * @param leftEdge the left edge
     */
    // its starting location
    public abstract void initialize(float leftEdge);

    /**
     * Destroy.
     */
    public abstract void destroy();
}
