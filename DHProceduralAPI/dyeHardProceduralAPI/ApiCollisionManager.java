package dyeHardProceduralAPI;


import java.util.HashMap;
import java.util.Set;

import dyehard.Collectibles.*;
import dyehard.Collectibles.PowerUp;
import dyehard.Collision.CollidableGameObject;
import dyehard.Enemies.Enemy;
import dyehard.Obstacles.Debris;
import dyehard.Player.Hero;
import dyehard.Util.Colors;
import dyehard.Weapons.Bullet;
import dyehard.World.WormHole.*;

/**
 * Provides custom collision functionality for the API.
 * Records collisions for use in user-defined behaviour.
 * @author Holden
 */
public class ApiCollisionManager {

	/**
	 * Holds identifiers for collisions.
	 *
	 * The key is a pair of ints formatted as "#,#"
	 */
	private static HashMap<String, Boolean> collisionMemory;

	/**
	 * A reference to the base CollisionManager instance
	 */
	private static dyehard.Collision.CollisionManager instance;

	static
	{
		collisionMemory = new HashMap<>();
		instance = dyehard.Collision.CollisionManager.getInstance();
	}

	/**
	 * The set of objects managed by the collision manager
	 */
	private static CollidableGameObject[] objects;

	/**
	 * Tell the base CollisionManager to refresh the set of objects
	 */
	public static void refreshSet()
	{
		instance.updateSet();
	}

	/**
	 * Retrieve the set of objects managed by the collision manager
	 * @return the set of objects managed by the collision manager
	 */
	public static CollidableGameObject[] getObjects()
	{
		return objects;
	}

	/**
	 * Find a game object based on the associated ID
	 * @param id the ID number
	 * @return the game object
	 */
	private static CollidableGameObject lookup(int id)
	{
		return ApiIDManager.get(id);
	}

	/**
	 * Retrieve the type of an object
	 * @param i the ID number
	 * @return the type of the object
	 */
	public static String getType(int i)
	{
		return parseType(lookup(i));
	}

	/**
	 * Retrieve the subtype of an object
	 * @param i the ID number
	 * @return the subtype of the object
	 */
	public static String getSubtype(int i)
	{
		return getSubtype(getType(i), lookup(i));
	}

	/**
	 * Check if a collision happened between two objects
	 * @param id1 the first ID number
	 * @param id2 the second ID number
	 */
	public static boolean rememberCollision(int id1, int id2)
	{
		return collisionMemory.getOrDefault(id1 + "," + id2, false);
	}

	/**
	 * Update the API CollisionManager
	 */
	public static void update(){
		collisionMemory.clear();
		
		refreshSet();

		Set<CollidableGameObject> orig = instance.getCollidables();

		objects = orig.toArray(new CollidableGameObject[0]);

		int count = objects.length;
		
		// ACTORS: Hero, Enemies
		// CollidableGameObjects: DyePacks, PowerUps, Bullets, and Debris
		for(int i = 0; i < count; i++)
		{
			//if(objects[firstID].collideState() != ManagerStateEnum.ACTIVE)
				//continue;

			// Clear objects that are far out of bounds to the right
			//
			// Special behavior is required for this, since the engine's out-of-bounds code
			// does not handle the right side of the boundary, because it would mess up the
			// behaviour of wormholes.
			if(objects[i].center.getX() > 200)
			{
				switch(parseType(objects[i]))
				{
				case "Debris":
				case "Bullet":
				case "Enemy":
				case "DyePack":
				case "PowerUp":
					objects[i].destroy();
					continue; // Object is being destroyed, no need to check collision.
				default:
					// Continue with collision check
				}
			}

			for(int j = i+1; j < count; j++)
			{
				//if(objects[secondID].collideState() != ManagerStateEnum.ACTIVE)
					//continue;
				
				if(objects[i].collided(objects[j]))
				{	
					objects[i].handleCollision(objects[j]);
					objects[j].handleCollision(objects[i]);
					
					String key = ApiIDManager.reverseLookupID(objects[i])+
							"," + ApiIDManager.reverseLookupID(objects[j]);
					
					collisionMemory.put(key, true);
				}
			}
		}
	}

	/**
	 * Figure out the type of an object
	 * @param obj the object
	 * @return the string representation of the type
	 */
	private static String parseType(CollidableGameObject obj)
	{
		if(obj instanceof Hero){
			return "Hero";
		}
		else if(obj instanceof Enemy){
			return "Enemy";
		}
		else if(obj instanceof Debris){
			return "Debris";
		}
		else if(obj instanceof Bullet){
			return "Bullet";
		}
		else if(obj instanceof DyePack){
			return "DyePack";
		}
		else if(obj instanceof PowerUp){
			return "PowerUp";
		}
		else if(obj instanceof GateDoor){
			return "GateDoor";
		}
		else if(obj instanceof StargatePath){
			return "StargatePath";
		}
		
		//default case:
		return "";
	}

	/**
	 * Figure out the subtype of an object
	 * @param type the type object
	 * @param obj the object
	 * @return the string representation of the subtype
	 */
	private static String getSubtype(String type, CollidableGameObject obj)
	{
		if(type == "Enemy")
		{
			return obj.toString();
		}
		else if(type == "PowerUp")
		{
			return getPowerUpType(obj);
		}
		else if(type == "Debris"){
			return obj.toString();
		}
		else if(type == "DyePack" || type == "Bullet")
		{
			return obj.color == Colors.Blue ? "Blue" :
				obj.color == Colors.Green ? "Green":
				obj.color == Colors.Pink ? "Pink" :
				obj.color == Colors.Red ? "Red":
				obj.color == Colors.Teal ? "Teal" :
				"Yellow";
		}
		return "";
	}
	
	/**
	 * Get the type of the PowerUp
	 * @param obj the collidableGameObject PowerUp
	 * @return the name of the PowerUp as a String 
	 */
	private static String getPowerUpType(CollidableGameObject obj){
		String type = "";
		if(obj instanceof Ghost)
		{
			type = "Ghost";
		}
		else if(obj instanceof Gravity){
			type = "Gravity";
		}
		else if(obj instanceof Invincibility){
			type = "Invincibility";
		}
		else if(obj instanceof Magnetism){
			type = "Magnetism";
		}
		else if(obj instanceof Overload)
		{
			type = "Overload";
		}
		else if(obj instanceof Repel){
			type = "Repel";
		}
		else if(obj instanceof SlowDown){
			type = "SlowDown";
		}
		else if(obj instanceof SpeedUp){
			type = "SpeedUp";
		}
		else if(obj instanceof Unarmed){
			type = "Unarmed";
		}
		return type;
	}
	
}
