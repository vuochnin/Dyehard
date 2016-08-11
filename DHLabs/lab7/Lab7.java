package lab7;
import dyeHardProceduralAPI.DHProceduralAPI;
import dyeHardProceduralAPI.KeysEnum;

/**
 * Lab7: Introduce students to For-loops
 * 
 *	@author Holden
 *	@author Nin
 */
public class Lab7 extends DHProceduralAPI
{
	int heroID;
	
	public void buildGame(){
		heroID = apiStartHero();
		
		for(int i = 0; i < 5; i++){
			int y = i * 5;
			apiSpawnSingleDebris(50, y);
		}
		
		for(int i = 0; i < 5; i++){
			
			int y = 58 + i * -5;
			
			apiSpawnSingleDebris(60, y);
		}
		

		for(int i = 0; i < 5; i++){
			
			int x = 50 + i * 5;
			
			apiSpawnSingleDebris(x, 20);
		}
		apiSpawnSingleDyePack("Yellow", 55, 15);
		
	}
	
	public void updateGame(){
		apiObjectFollowTheMouse(heroID);
		
	}	
}
