import dyeHardProceduralAPI.DHProceduralAPI;
import dyeHardProceduralAPI.KeysEnum;

/**
 * Lab8: American Flag! Introduces students to Nested For-loop
 * 
 *	@author Holden
 *	@author Nin
 */
public class Lab8 extends DHProceduralAPI
{
	
	public void buildGame(){
		
		// Blue background
		for(int row = 0; row < 7; row++){
			for(int col = 0; col < 7; col++){
				int x = 20 + col * 3;
				int y = 35 + row * 3;
				
				apiSpawnSingleDyePack("blue", x, y);
			}
		}
		
		//Stars. Since the white color is not available, we're gonna use teal instead
		for(int row = 0; row < 3; row++){
			for(int col = 0; col < 3; col++){
				int x = 23 + col * 6;
				int y = 38 + row * 6;
				
				apiSpawnSingleDyePack("teal", x, y);
			}
		}
		
		for(int row = 0; row < 2; row++){
			for(int col = 0; col < 2; col++){
				int x = 26 + col * 6;
				int y = 41 + row * 6;
				
				apiSpawnSingleDyePack("teal", x, y);
			}
		}
		
		// Stripes on the right of the stars
		for(int row = 0; row < 4; row++){
			for(int col = 0; col < 15; col++){
				int x = 41 + col * 3;
				int y = 35 + row * 6;
				
				apiSpawnSingleDyePack("red", x, y);
			}
		}
		for(int row = 0; row < 3; row++){
			for(int col = 0; col < 15; col++){
				int x = 41 + col * 3;
				int y = 38 + row * 6;
				
				apiSpawnSingleDyePack("teal", x, y);
			}
		}
		
		// lower 6 stripes
		for(int row = 0; row < 3; row++){
			for(int col = 0; col < 22; col++){
				int x = 20 + col * 3;
				int y = 17 + row * 6;
				
				apiSpawnSingleDyePack("red", x, y);
			}
		}
		for(int row = 0; row < 3; row++){
			for(int col = 0; col < 22; col++){
				int x = 20 + col * 3;
				int y = 20 + row * 6;
				
				apiSpawnSingleDyePack("teal", x, y);
			}
		}
	}
}
