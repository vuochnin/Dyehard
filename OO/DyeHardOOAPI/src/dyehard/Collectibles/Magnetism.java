package dyehard.Collectibles;

import java.util.Set;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.Collision.CollidableGameObject;
import dyehard.Collision.CollisionManager;
import dyehard.Enums.PowerUpType;
import dyehard.Player.Hero;
import dyehard.Player.Hero.CurPowerUp;
import dyehard.Resources.ConfigurationFileParser;

/**
 * The Class Magnetism.
 * The Magnetism Powerup attracts Dyepacks and Powerups towards Dye, as long as
 * she is within "magnitude" distance from the object. "magnitude" is specified 
 * by the xml file.
 */
public class Magnetism extends PowerUp {

    /** The magnitude. */
    protected float magnitude = ConfigurationFileParser.getInstance()
    			.getPowerupType(PowerUpType.MAGNETISM).getMagnitude();
    
    /** The attraction distance. */
    protected final float attractionDistance = magnitude;

    /**
     * Instantiates a new magnetism.
     */
    public Magnetism() {
        super();
        duration = ConfigurationFileParser.getInstance()
        		.getPowerupType(PowerUpType.MAGNETISM).getDuration() * 1000;

        texture = BaseCode.resources
                .loadImage("Textures/PowerUp_Magnetism.png");
        applicationOrder = 40;
        // label.setText("Magnet");
    }

    /* (non-Javadoc)
     * @see dyehard.Collectibles.PowerUp#apply(dyehard.Player.Hero)
     */
    @Override
    public void apply(Hero hero) {
        Set<CollidableGameObject> collidables = CollisionManager.getInstance().getCollidables();
        hero.curPowerUp = CurPowerUp.MAGNET;

        for (CollidableGameObject c : collidables) {
            if (c instanceof DyePack || c instanceof PowerUp) {
                // Finds the distance between the Hero and the dye/powerup
                Vector2 toHero = new Vector2(hero.center).sub(c.center);
                float distanceSqrd = toHero.lengthSQRD();

                if (distanceSqrd <= attractionDistance * attractionDistance) {
                    toHero.normalize();
                    c.velocity = toHero.mult(0.85f);
                }
            }
        }
    }

    /* (non-Javadoc)
     * @see dyehard.Collectibles.PowerUp#unapply(dyehard.Player.Hero)
     */
    @Override
    public void unapply(Hero hero) {
        hero.curPowerUp = CurPowerUp.NONE;
        return;
    }

    /* (non-Javadoc)
     * @see dyehard.Collectibles.PowerUp#clone()
     */
    @Override
    public PowerUp clone() {
        return new Magnetism();
    }

    /* (non-Javadoc)
     * @see dyehard.Collectibles.PowerUp#toString()
     */
    @Override
    public String toString() {
        return "Magnetism: " + super.toString();
    }

}
