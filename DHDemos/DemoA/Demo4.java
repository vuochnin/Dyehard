package DemoA;
import dyeHardProceduralAPI.DHProceduralAPI;
import dyeHardProceduralAPI.KeysEnum;

/**
 * 
 * @author vuochnin
 *
 * Demonstrates basic 
 */
public class Demo4 extends DHProceduralAPI
{

	int heroID;
	
	public void buildGame()
	{
		heroID = apiStartHero();
		
		 // Start an object spawner with the default setting
		apiStartDebrisSpawner(0.5f);
		
		// Start object spawners with custom settings
		apiStartDyePackSpawner(3);
		apiStartEnemySpawner(2.5f);
		
	}
	
	public void updateGame()
	{
		apiObjectFollowTheMouse(heroID);
		
		if(apiIsMouseLeftClicked())
			apiHerofirePaint();

		if(apiRepeatingTimer("powerups", 1))
		{
			apiSpawnSinglePowerUp(90, 30);
		}

		for(int i = 0; i < apiObjectCount(); i++)
		{
			int id = apiGetID(i); // Get an ID

			if(apiGetType(id) == "Debris")
			{
				//APIMoveObject
				apiMoveObject(id,0,-0.1f);
			}

			// Demonstrate that IDs remain constant per object,
			// even as the backing array of objects has items removed.
			if(id % 3 == 1)
			{
				apiMoveObject(id, 0.2f, 0.05f);
			}
		}

		// Example collision loop
		for(int i = 0; i < apiObjectCount(); i++)
		{
			int id1 = apiGetID(i);
			
			for (int j = i+1; j < apiObjectCount(); j++)
			{
				int id2 = apiGetID(j);

				if(id1 % 11 == 3 && apiColliding(id1,id2))
				{
					apiEcho("Destroyed");
					apiDestroy(id1);
					apiDestroy(id2);
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
