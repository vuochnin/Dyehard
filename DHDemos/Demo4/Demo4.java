package Demo4;


import dyeHardProceduralAPI.DHProceduralAPI;

/**
 *	Demonstrates collision behavior in order to customize behavior for collisions
 *	
 *	Functions introduced:
 *		apiStartEnemySpawner(double)
 *		apiSpawnSingleDebris()
 *		apiRandomFloat(double, double)
 *		apiSetObjectVelocity(int, double, double)
 *		apiColliding(int, int)
 *		apiGetSubtype(int)
 *		apiDestroy(int)
 *		apiIncreaseScoreBy(int)
 *		apiDecreaseScoreBy(int)
 *	
 *	@author Holden
 *	@author Nin
 */
public class Demo4 extends DHProceduralAPI
{
	int heroID;
	
	public void buildGame()
	{
		
		heroID = apiStartHero();
		apiShowScore(true);
		apiSetLivesTo(3);
		apiStartEnemySpawner(1);
	}
	
	public void updateGame()
	{
		apiObjectFollowTheMouse(heroID);
		
		if(apiIsMouseLeftClicked())
			apiHerofirePaint();
		
		
		// Custom debris loop
		if(apiRepeatingTimer("reverse debris", 1.2))
		{
			int debrisID = apiSpawnSingleDebris();
			
			float y = apiGetObjectPositionY(debrisID);
			
			float yspeed = apiRandomFloat(-0.1, 0.1);
			
			apiMoveObjectTo(debrisID, 0, y);
			apiSetObjectVelocity(debrisID, 0.3, yspeed);
		}

		
		// Collision loop
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

						apiEcho("Special collision behavior executed." + apiGetSubtype(id1));
						apiDestroy(id2);
					}
					
					if(apiGetType(id1) == "Hero" && apiGetType(id2) == "Enemy")
					{
						apiDestroy(id1);
						apiDecreaseScoreBy(1);
					}
					
					if(apiGetType(id1) == "Bullet" && apiGetType(id2) == "Enemy" &&
							apiGetSubtype(id2) != "Charger")
					{
						apiIncreaseScoreBy(2);
						apiDestroy(id2);
					}

				}
					
			}
		}
	}
}
