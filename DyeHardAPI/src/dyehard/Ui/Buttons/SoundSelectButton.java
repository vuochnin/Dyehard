/** File:		SoundSelectButton
 *  Description: Subclass of button that has an extra ClickableMenuLayer
 * 				that will present to button options which are soundOn 
 * 				or soundOff which toggle the sound on and off
 * Author:		Paul Kessler
 * Date:		2/14/2016
 */

package dyehard.Ui.Buttons;

import Engine.Vector2;
import dyehard.Ui.ClickableMenuLayer;
import dyehard.Util.DyeHardSound;

// TODO: Auto-generated Javadoc
/**
 * The Class SoundSelectButton.
 */
public class SoundSelectButton extends Button {
	
	/** The sound tog. */
	private ClickableMenuLayer soundTog;
	
	/** The sound on. */
	private Vector2 soundOn;
	
	/** The Sound off. */
	private Vector2 SoundOff;
	
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
	public SoundSelectButton(float x1, float x2, float y1, float y2, ClickableMenuLayer menuSelect,
					ClickableMenuLayer soundTog, Vector2 soundSelect) {
		super(x1, x2, y1, y2, menuSelect, soundSelect);
		setSoundTog(soundTog);
		setSoundOn(new Vector2(46.328f, 36.111f));
		setSoundOff(new Vector2(56.614f, 36.111f));
	}

	/**
	 * - Moves the menu to the soundOn or soundOff button.
	 */
	@Override
	public void doClickAction() {
		DyeHardSound.setSound(!DyeHardSound.getSound());
		if (DyeHardSound.getSound()) {
			getSoundTog().move(getSoundOn());
		} else {
			getSoundTog().move(getSoundOff());
		}
	}

	/**
	 * Sets the sound tog.
	 *
	 * @param soundTog the new sound tog
	 */
	public void setSoundTog(ClickableMenuLayer soundTog) {
		this.soundTog = soundTog;
	}
	
	/**
	 * Sets the sound off.
	 *
	 * @param soundOff the new sound off
	 */
	public void setSoundOff(Vector2 soundOff) {
		SoundOff = soundOff;
	}

	/**
	 * Sets the sound on.
	 *
	 * @param soundOn the new sound on
	 */
	public void setSoundOn(Vector2 soundOn) {
		this.soundOn = soundOn;
	}

	/**
	 * Gets the sound off.
	 *
	 * @return the sound off
	 */
	public Vector2 getSoundOff() {
		return SoundOff;
	}

	/**
	 * Gets the sound tog.
	 *
	 * @return the sound tog
	 */
	public ClickableMenuLayer getSoundTog() {
		return soundTog;
	}
	
	/**
	 * Gets the sound on.
	 *
	 * @return the sound on
	 */
	public Vector2 getSoundOn() {
		return soundOn;
	}
}
	