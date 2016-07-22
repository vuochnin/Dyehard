package Demo4;


import dyeHardProceduralAPI.DHProceduralAPI;
import dyeHardProceduralAPI.KeysEnum;

/**
 * 
 * @author vuochnin
 *
 *
 *	Demonstrates
 */
public class Demo4 extends DHProceduralAPI
{
	int heroID;
	
	public void buildGame()
	{
		
		heroID = apiStartHero();
		apiEcho("Hero started with ID: " + heroID);
		
		apiStartEnemySpawner(1);
	}
	
	public void updateGame()
	{
		apiObjectFollowTheMouse(heroID);
		
		if(apiRepeatingTimer("reverse debris", 0.6f))
		{
			int debrisID = apiSpawnSingleDebris();
			
			float y = apiGetObjectPositionY(debrisID);
			
			apiMoveObjectTo(debrisID, 0, y);
			apiSetObjectVelocity(debrisID, 0.5, 0);
		}

		
		// Collison loop
		for(int i = 0; i < apiObjectCount(); i++)
		{
			int id1 = apiGetID(i);
			for(int j = 0; j < apiObjectCount(); j++)
			{
				if(j == i) continue;
				
				int id2 = apiGetID(j);
				if(apiColliding(id1, id2))
				{
					if((apiGetType(id1) == "Debris" &&
							apiGetType(id2) == "Enemy"))
					{

						apiEcho("1");
						apiDestroy(id2);
					}

				}
					
			}
		}
		
		//echo("Objects in play: " + objectCount());
		
	}
}
