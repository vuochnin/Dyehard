import dyehard.GameScreens.BackgroundScreen;

public class Lab6Correct extends BackgroundScreen {
	
	
	public Lab6Correct(){
		super();
		shipTexturePaths[0] = "Textures/Background/Warp_green_back.png";
		shipTexturePaths[1] = "Textures/Background/Warp_green_back.png";
		shipTexturePaths[2] = "Textures/Background/Warp_green_back.png";
		shipTexturePaths[3] = "Textures/Background/Warp_green_back.png";
		shipTextures = loadTextures(shipTexturePaths);
		
		refreshBackgroundTiles();
		refreshForegroundAndShip();
	}
	
}
