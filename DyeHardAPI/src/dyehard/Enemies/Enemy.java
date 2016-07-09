package dyehard.Enemies;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.Actor;
import dyehard.Collision.CollidableGameObject;
import dyehard.Enums.EnemyType;
import dyehard.Player.Hero;
import dyehard.Resources.ConfigurationFileParser;
import dyehard.Util.ImageTint;
import dyehard.Util.Timer;

// TODO: Auto-generated Javadoc
/**
 * The Class Enemy.
 */
public class Enemy extends Actor {
    
    /** The speed. */
    public float speed;
    
    /** The been hit. */
    public boolean beenHit = false;
    
    /** The hero. */
    protected Hero hero;   
    
    /** The enemy state. */
    protected EnemyState enemyState;  
    
    /** The base texture. */
    protected BufferedImage baseTexture;   
    
    /** The width. */
    protected float width;   
    
    /** The height. */
    protected float height;   
    
    /** The sleep timer. */
    protected float sleepTimer;
    
    /** The harmless. */
    protected boolean harmless = false;    
    
    /** The sound on. */
    protected boolean soundOn = false;
    
    /** The timer. */
    // This time is in milliseconds
    private Timer timer;    
    
    /** The harmless timer. */
    protected Timer harmlessTimer;

    /**
     * The Enum EnemyState.
     */
    protected enum EnemyState {
        
        /** The begin state. */
        BEGIN, 
        
        /** The chasehero state. */
        CHASEHERO, 
 		
		 /** The playing state... Never called? */
		 PLAYING, 
 		
		 /** The dead state... Never called? */
		 DEAD
    };

    /**
     * Instantiates a new enemy.
     *
     * @param center the center
     * @param width the width
     * @param height the height
     * @param hero the hero
     * @param texturePath the texture path
     */
    public Enemy(Vector2 center, float width, float height, Hero hero,
            String texturePath) {
        super(center, width, height);
        this.hero = hero;
        baseTexture = BaseCode.resources.loadImage(texturePath);
        texture = baseTexture;
        harmlessTimer = new Timer(4000);
        harmlessTimer.setActive(false);
    }

    /**
     * Instantiates a new enemy.
     *
     * @param center the center
     * @param width the width
     * @param height the height
     * @param hero the hero
     * @param img the img
     */
    public Enemy(Vector2 center, float width, float height, Hero hero,
            BufferedImage img) {
        super(center, width, height);
        this.hero = hero;
        baseTexture = img;
        texture = baseTexture;
        this.width = width;
        this.height = height;
        harmlessTimer = new Timer(4000);
        harmlessTimer.setActive(false);
    }

    /**
     * Initialize.
     */
    public void initialize() {
        timer = new Timer(sleepTimer);
        size.set(width, height);
        enemyState = EnemyState.BEGIN;
    }

    /* (non-Javadoc)
     * @see dyehard.Collision.CollidableGameObject#update()
     */
    @Override
    public void update() {
        if (timer.isDone()) {
            enemyState = EnemyState.CHASEHERO;
            timer.reset();
        }
        switch (enemyState) {
        case BEGIN:
            moveLeft();
            break;
        case CHASEHERO:
            chaseHero();
            break;
        default:
            break;
        }
        harmlessReset();
        super.update();
    }

    /**
     * Sets the center.
     *
     * @param c the new center
     */
    public void setCenter(Vector2 c) {
        center.set(c.getX(), c.getY());
    }

    /**
     * Sets the size.
     *
     * @param x the x
     * @param y the y
     */
    public void setSize(float x, float y) {
        size.set(x, y);
    }

    /**
     * Chase hero.
     */
    public void chaseHero() {
        Vector2 direction = new Vector2(hero.center).sub(center);
        direction.normalize();

        if (direction.getX() > 0f) {
            // account for the relative movement of the game world
            direction.setX(direction.getX() * 2);
        }

        velocity.set(-speed, 0f);
        velocity.add(direction.mult(speed));
    }

    /**
     * Move left.
     */
    public void moveLeft() {
        velocity.setX(-speed);
    }

    /* (non-Javadoc)
     * @see dyehard.Actor#handleCollision(dyehard.Collision.CollidableGameObject)
     */
    @Override
    public void handleCollision(CollidableGameObject other) {
        super.handleCollision(other);
        if ((other instanceof Hero) && (hero.damageOn)) {
            if (pixelTouches(hero)) {
                ((Hero) other).kill(this);
            }
        }
    }

    /* (non-Javadoc)
     * @see dyehard.Actor#setColor(java.awt.Color)
     */
    @Override
    public void setColor(Color color) {
        if (this.color != color) {
            super.setColor(color);
            texture = ImageTint.tintedImage(baseTexture, color, 0.25f);
        }
    }

    /**
     * Sets the harmless.
     */
    public void setHarmless() {
        harmlessTimer.reset();
        harmlessTimer.setActive(true);
    }

    /**
     * Harmless reset.
     */
    public void harmlessReset() {
        if (harmlessTimer.isDone()) {
            color = null;
            harmlessTimer.setActive(false);
        }
    }

    /**
     * Parses the node list.
     *
     * @param type the type
     * @param tag the tag
     * @return the string
     */
    public String parseNodeList(EnemyType type, String tag) {
        NodeList nodeList = ConfigurationFileParser.getInstance().getEnemyData(type).getUniqueAttributes();
        String retVal = "";

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;

                retVal = elem.getElementsByTagName(tag).item(0).getChildNodes()
                        .item(0).getNodeValue();

                return retVal;
            }
        }

        return retVal;
    }
}