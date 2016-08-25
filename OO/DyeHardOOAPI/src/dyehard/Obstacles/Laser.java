package dyehard.Obstacles;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.DyeHardGameObject;
import dyehard.DyehardRectangle;
import dyehard.Player.Hero;

// TODO: Auto-generated Javadoc
/**
 * The Class Laser.
 */
public class Laser extends DyeHardGameObject {
    
    /** The hero. */
    private final Hero hero;
    
    /** The laser reverse. */
    private final DyehardRectangle laserReverse;

    /**
     * Instantiates a new laser.
     *
     * @param hero is the hero
     * TODO: Find reasoning behind magic numbers
     */
    public Laser(Hero hero) {
        this.hero = hero;
        float height = BaseCode.world.getHeight()
                - BaseCode.world.getWorldPositionY();
        float width = height * 220 / 1024;
        center = new Vector2(width / 2 - 2, height / 2);
        size.set(width, height);
        texture = BaseCode.resources
                .loadImage("Textures/Background/DeathEdge.png");
        setPanning(true);
        setPanningSheet(texture, 16, 3, true);

        laserReverse = new DyehardRectangle();
        laserReverse.size.set(width / 2f, height);
        laserReverse.center.set(width / 2 - 3, height / 2);
        laserReverse.setPanning(true);
        laserReverse.reverse = true;
        laserReverse.setPanningSheet(texture, 32, 3, true);
        laserReverse.alwaysOnTop = true;
        alwaysOnTop = true;
    }

    /* (non-Javadoc)
     * @see Engine.Primitive#update()
     */
    // TODO should we put this collision into the user code?
    @Override
    public void update() {
        if (hero.center.getX() - (hero.size.getX() / 2) < center.getX()) {
            hero.kill(this);
        }
    }

    /* (non-Javadoc)
     * @see dyehard.DyeHardGameObject#destroy()
     */
    @Override
    public void destroy() {
        laserReverse.destroy();
        super.destroy();
    }

    /**
     * Draw front.
     */
    public void drawFront() {
        BaseCode.resources.moveToFrontOfDrawSet(this);
        BaseCode.resources.moveToFrontOfDrawSet(laserReverse);
    }
}