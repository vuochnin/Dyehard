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

public class CollisionManager {

	private static DHProceduralAPI api;
	
	private static boolean collisionIsDirty;
	
	private static int firstID, secondID;
	
	private static dyehard.Collision.CollisionManager instance;
	static
	{
		instance = dyehard.Collision.CollisionManager.getInstance();
	}
	public static void register(DHProceduralAPI newApi)
	{
		api = newApi;
	}
	
	public static void update(){
		Set<CollidableGameObject> orig = instance.getCollidables();
		
		CollidableGameObject[] objects = orig.toArray(new CollidableGameObject[0]);
				
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

					handleCollision(objects[firstID], objects[secondID]);
					handleCollision(objects[secondID], objects[firstID]);
					
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
	
	public static void setDirty()
	{
		collisionIsDirty = true;
	}
	
	private static void handleCollision(CollidableGameObject first, CollidableGameObject second)
	{
		String type1 = "", type2 = "", subtype1 = "", subtype2 = "";
		
		type1 = parseType(first);
		type2 = parseType(second);
		
		// get types of Collidable objects
		
		api.handleCollisions(type1, subtype1, firstID, type2, subtype2, secondID);
	}
	
	private static String parseType(CollidableGameObject obj)
	{
		if(obj instanceof Hero){
			return "Hero";
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
	
	/**
	 * Get the type of the PowerUp
	 * @param obj the collidableGameObject PowerUp
	 * @return the name of the PowerUp as a String 
	 */
	private String getPowerUpType(CollidableGameObject obj){
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
