import dyeHardProceduralAPI.DHProceduralAPI;
import dyeHardProceduralAPI.KeysEnum;
import dyehard.DyeHardGame;
import dyehard.World.GameState;

/**
 * 
 * 
 * @author vuochnin
 *
 */
public class NinDemo extends DHProceduralAPI
{
	int heroID;
	
	public void buildGame(){
		heroID = apiStartHero();
		apiShowScore(true);
		apiSetLivesTo(8);
		apiSetGoalDistance(1000);
		apiShowDistanceMeter();
		apiSetWinningScore(15);
		apiStartDebrisSpawner(1.5f);
		apiStartDyePackSpawner();
		apiStartEnemySpawner();
		//spawnGates();
		
		apiSpawnSingleDebris(30, 30);
		
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
		
		if(apiIsKeyboardButtonTapped(KeysEnum.y)){			// CHECK debris subtype
			int deId = apiSpawnSingleDebris();
			apiEcho("The subtype of this debris is " + apiGetSubtype(deId));
		}
		
		// TEST Change the weapon according to the keyboard inputs
		if(apiIsKeyboardLeftPressed()){
			apiActivateSpreadFireWeapon();
		}
		if(apiIsKeyboardDownPressed()){
			apiDefaultWeapon();
		}
		if(apiIsKeyboardButtonTapped(KeysEnum.S)){
			apiAdjustScoreBy(2);
		}
		if(apiIsKeyboardButtonTapped(KeysEnum.A)){
			apiAdjustScoreBy(-2);
		}
		if(apiIsKeyboardButtonTapped(KeysEnum.E)){
			apiSpawnSingleEnemy("Portal", 50, 20);			//TEST SpawnSingleEnemy()
		}
		if(apiIsKeyboardButtonTapped(KeysEnum.D))
			apiSpawnSingleDyePack("blue", 50, 20); 		// TEST spawnSingleDyePack(color, x, y)

		if(apiIsKeyboardButtonTapped(KeysEnum.ESCAPE)){
			if(getState() == State.PLAYING)
				DyeHardGame.setState(State.PAUSED);
			else
				DyeHardGame.setState(State.PLAYING);
		}
		
		if(apiIsKeyboardButtonTapped(KeysEnum.X))
			apiQuitGame();
		
		if(apiUserLose()){
			apiShowLoseMenu(true);
		}
		if(apiUserWon()){
			apiShowWinMenu(true);
		}
		
		if(apiIsKeyboardRightPressed()){
			apiSpeedUp(true);
		}else{
			apiSpeedUp(false);
		}
		if(apiIsKeyboardButtonTapped(KeysEnum.G))
			apiSpawnSinglePowerUp("SlowDown", 90, 30);
		
		if(apiIsKeyboardButtonTapped(KeysEnum.b))
			apiEcho("DistanceTravelled = " + GameState.DistanceTravelled);
		if(apiIsKeyboardButtonTapped(KeysEnum.r))
			apiRestartGame();
		if(apiIsKeyboardButtonTapped(KeysEnum.c))
			apiEcho("count " + apiObjectCount());

	}
		
		// AMERICAN FLAG		
//		for(int row = 0; row < 7; row++){
//			for(int col = 0; col < 7; col++){
//				int x = 25 + col * 3;
//				int y = 35 + row * 3;
//				
//				apiSpawnSingleDyePack("blue", x, y);
//			}
//		}
//		
//		for(int i = 0; i < 5; i++){
//			for(int k = 0; k < 5; k++){
//				int x = 28 + k*3;
//				int y = 38 + i*3;
//				if(i%2 == 0 && k%2 == 0) // both i and k are even
//					apiSpawnSingleDyePack("teal", x, y);
//				else if(i%2 == 1 && k%2 == 1)	// both i and k are odd
//					apiSpawnSingleDyePack("teal", x, y);
//			}
//		}
//		
//		
//		for(int i = 0; i < 7; i++){
//			for(int k = 0; k < 15; k++){
//				int x = 46 + k * 3;
//				int y = 35 + i * 3;
//				if(i%2 == 0)
//					apiSpawnSingleDyePack("red", x, y);
//				else
//					apiSpawnSingleDyePack("teal", x, y);
//			}
//		}
//		
//		for(int i = 0; i < 4; i++){
//			for(int k = 0; k < 22; k++){
//				int x = 25 + k * 3;
//				int y = 23 + i * 3;
//				if(i%2 == 0)
//					apiSpawnSingleDyePack("red", x, y);
//				else
//					apiSpawnSingleDyePack("teal", x, y);
//			}
//		}

}
