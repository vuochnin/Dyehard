import dyehard.Obstacles.Debris;

/**
 * To be used with Lab 4. 
 * @author Sammy
 *
 */
public class Lab4DebrisGenerator extends DebrisGenerator {
	
	static { instance = new Lab4DebrisGenerator(); }
	
	public static Lab4DebrisGenerator getInstance() {
		return (Lab4DebrisGenerator) instance;
	}
	
	public Lab4DebrisGenerator(){}
	
	
	// This is what the student should override. Mainly by adding their new
	// Debris into the debrisList. Students can also modify update() to use
	// a different timer countdown.
	@Override
	public void initialize(float leftEdge) {
		super.initialize(leftEdge);
			
        float region = (rightEdge() - leftEdge()) / 2;
        float regionLeft = leftEdge + region;
        float regionRight = regionLeft + region;
        debrisList.add(new Lab4Correct(regionLeft, regionRight));
	}
}
