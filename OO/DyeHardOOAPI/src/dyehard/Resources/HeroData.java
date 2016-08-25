package dyehard.Resources;

// TODO: Auto-generated Javadoc
/**
 * The Class HeroData.
 */
public class HeroData {
	
    /** The hero width. */
    private float heroWidth;
    
    /** The hero height. */
    private static float heroHeight;	
	
	/** The hero jet speed. */
	private static float heroJetSpeed;    
    
    /** The hero speed limit. */
    private static float heroSpeedLimit;    
    
    /** The hero drag. */
    private static float heroDrag;
    
	/**
	 * Gets the hero width.
	 *
	 * @return the hero width
	 */
	public float getHeroWidth() {
		return heroWidth;
	}
	
	/**
	 * Sets the hero width.
	 *
	 * @param heroWidth the new hero width
	 */
	public void setHeroWidth(float heroWidth) {
		this.heroWidth = heroWidth;
	}

	/**
	 * Gets the hero height.
	 *
	 * @return the hero height
	 */
	public float getHeroHeight() {
		return heroHeight;
	}
	
	/**
	 * Sets the hero height.
	 *
	 * @param heroHeight the new hero height
	 */
	public void setHeroHeight(float heroHeight) {
		HeroData.heroHeight = heroHeight;
	}
	
	/**
	 * Gets the hero jet speed.
	 *
	 * @return the hero jet speed
	 */
	public float getHeroJetSpeed() {
		return heroJetSpeed;
	}
	
	/**
	 * Sets the hero jet speed.
	 *
	 * @param heroJetSpeed the new hero jet speed
	 */
	public void setHeroJetSpeed(float heroJetSpeed) {
		HeroData.heroJetSpeed = heroJetSpeed;
	}
	
	/**
	 * Gets the hero speed limit.
	 *
	 * @return the hero speed limit
	 */
	public float getHeroSpeedLimit() {
		return heroSpeedLimit;
	}
	
	/**
	 * Sets the hero speed limit.
	 *
	 * @param heroSpeedLimit the new hero speed limit
	 */
	public void setHeroSpeedLimit(float heroSpeedLimit) {
		HeroData.heroSpeedLimit = heroSpeedLimit;
	}
	
	/**
	 * Gets the hero drag.
	 *
	 * @return the hero drag
	 */
	public float getHeroDrag() {
		return heroDrag;
	}
	
	/**
	 * Sets the hero drag.
	 *
	 * @param heroDrag the new hero drag
	 */
	public void setHeroDrag(float heroDrag) {
		HeroData.heroDrag = heroDrag;
	}
}
