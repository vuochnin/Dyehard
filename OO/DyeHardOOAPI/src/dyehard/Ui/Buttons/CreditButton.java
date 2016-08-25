/** File:  		CreditButton
// Description:	Button subclass had specialized functionality to
//              support the toggling visibility of the credit screen
// Author:		Paul Kessler
// Date:		2/14/2016
*/
package dyehard.Ui.Buttons;

import Engine.Vector2;
import dyehard.DyeHardGame;
import dyehard.GameScreens.CreditScreen;
import dyehard.Ui.ClickableMenuLayer;

// TODO: Auto-generated Javadoc
/**
 * The Class CreditButton.
 */
public class CreditButton extends Button {
	
	/** The credits. */
	// Used to display the credits
	private CreditScreen credits;

	/**
	 * @param x1 is the new topLeftX
	 * @param x2 is the new bottomRightX
	 * @param y1 is the new topLeftY
	 * @param y2 is the new bottomRightY
	 * @param menuSelect is the new menu layer to select
	 */
	public CreditButton(float x1, float x2, float y1, float y2, ClickableMenuLayer menuSelect) {
		super(x1, x2, y1, y2, menuSelect, new Vector2(38.383f, 18.95f));
		credits = new CreditScreen();
	}

	/**
	 *	Will pause the game and display the credits
	 */
	@Override
	public void doClickAction() {
		DyeHardGame.setState(DyeHardGame.State.PAUSED);
		getCredits().showScreen(true);
		
	}

	/**
	 * @return the credits object
	 */
	public CreditScreen getCredits() {
		return credits;
	}
	
    /**
     * - Turns off the credits
     */
    public void creditsOff() {
        if (credits.isShown()) {
            credits.showScreen(false);
        }
    }

    /**
     * @return
     * 	- True if the credits are being shown
     * 	_ False otherwise
     */
    public boolean isShown() {
        return credits.isShown();
    }

}
