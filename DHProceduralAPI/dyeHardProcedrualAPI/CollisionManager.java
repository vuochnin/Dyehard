package dyeHardProcedrualAPI;


import java.util.Arrays;
import java.util.Set;

import dyehard.Collectibles.*;
import dyehard.Collectibles.PowerUp;
import dyehard.Collision.CollidableGameObject;
import dyehard.Enemies.Enemy;
import dyehard.Enemies.EnemyManager;
import dyehard.Enums.ManagerStateEnum;
import dyehard.Obstacles.Debris;
import dyehard.Player.Hero;
import dyehard.Weapons.Bullet;
import dyehard.World.WormHole.*;

public class CollisionManager {

	
	private static DHProceduralAPI api;
	
	private static boolean collisionIsDirty;
	
	private static int firstID, secondID;
	
	private static dyehard.Collision.CollisionManager instance;
	static
	{
		instance = dyehard.Collision.CollisionManager.getInstance();
	}
	
	private static CollidableGameObject[] objects;
	
	public static void register(DHProceduralAPI newApi)
	{
		api = newApi;
	}
	
	public static CollidableGameObject findByID(int id)
	{
		return objects[id];
	}
	
	public static int objectCount()
	{
		return objects.length;
	}
	
	public static String getType(int i)
	{
		return parseType(objects[i]);
	}
	
	public static String getSubtype(int i)
	{
		return getSubtype(getType(i), objects[i]);
	}
	public static void update(){
		Set<CollidableGameObject> orig = instance.getCollidables();
		
		
		objects = orig.toArray(new CollidableGameObject[0]);
				
		int count = objects.length;
		
		// ACTORS: Hero, Enemies
		// CollidableGameObjects: DyePacks, PowerUps, Bullets, and Debris
		for(firstID = 0; firstID < count; firstID++)
		{
			if(objects[firstID].collideState() != ManagerStateEnum.ACTIVE)
				continue;
			
			for(secondID = firstID+1; secondID < count; secondID++)
			{
				if(objects[secondID].collideState() != ManagerStateEnum.ACTIVE)
					continue;
				
				if(objects[firstID].collided(objects[secondID]))
				{	
					collisionIsDirty = false;

					handleCollision(objects[firstID], firstID, objects[secondID], secondID);
					handleCollision(objects[secondID], secondID, objects[firstID], firstID);
					
					// if the user has not executed custom behavior
					// do default behavior
					if(!collisionIsDirty) 
					{
						objects[firstID].handleCollision(objects[secondID]);
						objects[secondID].handleCollision(objects[firstID]);
					}
				}
			}
		}
		
		instance.updateSet();
	}
	
	public static boolean isColliding(int id1, int id2){
		if(id1 < 0 || id1 >= objectCount())
		{
			System.err.println("Invalid id for id1: " + id1);
			return false;
		}
		if(id2 < 0 || id2 >= objectCount())
		{
			System.err.println("Invalid id for id2: " + id2);
			return false;
		}
		
		return objects[id1].collided(objects[id2]);
	}
	
	public static void setDirty()
	{
		collisionIsDirty = true;
	}
	
	private static void handleCollision(CollidableGameObject first, int id1, CollidableGameObject second, int id2)
	{
		String type1 = "", type2 = "", subtype1 = "", subtype2 = "";
		
		type1 = parseType(first);
		type2 = parseType(second);

		subtype1 = getSubtype(type1, first);
		subtype2 = getSubtype(type2, second);
		
		// get types of Collidable objects
		if(type1 != "" && type2 != "")
			api.handleCollisions(type1, subtype1, id1, type2, subtype2, id2);
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
	

	
	
	
	
	
	
	
	
//	private void handleHeroCollisions(CollidableGameObject hero, CollidableGameObject other)
//	{
//		//if other instanceof Debris
//		if(other instanceof Debris){
//			int id = DebrisGenerator.getID((Debris)other);
//			//handleDebrisCollision(id);
//		}
//		//handle enemy(id, type)
//		else if(other instanceof Enemy){
//			String type = other.toString();
//			int id = EnemyManager.getInstance().getId((Enemy) other);
//			//handleEnemyCollision(id, type);
//		}
//		
//		// powerup(id, type)
//		else if(other instanceof PowerUp)
//		{
//			String type = getPowerUpType(other);
//			
//			//handlePowerUpCollision(id, type);
//		}
//		
//		// dyePack(id, color)
//		else if(other instanceof DyePack){
//			
//		}
//	}
	
	private static String getSubtype(String type, CollidableGameObject obj)
	{
		if(type == "Enemy")
		{
			return obj.toString();
		}
		else if(type == "PowerUp")
		{
			return getPowerUpType(obj);
		}else if(type == "DyePack")
		{
			// return color of dyepack
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
