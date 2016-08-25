import dyehard.Util.Colors;

import java.awt.Color;

import Engine.Vector2;

public class Lab3Incorrect {

	// Test Fields
	private float height = 2;		// Test correctness
	public float width = 2;			// Test privacy/access
	private boolean color = false;	// Test field type
	
	// Test Constructors
	public Lab3Incorrect(){}								// Test Correct Default Constructor
	public Lab3Incorrect(Vector2 c, float w, float h){}		// Test Correct Param Constructor
	public Lab3Incorrect(Vector2 c, float w, int h){}		// Test Parameter Type
	public Lab3Incorrect(Vector2 c, float w){}				// Test Parameter Count
	
	// Test Methods
	public void setCenter(float test, float test2){}		// Test correctness
	public boolean setHeight(float test){ return false; }	// Test return type
	public void setWidth(boolean test, int test2){}			// Test parameters
	private float getHeight(){ return 2; }					// Test access
	public Colors setColor(Colors toSet){ return toSet; }	
	
}
