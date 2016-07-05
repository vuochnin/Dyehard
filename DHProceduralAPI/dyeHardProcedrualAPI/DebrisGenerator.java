package dyeHardProcedrualAPI;

import dyehard.Enums.ManagerStateEnum;
import dyehard.Obstacles.Debris;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Holden
 */
public class DebrisGenerator
{
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
	 * TODO: Overload with Y parameter *Requires modification to Debris class*
	 */
	public static void spawnDebris()
	{
		Debris d = new Debris(100,100);// spawns debris at right edge of screen
		debrisList.add(d);
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
