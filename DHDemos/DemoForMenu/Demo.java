package DemoForMenu;
import dyeHardProceduralAPI.DHProceduralAPI;

/**
 * 
 * @author vuochnin
 *
 *
 *	Demonstrates DyeHard UI menu (not complete yet)
 */
public class Demo extends DHProceduralAPI
{
	int heroID;
	
	public void buildGame(){
		heroID = apiStartHero();
		
		// Start object spawners with the default setting
		apiStartDebrisSpawner();
		apiStartDyePackSpawner();
		apiStartEnemySpawner();
		
	
	}
	
	public void updateGame()
	{
		apiObjectFollowTheMouse(heroID);
		if(apiRepeatingTimer("PowerUp Spawner", 5)){
			float posY = apiGetWorldHeight();
			apiSpawnSinglePowerUp(apiGetWorldWidth(), apiRandomFloat(posY));
		}
	}
}
