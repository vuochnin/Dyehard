package dyeHardProceduralAPI;

import java.util.Random;

import Engine.BaseCode;
import dyehard.Obstacles.Debris;

/**
 * APIDebris is a sub class of Dyehard OOAPI Debris class. 
 * The purpose of this class is to initialize debris with the ability to 
 * identify the subtype (name) of each debris generated.
 * 
 * @author vuochnin
 *
 */
public class APIDebris extends Debris {
	int type;
	public String typeName;
	private static Random RANDOM = new Random();
	
	/**
	 * Constructor to initialize a debris
	 * @param type the type of the debris as Integer (There're 3 types)
	 * @param minX the left boundary of the spawning region
	 * @param maxX the right boundary of the spawning region
	 */
	public APIDebris(int type, float minX, float maxX){
		super(minX, maxX);
		this.type = type;
		switch (RANDOM.nextInt(3)) {
        case 0:
            texture = BaseCode.resources.loadImage("Textures/Debris/debris_01.png");
            typeName = "Plate";
            break;
        case 1:
            texture = BaseCode.resources.loadImage("Textures/Debris/debris_02.png");
            typeName = "Light";
            break;
        case 2:
            texture = BaseCode.resources.loadImage("Textures/Debris/debris_03.png");
            typeName = "Pipe";
            size.setX(size.getY() * 1.28f);
            break;
        }
	}
	
	/**
	 * Constructor to initialize a debris
	 * @param type the type of the debris as Integer (There're 3 types)
	 * @param minX the left boundary of the spawning region
	 * @param maxX the right boundary of the spawning region
	 * @param minY the lower boundary of the spawning region
	 * @param maxY the upper boundary of the spawning region
	 */
	public APIDebris(int type, float minX, float maxX, float minY, float maxY){
		super(minX, maxX, minY, maxY);
		this.type = type;
		switch (RANDOM.nextInt(3)) {
        case 0:
            texture = BaseCode.resources.loadImage("Textures/Debris/debris_01.png");
            typeName = "Plate";
            break;
        case 1:
            texture = BaseCode.resources.loadImage("Textures/Debris/debris_02.png");
            typeName = "Light";
            break;
        case 2:
            texture = BaseCode.resources.loadImage("Textures/Debris/debris_03.png");
            typeName = "Pipe";
            size.setX(size.getY() * 1.28f);
            break;
        }
	}
	
	/**
	 * Returns the types of the Debris as String. 
	 * Types are: "Plate", "Light", and "Pipe"
	 */
	public String toString(){
		return typeName;
	}
}
