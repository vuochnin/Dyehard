package Demo1;
import dyeHardProceduralAPI.DHProceduralAPI;
import dyeHardProceduralAPI.KeysEnum;

/**
 *	Demonstrates DyeHard UI, menus, random object spawning, and other basic utilities 
 *	Functions used:
 *		apiStartHero()
 *		apiSetLivesTo()
 *		apiSetGoalDistance()
 *		apiShowDistanceMeter()
 *		apiStartDebrisSpawner()
 *		apiStartDyePackSpawner()
 *		apiStartEnemySpawner()
 *		apiObjectFollowTheMouse()
 *		apiIsMouseLeftClicked()
 *		apiHerofirePaint()
 *		apiRepeatingTimer()
 *		apiGetWorldWidth()
 *		apiGetWorldHeight()
 *		apiRandomFloat()
 *		apiSpawnSinglePowerUp(x, y)
 *		apiUserWon()
 *		apiShowWinMenu(true)
 *		apiUserLose()
 *		apiShowLoseMenu(true)
 *		apiIsKeyboardButtonTapped(KeysEnum)
 *		apiQuitGame()
 *		apiRestartGame()
 *
 *@author vuochnin
 */
public class Demo1 extends DHProceduralAPI {
	int heroID;
	
	public void buildGame(){
		heroID = apiStartHero();
		
		// UI
		apiSetLivesTo(3); 		// Sets hero health to 5 and display on the screen 
		apiShowScore(true);		// Shows the current score on the screen (Not updating in this demo)
		apiSetGoalDistance(500); 		// Set the goal
		apiShowDistanceMeter(true); 	// Displays the distance meter
		
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
		if(apiUserWon()){			// Check if the user win
			apiShowWinMenu(true);
		}
		if(apiUserLose()){			// Check if the user lose
			apiShowLoseMenu(true);
		}
		
		if(apiIsKeyboardButtonTapped(KeysEnum.Q)){
			apiQuitGame();
		}
		if(apiIsKeyboardButtonTapped(KeysEnum.R)){
			apiRestartGame();
		}
	}
}
