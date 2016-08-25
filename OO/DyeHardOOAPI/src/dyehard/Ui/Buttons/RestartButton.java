/** File:		RestartButton
 * Description:	Subclass of button that sets the state of the
 * 				game to restart in the doClick method below
 * Author:		Paul Kessler
 * Date:		2/14/2016
 */
package dyehard.Ui.Buttons;

import Engine.Vector2;
import dyehard.DyeHardGame;
import dyehard.Ui.ClickableMenuLayer;

// TODO: Auto-generated Javadoc
/**
 * The Class RestartButton.
 */
public class RestartButton extends Button {
	
	/**
	 * @param x1 is the new topLeftX
	 * @param x2 is the new bottomRightX
	 * @param y1 is the new topLeftY
	 * @param y2 is the new bottomRightY
	 * @param menuSelect move the menu to the selected button that is passed in as the selector
	 * @param restartSelect moves to the menu that restarts the game
	 */
	public RestartButton(float x1, float x2, float y1, float y2, ClickableMenuLayer menuSelect, Vector2 restartSelect) {
		
		super(x1, x2, y1, y2,menuSelect, restartSelect);
	}

	/**
	 * - Restarts the game
	 */
	@Override
	public void doClickAction() {
		DyeHardGame.setState(DyeHardGame.State.RESTART);
	}
}
