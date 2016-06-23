package dyehard.Enemies;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.DyeHardGameObject;
import dyehard.Enums.EnemyType;
import dyehard.Player.Hero;
import dyehard.Resources.ConfigurationFileParser;
import dyehard.Util.ImageTint;
import dyehard.Util.Timer;

// TODO: Auto-generated Javadoc
/**
 * The Class Portal.
 */
public class Portal extends DyeHardGameObject {
    
    /** The hero. */
    protected Hero hero;    
    
    /** The timer. */
    protected Timer timer;    
    
    /** The width. */
    protected float width;    
    
    /** The height. */
    protected float height;    
    
    /** The duration. */
    protected float duration = 4000f;
    
    /** The ported. */
    private boolean ported = false;    
    
    /** The collide. */
    private final boolean collide;
    
    /** The exit texture. */
    private static BufferedImage exitTexture;
    
    static {
        exitTexture = ImageTint.tintedImage(BaseCode.resources
                .loadImage("Textures/Enemies/Minion_Portal_AnimSheet.png"),
                Color.blue, 0.5f);
    }

    /**
     * Instantiates a new portal.
     *
     * @param center the center
     * @param hero the hero
     */
    public Portal(Vector2 center, Hero hero) {
        this.center = center.clone();

        width = ConfigurationFileParser.getInstance().getEnemyData(EnemyType.PORTAL_SPAWN).getWidth();
        height = ConfigurationFileParser.getInstance().getEnemyData(EnemyType.PORTAL_SPAWN).getHeight();
        parseNodeList();

        size.set(width, height);
        this.hero = hero;
        texture = BaseCode.resources
                .loadImage("Textures/Enemies/Minion_Portal_AnimSheet.png");
        setUsingSpriteSheet(true);
        setSpriteSheet(texture, 160, 160, 20, 2);
        timer = new Timer(duration);
        collide = true;
    }

    /**
     * Instantiates a new portal.
     *
     * @param hero the hero
     * @param center the center
     */
    public Portal(Hero hero, Vector2 center) {
        collide = false;
        this.center = center.clone();

        this.hero = hero;

        width = ConfigurationFileParser.getInstance().getEnemyData(EnemyType.PORTAL_SPAWN).getWidth();
        height = ConfigurationFileParser.getInstance().getEnemyData(EnemyType.PORTAL_SPAWN).getHeight();
        parseNodeList();

        size.set(width, height);
        texture = exitTexture;
        setUsingSpriteSheet(true);
        setSpriteSheet(texture, 160, 160, 20, 2);
        timer = new Timer(1500);
    }

    /* (non-Javadoc)
     * @see Engine.Primitive#update()
     */
    @Override
    public void update() {
        if (collide) {
            if (heroInside()) {
                if (!hero.noMoreTeleport) {
                    Random rand = new Random();
                    new Portal(hero, new Vector2(rand.nextInt(90) + 5,
                            rand.nextInt(50) + 5));
                    hero.startFlashing();
                    timer = new Timer(1500);
                }
            }
        }
        if (timer.isDone()) {
            if (!collide) {
                hero.center.set(center.clone());
                hero.velocity = new Vector2(0f, 0f);
                if (!ported) {
                    ported = true;
                    timer.reset();
                } else {
                    hero.stopFlashing();
                    destroy();
                }
            } else {
                destroy();
            }
        }
    }

    /**
     * Hero inside.
     *
     * @return true, if successful
     */
    public boolean heroInside() {
        float xDif = Math.abs(center.getX() - hero.center.getX());
        float yDif = Math.abs(center.getY() - hero.center.getY());
        if (xDif + yDif < 5f) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Parses the node list.
     */
    public void parseNodeList() {
        NodeList nodeList = ConfigurationFileParser.getInstance().getEnemyData(EnemyType.PORTAL_SPAWN).getUniqueAttributes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;

                duration = Float.parseFloat(elem
                        .getElementsByTagName("duration").item(0)
                        .getChildNodes().item(0).getNodeValue()) * 1000;
            }
        }
    }
}