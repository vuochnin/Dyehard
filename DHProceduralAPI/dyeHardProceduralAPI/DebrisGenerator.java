package dyeHardProceduralAPI;

import dyehard.Collision.CollidableGameObject;
import dyehard.Enums.ManagerStateEnum;
import dyehard.Obstacles.Debris;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author Holden
 */
public class DebrisGenerator
{
	private static Random RANDOM = new Random();
	private static float        interval;
	private static boolean      active;
	private static List<Debris> debrisList;


	static
	{
		interval = 1.0f;
		active = false;
		debrisList = new ArrayList<Debris>();
	}

	/**
	 * 	Hide the constructor to simulate a static class. Because Java doesn't have static classes.
	 */
	private DebrisGenerator(){}

	/**
	 * Starts the debris generator
	 */
	public static void enable()
	{
		active = true;
	}

	/**
	 * Stops the debris generator
	 */
	public static void disable()
	{
		active = false;
	}

	/**
	 * Sets the spawn interval of debris
	 * @param seconds the time
	 */
	public static void setInterval(float seconds)
	{
		interval = seconds;
	}

	/**
	 * Reports the number of debris instantiated
	 */
	public static int debrisCount()
	{
		return debrisList.size();
	}
	
	/**
	 * Spawns a single debris
	 */
	public static int spawnDebris()
	{
		Debris d = new APIDebris(RANDOM.nextInt(3),100,100);// spawns debris at right edge of screen
		debrisList.add(d);
		
		return IDManager.register(d);
	}

	/**
	 * Spawns a single debris with a specified height
	 * @param height The height at which to spawn the debris
	 */
	public static int spawnDebris(float height)
	{
		Debris d = new APIDebris(RANDOM.nextInt(3), 100, 100, height, height);// spawns debris at right edge of screen
		debrisList.add(d);
		return IDManager.register(d);
	}

	/**
	 * Updates the debris generator
	 */
	public static void update()
	{
		if(active &&
		   TimeManager.repeatingTimer("DEBRIS_GENERATION", interval))
		{
			spawnDebris();
			cleanup();
		}
	}

	/**
	 * Removes destroyed debris from game
	 */
	private static void cleanup()
	{
		// Set up queue for cleaning destroyed debris
		LinkedList<Debris> removing = new LinkedList<>();

		// Scan list for destroyed debris an add them to the queue
		for(Debris d : debrisList)
		{
			if(!d.visible)
			{
				removing.add(d);
			}
		}

		// Remove queued debris from the list
		for(Debris r : removing)
		{
			debrisList.remove(r);
		}
	}
}
