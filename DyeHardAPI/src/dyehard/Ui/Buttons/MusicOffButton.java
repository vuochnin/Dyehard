/** File:		MusicOffButton
 * Description:	MusicSelectButton subclass that offers additional
 * 				functionality to toggle music off.  Note that there
 * 				is a seperate class to toggle music back on
 * Author:		Paul Kessler
 * Date:		2/14/2016
 */
package dyehard.Ui.Buttons;

import Engine.Vector2;
import dyehard.Ui.ClickableMenuLayer;
import dyehard.Util.DyeHardSound;

// TODO: Auto-generated Javadoc
/**
 * The Class MusicOffButton.
 */
public class MusicOffButton extends MusicSelectButton {

	/**
	 * @param x1 is the new topLeftX
	 * @param x2 is the new bottomRightX
	 * @param y1 is the new topLeftY
	 * @param y2 is the new bottomRightY
	 * @param menuSelect move the menu to the selected button that is passed in as the selector
	 * @param musicTog move the menu to the music toggle menu
	 * @param musicSelect selects the music on or off
	 */
	public MusicOffButton(float x1, float x2, float y1, float y2, ClickableMenuLayer menuSelect, 
					ClickableMenuLayer musicTog, Vector2 musicSelect) {
		super(x1, x2, y1, y2, menuSelect, musicTog, musicSelect);
	}
	
	/**
	 * - If music is playing, turn the music off
	 */
	public void doClickAction() {
		if(isMusic()) {
			DyeHardSound.setMusic(false);
			DyeHardSound.stopBgMusic();
			getMusicTog().move(getMusicOff());
			setMusic(false);
		}
	}
}
