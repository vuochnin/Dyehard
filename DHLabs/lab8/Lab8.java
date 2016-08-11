package lab8;
import dyeHardProceduralAPI.DHProceduralAPI;
import dyeHardProceduralAPI.KeysEnum;

/**
 * Lab8: American Flag! Introduce students to Nested For-loop
 * 
 *	@author Holden
 *	@author Nin
 */
public class Lab8 extends DHProceduralAPI
{
	int heroID;
	
	public void buildGame(){
		//heroID = apiStartHero();
		
		
		
		for(int i = 0; i < 7; i++){
			for(int k = 0; k < 7; k++){
				int x = 25 + k * 3;
				int y = 35 + i * 3;
				
				apiSpawnSingleDyePack("blue", x, y);
			}
		}
		
//		for(int i = 0; i < 3; i++){
//			for(int k = 0; k < 3; k++){
//				int x = 28 + k * 6;
//				int y = 38 + i * 6;
//				
//				apiSpawnSingleDyePack("teal", x, y);
//			}
//		}
		
		for(int i = 0; i < 5; i++){
			for(int k = 0; k < 5; k++){
				int x = 28 + k*3;
				int y = 38 + i*3;
				if(i%2 == 0 && k%2 == 0) // both i and k are even
					apiSpawnSingleDyePack("teal", x, y);
				else if(i%2 == 1 && k%2 == 1)	// both i and k are odd
					apiSpawnSingleDyePack("teal", x, y);
			}
		}
		
		
		for(int i = 0; i < 7; i++){
			for(int k = 0; k < 15; k++){
				int x = 46 + k * 3;
				int y = 35 + i * 3;
				if(i%2 == 0)
					apiSpawnSingleDyePack("red", x, y);
				else
					apiSpawnSingleDyePack("teal", x, y);
			}
		}
		
		for(int i = 0; i < 4; i++){
			for(int k = 0; k < 22; k++){
				int x = 25 + k * 3;
				int y = 23 + i * 3;
				if(i%2 == 0)
					apiSpawnSingleDyePack("red", x, y);
				else
					apiSpawnSingleDyePack("teal", x, y);
			}
		}
		// stars end
//		for(int i = 0; i < 5; i++){
//			for(int k = 0; k < 5; k++){
//				int x = 55 + k * 3;
//				int y = 29 + i * 6;
//				
//				apiSpawnSingleDyePack("red", x, y);
//			}
//		}
//		for(int i = 0; i < 5; i++){
//			for(int k = 0; k < 5; k++){
//				int x = 55 + k * 3;
//				int y = 32 + i * 3;
//				
//				apiSpawnSingleDyePack("yellow", x, y);
//			}
//		}
		
		
//		for(int i = 0; i < 5; i++){
//			for(int k = 0; k <= i; k++){
//				int x = 55 + k * 3;
//				int y = 10 + i * 3;
//				
//				apiSpawnSingleDyePack("green", x, y);
//			}
//		}
	}
	
	public void updateGame(){
		//apiObjectFollowTheMouse(heroID);
		
	}
	

	
}
