package TutorialFive;

import java.util.ArrayList;
import java.util.List;

import dyehard.Obstacles.Debris;
import dyehard.Util.Timer;
import dyehard.World.GameWorldRegion;

public class DebrisGenerator extends GameWorldRegion {
	
	private static DebrisGenerator instance;
	private Timer timer;
	private float debrisFrequency = 1500f;
	
	static {
		instance = new DebrisGenerator();
	}
	
	private int debrisCount = 1;
	private List<Debris> debrisList;
	
	public static DebrisGenerator getInstance() {
		return instance;
	}
	
	private DebrisGenerator() {
		debrisList = new ArrayList<Debris>();
		timer = new Timer(debrisFrequency);
	}
	
	@Override
	public void initialize(float leftEdge) {
		position = leftEdge + width * 0.5f;
		
		// offset the region to pad the space before the next element
        // this makes the region slightly smaller than it actually should be
        // otherwise
        int offset = 1;
        float region = (rightEdge() - leftEdge()) / (debrisCount + offset);
        for (int i = 0; i < debrisCount; i++) {
            float regionLeft = leftEdge + (i * region);
            float regionRight = regionLeft + region;
            debrisList.add(new Debris(regionLeft, regionRight));
        }
	}
	
	public void update() {
		if(timer.isDone())
		{
			initialize(100f);
			timer.reset();
		}
	}

	@Override
	public void destroy() {
		for(Debris deb: debrisList) {
			deb.destroy();
		}
	}
}
