package dyehard;

import java.awt.Color;

import Engine.Primitive;
import Engine.Vector2;
import dyehard.Collision.CollidableGameObject;
import dyehard.Collision.Collision;
import dyehard.Collision.CollisionManager;
import dyehard.Enemies.ChargerEnemy;
import dyehard.World.PlatformSingle;

// TODO: Auto-generated Javadoc
/**
 * The Class Actor.
 */
public class Actor extends CollidableGameObject {
	
    /** Is the actor alive? */
    protected boolean alive;
//    public boolean collideLeft = false;
//    public boolean collideRight = false;
//    public boolean collideUp = false;
//    public boolean collideDown = false;

 /**
 * Instantiates a new actor.
 *
 * @param position is the new center (Vector2)
 * @param width is the new width
 * @param height is the new height
 */
public Actor(Vector2 position, float width, float height) {
    	
        CollisionManager.getInstance().registerActor(this);
        center = position;
        size.set(width, height);
        color = null;
        // set object into motion;
        velocity = new Vector2(0, 0);
        shouldTravel = true;
        alive = true;
    }

    /**
     * Sets the color.
     *
     * @param color, the new color
     */
    public void setColor(Color color) {
        this.color = color;
        // position.TextureTintColor = color;
    }

    /**
     * Gets the color.
     *
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    // What?  We can just remove this right?
    /**
     * Kill the calling object....
     * 
     * @param who the who
     */
    // The param isnt even used...
    public void kill(Primitive who) {
        destroy();
    }

    /* (non-Javadoc)
     * @see dyehard.Collision.CollidableGameObject#handleCollision(dyehard.Collision.CollidableGameObject)
     */
    @Override
    public void handleCollision(CollidableGameObject other) {
        //if (other instanceof Obstacle) { //
            if ((this instanceof ChargerEnemy)
                    && (!(other instanceof PlatformSingle))) {
                other.destroy();
            } else {
                collideWith(this,  other);
            }
        //}
    }

    /**
     * collideWith
     * Check collisions with each character and push them out of the
     * Collidable. This causes the player and enemy units to glide along the
     * edges of the Collidable
     *
     * @param actor to check collision with obstacle 
     * @param obstacle to check collision with actor
     */
    private static void collideWith(Actor actor, CollidableGameObject obstacle) {
        Vector2 out = new Vector2(0, 0);
        
        if (Collision.isOverlap(actor, obstacle, out)) {
            // Move the character so that it's no longer overlapping the
            // debris
            actor.center.add(out);

            // Stop the character from moving if they collide with the
            // Collidable
            if (Math.abs(out.getX()) > 0f) {
                if (Math.signum(out.getX()) != Math.signum(actor.velocity
                        .getX())) {
                    actor.velocity.setX(0f);
                }
            }

            if (Math.abs(out.getY()) > 0f) {
                if (Math.signum(out.getY()) != Math.signum(actor.velocity
                        .getY())) {
                    actor.velocity.setY(0f);
                }
            }
        }
    }

    /* (non-Javadoc)
     * @see dyehard.Collision.CollidableGameObject#destroy()
     */
    @Override
    public void destroy() {
        alive = false;
        super.destroy();
    }
}
