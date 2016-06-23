package dyehard.Resources;

import org.w3c.dom.NodeList;

// TODO: Auto-generated Javadoc
/**
 * The Class EnemyData.
 */
public class EnemyData {
	
	/** The width. */
	private float width;	
	
	/** The height. */
	private float height;	
	
	/** The sleep timer. */
	private float sleepTimer;	
	
	/** The speed. */
	private float speed;	
	
	/** The unique attributes. */
	private NodeList uniqueAttributes;
	
	/**
	 * Sets the sleep timer.
	 *
	 * @param sleepTimer the new sleep timer
	 */
	public void setSleepTimer(float sleepTimer) {
		this.sleepTimer = sleepTimer;
	}

	/**
	 * Sets the speed.
	 *
	 * @param speed the new speed
	 */
	public void setSpeed(float speed) {
		this.speed = speed;
	}

	/**
	 * Sets the unique attributes.
	 *
	 * @param uniqueAttributes the new unique attributes
	 */
	public void setUniqueAttributes(NodeList uniqueAttributes) {
		this.uniqueAttributes = uniqueAttributes;
	}
	
	/**
	 * Sets the height.
	 *
	 * @param height the new height
	 */
	public void setHeight(float height) {
		this.height = height;
	}

	/**
	 * Sets the width.
	 *
	 * @param width the new width
	 */
	public void setWidth(float width) {
		this.width = width;
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
	 * Gets the height.
	 *
	 * @return the height
	 */
	public float getHeight() {
		return height;
	}
	
	/**
	 * Gets the sleep timer.
	 *
	 * @return the sleep timer
	 */
	public float getSleepTimer() {
		return sleepTimer;
	}
	
	/**
	 * Gets the unique attributes.
	 *
	 * @return the unique attributes
	 */
	public NodeList getUniqueAttributes() {
		return uniqueAttributes;
	}
	
	/**
	 * Gets the speed.
	 *
	 * @return the speed
	 */
	public float getSpeed() {
		return speed;
	}
}