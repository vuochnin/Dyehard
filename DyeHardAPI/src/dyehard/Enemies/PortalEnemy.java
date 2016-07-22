package dyehard.Enemies;

import java.awt.Color;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.Enums.EnemyType;
import dyehard.Player.Hero;
import dyehard.Resources.ConfigurationFileParser;
import dyehard.Util.Timer;

// TODO: Auto-generated Javadoc
/**
 * The Class PortalEnemy.
 */
public class PortalEnemy extends Enemy {
    
    /** The timer. */
    protected Timer timer;    
    
    /** The portal spawn interval. */
    protected float portalSpawnInterval = 0f;

    /**
     * Instantiates a new portal enemy.
     *
     * @param center the center
     * @param currentHero the current hero
     */
    public PortalEnemy(Vector2 center, Hero currentHero) {
        super(center, 0, 0, currentHero,
                "Textures/Enemies/PortalMinion_AnimSheet_Left.png");
        setUsingSpriteSheet(true);
        setSpriteSheet(texture, 140, 140, 12, 2);

        //investigate
        try
        {
        	portalSpawnInterval = Float.parseFloat(parseNodeList(
                EnemyType.PORTAL_ENEMY, "portalSpawnInterval")) * 1000;
        }
        catch(NumberFormatException nfe)
        {
        	portalSpawnInterval = 2000f;
        }
        catch(NullPointerException npe)
        {
        	portalSpawnInterval = 2000f;
        }
        
        timer = new Timer(portalSpawnInterval);

        width = ConfigurationFileParser.getInstance().getEnemyData(EnemyType.PORTAL_ENEMY).getWidth();
        height = ConfigurationFileParser.getInstance().getEnemyData(EnemyType.PORTAL_ENEMY).getHeight();
        sleepTimer = ConfigurationFileParser.getInstance().getEnemyData(EnemyType.PORTAL_ENEMY).getSleepTimer() * 1000f;
        speed = ConfigurationFileParser.getInstance().getEnemyData(EnemyType.PORTAL_ENEMY).getSpeed();
    }

    /* (non-Javadoc)
     * @see dyehard.Enemies.Enemy#update()
     */
    @Override
    public void update() {
        if (harmlessTimer.isDone()) {
            harmlessTimer.setActive(false);
            color = null;
            setSpriteSheet(Hero.portalEnemyTextures.get(Color.gray), 140, 140,
                    12, 2);
        }
        if (timer.isDone()) {
            new Portal(center.clone(), hero);
            timer.reset();
        }
        if (center.getX() < BaseCode.world.getPositionX() - 5f) {
            destroy();
        }
    }

    /* (non-Javadoc)
     * @see dyehard.Enemies.Enemy#setColor(java.awt.Color)
     */
    @Override
    public void setColor(Color color) {
        if (this.color != color) {
            this.color = color;
            int temp = getCurFrame();
            setSpriteSheet(Hero.portalEnemyTextures.get(color), 140, 140, 12, 2);
            setCurFrame(temp);
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Portal";
    }
}