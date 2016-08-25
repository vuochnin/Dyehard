import dyeHardProceduralAPI.DHProceduralAPI;
import dyeHardProceduralAPI.KeysEnum;
import dyehard.DyeHardGame;
import dyehard.World.GameState;


public class APIDemo extends DHProceduralAPI
{
	int heroID;
	
	public void buildGame(){
		heroID = apiStartHero();
		apiShowScore(true);
		apiSetLivesTo(4);
		apiSetGoalDistance(1000);
		apiShowDistanceMeter();
		apiSetWinningScore(30);
		apiShowStartScreen();
		apiStartDebrisSpawner(1.5f);
		apiStartDyePackSpawner();		// Spawn DyePack randomly in the world
		apiStartEnemySpawner();			// Spawn enemy randomly in the world
	}
	
	public void updateGame(){
		
		if(apiRepeatingTimer("powerup", 5)){
			apiSpawnSinglePowerUp(apiGetWorldWidth() - 10f, apiRandomInt((int)apiGetWorldHeight() - 10));
		}
		
		apiObjectFollowTheMouse(heroID);
		
		// Fire the paint
		if(apiIsMouseLeftClicked() || apiIsKeyboardSpacePressed()){
			apiHerofirePaint();
		}
		
		// MENUs
		apiHandleWinLose();
		
		if(apiIsKeyboardButtonTapped(KeysEnum.Q)){
			apiQuitGame();
		}
		if(apiIsKeyboardButtonTapped(KeysEnum.R)){
			apiRestartGame();
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

						apiEcho("Special collision behavior executed. " + apiGetSubtype(id1));
						apiDestroy(id2);
					}
					
					if(apiGetType(id1) == "Hero" && apiGetType(id2) == "Enemy")
					{
						apiDestroy(id1);
						apiAdjustScoreBy(-1);
					}
					
					if(apiGetType(id1) == "Bullet" && apiGetType(id2) == "Enemy" &&
							apiGetSubtype(id2) != "Charger")
					{
						apiAdjustScoreBy(5);
						apiDestroy(id2);
					}
				}	
			}
		}

	}
		


}
