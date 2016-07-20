package Demo3;


import dyeHardProceduralAPI.DHProceduralAPI;
import dyeHardProceduralAPI.KeysEnum;

/**
 * 
 * @author vuochnin
 *
 *
 *	Demonstrates
 */
public class Demo3 extends DHProceduralAPI
{
	int heroID;
	
	public void buildGame()
	{
		
		heroID = startHero();
		echo("Hero started with ID: " + heroID);
		
		startEnemySpawner(1);
	}
	
	public void updateGame()
	{
		API_ObjectFollowTheMouse(heroID);
		
		if(repeatingTimer("reverse debris", 0.6f))
		{
			int debrisID = spawnSingleDebris();
			
			float y = apiGetObjectPositionY(debrisID);
			
			API_MoveObjectTo(debrisID, 0, y);
			apiSetObjectVelocity(debrisID, 0.5, 0);
		}

		
		// Collison loop
		for(int i = 0; i < objectCount(); i++)
		{
			int id1 = getID(i);
			for(int j = 1; j < objectCount(); j++)
			{
				if(j == i) continue;
				
				int id2 = getID(j);
				if(colliding(id1, id2))
				{
					if((getType(id1) == "Debris" &&
							getType(id2) == "Enemy"))
					{

						echo("1");
						destroy(id2);
					}
					else if(getType(id2) == "Debris" &&
							getType(id1) == "Enemy")
					{
						echo("2");
						destroy(id1);
					}

				}
					
			}
		}
		
		//echo("Objects in play: " + objectCount());
		
	}
}
