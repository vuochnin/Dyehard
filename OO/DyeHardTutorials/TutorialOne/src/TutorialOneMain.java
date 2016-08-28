/**
 * File:		TutorialOneMain
 * Description:	Example on how to create the entry point into Dye Game
 * Author:		Paul Kessler
 * Date:		2/19/2016
 */
import Engine.GameWindow;

public class TutorialOneMain extends GameWindow {
	
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		(new TutorialOneMain()).startProgram();
	}
	
    public TutorialOneMain() {
        setRunner(new UserCode()); 
    }
}
