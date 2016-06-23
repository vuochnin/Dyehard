/** File:		QuitButton
 * Description:	Subclass of button which sets the state of the
 * 				game to quit in the doClick method below.
 * Author:		Paul Kessler
 * Date:		2/14/2016
 */
package dyehard.Ui.Buttons;

import Engine.Vector2;
import dyehard.DyeHardGame;
import dyehard.Ui.ClickableMenuLayer;

// TODO: Auto-generated Javadoc
/**
 * The Class QuitButton.
 */
public class QuitButton extends Button {
		
	/**
	 * @param x1 is the new topLeftX
	 * @param x2 is the new bottomRightX
	 * @param y1 is the new topLeftY
	 * @param y2 is the new bottomRightY
	 * @param menuSelect move the menu to the selected button that is passed in as the selector
	 * @param quitSelect move the menu to the quit select
	 */
	public QuitButton(float x1, float x2, float y1, float y2, ClickableMenuLayer menuSelect, Vector2 quitSelect) {
		super(x1, x2, y1, y2, menuSelect, quitSelect);
	}

	/**
	 * - Quits the game
	 */
	@Override
	public void doClickAction() {
		DyeHardGame.setState(DyeHardGame.State.QUIT);
	}
}
