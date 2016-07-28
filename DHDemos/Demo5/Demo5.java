package Demo5;


import java.awt.Color;

import dyeHardProceduralAPI.DHProceduralAPI;

/**
 *	Demonstrates wormhole and color functions
 *
 *	Functions introduced:
 *		apiGetHeroColor()
 *		apiAddOneWormHole(Color, double, double, double, double)
 *		apiGetRandomColor()
 *		apiSpawnGates()
 *		apiSpeedUp(boolean)
 *		apiGetTravelledDistance()
 *	
 *	@author Holden
 *	@author Nin
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
		
		if(apiGetTravelledDistance() == 300){
			apiAddOneWormHole(apiGetHeroColor(), 0f, 60f, 120f, 30f);
		}
	}
}
