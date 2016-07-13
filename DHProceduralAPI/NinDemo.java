import dyeHardProcedrualAPI.DHProceduralAPI;
import dyeHardProcedrualAPI.KeysEnum;

/**
 * NOTE: NO NEED TO PUT ANY CODE IN THIS CLASS YET
 * (THE GAME WILL RUN FROM NinProceduralAPI.java)
 * 
 * @author vuochnin
 *
 */
public class NinDemo extends DHProceduralAPI
{
	public void buildGame(){
		startHero();
		
		setLivesTo(6);
		displayScore(true);
		
		startDebrisSpawner(1.5f);
		startDyePackSpawner();
		startEnemySpawner();

		spawnGates();
	}
	
	public void updateGame(){
		heroFollowTheMouse();
		
		// TEST Change the weapon according to the keyboard inputs
		if(isKeyboardLeftPressed()){
			activateSpreadFireWeapon();
		}
		if(isKeyboardRightPressed()){
			activateLimitedAmmoWeapon();
		}
		if(isKeyboardDownPressed()){
			defaultWeapon();
		}
		if(isKeyboardButtonTapped(KeysEnum.p)){
			increaseScoreBy(2);
		}
		if(isKeyboardButtonTapped(KeysEnum.E)){
			spawnSingleEnemy("charger");			//TEST SpawnSingleEnemy()
		}



		if(repeatingTimer("powerup", 4))
		{
			spawnSinglePowerUp(100, 30);
		}

		// Fire the paint
		if(isMouseLeftClicked() || isKeyboardSpacePressed()){
			firePaint();
		}
	}
}
