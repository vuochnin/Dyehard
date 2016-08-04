import java.awt.Color;
import java.awt.image.BufferedImage;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.Player.Hero;

public class Lab3Correct {
	
	// Test Fields
	private float height = 2;		// Test correctness
	private float width = 2;		// Test privacy/access
	private Color color;			// Test field type

	// Test Methods
	public void setCenter(float test, float test2){}	// Test correctness
	public void setHeight(float test){}					// Test return type
	public void setWidth(float test){}					// Test parameters
	public float getHeight(){ return 2; }				// Test access
	
    /**
     * Instantiates a new student obj.
     */
    public Lab3Correct() {
        height = 5f;
        width = 5f;
        System.out.println("Constructor called");
    }

    /**
     * @purpose	Instantiates a new student object.
     *
     * @param 	c, the center
     * @param 	w, the width
     * @param 	h, the height
     */
    public Lab3Correct(Vector2 c, float w, float h) {
        height = h;
        width = w;
    }

}
