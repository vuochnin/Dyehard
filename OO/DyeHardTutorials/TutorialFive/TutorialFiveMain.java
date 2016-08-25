/**
 * File:		TutorialTwoMain
 * Description:	Example on how to start the Dye Game
 * Author:		Paul Kessler
 * Date:		2/19/2016
 */
package TutorialFive;

import Engine.GameWindow;

public class TutorialFiveMain extends GameWindow {
	
	private static final long serialVersionUID = 1L;
	
	public static void main(String args[]) {
		(new TutorialFiveMain()).startProgram();
	}
	
	public TutorialFiveMain() {
		setRunner(new UserCode());
	}
}
