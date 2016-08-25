/**
 * File:		TutorialTwoMain
 * Description:	Example on how to start the Dye Game
 * Author:		Paul Kessler
 * Date:		2/19/2016
 */
package TutorialFour;

import Engine.GameWindow;

public class TutorialFourMain extends GameWindow {
	
	private static final long serialVersionUID = 1L;
	
	public static void main(String args[]) {
		(new TutorialFourMain()).startProgram();
	}
	
	public TutorialFourMain() {
		setRunner(new UserCode());
	}
}
