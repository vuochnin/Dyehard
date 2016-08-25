package dyehard.Collectibles;

import java.awt.Color;
import Engine.BaseCode;
import Engine.Vector2;
import dyehard.Collision.CollidableGameObject;
import dyehard.Player.Hero;
import dyehard.Resources.ConfigurationFileParser;
import dyehard.Util.Colors;
import dyehard.Util.DyeHardSound;
import dyehard.Util.Timer;

/**
 * The Class DyePack. Dye is able to pick up Dyepacks which alter the 
 * projectile's colors which she shoots. 
 */
public class DyePack extends CollidableGameObject {
    
    /** The Constant width of the dyepack. */
    public static final float width = ConfigurationFileParser.getInstance().getDyePackData().getDyePackWidth();    
    
    /** The Constant height of the dyepack. */
    public static final float height = ConfigurationFileParser.getInstance().getDyePackData().getDyePackHeight();
    
    /** Timer which is used to rotate the Dyepack. */
    protected Timer timer;

    /**
     * Instantiates a new dye pack.
     *
     * @param color is the new DyePack color
     */
    public DyePack(Color color) {
        this.color = color;
        texture = BaseCode.resources.loadImage(getTexture(color));
        shouldTravel = false;
        visible = false;
        timer = new Timer();
    }

    /**
     * Initialize the dyepack
     *
     * @param center is the center of the DyePack in world space.
     */
    public void initialize(Vector2 center) {
        this.center = center;
        velocity = new Vector2(-ConfigurationFileParser.getInstance().getDyePackData().getDyePackSpeed(), 0f);

        size.set(width, height);
        shouldTravel = true;
        visible = true;
    }

    /**
     * Activate the dyepack.
     *
     * @param sets the hero's color and sets visibility to false
     */
    public void activate(Hero hero) {
        hero.setColor(color);
        visible = false;
    }

    /**
     * Get the color.
     *
     * @param color to return
     * @return the texture of param color
     */
    public static String getTexture(Color color) {
        if (color == Colors.Green) {
            return "Textures/Dye_Green.png";
        }
        if (color == Colors.Blue) {
            return "Textures/Dye_Blue.png";
        }
        if (color == Colors.Yellow) {
            return "Textures/Dye_Yellow.png";
        }
        if (color == Colors.Teal) {
            return "Textures/Dye_Teal.png";
        }
        if (color == Colors.Pink) {
            return "Textures/Dye_Pink.png";
        }
        if (color == Colors.Red) {
            return "Textures/Dye_Red.png";
        }
        return "";
    }

    /* (non-Javadoc)
     * @see dyehard.Collision.CollidableGameObject#update()
     */
    @Override
    public void update() {
        super.update();
        rotate += 60f * timer.deltaTime();	// rotate 60 units per second
    }

    /* (non-Javadoc)
     * @see dyehard.Collision.CollidableGameObject#handleCollision(dyehard.Collision.CollidableGameObject)
     */
    @Override
    public void handleCollision(CollidableGameObject other) {
        if (other instanceof Hero) {
            Hero hero = (Hero) other;
            hero.collect(this);
            DyeHardSound.play(DyeHardSound.pickUpSound);
        }
    }
}
