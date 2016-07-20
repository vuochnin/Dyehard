package Demo2;
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
public class Demo2 extends DHProceduralAPI
{
	int heroID;
	
	public void buildGame()
	{

		heroID = startHero();
		echo("Hero started with ID: " + heroID);
	}
	
	public void updateGame()
	{
		if(repeatingTimer("debris", randomFloat(0.5f, 3)))
		{
			int spawnedID = spawnSingleDebris(10);
			echo("Spawned debris with ID: " + spawnedID);
		}
		
		for(int i = 0; i < objectCount(); i++)
		{
			int id = getID(i);
			if(getType(id) == "Debris")
			{
				if(apiGetObjectPositionY(id) > 20)
				{
					float x = apiGetObjectPositionX(id);
					API_MoveObjectTo(id, x, 10);
				}
				
				API_MoveObject(id, 0, 0.3f);
			}
			
		}
		
	}
}
