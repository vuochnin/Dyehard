package dyeHardProceduralAPI;


import java.util.HashMap;
import java.util.Set;

import dyehard.Collectibles.*;
import dyehard.Collectibles.PowerUp;
import dyehard.Collision.CollidableGameObject;
import dyehard.Enemies.Enemy;
import dyehard.Enums.ManagerStateEnum;
import dyehard.Obstacles.Debris;
import dyehard.Player.Hero;
import dyehard.Util.Colors;
import dyehard.Weapons.Bullet;
import dyehard.World.WormHole.*;

public class CollisionManager {

	private static HashMap<String, Boolean> collisionMemory;
	
	
	private static int i, j;
	
	private static dyehard.Collision.CollisionManager instance;

	static
	{
		collisionMemory = new HashMap<>();
		instance = dyehard.Collision.CollisionManager.getInstance();
	}
	
	private static CollidableGameObject[] objects;
	
	public static int objectCount()
	{
		return objects.length;
	}

	public static void refreshSet()
	{
		instance.updateSet();
	}

	public static CollidableGameObject[] getObjects()
	{
		return objects;
	}

	private static CollidableGameObject lookup(int id)
	{
		return IDManager.get(id);
	}

	public static String getType(int i)
	{
		return parseType(lookup(i));
	}
	
	public static String getSubtype(int i)
	{
		return getSubtype(getType(i), lookup(i));
	}

	public static boolean rememberCollision(int id1, int id2)
	{
		return collisionMemory.getOrDefault(id1 + "," + id2, false);
	}
	
	public static void update(){
		collisionMemory.clear();
		
		refreshSet();

		Set<CollidableGameObject> orig = instance.getCollidables();

		objects = orig.toArray(new CollidableGameObject[0]);

		int count = objects.length;
		
		// ACTORS: Hero, Enemies
		// CollidableGameObjects: DyePacks, PowerUps, Bullets, and Debris
		for(i = 0; i < count; i++)
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

			for(j = i+1; j < count; j++)
			{
				//if(objects[secondID].collideState() != ManagerStateEnum.ACTIVE)
					//continue;
				
				if(objects[i].collided(objects[j]))
				{	
					objects[i].handleCollision(objects[j]);
					objects[j].handleCollision(objects[i]);
					
					String key = IDManager.reverseLookupID(objects[i])+
							"," + IDManager.reverseLookupID(objects[j]);
					
					collisionMemory.put(key, true);
				}
			}
		}
	}
	
	
	
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
