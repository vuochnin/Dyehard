package dyehard.Obstacles;

import java.util.Random;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.Collision.CollidableGameObject;
import dyehard.Resources.ConfigurationFileParser;

// TODO: Auto-generated Javadoc
/**
 * The Class Debris.
 */
public class Debris extends CollidableGameObject {
    
    /** The random generator. */
    private static Random RANDOM = new Random();    
    
    /** The width of the debris, specified by xml. */
    private final float width = ConfigurationFileParser.getInstance().getDebrisData().getDebrisWidth();    
    
    /** The height of the debris, specified by xml. */
    private final float height = ConfigurationFileParser.getInstance().getDebrisData().getDebrisHeight();

    /**
     * Instantiates a new debris.
     *
     * @param minX, the min x of where to spawn
     * @param maxX, the max x of where to spawn
     */
    public Debris(float minX, float maxX) {
        float randomX = (maxX - minX - width) * RANDOM.nextFloat() + minX
                + width / 2f;
        float randomY = (BaseCode.world.getHeight()
                - BaseCode.world.getWorldPositionY() - height)
                * RANDOM.nextFloat() + height / 2f;

        center.set(new Vector2(randomX, randomY));
        size.set(width, height);

        float speed = ConfigurationFileParser.getInstance().getDebrisData().getDebrisSpeed();
        velocity = new Vector2(-(RANDOM.nextFloat() * speed / 2 + speed), 0f);
        shouldTravel = true;

        initializeRandomTexture();
    }

    /**
     * Initialize random texture.
     */
    private void initializeRandomTexture() {
        switch (RANDOM.nextInt(3)) {
        case 0:
            texture = BaseCode.resources
                    .loadImage("Textures/Debris/debris_01.png");
            break;
        case 1:
            texture = BaseCode.resources
                    .loadImage("Textures/Debris/debris_02.png");
            break;
        case 2:
            texture = BaseCode.resources
                    .loadImage("Textures/Debris/debris_03.png");
            size.setX(size.getY() * 1.28f);
            break;
        }
    }

	/* (non-Javadoc)
	 * @see dyehard.Collision.CollidableGameObject#handleCollision(dyehard.Collision.CollidableGameObject)
	 */
	@Override
	public void handleCollision(CollidableGameObject other) {
		// TODO Auto-generated method stub
		// cinsdier throwing a runtime exception
		
	}
}
