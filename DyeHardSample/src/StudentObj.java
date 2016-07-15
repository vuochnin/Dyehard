import java.awt.Color;
import java.awt.image.BufferedImage;

import Engine.BaseCode;
import Engine.Vector2;

// TODO: Auto-generated Javadoc
/**
 * The Class StudentObj.
 */
public class StudentObj {
    
    /** The height. */
    private float height;
    
    /** The width. */
    private float width;
    
    /** The center. */
    private final Vector2 center;
    
    /** The texture. */
    private BufferedImage texture;
    
    /** The color. */
    private Color color;

    /**
     * Instantiates a new student obj.
     */
    public StudentObj() {
        center = new Vector2(0, 0);
        height = 5f;
        width = 5f;
        texture = BaseCode.resources.loadImage("Beak.png");
    }

    /**
     * StudentObj
     * @purpose	Instantiates a new student object.
     *
     * @param 	c, the center
     * @param 	w, the width
     * @param 	h, the height
     */
    public StudentObj(Vector2 c, float w, float h) {
        center = c;
        height = h;
        width = w;
        texture = BaseCode.resources.loadImage("Beak.png");
    }

    /**
     * Sets the center.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public void setCenter(float x, float y) {
        center.set(x, y);
    }

    /**
     * Sets the height.
     *
     * @param h the new height
     */
    public void setHeight(float h) {
        height = h;
    }

    /**
     * Sets the width.
     *
     * @param w the new width
     */
    public void setWidth(float w) {
        width = w;
    }

    /**
     * Sets the texture.
     *
     * @param img the new texture
     */
    public void setTexture(BufferedImage img) {
        texture = img;
    }

    /**
     * Sets the color.
     *
     * @param c the new color
     */
    public void setColor(Color c) {
        color = c;
    }

    /**
     * Gets the center.
     *
     * @return the center
     */
    public Vector2 getCenter() {
        return center;
    }

    /**
     * Gets the height.
     *
     * @return the height
     */
    public float getHeight() {
        return height;
    }

    /**
     * Gets the width.
     *
     * @return the width
     */
    public float getWidth() {
        return width;
    }

    /**
     * Gets the color.
     *
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Student Object";
    }

    /**
     * Gets the texture.
     *
     * @return the texture
     */
    public BufferedImage getTexture() {
        return texture;
    }
}
