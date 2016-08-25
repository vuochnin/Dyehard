/** File:		SoundOffButton
 * Description: Subclass of SoundSelectButton that offers functionality to 
 * 				turn the sound off.  There is another class that turns the 
 * 				sound on.
 * Author:		Paul Kessler
 * Date:		2/14/2016
 */
package dyehard.Ui.Buttons;

import Engine.Vector2;
import dyehard.Ui.ClickableMenuLayer;
import dyehard.Util.DyeHardSound;

// TODO: Auto-generated Javadoc
/**
 * The Class SoundOffButton.
 */
public class SoundOffButton extends SoundSelectButton {
	
	/**
	 * Instantiates a new sound select button.
	 *
	 * @param x1 is the new topLeftX
	 * @param x2 is the new bottomRightX
	 * @param y1 is the new topLeftY
	 * @param y2 is the new bottomRightY
	 * @param menuSelect move the menu to the selected button that is passed in as the selector
	 * @param soundTog move the menu to the sound toggle menu
	 * @param soundSelect move the menu to the sound select menu
	 */
	public SoundOffButton(float x1, float x2, float y1, float y2, ClickableMenuLayer menuSelect, 
					ClickableMenuLayer soundTog, Vector2 soundSelect) {
		super(x1, x2, y1, y2, menuSelect, soundTog, soundSelect);
	}

	/**
	 * Turns the sound off
	 */
	@Override
	public void doClickAction() {
		DyeHardSound.setSound(false);
		getSoundTog().move(getSoundOff());
	}
}
