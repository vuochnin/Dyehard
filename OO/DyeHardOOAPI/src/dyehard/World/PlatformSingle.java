/*
 * 
 */
package dyehard.World;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.DyeHardGameObject;
import dyehard.Collision.CollidableGameObject;
import dyehard.Resources.ConfigurationFileParser;

// TODO: Auto-generated Javadoc
/**
 * The Class PlatformSingle.
 */
public class PlatformSingle extends CollidableGameObject {
    
    /** The arc. */
    private DyeHardGameObject arc;

    /**
     * Instantiates a new platform single.
     */
    public PlatformSingle() {
    }

    /**
     * Instantiates a new single platform.
     *
     * @param center the center of the platform
     */
    public PlatformSingle(Vector2 center) {
        this.center = center;
        size = new Vector2(6.5f, 1.25f);
        velocity = new Vector2(-ConfigurationFileParser.getInstance().getWorldData().getWorldGameSpeed(), 0f);
        shouldTravel = true;
        texture = BaseCode.resources
                .loadImage("Textures/Background/Warp_Divider.png");

        arc = new DyeHardGameObject();
        arc.center = center.clone();
        arc.velocity = velocity;
        arc.texture = BaseCode.resources
                .loadImage("Textures/Background/Warp_Divider_Arc.png");
        arc.size = size.clone();
        arc.setPanning(true);
        arc.setPanningSheet(arc.texture, 8, 2, false);
        // so the platform is on top of the arc
        removeFromAutoDrawSet();
        addToAutoDrawSet();
    }

    /* (non-Javadoc)
     * @see dyehard.Collision.CollidableGameObject#update()
     */
    @Override
    public void update() {
        super.update();
        if (center.getX() < (BaseCode.world.getPositionX() - 5f)) {
            arc.destroy();
        }
    }

    /* (non-Javadoc)
     * @see dyehard.Collision.CollidableGameObject#destroy()
     */
    @Override
    public void destroy() {
        arc.destroy();
        super.destroy();
    }

	/* (non-Javadoc)
	 * @see dyehard.Collision.CollidableGameObject#handleCollision(dyehard.Collision.CollidableGameObject)
	 */
	@Override
	public void handleCollision(CollidableGameObject other) {
		// TODO Auto-generated method stub
		
	}

}
