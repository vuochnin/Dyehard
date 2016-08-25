package dyehard.GameScreens;

import Engine.BaseCode;
import dyehard.DyehardRectangle;

// TODO: Auto-generated Javadoc
/**
 * The Class Credit.
 */
public class Credit extends DyehardRectangle {
	
	/**
	 * Instantiates a new credit.
	 */
	public Credit() {
		
		size.set(BaseCode.world.getWidth() * 0.683f,
                BaseCode.world.getHeight());
		
		center.set(BaseCode.world.getWidth() / 2,
                BaseCode.world.getHeight() / 2);
		
		texture = BaseCode.resources
                .loadImage("Textures/UI/DyeHard_CreditsScroll.png");
		
		texture = BaseCode.resources
                .getImage("Textures/UI/DyeHard_CreditsScroll.png");
		
		setPanning(true);
		setPanningSheet(texture, 300, 2, true);
		visible = false;
		overRide = true;
		stopAtEnd = true;
	}
}
