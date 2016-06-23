/** File:		Button
// Description:	SuperClass that holds the four floats that make the top left
//				and bottom right of a button.  Has a ClickableButtonLayer which
//				moves the menu to the buttonSelect.  i.e if the player dies, the 
//				Menu moves to the restart select which has a restart and quit
//				option button
// Author:		Paul Kessler
// Date:		2/14/16
*/
package dyehard.Ui.Buttons;

import Engine.Vector2;
import dyehard.Ui.ClickableMenuLayer;

// TODO: Auto-generated Javadoc
/**
 * The Class Button.
 */
public abstract class Button {
	
	/** The top left x. */
	// four floats that make up a button
	private float topLeftX;
	
	/** The top left y. */
	private float topLeftY;
	
	/** The bottom right x. */
	private float bottomRightX;
	
	/** The bottom right y. */
	private float bottomRightY;
	
	/** The button select. */
	// The button to select which is passed in through the constructor
	private Vector2 buttonSelect;
	
	/** The menu select. */
	// Moves the menu to the selected button
	private ClickableMenuLayer menuSelect;
	
	/**
	 * Instantiates a new button.
	 * - Four floats used to construct a button
	 * @param topLeftX is the new topLeftX
	 * @param bottomRightX is the new bottomRightX
	 * @param topLeftY is the new topLeftY
	 * @param bottomRightY is the new bottomRightY
	 * @param menuSelect Move the menu to the selected button that is passed in as the selector
	 * @param selector The button that the menu will highlight
	 */
	public Button(float topLeftX, float bottomRightX, float topLeftY, float bottomRightY, ClickableMenuLayer menuSelect, Vector2 selector) {
		this.topLeftX = topLeftX;
		this.topLeftY = topLeftY;
		this.bottomRightX = bottomRightX;
		this.bottomRightY = bottomRightY;	
		buttonSelect = selector;
		setMenuSelect(menuSelect);
	}
	
	
	/**
	 * Gets the select.
	 *
	 * @return buttonSelect
	 */
	public Vector2 getSelect() {
		return buttonSelect;
	}

	/**
	 * Sets the select.
	 *
	 * @param selector is the new buttonSelect
	 */
	public void setSelect(Vector2 selector) {
		buttonSelect = selector;
	}
	
	/**
	 * Was clicked.
	 *
	 * @param x 	- X position of the mouse
	 * @param y 	- Y position of the mouse
	 * @return 	- True if the mouse x and y are within the button
	 * 	- False otherwise
	 */
	public boolean wasClicked(float x, float y) {
		return x >= getTopLeftX() && x <= getBottomRightX()
				&& y >= getTopLeftY() && y <= getBottomRightY();
	}
	
	/**
	 * Sets the top left x.
	 *
	 * @param topLeftX the new top left x
	 */
	public void setTopLeftX(float topLeftX) {
		this.topLeftX = topLeftX;
	}
	
	/**
	 * Sets the top left y.
	 *
	 * @param topLeftY the new top left y
	 */
	public void setTopLeftY(float topLeftY) {
		this.topLeftY = topLeftY;
	}
	
	/**
	 * Sets the bottom right y.
	 *
	 * @param bottomRightY the new bottom right y
	 */
	public void setBottomRightY(float bottomRightY) {
		this.bottomRightY = bottomRightY;
	}
	
	/**
	 * Sets the bottom right x.
	 *
	 * @param bottomRightX the new bottom right x
	 */
	public void setBottomRightX(float bottomRightX) {
		this.bottomRightX = bottomRightX;
	}
	
	/**
	 * Sets the menu select.
	 *
	 * @param menuSelect the new menu select
	 */
	public void setMenuSelect(ClickableMenuLayer menuSelect) {
		this.menuSelect = menuSelect;
	}
	
	/**
	 * Gets the menu select.
	 *
	 * @return the menu select
	 */
	public ClickableMenuLayer getMenuSelect() {
		return menuSelect;
	}
	
	/**
	 * Gets the top left x.
	 *
	 * @return the top left x
	 */
	public float getTopLeftX() {
		return topLeftX;
	}

	/**
	 * Gets the top left y.
	 *
	 * @return the top left y
	 */
	public float getTopLeftY() {
		return topLeftY;
	}

	/**
	 * Gets the bottom right y.
	 *
	 * @return the bottom right y
	 */
	public float getBottomRightY() {
		return bottomRightY;
	}
	
	/**
	 * Gets the bottom right x.
	 *
	 * @return the bottom right x
	 */
	public float getBottomRightX() {
		return bottomRightX;
	}
	
	/**
	 *  Each subclass button will define a method on what it will do 
	 *  when clicked by the user.
	 */
	public abstract void doClickAction();
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Button [topLeftX=" + topLeftX + ", topLeftY=" + topLeftY + ", bottomRightX=" + bottomRightX
				+ ", bottomRightY=" + bottomRightY + "]";
	}

	/**
	 *  The menu will move to the selector button.
	 */
	public void menuSelect() {
		getMenuSelect().move(getSelect());
	}
	
}