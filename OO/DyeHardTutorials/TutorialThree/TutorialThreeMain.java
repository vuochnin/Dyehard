/**
 * File:		TutorialTwoMain
 * Description:	Example on how to start the Dye Game
 * Author:		Paul Kessler
 * Date:		2/19/2016
 */
package TutorialThree;

import Engine.GameWindow;

public class TutorialThreeMain extends GameWindow {
	
	private static final long serialVersionUID = 1L;
	
	public static void main(String args[]) {
		(new TutorialThreeMain()).startProgram();
	}
	
	public TutorialThreeMain() {
		setRunner(new UserCode());
	}
}
