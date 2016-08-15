package ExampleGame;
import dyeHardProceduralAPI.DHProceduralAPI;

public class ExampleGame extends DHProceduralAPI
{
	int heroID;
	int goalID;

	int level = 1;

	public void buildGame(){
		heroID = apiStartHero();

		if(level == 1)
			Level1();
		else if (level == 2)
			Level2();
	}
	
	public void updateGame()
	{
		apiObjectFollowTheMouse(heroID);

		if(apiColliding(heroID, goalID))
		{
			level = level + 1;
			apiRestartGame();
		}
	}

	public void Level1()
	{
		for(int i = 0; i < 9; i = i + 1)
		{
			float yPosition = 0 + i * 5;

			apiSpawnSingleDebris(50, yPosition);
		}

		goalID = apiSpawnSingleDyePack("red", 70, 30);
	}


	public void Level2()
	{
		for(int i = 0; i < 4; i = i + 1)
		{
			float xPosition = 80 + i * 5;

			apiSpawnSingleDebris(xPosition, 35);
		}

		for(int i = 0; i < 4; i = i + 1)
		{
			float xPosition = 65 + i * 5;

			apiSpawnSingleDebris(xPosition, 20);
		}

		for(int i = 0; i < 9; i = i + 1)
		{
			float yPosition = 20 + i * 5;

			apiSpawnSingleDebris(65, yPosition);
		}

		goalID = apiSpawnSingleDyePack("red", 85, 45);
	}
}
