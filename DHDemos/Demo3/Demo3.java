package Demo3;
import dyeHardProceduralAPI.DHProceduralAPI;
import dyeHardProceduralAPI.KeysEnum;

/**
 *	Demonstrates weapons switching and interacting with objects by their IDs
 *
 *	Functions introduced:
 *		apiEcho(String)
 *		apiDefaultWeapon()
 *		apiActivateLimitedAmmoWeapon()
 *		apiActivateSpreadFireWeapon()
 *		apiRandomFloat(double)
 *		apiSpawnSingleDebris(double, double)
 *		apiObjectCount()
 *		apiGetID(int)
 *		apiGetType(int)
 *		apiGetObjectPositionX(int)
 *		apiGetObjectPositionY(int)
 *		apiMoveObjectTo(int, double, double)
 *		apiMoveObject(int, double, double)
 *
 *	@author Holden
 *	@author Nin
 *  	
 */
public class Demo3 extends DHProceduralAPI
{
	int heroID;
	
	public void buildGame()
	{

		heroID = apiStartHero();
		apiEcho("Hero started with ID: " + heroID);
	}
	
	int weaponselect = 0;
	
	public void updateGame()
	{
		apiObjectFollowTheMouse(heroID);
		
		if(apiIsMouseLeftClicked())
			apiHerofirePaint();
		
		// Switching the weapon
		if(apiIsKeyboardButtonTapped(KeysEnum.w)){
			weaponselect = (weaponselect + 1) % 3;
			
			switch(weaponselect)
			{
			case 0:
				apiDefaultWeapon();
				break;
			case 1:
				apiActivateLimitedAmmoWeapon();
				break;
			case 2:
				apiActivateSpreadFireWeapon();
				break;
			}
		}
		
		// Spawn single debris in random interval
		if(apiRepeatingTimer("debris", apiRandomFloat(3)))
		{
			int spawnedID = apiSpawnSingleDebris(100, 10);
			apiEcho("Spawned debris with ID: " + spawnedID);
		}
		
		// Interacting with debris
		for(int i = 0; i < apiObjectCount(); i++)
		{
			int id = apiGetID(i);
			if(apiGetType(id) == "Debris")
			{
				if(apiGetObjectPositionY(id) > 20)
				{
					float x = apiGetObjectPositionX(id);
					apiMoveObjectTo(id, x, 10);
				}
				
				apiMoveObject(id, 0, 0.3f);
			}
			
		}
		
	}
}
