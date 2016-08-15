package lab3;
import dyeHardProceduralAPI.DHProceduralAPI;
import dyeHardProceduralAPI.KeysEnum;

/**
 * Lab3: Introduce If/Else statement
 * 
 *	@author Holden
 *	@author Nin
 */
public class Lab3 extends DHProceduralAPI
{
	public void updateGame()
	{
		if(apiIsKeyboardButtonTapped(KeysEnum.UP))
		{
			apiSpawnSingleDebris();
		}
		else if(apiIsKeyboardButtonTapped(KeysEnum.DOWN))
		{
			apiSpawnSingleDebris(50, 30);
		}
		
		if(apiIsKeyboardButtonTapped(KeysEnum.LEFT))
		{
			apiSpawnSingleDyePack();
		}
		else if(apiIsKeyboardButtonTapped(KeysEnum.RIGHT))
		{
			apiSpawnSingleDyePack("blue", 20, 20);
		}
	}
}
