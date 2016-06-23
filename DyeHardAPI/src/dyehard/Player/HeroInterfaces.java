package dyehard.Player;

import Engine.Primitive;
import dyehard.Collision.CollidableGameObject;

// TODO: Auto-generated Javadoc
/**
 * The Class HeroInterfaces.
 */
public class HeroInterfaces {
    
    /**
     * The Interface HeroCollision.
     */
    public interface HeroCollision {
        
        /**
         * Collide with hero.
         *
         * @param hero to check collision with other
         * @param other to check collision with actor
         */
        public abstract void collideWithHero(Hero hero, CollidableGameObject other);
    }

    /**
     * The Interface HeroDamage.
     */
    public interface HeroDamage {
        
        /**
         * Damage hero.
         *
         * @param hero to take damage
         * @param who is how much damage the hero takes
         */
        public void damageHero(Hero hero, Primitive who);
    }
}
