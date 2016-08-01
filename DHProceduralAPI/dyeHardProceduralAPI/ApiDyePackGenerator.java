package dyeHardProceduralAPI;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.Collectibles.DyePack;
import dyehard.Util.Colors;

/**
 * Provides DyePack spawning functionality for the API
 *
 * @author Holden
 */
public class ApiDyePackGenerator {

	private static Random   myRandom;

	/**
	 * The spawn interval
	 */
	private static float    dyeFrequency;

	/**
	 * The horizontal position of the generator
	 */
	private static float    generatorPositionX;

	/**
	 * The active state of the generator
	 */
	private static boolean  active;

	/**
	 * The set of DyePacks
	 */
	private static List<DyePack> dyePacks;

	static
	{
		myRandom = new Random();
		dyeFrequency = 2f;
		active = false;
		dyePacks = new ArrayList<>();
	}

	/**
	 * 	Hide the constructor to simulate a static class. Because Java doesn't have static classes.
	 */
	private ApiDyePackGenerator(){}

	/**
	 * Sets the active state of the spawner
	 * @param val active or inactive
	 */
	public static void setActive(boolean val)
	{
		active = val;
	}

	/**
	 * Sets the spawn interval of debris
	 * @param length the time
	 */
	public static void setInterval(float length)
	{
		dyeFrequency = length;
	}

	/**
	 * Initialize the generator
	 * @param leftEdge
	 */
	public static void initialize(float leftEdge)
	{
		generatorPositionX = leftEdge;
	}

	/**
	 * Spawn a DyePack
	 * @param posX horizontal position
	 * @param posY vertical position
	 * @return the DyePack
	 */
	private static DyePack spawn(float posX, float posY)
	{
		Color randomColor = Colors.randomColor();
		DyePack dye = new DyePack(randomColor);

		dye.initialize(new Vector2(posX, posY));

		return dye;
	}

	/**
	 * Spawn a DyePack.
	 * @param color the color
	 * @param posX horizontal position
	 * @param posY vertical position
	 * @return the DyePack
	 */
	private static DyePack spawn(String color, float posX, float posY)
	{
		Color c = Colors.Red;
		if(color.equalsIgnoreCase("blue")){
			c = Colors.Blue;
		}
		else if(color.equalsIgnoreCase("teal")){
			c = Colors.Teal;
		}
		else if(color.equalsIgnoreCase("green")){
			c = Colors.Green;
		}
		else if(color.equalsIgnoreCase("pink")){
			c = Colors.Pink;
		}
		else if(color.equalsIgnoreCase("yellow")){
			c = Colors.Yellow;
		}
		DyePack dye = new DyePack(c);

		dye.initialize(new Vector2(posX, posY));

		return dye;
	}

	/**
	 * Spawn a DyePack.
	 * @param color the color
	 * @param x horizontal position
	 * @param y vertical position
	 * @return the DyePack's id
	 */
	public static int spawnDyePack(String color, float x, float y)
	{
		DyePack dye = spawn(color, x, y);

		int result =  ApiIDManager.register(dye);

		return  result;
	}


	/**
	 * Spawn a random DyePack.
	 * @return the DyePack's id
	 */
	public static int generateDyePack()
	{
		float regionHeight = BaseCode.world.getHeight()
		                     - BaseCode.world.getWorldPositionY();

		float posY = (regionHeight - DyePack.height) * myRandom.nextFloat()
		      + DyePack.height / 2f;

		DyePack dye = spawn(generatorPositionX, posY);

		int result =  ApiIDManager.register(dye);

		return  result;
	}

	/**
	 * Spawn a DyePack at a specified height.
	 * @return the DyePack's id
	 */
	public static int generateDyePack(float posY)
	{
		DyePack dye = spawn(generatorPositionX, posY);

		dyePacks.add(dye);

		return ApiIDManager.register(dye);
	}

	/**
	 * Update the DyePack manager.
	 */
	public static void update()
	{
		if(active &&
		   ApiTimeManager.repeatingTimer("DYEPACK_GENERATION", dyeFrequency))
		{
			generateDyePack();
			cleanup();
		}
	}

	/**
	 * Remove destroyed DyePacks from the list
	 */
	private static void cleanup()
	{
		// Set up queue for cleaning destroyed packs
		LinkedList<DyePack> removing = new LinkedList<>();

		// Scan list for destroyed packs and add them to the queue
		for(DyePack d : dyePacks)
		{
			if(!d.visible)
			{
				removing.add(d);
			}
		}

		// Remove queued packs from the list
		for(DyePack r : removing)
		{
			dyePacks.remove(r);
		}
	}

	/**
	 * Destroy all dye packs
	 */
	public static void destroy()
	{
		for(DyePack d : dyePacks)
		{
			d.destroy();
		}
	}
}

