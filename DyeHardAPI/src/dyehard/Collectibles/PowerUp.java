/*
 * 
 */
package dyehard.Collectibles;

import java.awt.Color;

import Engine.Text;
import Engine.Vector2;
import dyehard.Collision.CollidableGameObject;
import dyehard.Player.Hero;
import dyehard.Resources.ConfigurationFileParser;
import dyehard.Util.DyeHardSound;
import dyehard.Util.Timer;

// TODO: Auto-generated Javadoc
/**
 * The Class PowerUp.
 */
public abstract class PowerUp extends CollidableGameObject implements Cloneable,
        Comparable<PowerUp> {
	    
    /** The Constant width. */
    public static final float width = ConfigurationFileParser.getInstance().getPowerupData().getPowerUpWidth();    
    
    /** The Constant height. */
    public static final float height = ConfigurationFileParser.getInstance().getPowerupData().getPowerUpHeight();
    
    /** The application order. */
    protected int applicationOrder;
    
    /** The duration. */
    protected float duration = 5000f;    
    
    /** The timer. */
    protected Timer timer;
    
    /** The label. */
    protected Text label;

    /**
     * Instantiates a new power up.
     */
    public PowerUp() {
        shouldTravel = false;
        visible = false;
        applicationOrder = 0;
        timer = new Timer(duration);

        label = new Text("", center.getX(), center.getY());
        label.setFrontColor(Color.WHITE);
        label.setBackColor(Color.BLACK);
        label.setFontSize(20);
        label.setFontName("Arial");
    }

    /**
     * Instantiates a new power up.
     *
     * @param other set the texture of this.texture
     */
    public PowerUp(PowerUp other) {
        texture = other.texture;
    }

    /**
     * Initialize.
     *
     * @param center the new center
     */
    public void initialize(Vector2 center) {
        timer.setInterval(duration);

        this.center = center;
        velocity = new Vector2(-ConfigurationFileParser.getInstance().getPowerupData().getPowerUpSpeed(), 0f);

        size.set(width, height);
        shouldTravel = true;
        visible = true;
    }

    /* (non-Javadoc)
     * @see dyehard.Collision.CollidableGameObject#update()
     */
    @Override
    public void update() {
        super.update();
        // label.center.set(center.getX() - 2.5f, center.getY() - 0.5f);
    }

    /* (non-Javadoc)
     * @see dyehard.Collision.CollidableGameObject#destroy()
     */
    @Override
    public void destroy() {
        label.destroy();
        super.destroy();
    }

    /**
     * Gets the duration.
     *
     * @return the duration
     */
    public float getDuration() {
        return duration;
    }

    /**
     * Gets the remaining time.
     *
     * @return the remaining time
     */
    public float getRemainingTime() {
        return timer.timeRemaining();
    }

    /**
     * Checks if is done.
     *
     * @return true, if is done
     */
    public boolean isDone() {
        return timer.isDone();
    }

    /**
     * Apply.
     *
     * @param hero to apply
     */
    public abstract void apply(Hero hero);

    /**
     * Unapply.
     *
     * @param hero to unapply
     */
    public abstract void unapply(Hero hero);

    /**
     * Activate.
     *
     * @param hero to activate
     */
    public void activate(Hero hero) {
        timer.reset();
        System.out.println("Activating " + toString());
        destroy();
    }

    /* (non-Javadoc)
     * @see dyehard.Collision.CollidableGameObject#handleCollision(dyehard.Collision.CollidableGameObject)
     */
    @Override
    public void handleCollision(CollidableGameObject other) {
        if (other instanceof Hero) {
            Hero hero = (Hero) other;
            hero.collect(this);
            DyeHardSound.play(DyeHardSound.powerUpSound);
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    public abstract PowerUp clone();

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(PowerUp other) {
        return applicationOrder - other.applicationOrder;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("%.0f", timer.timeRemaining() / 1000);
    }
}
