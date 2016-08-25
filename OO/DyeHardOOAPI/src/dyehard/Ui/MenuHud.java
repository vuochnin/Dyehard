package dyehard.Ui;
// this is a wrapper to contain privacy leaks and poorly written code.  This was known in advance that the primitive class should be refactored.
import java.awt.image.BufferedImage;

import Engine.Vector2;
import dyehard.DyehardRectangle;


// TODO: Auto-generated Javadoc
/**
 * The Class MenuHud.
 */
public class MenuHud extends DyehardRectangle {
	
	/**
	 * Instantiates a new menu hud.
	 *
	 * @param size the size
	 * @param center the center
	 * @param texture the texture
	 * @param alwaysOnTop the always on top
	 * @param visible the visible
	 */
	public MenuHud(Vector2 size, Vector2 center, BufferedImage texture, boolean alwaysOnTop, boolean visible) {
		
		setSize(size);
		setCenter(center);
		setTexture(texture);
		setAlwaysOnTop(alwaysOnTop);
		setVisible(visible);
	}
	
	/**
	 * Sets the size.
	 *
	 * @param size the new size
	 */
	public void setSize(Vector2 size) {
		this.size = size;
	}
	
	/**
	 * Sets the center.
	 *
	 * @param center the new center
	 */
	public void setCenter(Vector2 center) {
		this.center = center;
	}
	
	/**
	 * Sets the texture.
	 *
	 * @param texture the new texture
	 */
	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}
	
	/**
	 * Sets the always on top.
	 *
	 * @param alwaysOnTop the new always on top
	 */
	public void setAlwaysOnTop(boolean alwaysOnTop) {
		this.alwaysOnTop = alwaysOnTop;
	}
	
	/**
	 * Sets the visible.
	 *
	 * @param visible the new visible
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
