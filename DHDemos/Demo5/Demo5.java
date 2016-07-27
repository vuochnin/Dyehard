package Demo5;


import java.awt.Color;

import dyeHardProceduralAPI.DHProceduralAPI;

/**
 * @author vuochnin
 * @author Holden
 *
 *	Demonstrates wormhole and color functions
 */
public class Demo5 extends DHProceduralAPI
{
	int heroID;
	
	public void buildGame()
	{
		heroID = apiStartHero();
		
		Color color1 = apiGetHeroColor();
		
		apiAddOneWormHole(color1, 50, 25, 170, 45);
		
		Color color2 = apiGetRandomColor();
		
		apiAddOneWormHole(color2, 50, 25, 170, 15);
		
	}
	
	public void updateGame()
	{
		apiObjectFollowTheMouse(heroID);
		
		if(apiRepeatingTimer("Gate", 10)){
			apiSpawnGates();
		}
		
		if(apiIsKeyboardSpacePressed()){
			apiSpeedUp(true);
		}
		else{
			apiSpeedUp(false);
		}
	}
}
