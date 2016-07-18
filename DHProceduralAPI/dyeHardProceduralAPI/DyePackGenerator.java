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
 * @author Holden
 */
public class DyePackGenerator {

	private static Random   myRandom;
	private static float    dyeFrequency;
	private static float    generatorPositionX;
	private static boolean  active;

	private static List<DyePack> dyePacks;

	static
	{
		myRandom = new Random();
		dyeFrequency = 2f;
		active = false;
		dyePacks = new ArrayList<>();
	}

	private DyePackGenerator(){}

	public static void setActive(boolean val)
	{
		active = val;
	}

	public static void setInterval(float length)
	{
		dyeFrequency = length;
	}

	public static void initialize(float leftEdge)
	{
		generatorPositionX = leftEdge;
	}

	private static DyePack spawn(float posX, float posY)
	{
		Color randomColor = Colors.randomColor();
		DyePack dye = new DyePack(randomColor);

		dye.initialize(new Vector2(posX, posY));

		return dye;
	}

	public static int generateDyePack()
	{
		float regionHeight = BaseCode.world.getHeight()
		                     - BaseCode.world.getWorldPositionY();

		float posY = (regionHeight - DyePack.height) * myRandom.nextFloat()
		      + DyePack.height / 2f;

		DyePack dye = spawn(generatorPositionX, posY);

		int result =  IDManager.register(dye);

		System.out.println("Spawned DyePack with ID: " + result);

		return  result;
	}

	public static int generateDyePack(float posY)
	{
		DyePack dye = spawn(generatorPositionX, posY);

		dyePacks.add(dye);

		return IDManager.register(dye);
	}

	public static void update()
	{
		if(active &&
		   TimeManager.repeatingTimer("DYEPACK_GENERATION", dyeFrequency))
		{
			generateDyePack();
			cleanup();
		}
	}
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

	public static void destroy()
	{
		for(DyePack d : dyePacks)
		{
			d.destroy();
		}
	}
}

