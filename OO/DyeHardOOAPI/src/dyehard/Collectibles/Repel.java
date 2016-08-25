/*
 * 
 */
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

// TODO: Auto-generated Javadoc
/**
 * The Class Repel.
 */
public class Repel extends PowerUp {

    /** The magnitude. */
    protected float magnitude = ConfigurationFileParser.getInstance().getPowerupType(PowerUpType.REPEL).getMagnitude();    
    
    /** The attraction distance. */
    protected final float attractionDistance = magnitude;

    /**
     * Instantiates a new repel.
     */
    public Repel() {
        super();
        duration = ConfigurationFileParser.getInstance().getPowerupType(PowerUpType.REPEL).getDuration() * 1000;

        texture = BaseCode.resources.loadImage("Textures/PowerUp_Repel.png");
        applicationOrder = 40;
    }

    /* (non-Javadoc)
     * @see dyehard.Collectibles.PowerUp#apply(dyehard.Player.Hero)
     */
    @Override
    public void apply(Hero hero) {
        Set<CollidableGameObject> collidables = CollisionManager.getInstance().getCollidables();
        hero.curPowerUp = CurPowerUp.REPEL;
        hero.isRepel = true;

        for (CollidableGameObject c : collidables) {
            if (c instanceof DyePack || c instanceof PowerUp) {
                // Finds the distance between the Hero and the dye/powerup
                Vector2 toHero = new Vector2(hero.center).sub(c.center);
                float distanceSqrd = toHero.lengthSQRD();

                if (distanceSqrd <= attractionDistance * attractionDistance) {
                    toHero.normalize();
                    c.velocity = toHero.mult(-0.85f);
                }
            }
        }
    }

    /* (non-Javadoc)
     * @see dyehard.Collectibles.PowerUp#unapply(dyehard.Player.Hero)
     */
    @Override
    public void unapply(Hero hero) {
        hero.isRepel = false;
        hero.curPowerUp = CurPowerUp.NONE;
        return;
    }

    /* (non-Javadoc)
     * @see dyehard.Collectibles.PowerUp#clone()
     */
    @Override
    public PowerUp clone() {
        return new Repel();
    }

    /* (non-Javadoc)
     * @see dyehard.Collectibles.PowerUp#toString()
     */
    @Override
    public String toString() {
        return "Repel: " + super.toString();
    }

}
