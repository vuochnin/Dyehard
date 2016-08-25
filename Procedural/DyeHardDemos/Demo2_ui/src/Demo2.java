import dyeHardProceduralAPI.DHProceduralAPI;
import dyeHardProceduralAPI.KeysEnum;

/**
 *	Demonstrates DyeHard UI, menus, random object spawning, and other basic utilities 
 *	Functions introduced:
 *		apiShowStartScreen()
 *		apiSetLivesTo(int)
 *		apiSetGoalDistance(int)
 *		apiShowDistanceMeter()
 *		apiStartDebrisSpawner()
 *		apiStartEnemySpawner()
 *		apiGetWorldWidth()
 *		apiGetWorldHeight()
 *		apiRandomFloat(double)
 *		apiSpawnSinglePowerUp(double, double)
 *		apiUserWon()
 *		apiShowWinMenu(boolean)
 *		apiUserLose()
 *		apiShowLoseMenu(boolean)
 *		apiQuitGame()
 *		apiRestartGame()
 *		
 *	@author Holden
 *	@author Nin
 */
public class Demo2 extends DHProceduralAPI {
	int heroID;
	
	public void buildGame(){
		heroID = apiStartHero();
		
		// UI
		apiShowStartScreen();
		apiSetLivesTo(3); 		// Sets hero health to 5 and display on the screen 
		apiShowScore(true);		// Shows the current score on the screen (Not updating in this demo)
		apiSetGoalDistance(500); 		// Set the goal
		apiShowDistanceMeter(); 	// Displays the distance meter
		
		// START OBJECT SPAWNERS WITH DEFAULT SETTING
		apiStartDebrisSpawner();	// Spawns Debris randomly
		apiStartDyePackSpawner();	// Spawns DyePack randomly
		apiStartEnemySpawner();		// Spawns Enemy randomly
	}
	
	public void updateGame()
	{
		apiObjectFollowTheMouse(heroID);	// Moves Dye (Hero) using the mouse
		
		// Fire the paint every time the left button of the mouse clicked
		if(apiIsMouseLeftClicked()){
			apiHerofirePaint();
		}
		
		// POWER UP. Spawn power up objects randomly every 5 seconds
		if(apiRepeatingTimer("PowerUp Spawner", 5)){
			float posX = apiGetWorldWidth();
			float posY = apiGetWorldHeight();
			apiSpawnSinglePowerUp(posX, apiRandomFloat(posY));
		}
		
		// MENUs
		apiHandleWinLose();
		
		if(apiIsKeyboardButtonTapped(KeysEnum.Q)){
			apiQuitGame();
		}
		if(apiIsKeyboardButtonTapped(KeysEnum.R)){
			apiRestartGame();
		}
	}
}
