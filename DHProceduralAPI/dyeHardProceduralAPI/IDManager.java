package dyeHardProceduralAPI;

import dyehard.Collision.CollidableGameObject;

import java.util.*;
import java.util.Map.Entry;

import org.omg.CORBA.INITIALIZE;

public class IDManager
{
	private static boolean dirty;
	private static int nextID;

	private static
		HashMap<Integer, CollidableGameObject> idMap;

	private static Integer[] indexed;

	static
	{
		initialize();
	}

	private static void initialize()
	{
		dirty = true;
		nextID = 0;
		idMap = new HashMap<>();		
	}
	
	private IDManager(){}

	public static int count()
	{
		return idMap.size();
	}

	/**
	 * Retrieves the ID of a registered object. Used in for loops.
	 * @param index
	 * @return
	 */
	public static int getID(int index)
	{
		if (index < 0)
		{
			System.err.println("Invalid index for getID: " + index);
			return -1;
		}

		if(dirty)
		{
			indexed = idMap.keySet().toArray(new Integer[0]);
			dirty = false;
		}

		if (index >= indexed.length)
		{
			System.err.println("Invalid index for getID: " + index);
			return -1;
		}

		return indexed[index];
	}

	/**
	 * Associate an ID with an object
	 * @param obj The object to be assigned an ID
	 * @return The ID assigned to the object
	 */
	public static int register(CollidableGameObject obj)
	{
		if(obj == null)
		{
			System.err.println("IDManager: tried to register null object");
			return -1;
		}

		int result = nextID;

		idMap.put(nextID++, obj);

		dirty = true;

		return result;
	}

	/**
	 * Search the set of objects associated with IDs for missing objects.
	 * Remove IDs associated with destroyed objects.
	 */
	public static void cleanup()
	{
		List<CollidableGameObject> currentSet
			= Arrays.asList(CollisionManager.getObjects());

		List<Entry<Integer, CollidableGameObject>> entries = new ArrayList<>();

		entries.addAll(idMap.entrySet()); // Shallow clone entries to avoid concurrent exception

		for(Entry<Integer, CollidableGameObject> e : entries)
		{
			if (!currentSet.contains(e.getValue()))
			{
				dirty = true;
				idMap.remove(e.getKey());
			}
		}
	}

	public static void reset()
	{
		for(CollidableGameObject o : idMap.values())
		{
			o.destroy();
		}
		initialize();
	}
	
	/**
	 * Retrieve an object by ID
	 * @param id
	 * @return
	 */
	public static CollidableGameObject get(int id)
	{
		return idMap.get(id);
	}
	
	public static int reverseLookupID(CollidableGameObject obj)
	{
		for(Entry<Integer, CollidableGameObject> e : idMap.entrySet())
		{
			if(e.getValue() == obj)
				return e.getKey();
		}
		
		return -1;
	}
}
