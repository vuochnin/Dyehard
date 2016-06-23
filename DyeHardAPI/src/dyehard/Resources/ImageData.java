package dyehard.Resources;

import java.awt.image.BufferedImage;

import Engine.Vector2;

// TODO: Auto-generated Javadoc
/**
 * The Class ImageData.
 */
public class ImageData {
	
    /** The texture path. */
    private String texturePath;    
    
    /** The targeted pixel size. */
    private Vector2 targetedPixelSize;    
    
    /** The actual pixel size. */
    private Vector2 actualPixelSize;
    
    /** The texture. */
    private BufferedImage texture = null;
    
	/**
	 * Gets the texture path.
	 *
	 * @return the texture path
	 */
	public String getTexturePath() {
		return texturePath;
	}

	/**
	 * Sets the texture path.
	 *
	 * @param texturePath the new texture path
	 */
	public void setTexturePath(String texturePath) {
		this.texturePath = texturePath;
	}

	/**
	 * Gets the targeted pixel size.
	 *
	 * @return the targeted pixel size
	 */
	public Vector2 getTargetedPixelSize() {
		return targetedPixelSize;
	}

	/**
	 * Sets the targeted pixel size.
	 *
	 * @param targetedPixelSize the new targeted pixel size
	 */
	public void setTargetedPixelSize(Vector2 targetedPixelSize) {
		this.targetedPixelSize = targetedPixelSize;
	}

	/**
	 * Gets the actual pixel size.
	 *
	 * @return the actual pixel size
	 */
	public Vector2 getActualPixelSize() {
		return actualPixelSize;
	}

	/**
	 * Sets the actual pixel size.
	 *
	 * @param actualPixelSize the new actual pixel size
	 */
	public void setActualPixelSize(Vector2 actualPixelSize) {
		this.actualPixelSize = actualPixelSize;
	}

	/**
	 * Gets the texture.
	 *
	 * @return the texture
	 */
	public BufferedImage getTexture() {
		return texture;
	}

	/**
	 * Sets the texture.
	 *
	 * @param texture the new texture
	 */
	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}
}
