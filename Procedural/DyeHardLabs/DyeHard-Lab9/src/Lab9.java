import dyeHardProceduralAPI.DHProceduralAPI;
import dyeHardProceduralAPI.KeysEnum;

/**
 * Lab9: Loops with condition
 * 
 *	@author Holden
 *	@author Nin
 */
public class Lab9 extends DHProceduralAPI
{
	int heroID;

	public void buildGame(){
		heroID = apiStartHero();

		apiStartEnemySpawner();
		apiStartDyePackSpawner(0.7);
	}
	
	public void updateGame(){
		apiObjectFollowTheMouse(heroID);

		if(apiIsMouseLeftClicked()){
			apiHerofirePaint();
		}

		for(int i = 0; i < apiObjectCount(); i = i + 1){
			int firstID = apiGetID(i);

			String firstType = apiGetType(firstID);
			String firstSubtype = apiGetSubtype(firstID);

			for (int j = 0; j < apiObjectCount(); j = j + 1){
				int secondID = apiGetID(j);

				String secondType = apiGetType(secondID);
				String secondSubtype = apiGetSubtype(secondID);

				if(apiColliding(firstID, secondID))
				{

					if (firstType == "Bullet" && secondType == "Enemy"){
						if (secondSubtype == "Charger"){

							apiMoveObject(secondID, 10, 0);
						}
						else{
							if (firstSubtype == "Blue"){

								apiMoveObject(secondID, 0, -15);
							}
							else if (firstSubtype == "Green"){

								apiMoveObject(secondID, 0, 15);
							}
							else{

								apiDestroy(secondID);
							}

							if(firstSubtype != "Red"){
								
								apiDestroy(firstID);
							}
						}
					}
				}
			}
		}

	}
}
