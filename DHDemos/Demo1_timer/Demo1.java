package Demo1_timer;
import dyeHardProceduralAPI.DHProceduralAPI;

/**
 * 
 * @author vuochnin
 *
 *
 *	Demonstrates timer functions, spawning single object using timer
 *
 *	Functions introduced:
 *		apiStartDebrisSpawner(double)
 *		apiSetSingleTimer(String, double)
 *		apiIsTimerFinished(String)
 *		apiSpawnSinglePowerUp(String, double, double)
 *		apiRepeatingTimer(String, double)
 *		apiStartDyePackSpawner()
 *		apiStopDyePackSpawner()
 *		apiIsMouseLeftClicked()
 *		apiHerofirePaint()
 *
 *	@author Holden
 *	@author Nin
 */
public class Demo1 extends DHProceduralAPI
{
	int heroID;
	
	public void buildGame(){
		heroID = apiStartHero();
	 
		// Start an object spawner with the default setting
		apiStartDebrisSpawner(0.5f);
	
		apiSetSingleTimer("test", 2);
		apiSetSingleTimer("dyepack", 5);
	
	}
	
	public void updateGame()
	{
		if(apiIsTimerFinished("test")){
			apiSpawnSinglePowerUp("Ghost", 70, 20);
		}
	
		if(apiIsTimerFinished("dyepack"))
		{
			apiStartDyePackSpawner();
			apiSetSingleTimer("dyepack_stop", 5);
		}
	 
		if(apiIsTimerFinished("dyepack_stop"))
		{
			apiStopDyePackSpawner();
			apiSetSingleTimer("dyepack", 5);
		}
		
		// POWER UP. Spawn a SlowDown power up objects every 10 seconds
		if(apiRepeatingTimer("PowerUp Spawner", 10)){
			apiSpawnSinglePowerUp("SlowDown", 90, 30);
		}
	 
		apiObjectFollowTheMouse(heroID);
	 
		if(apiIsMouseLeftClicked())
			apiHerofirePaint();
	
	}
}
