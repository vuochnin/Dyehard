/**
 * File:		TutorialTwoMain
 * Description:	Example on how to start the Dye Game
 * Author:		Paul Kessler
 * Date:		2/19/2016
 */
package TutorialTwo;

import Engine.GameWindow;

public class TutorialTwoMain extends GameWindow {
	
	private static final long serialVersionUID = 1L;
	
	public static void main(String args[]) {
		(new TutorialTwoMain()).startProgram();
	}
	
	public TutorialTwoMain() {
		setRunner(new UserCode());
	}
}
