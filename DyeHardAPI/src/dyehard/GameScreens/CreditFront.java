package dyehard.GameScreens;

import Engine.BaseCode;
import dyehard.DyehardRectangle;

// TODO: Auto-generated Javadoc
/**
 * The Class CreditFront.
 */
public class CreditFront extends DyehardRectangle {
	
	/**
	 * Instantiates a new credit front.
	 */
	public CreditFront() {
		
		size.set(BaseCode.world.getWidth(), BaseCode.world.getHeight());
		center.set(BaseCode.world.getWidth() / 2, BaseCode.world.getHeight() / 2);
		texture = BaseCode.resources
                .getImage("Textures/UI/DyeHard_CreditsFront.png");
		visible = false;
	}
}
