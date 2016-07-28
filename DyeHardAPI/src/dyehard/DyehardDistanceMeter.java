package dyehard;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import Engine.BaseCode;
import Engine.Rectangle;
import dyehard.Resources.DyeHardResources;
import dyehard.Resources.ImageDataParser.ImageID;

// TODO: Auto-generated Javadoc
/**
 * The Class DyehardDistanceMeter.
 * 
 * Modified by Nin
 */
public class DyehardDistanceMeter {
    
    /** The max value. */
    private int maxValue;    
    
    /** The current value. */
    private int currentValue;
    
    /** The progress. */
    private Rectangle progress;
    
    /** The dye marker. */
    private Rectangle dyeMarker;
    
    /** The filled marker. */
    private BufferedImage filledMarker;

    /** The markers. */
    private List<Rectangle> markers;
    


    /**
     * Instantiates a new dyehard distance meter.
     *
     * @param maxValue is the new max distance
     */
    // TODO remove magic numbers
    public DyehardDistanceMeter(int maxValue) {
        this.maxValue = maxValue;
        currentValue = 0;

        progress = DyeHardResources.getInstance().getScaledRectangle(ImageID.UI_PATH);
        progress.alwaysOnTop = true;

        progress.center.setX(BaseCode.world.getWidth() / 2f);
        progress.center.setY(fromTop(progress, 1.4f));

        Rectangle baseMarker = DyeHardResources.getInstance().getScaledRectangle(ImageID.UI_PATH_MARKER);
        markers = new ArrayList<Rectangle>();

        // first gate at 500 and every 900 afterwards
        for (int i = 500; i < maxValue; i += 900) {
            Rectangle marker = new Rectangle(baseMarker);
            marker.center.setX(toWorldUnits(i));
            marker.center.setY(fromTop(marker, 0.9f));
            markers.add(marker);
        }

        baseMarker.visible = false;

        filledMarker = DyeHardResources.getInstance().getTexture(ImageID.UI_PATH_MARKER_FULL);

        dyeMarker = DyeHardResources.getInstance().getScaledRectangle(ImageID.UI_DYE_PATH_MARKER);
        dyeMarker.alwaysOnTop = true;
        dyeMarker.center.setY(fromTop(dyeMarker, 0.9f));

        setValue(currentValue);
    }
    
    /**
     * (Added for ProAPI)
     * Instantiates a new dyehard distance meter with yPadding
     * This additional constructor is for the purpose of adjusting the
     * location (yPadding) of the distance meter UI.
     * @param maxValue the maximum distance (target distance)
     * @param yPadding the horizontal padding of the UI
     */
    public DyehardDistanceMeter(int maxValue, float yPadding) {
        this.maxValue = maxValue;
        currentValue = 0;

        progress = DyeHardResources.getInstance().getScaledRectangle(ImageID.UI_PATH);
        progress.alwaysOnTop = true;

        progress.center.setX(BaseCode.world.getWidth() / 2f);
        progress.center.setY(fromTop(progress, yPadding + 0.5f)); //1.4f

        Rectangle baseMarker = DyeHardResources.getInstance().getScaledRectangle(ImageID.UI_PATH_MARKER);
        markers = new ArrayList<Rectangle>();

        // first gate at 500 and every 900 afterwards
        for (int i = 500; i < maxValue; i += 900) {
            Rectangle marker = new Rectangle(baseMarker);
            marker.center.setX(toWorldUnits(i));
            marker.center.setY(fromTop(marker, yPadding)); //0.9f
            markers.add(marker);
        }

        baseMarker.visible = false;

        filledMarker = DyeHardResources.getInstance().getTexture(ImageID.UI_PATH_MARKER_FULL);

        dyeMarker = DyeHardResources.getInstance().getScaledRectangle(ImageID.UI_DYE_PATH_MARKER);
        dyeMarker.alwaysOnTop = true;
        dyeMarker.center.setY(fromTop(dyeMarker, yPadding)); //0.9f

        setValue(currentValue);
    }

    /**
     * From top.
     *
     * @param image
     * @param vertical padding
     * @return World.Height - Img.Y / 2 - padding
     */
    protected float fromTop(Rectangle image, float padding) {
        return BaseCode.world.getHeight() - image.size.getY() / 2f - padding;
    }

    /**
     * Sets the value.
     *
     * @param value the new value
     */
    public void setValue(int value) {
        currentValue = value;
        dyeMarker.center.setX(toWorldUnits(value));

        for (Rectangle r : markers) {
            if (dyeMarker.center.getX() > r.center.getX()) {
                r.texture = filledMarker;
            }
        }
    }

    /**
     * To world units.
     *
     * @param value to convert into world units
     * @return the float in world units
     */
    protected float toWorldUnits(int value) {
        float scale = progress.size.getX() / maxValue;
        float startX = progress.center.getX() - progress.size.getX() / 2f;

        return startX + value * scale;
    }

    /**
     * Sets the prog texture.
     *
     * @param name the new prog texture
     */
    public void setProgTexture(String name) {
        dyeMarker.texture = BaseCode.resources.loadImage("Textures/" + name
                + ".png");
        dyeMarker.size.set(3.5f, 3.5f); // TODO clean up original dyeMarker
                                        // declaration, also consider not using
                                        // fixed numbers.
    }

    /**
     * Draw front. Moves the dyeMarker and Progress to the front draw set
     */
    public void drawFront() {
        BaseCode.resources.moveToFrontOfDrawSet(dyeMarker);
        BaseCode.resources.moveToFrontOfDrawSet(progress);
    }
}