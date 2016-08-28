/**
 * File:		Sammy
 * Description:	Example on how to create the entry point into Dye Game
 * Author:		Sammy
 * Date:		2/19/2016
 */
import Engine.GameWindow;

public class TutorialOneMainSammy extends GameWindow {
	
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		(new TutorialOneMainSammy()).startProgram();
	}
	
    public TutorialOneMainSammy() {
        // setRunner(new SammyTutorialOne());
    	// setRunner (new SammyWormholesTutorial());
        // setRunner (new SammyEnemyTutorial());
        // setRunner (new SammyObstacles());
    	setRunner (new SammyAllTogether());
    }
}
