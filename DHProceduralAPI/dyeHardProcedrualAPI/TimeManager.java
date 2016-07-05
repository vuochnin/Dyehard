package dyeHardProcedrualAPI;

import dyehard.Util.Timer;
import java.util.HashMap;

/**
 * @author Holden
 *
 * Handles a set of timers matched to string IDs.
 */
public class TimeManager
{
	/**
	 * A representation of a conversion from seconds to milliseconds.
	 */
	private static final float SECONDS_TO_MILLISECONDS = 1000;

	/**
	 * The storage of String Timer pairs
	 */
	private static HashMap<String, Timer> timers;

	// Initialize the HashMap
	static
	{
		timers = new HashMap<>();
	}

	/**
	 * 	Hide the constructor to simulate a static class. Because Java doesn't have static classes.
	 */
	private TimeManager(){}

	/**
	 * Sets a timer associated with an ID.
	 * <br><br>
	 * If there is already a timer associated with the ID,<br>
	 * * Resets the timer.
	 *
	 * @param id The string ID
	 * @param seconds The length of the timer in seconds
	 */
	public static void setTimer(String id, float seconds)
	{
		seconds *= SECONDS_TO_MILLISECONDS;

		if(timers.containsKey(id))
		{
			Timer t = timers.get(id);
			if(t.isDone())
			{
				t.setInterval(seconds);
			}
			t.reset();
		}
		else
		{
			System.out.println("Made \"" + id + "\" timer");
			timers.put(id, new Timer(seconds));
		}
	}

	/**
	 * Reports whether a timer associated with an ID has finished
	 *
	 * @param id The ID of the timer to check
	 */
	public static boolean isTimerFinished(String id)
	{
		if(timers.containsKey(id))
		{
			return timers.get(id).isDone();
		}

		System.err.println("Timer \"" + id + "\" has not been created.");

		return false;
	}

	/**
	 * Handles a repeating timer.
	 * <br><br>
	 * Returns true every time the timer elapses, false otherwise.
	 *
	 * @param id the ID of the timer
	 * @param seconds
	 */
	public static boolean repeatingTimer(String id, float seconds)
	{

		boolean result = false;

		if(timers.containsKey(id))
		{
			result = timers.get(id).isDone();
			if(result)
				setTimer(id, seconds);
		}
		else
		{
			setTimer(id, seconds);
		}

		return result;
	}
}
