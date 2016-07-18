package Demo2;
import dyeHardProceduralAPI.DHProceduralAPI;
import dyeHardProceduralAPI.KeysEnum;

/**
 * 
 * @author vuochnin
 *
 * Demonstrates basic 
 */
public class Demo2 extends DHProceduralAPI
{

	public void buildGame()
	{
		startHero();
		
		 // Start an object spawner with the default setting
		startDebrisSpawner(0.5f);
		
		// Start object spawners with custom settings
		startDyePackSpawner(3);
		startEnemySpawner(2.5f);
		
	}
	
	public void updateGame()
	{
		heroFollowTheMouse();
		
		if(isMouseLeftClicked())
			firePaint();

		if(repeatingTimer("powerups", 1))
		{
			spawnSinglePowerUp(90, 30);
		}

		for(int i = 0; i < objectCount(); i++)
		{
			int id = getID(i); // Get an ID

			if(getType(id) == "Debris")
			{
				move(id,0,-0.1f);
			}

			// Demonstrate that IDs remain constant per object,
			// even as the backing array of objects has items removed.
			if(id % 3 == 1)
			{
				move(id, 0.2f, 0.05f);
			}
		}

		// Example collision loop
		for(int i = 0; i < objectCount(); i++)
		{
			int id1 = getID(i);
			for (int j = i+1; j < objectCount(); j++)
			{
				int id2 = getID(j);

				if(id1 % 11 == 3 && colliding(id1,id2))
				{
					echo("Destroyed");
					destroy(id1);
					destroy(id2);
				}
			}
		}

	}
	
	// Define custom collision code
//	public void handleCollisions(String type1, String subtype1, int id1, String type2, String subtype2, int id2)
//	{
//		// Examine the types of the colliding objects
//		if(type1 == "Debris" && type2 == "Debris")
//		{
//			move(id1,-2,-4);
//			move(id2, 2, 4); // This function overrides default collision code
//		}
//
//		if(type1 == "Bullet" && subtype2 == "Charger")
//		{
//			destroy(id1);
//			destroy(id2);
//		}
//
//		if(type1 == "Hero" && subtype2 == "Shooting")
//		{
//			doNothing(); // prevent default collision behavior
//		}
//
//		// DyePacks will have default behavior on collision
//	}
	
}
