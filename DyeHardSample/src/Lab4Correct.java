import java.util.Random;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.Obstacles.Debris;
import dyehard.Resources.ConfigurationFileParser;

public class Lab4Correct extends Debris {
	
	// Private members from Debris superclass
	private static Random RANDOM = new Random();  
	private float width = 5f;
	private float height = 10f;
	
	// Variables to mess around with: center, size, velocity, speed, shouldTravel

	
	public Lab4Correct(float minX, float maxX) {
		this(minX,maxX, BaseCode.world.getWorldPositionY(), BaseCode.world.getHeight());
	}

	// Objective: Define a new width, height, speed, and texture
	public Lab4Correct(float minX, float maxX, float minY, float maxY) {
		super(minX, maxX, minY, maxY);
		
		float randomX = (maxX - minX - width) * RANDOM.nextFloat()
                + minX + width / 2f;
		float randomY = (maxY - minY - height)* RANDOM.nextFloat()
                + minY + height / 2f;

		center.set(new Vector2(randomX, randomY));
		size.set(width, height);

		float speed = 1.5f;
		velocity = new Vector2(-(RANDOM.nextFloat() * speed / 2 + speed), 0f);
		shouldTravel = true;

		// Modify texture
		texture = BaseCode.resources.loadImage("Beak.png");
	}

}
