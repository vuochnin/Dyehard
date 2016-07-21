package Demo3;
import dyeHardProceduralAPI.DHProceduralAPI;
import dyeHardProceduralAPI.KeysEnum;

/**
 * 
 * @author vuochnin
 *
 *
 *	Demonstrates interacting with objects by their IDs, spawn single 
 *	debris object, and the repeatingTimer()
 */
public class Demo3 extends DHProceduralAPI
{
	int heroID;
	
	public void buildGame()
	{

		heroID = apiStartHero();
		apiEcho("Hero started with ID: " + heroID);
	}
	
	public void updateGame()
	{
		if(apiRepeatingTimer("debris", apiRandomFloat(0.5f, 3)))
		{
			int spawnedID = apiSpawnSingleDebris(10);
			apiEcho("Spawned debris with ID: " + spawnedID);
		}
		
		for(int i = 0; i < apiObjectCount(); i++)
		{
			int id = apiGetID(i);
			if(apiGetType(id) == "Debris")
			{
				if(apiGetObjectPositionY(id) > 20)
				{
					float x = apiGetObjectPositionX(id);
					apiMoveObjectTo(id, x, 10);
				}
				
				apiMoveObject(id, 0, 0.3f);
			}
			
		}
		
	}
}
